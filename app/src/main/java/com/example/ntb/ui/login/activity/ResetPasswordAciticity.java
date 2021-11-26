package com.example.ntb.ui.login.activity;

import android.os.CountDownTimer;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.home.bean.JsonBase;
import com.example.ntb.ui.login.bean.JsonGetCode;
import com.example.ntb.ui.util.AESUtil;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.LoadUtils;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.TitleBarLayout;
import com.example.ntb.ui.util.UserUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/11/22
 * Describe :重置密码
 */
public class ResetPasswordAciticity extends BaseActivity implements BaseView {

    @BindView(R.id.titleBar)
    TitleBarLayout titleBar;

    @BindView(R.id.cb_passWord)
    CheckBox cb_passWord;

    @BindView(R.id.et_verificationCode)
    EditText et_verificationCode;

    @BindView(R.id.tv_get_code)
    TextView tv_get_code;

    @BindView(R.id.et_passWord)
    EditText et_passWord;

    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.et_phone)
    EditText et_phone;

    private MyCountDownTimer timer;

    private BasePresenter basePresenter = new BasePresenter(this,this);

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void initData() {
        titleBar.setTitle("重置密码");
        titleBar.setBackOnClick(this);
        timer = new MyCountDownTimer(60 * 1000, 1000);

        cb_passWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    et_passWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    et_passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                // 切换后光标位于文本末尾
                Spannable spanText = et_passWord.getText();
                if (spanText != null) {
                    Selection.setSelection(spanText, spanText.length());
                }
            }
        });
    }

    @OnClick({R.id.btn_ok,R.id.tv_get_code})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(et_phone.getText().toString())||et_phone.getText().toString().length() != 11){
                    showShortToast("手机号有误");
                }else {
                    getCodeFromServer();//获取验证码
                }
                break;
            case R.id.btn_ok:
                if (TextUtils.isEmpty(et_phone.getText().toString())||et_phone.getText().toString().length() != 11) {
                    showShortToast("手机号有误");
                    return;
                }
                if (TextUtils.isEmpty(et_verificationCode.getText().toString())) {
                    showShortToast("请填写验证码");
                    return;
                }
                if (TextUtils.isEmpty(et_passWord.getText().toString())) {
                    showShortToast("请填写密码");
                    return;
                }
                setPassword();
                break;
        }
    }

    /**
     * 重置密码
     */
    private void setPassword() {
        LoadUtils.showWaitProgress(this, "重置密码中,请稍后...");
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.restPassword;//重置密码
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNo",et_phone.getText().toString()+"");//电话号码
        map.put("password",md5Java(et_passWord.getText().toString()));//密码
        map.put("sellerNo", RequestURL.getNew_SellerNo());//当前登录商家
        map.put("sellerId",RequestURL.getNew_SellerId());//商家id
        map.put("sourceType",1);//来源渠道：0iosApp，1安卓app，2微信公众号，3小程序
        map.put("loginType",4);  // 操作类型(1注册,2登录，3修改密码，4重置密码)
        map.put("codeNum",et_verificationCode.getText().toString()+"");//验证码
        basePresenter.postRequesttoHead(this,Url,false,map,SPUtils.getSharedStringData(this,"token"),2);
    }

    /**
     * 获取验证码
     */
    private void getCodeFromServer() {
        LoadUtils.showWaitProgress(this, "获取验证码中,请稍后...");
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getIdCode;
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNo",AESUtil.keyEncryptData(et_phone.getText().toString())+"");//电话号码
        map.put("sellerNo", RequestURL.getNew_SellerNo());//当前登录商家
        map.put("sourceType",1);//来源渠道：0iosApp，1安卓app，2微信公众号，3小程序
        map.put("loginType",4);  // 操作类型(1注册,2登录，3修改密码，4重置密码)
        basePresenter.postRequest(this,Url,false,map,1);
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
                        Gson gson = new Gson();
                        JsonBase jsonBase = gson.fromJson(json.trim(),new TypeToken<JsonBase>(){}.getType());{
                            if (jsonBase.code == 0){
                                showShortToast(jsonBase.msg+"");
                                EventBus.getDefault().post(new EventBlack(1));//退出登录
                                UserUtils.clearUserStatus();
                                finish();
                            }else {
                                showShortToast(jsonBase.msg+"");
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void resultFailure(int type, final String Msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShortToast(Msg+"");
            }
        });
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

    /**
     * Java中的md5
     * @param content 输入的值
     * @return 输出md5加密后的值
     */
    public static String md5Java(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append(0);
            }
            hex.append(Integer.toHexString(b & 0xff));
        }

        return hex.toString();
    }
}
