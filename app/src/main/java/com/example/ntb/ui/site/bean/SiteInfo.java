package com.example.ntb.ui.site.bean;

import com.amap.api.maps.model.LatLng;
import com.example.ntb.ui.util.ClusterItem;

import java.io.Serializable;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public class SiteInfo implements Serializable, ClusterItem {

    /**
     * 站点ID
     */
    public String id;
    /**
     * 站点名称
     */
    public String name;
    /**
     * 站点图标
     */
//    public String icon;
    public String stationPic1;
    /**
     * 站点状态
     */
    public String status;
    /**
     * 经度
     */
//    public String x;
    public String latitude;
    /**
     * 纬度
     */
//    public String y;
    public String longitude;
    /**
     * 服务费单价(单位:分)
     */
    public String servicePrice;
    /**
     * 停车费单价(单位:分)
     */
    public String parkingPrice;
    /**
     * 预约费单价(单位:分)
     */
    public String appointPrice;
    /**
     * 电费
     */
//    public String eletrPrice;

    public String electricPrice;
    /**
     * 空闲桩数
     */
    public String freeCount;

    /**
     * 站点桩数
     */
    public String count;
    /**
     * 营业开始时间
     */
    public String openTime;
    /**
     * 营业结束时间
     */
    public String closeTime;

    /**
     * 地址
     */
    public String stationAddress;

    /**
     * 电话号码
     */
    public String stationPhoneNo;

    /**
     * 快充空闲
     */
//    public String fastFreeCount;
    public String freeFastCount;
    /**
     * 慢充空闲
     */
    public String slowFreeCount;
    /**
     * 快充总数
     */
    public String fastTotalCount;
    /**
     * 慢充总数
     */
    public String slowTotalCount;

    /**
     * 站点里当前位置的距离（自己计算）
     */
    public double distance;

    /**
     * 上午开始时间
     */
//    public String amOpenTime;
    public String openTimeAm;
    /**
     * 上午关闭时间
     */
//    public String amCloseTime;
    public String closeTimeAm;
    /**
     * 下午开始时间
     */
//    public String pmOpenTime;
    public String openTimePm;
    /**
     * 下午关闭时间
     */
//    public String pmCloseTime;
    public String closeTimePm;

    /**
     * 站点补贴
     */
    public String subsidy;

//        /**
//         * 抢空闲数
//         */
//        public String freeGunCount;

    /**
     * 枪的充电数
     */
    public String chargingGunCount;

    /**
     * 枪充电完成的数量
     */
    public String chargeFinishGunCount;

    /**
     * 站点评分
     */
    public String score;

    /**
     * 站点是否需要停车费 （false免费  true收费）
     */
    public boolean parkingFlag;

    /**
     * 限时内起步收费
     */
    public String startingRestriction;


    /**
     * 折扣
     */
    public String stationdiscount;


    /**
     * true：自营
     */
    public boolean isauto;

    /**
     * 聚合点(地图显示点聚合使用)
     */
    public LatLng mLatLng;

    /**
     * 是否有贩卖机
     */
    public boolean izHaveVendingMachine;


    /**
     * 是否有优惠
     */
    public String hasDiscount;//0为没有，1是有

    /**
     * 优惠时间
     */
    public String discountTime;

    /**
     * 差价信息
     */
    public String priceDiff;

    /**
     * 距离分数
     */
    public double distanceFraction;
    /**
     * 价格分数
     */
    public double priceFraction;
    /**
     * 空闲桩分数
     */
    public double freeGunFraction;


    public String stationRunType;//（运营类型）

    public String time;

    private String times;

    public boolean AppointAble;//是否可预约

    public boolean isTure;

    public String tag1;

    public String tag2;

    public double stationLowPrice;//价格

    public String pages;//总页数

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    /**
     * 是否有优惠
     */
    public String isHasDiscount() {
        return hasDiscount;
    }

    /**
     * 优惠时间
     */
    public String getStationdiscount() {
        return stationdiscount;
    }

    /**
     * 差价信息
     */
    public String getPriceDiff() {
        return priceDiff;
    }

    /**
     * 预约费
     *
     * @return
     */
    public String getAppointPrice() {
        return appointPrice;
    }


    @Override
    public LatLng getPosition() {
        return mLatLng;
    }

    @Override
    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SiteInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", stationPic1='" + stationPic1 + '\'' +
                ", status='" + status + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", servicePrice='" + servicePrice + '\'' +
                ", parkingPrice='" + parkingPrice + '\'' +
                ", appointPrice='" + appointPrice + '\'' +
                ", electricPrice='" + electricPrice + '\'' +
                ", freeCount='" + freeCount + '\'' +
                ", count='" + count + '\'' +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", stationAddress='" + stationAddress + '\'' +
                ", stationPhoneNo='" + stationPhoneNo + '\'' +
                ", freeFastCount='" + freeFastCount + '\'' +
                ", slowFreeCount='" + slowFreeCount + '\'' +
                ", fastTotalCount='" + fastTotalCount + '\'' +
                ", slowTotalCount='" + slowTotalCount + '\'' +
                ", distance=" + distance +
                ", openTimeAm='" + openTimeAm + '\'' +
                ", closeTimeAm='" + closeTimeAm + '\'' +
                ", openTimePm='" + openTimePm + '\'' +
                ", closeTimePm='" + closeTimePm + '\'' +
                ", subsidy='" + subsidy + '\'' +
                ", chargingGunCount='" + chargingGunCount + '\'' +
                ", chargeFinishGunCount='" + chargeFinishGunCount + '\'' +
                ", score='" + score + '\'' +
                ", parkingFlag=" + parkingFlag +
                ", startingRestriction='" + startingRestriction + '\'' +
                ", stationdiscount='" + stationdiscount + '\'' +
                ", isauto=" + isauto +
                ", mLatLng=" + mLatLng +
                ", izHaveVendingMachine=" + izHaveVendingMachine +
                ", hasDiscount=" + hasDiscount +
                ", discountTime='" + discountTime + '\'' +
                ", priceDiff='" + priceDiff + '\'' +
                ", distanceFraction=" + distanceFraction +
                ", tag1=" + tag1 + '\'' +
                ", tag1=" + tag2 + '\'' +
                ", priceFraction=" + priceFraction +
                ", time=" + time +
                ", AppointAble=" + AppointAble +
                ", freeGunFraction=" + freeGunFraction +
                ", stationRunType='" + stationRunType + '\'' +
                '}';
    }
}
