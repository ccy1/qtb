package com.example.ntb.ui.site.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/8
 * Describe :
 */
public class JsonSite {

    /**
     * code : 0
     * msg : 成功
     * data : {"pageNo":1,"pageSize":10,"totalCount":1,"data":[{"id":43,"name":"杨汊湖充电站","price":0,"priceDiff":"0","fastGunCount":0,"fastFreeGunCount":0,"slowGunCount":0,"slowFreeGunCount":0,"longitude":"114.255287","latitude":"30.63002","openTime":"全天开放","appointment":false,"discount":false,"discountTime":null,"parking":false,"parkingPrice":"0","parkingPriceOutMsg":"","parkingSubsidy":"免停车费","tag1":null,"tag2":null,"arrivalTime":12,"distance":6,"pic":"http://119.96.224.79:7110/group1/image/b587d5d313fe976b522254113e93c312065938980588792886.jpg?download=0","score":5,"address":"湖北省武汉市江汉区杨汊湖地铁站D1出口附近","onBusiness":true,"tags":[],"runType":"ZiYing"}],"info":null,"pages":1}
     * success : true
     * fail : false
     */

    public int code;
    public String msg;
    public DataBeanX data;

    public static class DataBeanX {
        /**
         * pageNo : 1
         * pageSize : 10
         * totalCount : 1
         * data : [{"id":43,"name":"杨汊湖充电站","price":0,"priceDiff":"0","fastGunCount":0,"fastFreeGunCount":0,"slowGunCount":0,"slowFreeGunCount":0,"longitude":"114.255287","latitude":"30.63002","openTime":"全天开放","appointment":false,"discount":false,"discountTime":null,"parking":false,"parkingPrice":"0","parkingPriceOutMsg":"","parkingSubsidy":"免停车费","tag1":null,"tag2":null,"arrivalTime":12,"distance":6,"pic":"http://119.96.224.79:7110/group1/image/b587d5d313fe976b522254113e93c312065938980588792886.jpg?download=0","score":5,"address":"湖北省武汉市江汉区杨汊湖地铁站D1出口附近","onBusiness":true,"tags":[],"runType":"ZiYing"}]
         * info : null
         * pages : 1
         */

        public int pageNo;
        public int pageSize;
        public int totalCount;
        public int pages;
        public List<DataBean> data;

        public static class DataBean {
            /**
             * id : 43
             * name : 杨汊湖充电站
             * price : 0
             * priceDiff : 0
             * fastGunCount : 0
             * fastFreeGunCount : 0
             * slowGunCount : 0
             * slowFreeGunCount : 0
             * longitude : 114.255287
             * latitude : 30.63002
             * openTime : 全天开放
             * appointment : false
             * discount : false
             * discountTime : null
             * parking : false
             * parkingPrice : 0
             * parkingPriceOutMsg :
             * parkingSubsidy : 免停车费
             * tag1 : null
             * tag2 : null
             * arrivalTime : 12
             * distance : 6
             * pic : http://119.96.224.79:7110/group1/image/b587d5d313fe976b522254113e93c312065938980588792886.jpg?download=0
             * score : 5
             * address : 湖北省武汉市江汉区杨汊湖地铁站D1出口附近
             * onBusiness : true
             * tags : []
             * runType : ZiYing
             */

            public int id;
            public String name;
            public int price;
            public String priceDiff;
            public int fastGunCount;
            public int fastFreeGunCount;
            public int slowGunCount;
            public int slowFreeGunCount;
            public String longitude;
            public String latitude;
            public String openTime;
            public boolean appointment;
            public boolean discount;
            public Object discountTime;
            public boolean parking;
            public String parkingPrice;
            public String parkingPriceOutMsg;
            public String parkingSubsidy;
            public Object tag1;
            public Object tag2;
            public int arrivalTime;
            public int distance;
            public String pic;
            public int score;
            public String address;
            public boolean onBusiness;
            public String runType;
            public List<String> tags;
            public int servicePrice;//服务费
        }
    }
}
