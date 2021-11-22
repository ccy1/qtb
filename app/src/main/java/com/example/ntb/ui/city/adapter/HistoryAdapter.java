package com.example.ntb.ui.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.ntb.app.App;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.bean.LocalAreasInfo;
import com.example.ntb.ui.db.DaoSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class HistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<LocalAreasInfo> mCities;

    public HistoryAdapter(Context context) {
        this.mContext = context;
        mCities = new ArrayList<>();
        DaoSession daoSession = App.getInstance().daoSession;
        List<LocalAreasInfo> areases = daoSession.getLocalAreasInfoDao().loadAll();
        if (areases != null){
            mCities.addAll(areases);
        }

        Collections.sort(mCities, new Comparator<LocalAreasInfo>() {
            @Override
            public int compare(LocalAreasInfo o1, LocalAreasInfo o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
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

