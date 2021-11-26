package com.example.ntb.ui.vehiclemanagement.bean;

import java.util.ArrayList;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */

public class CarBrandInfo {
    /**
     * code : 0
     * msg : 成功
     * data : [{"id":123,"deleted":0,"createTime":"2018-06-07 10:38:38","updateTime":"2018-06-07 10:38:38","carBrand":"众泰"}]
     * fail : false
     * success : true
     */

    public int code;
    public String msg;
    //    public List<DataBean> data;
    public ArrayList<CarBrand> data;


    public static class DataBean {
        /**
         * id : 123
         * deleted : 0
         * createTime : 2018-06-07 10:38:38
         * updateTime : 2018-06-07 10:38:38
         * carBrand : 众泰
         */

        private int id;
        private int deleted;
        private String createTime;
        private String updateTime;
        private String carBrand;

    }


    public int failReason;

//    public ArrayList<CarBrand> carbrand;

    public static class CarBrand{
        public String id;
        public String carBrand;
        public String pinyin;

        public CarBrand(String id, String carBrand, String pinyin) {
            this.id = id;
            this.carBrand = carBrand;
            this.pinyin = pinyin;
        }

        @Override
        public String toString() {
            return "CarBrand{" +
                    "id='" + id + '\'' +
                    ", carBrand='" + carBrand + '\'' +
                    ", pinyin='" + pinyin + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarBrandInfo{" +
                "failReason=" + failReason +
                ", data=" + data +
                '}';
    }
}

