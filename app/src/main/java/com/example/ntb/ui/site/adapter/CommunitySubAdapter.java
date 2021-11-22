package com.example.ntb.ui.site.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.example.ntb.ui.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by ccy.
 * Date: 2021/11/8
 * Describe :
 */
public class CommunitySubAdapter extends BGAAdapterViewAdapter<String> {

    public CommunitySubAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        TextView tv_sub_comment = helper.getTextView(R.id.tv_sub_comment);
        tv_sub_comment.setText(model+"");
        if (model.equals("泥头车专用站")){
            tv_sub_comment.setTextColor(Color.parseColor("#01CA7E"));
            tv_sub_comment.setBackgroundResource(R.drawable.tag_bg_shape);
        }else {
            tv_sub_comment.setTextColor(Color.parseColor("#4498F8"));
            tv_sub_comment.setBackgroundResource(R.drawable.tag_two_bg_shape);
        }
    }
}
