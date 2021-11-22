package com.example.ntb.ui.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ntb.app.App;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.home.adapter.ChargingListAdapter;
import com.example.ntb.ui.home.bean.JsonchargingList;
import com.example.ntb.ui.home.bean.JsongetMemberInfo;
import com.example.ntb.ui.login.activity.LoginActivity;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ccy.
 * Date: 2021/10/20
 * Describe ：首页
 */
public class HomePageFragment extends Fragment implements BaseView,View.OnClickListener {

    private ListView lv_home;
    private LinearLayout ll_home;
    private SmartRefreshLayout mRefreshLayout;
    private ImageView iv_scan;
    private TextView tv,tv_login,tv_memberName,tv_accountBalance,tv_dailyPower,tv_monthPower,tv_monthConsumption;

    private BasePresenter basePresenter = new BasePresenter(getActivity(),this);

    private ChargingListAdapter chargingListAdapter;
    private List<JsonchargingList.DataBeanX.DataBean> list = new ArrayList<>();
    private int index = 1;
    private int tag = 0;
    private DecimalFormat df = new DecimalFormat("######0.00#");
    private String token;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    tag++;
                    getMemberChargingList();
                    getMember();//获取会员信息
                    break;
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_home, null);
        initView(view);
        token = SPUtils.getSharedStringData(getActivity(),"token");
        if (!TextUtils.isEmpty(token)){
            getMemberChargingList();//获取充电中list
            getMember();//获取会员信息
            ll_home.setOnClickListener(null);
        }else {
            ll_home.setVisibility(View.VISIBLE);
            ll_home.setOnClickListener(this);
        }
        return  view;
    }

    /**
     * 初始化view
     * @param view
     */
    private void initView(View view) {

        EventBus.getDefault().register(this);
        lv_home =(ListView) view.findViewById(R.id.lv_home);
        ll_home =(LinearLayout) view.findViewById(R.id.ll_home);
        ll_home.setOnClickListener(this);
        tv =(TextView) view.findViewById(R.id.tv);
        tv_login =(TextView) view.findViewById(R.id.tv_login);
        iv_scan =(ImageView) view.findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(this);
        tv_memberName =(TextView) view.findViewById(R.id.tv_memberName);
        tv_accountBalance =(TextView) view.findViewById(R.id.tv_accountBalance);
        tv_dailyPower =(TextView) view.findViewById(R.id.tv_dailyPower);
        tv_monthPower =(TextView) view.findViewById(R.id.tv_monthPower);
        tv_monthConsumption =(TextView) view.findViewById(R.id.tv_monthConsumption);

        mRefreshLayout =(SmartRefreshLayout) view.findViewById(R.id.mSmartRefreshLayout);
        chargingListAdapter = new ChargingListAdapter(getActivity(),list,onItemListener);
        lv_home.setAdapter(chargingListAdapter);

        mRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                index = 1;
                getMemberChargingList();
                getMember();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                index = index + 1;
                getMemberChargingList();
            }
        });
    }

    /**
     * 获取会员信息
     */
    private void getMember() {
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getMemberReportInfo;//获取会员信息
//        String Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/getMemberInfo";
        JSONObject obj = new JSONObject();
        basePresenter.jsonRequesttoHead(getActivity(),Url,false,obj.toString(),token+"",2);
    }

    /**
     * 获取充电中list
     */
    private void getMemberChargingList() {
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getMemberChargingList;//获取充电中list
//        String Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/api/chargingList";

        JSONObject obj = new JSONObject();
        try {
            obj.put("current",index);
            obj.put("pageSize","12");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        basePresenter.jsonRequesttoHead(getActivity(),Url,false,obj.toString(),token+"",1);
    }

    @Override
    public void resultSucess(final int type, final String json) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    Gson gson = new Gson();
                    JsonchargingList jsonPersonalComment = gson.fromJson(json.trim(),new TypeToken<JsonchargingList>(){}.getType());
                    if (jsonPersonalComment.code == 0){
                        if (jsonPersonalComment.data.data.size() != 0){
                            if (index == 1){
                                list.clear();
                            }
                            if (jsonPersonalComment.data.pages == index){
                                mRefreshLayout.setEnableLoadMore(false);
                            }else {
                                mRefreshLayout.setEnableLoadMore(true);
                            }
                            for (JsonchargingList.DataBeanX.DataBean item : jsonPersonalComment.data.data){
                                list.add(item);
                            }
                            chargingListAdapter.notifyDataSetChanged();
                            ll_home.setVisibility(View.GONE);
                            lv_home.setVisibility(View.VISIBLE);
                            tv.setVisibility(View.GONE);
                        }else {
                            ll_home.setVisibility(View.VISIBLE);
                            lv_home.setVisibility(View.GONE);
                            tv.setVisibility(View.GONE);
                            tv_login.setText("暂无内容~");
                            tv_login.setTextColor(Color.parseColor("#FF707485"));
                        }
                    }
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
//                    if (handler != null){
//                        handler.sendEmptyMessageDelayed(1, 60 * 1000);
//                    }
                }else if (type == 2){
                    if (!TextUtils.isEmpty(json)){
                        Gson gson = new Gson();
                        JsongetMemberInfo jsongetMemberInfo = gson.fromJson(json.trim(),new TypeToken<JsongetMemberInfo>(){}.getType());
                        if (jsongetMemberInfo.code == 0){
                            tv_memberName.setText(jsongetMemberInfo.data.memberName+"");
                            String str = df.format(Double.valueOf(jsongetMemberInfo.data.accountBalance));
                            tv_accountBalance.setText(str);
                            tv_dailyPower.setText(jsongetMemberInfo.data.dailyPower+"kw.h");
                            tv_monthPower.setText(jsongetMemberInfo.data.monthPower+"kw.h");
                            tv_monthConsumption.setText("￥"+df.format(Double.valueOf(jsongetMemberInfo.data.monthConsumption)));
                        }
                    }
                }
            }
        });
    }

    @Override
    public void resultFailure(final int type, final String Msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                        mRefreshLayout.finishRefresh(false);
                        ll_home.setVisibility(View.GONE);
                    }
                    ToastUtil.showToast(getActivity(),Msg+"");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_scan:
//                Intent intent = new Intent(getActivity(),LoginActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_home:
                Intent intent01 = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent01);
                break;
        }
    }


    ChargingListAdapter.OnItemListener onItemListener = new ChargingListAdapter.OnItemListener() {
        @Override
        public void mOnItemListener(final int position, final List<JsonchargingList.DataBeanX.DataBean> data) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showToast(getActivity(),data.get(position).soc+"");
                }
            });
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Subscribe
    public void EventBlacks(EventBlack event) {
        if (event.getCode() == 200){//登录成功
            Utils.out("HomePage","首页收到登录成功的通知");
            token = SPUtils.getSharedStringData(getActivity(),"token");
            getMemberChargingList();
            getMember();
            ll_home.setOnClickListener(null);
        }else if (event.getCode() == 1){//退出登录
            ll_home.setVisibility(View.VISIBLE);
            Utils.out("HomePage","首页收到退出成功的通知");
            tv_memberName.setText("未登录");
            tv_accountBalance.setText("0.00");
            tv_dailyPower.setText("0kw.h");
            tv_monthPower.setText("0kw.h");
            tv_monthConsumption.setText("￥0.00");
            handler = null;
            token = null;
            ll_home.setOnClickListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroyView();
    }
}