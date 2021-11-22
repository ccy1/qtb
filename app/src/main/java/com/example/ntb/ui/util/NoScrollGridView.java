package com.example.ntb.ui.util;

/**
 * Created by ccy.
 * Date: 2021/11/8
 * Describe :
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * description:不会滚动的gridview
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoScrollGridView(Context context) {
        super(context);
    }
    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

//        （1）精确模式（MeasureSpec.EXACTLY）
//
//        在这种模式下，尺寸的值是多少，那么这个组件的长或宽就是多少。
//
//（2）最大模式（MeasureSpec.AT_MOST）
//
//        这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
//
//（3）未指定模式（MeasureSpec.UNSPECIFIED）
//
//        这个就是说，当前组件，可以随便用空间，不受限制。
    }
}

