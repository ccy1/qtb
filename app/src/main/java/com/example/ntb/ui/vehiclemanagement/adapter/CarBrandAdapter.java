package com.example.ntb.ui.vehiclemanagement.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ntb.base.RequestURL;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.bean.LocateState;
import com.example.ntb.ui.util.PinyinUtils;
import com.example.ntb.ui.vehiclemanagement.bean.CarBrandInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */
public class CarBrandAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 1;

    private Context mContext;
    private LayoutInflater inflater;
    private List<CarBrandInfo.CarBrand> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;

    public CarBrandAdapter(Context mContext, List<CarBrandInfo.CarBrand> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null) {
            mCities = new ArrayList<>();
        }
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];


        for (int index = 0; index < size; index++) {
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).pinyin);

            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).pinyin) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     *
     * @param state
     */
    public void updateLocateState(int state, String city) {
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public CarBrandInfo.CarBrand getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_car, parent, false);
            holder = new CityViewHolder();
            holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
            holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
            holder.iv_car = (ImageView) view.findViewById(R.id.iv_car);
            view.setTag(holder);
        } else {
            holder = (CityViewHolder) view.getTag();
        }
        try {
            final CarBrandInfo.CarBrand areas = mCities.get(position);
            String city = areas.carBrand;
            holder.name.setText(city);
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).pinyin);
            String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).pinyin) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                holder.letter.setVisibility(View.VISIBLE);
                holder.letter.setText(currentLetter.toUpperCase());
            } else {
                holder.letter.setVisibility(View.GONE);
            }
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCityClickListener != null) {
                        onCityClickListener.onCityClick(areas);
                    }
                }
            });
            String icon = "http://" + RequestURL.new_Url + "/pic/car/" + areas.id + ".jpg";
            Glide.with(mContext)
                    .load(icon)
                    .placeholder(R.mipmap.success_car)
                    .into(holder.iv_car);
//            ImageLoader.getInstance().displayImage(icon, holder.iv_car, ImageLoadUtils.getNormalOptions(R.mipmap.success_car));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public class CityViewHolder {
        TextView letter;
        TextView name;
        ImageView iv_car;
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener {
        void onCityClick(CarBrandInfo.CarBrand areas);

        void onLocateClick();
    }
}

