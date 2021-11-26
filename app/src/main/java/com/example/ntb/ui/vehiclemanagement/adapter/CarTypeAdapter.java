package com.example.ntb.ui.vehiclemanagement.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ntb.base.RequestURL;
import com.example.ntb.ui.R;
import com.example.ntb.ui.vehiclemanagement.bean.CarTypeInfo;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */
public class CarTypeAdapter extends BGARecyclerViewAdapter<CarTypeInfo.BrandTypeDtos> {

    public CarTypeAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        super(recyclerView, defaultItemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, CarTypeInfo.BrandTypeDtos model) {
        helper.setText(R.id.tv_carName, model.carType);
        ImageView iv_car = helper.getView(R.id.iv_car);
        String icon = "http://" + RequestURL.new_Url + "/pic/car/cartype/" + model.id + ".jpg";
        Glide.with(mContext)
                .load(icon)
                .placeholder(R.mipmap.success_car)
                .into(iv_car);
//        ImageLoader.getInstance().displayImage(icon, iv_car, ImageLoadUtils.getNormalOptions(R.mipmap.success_car));
    }
}
