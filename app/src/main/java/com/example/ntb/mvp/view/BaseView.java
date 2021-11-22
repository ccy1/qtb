package com.example.ntb.mvp.view;

/**
 * Created by ccy.
 * Date: 2021/8/27
 * Describe :
 */
public interface BaseView {
    //请求成功
    void resultSucess(int type, String json);
    //请求失败
    void resultFailure(int type, String Msg);
}
