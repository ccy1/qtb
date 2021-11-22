package com.example.ntb.ui.home.bean;

/**
 * Created by ccy.
 * Date: 2021/11/11
 * Describe :
 */
public class JsongetMemberInfo {
    /**
     * code : 0
     * msg : 成功
     * data : {"memberName":"泥头宝车队","accountBalance":"9999","dailyPower":"6666","monthPower":"10000","monthConsumption":"8888"}
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
         * memberName : 泥头宝车队
         * accountBalance : 9999
         * dailyPower : 6666
         * monthPower : 10000
         * monthConsumption : 8888
         */
        public String memberName;//会员名称
        public String accountBalance;//账户余额
        public String dailyPower;//今日电量
        public String monthPower;//本月电量
        public String monthConsumption;//本月金额
    }
}
