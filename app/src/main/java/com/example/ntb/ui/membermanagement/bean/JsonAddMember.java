package com.example.ntb.ui.membermanagement.bean;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :
 */
public class JsonAddMember {
    /**
     * code : 0
     * msg : 成功
     * data : [{"id":1,"deleted":null,"createTime":null,"updateTime":null,"telephone":"18218643651","name":"张三","memberId":null,"cardNumber":null,"plateNo":"粤A88888","wxOpenId":null,"miniOpenId":null,"sellerId":null,"status":null,"icon":null,"chargePass":null},{"id":2,"deleted":null,"createTime":null,"updateTime":null,"telephone":"1350000000","name":"李四","memberId":null,"cardNumber":null,"plateNo":"粤A66666","wxOpenId":null,"miniOpenId":null,"sellerId":null,"status":null,"icon":null,"chargePass":null}]
     * success : true
     * fail : false
     */
    public int code;
    public String msg;
    public List<DataBean> data;
    public static class DataBean {
        public int id;
        public String telephone;
        public String plateNo;
        public String name;
        public boolean isSelected;
    }
}