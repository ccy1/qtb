package com.example.ntb.ui.vehiclemanagement.bean;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */
public class CarEvent {

    /**
     * 汽车品牌ID
     */
    public String brandId;
    /**
     * 汽车品牌名称
     */
    public String brandName;
    /**
     * 汽车车型ID
     */
    public String carTypeId;
    /**
     * 汽车品牌名称
     */
    public String carTypeName;

    public CarEvent(String brandId, String brandName, String carTypeId, String carTypeName) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.carTypeId = carTypeId;
        this.carTypeName = carTypeName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }
}

