package com.example.ntb.ui.site.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public class JsonReservation {


    /**
     * code : 0
     * msg : 成功
     * data : {"cancelDaily":3,"waitTimeOne":null,"waitTimeTwo":null,"waitTimeThree":null,"waitCostOne":null,"waitCostTwo":null,"waitCostThree":null,"renewTime":3,"renewLong":null,"renewCost":500,"desc":"1、用户需要守时守信，不要随意违约；\r\n2、同一用户账号，在同一时段内只允许预约一个充电桩的充电枪；\r\n3、充电中的账户不能进行预约；\r\n4、充电枪被预约后，将预定开始时间起有3分钟的等待充电时间，在开始3分钟后，该用户没来充电将解除预约关系，视为违约。将扣除违约金；\r\n5、预约提交后可以在预约开始3分钟内无责取消，在约定时间3分钟后将不能取消预约；单日取消次数不得超过3次，取消超过3次则当日将无法进行预约；\r\n6、为建设和维护和谐的充电环境，不浪费公共资源。请车主务必停止充电后驶离充电车位，以免影响其他用户充电。","userId":null,"freeTime":null,"openCount":null,"stationIds":null,"rules":[{"cost":"全部","time":"全部"},{"cost":"500","time":"15"}],"ruleTwo":[{"cost":0,"time":15},{"cost":500,"time":15}]}
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
         * cancelDaily : 3
         * waitTimeOne : null
         * waitTimeTwo : null
         * waitTimeThree : null
         * waitCostOne : null
         * waitCostTwo : null
         * waitCostThree : null
         * renewTime : 3
         * renewLong : null
         * renewCost : 500
         * desc : 1、用户需要守时守信，不要随意违约；
         * 2、同一用户账号，在同一时段内只允许预约一个充电桩的充电枪；
         * 3、充电中的账户不能进行预约；
         * 4、充电枪被预约后，将预定开始时间起有3分钟的等待充电时间，在开始3分钟后，该用户没来充电将解除预约关系，视为违约。将扣除违约金；
         * 5、预约提交后可以在预约开始3分钟内无责取消，在约定时间3分钟后将不能取消预约；单日取消次数不得超过3次，取消超过3次则当日将无法进行预约；
         * 6、为建设和维护和谐的充电环境，不浪费公共资源。请车主务必停止充电后驶离充电车位，以免影响其他用户充电。
         * userId : null
         * freeTime : null
         * openCount : null
         * stationIds : null
         * rules : [{"cost":"全部","time":"全部"},{"cost":"500","time":"15"}]
         * ruleTwo : [{"cost":0,"time":15},{"cost":500,"time":15}]
         */

        private int cancelDaily;
        private Object waitTimeOne;
        private Object waitTimeTwo;
        private Object waitTimeThree;
        private Object waitCostOne;
        private Object waitCostTwo;
        private Object waitCostThree;
        private int renewTime;
        private int renewLong;
        private int renewCost;
        private String desc;
        private Object userId;
        private Object freeTime;
        private Object openCount;
        private Object stationIds;
        private List<RulesBean> rules;
        private List<RuleTwoBean> ruleTwo;

        public int getCancelDaily() {
            return cancelDaily;
        }

        public void setCancelDaily(int cancelDaily) {
            this.cancelDaily = cancelDaily;
        }

        public Object getWaitTimeOne() {
            return waitTimeOne;
        }

        public void setWaitTimeOne(Object waitTimeOne) {
            this.waitTimeOne = waitTimeOne;
        }

        public Object getWaitTimeTwo() {
            return waitTimeTwo;
        }

        public void setWaitTimeTwo(Object waitTimeTwo) {
            this.waitTimeTwo = waitTimeTwo;
        }

        public Object getWaitTimeThree() {
            return waitTimeThree;
        }

        public void setWaitTimeThree(Object waitTimeThree) {
            this.waitTimeThree = waitTimeThree;
        }

        public Object getWaitCostOne() {
            return waitCostOne;
        }

        public void setWaitCostOne(Object waitCostOne) {
            this.waitCostOne = waitCostOne;
        }

        public Object getWaitCostTwo() {
            return waitCostTwo;
        }

        public void setWaitCostTwo(Object waitCostTwo) {
            this.waitCostTwo = waitCostTwo;
        }

        public Object getWaitCostThree() {
            return waitCostThree;
        }

        public void setWaitCostThree(Object waitCostThree) {
            this.waitCostThree = waitCostThree;
        }

        public int getRenewTime() {
            return renewTime;
        }

        public void setRenewTime(int renewTime) {
            this.renewTime = renewTime;
        }

        public int getRenewLong() {
            return renewLong;
        }

        public void setRenewLong(int renewLong) {
            this.renewLong = renewLong;
        }

        public int getRenewCost() {
            return renewCost;
        }

        public void setRenewCost(int renewCost) {
            this.renewCost = renewCost;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getFreeTime() {
            return freeTime;
        }

        public void setFreeTime(Object freeTime) {
            this.freeTime = freeTime;
        }

        public Object getOpenCount() {
            return openCount;
        }

        public void setOpenCount(Object openCount) {
            this.openCount = openCount;
        }

        public Object getStationIds() {
            return stationIds;
        }

        public void setStationIds(Object stationIds) {
            this.stationIds = stationIds;
        }

        public List<RulesBean> getRules() {
            return rules;
        }

        public void setRules(List<RulesBean> rules) {
            this.rules = rules;
        }

        public List<RuleTwoBean> getRuleTwo() {
            return ruleTwo;
        }

        public void setRuleTwo(List<RuleTwoBean> ruleTwo) {
            this.ruleTwo = ruleTwo;
        }

        public static class RulesBean {
            /**
             * cost : 全部
             * time : 全部
             */

            private String cost;
            private String time;
            public boolean isChecked;

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class RuleTwoBean {
            /**
             * cost : 0
             * time : 15
             */


            private int cost;
            private int time;
            public boolean isChecked;

            public int getCost() {
                return cost;
            }

            public void setCost(int cost) {
                this.cost = cost;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }
        }
    }
}
