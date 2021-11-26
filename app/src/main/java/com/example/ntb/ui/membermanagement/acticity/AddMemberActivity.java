package com.example.ntb.ui.membermanagement.acticity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.home.bean.JsonBase;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.greenrobot.eventbus.EventBus;;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :添加成员
 */
public class AddMemberActivity extends BaseActivity implements BaseView {

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.iv_add_member)
    ImageView iv_add_member;

    @BindView(R.id.ll_add_member_two)
    LinearLayout ll_add_member_two;

    @BindView(R.id.ll_add_member_three)
    LinearLayout ll_add_member_three;

    @BindView(R.id.et_province)
    EditText et_province;

    @BindView(R.id.et_province_two)
    EditText et_province_two;

    @BindView(R.id.et_province_three)
    EditText et_province_three;

    @BindView(R.id.et_letter)
    EditText et_letter;

    @BindView(R.id.et_letter_two)
    EditText et_letter_two;

    @BindView(R.id.et_letter_three)
    EditText et_letter_three;

    @BindView(R.id.et_number)
    EditText et_number;

    @BindView(R.id.et_number_two)
    EditText et_number_two;

    @BindView(R.id.et_number_three)
    EditText et_number_three;

    private int tag = 0;//0：默认显示    1：显示第二项车牌输入框    2：显示第三项车牌输入框

    private BasePresenter basePresenter = new BasePresenter(this,this);

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_member;
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.iv_member_back,R.id.tv_save,R.id.iv_add_member,R.id.iv_add_member_two,R.id.iv_add_member_three,R.id.iv_delete_member_two,R.id.iv_delete_member_three})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_member_back:
                finish();
                break;
            case R.id.tv_save:
                saveMember();
                break;
            case R.id.iv_add_member:
                ll_add_member_two.setVisibility(View.VISIBLE);
                tag = 1;
                break;
            case R.id.iv_add_member_two:
                ll_add_member_three.setVisibility(View.VISIBLE);
                tag = 2;
                break;
            case R.id.iv_delete_member_two:
                ll_add_member_two.setVisibility(View.GONE);
                et_province_two.setText("");
                et_letter_two.setText("");
                et_number_two.setText("");
                tag = 0;
                break;
            case R.id.iv_delete_member_three:
                ll_add_member_three.setVisibility(View.GONE);
                et_province_three.setText("");
                et_letter_three.setText("");
                et_number_three.setText("");
                tag = 1;
                break;
        }
    }

    private void saveMember() {

        if (TextUtils.isEmpty(et_phone.getText().toString())){
            showShortToast("请填写手机号");
            return;
        }
        if (TextUtils.isEmpty(et_name.getText().toString())){
            showShortToast("请填写名称");
            return;
        }
//        if (tag == 0){
//           if (TextUtils.isEmpty(et_province.getText().toString())||TextUtils.isEmpty(et_letter.getText().toString())||TextUtils.isEmpty(et_number.getText().toString())){
//               showShortToast("请填写正确的车牌号");
//               return;
//           }
//        }else if (tag == 1){
//            if (TextUtils.isEmpty(et_province.getText().toString())||TextUtils.isEmpty(et_letter.getText().toString())||TextUtils.isEmpty(et_number.getText().toString())||
//                    TextUtils.isEmpty(et_province_two.getText().toString())||TextUtils.isEmpty(et_letter_two.getText().toString())||TextUtils.isEmpty(et_number_two.getText().toString())){
//                showShortToast("请填写正确的车牌号");
//                return;
//            }
//        }else if (tag == 2){
//            if (TextUtils.isEmpty(et_province.getText().toString())||TextUtils.isEmpty(et_letter.getText().toString())||TextUtils.isEmpty(et_number.getText().toString())||
//                    TextUtils.isEmpty(et_province_two.getText().toString())||TextUtils.isEmpty(et_letter_two.getText().toString())||TextUtils.isEmpty(et_number_two.getText().toString())||
//                    TextUtils.isEmpty(et_province_three.getText().toString())||TextUtils.isEmpty(et_letter_three.getText().toString())||TextUtils.isEmpty(et_number_three.getText().toString())){
//                showShortToast("请填写正确的车牌号");
//                return;
//            }
//        }
        if (TextUtils.isEmpty(et_province.getText().toString())||TextUtils.isEmpty(et_letter.getText().toString())||TextUtils.isEmpty(et_number.getText().toString())){
            showShortToast("请填写正确的车牌号");
            return;
        }

        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.saveMemberLeague;// 添加成员
        Map<String,Object> map = new HashMap<>();
        map.put("telephone",et_phone.getText().toString()+"");
        map.put("name",et_name.getText().toString()+"");
        map.put("plateNo",et_province.getText().toString()+et_letter.getText().toString()+et_number.getText().toString()+"");
        basePresenter.postRequesttoHead(this,Url,false,map,SPUtils.getSharedStringData(this,"token"),1);
    }

    @Override
    public void resultSucess(final int type, final String json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (!TextUtils.isEmpty(json)){
                        Gson gson = new Gson();
                        JsonBase jsonBase = gson.fromJson(json.trim(),new TypeToken<JsonBase>(){}.getType());
                        if (jsonBase.code == 0){
                            EventBus.getDefault().post(new EventBlack(4));//添加成员成功
                            showShortToast(jsonBase.msg+"");
                            finish();
                        }else {
                            showShortToast(jsonBase.msg+"");
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
}
