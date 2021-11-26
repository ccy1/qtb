package com.example.ntb.ui.util;

/**
 * Created by ccy.
 * Date: 2021/10/25
 * Describe :EventBus
 */
public class EventBlack {
    private int code;
    private boolean isLogin;
    public EventBlack(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
    //0:授权定位权限了，通知地图界面更新地图
    //200：刷新我的界面
    //1：退出登录
    //2：定位成功了
    //3：添加车辆成功
    //4：添加成员成功
}
