package com.example.ntb.ui.site.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public class JsonAllAmap {


    /**
     * code : 0
     * msg : 成功
     * data : [{"id":4,"longitude":"113.4368300000","latitude":"23.1098000000"},{"id":14,"longitude":"113.3223652840","latitude":"23.1532439030"},{"id":22,"longitude":"113.218658","latitude":"23.358931"},{"id":23,"longitude":"113.307022","latitude":"23.237278"},{"id":24,"longitude":"113.4291300000","latitude":"22.9359800000"},{"id":25,"longitude":"113.208282","latitude":"23.114193"},{"id":70,"longitude":"113.3783110000","latitude":"23.1480370000"},{"id":71,"longitude":"113.325","latitude":"23.148"},{"id":73,"longitude":"113.4451100000","latitude":"23.0999300000"},{"id":108,"longitude":"113.4451100000","latitude":"23.0999300000"},{"id":2757,"longitude":"113.0","latitude":"22"},{"id":2758,"longitude":"113.0000","latitude":"22.0000"},{"id":2759,"longitude":"113.0","latitude":"22"},{"id":2767,"longitude":"112","latitude":"35"},{"id":2771,"longitude":"140","latitude":"55"},{"id":2773,"longitude":"103.218658","latitude":"43.358931"},{"id":2774,"longitude":"90","latitude":"83"},{"id":2777,"longitude":"90","latitude":"83"},{"id":2786,"longitude":"133.218658","latitude":"63.358931"},{"id":2797,"longitude":"113.0000","latitude":"22.0000"},{"id":2808,"longitude":"90","latitude":"83"},{"id":2809,"longitude":"90","latitude":"83"},{"id":2810,"longitude":"90","latitude":"83"},{"id":2811,"longitude":"90","latitude":"45"},{"id":2812,"longitude":"95","latitude":"32"}]
     * fail : false
     * success : true
     */

    private int code;
    private String msg;
    private boolean fail;
    private boolean success;
    public List<SiteInfo> data = new ArrayList<SiteInfo>();


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

    public boolean isFail() {
        return fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}

