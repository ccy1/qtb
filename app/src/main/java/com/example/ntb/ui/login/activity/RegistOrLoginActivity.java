package com.example.ntb.ui.login.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.example.ntb.app.App;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.login.bean.JsonGetCode;
import com.example.ntb.ui.login.bean.JsonLogin;
import com.example.ntb.ui.util.AESUtil;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.LoadUtils;
import com.example.ntb.ui.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyn.vcview.VerificationCodeView;
import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/10/25
 * Describe :注册或者登录验证码
 */
public class RegistOrLoginActivity extends BaseActivity implements VerificationCodeView.OnCodeFinishListener ,BaseView {

    @BindView(R.id.tv_get_code)
    TextView tv_get_code;

    @BindView(R.id.verificationcodeview2)
    VerificationCodeView verificationcodeview2;

    private MyCountDownTimer timer;
    private String account;
    private BasePresenter basePresenter = new BasePresenter(this,this);
    private JsonLogin jsonLogin;

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initData() {
        verificationcodeview2.setOnCodeFinishListener(this);
        timer = new MyCountDownTimer(60 * 1000, 1000);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        getCodeFromServer(account);//获取验证码
    }
    /**
     * 获取验证码
     * @param account
     */
    private void getCodeFromServer(String account) {
        LoadUtils.showWaitProgress(this, "获取验证码中");
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getIdCode;
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNo",AESUtil.keyEncryptData(account)+"");//电话号码
        map.put("sellerNo", RequestURL.getNew_SellerNo());//当前登录商家
        map.put("sourceType",1);//来源渠道：0iosApp，1安卓app，2微信公众号，3小程序
        map.put("loginType",2);  // 操作类型(1注册,2登录，3修改密码，4重置密码)
        basePresenter.postRequest(this,Url,false,map,1);
    }

    @OnClick({R.id.tv_get_code})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_get_code:
                getCodeFromServer(account);
                break;
        }
    }

    @Override
    public void onTextChange(View view, String content) {

    }

    @Override
    public void onComplete(View view, String code) {
        Login(account,code);//注册或者登录
    }

    /**
     * 注册或者登录
     * @param account
     * @param code
     */
    private void Login(String account, String code) {
        LoadUtils.showWaitProgress(RegistOrLoginActivity.this, "正在为您登录，请稍后...");
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.vcodeLogin;
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNo",account);//电话号码
        map.put("sellerId", RequestURL.getNew_SellerId());//当前登录商家id
        map.put("codeNum",code);//当前登录验证码
        map.put("loginType",2);//操作类型(1注册,2登录，3修改密码，4重置密码)
        map.put("sourceType","1");//来源渠道：0iosApp，1安卓app，2微信公众号，3小程序
        basePresenter.postRequest(this,Url,false,map,2);
    }

    @Override
    public void resultSucess(final int type, final String json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (!TextUtils.isEmpty(json)){
                        Gson mGson = new Gson();
                        final JsonGetCode jsonGetCode = mGson.fromJson(json.trim().toString(), new TypeToken<JsonGetCode>() {
                        }.getType());
                        if (jsonGetCode.getCode() == 0){
                            showShortToast(jsonGetCode.getMsg()+"");
                            timer.start();
                        }else {
                            showShortToast(jsonGetCode.getMsg()+"");
                            timer.cancel();
                            tv_get_code.setText("获取验证码");
                            tv_get_code.setClickable(true);
                        }
                    }
                }else if (type == 2){
                    if (!TextUtils.isEmpty(json)){
                        Gson mGson = new Gson();
                        jsonLogin = mGson.fromJson(json.trim().toString(), new TypeToken<JsonLogin>() {
                        }.getType());
                        if (jsonLogin.getCode() == 0){
                            showShortToast(jsonLogin.getMsg());
                            saveUserData();//保存数据
                            EventBus.getDefault().post(new EventBlack(200));//登录成功
                            finish();
                        }else {
                            showShortToast(jsonLogin.getMsg()+"");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void resultFailure(final int type, final String Msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    timer.cancel();
                    showShortToast(Msg+"");
                }
            }
        });
    }

    /**
     * 保存数据
     */
    private void saveUserData() {
        App.getInstance().token = jsonLogin.getData().getToken();
        SPUtils.setSharedStringData(App.getInstance(), "token", jsonLogin.getData().getToken()+"");
        SPUtils.setSharedStringData(App.getInstance(), "PhoneNo", jsonLogin.getData().getMemberno()+"");
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (tv_get_code != null) {
                tv_get_code.setText("获取验证码");
                tv_get_code.setClickable(true);
            }
        }
        @Override
        public void onTick(long millisUntilFinished) {
            if (tv_get_code != null) {
                tv_get_code.setText(millisUntilFinished / 1000+"后重新获取验证码");
                tv_get_code.setClickable(false);
            }
        }
    }
}
