package com.example.ntb.ui.vehiclemanagement.acticity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.home.bean.JsonBase;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.TitleBarLayout;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.Utils;
import com.example.ntb.ui.vehiclemanagement.adapter.VehicleManagementAdapter;
import com.example.ntb.ui.vehiclemanagement.bean.JsonVehicle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :车辆列表
 */
public class VehicleManagementActivity extends BaseActivity implements BaseView {

    @BindView(R.id.titleBar)
    TitleBarLayout titleBar;

    @BindView(R.id.lv_vehicleManagement)
    ListView lv_vehicleManagement;

    @BindView(R.id.ll_show)
    LinearLayout ll_show;

    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private BasePresenter basePresenter = new BasePresenter(this,this);

    private VehicleManagementAdapter vehicleManagementAdapter;
    private List<JsonVehicle.DataBean> list = new ArrayList<>();

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vehicle_management;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        titleBar.setTitle("车辆列表");
        titleBar.setNoticeDrawable(R.mipmap.add_car);
        titleBar.setNoticeVisibility(View.VISIBLE);

        vehicleManagementAdapter = new VehicleManagementAdapter(this,list,onDeleteItemListener);
        lv_vehicleManagement.setAdapter(vehicleManagementAdapter);

        mRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData();
            }
        });
    }
    @Override
    protected void getNetworkRequest() {
        getData();
    }

    private void getData(){
//        String Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/addMember";
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getMemberVehicle;// 查询车辆列表
        Map<String,Object> map = new HashMap<>();
        basePresenter.postRequesttoHead(this,Url,false,map,SPUtils.getSharedStringData(this,"token"),1);
    }

    @OnClick({R.id.iv_back,R.id.iv_notice})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_notice:
                startActivity(new Intent(VehicleManagementActivity.this,AddCarActivity.class));//添加车辆
                break;
        }
    }
    /**
     * 删除车辆的回调
     */
    VehicleManagementAdapter.OnDeleteItemListener onDeleteItemListener = new VehicleManagementAdapter.OnDeleteItemListener() {
        @Override
        public void onDeleteListener(int positon, List<JsonVehicle.DataBean> list) {
            String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.delVehicle;// 删除车辆
            Map<String,Object> map = new HashMap<>();
            map.put("id",list.get(positon).id);
            basePresenter.postRequesttoHead(VehicleManagementActivity.this,Url,false,map,SPUtils.getSharedStringData(VehicleManagementActivity.this,"token"),2);
        }
    };
    @Override
    public void resultSucess(final int type, final String json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (!TextUtils.isEmpty(json)){
                        Gson gson = new Gson();
                        JsonVehicle jsonVehicle = gson.fromJson(json.trim(),new TypeToken<JsonVehicle>(){}.getType());{
                            if (jsonVehicle.code == 0){
                                if (jsonVehicle.data.size() != 0){
                                    ll_show.setVisibility(View.GONE);
                                    lv_vehicleManagement.setVisibility(View.VISIBLE);
                                    list.clear();
                                    for (JsonVehicle.DataBean item : jsonVehicle.data){
                                        list.add(item);
                                    }
                                    vehicleManagementAdapter.notifyDataSetChanged();
                                }else {
                                    ll_show.setVisibility(View.VISIBLE);
                                    lv_vehicleManagement.setVisibility(View.GONE);
                                }
                            }else {
                                ToastUtil.showToast(VehicleManagementActivity.this,jsonVehicle.msg+"");
                            }
                        }
                    }
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                }else if (type == 2){
                    if (!TextUtils.isEmpty(json)){
                        Gson gson = new Gson();
                        JsonBase jsonBase = gson.fromJson(json.trim(),new TypeToken<JsonBase>(){}.getType());{
                            if (jsonBase.code == 0){
                                showShortToast(jsonBase.msg+"");
                                getData();
                            }else {
                                showShortToast(jsonBase.msg+"");
                            }
                        }
                    }
                }
            }
        });
    }
    @Override
    public void resultFailure(int type, final String Msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShortToast(Msg+"");
                if (mRefreshLayout != null) {
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadMore();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Subscribe
    public void EventBlacks(EventBlack event) {
        if (event.getCode() == 3){
            Utils.out("VehicleManamgement","收到添加车辆成功的通知");
            getData();
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
