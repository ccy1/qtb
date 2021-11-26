package com.example.ntb.ui.order.activity;

import android.app.DatePickerDialog;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.membermanagement.bean.JsonAddMember;
import com.example.ntb.ui.order.adapter.MemberLeagueAdapter;
import com.example.ntb.ui.order.adapter.MyOrderAdapter;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.TitleBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :我的订单
 */
public class MyOrderActivity extends BaseActivity implements BaseView {

    @BindView(R.id.titleBar)
    TitleBarLayout titleBar;

    @BindView(R.id.lv_my_order)
    ListView lv_my_order;

    @BindView(R.id.rb_date)
    RadioButton rb_date;

    @BindView(R.id.rb_account)
    RadioButton rb_account;

    @BindView(R.id.view_line)
    View view_line;

    @BindView(R.id.ll_show)
    LinearLayout ll_show;

    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private BasePresenter basePresenter = new BasePresenter(this,this);
    private List<JsonAddMember.DataBean> list = new ArrayList<>();
    private MyOrderAdapter myOrderAdapter;

    private MemberLeagueAdapter memberLeagueAdapter;
    private TextView tv_startTime,tv_endTime;
    private String startTime,endTime;

    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initData() {
        titleBar.setTitle("我的订单");
        myOrderAdapter = new MyOrderAdapter(this,list);
        lv_my_order.setAdapter(myOrderAdapter);

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
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.getMemberLeague;//获取成员列表
//        String Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/addMember";
        Map<String,Object> map = new HashMap<>();
        basePresenter.postRequesttoHead(this,Url,false,map,SPUtils.getSharedStringData(this,"token"),1);
    }

    @OnClick({R.id.iv_back,R.id.rb_date,R.id.rb_account})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.rb_date:
                rb_date.setChecked(true);
                rb_account.setChecked(false);
                showDatePopup();
                break;
            case R.id.rb_account:
                rb_date.setChecked(false);
                rb_account.setChecked(true);
                showAccountPopup();
                break;
        }
    }

    /**
     * 选择筛选用户
     */
    private void showAccountPopup() {

        final View view = View.inflate(this, R.layout.view_accunt, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        LinearLayout ll_main = view.findViewById(R.id.ll_main);
        ListView lv_member = view.findViewById(R.id.lv_member);
        memberLeagueAdapter = new MemberLeagueAdapter(this,list);
        lv_member.setAdapter(memberLeagueAdapter);
        ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        lv_member.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (JsonAddMember.DataBean item : list){
                    item.isSelected = false;
                }
                list.get(i).isSelected = true;
                memberLeagueAdapter.notifyDataSetChanged();
                rb_account.setText(list.get(i).name+"");
                popupWindow.dismiss();
            }
        });

        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            view_line.getGlobalVisibleRect(visibleFrame);
            int height = view_line.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(view_line, 0, 0);
        } else {
            popupWindow.showAsDropDown(view_line, 0, 0);
        }
    }

    /**
     * 选择筛选时间
     */
    private void showDatePopup() {

        final View view = View.inflate(this, R.layout.view_date, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        LinearLayout ll_main = view.findViewById(R.id.ll_main);
        ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        tv_startTime = view.findViewById(R.id.tv_startTime);
        tv_endTime = view.findViewById(R.id.tv_endTime);
        if (!TextUtils.isEmpty(startTime)){
            tv_startTime.setText(startTime);
        }
        tv_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                new DatePickerDialog(MyOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startTime = year + "-" + (month + 1) + "-" + dayOfMonth;
                        tv_startTime.setText(startTime);
                    }
                } ,calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        if (!TextUtils.isEmpty(endTime)){
            tv_endTime.setText(endTime);
        }

        tv_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                new DatePickerDialog(MyOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endTime = year + "-" + (month + 1) + "-" + dayOfMonth;
                        tv_endTime.setText(endTime);
                        if (TextUtils.isEmpty(startTime)){
                            rb_date.setText(endTime+"至"+endTime);
                        }else if (TextUtils.isEmpty(endTime)){
                            rb_date.setText(startTime+"至"+startTime);
                        }else {
                            rb_date.setText(startTime+"至"+endTime);
                        }
                    }
                } ,calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
                popupWindow.dismiss();
            }
        });
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            view_line.getGlobalVisibleRect(visibleFrame);
            int height = view_line.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(view_line, 0, 0);
        } else {
            popupWindow.showAsDropDown(view_line, 0, 0);
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
                        JsonAddMember jsonAddMember = gson.fromJson(json.trim(),new TypeToken<JsonAddMember>(){}.getType());{
                            if (jsonAddMember.code == 0){
                                if (jsonAddMember.data.size() != 0){
                                    ll_show.setVisibility(View.GONE);
                                    lv_my_order.setVisibility(View.VISIBLE);
                                    list.clear();
                                    for (JsonAddMember.DataBean item : jsonAddMember.data){
                                        list.add(item);
                                    }
                                    myOrderAdapter.notifyDataSetChanged();
                                }else {
                                    ll_show.setVisibility(View.VISIBLE);
                                    lv_my_order.setVisibility(View.GONE);
                                }
                            }else {
                                showShortToast(jsonAddMember.msg+"");
                            }
                        }
                    }
                }
                if (mRefreshLayout != null) {
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadMore();
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

    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getTimeCompareSize(String startTime, String endTime){
        int i=0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//年-月-日 时-分
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime()<date1.getTime()){
                i= 1;
            }else if (date2.getTime()==date1.getTime()){
                i= 2;
            }else if (date2.getTime()>date1.getTime()){
                //正常情况下的逻辑操作.
                i= 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("时间",i+"");
        return  i;
    }
}
