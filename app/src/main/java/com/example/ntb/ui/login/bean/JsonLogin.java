package com.example.ntb.ui.login.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class JsonLogin {
    /**
     * code : 0
     * msg : 成功
     * data : {"token":"eyJhbGciOiJIUzI1NiJ9.eyJzZWxsZXJJZCI6MSwiaXNMZWFndWVyIjoiMSIsInR5cGUiOiIxIiwiZXhwIjoxNTkwNDY0MzA2LCJwaG9uZU5vIjoiMTgyMTg2NDM2NTEifQ.0H_L0HCcWdZxUeDOqhA5jP3whz0HRUpQ7qYkaqbz-UU","memberName":"%E4%BA%94%E6%9C%88","photoPath":"http://113.108.166.155/fdfs/group2/M00/00/00/CluteV3Bd-SAXHPQAAHgSohRX0Q087.jpg","memberLevel":"1","sex":"0","memberType":"1","chargingStatus":"Free","billNo":null,"cartype":["奥迪"],"collectionnum":"5","membercredit":"0","accbalance":"0","memberintegral":"10","memberno":"18218643651","signIn":true,"signCount":"1","vehicle":[{"id":11772,"deleted":0,"createTime":"2019-07-15 21:16:20","updateTime":null,"plateno":"粤A985265","vin":null,"memberId":13188,"leaguerId":null,"isEnable":true,"brandId":3,"carTypeId":6,"engineModel":null,"url":null,"mileage":144000,"productionTime":1561910400000,"city":null,"company":null,"terminalNo":null,"terminalBluetooth":null,"driverId":null,"carrierType":null,"sellerId":1,"vehicleType":null,"carsSelfNumber":null,"organizingCode":null,"groupName":null,"vehicleInformation":null,"manufacturer":null,"circuit":null,"dailyMileage":null,"chargingSite":null,"parkingSite":null},{"id":12176,"deleted":0,"createTime":"2019-09-24 09:09:41","updateTime":null,"plateno":"粤C998966","vin":null,"memberId":13188,"leaguerId":null,"isEnable":true,"brandId":3,"carTypeId":3,"engineModel":null,"url":null,"mileage":5000,"productionTime":1567267200000,"city":null,"company":null,"terminalNo":null,"terminalBluetooth":null,"driverId":null,"carrierType":null,"sellerId":1,"vehicleType":null,"carsSelfNumber":null,"organizingCode":null,"groupName":null,"vehicleInformation":null,"manufacturer":null,"circuit":null,"dailyMileage":null,"chargingSite":null,"parkingSite":null}],"izLeaguer":false,"leaguerPhone":null,"leaguerName":null}
     * success : true
     * fail : false
     */

    private int code;
    private String msg;
    private DataBean data;
    private boolean success;
    private boolean fail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isFail() {
        return fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzI1NiJ9.eyJzZWxsZXJJZCI6MSwiaXNMZWFndWVyIjoiMSIsInR5cGUiOiIxIiwiZXhwIjoxNTkwNDY0MzA2LCJwaG9uZU5vIjoiMTgyMTg2NDM2NTEifQ.0H_L0HCcWdZxUeDOqhA5jP3whz0HRUpQ7qYkaqbz-UU
         * memberName : %E4%BA%94%E6%9C%88
         * photoPath : http://113.108.166.155/fdfs/group2/M00/00/00/CluteV3Bd-SAXHPQAAHgSohRX0Q087.jpg
         * memberLevel : 1
         * sex : 0
         * memberType : 1
         * chargingStatus : Free
         * billNo : null
         * cartype : ["奥迪"]
         * collectionnum : 5
         * membercredit : 0
         * accbalance : 0
         * memberintegral : 10
         * memberno : 18218643651
         * signIn : true
         * signCount : 1
         * vehicle : [{"id":11772,"deleted":0,"createTime":"2019-07-15 21:16:20","updateTime":null,"plateno":"粤A985265","vin":null,"memberId":13188,"leaguerId":null,"isEnable":true,"brandId":3,"carTypeId":6,"engineModel":null,"url":null,"mileage":144000,"productionTime":1561910400000,"city":null,"company":null,"terminalNo":null,"terminalBluetooth":null,"driverId":null,"carrierType":null,"sellerId":1,"vehicleType":null,"carsSelfNumber":null,"organizingCode":null,"groupName":null,"vehicleInformation":null,"manufacturer":null,"circuit":null,"dailyMileage":null,"chargingSite":null,"parkingSite":null},{"id":12176,"deleted":0,"createTime":"2019-09-24 09:09:41","updateTime":null,"plateno":"粤C998966","vin":null,"memberId":13188,"leaguerId":null,"isEnable":true,"brandId":3,"carTypeId":3,"engineModel":null,"url":null,"mileage":5000,"productionTime":1567267200000,"city":null,"company":null,"terminalNo":null,"terminalBluetooth":null,"driverId":null,"carrierType":null,"sellerId":1,"vehicleType":null,"carsSelfNumber":null,"organizingCode":null,"groupName":null,"vehicleInformation":null,"manufacturer":null,"circuit":null,"dailyMileage":null,"chargingSite":null,"parkingSite":null}]
         * izLeaguer : false
         * leaguerPhone : null
         * leaguerName : null
         */

        private String token;
        private String memberName;
        private String photoPath;
        private String memberLevel;
        private String sex;
        private String memberType;
        private String chargingStatus;
        private Object billNo;
        private String collectionnum;
        private String membercredit;
        private String accbalance;
        private String memberintegral;
        private String memberno;
        private boolean signIn;
        private String signCount;
        private boolean izLeaguer;
        private Object leaguerPhone;
        private Object leaguerName;
        private List<String> cartype;
        private List<VehicleBean> vehicle;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getPhotoPath() {
            return photoPath;
        }

        public void setPhotoPath(String photoPath) {
            this.photoPath = photoPath;
        }

        public String getMemberLevel() {
            return memberLevel;
        }

        public void setMemberLevel(String memberLevel) {
            this.memberLevel = memberLevel;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMemberType() {
            return memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }

        public String getChargingStatus() {
            return chargingStatus;
        }

        public void setChargingStatus(String chargingStatus) {
            this.chargingStatus = chargingStatus;
        }

        public Object getBillNo() {
            return billNo;
        }

        public void setBillNo(Object billNo) {
            this.billNo = billNo;
        }

        public String getCollectionnum() {
            return collectionnum;
        }

        public void setCollectionnum(String collectionnum) {
            this.collectionnum = collectionnum;
        }

        public String getMembercredit() {
            return membercredit;
        }

        public void setMembercredit(String membercredit) {
            this.membercredit = membercredit;
        }

        public String getAccbalance() {
            return accbalance;
        }

        public void setAccbalance(String accbalance) {
            this.accbalance = accbalance;
        }

        public String getMemberintegral() {
            return memberintegral;
        }

        public void setMemberintegral(String memberintegral) {
            this.memberintegral = memberintegral;
        }

        public String getMemberno() {
            return memberno;
        }

        public void setMemberno(String memberno) {
            this.memberno = memberno;
        }

        public boolean isSignIn() {
            return signIn;
        }

        public void setSignIn(boolean signIn) {
            this.signIn = signIn;
        }

        public String getSignCount() {
            return signCount;
        }

        public void setSignCount(String signCount) {
            this.signCount = signCount;
        }

        public boolean isIzLeaguer() {
            return izLeaguer;
        }

        public void setIzLeaguer(boolean izLeaguer) {
            this.izLeaguer = izLeaguer;
        }

        public Object getLeaguerPhone() {
            return leaguerPhone;
        }

        public void setLeaguerPhone(Object leaguerPhone) {
            this.leaguerPhone = leaguerPhone;
        }

        public Object getLeaguerName() {
            return leaguerName;
        }

        public void setLeaguerName(Object leaguerName) {
            this.leaguerName = leaguerName;
        }

        public List<String> getCartype() {
            return cartype;
        }

        public void setCartype(List<String> cartype) {
            this.cartype = cartype;
        }

        public List<VehicleBean> getVehicle() {
            return vehicle;
        }

        public void setVehicle(List<VehicleBean> vehicle) {
            this.vehicle = vehicle;
        }

        public static class VehicleBean {
            /**
             * id : 11772
             * deleted : 0
             * createTime : 2019-07-15 21:16:20
             * updateTime : null
             * plateno : 粤A985265
             * vin : null
             * memberId : 13188
             * leaguerId : null
             * isEnable : true
             * brandId : 3
             * carTypeId : 6
             * engineModel : null
             * url : null
             * mileage : 144000
             * productionTime : 1561910400000
             * city : null
             * company : null
             * terminalNo : null
             * terminalBluetooth : null
             * driverId : null
             * carrierType : null
             * sellerId : 1
             * vehicleType : null
             * carsSelfNumber : null
             * organizingCode : null
             * groupName : null
             * vehicleInformation : null
             * manufacturer : null
             * circuit : null
             * dailyMileage : null
             * chargingSite : null
             * parkingSite : null
             */

            private int id;
            private int deleted;
            private String createTime;
            private Object updateTime;
            private String plateno;
            private Object vin;
            private int memberId;
            private Object leaguerId;
            private boolean isEnable;
            private int brandId;
            private int carTypeId;
            private Object engineModel;
            private Object url;
            private int mileage;
            private long productionTime;
            private Object city;
            private Object company;
            private Object terminalNo;
            private Object terminalBluetooth;
            private Object driverId;
            private Object carrierType;
            private int sellerId;
            private Object vehicleType;
            private Object carsSelfNumber;
            private Object organizingCode;
            private Object groupName;
            private Object vehicleInformation;
            private Object manufacturer;
            private Object circuit;
            private Object dailyMileage;
            private Object chargingSite;
            private Object parkingSite;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDeleted() {
                return deleted;
            }

            public void setDeleted(int deleted) {
                this.deleted = deleted;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public String getPlateno() {
                return plateno;
            }

            public void setPlateno(String plateno) {
                this.plateno = plateno;
            }

            public Object getVin() {
                return vin;
            }

            public void setVin(Object vin) {
                this.vin = vin;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public Object getLeaguerId() {
                return leaguerId;
            }

            public void setLeaguerId(Object leaguerId) {
                this.leaguerId = leaguerId;
            }

            public boolean isIsEnable() {
                return isEnable;
            }

            public void setIsEnable(boolean isEnable) {
                this.isEnable = isEnable;
            }

            public int getBrandId() {
                return brandId;
            }

            public void setBrandId(int brandId) {
                this.brandId = brandId;
            }

            public int getCarTypeId() {
                return carTypeId;
            }

            public void setCarTypeId(int carTypeId) {
                this.carTypeId = carTypeId;
            }

            public Object getEngineModel() {
                return engineModel;
            }

            public void setEngineModel(Object engineModel) {
                this.engineModel = engineModel;
            }

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public int getMileage() {
                return mileage;
            }

            public void setMileage(int mileage) {
                this.mileage = mileage;
            }

            public long getProductionTime() {
                return productionTime;
            }

            public void setProductionTime(long productionTime) {
                this.productionTime = productionTime;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getCompany() {
                return company;
            }

            public void setCompany(Object company) {
                this.company = company;
            }

            public Object getTerminalNo() {
                return terminalNo;
            }

            public void setTerminalNo(Object terminalNo) {
                this.terminalNo = terminalNo;
            }

            public Object getTerminalBluetooth() {
                return terminalBluetooth;
            }

            public void setTerminalBluetooth(Object terminalBluetooth) {
                this.terminalBluetooth = terminalBluetooth;
            }

            public Object getDriverId() {
                return driverId;
            }

            public void setDriverId(Object driverId) {
                this.driverId = driverId;
            }

            public Object getCarrierType() {
                return carrierType;
            }

            public void setCarrierType(Object carrierType) {
                this.carrierType = carrierType;
            }

            public int getSellerId() {
                return sellerId;
            }

            public void setSellerId(int sellerId) {
                this.sellerId = sellerId;
            }

            public Object getVehicleType() {
                return vehicleType;
            }

            public void setVehicleType(Object vehicleType) {
                this.vehicleType = vehicleType;
            }

            public Object getCarsSelfNumber() {
                return carsSelfNumber;
            }

            public void setCarsSelfNumber(Object carsSelfNumber) {
                this.carsSelfNumber = carsSelfNumber;
            }

            public Object getOrganizingCode() {
                return organizingCode;
            }

            public void setOrganizingCode(Object organizingCode) {
                this.organizingCode = organizingCode;
            }

            public Object getGroupName() {
                return groupName;
            }

            public void setGroupName(Object groupName) {
                this.groupName = groupName;
            }

            public Object getVehicleInformation() {
                return vehicleInformation;
            }

            public void setVehicleInformation(Object vehicleInformation) {
                this.vehicleInformation = vehicleInformation;
            }

            public Object getManufacturer() {
                return manufacturer;
            }

            public void setManufacturer(Object manufacturer) {
                this.manufacturer = manufacturer;
            }

            public Object getCircuit() {
                return circuit;
            }

            public void setCircuit(Object circuit) {
                this.circuit = circuit;
            }

            public Object getDailyMileage() {
                return dailyMileage;
            }

            public void setDailyMileage(Object dailyMileage) {
                this.dailyMileage = dailyMileage;
            }

            public Object getChargingSite() {
                return chargingSite;
            }

            public void setChargingSite(Object chargingSite) {
                this.chargingSite = chargingSite;
            }

            public Object getParkingSite() {
                return parkingSite;
            }

            public void setParkingSite(Object parkingSite) {
                this.parkingSite = parkingSite;
            }
        }
    }
}

