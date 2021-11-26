package com.example.ntb.ui.membermanagement.acticity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.home.bean.JsonBase;
import com.example.ntb.ui.membermanagement.adapter.MemberManagementAdapter;
import com.example.ntb.ui.membermanagement.bean.JsonAddMember;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :成员管理
 */
public class MemberManagementActivity extends BaseActivity implements BaseView {

    @BindView(R.id.lv_member)
    ListView lv_member;

    @BindView(R.id.tv_member_title)
    TextView tv_title;

    @BindView(R.id.ll_show)
    LinearLayout ll_show;

    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private BasePresenter basePresenter = new BasePresenter(this,this);
    private MemberManagementAdapter memberManagementAdapter;
    private List<JsonAddMember.DataBean> list = new ArrayList<>();

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_management;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        tv_title.setText("成员列表");
        memberManagementAdapter = new MemberManagementAdapter(this,list,onDeleteItemListenerClickListener);
        lv_member.setAdapter(memberManagementAdapter);

        mRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData();
            }
        });
    }

    @Override
    protected void getNetworkRequest() {
        getData();
    }

    private void getData(){
//        String Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/addMember";
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getMemberLeague;//获取成员列表
        Map<String,Object> map = new HashMap<>();
        basePresenter.postRequesttoHead(this,Url,false,map,SPUtils.getSharedStringData(this,"token"),1);
    }

    @OnClick({R.id.iv_member_back,R.id.iv_member_notice})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_member_back:
                finishActivity();
                break;
            case R.id.iv_member_notice:
                startActivity(new Intent(MemberManagementActivity.this,AddMemberActivity.class));
                break;
        }
    }
    MemberManagementAdapter.OnDeleteItemListenerClickListener onDeleteItemListenerClickListener = new MemberManagementAdapter.OnDeleteItemListenerClickListener() {
        @Override
        public void onItemListener(int position, List<JsonAddMember.DataBean> list) {
            Utils.out("删除了",list.get(position).name+"");
            String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.delMemberLeague;//删除成员
            Map<String,Object> map = new HashMap<>();
            map.put("id",list.get(position).id+"");
            basePresenter.postRequesttoHead(MemberManagementActivity.this,Url,false,map,SPUtils.getSharedStringData(MemberManagementActivity.this,"token"),2);
        }
    };

    @Override
    public void resultSucess(final int type, final String json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (!TextUtils.isEmpty(json)){
                        Gson gson = new Gson();
                        JsonAddMember jsonAddMember = gson.fromJson(json.trim(),new TypeToken<JsonAddMember>(){}.getType());{
                            if (jsonAddMember.code == 0){
                                if (jsonAddMember.data.size() != 0){
                                    ll_show.setVisibility(View.GONE);
                                    lv_member.setVisibility(View.VISIBLE);
                                    list.clear();
                                    for (JsonAddMember.DataBean item : jsonAddMember.data){
                                        list.add(item);
                                    }
                                    memberManagementAdapter.notifyDataSetChanged();
                                }else {
                                    ll_show.setVisibility(View.VISIBLE);
                                    lv_member.setVisibility(View.GONE);
                                }
                            }else {
                                ToastUtil.showToast(MemberManagementActivity.this,jsonAddMember.msg+"");
                            }
                        }
                    }
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                }else if (type == 2){
                    if (!TextUtils.isEmpty(json)){
                        Gson gson = new Gson();
                        JsonBase jsonBase = gson.fromJson(json.trim(),new TypeToken<JsonBase>(){}.getType());{
                            if (jsonBase.code == 0){
                                showShortToast(jsonBase.msg+"");
                                getData();
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
                if (mRefreshLayout != null) {
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadMore();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Subscribe
    public void EventBlacks(EventBlack event) {
        if (event.getCode() == 4){
            Utils.out("MemberManagementActivity","收到添加成员成功的通知");
            getData();
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}