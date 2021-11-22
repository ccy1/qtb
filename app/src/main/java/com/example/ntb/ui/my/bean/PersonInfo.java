package com.example.ntb.ui.my.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class PersonInfo implements Serializable {
    public int code;
    public String msg;
    public DataBean data;
    public boolean success;
    public boolean fail;
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        public boolean hasInvoice;

        public String token;
        // 会员名称
        public String name;
        // 会员头像
        public String icon;
        // 签到次数
        public int signCount;
        // 是否已签到
        public boolean signIn;

        // 会员等级
        public String level;
        // 拥有的车辆型号
        public String vehicleNum;
        // 账户余额
        public String accountBalance;
        /**
         * 会员充值积分
         */
        public String integral;
        /**
         * 预约数量
         */
        public String appointSum;

        /**
         * 会员消费积分
         */
        public String consumeIntegral;

        /**
         * 会员免单次数
         */
        public Integer singleRemainder;

        public Integer  couponList;//    优惠卷数量

        // 收藏数目
        public String collectNum;
        // 性别
        public String sex;
        // 出生日期
        public String birthday;
        // 学历
        public String education;
        // 居住城市
        public String liveCity;
        // 手机号
        public String telephone;
        // 收货地址
        public String receiveAddress;

        /**
         * 0:个人 1:成员 2:团队
         */
        public int memberType;

        /**
         * 是否是团队成员true是
         */
        public boolean izLeaguer;

        /**
         * 会员ID
         */
        public String memberId;

        /**
         * 身份个数
         */
        public int memberCount;

        /**
         * 子成员的名字
         */
        public String leaguerName;

        /**
         * 子成员的手机号码
         */
        public String leaguerPhone;

        public String electricCardsMoney;//电卡金额

        public String electricCardsNumber;//电卡数量

        public List<VehiclesBean> vehicles;

    }


    /**
     * 车辆信息
     */
    public static class VehiclesBean implements Serializable{
        public String id;
        public String plateNo;
        public String member;
        public String isenable;
        public String brandId;
        public String brandName;
        public String carTypeId;
        public String carTypeName;
        public String mileage;
        public String mileageStr;
        public String productionTime;
        public String vehicleType;

        public int defaultVehicle;//是否选中为默认  0：默认 1：不默认

        /**
         * 集团的名称
         */
        public String memberName;

        public void setMemberName(String groupName) {
            this.memberName = groupName;
        }

        public String getMemberName() {
            return memberName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlateno() {
            return plateNo;
        }

        public void setPlateno(String plateno) {
            this.plateNo = plateno;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getIsenable() {
            return isenable;
        }

        public void setIsenable(String isenable) {
            this.isenable = isenable;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCarTypeId() {
            return carTypeId;
        }

        public void setCarTypeId(String carTypeId) {
            this.carTypeId = carTypeId;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public String getMileageStr() {
            return mileageStr;
        }

        public void setMileageStr(String mileageStr) {
            this.mileageStr = mileageStr;
        }

        public String getProductionTime() {
            return productionTime;
        }

        public void setProductionTime(String productionTime) {
            this.productionTime = productionTime;
        }
    }
}

