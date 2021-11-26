package com.example.ntb.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ntb.ui.R;
import com.example.ntb.ui.home.bean.JsonchargingList;
import com.example.ntb.ui.site.adapter.SiteListAdapter;
import com.example.ntb.ui.site.bean.JsonSite;
import com.example.ntb.ui.util.ToastUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/20
 * Describe :充电中列表适配器
 */
public class ChargingListAdapter extends BaseAdapter {
    private Context context;
    private List<JsonchargingList.DataBeanX.DataBean>_list;
    private LayoutInflater layoutInflater;
    private DecimalFormat df = new DecimalFormat("######0.00#");
    public OnItemListener onItemListener;

    public ChargingListAdapter(Context context, List<JsonchargingList.DataBeanX.DataBean> list,OnItemListener onItemListener) {
        this.context = context;
        this._list = list;
        this.onItemListener = onItemListener;
        if (context != null){
            layoutInflater = LayoutInflater.from(context);
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
        final ViewHolder viewHolder;
        if (view == null){
            view = layoutInflater.inflate(R.layout.item_charging_list,viewGroup,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) view.getTag();
        }
        viewHolder.tv_licensePlate.setText(_list.get(i).licensePlate+"");
        viewHolder.tv_amountOfConsumption.setText(df.format(Double.valueOf(_list.get(i).amountOfConsumption))+"");
        viewHolder.tv_soc.setText(_list.get(i).soc+"%");
        viewHolder.tv_chargedElectricity.setText(_list.get(i).chargedElectricity+"kw.h");
        viewHolder.tv_stationName.setText(_list.get(i).stationName+"");
        viewHolder.tv_startTime.setText(_list.get(i).startTime+"");

        viewHolder.iv_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListener.mOnItemListener(i,_list);
            }
        });

        return view;
    }
    public interface OnItemListener {
        void mOnItemListener(int position, List<JsonchargingList.DataBeanX.DataBean> data);//暂停
    }

    public class ViewHolder{
        private TextView tv_licensePlate,tv_amountOfConsumption,tv_soc,tv_chargedElectricity,tv_stationName,tv_startTime;
        private ImageView iv_pause;

        public ViewHolder(View view){
            tv_licensePlate =(TextView) view.findViewById(R.id.tv_licensePlate);
            tv_amountOfConsumption =(TextView) view.findViewById(R.id.tv_amountOfConsumption);
            tv_soc =(TextView) view.findViewById(R.id.tv_soc);
            tv_chargedElectricity =(TextView) view.findViewById(R.id.tv_chargedElectricity);
            tv_stationName =(TextView) view.findViewById(R.id.tv_stationName);
            tv_startTime =(TextView) view.findViewById(R.id.tv_startTime);
            iv_pause =(ImageView) view.findViewById(R.id.iv_pause);
        }
    }
}
