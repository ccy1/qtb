package com.example.ntb.ui.my.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ntb.ui.R;
import com.example.ntb.ui.home.adapter.ChargingListAdapter;
import com.example.ntb.ui.my.activity.AccountManagementActivity;
import com.example.ntb.ui.my.bean.JsonQuerRevenue;
import com.example.ntb.ui.site.adapter.SiteListAdapter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/22
 * Describe :
 */
public class RecordsAdapter extends BaseAdapter {

    private Context context;
    private List<JsonQuerRevenue.DataBean.RevenuelistBean>_list;
    private LayoutInflater inflater;

    public RecordsAdapter(Context context, List<JsonQuerRevenue.DataBean.RevenuelistBean> list) {
        this.context = context;
        this._list = list;
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
        final ViewHolder viewHolder;

        if (view == null){
            view = inflater.inflate(R.layout.item_records,viewGroup,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) view.getTag();
        }
        BigDecimal decimal = new BigDecimal(_list.get(i).blance);
        BigDecimal decimal1 = new BigDecimal(100);
        BigDecimal divide = decimal.divide(decimal1, 2, BigDecimal.ROUND_HALF_UP);

        viewHolder.tv_time.setText(_list.get(i).date);//时间
        if (_list.get(i).display == null){
            viewHolder.tv_name.setText("【"+_list.get(i).type+"】");//类型
        }else {
            viewHolder.tv_name.setText("【"+_list.get(i).type+"】"+_list.get(i).display+"");//类型
        }
        if (_list.get(i).spend == true){
            viewHolder.tv_money.setTextColor(Color.parseColor("#FF101734"));
            viewHolder.tv_money.setText("-"+divide+"");
        }else {
            viewHolder.tv_money.setTextColor(Color.parseColor("#FFFF7073"));
            viewHolder.tv_money.setText("+"+divide+"");
        }
        return view;
    }
    public class ViewHolder{
        private TextView tv_money,tv_name,tv_time;

        public  ViewHolder(View view){
            tv_money = view.findViewById(R.id.tv_money);
            tv_name = view.findViewById(R.id.tv_name);
            tv_time = view.findViewById(R.id.tv_time);
        }
    }
}
