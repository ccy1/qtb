package com.example.ntb.ui.membermanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ntb.ui.R;
import com.example.ntb.ui.membermanagement.acticity.MemberManagementActivity;
import com.example.ntb.ui.membermanagement.bean.JsonAddMember;
import com.example.ntb.ui.my.adapter.RecordsAdapter;
import com.example.ntb.ui.vehiclemanagement.adapter.VehicleManagementAdapter;

import java.util.List;
import java.util.PropertyResourceBundle;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */
public class MemberManagementAdapter extends BaseAdapter {

    private List<JsonAddMember.DataBean>_list;
    private Context context;
    private LayoutInflater inflater;
    private OnDeleteItemListenerClickListener onDeleteItemListenerClickListener;

    public MemberManagementAdapter(Context context, List<JsonAddMember.DataBean> list,OnDeleteItemListenerClickListener onDeleteItemListenerClickListener) {
        this._list = list;
        this.context = context;
        this.onDeleteItemListenerClickListener = onDeleteItemListenerClickListener;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHoler;

        if (view == null){
            view = inflater.inflate(R.layout.item_add_menber,viewGroup,false);
            viewHoler = new ViewHolder(view);
            view.setTag(viewHoler);
        }else {
            viewHoler =(ViewHolder) view.getTag();
        }
        viewHoler.tv_name.setText(_list.get(i).name+"");
        viewHoler.tv_phone.setText(_list.get(i).telephone+"");
        viewHoler.tv_plate.setText(_list.get(i).plateNo+"");
        viewHoler.iv_delete_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteItemListenerClickListener.onItemListener(i,_list);
            }
        });

        return view;
    }

    public interface  OnDeleteItemListenerClickListener{
        void onItemListener(int position,List<JsonAddMember.DataBean> list);
    }

    public class ViewHolder {

        private TextView tv_phone,tv_name,tv_plate;
        private ImageView iv_delete_member;

        public ViewHolder(View view){
            tv_phone = view.findViewById(R.id.tv_phone);
            tv_name = view.findViewById(R.id.tv_name);
            tv_plate = view.findViewById(R.id.tv_plate);
            iv_delete_member = view.findViewById(R.id.iv_delete_member);
        }
    }
}
