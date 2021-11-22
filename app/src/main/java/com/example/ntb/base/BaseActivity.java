package com.example.ntb.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ntb.ui.R;
import com.example.ntb.ui.util.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBeforeSetContentView();
        setContentView(getLayoutId());

        try {
            StatusBarUtils.with(this)
                    .init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBind = ButterKnife.bind(this);
        initView(savedInstanceState);
        initData();
        View titleBar = getTitleBar();
        if (titleBar != null){
            if (Build.VERSION.SDK_INT >= 20.) {
                int statusBarHeight = getStatusBarHeight(this);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) titleBar.getLayoutParams();
                lp.setMargins(0, lp.topMargin + statusBarHeight, 0, 0);
                titleBar.setLayoutParams(lp);
            }
        }

    }


    /**
     * 获取标题栏
     * @return
     */
    protected abstract View getTitleBar();


    /**
     * 绑定布局文件
     *
     * @return 布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected void initView(Bundle savedInstanceState) {
        setStatusBar();
    }

    protected abstract void initData();

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    protected int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String str) {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 设置layout前配置
     */
    protected void onBeforeSetContentView() {
        //设置昼夜主题
        initTheme();
        // 把actvity放到application栈中管理
//        AppManager.getAppManager().addActivity(this);
        // 无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // 设置竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 默认着色状态栏
//        setStatusBarColor();
    }

    /**
     * 设置主题
     */
    private void initTheme() {
//        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme);
    }




    /**
     * 退出当前Activity
     */
    public void finishActivity() {
//        AppManager.getAppManager().finishActivity(this);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销
        mBind.unbind();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    //修改状态栏
    protected void  setStatusBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorWhite));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//状态栏为白色 图标显示深色
        }




//        StatusBarUtil.setRootViewFitsSystemWindows(getActivity(),true);
//        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
//        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
//        if (!StatusBarUtil.setStatusBarDarkTheme(getActivity(), true)) {
//            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
//            //这样半透明+白=灰, 状态栏的文字能看得清
//            StatusBarUtil.setStatusBarColor(getActivity(),0xFFFFFFFF);
////            //设置状态栏透明
////            StatusBarUtil.setTranslucentStatus(getActivity());
//        }
    }
}

