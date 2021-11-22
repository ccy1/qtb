package com.example.ntb.ui.site.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/8
 * Describe :
 */
public class JsonCityId {


    /**
     * code : 0
     * msg : 成功
     * data : [{"cityName":"广州市","cityPinyin":"G","id":100293,"cityId":100293},{"cityName":"深圳市","cityPinyin":"S","id":100287,"cityId":100287}]
     * success : true
     * fail : false
     */

    private int code;
    private String msg;
    private boolean success;
    private boolean fail;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cityName : 广州市
         * cityPinyin : G
         * id : 100293
         * cityId : 100293
         */

        private String cityName;
        private String cityPinyin;
        private int id;
        private int cityId;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityPinyin() {
            return cityPinyin;
        }

        public void setCityPinyin(String cityPinyin) {
            this.cityPinyin = cityPinyin;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }
    }
}
