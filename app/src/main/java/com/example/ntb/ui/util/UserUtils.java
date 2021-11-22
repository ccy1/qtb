package com.example.ntb.ui.util;

import com.example.ntb.app.App;

/**
 * Created by ccy.
 * Date: 2021/11/8
 * Describe :用户状态
 */
public class UserUtils {
    /**
     * 清空用户的状态
     */
    public static void clearUserStatus() {
        SPUtils.setSharedStringData(App.getInstance(), "token", "");
//        SPUtils.setSharedStringData(App.getInstance(),"PhoneNo","");
    }
}
