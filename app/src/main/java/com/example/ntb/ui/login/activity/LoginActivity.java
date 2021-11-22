package com.example.ntb.ui.login.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.ui.R;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.Utils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/10/25
 * Describe :登录页
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_ok)
    Button btn_ok;//登录

    @BindView(R.id.et_phone)
    EditText et_phone;//手机号

    @BindView(R.id.cb_checkBox)
    CheckBox cb_checkBox;

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        btn_ok.setEnabled(false);
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 11){
                    btn_ok.setBackgroundResource(R.drawable.btn_shape_sel_bg);
                    btn_ok.setEnabled(true);
                }else {
                    btn_ok.setBackgroundResource(R.drawable.btn_shape_un_bg);
                    btn_ok.setEnabled(false);
                }
            }
        });

        String phone = SPUtils.getSharedStringData(this,"PhoneNo");
        Utils.out("PhoneNo",phone+"");

        if (!TextUtils.isEmpty(phone)){
            et_phone.setText(phone+"");
        }
        String isChecked = SPUtils.getSharedStringData(this,"isChecked");
        if (!TextUtils.isEmpty(isChecked)){
            if (isChecked.equals("true")){
                cb_checkBox.setChecked(true);
            }else {
                cb_checkBox.setChecked(false);
            }
        }
    }

    @OnClick({R.id.btn_ok,R.id.tv_protocol})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                if (cb_checkBox.isChecked() == false){
                    showShortToast("请勾选同意服务协议!");
                }else {
                    SPUtils.setSharedStringData(LoginActivity.this,"isChecked","true");
                    Intent intent = new Intent(this,RegistOrLoginActivity.class);
                    intent.putExtra("account",et_phone.getText().toString());
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.tv_protocol:
//                Intent intent = new Intent(this,AgreeActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
