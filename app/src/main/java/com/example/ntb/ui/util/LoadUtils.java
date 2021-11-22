package com.example.ntb.ui.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.ntb.ui.R;

/**
 * Created by ccy.
 * Date: 2021/10/20
 * Describe :等待进度条
 */
public class LoadUtils {
    public static boolean isDialogShow = false;
    static private Dialog dialog = null;

    /**
     * 显示等待的进度条
     *
     * @param context 上下文
     * @param msg     信息
     */
    public static void showWaitProgress(Context context, String msg) {
        if (dialog != null) {
            dissmissWaitProgress();
        }
        /**
         * 展示加载等待的对话框
         */
        if (isDialogShow) {
            return;
        }
        showWaitDialog(context, msg);
    }

    public static void dissmissWaitProgress() {
        if (dialog != null) {
            dialog.dismiss();
            isDialogShow = false;
            dialog = null;
        }
    }


    /**
     * 加载等待对话框
     *
     * @param mContext
     * @param msg
     */
    protected static void showWaitDialog(Context mContext, String msg) {
        isDialogShow = true;
        dialog = new Dialog(mContext, R.style.MyDialogStyle);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_wait, null);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);

        TextView tv_des = (TextView) dialogView.findViewById(R.id.tv_dialog_des);
        if (!msg.equals("")){
            tv_des.setText(msg);
        }

        if (dialog != null) {
            dialog.show();
        }

    }
}

