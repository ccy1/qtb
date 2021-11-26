package com.example.ntb.ui.vehiclemanagement.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/24
 * Describe :
 */
public class JsonVehicle {

    /**
     * code : 0
     * msg : 成功
     * data : [{"id":1703,"deleted":0,"createTime":"2021-11-24 11:52:47","updateTime":null,"plateNo":"粤A123658","vin":"123456799","memberId":11131,"leaguerId":null,"isEnable":true,"brandId":3,"carTypeId":3,"engineModel":null,"url":null,"mileage":123658,"productionTime":null,"city":null,"company":null,"terminalNo":null,"terminalBluetooth":null,"driverId":null,"carrierType":null,"sellerId":29,"vehicleType":null,"selfNumber":null,"organizingCode":null,"groupName":null,"vehicleInformation":null,"manufacturer":null,"circuit":null,"dailyMileage":null,"chargingSite":null,"parkingSite":null,"mileageStr":null,"brandName":"���迪","carTypeName":"R8 e-tron","productionTimeStr":null},{"id":1704,"deleted":0,"createTime":"2021-11-24 13:35:10","updateTime":null,"plateNo":"粤A123695","vin":"147258","memberId":11131,"leaguerId":null,"isEnable":true,"brandId":3,"carTypeId":3,"engineModel":null,"url":null,"mileage":123695,"productionTime":null,"city":null,"company":null,"terminalNo":null,"terminalBluetooth":null,"driverId":null,"carrierType":null,"sellerId":29,"vehicleType":null,"selfNumber":null,"organizingCode":null,"groupName":null,"vehicleInformation":null,"manufacturer":null,"circuit":null,"dailyMileage":null,"chargingSite":null,"parkingSite":null,"mileageStr":null,"brandName":"奥迪","carTypeName":"R8 e-tron","productionTimeStr":null}]
     * success : true
     * fail : false
     */
    public int code;
    public String msg;
    public List<DataBean> data;
    public static class DataBean {
        /**
         * id : 1703
         * deleted : 0
         * createTime : 2021-11-24 11:52:47
         * updateTime : null
         * plateNo : 粤A123658
         * vin : 123456799
         * memberId : 11131
         * leaguerId : null
         * isEnable : true
         * brandId : 3
         * carTypeId : 3
         * engineModel : null
         * url : null
         * mileage : 123658
         * productionTime : null
         * city : null
         * company : null
         * terminalNo : null
         * terminalBluetooth : null
         * driverId : null
         * carrierType : null
         * sellerId : 29
         * vehicleType : null
         * selfNumber : null
         * organizingCode : null
         * groupName : null
         * vehicleInformation : null
         * manufacturer : null
         * circuit : null
         * dailyMileage : null
         * chargingSite : null
         * parkingSite : null
         * mileageStr : null
         * brandName : ���迪
         * carTypeName : R8 e-tron
         * productionTimeStr : null
         */

        public int id;
        public int deleted;
        public String createTime;
        public Object updateTime;
        public String plateNo;
        public String vin;
        public int memberId;
        public Object leaguerId;
        public boolean isEnable;
        public int brandId;
        public int carTypeId;
        public Object engineModel;
        public Object url;
        public int mileage;
        public Object productionTime;
        public Object city;
        public Object company;
        public Object terminalNo;
        public Object terminalBluetooth;
        public Object driverId;
        public Object carrierType;
        public int sellerId;
        public Object vehicleType;
        public Object selfNumber;
        public Object organizingCode;
        public Object groupName;
        public Object vehicleInformation;
        public Object manufacturer;
        public Object circuit;
        public Object dailyMileage;
        public Object chargingSite;
        public Object parkingSite;
        public Object mileageStr;
        public String brandName;
        public String carTypeName;
        public Object productionTimeStr;
    }
}
