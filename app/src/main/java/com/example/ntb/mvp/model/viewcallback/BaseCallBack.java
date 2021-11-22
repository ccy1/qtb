package com.example.ntb.mvp.model.viewcallback;

/**
 * Created by ccy.
 * Date: 2021/8/27
 * Describe :
 */
public interface BaseCallBack {
    void Successful(String Json);//成功的回调
    void Failure(String Msg);//失败的回调
}
