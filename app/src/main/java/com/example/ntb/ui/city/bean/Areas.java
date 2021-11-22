package com.example.ntb.ui.city.bean;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class Areas {

    public boolean isHome;
    public String pid;
    public String name;
    public String pinyin;
    public String x;
    public String y;
    public String id;

    public Areas(String pid, String name, String pinyin, String x, String y, String id) {
        this.pid = pid;
        this.name = name;
        this.pinyin = pinyin;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Areas() {
    }

    @Override
    public String toString() {
        return "Areas{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

