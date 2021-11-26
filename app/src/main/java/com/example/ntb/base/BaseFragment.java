package com.example.ntb.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ntb.ui.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ccy.
 * Date: 2021/10/21
 * Describe :
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener ,View.OnTouchListener{

    /**
     * 主布局文件
     */
    protected View rootView;
    /**
     * 注解
     */
    private Unbinder mBind;

    protected Handler mHandler;

    private boolean isExtend = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(getLayoutResource(), container, false);
        mBind = ButterKnife.bind(this, rootView);
        initView();
        initView(savedInstanceState);
        initData();
        //延迟获取网络数据
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                getNetworkRequest();
            }
        }, 300);
        return rootView;
    }

    /**
     * 获取布局文件
     */
    protected abstract int getLayoutResource();

    /**
     * 初始化view
     */
    protected void initView() {

    }
    protected void initView(Bundle savedInstanceState) {
    }
    /**
     *加载各种数据
     */
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
     * 延迟500毫秒加载网络数据，你自己看着办
     */
    protected void getNetworkRequest() {
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (isExtend) {
            if (Build.VERSION.SDK_INT >= 20.) {
                if (rootView != null) {
                    if (hidden) {
                        rootView.setFitsSystemWindows(false);
                    } else {
                        rootView.setFitsSystemWindows(true);
                    }
                    rootView.requestApplyInsets();
                }
            }
        }
        super.onHiddenChanged(hidden);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onPause() {
        if (isExtend) {
            if (Build.VERSION.SDK_INT >= 20.) {
                if (rootView != null) {
                    rootView.setFitsSystemWindows(false);
                    rootView.requestApplyInsets();
                }
            }
        }
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onResume() {
        if (isExtend) {
            if (Build.VERSION.SDK_INT >= 20.) {
                if (rootView != null) {
                    rootView.setFitsSystemWindows(true);
                    rootView.requestApplyInsets();
                }
            }
        }
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind.unbind();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


    /**
     * 修改显示的内容 不会重新加载
     **/
    public void switchContentAndAddToBackStack(Fragment to, String fragmentTag) {
        Fragment mContent = this;
        if (mContent != to) {

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right,
                    R.anim.slide_out_right);
            transaction.addToBackStack(fragmentTag);
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mContent).add(R.id.set, to, fragmentTag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
    /**
     * 退出当前Fragment
     */
    public void finishFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * 退出当前Fragment以上的所有Fragment(包括自己)
     */
    public void finishFragment(String tag) {
//        getActivity().getSupportFragmentManager().popBackStack(tag, 1);
//        getFragmentManager().popBackStackImmediate();
        getFragmentManager().popBackStack(tag, 1);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // 拦截触摸事件，防止泄露下去
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

}

