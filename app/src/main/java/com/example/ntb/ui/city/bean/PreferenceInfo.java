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
public class PreferenceInfo {

    @Id
    private Long id;

    /**
     * 是否免费停车
     */
    private boolean isParking;

    /**
     * 是否有空闲
     */
    private boolean isFree;

    /**
     * 是否全时段开发
     */
    private boolean isTime;

    /**
     * 充电桩类型(0、全部 1、快充 2、慢充)
     */
    private int stateType;

    /**
     * 充电桩类型(0、全部 1、本商家 2、其他)
     */
    private int operate;



    @Generated(hash = 1235935255)
    public PreferenceInfo(Long id, boolean isParking, boolean isFree,
                          boolean isTime, int stateType, int operate) {
        this.id = id;
        this.isParking = isParking;
        this.isFree = isFree;
        this.isTime = isTime;
        this.stateType = stateType;
        this.operate = operate;
    }

    @Generated(hash = 393905394)
    public PreferenceInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsParking() {
        return this.isParking;
    }

    public void setIsParking(boolean isParking) {
        this.isParking = isParking;
    }

    public boolean getIsFree() {
        return this.isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    public boolean getIsTime() {
        return this.isTime;
    }

    public void setIsTime(boolean isTime) {
        this.isTime = isTime;
    }

    public int getStateType() {
        return this.stateType;
    }

    public void setStateType(int stateType) {
        this.stateType = stateType;
    }

    public int getOperate() {
        return this.operate;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }




}
