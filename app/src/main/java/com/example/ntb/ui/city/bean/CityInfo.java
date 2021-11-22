package com.example.ntb.ui.city.bean;

import java.util.ArrayList;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class CityInfo {
    public int code;
    public DataBean data;
    public String msg;
    public class DataBean {
        public ArrayList<Areas> maplist;
    }
}

