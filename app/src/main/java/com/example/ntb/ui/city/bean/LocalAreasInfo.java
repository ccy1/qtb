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
public class LocalAreasInfo {

    @Id
    private Long id;

    private String areasId;

    private String name;

    private String pinyin;

    private String pid;
    private String x;
    private String y;
    @Generated(hash = 292246783)
    public LocalAreasInfo(Long id, String areasId, String name, String pinyin,
                          String pid, String x, String y) {
        this.id = id;
        this.areasId = areasId;
        this.name = name;
        this.pinyin = pinyin;
        this.pid = pid;
        this.x = x;
        this.y = y;
    }
    @Generated(hash = 1852063003)
    public LocalAreasInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAreasId() {
        return this.areasId;
    }
    public void setAreasId(String areasId) {
        this.areasId = areasId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPinyin() {
        return this.pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public String getPid() {
        return this.pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
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
}

