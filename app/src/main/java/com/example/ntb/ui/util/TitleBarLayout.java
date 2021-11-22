package com.example.ntb.ui.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ntb.ui.R;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :标题栏
 */
public class TitleBarLayout extends LinearLayout {

    /**
     * 返回按钮
     */
    ImageView mBack;
    /**
     * 右边通知
     */
    ImageView mNotice;
    /**
     * 标题
     */
    TextView mTitle;
    /**
     * 右边文字
     */
    TextView mMenu;
    /**
     * 右边红点
     */
    TextView mRead;
    /**
     * 左边文字
     */
    TextView mSet;


    public TitleBarLayout(Context context) {
        this(context, null);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarLayout(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View inflate = View.inflate(context, R.layout.title_bar_layout, null);
        mBack = (ImageView) inflate.findViewById(R.id.iv_back);
        mNotice = (ImageView) inflate.findViewById(R.id.iv_notice);
        mTitle = (TextView) inflate.findViewById(R.id.tv_title);
        mSet = (TextView) inflate.findViewById(R.id.tv_set);
        mMenu = (TextView) inflate.findViewById(R.id.tv_menu);
        mRead = (TextView) inflate.findViewById(R.id.tv_view_read);
        mBack.setImageDrawable(getResources().getDrawable(R.drawable.selector_title_back));
//        mMenu.setBackgroundResource(R.drawable.tv_menu_selector);

        this.addView(inflate);
    }

    public void setmBack(Drawable drawable) {
        mBack.setImageDrawable(drawable);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void setMenu(String title) {
        mMenu.setText(title);
        mMenu.setVisibility(View.VISIBLE);
    }

    public void setSet(String title) {
        mSet.setText(title);
        mSet.setVisibility(View.VISIBLE);
    }


    public void setNoticeDrawable(int ids){
        mNotice.setImageDrawable(getResources().getDrawable(ids));
    }


    public void setTitleOnClick(OnClickListener onClick){
        mTitle.setOnClickListener(onClick);
    }
    public void setNoticeOnClick(OnClickListener onClick){
        mNotice.setOnClickListener(onClick);
    }
    public void setMenuOnClick(OnClickListener onClick){
        mMenu.setOnClickListener(onClick);
    }
    public void setSetOnClick(OnClickListener onClick){
        mSet.setOnClickListener(onClick);
    }
    public void setBackOnClick(OnClickListener onClick){
        mBack.setOnClickListener(onClick);
    }

    public void setMenuVisibility(int visibility){
        mMenu.setVisibility(visibility);
    }

    public void setBackVisibility(int visibility){
        mBack.setVisibility(visibility);
    }

    public void setNoticeVisibility(int visibility){
        mNotice.setVisibility(visibility);
    }

    public void setmTitleColor(int color){
        mTitle.setTextColor(color);
    }

    public void setmMenueColor(int color){
        mMenu.setTextColor(color);
    }
}

