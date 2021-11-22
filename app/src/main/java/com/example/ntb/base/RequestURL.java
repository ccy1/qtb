package com.example.ntb.base;

import android.text.TextUtils;

import com.example.ntb.app.App;
import com.example.ntb.ui.util.SPUtils;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
public class RequestURL {
    //  http://jingxun.wuliujie.com:7100/api-service/api/queryCollection   //京勋车服
    //http://1.14.130.48/api/api-service/api/getMemberInfo //海口   26
    public static String new_Port = getNew_Port();
    public static String new_Url = getNew_Url();
    public static String sellerId = getNew_SellerId();//项目重构测试暂时用商家id
    public static String API = "/api-service/api/";
    //url = 106.53.24.142
    public static String sellerNo = getNew_SellerNo();//项目重构测试暂时用商家编号

    /**
     * 端口号
     * @return
     */
    public static String getNew_Port(){
        String Port = SPUtils.getSharedStringData(App.getInstance(), "new_Port");
        if (!TextUtils.isEmpty(Port)){
            return Port;
        }else {
            return Constants.SELLERPORT;
        }
    }

    /**
     * IP
     * @return
     */
    public static String getNew_Url(){
        String Url = SPUtils.getSharedStringData(App.getInstance(), "new_Url");
        if (!TextUtils.isEmpty(Url)){
            return Url;
        }else {
            return Constants.URL;
        }
    }

    /**
     * 商家号
     * @return
     */
    public static String getNew_SellerNo(){
        String new_sellerNo = SPUtils.getSharedStringData(App.getInstance(), "new_sellerNo");
        if (!TextUtils.isEmpty(new_sellerNo)){
            return new_sellerNo;
        }else {
            return Constants.SELLERNO;
        }
    }

    /**
     * 商家id
     * @return
     */
    public static String getNew_SellerId(){
        String new_sellerId = SPUtils.getSharedStringData(App.getInstance(), "new_sellerId");
        if (!TextUtils.isEmpty(new_sellerId)){
            return new_sellerId;
        }else {
            return Constants.SELLERID;
        }
    }
    /**
     * 获取验证码
     */
    public static String getIdCode = "getIdCode";

    /**
     * 注册
     */
    public static String registTwo = "registTwo";

    /**
     * 登录
     */
    public static String vcodeLogin = "vcodeLogin";

    /**
     * 退出登录
     */
    public static String logoutTwo = "logoutTwo";

    /**
     * 获取版本号
     */
    public static String getAppVersion = "getAppVersion";

    /**
     * 发票记录列表&&发票记录列表详情
     */
    public static String queryInvoiceOrder = "queryInvoiceOrder";

    /**
     * 新增发票
     */
    public static String addInvoiceTitle = "addInvoiceTitle";
    /**
     * 编辑发票
     */
    public static String modifyInvoiceTitle = "modifyInvoiceTitle";

    /**
     * 删除发票
     */
    public static String deleteInvoiceTitle = "deleteInvoiceTitle";

    /**
     * 发票所有抬头&&最新发票抬头
     */
    public static String queryInvoiceTitle = "queryInvoiceTitle";

    /**
     * 开发票
     */
    public static String orderInvoice = "orderInvoice";

    /**
     * 是否存在羊城通或者微信支付
     */
    public static String isExistPayInterface = "isExistPayInterface";

    /**
     * 微信预下单
     */
    public static String unifiedOrder = "unifiedOrder";

    /**
     * 羊城通预下单
     */
    public static String yctongOrder = "yctongOrder";

    /**
     * 支付宝预下单
     */
    public static String alipay = "alipay";

    /**
     * 成功支付通知后台查询账单
     */
    public static String orderQuery = "orderQuery";

    /**
     * 有效期&&无效券
     */
    public static String couponList = "couponList";

    /**
     * 扫码准备充电
     */
    public static String querChargeRealy = "querChargeRealy";

    /**
     * 可充电枪列表 -- 扫桩进入
     */
    public static String queryGunListIsFree = "queryGunListIsFree";

    /**
     * 未登录站点分页查询
     */
    public static String getStationListUnlisted = "getStationListUnlisted";

    /**
     * 站点列表分页查询
     */
    public static String getStationList = "getStationList";

    /**
     * 未登录状态单个站点详情
     */
    public static String getStationDetailUnlisted = "getStationDetailUnlisted";

    /**
     * 登录状态单个站点详情
     */
    public static String getStationDetail = "getStationDetail";

    /**
     * 获取预约时间
     */
    public static String appointTime = "appointTime";

    /**
     * 提交预约
     */
    public static String addAppointBill = "addAppointBill";

    /**
     * 预约列表分页
     */
    public static String getAppointBillList = "getAppointBillList";

    /**
     * 预约详情
     */
    public static String getAppointBill = "getAppointBill";

    /**
     * 取消预约
     */
    public static String cancelAppointBill = "cancelAppointBill";

    /**
     * 续约
     */
    public static String continueAppointBill = "continueAppointBill";

    /**
     * 收藏站点
     */
    public static String collectTwo = "collectTwo";

    /**
     * 取消收藏站点
     */
    public static String cancelCollectTwo = "cancelCollectTwo";

    /**
     * 我的收藏
     */
    public static String queryCollection = "queryCollection";

    /**
     * 获取枪的详情
     */
    public static String queryGunDatil = "queryGunDatil";

    /**
     * 启动充电
     */
    public static String startCharge = "startCharge";

    /**
     * 通过流水号查询是否成功启动充电
     */
    public static String queryIsChargeByBusId = "queryIsChargeByBusId";

    /**
     *  得到会员状态(判断是否充电中)
     */
    public static String querMemberStatus = "querMemberStatus";

    /**
     * 充电中获取温度和功率
     */
    public static String queryAllTimeDataByMemberNo = "queryAllTimeDataByMemberNo";

    /**
     * 充电中的数据监控
     */
    public static String querChargeOnline = "querChargeOnline";

    /**
     * 停止充电
     */
    public static String stopCharge = "stopCharge";

    /**
     * App查询停止充电是否成功
     */
    public static String queryIsStopeByBusId = "queryIsStopeByBusId";

    /**
     * 充完电的账单
     */
    public static String queryBillDatil = "queryBillDatil";

    /**
     * 发表保存站点评论
     */
    public static String saveComment = "saveComment";

    /**
     * 会员账户列表
     */
    public static String queryAccountTwo = "queryAccountTwo";

    /**
     * 站点所有电价详情
     */
    public static String queryElectPriceDatil = "queryElectPriceDatil";

    /**
     * 修改会员信息
     */
    public static String updateMemberInfo = "updateMemberInfo";

    /**
     * 商家消息
     */
    public static String querySellerMessageTwo = "querySellerMessageTwo";

    /**
     * 我的信息页
     */
    public static String getMemberInfo = "getMemberInfo";

    /**
     * 查询车的品牌
     */
    public static String carBrand = "carBrand";

    /**
     * 车辆的类型
     */
    public static String carBrandType = "carBrandType";

    /**
     * 添加车辆
     */
    public static String carImport = "carImport";

    /**
     * 删除车辆
     */
    public static String delCarInfo = "delCarInfo";

    /**
     *修改车辆
     */
    public static String updateCarInfo = "updateCarInfo";

    /**
     * 获取会员身份
     */
    public static String updateRoleList = "updateRoleList";

    /**
     * 城市列表
     */
    public static String cityList = "cityList";

    /**
     *会员信息反馈
     */
    public static String addFeedback = "addFeedback";

    /**
     * 添加话题
     */
    public static String addTopic = "addTopic";

    /**
     * 点赞
     */
    public static String fabulousOfMember = "fabulousOfMember";

    /**
     * 获取话题类型
     */
    public static String getThemeType = "getThemeType";

    /**
     * 获取话题列表
     */
    public static String getTopicList = "getTopicList";

    /**
     * 轮播图
     */
    public static String queryDynamicsActivity = "queryDynamicsActivity";

    /**
     * 枪的电价详情
     */
    public static String queryGunPowDatil = "queryGunPowDatil";

    /**
     * 站点相关桩列表
     */
    public static String queryStationPileDatil= "queryStationPileDatil";

    /**
     * 可退款列表
     */
    public static String refundList = "refundList";

    /**
     * 退款记录
     */
    public static String refundListHis = "refundListHis";

    /**
     * 收支记录（账户流水）
     */
    public static String querRevenue = "querRevenue";

    /**
     * 我的订单
     */
    public static String orderByMember = "orderByMember";

    /**
     * 一键退款
     */
    public static String refundApply = "refundApply";

    /**
     * 获取移动端的token
     */
    public static String getToken = "getToken";

    /**
     * 查询月报记录
     */
    public static String queryMonthReportTwo = "queryMonthReportTwo";

    /**
     * 邀请好友
     */
    public static String getCheckActivity= "getCheckActivity";

    /**
     * 获取发票包含订单信息
     */
    public static String queryConsumes = "queryConsumes";

    /***
     * 登录新的站点列表分页
     */
    public static String pageStation = "pageStation";

    /**
     * 没有登录新的站点列表分页
     */
    public static String pageStationWithoutLogin = "pageStationWithoutLogin";

    /***
     * 地图模式获取所有站点
     */
    public static String cementTruckOnMap = "cementTruckOnMap";

    /**
     *登录获取地图站点详情
     */
    public static String stationSummary = "stationSummary";

    /**
     * 没有登录获取地图站点详情
     */
    public static String stationSummaryWithoutLogin ="stationSummaryWithoutLogin";

    /**
     * 绑定车辆
     */
    public static String getVehicleMember = "getVehicleMember";

    /**
     * 获取商品列表
     */
    public static String productDetailList = "productDetailList";

    /**
     * 商品结算
     */
    public static String productSettlement = "productSettlement";

    /**
     * 判断商品出货是否成功
     */
    public static String queryProductOrderState = "queryProductOrderState";

    /**
     * 售货机商品订单详情
     */
    public static String productBillDetail = "productBillDetail";

    /**
     * 会员规则
     */
    public static String getMemberRuleInfo = "getMemberRuleInfo";

    /**
     * 所在城市对应的id
     */
    public static String getStationArea = "getStationArea";

    /**
     * 登录搜索站点
     */
    public static String searchStation = "searchStation";

    /**
     * 没有登录站点搜索
     */
    public static String searchStationWithoutLogin = "searchStationWithoutLogin";

    /**
     * 首页弹窗
     */
    public static String dynamicsActivityModel = "dynamicsActivityModel";

    /**
     * 余额提醒
     */
    public static String getSystemConfiguration = "getSystemConfiguration";

    /**
     * 获取用商家配置
     */
    public static  String sellerBaseConfigResult = "sellerBaseConfigResult";

    /**
     * 有效/无效电卡
     */

    public static String electricCardsPackage = "electricCardsPackage";

    /**
     * 电卡充电记录
     */
    public static String electricCardsChargingRecord = "electricCardsChargingRecord";

    /**
     *获取会员余额配置
     */
    public static  String getNoticeConfigByMemberId = "getNoticeConfigByMemberId";

    /**
     * 添加或修改余额提醒
     */
    public static String addOrUpdateNoticeConfig = "addOrUpdateNoticeConfig";

    /**
     * 修改默认车辆
     */
    public static String updateDefaultVehicle = "updateDefaultVehicle";

    /**
     * 车辆列表
     */
    public static String getVehicleList = "getVehicleList";

    /**
     * 查询用户余额以及最新完成的账单
     */
    public static String getMemberBalanceBill = "getMemberBalanceBill";

    /**
     * 账户建议充值
     */
    public static String getRecommendedRecharge = "getRecommendedRecharge";

    /**
     * 超时订单详情
     */
    public static String queryOvertimeBill = "queryOvertimeBill";

    /**
     * 获取会员信息
     */
    public static String getMemberReportInfo = "getMemberReportInfo";

    /**
     * 充电中list
     */
    public static String getMemberChargingList = "getMemberChargingList";
}

