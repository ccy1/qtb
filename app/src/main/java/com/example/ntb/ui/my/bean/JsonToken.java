package com.example.ntb.ui.my.bean;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class JsonToken {
    private int code;
    private String msg;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}
