//package com.example.ntb.ui.site.fragment;
//
//import android.graphics.Point;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.SystemClock;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.BounceInterpolator;
//import android.view.animation.Interpolator;
//
//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationClient;
//import com.amap.api.location.AMapLocationClientOption;
//import com.amap.api.location.AMapLocationListener;
//import com.amap.api.maps.AMap;
//import com.amap.api.maps.CameraUpdateFactory;
//import com.amap.api.maps.LocationSource;
//import com.amap.api.maps.MapView;
//import com.amap.api.maps.Projection;
//import com.amap.api.maps.model.BitmapDescriptor;
//import com.amap.api.maps.model.BitmapDescriptorFactory;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.LatLngBounds;
//import com.amap.api.maps.model.Marker;
//import com.amap.api.maps.model.MyLocationStyle;
//import com.example.ntb.app.App;
//import com.example.ntb.base.BaseFragment;
//import com.example.ntb.mvp.presenter.BasePresenter;
//import com.example.ntb.mvp.view.BaseView;
//import com.example.ntb.ui.R;
//import com.example.ntb.ui.site.bean.JsonAllAmap;
//import com.example.ntb.ui.site.bean.SiteInfo;
//import com.example.ntb.ui.util.ClusterClickListener;
//import com.example.ntb.ui.util.ClusterItem;
//import com.example.ntb.ui.util.ClusterOverlay;
//import com.example.ntb.ui.util.ClusterRender;
//import com.example.ntb.ui.util.ScreenEvent;
//import com.example.ntb.ui.util.Utils;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import butterknife.BindView;
//
///**
// * Created by ccy.
// * Date: 2021/10/22
// * Describe :地图模式
// */
//public class AMapTwoFragment extends BaseFragment implements LocationSource, ClusterRender, AMap.OnMapLoadedListener, ClusterClickListener, AMap.OnMapClickListener,
//        AMapLocationListener ,View.OnClickListener,BaseView  {
//
//    @BindView(R.id.map)
//    MapView mMapView;
//
//    //定位需要的声明
//    private AMapLocationClient mLocationClient = null;//定位发起端
//    private AMapLocationClientOption mLocationOption = null;//定位参数
//    private OnLocationChangedListener mListener = null;//定位监听器
//    private AMap mAMap;
//
//
//    //多少像素内的点进行聚合
//    private int clusterRadius = 30;
//
//    //聚合工具
//    private ClusterOverlay mClusterOverlay;
//    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();
//    private List<SiteInfo> infoList;
//
//    private List<SiteInfo> mOriginalList = new ArrayList<>();
//
//    private BasePresenter basePresenter = new BasePresenter(getActivity(),this);
//
//    @Override
//    protected int getLayoutResource() {
//        return R.layout.fragment_maptwo;
//    }
//
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//        super.initView(savedInstanceState);
//        mMapView.onCreate(savedInstanceState);
//    }
//
//    @Override
//    protected void getNetworkRequest() {
//        getStationOnMap();
//    }
//
//    private void getStationOnMap() {
//        String Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/stationOnMap";//地图模式获取所有站点
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("sellerNo","nz");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        basePresenter.jsonRequesttoHead(getActivity(),Url,false,obj.toString(),"",1);
//    }
//
//    @Override
//    protected void initData() {
//        EventBus.getDefault().register(this);
//        initLocationStyle();//显示定位蓝点图标
//    }
//    /**
//     * 显示定位蓝点图标
//     */
//    private void initLocationStyle() {
//        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_me);//自定义蓝点图标
//        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.myLocationIcon(descriptor);
////        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
////        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
////        myLocationStyle.strokeColor(getResources().getColor(R.color.colorPrimary));// 设置圆形的边框颜色
////        myLocationStyle.radiusFillColor(getResources().getColor(R.color.colorAccent));// 设置圆形的填充颜色
//        mMapView.getMap().setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
////        mMapView.getMap().getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
//        mMapView.getMap().setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        //设置希望展示的地图缩放级别。 缩放等级为1-19级
//        mMapView.getMap().moveCamera(CameraUpdateFactory.zoomTo(16));
//        mAMap = mMapView.getMap();
//        //开始定位
//        initLoc();
//    }
//
//    private void initLoc() {
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getActivity());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(this);
//        //初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //设置是否只定位一次,默认为false
//        mLocationOption.setOnceLocation(false);
//        //设置是否强制刷新WIFI，默认为强制刷新
//        mLocationOption.setWifiActiveScan(true);
//        //设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();
//    }
//
//    @Override
//    public void onClick(View view) {
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mMapView.onDestroy();//销毁地图
//        EventBus.getDefault().unregister(this);
//        if (mClusterOverlay != null) {
//            mClusterOverlay.onDestroy();
//        }
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();//重新绘制加载地图
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        mMapView.onPause();//暂停地图的绘制
//    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mMapView.onSaveInstanceState(outState);//保存地图当前的状态
//    }
//
//    @Override
//    public void onLocationChanged(AMapLocation amapLocation) {
//        if (amapLocation != null) {
//            if (amapLocation.getErrorCode() == 0) {
//                //定位成功回调信息，设置相关消息
//                Log.e("定位成功",amapLocation.getErrorCode()+"==="+amapLocation.getCity()+"===="+amapLocation.getLatitude()+"===="+amapLocation.getLongitude()+"");
//            } else {
//                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                Log.e("定位失败", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
//            }
//        }
//    }
//    //激活定位
//    @Override
//    public void activate(OnLocationChangedListener onLocationChangedListener) {
//        mListener = onLocationChangedListener;
//    }
//    //停止定位
//    @Override
//    public void deactivate() {
//        mListener = null;
//    }
//
//    @Override
//    public void resultSucess(final int type, final String json) {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (type == 1){
//                    if (!TextUtils.isEmpty(json)){
//                        if (json != null) {
//                            Gson mGson = new Gson();
//                            final JsonAllAmap jsonAllAmap = mGson.fromJson(json, new TypeToken<JsonAllAmap>() {
//                            }.getType());
//                            if (jsonAllAmap.getCode() == 0){
////                                tv_Retry.setVisibility(View.GONE);
//                                for (SiteInfo item : jsonAllAmap.data){
//                                    mOriginalList.add(item);
//                                }
//                                EventBus.getDefault().post(new ScreenEvent(mOriginalList));
//                            }else {
////                                tv_Retry.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public void resultFailure(int type, String Msg) {
//
//    }
//
//    @Override
//    public void onMapClick(LatLng latLng) {
//
//    }
//
//    @Override
//    public void onMapLoaded() {
//
//    }
//
//    SiteInfo mapInfo;
//    /**
//     * 聚合点的点击事件
//     *
//     * @param marker       点击的聚合点
//     * @param clusterItems
//     */
//    @Override
//    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
//        if (clusterItems.size() == 1) {
//            ClusterItem clusterItem = clusterItems.get(0);
//            String id = clusterItem.getID();
//            LatLng position = clusterItem.getPosition();
//            if (infoList != null) {
//                for (SiteInfo info : infoList) {
//                    if (info.id.equals(id)) {
//                        mapInfo = null;
//                        mapInfo = info;
////                        showPop(marker,info.id,info.latitude,info.longitude);
//                        jumpPoint(marker, position);
//                    }
//                }
//            }
//
//        } else { //放大地图
//            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.new_amap_sel_icon));//map_icon_two
//            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//            for (ClusterItem clusterItem : clusterItems) {
//                builder.include(clusterItem.getPosition());
//            }
//            LatLngBounds latLngBounds = builder.build();
//            mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0)
//            );
//        }
//    }
//
//
//    /**
//     * marker点击时跳动一下
//     *
//     * @param latLng
//     */
//    public void jumpPoint(final Marker marker, final LatLng latLng) {
//        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.new_amap_sel_bg));//map_icon_three
//        marker.setPosition(latLng);
//        final Handler handler = new Handler();
//        final long start = SystemClock.uptimeMillis();
//        Projection proj = mAMap.getProjection();
//        Point startPoint = proj.toScreenLocation(latLng);
//        startPoint.offset(0, -50);
//        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
//        final long duration = 1500;
//
//        final Interpolator interpolator = new BounceInterpolator();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                long elapsed = SystemClock.uptimeMillis() - start;
//                float t = interpolator.getInterpolation((float) elapsed / duration);
//                double lng = t * latLng.longitude + (1 - t) * startLatLng.longitude;
//                double lat = t * latLng.latitude + (1 - t) * startLatLng.latitude;
//                marker.setPosition(new LatLng(lat, lng));
//                if (t < 1.0) {
//                    handler.postDelayed(this, 16);
//                }
//            }
//        });
//    }
//
//    @Override
//    public Drawable getDrawAble(int clusterNum) {
//        int radius = Utils.dp2px(getActivity(), 80);
//        if (clusterNum == 1) {
//            Drawable bitmapDrawable = mBackDrawAbles.get(1);
//            if (bitmapDrawable == null) {
//                bitmapDrawable = getActivity().getResources().getDrawable(R.mipmap.yuan);
//                mBackDrawAbles.put(1, bitmapDrawable);
//            }
//            return bitmapDrawable;
//        } else {
//            Drawable bitmapDrawable = mBackDrawAbles.get(1);
//            if (bitmapDrawable == null) {
////                bitmapDrawable = getActivity().getResources().getDrawable(R.mipmap.map_jl);
//                bitmapDrawable = getActivity().getResources().getDrawable(R.mipmap.yuan_five);//map_custom_three_icon
//                mBackDrawAbles.put(1, bitmapDrawable);
//            }
//            return bitmapDrawable;
//        }
//    }
//    //筛选后的数据
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onScreenEvent(ScreenEvent event) {
//        infoList = event.getInfoList();
//        Utils.out("地图数据", infoList.toString());
//        List<ClusterItem> items = new ArrayList<ClusterItem>();
//        if (infoList != null && infoList.size() > 0) {
//            for (int i = 0; i < infoList.size(); i++) {
//                SiteInfo siteInfo = infoList.get(i);
//                String xstr = siteInfo.latitude == null ? "0" : siteInfo.latitude;
//                String ystr = siteInfo.longitude == null ? "0" : siteInfo.longitude;
//                double lat = Double.valueOf(xstr);
//                double lon = Double.valueOf(ystr);
//                LatLng latLng = new LatLng(lat, lon, false);
//                siteInfo.mLatLng = latLng;
//                items.add(siteInfo);
//                if (App.getInstance().isFirstLoc) {
//                    mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
//                    App.getInstance().isFirstLoc = false;
//                }
//            }
//            if (mClusterOverlay == null) {
//                mClusterOverlay = new ClusterOverlay(mAMap, items, Utils.dp2px(getActivity(), clusterRadius), getActivity());
//                mClusterOverlay.setClusterRenderer(AMapTwoFragment.this);
//                mClusterOverlay.setOnClusterClickListener(AMapTwoFragment.this);
//            } else {
//                mClusterOverlay.setClusterItems(items);
//            }
//        } else {
//            if (mClusterOverlay == null) {
//                mClusterOverlay = new ClusterOverlay(mAMap, items, Utils.dp2px(getActivity(), clusterRadius), getActivity());
//
//                mClusterOverlay.setClusterRenderer(AMapTwoFragment.this);
//                mClusterOverlay.setOnClusterClickListener(AMapTwoFragment.this);
//            } else {
//                mClusterOverlay.setClusterItems(items);
//            }
//        }
//    }
//}
