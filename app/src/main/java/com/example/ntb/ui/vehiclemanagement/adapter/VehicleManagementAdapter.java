package com.example.ntb.ui.vehiclemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ntb.base.RequestURL;
import com.example.ntb.ui.R;
import com.example.ntb.ui.membermanagement.bean.JsonAddMember;
import com.example.ntb.ui.site.adapter.SiteListAdapter;
import com.example.ntb.ui.vehiclemanagement.bean.JsonVehicle;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/24
 * Describe :
 */
public class VehicleManagementAdapter extends BaseAdapter {

    private List<JsonVehicle.DataBean> _list;
    private Context context;
    private LayoutInflater inflater;
    public OnDeleteItemListener onDeleteItemListener;

    public VehicleManagementAdapter(Context context, List<JsonVehicle.DataBean> list,OnDeleteItemListener onDeleteItemListener) {
        this._list = list;
        this.context = context;
        this.onDeleteItemListener = onDeleteItemListener;
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
            view = inflater.inflate(R.layout.item_vehicle_list,viewGroup,false);
            viewHoler = new ViewHolder(view);
            view.setTag(viewHoler);
        }else {
            viewHoler =(ViewHolder) view.getTag();
        }
        viewHoler.tv_plate.setText(_list.get(i).plateNo+"");
        viewHoler.tv_vin.setText(_list.get(i).vin+"");
        String icon = "http://" + RequestURL.new_Url + "/pic/car/cartype/" + _list.get(i).id + ".jpg";
        Glide.with(context)
                .load(icon)
                .placeholder(R.mipmap.vehicle_item)
                .into(viewHoler.iv_car_icon);

        viewHoler.iv_delete_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteItemListener.onDeleteListener(i,_list);
            }
        });

        return view;
    }

    public interface OnDeleteItemListener{
        void onDeleteListener(int positon,List<JsonVehicle.DataBean> list);
    }

    public class ViewHolder {

        public ImageView iv_car_icon,iv_delete_car;
        public TextView tv_plate,tv_vin;

        public ViewHolder(View view){
            iv_car_icon = view.findViewById(R.id.iv_car_icon);
            tv_plate = view.findViewById(R.id.tv_plate);
            tv_vin = view.findViewById(R.id.tv_vin);
            iv_delete_car = view.findViewById(R.id.iv_delete_car);
        }
    }
}
