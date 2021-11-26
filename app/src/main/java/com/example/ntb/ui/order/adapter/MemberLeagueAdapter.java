package com.example.ntb.ui.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ntb.ui.R;
import com.example.ntb.ui.membermanagement.bean.JsonAddMember;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/25
 * Describe :
 */
public class MemberLeagueAdapter extends BaseAdapter {

    private List<JsonAddMember.DataBean> _list;
    private Context context;
    private LayoutInflater inflater;

    public MemberLeagueAdapter(Context context, List<JsonAddMember.DataBean> list) {
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
            view = inflater.inflate(R.layout.item_member_league,viewGroup,false);
            viewHoler = new ViewHolder(view);
            view.setTag(viewHoler);
        }else {
            viewHoler =(ViewHolder) view.getTag();
        }

        viewHoler.tv_name.setText(_list.get(i).name+"");

        if (_list.get(i).isSelected == true){
            viewHoler.iv_yes.setVisibility(View.VISIBLE);
            viewHoler.tv_name.setTextColor(Color.parseColor("#4499F9"));
            viewHoler.iv_icon.setImageResource(R.mipmap.sel_member);
        }else {
            viewHoler.iv_yes.setVisibility(View.GONE);
            viewHoler.tv_name.setTextColor(Color.parseColor("#575C71"));
            viewHoler.iv_icon.setImageResource(R.mipmap.un_member);
        }
        return view;
    }

    public class ViewHolder {

        private ImageView iv_icon,iv_yes;
        private TextView tv_name;

        public ViewHolder(View view){
            tv_name = view.findViewById(R.id.tv_name);
            iv_icon = view.findViewById(R.id.iv_icon);
            iv_yes = view.findViewById(R.id.iv_yes);
        }
    }
}

