package com.example.ntb.ui.vehiclemanagement.fragement;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ntb.app.App;
import com.example.ntb.base.BaseFragment;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.bean.LocateState;
import com.example.ntb.ui.util.CharacterParser;
import com.example.ntb.ui.util.DialogUtils;
import com.example.ntb.ui.util.LoadUtils;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.SideLetterBar;
import com.example.ntb.ui.util.TitleBarLayout;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.UserUtils;
import com.example.ntb.ui.util.Utils;
import com.example.ntb.ui.vehiclemanagement.adapter.CarBrandAdapter;
import com.example.ntb.ui.vehiclemanagement.adapter.CarTypeAdapter;
import com.example.ntb.ui.vehiclemanagement.bean.CarBrandInfo;
import com.example.ntb.ui.vehiclemanagement.bean.CarEvent;
import com.example.ntb.ui.vehiclemanagement.bean.CarTypeInfo;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */
public class CarTypeFrament extends BaseFragment implements BGAOnRVItemClickListener,BaseView {

    @BindView(R.id.titleBar)
    TitleBarLayout titleBar;//标题

    @BindView(R.id.listview_all_city)
    ListView mListView;

    @BindView(R.id.side_letter_bar)
    SideLetterBar mLetterBar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;//侧滑菜单

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;//车辆类型列表

    @BindView(R.id.tv_carName)
    TextView tv_carName;//车辆名称

    @BindView(R.id.main_right_drawer_layout)
    LinearLayout main_right_drawer_layout;//右边侧滑菜单栏

    @BindView(R.id.tv_letter_overlay)
    TextView overlay;

    @BindView(R.id.tv_prompt)
    TextView tv_prompt;

    private CharacterParser characterParser;//汉字转换成拼音的类
    private ArrayList<CarBrandInfo.CarBrand> list = new ArrayList<>();
    private  String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private CarTypeAdapter carTypeAdapter;
    private CarBrandAdapter adapter;
    private BasePresenter basePresenter = new BasePresenter(getContext(),this);
    private String token;

    @Override
    protected int getLayoutResource() {
        return R.layout.frament_car_type;
    }

    @Override
    protected void initData() {
        titleBar.setTitle("选择车辆");
        titleBar.setBackVisibility(View.VISIBLE);
        titleBar.setBackOnClick(this);
        characterParser = CharacterParser.getInstance();

        mLetterBar.setB(b);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                if (adapter != null) {
                    int position = adapter.getLetterPosition(letter.toLowerCase());
                    mListView.setSelection(position);
                }
            }
        });
        carTypeAdapter = new CarTypeAdapter(recyclerView, R.layout.item_car_type);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance()));
        recyclerView.setAdapter(carTypeAdapter);
        carTypeAdapter.setOnRVItemClickListener(this);

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        //禁止手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //打开手势滑动
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        token = SPUtils.getSharedStringData(getActivity(),"token");
    }

    @Override
    protected void getNetworkRequest() {
        carBrandTwo();//查询车辆品牌
    }

    private void carBrandTwo() {
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.carBrand;// 查询车辆品牌
//        String Url = "https://www.fastmock.site/mock/c2c1d23aaef5d6291972965ad02e2b47/ntb/getCar";
        Map<String, Object> map = new HashMap<>();
        basePresenter.postRequesttoHead(getContext(),Url,true,map,token+"",1);
    }
    /**
     * 车辆类型
     * @param id
     */
    private void carBrandTypeTwo(String id) {
        String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.carBrandType;// 车辆类型
        Map<String, Object> map = new HashMap<>();
        map.put("carBrandId",id);
        basePresenter.postRequesttoHead(getContext(),Url,true,map,token+"",2);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finishFragment();
                break;
        }
    }
    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        List<CarTypeInfo.BrandTypeDtos> data = carTypeAdapter.getData();
        CarTypeInfo.BrandTypeDtos brandTypeDtos = data.get(position);
        Utils.out("BrandTypeDtos", brandTypeDtos.carType);
        EventBus.getDefault().post(new CarEvent(brandId, brandName, brandTypeDtos.id, brandTypeDtos.carType));
        finishFragment();
    }

    @Override
    public void resultSucess(final int type,final String json) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (json != null) {
                        try {
                            CarBrandInfo carTypeInfo = new Gson().fromJson(json, CarBrandInfo.class);
                            if (carTypeInfo.code == 0){
                                ArrayList<CarBrandInfo.CarBrand> carbrand = carTypeInfo.data;
                                for (CarBrandInfo.CarBrand info : carbrand) {
                                    String selling = characterParser.getSelling(info.carBrand);
                                    info.pinyin = selling.toLowerCase();
                                    String substring = info.pinyin.substring(0, 1);
                                    if (substring.matches("[0-9]")) {
                                        info.pinyin = "#";
                                    }
                                    list.add(info);
                                }
                            } else if (carTypeInfo.code == 99999) {
                                LoadUtils.dissmissWaitProgress();
                                UserUtils.clearUserStatus();
                            }
                            Collections.sort(list, new CityComparator());
                            adapter = new CarBrandAdapter(getActivity(), list);
                            mListView.setAdapter(adapter);
                            Utils.out("list", list.toString());
                            adapter.setOnCityClickListener(new CarBrandAdapter.OnCityClickListener() {

                                @Override
                                public void onCityClick(CarBrandInfo.CarBrand carBrand) {
                                    Utils.out("点击的车辆", carBrand.toString());
                                    tv_carName.setText(carBrand.carBrand);
                                    brandId = carBrand.id;
                                    brandName = carBrand.carBrand;
                                    carBrandTypeTwo(carBrand.id);//车辆类型
                                }

                                @Override
                                public void onLocateClick() {
                                    adapter.updateLocateState(LocateState.LOCATING, null);
                                }
                            });

                            if (list.size() == 0){
                                tv_prompt.setVisibility(View.VISIBLE);
                            }else {
                                tv_prompt.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else if (type == 2){
                    if (json != null) {
                        try {
                            CarTypeInfo carTypeInfo = new Gson().fromJson(json, CarTypeInfo.class);
                            if (carTypeInfo.code == 0){
                                carTypeAdapter.setData(carTypeInfo.data);
                                drawerLayout.openDrawer(main_right_drawer_layout);
                            }else {
                                ToastUtil.showToast(getActivity(),carTypeInfo.msg+"");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
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
                    ToastUtil.showToast(getActivity(),Msg+"");
                    if (list.size() == 0){
                        tv_prompt.setVisibility(View.VISIBLE);
                    }else {
                        tv_prompt.setVisibility(View.GONE);
                    }
                }else if (type == 2){
                    ToastUtil.showToast(getActivity(),Msg+"");
                }
            }
        });
    }
    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<CarBrandInfo.CarBrand> {
        @Override
        public int compare(CarBrandInfo.CarBrand lhs, CarBrandInfo.CarBrand rhs) {
            String a = lhs.pinyin.substring(0, 1);
            String b = rhs.pinyin.substring(0, 1);
            return a.compareTo(b);
        }
    }
    /**
     * 汽车品牌ID
     */
    public String brandId;
    /**
     * 汽车品牌名称
     */
    public String brandName;
}
