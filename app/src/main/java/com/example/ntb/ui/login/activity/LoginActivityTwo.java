package com.example.ntb.ui.login.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ntb.app.App;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.login.bean.JsonLogin;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.LoadUtils;
import com.example.ntb.ui.util.SPUtils;
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
 * Describe : 登录
 */
public class LoginActivityTwo extends BaseActivity implements BaseView {

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_passWord)
    EditText et_passWord;

    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.cb_passWord)
    CheckBox cb_passWord;

    @BindView(R.id.cb_checkBox)
    CheckBox cb_checkBox;

    private BasePresenter basePresenter = new BasePresenter(this,this);
    private JsonLogin jsonLogin;

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_two;
    }

    @Override
    protected void initData() {

        String isChecked = SPUtils.getSharedStringData(this,"isChecked");
        if (!TextUtils.isEmpty(isChecked)){
            if (isChecked.equals("true")){
                cb_checkBox.setChecked(true);
            }else {
                cb_checkBox.setChecked(false);
            }
        }
        String PhoneNo = SPUtils.getSharedStringData(this,"PhoneNo");
        if (!TextUtils.isEmpty(PhoneNo)){
            et_phone.setText(PhoneNo+"");
        }

        btn_ok.setEnabled(false);
        et_passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(et_phone.getText().toString())&&et_phone.getText().toString().length() == 11){
                    if (!TextUtils.isEmpty(editable.toString())){
                        btn_ok.setBackgroundResource(R.drawable.btn_shape_sel_bg);
                        btn_ok.setEnabled(true);
                    }else {
                        btn_ok.setBackgroundResource(R.drawable.btn_shape_un_bg);
                        btn_ok.setEnabled(false);
                    }
                }
                filterChinese(et_passWord);
            }
        });

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

    @OnClick({R.id.btn_ok})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                if (cb_checkBox.isChecked() == false){
                    showShortToast("请勾选同意服务协议!");
                }else {
                    LoadUtils.showWaitProgress(LoginActivityTwo.this, "正在为您登录，请稍后...");
                    SPUtils.setSharedStringData(LoginActivityTwo.this,"isChecked","true");
                    String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.login+"";//登录
                    Map<String, Object> map = new HashMap<>();
                    map.put("phoneNo",et_phone.getText().toString());//电话号码
                    map.put("sellerId", RequestURL.sellerId);//当前登录商家id
                    map.put("password",md5Java(et_passWord.getText().toString()));//当前登录密密码
                    map.put("loginType",2);//操作类型(1注册,2登录，3修改密码，4重置密码)
                    map.put("sourceType","1");//来源渠道：0iosApp，1安卓app，2微信公众号，3小程序
                    basePresenter.postRequest(LoginActivityTwo.this,Url,false,map,1);
                }
                break;
        }
    }

    @Override
    public void resultSucess(final int type, final String json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
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
    public void resultFailure(int type, final String Msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShortToast(Msg+"");
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

    public static void filterChinese(TextView v) {
        v.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (null != source && isChinese(source.toString())) return "";
                return source;
            }
        }});
    }
    // 完整的判断中文汉字和符号
    @SuppressWarnings("unused")
    private static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
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


