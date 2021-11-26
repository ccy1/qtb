package com.example.ntb.ui.vehiclemanagement.bean;

import java.util.ArrayList;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */
public class CarTypeInfo {
    /**
     * code : 0
     * msg : 成功
     * data : [{"id":1,"deleted":0,"createTime":"2018-06-07 10:39:27","updateTime":"2018-06-07 10:39:27","carBrandId":1,"carType":"U5 ION"}]
     * fail : false
     * success : true
     */

    public int code;
    public String msg;
    public ArrayList<BrandTypeDtos> data;
//    public List<DataBean> data;
//
//    public static class DataBean {
//        /**
//         * id : 1
//         * deleted : 0
//         * createTime : 2018-06-07 10:39:27
//         * updateTime : 2018-06-07 10:39:27
//         * carBrandId : 1
//         * carType : U5 ION
//         */
//
//        private int id;
//        private int deleted;
//        private String createTime;
//        private String updateTime;
//        private int carBrandId;
//        private String carType;
//
//    }


//    public int failReason;

    public class BrandTypeDtos{
        public String id;
        public String carBrandId;
        public String carType;
    }
}

