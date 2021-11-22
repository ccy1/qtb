package com.example.ntb.ui.city.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.city.bean.Areas;
import com.example.ntb.ui.city.bean.LocalAreasInfo;
import com.example.ntb.ui.city.bean.LocateState;
import com.example.ntb.ui.util.PinyinUtils;
import com.example.ntb.ui.util.WrapHeightGridView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class CityAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<Areas> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;

    public CityAdapter(Context mContext, List<Areas> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null){
            mCities = new ArrayList<>();
        }
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];

        mCities.add(0, new Areas("", "", "0", "", "", ""));
        mCities.add(1, new Areas("", "", "1", "", "", ""));

        for (int index = 0; index < size; index++){
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).pinyin);
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).pinyin) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     * @param state
     */
    public void updateLocateState(int state, String city){
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter){
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
        return mCities == null ? 0: mCities.size();
    }

    @Override
    public Areas getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType){
            case 0:     //历史
//                view = inflater.inflate(R.layout.cp_view_locate_city, parent, false);
                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
                TextView tv_hot_city1 = (TextView) view.findViewById(R.id.tv_hot_city);
                tv_hot_city1.setText("历史城市");
                WrapHeightGridView gridView = (WrapHeightGridView) view.findViewById(R.id.gridview_hot_city);
                final HistoryAdapter hotCityGridAdapter = new HistoryAdapter(mContext);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null){
                            LocalAreasInfo localAreasInfo = hotCityGridAdapter.getItem(position);
                            Areas areas = new Areas();
                            areas.id = localAreasInfo.getAreasId();
                            areas.name = localAreasInfo.getName();
                            areas.pinyin = localAreasInfo.getPinyin();
                            areas.pid = localAreasInfo.getPid();
                            areas.x = localAreasInfo.getX();
                            areas.y = localAreasInfo.getY();

                            onCityClickListener.onCityClick(areas);
                        }
                    }
                });
                break;
            case 1:     //热门
                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
                TextView tv_hot_city = (TextView) view.findViewById(R.id.tv_hot_city);
                tv_hot_city.setText("热门城市");
                WrapHeightGridView gridView1 = (WrapHeightGridView) view.findViewById(R.id.gridview_hot_city);
                final HotCityAdapter hotCityAdapter = new HotCityAdapter(mContext);
                gridView1.setAdapter(hotCityAdapter);
                gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null){

                            LocalAreasInfo localAreasInfo = hotCityAdapter.getItem(position);
                            Areas areas = new Areas();
                            areas.id = localAreasInfo.getAreasId();
                            areas.name = localAreasInfo.getName();
                            areas.pinyin = localAreasInfo.getPinyin();
                            areas.pid = localAreasInfo.getPid();
                            areas.x = localAreasInfo.getX();
                            areas.y = localAreasInfo.getY();

                            onCityClickListener.onCityClick(areas);
                        }
                    }
                });

                break;
            case 2:     //所有
                if (view == null){
                    view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                }else{
                    holder = (CityViewHolder) view.getTag();
                }
                if (position >= 1){
                    final Areas areas = mCities.get(position);
                    String city = areas.name;
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).pinyin);
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).pinyin) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)){
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter.toUpperCase());
                    }else{
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null){
                                onCityClickListener.onCityClick(areas);
                            }
                        }
                    });
                }
                break;
        }
        return view;
    }

    public static class CityViewHolder{
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener){
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener{
        void onCityClick(Areas areas);
        void onLocateClick();
    }
}

