package com.example.ntb.ui.util;

import android.graphics.drawable.Drawable;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */

public interface ClusterRender {
    /**
     * 根据聚合点的元素数目返回渲染背景样式
     *
     * @param clusterNum
     * @return
     */
    Drawable getDrawAble(int clusterNum);
}
