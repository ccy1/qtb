package com.example.ntb.ui.util;

import com.example.ntb.ui.site.bean.SiteInfo;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public class ScreenEvent {

    private List<SiteInfo> infoList;

    public ScreenEvent(List<SiteInfo> infoList) {
        this.infoList = infoList;
    }
    public List<SiteInfo> getInfoList() {
        return infoList;
    }
}
