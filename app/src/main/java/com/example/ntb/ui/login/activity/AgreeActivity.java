package com.example.ntb.ui.login.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.ntb.base.BaseActivity;
import com.example.ntb.ui.R;
import com.example.ntb.ui.util.TitleBarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/10/25
 * Describe :隐私协议
 */
public class AgreeActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.titleBar)
    TitleBarLayout titleBar;

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agree;
    }

    @Override
    protected void initData() {

        titleBar.setBackOnClick(this);
        titleBar.setTitle("服务协议");

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setDefaultTextEncodingName("GBK");//避免乱码
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);//使能JavaScript
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webView.loadUrl("http://8.134.61.214/miniappH5/jw/privacyPolicy.html");
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
