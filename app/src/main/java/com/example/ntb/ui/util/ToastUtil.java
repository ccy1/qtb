package com.example.ntb.ui.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.example.ntb.app.App;

/**
 * Created by ccy.
 * Date: 2021/10/25
 * Describe :
 */
public class ToastUtil {
    private static String oldMsg;
    protected static Toast toast   = null;
    private static long oneTime=0;
    private static long twoTime=0;

    public static void showToast(Context context, String msg){
        if(toast==null){
            toast =Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(TextUtils.equals(msg , oldMsg)){
                if(twoTime-oneTime>Toast.LENGTH_SHORT)
                    toast.show();
            }else{
                oldMsg = msg;
                toast.setText(msg);
                toast.show();
            }
        }
        oneTime=twoTime;
    }
}
