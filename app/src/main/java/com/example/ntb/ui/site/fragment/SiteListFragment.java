package com.example.ntb.ui.site.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.api.navi.model.NaviLatLng;
import com.example.ntb.app.App;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.activity.CityActivity;
import com.example.ntb.ui.city.bean.Areas;
import com.example.ntb.ui.city.bean.CityEvent;
import com.example.ntb.ui.city.bean.SelectedCityInfo;
import com.example.ntb.ui.db.DaoSession;
import com.example.ntb.ui.site.activity.GPSNaviActivity;
import com.example.ntb.ui.site.adapter.SiteListAdapter;
import com.example.ntb.ui.site.bean.JsonCityId;
import com.example.ntb.ui.site.bean.JsonSite;
import com.example.ntb.ui.util.DialogUtils;
import com.example.ntb.ui.util.EasyPermissions;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ccy.
 * Date: 2021/10/20
 * Describe :??????
 */
public class SiteListFragment extends Fragment implements BaseView ,View.OnClickListener ,EasyPermissions.PermissionCallback{


    private ListView lv_site;
    private LinearLayout ll_site,ll_switch_map,ll_fragment,ll_filter;
    private SmartRefreshLayout mRefreshLayout;
    private ImageView iv_switch_map;
    private RelativeLayout rl_stie;
    private TextView tv_city;

    private boolean flag = false;//????????????????????????

    private BasePresenter basePresenter = new BasePresenter(getActivity(),this);
    private SiteListAdapter siteListAdapter;
    private List<JsonSite.DataBeanX.DataBean> list = new ArrayList<>();

    private String isPerms;//?????????????????????
    private final int RC_CAMERA = 666;
    private Dialog dialog;
    private DialogUtils dialogUtils = new DialogUtils();
    private int index = 1;
    private int areaId;//??????id
    private String cityName = "?????????";//????????????(???????????????)
    private String Url,Token;
    private int tag = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_site, null);
        initView(view);
        getCityId();
        return  view;
    }

    private void getCityId() {
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getStationArea;// ?????????????????????id
        Map<String, Object> map = new HashMap<>();
        map.put("sellerId",RequestURL.sellerId);
        basePresenter.postRequesttoHead(getContext(),Url,false,map,SPUtils.getSharedStringData(getContext(),"token")+"",1);
    }

    private void initView(View view) {

        EventBus.getDefault().register(this);
        lv_site =(ListView) view.findViewById(R.id.lv_site);
        ll_site  =(LinearLayout) view.findViewById(R.id.ll_stie);
        ll_switch_map =(LinearLayout) view.findViewById(R.id.ll_switch_map);
        ll_switch_map.setOnClickListener(this);
        iv_switch_map =(ImageView) view.findViewById(R.id.iv_switch_map);
        ll_fragment =(LinearLayout) view.findViewById(R.id.ll_fragment);
        rl_stie =(RelativeLayout) view.findViewById(R.id.rl_stie);
        tv_city =(TextView) view.findViewById(R.id.tv_city);
        LinearLayout ll_city =(LinearLayout) view.findViewById(R.id.ll_city);
        ll_city.setOnClickListener(this);
        ll_filter = view.findViewById(R.id.ll_filter);


        DaoSession daoSession = App.getInstance().daoSession;
        List<SelectedCityInfo> selectedCityInfos = daoSession.getSelectedCityInfoDao().loadAll();
        if (selectedCityInfos.size() > 0) {
            for (SelectedCityInfo cityInfo : selectedCityInfos) {
                String name = cityInfo.getName();
                cityName = name;
                tv_city.setText(cityName);
            }
        }

        mRefreshLayout =(SmartRefreshLayout) view.findViewById(R.id.mSmartRefreshLayout);
        siteListAdapter = new SiteListAdapter(getActivity(),list,onItemListener);
        lv_site.setAdapter(siteListAdapter);

        iv_switch_map.setBackgroundResource(R.drawable.selector_map_no);
        getChildFragmentManager().beginTransaction().replace(R.id.site_frame, new AMapFragment()).commit();//????????????

        mRefreshLayout.setEnableLoadMore(false);//??????????????????????????????
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                index = 1;
                getCityId();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                index = index + 1;
                getCityId();
            }
        });
    }

    @Override
    public void resultSucess(final int type, final String json) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    try {
                        if (!TextUtils.isEmpty(json)){
                            Gson mGson = new Gson();
                            JsonCityId jsonCityId = mGson.fromJson(json.trim().toString(), new TypeToken<JsonCityId>() {
                            }.getType());
                            if (jsonCityId.getCode() == 0){
                                for (JsonCityId.DataBean item : jsonCityId.getData()){
                                    Utils.out("???????????????",cityName+"-----"+"??????????????????===="+item.getCityName()+"");
                                    if (!TextUtils.isEmpty(cityName)){
                                        if (item.getCityName().equals(cityName)){
                                            areaId = item.getCityId();
                                            getsiteList(index);//?????????????????????
                                        }else if (cityName.equals("??????")){
                                            areaId = 1;
                                            getsiteList(index);
                                        }else {
                                            ll_site.setVisibility(View.VISIBLE);
                                            lv_site.setVisibility(View.GONE);
                                        }
                                    }else {
                                        areaId = 100293;//???????????????
                                        getsiteList(index);
                                    }
                                }
                            }else {
                                areaId = 100293;//???????????????
                                getsiteList(index);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                }else if (type == 2){
                    Gson gson = new Gson();
                    JsonSite jsonPersonalComment = gson.fromJson(json.trim(),new TypeToken<JsonSite>(){}.getType());
                    if (jsonPersonalComment.code == 0){
                        if (jsonPersonalComment.data.data.size() != 0){
                            if (index == 1){
                                list.clear();
                            }
                            if (jsonPersonalComment.data.pages == index){
                                mRefreshLayout.setEnableLoadMore(false);
                            }else {
                                mRefreshLayout.setEnableLoadMore(true);
                            }
                            for (JsonSite.DataBeanX.DataBean item : jsonPersonalComment.data.data){
                                list.add(item);
                            }
                            siteListAdapter.notifyDataSetChanged();
                            ll_site.setVisibility(View.GONE);
                            lv_site.setVisibility(View.VISIBLE);
                        }else {
                            ll_site.setVisibility(View.VISIBLE);
                            lv_site.setVisibility(View.GONE);
                        }
                    }
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
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
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                        mRefreshLayout.finishRefresh(false);
                    }
                    ToastUtil.showToast(getActivity(),Msg+"");
                }else if (type == 2){
                    ll_site.setVisibility(View.VISIBLE);
                    lv_site.setVisibility(View.GONE);
                }
            }
        });
    }

    private double cityLongitude,cityLlatitude;
    /**
     * ??????????????????
     * @param index
     */
    private void getsiteList(int index) {
        Token = SPUtils.getSharedStringData(getContext(),"token");
//        Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/getSite";
        if (TextUtils.isEmpty(Token)){
            Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.pageStationWithoutLogin;//????????????????????????????????????
            Token = "";
        }else {
            Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.pageStation;//??????????????????????????????
            Token = SPUtils.getSharedStringData(getContext(),"token");
        }
        JSONObject obj = new JSONObject();
        try {
            if (App.getInstance().whereLocation != null){
                cityLongitude = App.getInstance().whereLocation.getLongitude();
                cityLlatitude = App.getInstance().whereLocation.getLatitude();
            }
            obj.put("current",index);//?????????
            obj.put("sellerNo",RequestURL.sellerNo);
            obj.put("latitude", cityLlatitude+"");//??????
            obj.put("longitude", cityLongitude+"");//??????
            obj.put("areaId",areaId+"");//??????id
            obj.put("sortMode",1);//0:??????;1:??????;2:??????;3:??????
            obj.put("cementTruck",true);//??????????????????
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePresenter.jsonRequesttoHead(getContext(),Url,false,obj.toString(),Token+"",2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_switch_map:
                if (flag == true){
                    iv_switch_map.setBackgroundResource(R.drawable.selector_map_yes);
                    flag = false;
                    ll_fragment.setVisibility(View.VISIBLE);
                    rl_stie.setVisibility(View.GONE);
                    mRefreshLayout.setEnableRefresh(false);
                    ll_filter.setVisibility(View.GONE);
                }else {
                    iv_switch_map.setBackgroundResource(R.drawable.selector_map_no);
                    flag = true;
                    ll_fragment.setVisibility(View.GONE);
                    rl_stie.setVisibility(View.VISIBLE);
                    mRefreshLayout.setEnableRefresh(true);
                    ll_filter.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_city://????????????
                Intent intent = new Intent(getActivity(),CityActivity.class);
                startActivity(intent);
                break;
        }
    }
    SiteListAdapter.OnItemListener onItemListener = new SiteListAdapter.OnItemListener() {
        @Override
        public void onNavigationListener(int position, List<JsonSite.DataBeanX.DataBean> data, int type) {
            if (type == 1){
                judgeCautions(data.get(position).latitude,data.get(position).longitude);
            }
        }
    };
    private void judgeCautions(String latitude, String longitude) {
        NaviLatLng userLocation = App.getInstance().whereLocation;
        if (userLocation != null){
            Intent intent = new Intent(getActivity(), GPSNaviActivity.class);
            intent.putExtra("startLa",userLocation.getLatitude());
            intent.putExtra("startLo",userLocation.getLongitude());
            intent.putExtra("endLa", Double.valueOf(latitude));
            intent.putExtra("endLo", Double.valueOf(longitude));
            startActivity(intent);

        }else {
            showGPSContacts();
        }
    }

    private void showGPSContacts() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("isPerms", MODE_PRIVATE);
        isPerms = userInfo.getString("isPerms", null);
        if (!TextUtils.isEmpty(isPerms)) {
            permsission();
        } else {
            String[] scanPerms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            EasyPermissions.requestPermissions(this, "????????????????????????,??????????????????????????????",RC_CAMERA, scanPerms);
        }
    }

    /**
     * ???????????????????????????
     */
    private void permsission() {

        if (dialog != null) {
            dialog.dismiss();
        }

        if (TextUtils.isEmpty(isPerms)) {
            return;
        }

        dialog = dialogUtils.showDialog(getActivity(), "????????????", "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", "?????????", "??????");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //???????????????????????????EasyPermissions???EasyPermissions.PermissionCallbacks??????
    //??????????????????????????????????????????
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Utils.out("??????", perms + "");
        for (String str : perms) {
            if (str.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                //?????????????????????????????????
                EventBus.getDefault().post(new EventBlack(0));//??????????????????????????????????????????????????????
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
    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityEvent(CityEvent event) {
        Areas info = event.getInfo();
        cityName = info.name;
        tv_city.setText(info.name);
        getCityId();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Subscribe
    public void EventBlacks(EventBlack event) {
        if (event.getCode() == 200){//????????????
            getCityId();
            Utils.out("SiteList","????????????????????????????????????");
        }else if (event.getCode() == 1){//????????????
            getCityId();
            Utils.out("SiteList","????????????????????????????????????");
        }else if (event.getCode() == 2){//???????????????
            tag++;
            if (tag<=1){
                Utils.out("SiteList","???????????????");
                getCityId();
            }
        }
    }
}
