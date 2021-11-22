package com.example.ntb.ui.site.bean;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public class JsonAllAmapDetail {


    /**
     * code : 0
     * msg : 成功
     * data : {"id":24,"name":"禺快充石基站C区","price":null,"priceDiff":0,"fastGunCount":0,"fastFreeGunCount":0,"slowGunCount":0,"slowFreeGunCount":0,"longitude":"113.4291300000","latitude":"22.9359800000","openTime":"00:00-00:00/12:00-20:00","appointment":false,"discount":false,"discountTime":null,"parking":false,"parkingPrice":"0","parkingPriceOutMsg":"","parkingSubsidy":null,"tag1":null,"tag2":null,"arrivalTime":0,"distance":0,"pic":null,"score":4,"address":"广州市番禺区亚运大道番禺区公共汽车有限公司"}
     * success : true
     * fail : false
     */

    private int code;
    private String msg;
    private DataBean data;
    private boolean success;
    private boolean fail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isFail() {
        return fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public static class DataBean {
        /**
         * id : 24
         * name : 禺快充石基站C区
         * price : null
         * priceDiff : 0
         * fastGunCount : 0
         * fastFreeGunCount : 0
         * slowGunCount : 0
         * slowFreeGunCount : 0
         * longitude : 113.4291300000
         * latitude : 22.9359800000
         * openTime : 00:00-00:00/12:00-20:00
         * appointment : false
         * discount : false
         * discountTime : null
         * parking : false
         * parkingPrice : 0
         * parkingPriceOutMsg :
         * parkingSubsidy : null
         * tag1 : null
         * tag2 : null
         * arrivalTime : 0
         * distance : 0
         * pic : null
         * score : 4
         * address : 广州市番禺区亚运大道番禺区公共汽车有限公司
         */

        private int id;
        private String name;
        private int price;
        private String priceDiff;
        private int fastGunCount;
        private int fastFreeGunCount;
        private int slowGunCount;
        private int slowFreeGunCount;
        private String longitude;
        private String latitude;
        private String openTime;
        private boolean appointment;
        private boolean discount;
        private Object discountTime;
        private boolean parking;
        private String parkingPrice;
        private String parkingPriceOutMsg;
        private Object parkingSubsidy;
        private Object tag1;
        private Object tag2;
        private int arrivalTime;
        private int distance;
        private String pic;
        private int score;
        private String address;

        public boolean onBusiness;//是否在运营时间内

        public String runType;//运营类型

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getPriceDiff() {
            return priceDiff;
        }

        public void setPriceDiff(String priceDiff) {
            this.priceDiff = priceDiff;
        }

        public int getFastGunCount() {
            return fastGunCount;
        }

        public void setFastGunCount(int fastGunCount) {
            this.fastGunCount = fastGunCount;
        }

        public int getFastFreeGunCount() {
            return fastFreeGunCount;
        }

        public void setFastFreeGunCount(int fastFreeGunCount) {
            this.fastFreeGunCount = fastFreeGunCount;
        }

        public int getSlowGunCount() {
            return slowGunCount;
        }

        public void setSlowGunCount(int slowGunCount) {
            this.slowGunCount = slowGunCount;
        }

        public int getSlowFreeGunCount() {
            return slowFreeGunCount;
        }

        public void setSlowFreeGunCount(int slowFreeGunCount) {
            this.slowFreeGunCount = slowFreeGunCount;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public boolean isAppointment() {
            return appointment;
        }

        public void setAppointment(boolean appointment) {
            this.appointment = appointment;
        }

        public boolean isDiscount() {
            return discount;
        }

        public void setDiscount(boolean discount) {
            this.discount = discount;
        }

        public Object getDiscountTime() {
            return discountTime;
        }

        public void setDiscountTime(Object discountTime) {
            this.discountTime = discountTime;
        }

        public boolean isParking() {
            return parking;
        }

        public void setParking(boolean parking) {
            this.parking = parking;
        }

        public String getParkingPrice() {
            return parkingPrice;
        }

        public void setParkingPrice(String parkingPrice) {
            this.parkingPrice = parkingPrice;
        }

        public String getParkingPriceOutMsg() {
            return parkingPriceOutMsg;
        }

        public void setParkingPriceOutMsg(String parkingPriceOutMsg) {
            this.parkingPriceOutMsg = parkingPriceOutMsg;
        }

        public Object getParkingSubsidy() {
            return parkingSubsidy;
        }

        public void setParkingSubsidy(Object parkingSubsidy) {
            this.parkingSubsidy = parkingSubsidy;
        }

        public Object getTag1() {
            return tag1;
        }

        public void setTag1(Object tag1) {
            this.tag1 = tag1;
        }

        public Object getTag2() {
            return tag2;
        }

        public void setTag2(Object tag2) {
            this.tag2 = tag2;
        }

        public int getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(int arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}

