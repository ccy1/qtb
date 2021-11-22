package com.example.ntb.ui.site.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.ui.R;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.Utils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :导航
 */
public class GPSNaviActivity extends BaseActivity implements AMapNaviListener, AMapNaviViewListener {

    @BindView(R.id.navi_view)
    AMapNaviView mAMapNaviView;

    protected AMapNavi mAMapNavi;

    private Intent mIntent;
    private List<NaviLatLng> mStartList = new ArrayList<>();
    private List<NaviLatLng> mEndList = new ArrayList<>();
    private NaviLatLng mEndLatlng = null;
    private NaviLatLng mStartLatlng = null;
    private List<NaviLatLng> mWayPointList;

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gpsnavi;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAMapNaviView.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        setStatusBar();
        mIntent = getIntent();
        double startLa = mIntent.getDoubleExtra("startLa", 0);
        double startLo = mIntent.getDoubleExtra("startLo", 0);
        double endLa = mIntent.getDoubleExtra("endLa", 0);
        double endLo = mIntent.getDoubleExtra("endLo", 0);

        Log.d("startLa",startLa+"startLo"+startLo+"endLa"+endLa+"endLo"+endLo);

        mStartLatlng = new NaviLatLng(startLa, startLo);
        mEndLatlng = new NaviLatLng(endLa, endLo);
        mAMapNaviView.setAMapNaviViewListener(this);
        setAmapNaviViewOptions();
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.setUseInnerVoice(true);//语音播报
        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
    }

    private void setAmapNaviViewOptions() {
        if (mAMapNaviView == null) {
            return;
        }
        AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
        viewOptions.setSettingMenuEnabled(false);// 设置菜单按钮是否在导航界面显示
        viewOptions.setNaviNight(false);// 设置导航界面是否显示黑夜模式
        viewOptions.setReCalculateRouteForYaw(true);// 设置偏航时是否重新计算路径
        viewOptions.setReCalculateRouteForTrafficJam(true);// 前方拥堵时是否重新计算路径
        viewOptions.setTrafficInfoUpdateEnabled(true);// 设置交通播报是否打开
        viewOptions.setCameraInfoUpdateEnabled(true);// 设置摄像头播报是否打开
        viewOptions.setScreenAlwaysBright(true);// 设置导航状态下屏幕是否一直开启。
        viewOptions.setLeaderLineEnabled(Color.RED);//设置牵引线
        mAMapNaviView.setViewOptions(viewOptions);
    }

    /**
     * 初始化成功
     */
    @Override
    public void onInitNaviSuccess() {
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList, strategy);
    }

    /**
     * 多路径算路成功回调
     * @param ints
     */
    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        mAMapNavi.startNavi(NaviType.GPS);//GPS导航模式、EMULATOR 模拟导航
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
//            showShortToast("语音技术由科大讯飞提供");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();

        mStartList.add(mStartLatlng);
        mEndList.add(mEndLatlng);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mAMapNaviView.onPause();
            mAMapNavi.stopNavi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mAMapNaviView != null) {
                mAMapNaviView.onDestroy();
            }
            mAMapNavi.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onInitNaviFailure() {
        try {
            ToastUtil.showToast(GPSNaviActivity.this,"导航初始化失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onStartNavi(int type) {
        //开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {
        //
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        Utils.out("导航路线错误码errorInfo", errorInfo +"");
        if (errorInfo == 2){
            showShortToast("路线计算失败,请检查网络是否通畅,稍候再试!");
        } else if (errorInfo == 20){
            showShortToast("起点/终点/途经点的距离太长(距离＞6000km)");
        } else if (errorInfo == 6){
            showShortToast("路径规划终点经纬度不合法");
            finish();
        } else if (errorInfo == 3){
            showShortToast("路径规划起点经纬度不合法");
            finish();
        } else if (errorInfo == 21){
            showShortToast("途经点经纬度不合法");
            finish();
        }
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
    }

    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
    }

    @Override
    public void onNaviMapMode(int isLock) {
        //地图的模式，锁屏或锁车
    }

    @Override
    public void onNaviCancel() {
        finish();
    }


    @Override
    public void onNaviTurnClick() {
        //转弯view的点击回调
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
    }


    @Override
    public void onScanViewButtonClick() {
        //全览按钮点击回调
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //已过时
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //已过时
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
        //显示车道信息

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
    }


    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Log.d("wlx", "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Log.d("wlx", "当前在主路");
            return;
        }
        if (i == 2) {
            Log.d("wlx", "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }


    @Override
    public void onLockMap(boolean isLock) {
        //锁地图状态发生变化时回调
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviViewShowMode(int i) {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

}
