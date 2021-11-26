package com.example.ntb.ui.my.activity;

import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ntb.app.App;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.my.adapter.RecordsAdapter;
import com.example.ntb.ui.my.bean.JsonQuerRevenue;
import com.example.ntb.ui.my.bean.PersonInfo;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.TitleBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import static com.example.ntb.ui.util.StatusBarUtil.setAndroidNativeLightStatusBar;
import static com.example.ntb.ui.util.StatusBarUtil.setStatusBarColor;

/**
 * Created by ccy.
 * Date: 2021/11/22
 * Describe :账户管理
 */
public class AccountManagementActivity extends BaseActivity implements BaseView {

    @BindView(R.id.titleBar)
    TitleBarLayout titleBar;

    @BindView(R.id.lv_records)
    ListView lv_records;

    @BindView(R.id.tv_money)
    TextView tv_money;

    @BindView(R.id.ll_show)
    LinearLayout ll_show;

    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private BasePresenter basePresenter = new BasePresenter(this,this);
    private RecordsAdapter recordsAdapter;
    private List<JsonQuerRevenue.DataBean.RevenuelistBean> list = new ArrayList<>();

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_management;
    }

    @Override
    protected void initData() {

        titleBar.setmTitleColor(Color.parseColor("#FFFFFF"));
        titleBar.setmBack(getResources().getDrawable(R.mipmap.new_back_four));
        setStatusBarColor(AccountManagementActivity.this,R.color.colorTheme);
        setAndroidNativeLightStatusBar(AccountManagementActivity.this,false);//修改状态栏文字颜色
        if (titleBar != null){
            if (Build.VERSION.SDK_INT >= 20.) {
                int statusBarHeight = getStatusBarHeight(this);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) titleBar.getLayoutParams();
                lp.setMargins(0, lp.topMargin + statusBarHeight, 0, 0);
                titleBar.setLayoutParams(lp);
            }
        }
        titleBar.setTitle("账户管理");
        titleBar.setBackOnClick(this);

        recordsAdapter = new RecordsAdapter(this,list);
        lv_records.setAdapter(recordsAdapter);

        PersonInfo personInfo = App.getInstance().getPersonInfoTwo();
        if (personInfo!=null){
            DecimalFormat df1 = new DecimalFormat("0.00");
            String str = df1.format(Double.valueOf(personInfo.data.accountBalance)/100);
            tv_money.setText(str);
        }

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
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.querRevenue;//收支记录（账户流水）
        Map<String,Object> map = new HashMap<>();
        basePresenter.postRequesttoHead(this,Url,false,map,SPUtils.getSharedStringData(AccountManagementActivity.this,"token"),1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
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
                        Gson gson = new Gson();
                        JsonQuerRevenue jsonReader = gson.fromJson(json.trim(),new TypeToken<JsonQuerRevenue>(){}.getType());
                        if (jsonReader.code == 0){
                            if (jsonReader.data.size != 0){
                                lv_records.setVisibility(View.VISIBLE);
                                ll_show.setVisibility(View.GONE);
                                list.clear();
                                for (JsonQuerRevenue.DataBean.RevenuelistBean item : jsonReader.data.revenuelist){
                                    list.add(item);
                                }
                                recordsAdapter.notifyDataSetChanged();
                            }else {
                                lv_records.setVisibility(View.GONE);
                                ll_show.setVisibility(View.VISIBLE);
                            }
                        }else {
                            ll_show.setVisibility(View.VISIBLE);
                            lv_records.setVisibility(View.GONE);
                            showShortToast(jsonReader.msg+"");
                        }
                    }
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
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
}
