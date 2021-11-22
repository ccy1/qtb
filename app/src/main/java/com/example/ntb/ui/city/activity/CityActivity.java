package com.example.ntb.ui.city.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.api.navi.model.NaviLatLng;
import com.example.ntb.app.App;
import com.example.ntb.base.BaseActivity;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.adapter.CityAdapter;
import com.example.ntb.ui.city.adapter.ResultAdapter;
import com.example.ntb.ui.city.bean.Areas;
import com.example.ntb.ui.city.bean.CityEvent;
import com.example.ntb.ui.city.bean.CityInfo;
import com.example.ntb.ui.city.bean.LocalAreasInfo;
import com.example.ntb.ui.city.bean.LocateState;
import com.example.ntb.ui.db.DaoSession;
import com.example.ntb.ui.util.CharacterParser;
import com.example.ntb.ui.util.LoadUtils;
import com.example.ntb.ui.util.SideLetterBar;
import com.example.ntb.ui.util.TitleBarLayout;
import com.example.ntb.ui.util.Utils;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :选择城市
 */
public class CityActivity extends BaseActivity implements BaseView {

    @BindView(R.id.titleBar)
    TitleBarLayout mTitleBar;//标题栏

    @BindView(R.id.tv_city)
    TextView tv_city;//城市

    @BindView(R.id.ll_empty)
    RelativeLayout mViewEmpty;//没数据显示

    @BindView(R.id.tv_prompt)
    TextView tv_prompt;//没数据显示

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ViewGroup emptyView;
    private CityAdapter cityAdapter;
    private ResultAdapter mResultAdapter;
    private List<Areas> mResultCities;
    private ArrayList<Areas> areasArrayList = new ArrayList<>();
    private CharacterParser characterParser;//汉字转换成拼音的类
    private boolean isHome;//选择的城市
    private BasePresenter basePresenter = new BasePresenter(this,this);
    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city;
    }

    @Override
    protected void initData() {
        mTitleBar.setTitle("选择城市");
        mTitleBar.setBackVisibility(View.VISIBLE);
        mTitleBar.setBackOnClick(this);
        characterParser = CharacterParser.getInstance();
        mResultAdapter = new ResultAdapter(this, null);
        mViewEmpty.setVisibility(View.GONE);
        String userCity = App.getInstance().whereCityName == null ? "定位失败" : App.getInstance().whereCityName;
        tv_city.setText("所在城市:　" + userCity);
        isHome = getIntent().getBooleanExtra("isHome", false);
        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                if (cityAdapter != null) {
                    int position = cityAdapter.getLetterPosition(letter.toLowerCase());
                    mListView.setSelection(position);
                }
            }
        });
        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    mResultCities = new ArrayList<>();
                    for (Areas areas : areasArrayList) {
                        String pingyin = areas.pinyin;
                        String name = areas.name;
                        String substring = keyword.substring(0, 1);
                        if (substring.matches("[a-z]")) {
                            if (pingyin.indexOf(keyword) != -1) {
                                mResultCities.add(areas);
                            }
                        } else {
                            if (name.indexOf(keyword) != -1) {
                                mResultCities.add(areas);
                            }
                        }
                    }
                    if (mResultCities == null || mResultCities.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(mResultCities);
                    }
                }
            }
        });
        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                backWithData(mResultAdapter.getItem(position));
            }
        });
        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        clearBtn.setOnClickListener(this);
        getDatasTwo();
    }
    private void getDatasTwo() {
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.cityList;// 城市列表
        basePresenter.getRequest(this,true,Url,1);
    }
    private void showEmpty(){
        if (areasArrayList.size() == 0){
            if (tv_prompt != null)
                tv_prompt.setVisibility(View.VISIBLE);
        } else {
            if (tv_prompt != null)
                tv_prompt.setVisibility(View.GONE);
        }
    }
    @Override
    public void resultSucess(final int type, final String json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    try {
                        LoadUtils.dissmissWaitProgress();
                        if (json != null) {
                            CityInfo cityInfo = new Gson().fromJson(json,CityInfo.class);
                            if (cityInfo != null) {
                                if (cityInfo.code == 0) {
                                    if (cityInfo.data.maplist != null) {
                                        for (Areas areas : cityInfo.data.maplist) {
                                            String selling = characterParser.getSelling(areas.name);
                                            areas.pinyin = selling.toLowerCase();
                                            String substring = areas.pinyin.substring(0, 1);
                                            if (substring.matches("[0-9]")) {
                                                areas.pinyin = "#";
                                            }
                                            areasArrayList.add(areas);
                                        }
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Collections.sort(areasArrayList, new CityComparator());
                                            cityAdapter = new CityAdapter(CityActivity.this, areasArrayList);
                                            mListView.setAdapter(cityAdapter);
                                            cityAdapter.setOnCityClickListener(new CityAdapter.OnCityClickListener() {
                                                @Override
                                                public void onCityClick(Areas areas) {
                                                    backWithData(areas);
                                                }
                                                @Override
                                                public void onLocateClick() {
                                                    cityAdapter.updateLocateState(LocateState.LOCATING, null);
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    showShortToast(cityInfo.msg+"");
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showEmpty();
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
                    LoadUtils.dissmissWaitProgress();
                    showShortToast(Msg+"");
                }
            }
        });
    }
    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<Areas> {
        @Override
        public int compare(Areas lhs, Areas rhs) {
            String a = lhs.pinyin.substring(0, 1);
            String b = rhs.pinyin.substring(0, 1);
            return a.compareTo(b);
        }
    }
    private void backWithData(Areas city) {
        try {
            city.isHome = isHome;
            EventBus.getDefault().post(new CityEvent(city));
            boolean isKeyWord = false;
            DaoSession daoSession = App.getInstance().daoSession;
            List<LocalAreasInfo> localAreasInfos = daoSession.getLocalAreasInfoDao().loadAll();
            Collections.sort(localAreasInfos, new Comparator<LocalAreasInfo>() {
                @Override
                public int compare(LocalAreasInfo o1, LocalAreasInfo o2) {
                    return (int) (o2.getId() - o1.getId());
                }
            });
            for (LocalAreasInfo info : localAreasInfos) {
                if (city.name.equals(info.getName())) {
                    isKeyWord = true;
                    daoSession.delete(info);
                }
            }
            if (!isKeyWord) {
                if (localAreasInfos.size() == 6) {
                    daoSession.delete(localAreasInfos.get(5));
                }
            }
            LocalAreasInfo info = new LocalAreasInfo();
            info.setName(city.name);
            info.setPinyin(city.pinyin);
            info.setAreasId(city.id);
            info.setPid(city.pid);
            info.setX(city.x);
            info.setY(city.y);
            daoSession.insert(info);
            Utils.out("选择的城市",city.name+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finishActivity();
    }

    @OnClick({R.id.tv_city})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_clear:
                searchBox.setText("");
                clearBtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                mResultListView.setVisibility(View.GONE);
                mResultCities = null;
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_city:
                String cityName = tv_city.getText().toString().trim();
                if(!cityName.equals("定位失败")){
                    Areas city = new Areas();
                    city.name = App.getInstance().whereCityName;
                    NaviLatLng whereLocation = App.getInstance().whereLocation;
                    if (whereLocation != null){
                        city.y = whereLocation.getLongitude() + "";
                        city.x = whereLocation.getLatitude() + "";
                    } else {
                        city.y = "0";
                        city.x = "0";
                    }
                    EventBus.getDefault().post(new CityEvent(city));
                    finishActivity();
                } else {
                }
                break;
        }
    }
}
