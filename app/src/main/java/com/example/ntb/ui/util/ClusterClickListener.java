package com.example.ntb.ui.util;

import com.amap.api.maps.model.Marker;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */

public interface ClusterClickListener{
    /**
     * 点击聚合点的回调处理函数
     *
     * @param marker
     *            点击的聚合点
     * @param clusterItems
     *            聚合点所包含的元素
     */
    public void onClick(Marker marker, List<ClusterItem> clusterItems);
}

