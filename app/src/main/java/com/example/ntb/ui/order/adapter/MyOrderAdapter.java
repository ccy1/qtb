package com.example.ntb.ui.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ntb.ui.R;
import com.example.ntb.ui.membermanagement.bean.JsonAddMember;
import com.example.ntb.ui.order.activity.MyOrderActivity;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */
public class MyOrderAdapter extends BaseAdapter {

    private List<JsonAddMember.DataBean>_list;
    private Context context;
    private LayoutInflater inflater;

    public MyOrderAdapter(Context context, List<JsonAddMember.DataBean> list) {
        this._list = list;
        this.context = context;
        if (context != null){
            inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public Object getItem(int i) {
        return _list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHoler;

        if (view == null){
            view = inflater.inflate(R.layout.item_my_order,viewGroup,false);
            viewHoler = new ViewHolder(view);
            view.setTag(viewHoler);
        }else {
            viewHoler =(ViewHolder) view.getTag();
        }
        return view;
    }

    public class ViewHolder {

        public ViewHolder(View view){

        }
    }
}
