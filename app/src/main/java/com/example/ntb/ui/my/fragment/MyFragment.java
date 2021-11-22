package com.example.ntb.ui.my.fragment;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ntb.app.App;
import com.example.ntb.base.BaseFragment;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.login.activity.LoginActivity;
import com.example.ntb.ui.my.activity.AccountManagementActivity;
import com.example.ntb.ui.my.bean.JsonToken;
import com.example.ntb.ui.my.bean.Jsonlogout;
import com.example.ntb.ui.my.bean.PersonInfo;
import com.example.ntb.ui.util.DialogUtils;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.LoadUtils;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.UserUtils;
import com.example.ntb.ui.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/10/20
 * Describe :我的界面
 */
public class MyFragment extends BaseFragment implements BaseView {

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_money)
    TextView tv_money;

    @BindView(R.id.ll_switchUser)
    LinearLayout ll_switchUser;

    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.ll_memberManagement)
    LinearLayout ll_memberManagement;//成员管理

    private String token;
    private BasePresenter basePresenter = new BasePresenter(getContext(),this);

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @Override
    protected void getNetworkRequest() {

        token = SPUtils.getSharedStringData(getContext(),"token");
        Utils.out("token",token+"");
        if (!TextUtils.isEmpty(token)){
            getMemberInfo();//获取会员信息
        }
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        mSmartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getMemberInfo();
            }
        });
    }

    @OnClick({R.id.ll_switchUser})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_switchUser://账户管理
//                if (!TextUtils.isEmpty(token)){
//                    showDialog();//退出登录
//                }else {
//                    Intent intent = new Intent(getActivity(),LoginActivity.class);
//                    startActivity(intent);
//                }
                Intent intent = new Intent(getContext(),AccountManagementActivity.class);
                startActivity(intent);

                break;
        }
    }

    /**
     * 退出登录
     */
    private void showDialog() {
        DialogUtils dialogUtils = new DialogUtils();
        dialogUtils.showDialog(getActivity(), "确定退出登录？", "", "确定", "取消");
        dialogUtils.setListener(new DialogUtils.OnDialogClickListener() {
            @Override
            public void onSave() {
                exitLoginTwo();//退出登录
            }

            @Override
            public void onCancel() {

            }
        });
    }
    /**
     * 退出登录
     */
    private void exitLoginTwo() {
        LoadUtils.showWaitProgress(getActivity(),"正在退出登录,请稍后...");
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.logoutTwo;//退出登录
        Map<String, Object> map = new HashMap<>();
        basePresenter.postRequesttoHead(getActivity(),Url,false,map,token+"",3);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Subscribe
    public void EventBlacks(EventBlack event) {
        if (event.getCode() == 200){//我的页面收到登录成功的通知
            token = SPUtils.getSharedStringData(getContext(),"token");
            Utils.out("My","我的页面收到登录成功的通知"+"====token===="+token);
            getMemberInfo();//获取会员信息
        }
    }

    /**
     * 获取会员信息
     */
    private void getMemberInfo() {
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getMemberInfo;// 我的信息页
        Map<String,Object> map = new HashMap<>();
        basePresenter.postRequesttoHead(getContext(),Url,false,map,token+"",1);
    }
    /**
     * token过期重新获取
     */
    private void getToken() {
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getToken;// 获取移动端的token
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNo",SPUtils.getSharedStringData(getContext(),"PhoneNo"));
        map.put("sellerNo",RequestURL.sellerNo);
        map.put("sourceType",1);
        basePresenter.postRequesttoHead(getActivity(),Url,false,map,token+"",2);
    }
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (basePresenter != null){
            basePresenter = null;
        }
    }

    @Override
    public void resultSucess(final int type, final String json) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (!TextUtils.isEmpty(json)){
                        Gson gson = new Gson();
                        PersonInfo personInfo = gson.fromJson(json, PersonInfo.class);
                        if (personInfo.code == 0){
                            App.getInstance().setPersonInfoTwo(personInfo);
                            tv_name.setText(personInfo.data.name+"");
                            DecimalFormat df1 = new DecimalFormat("0.00");
                            String str = df1.format(Double.valueOf(personInfo.data.accountBalance)/100);
                            tv_money.setText(str);
                            if (personInfo.data.memberType == 1){//成员
                                ll_memberManagement.setVisibility(View.GONE);
                            }else if (personInfo.data.memberType == 2){//车队
                                ll_memberManagement.setVisibility(View.VISIBLE);
                            }else {
                                ll_memberManagement.setVisibility(View.GONE);
                            }

                        }else if (personInfo.code == 2){//token过期了
                            getToken();
                        }
                    }
                    if (mSmartRefreshLayout != null) {
                        mSmartRefreshLayout.finishRefresh();
                        mSmartRefreshLayout.finishLoadMore();
                    }
                }else if (type == 2){
                    if (!TextUtils.isEmpty(json)){
                        Gson mGson = new Gson();
                        JsonToken jsonToken = mGson.fromJson(json.trim().toString(), new TypeToken<JsonToken>() {
                        }.getType());
                        if (jsonToken.getCode() == 0){
                            SPUtils.setSharedStringData(App.getInstance(), "token", jsonToken.getData()+"");
                            EventBus.getDefault().post(new EventBlack(200));
                        }
                    }
                }else if (type == 3){
                    if (!TextUtils.isEmpty(json)){
                        Gson mGson = new Gson();
                        final Jsonlogout jsonlogout = mGson.fromJson(json.trim().toString(), new TypeToken<Jsonlogout>() {
                        }.getType());
                        if (jsonlogout.code == 0){
                            ToastUtil.showToast(getActivity(),jsonlogout.msg+"");
                            UserUtils.clearUserStatus();
                            tv_name.setText("未登录");
                            tv_money.setText("0.00");
                            EventBus.getDefault().post(new EventBlack(1));//退出登录
                            token = null;
                        }else {
                            ToastUtil.showToast(getActivity(),jsonlogout.msg+"");
                            UserUtils.clearUserStatus();
                            tv_name.setText("未登录");
                            tv_money.setText("0.00");
                            EventBus.getDefault().post(new EventBlack(1));//退出登录
                            token = null;
                        }
                    }
                }
            }
        });
    }
    @Override
    public void resultFailure(int type, String Msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSmartRefreshLayout != null) {
                    mSmartRefreshLayout.finishRefresh();
                    mSmartRefreshLayout.finishLoadMore();
                    mSmartRefreshLayout.finishRefresh(false);
                }
            }
        });
    }
}
