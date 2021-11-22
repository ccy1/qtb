package com.example.ntb.ui.city.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
@Entity
public class SelectedCityInfo {

    @Id
    private Long id;

    private String name;

    private String x;

    private String y;

    private boolean isHome;

    @Generated(hash = 2107608533)
    public SelectedCityInfo(Long id, String name, String x, String y,
                            boolean isHome) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.isHome = isHome;
    }

    @Generated(hash = 1480494051)
    public SelectedCityInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return this.y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public boolean getIsHome() {
        return this.isHome;
    }

    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }


}

