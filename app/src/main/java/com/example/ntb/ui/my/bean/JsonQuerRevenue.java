package com.example.ntb.ui.my.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/22
 * Describe :
 */
public class JsonQuerRevenue {
    /**
     * code : 0
     * msg : 成功
     * data : {"totleblance":"780.0","revenuelist":[{"busid":"1661890","type":"充电","display":"广州东站充电站","blance":"1350","evelentblance":"780","date":"2020-07-09 14:42:23","eventType":"1","eventName":"账单事件","operate":"账单事件"},{"busid":"1661889","type":"充电","display":"广州东站充电站","blance":"1350","evelentblance":"2130","date":"2020-07-09 14:39:03","eventType":"1","eventName":"账单事件","operate":"账单事件"},{"busid":"1661888","type":"充电","display":"广州东站充电站","blance":"1350","evelentblance":"3480","date":"2020-07-09 14:27:25","eventType":"1","eventName":"账单事件","operate":"账单事件"},{"busid":"1661887","type":"充电","display":"广州东站充电站","blance":"1350","evelentblance":"4830","date":"2020-07-09 14:25:52","eventType":"1","eventName":"账单事件","operate":"账单事件"},{"busid":"1661416","type":"充值","display":null,"blance":"4574","evelentblance":"6180","date":"2020-04-17 21:40:32","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"1661417","type":"充值","display":null,"blance":"5426","evelentblance":"1606","date":"2020-04-17 21:40:32","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"1149122","type":"还款","display":"","blance":"10","evelentblance":"-3820","date":"2019-12-01 11:18:20","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"857913","type":"还款","display":"","blance":"10","evelentblance":"-3830","date":"2019-10-11 20:31:31","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"854658","type":"还款","display":"","blance":"10","evelentblance":"-3840","date":"2019-10-11 10:57:08","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"756124","type":"充电","display":null,"blance":"11","evelentblance":"-3850","date":"2019-09-24 10:55:45","eventType":"1","eventName":"账单事件","operate":"账单事件"}],"total":18,"size":10,"current":1,"pages":2,"lastBalance":-3839}
     * success : true
     * fail : false
     */
    public int code;
    public String msg;
    public DataBean data;
    public boolean success;
    public boolean fail;

    public static class DataBean {
        /**
         * totleblance : 780.0
         * revenuelist : [{"busid":"1661890","type":"充电","display":"广州东站充电站","blance":"1350","evelentblance":"780","date":"2020-07-09 14:42:23","eventType":"1","eventName":"账单事件","operate":"账单事件"},{"busid":"1661889","type":"充电","display":"广州东站充电站","blance":"1350","evelentblance":"2130","date":"2020-07-09 14:39:03","eventType":"1","eventName":"账单事件","operate":"账单事件"},{"busid":"1661888","type":"充电","display":"广州东站充电站","blance":"1350","evelentblance":"3480","date":"2020-07-09 14:27:25","eventType":"1","eventName":"账单事件","operate":"账单事件"},{"busid":"1661887","type":"充电","display":"广州东站充电站","blance":"1350","evelentblance":"4830","date":"2020-07-09 14:25:52","eventType":"1","eventName":"账单事件","operate":"账单事件"},{"busid":"1661416","type":"充值","display":null,"blance":"4574","evelentblance":"6180","date":"2020-04-17 21:40:32","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"1661417","type":"充值","display":null,"blance":"5426","evelentblance":"1606","date":"2020-04-17 21:40:32","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"1149122","type":"还款","display":"","blance":"10","evelentblance":"-3820","date":"2019-12-01 11:18:20","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"857913","type":"还款","display":"","blance":"10","evelentblance":"-3830","date":"2019-10-11 20:31:31","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"854658","type":"还款","display":"","blance":"10","evelentblance":"-3840","date":"2019-10-11 10:57:08","eventType":"2","eventName":"充值事件","operate":"充值事件"},{"busid":"756124","type":"充电","display":null,"blance":"11","evelentblance":"-3850","date":"2019-09-24 10:55:45","eventType":"1","eventName":"账单事件","operate":"账单事件"}]
         * total : 18
         * size : 10
         * current : 1
         * pages : 2
         * lastBalance : -3839
         */

        public String totleblance;
        public int total;
        public int size;
        public int current;
        public int pages;
        public int lastBalance;
        public List<RevenuelistBean> revenuelist;

        public static class RevenuelistBean {
            /**
             * busid : 1661890
             * type : 充电
             * display : 广州东站充电站
             * blance : 1350
             * evelentblance : 780
             * date : 2020-07-09 14:42:23
             * eventType : 1
             * eventName : 账单事件
             * operate : 账单事件
             */
            public String busid;
            public String type;
            public String display;
            public String blance;
            public String evelentblance;
            public String date;
            public String eventType;
            public String eventName;
            public String operate;
            public boolean spend;//判断正负
        }
    }
}
