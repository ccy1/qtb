package com.example.ntb.ui.util;

import com.amap.api.maps.model.LatLng;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public interface ClusterItem {
    LatLng getPosition();
    String getID();
}
