package com.example.ntb.ui.membermanagement.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/25
 * Describe :
 */
public class JsonTest {

    /**
     * code : 0
     * msg : 成功
     * data : [{"province":"粤","letter":"A","number":"111111"}]
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
         * province : 粤
         * letter : A
         * number : 111111
         */

        private String province;
        private String letter;
        private String number;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
