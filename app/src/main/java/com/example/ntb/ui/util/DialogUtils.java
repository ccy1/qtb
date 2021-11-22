package com.example.ntb.ui.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ntb.ui.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ccy.
 * Date: 2021/10/25
 * Describe :
 */
public class DialogUtils {
    /**
     * 自定义对话框
     *
     * @return
     */
    public Dialog getDialog(Context context, View view) {
        // 使用自定义的主题
        Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
        dialog.setCancelable(false); // 设置抢占焦点
        dialog.setContentView(view);
        return dialog;
    }

    private static String content;
    private Dialog mDialog;
    private static EditText et_release_content;
    /**
     *
     * @param context   上下文
     * @param titles    标题
     * @param content   内容
     * @param okStr     右边按钮
     * @param cancelStr 左边按钮
     *
     * @return          Dialog
     */
    public Dialog showDialog(Context context, String titles, String content, String okStr, String cancelStr) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.view_dialog_vup, null);
        mDialog = getDialog(context, dialogView);

        mDialog.setContentView(dialogView);
        mDialog.setCancelable(false);
        Button okBtn = mDialog.findViewById(R.id.ok_btn);
        Button cancelBtn = mDialog.findViewById(R.id.cancle_btn);
        TextView title = mDialog.findViewById(R.id.tv_vu_title);
        TextView des = mDialog.findViewById(R.id.tv_vu_des);
        title.setText(titles);
        des.setText(content);
        des.setMovementMethod(ScrollingMovementMethod.getInstance());
        okBtn.setText(okStr);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消对话框
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                if (listener != null) {
                    listener.onSave();
                }
            }
        });

        cancelBtn.setVisibility(View.VISIBLE);
        cancelBtn.setText(cancelStr);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });
        if (cancelStr == null){
            mDialog.findViewById(R.id.view_l).setVisibility(View.GONE);
            cancelBtn.setVisibility(View.GONE);
        }
        mDialog.show();
        return mDialog;
    }

    private OnDialogClickListener listener;

    private OnClickDiglog onClicklistener;

    public void setDialogOnClick(OnClickDiglog onClicklistener){
        this.onClicklistener =onClicklistener;
    }

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }



    public interface OnDialogClickListener{
        void onSave();

        void onCancel();


    }

    public interface OnClickDiglog{
        void onClick();
        void onClose();
    }
}
