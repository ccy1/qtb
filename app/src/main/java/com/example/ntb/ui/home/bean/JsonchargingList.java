package com.example.ntb.ui.home.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/20
 * Describe :
 */
public class JsonchargingList {

    /**
     * code : 0
     * msg : 成功
     * data : {"pageNo":1,"pageSize":10,"totalCount":2,"data":[{"licensePlate":"粤A88888","amountOfConsumption":"100","soc":80,"chargedElectricity":"50","stationName":"泥头车站点","startTime":"2021-11-11 11:11:11"},{"licensePlate":"粤A99999","amountOfConsumption":"50","soc":50,"chargedElectricity":"30","stationName":"安能绿捷站点","startTime":"2021-12-12 12:12:12"}],"info":null,"pages":1}
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
         * totalCount : 2
         * data : [{"licensePlate":"粤A88888","amountOfConsumption":"100","soc":80,"chargedElectricity":"50","stationName":"泥头车站点","startTime":"2021-11-11 11:11:11"},{"licensePlate":"粤A99999","amountOfConsumption":"50","soc":50,"chargedElectricity":"30","stationName":"安能绿捷站点","startTime":"2021-12-12 12:12:12"}]
         * info : null
         * pages : 1
         */
        public int pageNo;
        public int pageSize;
        public int totalCount;
        public Object info;
        public int pages;
        public List<DataBean> data;
        public static class DataBean {
            /**
             * licensePlate : 粤A88888
             * amountOfConsumption : 100
             * soc : 80
             * chargedElectricity : 50
             * stationName : 泥头车站点
             * startTime : 2021-11-11 11:11:11
             */
            /** 牌照 */
            public String licensePlate;
            /** 消费金额 */
            public String amountOfConsumption;
            /** SOC */
            public Integer soc;
            /** 已充电量 */
            public String chargedElectricity;
            /** 站点名称 */
            public String stationName;
            /** 时间 */
            public String startTime;
        }
    }
}
