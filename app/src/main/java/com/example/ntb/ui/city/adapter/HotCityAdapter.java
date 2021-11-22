package com.example.ntb.ui.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.amap.api.navi.model.NaviLatLng;
import com.example.ntb.app.App;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.bean.LocalAreasInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class HotCityAdapter extends BaseAdapter {
    private Context mContext;
    private List<LocalAreasInfo> mCities;

    public HotCityAdapter(Context context) {
        this.mContext = context;
        mCities = new ArrayList<>();
        LocalAreasInfo info = new LocalAreasInfo();
        info.setName("全国");
        info.setPid("");
        info.setPinyin("QuanGuo");
        NaviLatLng whereLocation = App.getInstance().whereLocation;
        if (whereLocation != null){
            info.setX(whereLocation.getLongitude() + "");
            info.setY(whereLocation.getLatitude() + "");
        } else {
            info.setX("116.405285");
            info.setY("39.904989");
        }
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("北京市");
        info.setPid("110000");
        info.setPinyin("Beijing");
        info.setX("116.405285");
        info.setY("39.904989");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("上海市");
        info.setPid("310000");
        info.setPinyin("Shanghai");
        info.setX("121.472644");
        info.setY("31.231706");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("深圳市");
        info.setPid("440300");
        info.setPinyin("Shenzhen");
        info.setX("114.085947");
        info.setY("22.547");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("广州市");
        info.setPid("440100");
        info.setPinyin("Guangzhou");
        info.setX("113.280637");
        info.setY("23.125178");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("天津");
        info.setPid("120000");
        info.setPinyin("Tianjin");
        info.setX("117.190182");
        info.setY("39.125596");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("西安市");
        info.setPid("610100");
        info.setPinyin("Xi'an");
        info.setX("108.948024");
        info.setY("34.263161");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("成都市");
        info.setPid("510100");
        info.setPinyin("Chengdu");
        info.setX("104.065735");
        info.setY("30.659462");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("杭州市");
        info.setPid("330100");
        info.setPinyin("Hangzhou");
        info.setX("120.153576");
        info.setY("30.287459");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("重庆市");
        info.setPid("500000");
        info.setPinyin("Chongqing");
        info.setX("106.504962");
        info.setY("29.533155");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("南京市");
        info.setPid("320100");
        info.setPinyin("Nanjing");
        info.setX("118.767413");
        info.setY("32.041544");
        mCities.add(info);

        info = new LocalAreasInfo();
        info.setName("武汉市");
        info.setPid("420100");
        info.setPinyin("Wuhan");
        info.setX("114.298572");
        info.setY("30.584355");
        mCities.add(info);
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public LocalAreasInfo getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        }else{
            holder = (HotCityViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position).getName());
        return view;
    }

    public static class HotCityViewHolder{
        TextView name;
    }
}

