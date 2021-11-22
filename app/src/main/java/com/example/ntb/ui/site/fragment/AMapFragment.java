package com.example.ntb.ui.site.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.INavi;
import com.amap.api.navi.model.NaviLatLng;
import com.example.ntb.app.App;
import com.example.ntb.base.BaseFragment;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.bean.Areas;
import com.example.ntb.ui.city.bean.CityEvent;
import com.example.ntb.ui.city.bean.SelectedCityInfo;
import com.example.ntb.ui.db.DaoSession;
import com.example.ntb.ui.site.bean.JsonAllAmap;
import com.example.ntb.ui.site.bean.SiteInfo;
import com.example.ntb.ui.util.ClusterClickListener;
import com.example.ntb.ui.util.ClusterItem;
import com.example.ntb.ui.util.ClusterOverlay;
import com.example.ntb.ui.util.ClusterRender;
import com.example.ntb.ui.util.DialogUtils;
import com.example.ntb.ui.util.EasyPermissions;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.LoadUtils;
import com.example.ntb.ui.util.PermissionsUtils;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.ScreenEvent;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.ntb.ui.util.SPUtils.getSharedStringData;

/**
 * Created by ccy.
 * Date: 2021/10/21
 * Describe :地图模式
 */
public class AMapFragment extends BaseFragment implements LocationSource,ClusterRender, AMap.OnMapLoadedListener, ClusterClickListener, AMap.OnMapClickListener,
        AMapLocationListener ,View.OnClickListener,BaseView ,EasyPermissions.PermissionCallback{

    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.fl_near_pop)
    FrameLayout mFlNearPop;//底线

    @BindView(R.id.cb_jiaotongdeng)
    CheckBox cb_jiaotongdeng;//交通灯

    @BindView(R.id.cb_position)
    CheckBox cb_position;//定位

    @BindView(R.id.ll_refresh_amap)
    LinearLayout ll_refresh_amap;

    private AMap mAMap;
    private int clusterRadius = 30;  //多少像素内的点进行聚合
    private ClusterOverlay mClusterOverlay; //聚合工具
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();
    private double latitude = -1; //获取纬度
    private double longitude = -1; //获取经度
    private boolean traffic = true; //交通灯
    private boolean position = false;//定位
    private List<SiteInfo> infoList;
    private List<SiteInfo> mOriginalList = new ArrayList<>();//站点数据
    private BasePresenter basePresenter = new BasePresenter(getContext(),this);

    private String isPerms;//是否获取到权限
    private final int RC_CAMERA = 666;
    private Dialog dialog;
    private DialogUtils dialogUtils = new DialogUtils();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void getNetworkRequest() {
        showGPSContacts();
    }

    private void getStationOnMap() {

        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.cementTruckOnMap;//地图模式获取所有站点

//        String Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/stationOnMap";

        JSONObject obj = new JSONObject();
        try {
            obj.put("sellerNo",RequestURL.sellerNo+"");
            obj.put("cementTruck",true);//是否为泥头宝
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String token = SPUtils.getSharedStringData(getContext(),"token");
        Utils.out("tokentokentokentoken",token);
        basePresenter.jsonRequesttoHead(getActivity(),Url,false,obj.toString(),token,1);
    }
    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    private void showGPSContacts() {
        //定位的权限
        if (PermissionsUtils.from(getActivity()).setPermissions(PermissionsUtils.permissionsPositioning)){
            Log.d("定位权限","开启了");
            initAMap();//有权限，进行相应的处理
            getStationOnMap();
        }else {
            SharedPreferences userInfo = getActivity().getSharedPreferences("isPerms", MODE_PRIVATE);
            isPerms = userInfo.getString("isPerms", null);
            if (!TextUtils.isEmpty(isPerms)) {
                permsission();
            } else {
                String[] scanPerms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                EasyPermissions.requestPermissions(this, "为了你的正常使用,应用必须获取定位权限",RC_CAMERA, scanPerms);
            }
        }
    }


    @OnClick({R.id.ll_refresh_amap})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_refresh_amap:
                LoadUtils.showWaitProgress(getContext(),"");
                getStationOnMap();
                break;
        }
    }
    /**
     * 初始化地图
     */
    private void initAMap() {

        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_me);//自定义蓝点图标
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationIcon(descriptor);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.strokeColor(getResources().getColor(R.color.colorPrimary));// 设置圆形的边框颜色
        mMapView.getMap().setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mMapView.getMap().setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        if (mAMap == null) {
            // 初始化地图
            mAMap = mMapView.getMap();
            mAMap.setOnMapLoadedListener(this);
            mAMap.setOnMapClickListener(this);
            // 设置定位监听
            mAMap.setLocationSource(this);
            // 设置默认定位按钮是否显示
            mAMap.getUiSettings().setMyLocationButtonEnabled(false);
            //去掉高德地图右下角隐藏的缩放按钮
            mAMap.getUiSettings().setZoomControlsEnabled(true);
//            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            mAMap.setMyLocationEnabled(true);
            // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
            mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
            // 是否显示比例尺
            mAMap.getUiSettings().setScaleControlsEnabled(true);
        }
    }

    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                Log.d("定位成功","维度："+aMapLocation.getLatitude()+"===经度："+aMapLocation.getLongitude()+"===城市："+aMapLocation.getCity());
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                // 定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
                latitude = aMapLocation.getLatitude();// 获取纬度
                longitude = aMapLocation.getLongitude();// 获取经度
                aMapLocation.getAccuracy();// 获取精度信息
                mAMap.showBuildings(true);
                NaviLatLng  userLocation = new NaviLatLng(latitude, longitude);
                App.getInstance().whereLocation = userLocation;
                App.getInstance().whereCityName = aMapLocation.getCity();
                SPUtils.setSharedStringData(getContext(),"latitude",latitude+"");
                SPUtils.setSharedStringData(getContext(),"longitude",longitude+"");
                EventBus.getDefault().post(new EventBlack(2));//定位成功
            } else {
                Log.d("定位失败",aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo());
                ToastUtil.showToast(getContext(),aMapLocation.getErrorCode()+"");
            }
        }
    }
    /**
     * 激活定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }
    /**
     * 开始定位
     */
    private void startLocation() {

        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode
                    .Hight_Accuracy);
            mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            // 设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            // 设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
            // 设置是否强制刷新WIFI，默认为强制刷新
            mLocationOption.setWifiActiveScan(true);
            // 设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);
            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            mLocationOption.setInterval(10 * 100000);
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mClusterOverlay != null) {
            mClusterOverlay.onDestroy();
        }
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mClusterOverlay != null) {
            mClusterOverlay.onDestroy();
        }
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public Drawable getDrawAble(int clusterNum) {
        int radius = Utils.dp2px(getActivity(), 80);
        if (clusterNum == 1) {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            if (bitmapDrawable == null) {
                bitmapDrawable = getActivity().getResources().getDrawable(R.mipmap.yuan);
                mBackDrawAbles.put(1, bitmapDrawable);
            }
            return bitmapDrawable;
        } else {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            if (bitmapDrawable == null) {
//                bitmapDrawable = getActivity().getResources().getDrawable(R.mipmap.map_jl);
                bitmapDrawable = getActivity().getResources().getDrawable(R.mipmap.map_custom_three_icon);//yuan_five
                mBackDrawAbles.put(1, bitmapDrawable);
            }
            return bitmapDrawable;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    SiteInfo mapInfo;
    /**
     * 聚合点的点击事件
     * @param marker       点击的聚合点
     * @param clusterItems
     */
    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
        if (clusterItems.size() == 1) {
            ClusterItem clusterItem = clusterItems.get(0);
            String id = clusterItem.getID();
            LatLng position = clusterItem.getPosition();
            if (infoList != null) {
                for (SiteInfo info : infoList) {
                    if (info.id.equals(id)) {
                        mapInfo = null;
                        mapInfo = info;
                        jumpPoint(marker, position);
                    }
                }
            }
        } else { //放大地图
//            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.new_amap_sel_icon));//map_icon_two
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (ClusterItem clusterItem : clusterItems) {
                builder.include(clusterItem.getPosition());
            }
            LatLngBounds latLngBounds = builder.build();
            mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0)
            );
        }
    }

    /**
     * marker点击时跳动一下
     * @param latLng
     */
    public void jumpPoint(final Marker marker, final LatLng latLng) {
//        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.new_amap_sel_bg));//map_icon_three
        marker.setPosition(latLng);
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mAMap.getProjection();
        Point startPoint = proj.toScreenLocation(latLng);
        startPoint.offset(0, -50);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * latLng.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * latLng.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void resultSucess(final int type, final String json) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (!TextUtils.isEmpty(json)){
                        if (json != null) {
                            Gson mGson = new Gson();
                            final JsonAllAmap jsonAllAmap = mGson.fromJson(json, new TypeToken<JsonAllAmap>() {
                            }.getType());
                            if (jsonAllAmap.getCode() == 0){
                                mOriginalList.clear();
                                for (SiteInfo item : jsonAllAmap.data){
                                    mOriginalList.add(item);
                                }
                                EventBus.getDefault().post(new ScreenEvent(mOriginalList));
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void resultFailure(final int type, final String Msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                }
            }
        });
    }

    //筛选后的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScreenEvent(ScreenEvent event) {
        infoList = event.getInfoList();
        Utils.out("地图数据", infoList.toString());
        List<ClusterItem> items = new ArrayList<ClusterItem>();
        if (infoList != null && infoList.size() > 0) {
            for (int i = 0; i < infoList.size(); i++) {
                SiteInfo siteInfo = infoList.get(i);
                String xstr = siteInfo.latitude == null ? "0" : siteInfo.latitude;
                String ystr = siteInfo.longitude == null ? "0" : siteInfo.longitude;
                double lat = Double.valueOf(xstr);
                double lon = Double.valueOf(ystr);
                LatLng latLng = new LatLng(lat, lon, false);
                siteInfo.mLatLng = latLng;
                items.add(siteInfo);
            }
            if (mClusterOverlay == null) {
                mClusterOverlay = new ClusterOverlay(mAMap, items, Utils.dp2px(getActivity(), clusterRadius), getActivity());
                mClusterOverlay.setClusterRenderer(AMapFragment.this);
                mClusterOverlay.setOnClusterClickListener(AMapFragment.this);
            } else {
                mClusterOverlay.setClusterItems(items);
            }
        } else {
            if (mClusterOverlay == null) {
                mClusterOverlay = new ClusterOverlay(mAMap, items, Utils.dp2px(getActivity(), clusterRadius), getActivity());
                mClusterOverlay.setClusterRenderer(AMapFragment.this);
                mClusterOverlay.setOnClusterClickListener(AMapFragment.this);
            } else {
                mClusterOverlay.setClusterItems(items);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //下面两个方法是实现EasyPermissions的EasyPermissions.PermissionCallbacks接口
    //分别返回授权成功和失败的权限
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Utils.out("权限", perms + "");
        for (String str : perms) {
            if (str.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                initAMap();//有权限，进行相应的处理
                getStationOnMap();
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            permsission();
        } else {
            for (String str : perms) {
                if (str.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    permsission();
                }
            }
        }
    }

    /**
     * 前往该应用设置权限
     */
    private void permsission() {

        if (dialog != null) {
            dialog.dismiss();
        }

        if (TextUtils.isEmpty(isPerms)) {
            return;
        }

        dialog = dialogUtils.showDialog(getActivity(), "必需权限", "此应用程序必须获取存储、定位权限，否则可能无法正常工作。请打开应用设置界面以修改应用权限", "去设置", "取消");
        dialogUtils.setListener(new DialogUtils.OnDialogClickListener() {
            @Override
            public void onSave() {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package",getActivity().getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName",getActivity().getPackageName());
                }
                startActivity(localIntent);
            }

            @Override
            public void onCancel() {
                getActivity().finish();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Subscribe
    public void EventBlack(EventBlack event) {
        if (event.getCode() == 0){
            initAMap();//有权限，进行相应的处理
            getStationOnMap();
        }else if (event.getCode() == 1){

        }else if (event.getCode() == 200){
            Utils.out("AMap","地图页收到登录成功的通知");
            getStationOnMap();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityEvent(CityEvent event) {
//        DaoSession daoSession = App.getInstance().daoSession;
//        List<SelectedCityInfo> selectedCityInfos = daoSession.getSelectedCityInfoDao().loadAll();
//        if (selectedCityInfos.size() > 0) {
//            for (SelectedCityInfo cityInfo : selectedCityInfos) {
//                //设置经纬度
//                aMapLocation.setLatitude(Double.parseDouble(cityInfo.getX()));
//                aMapLocation.setLongitude(Double.parseDouble(cityInfo.getY()));
//                //设置精度，这个值会被设置为定位点上表示精度的圆形半径
//                aMapLocation.setAccuracy(aMapLocation.getAccuracy());
//                //设置定位标的旋转角度，注意 tencentLocation.getBearing() 只有在 gps 时才有可能获取
//                aMapLocation.setBearing((float) aMapLocation.getBearing());
//                //将位置信息返回给地图
//                mListener.onLocationChanged(aMapLocation);
//            }
//        }
    }
}
