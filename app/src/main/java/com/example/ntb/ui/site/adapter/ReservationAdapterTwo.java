package com.example.ntb.ui.site.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ntb.ui.R;
import com.example.ntb.ui.site.bean.JsonReservation;

import java.text.DecimalFormat;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public class ReservationAdapterTwo extends BGAAdapterViewAdapter<JsonReservation.DataBean.RuleTwoBean> {

    private DecimalFormat df = new DecimalFormat("0");

    public ReservationAdapterTwo(Context context) {
        super(context,R.layout.item_reservationtwo);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.ll_reservationtwo);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, JsonReservation.DataBean.RuleTwoBean model) {

        TextView tv_name = helper.getView(R.id.tv_name);

        tv_name.setText(model.getTime()+"分钟");
        LinearLayout ll_reservation = helper.getView(R.id.ll_reservationtwo);

        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(Double.valueOf(model.getCost())/100);
        TextView textView = helper.getView(R.id.tv);
        if (str.equals("0.00")){
            textView.setText("免费");
        }else {
            textView.setText(str+"元");
        }
//        if (model.isChecked) {
//            ll_reservation.setBackground(mContext.getResources().getDrawable(R.drawable.parklock_shape));
//            helper.setTextColor(R.id.tv_name, Color.parseColor("#FFFFFF"));
//            helper.setTextColor(R.id.tv,Color.parseColor("#FFFFFF"));
//        } else {
//            ll_reservation.setBackground(mContext.getResources().getDrawable(R.drawable.unreservation_shape));
//            helper.setTextColor(R.id.tv_name, Color.parseColor("#666666"));
//            helper.setTextColor(R.id.tv,Color.parseColor("#666666"));
//        }
    }
}


