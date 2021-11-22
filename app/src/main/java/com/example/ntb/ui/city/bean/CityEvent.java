package com.example.ntb.ui.city.bean;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class CityEvent {

    private Areas info;

    public CityEvent(Areas data){
        this.info = data;
    }

    public Areas getInfo() {
        return info;
    }

    public void setInfo(Areas info) {
        this.info = info;
    }
}
