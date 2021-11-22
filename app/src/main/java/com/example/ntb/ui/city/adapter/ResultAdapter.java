package com.example.ntb.ui.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.bean.Areas;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */

public class ResultAdapter extends BaseAdapter {
    private Context mContext;
    private List<Areas> mCities;

    public ResultAdapter(Context mContext, List<Areas> mCities) {
        this.mCities = mCities;
        this.mContext = mContext;
    }

    public void changeData(List<Areas> list){
        if (mCities == null){
            mCities = list;
        }else{
            mCities.clear();
            mCities.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public Areas getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ResultViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_search_result_listview, parent, false);
            holder = new ResultViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_item_result_listview_name);
            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position).name);
        return view;
    }

    public static class ResultViewHolder{
        TextView name;
    }
}

