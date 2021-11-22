package com.example.ntb.ui.util;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public class Cluster {

    private LatLng mLatLng;
    private List<ClusterItem> mClusterItems;
    private Marker mMarker;


    public Cluster(LatLng latLng) {

        mLatLng = latLng;
        mClusterItems = new ArrayList<ClusterItem>();
    }

    public void addClusterItem(ClusterItem clusterItem) {
        mClusterItems.add(clusterItem);
    }

    public int getClusterCount() {
        return mClusterItems.size();
    }



    public LatLng getCenterLatLng() {
        return mLatLng;
    }

    public void setMarker(Marker marker) {
        mMarker = marker;
    }

    public Marker getMarker() {
        return mMarker;
    }

    public List<ClusterItem> getClusterItems() {
        return mClusterItems;
    }
}

