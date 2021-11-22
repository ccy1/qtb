package com.example.ntb.ui.site.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ntb.app.App;
import com.example.ntb.ui.R;
import com.example.ntb.ui.site.bean.JsonSite;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/21
 * Describe :站点列表适配器
 */
public class SiteListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<JsonSite.DataBeanX.DataBean> _list;
    public OnItemListener onItemListener;
    private DecimalFormat df = new DecimalFormat("######0.0000#");

    public SiteListAdapter(Context context, List<JsonSite.DataBeanX.DataBean> list,OnItemListener onItemListener) {
        this._list = list;
        this.context = context;
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
            view = layoutInflater.inflate(R.layout.item_site_list,viewGroup,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) view.getTag();
        }
        viewHolder.ll_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListener.onNavigationListener(i,_list,1);
            }
        });

        viewHolder.tv_name.setText(_list.get(i).name+"");
        viewHolder.tv_open_time.setText(_list.get(i).openTime+" | ");
        String parkingSubsidy = _list.get(i).parkingSubsidy;
        if (!TextUtils.isEmpty(parkingSubsidy)){
            viewHolder.tv_parkingSubsidy.setText(_list.get(i).parkingSubsidy+" | ");
        }else {
            viewHolder.tv_parkingSubsidy.setText("");
        }

        viewHolder.tv_fast_number.setText(_list.get(i).fastFreeGunCount+"/"+_list.get(i).fastGunCount);
        viewHolder.tv_show_number.setText(_list.get(i).slowFreeGunCount+"/"+_list.get(i).slowGunCount);
        viewHolder.tv_distance.setText(_list.get(i).distance+"km");
        String str = df.format(Double.valueOf(_list.get(i).servicePrice)/10000);
        viewHolder.tv_service.setText("元/度(含服务费)"+str+"元/度");

        if (_list.get(i).discount == true) {
            viewHolder.ll_discount.setVisibility(View.VISIBLE);
            String priceDiff = String.valueOf(_list.get(i).priceDiff);
            String price = String.valueOf(_list.get(i).price);
            double yhPrice = Double.parseDouble(priceDiff);//折扣
            double yhprice = Double.parseDouble(price);//原价
            double totalAmount2 = (yhprice)/10000;
            viewHolder.tv_price.setText(df.format(totalAmount2));
            viewHolder.tv_discount.setText("直降"+df.format(yhPrice)+"元/度");
        } else {
            viewHolder.ll_discount.setVisibility(View.GONE);
            String Price = String.valueOf(_list.get(i).price);
            double totalAmount2 = Double.parseDouble(Price) / 10000;
            viewHolder.tv_price.setText(df.format(totalAmount2));
        }
        if (_list.get(i).tags != null){

            if (_list.get(i).tags.size() != 0){
                CommunitySubAdapter adapter = new CommunitySubAdapter(App.getInstance(), R.layout.item_sub_comment);
                viewHolder.gv_tag.setAdapter(adapter);
                adapter.setData(_list.get(i).tags);
                viewHolder.gv_tag.setVisibility(View.VISIBLE);
            }else {
                viewHolder.gv_tag.setVisibility(View.GONE);
            }
        }
        return view;
    }
    public interface OnItemListener {
        void onNavigationListener(int position,List<JsonSite.DataBeanX.DataBean> data,int type);//导航
    }

    public class ViewHolder{

        private LinearLayout ll_nav,ll_discount;
        private TextView tv_name,tv_open_time,tv_parkingSubsidy,tv_price,tv_distance,tv_fast_number,tv_show_number,tv_discount,tv_service;
        private GridView gv_tag;
        public ViewHolder(View view){
            ll_nav =(LinearLayout) view.findViewById(R.id.ll_nav);
            tv_name = view.findViewById(R.id.tv_name);//站点名称
            tv_open_time = view.findViewById(R.id.tv_open_time);//开放时间
            tv_parkingSubsidy = view.findViewById(R.id.tv_parkingSubsidy);//停车信息
            tv_price = view.findViewById(R.id.tv_price);//价格
            tv_fast_number = view.findViewById(R.id.tv_fast_number);//快枪
            tv_show_number = view.findViewById(R.id.tv_show_number);//慢枪
            tv_distance = view.findViewById(R.id.tv_distance);//距离
            tv_discount = view.findViewById(R.id.tv_discount);//折扣
            ll_discount = view.findViewById(R.id.ll_discount);
            gv_tag = view.findViewById(R.id.gv_tag);//标签
            tv_service = view.findViewById(R.id.tv_service);//服务费
        }
    }
}
