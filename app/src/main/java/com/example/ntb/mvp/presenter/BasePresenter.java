package com.example.ntb.mvp.presenter;

import android.content.Context;
import com.example.ntb.mvp.model.viewcallback.BaseCallBack;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.mvp.view.Okhttp3Util;
import java.util.Map;

/**
 * Created by ccy.
 * Date: 2021/8/27
 * Describe :
 */
public class BasePresenter {
    private Okhttp3Util okhttp3Util = Okhttp3Util.getInstance();
    private Context context;
    private BaseView baseView;
    private int successType;//0：get请求 1：get请求（有头部） 2：post请求  3：post请求(有头部) 4：json  5：json(有头部) 6：上传文件  7：上传文件（有头部）

    public BasePresenter(Context context, BaseView baseView) {
        this.baseView = baseView;
        this.context = context;
    }

    /**
     * 异步get请求
     * @param context   上下文
     * @param showWaitMsg   是否展示加载框
     * @param url   ip
     * @param successType   类型（主要用于区别多次请求接口来源）
     */
    public void getRequest(Context context, boolean showWaitMsg, String url, final int successType) {
        okhttp3Util.getSynchronized(context,showWaitMsg,url, new BaseCallBack() {
            @Override
            public void Successful(String success) {
                baseView.resultSucess(successType,success);
            }
            @Override
            public void Failure(String fail) {
                baseView.resultFailure(successType,fail);
            }
        });
    }

    /**
     * 异步get请求(有头部)
     * @param context   上下文
     * @param showWaitMsg   是否展示加载框
     * @param url   ip
     * @param head  头部
     * @param successType   类型（主要用于区别多次请求接口来源）
     */
    public void getRequesttoHead(Context context, boolean showWaitMsg, String url, String head, final int successType) {
        okhttp3Util.getSynchronizedtoHead(context,showWaitMsg,url,head, new BaseCallBack() {
            @Override
            public void Successful(String success) {
                baseView.resultSucess(successType,success);
            }
            @Override
            public void Failure(String fail) {
                baseView.resultFailure(successType,fail);
            }
        });
    }

    /**
     * post方式提交Map
     * @param context   上下文
     * @param url   ip
     * @param showWaitMsg   是否展示加载框
     * @param map   输入map
     * @param successType   类型（主要用于区别多次请求接口来源）
     */
    public void postRequest(Context context, String url, boolean showWaitMsg, Map<String,Object> map, final int successType) {

        okhttp3Util.postMap(context,url,showWaitMsg,map, new BaseCallBack() {
            @Override
            public void Successful(String success) {
                baseView.resultSucess(successType,success);
            }
            @Override
            public void Failure(String fail) {
                baseView.resultFailure(successType,fail);
            }
        });
    }

    /**
     * post方式提交Map(有头部)
     * @param context   上下文
     * @param url   ip
     * @param showWaitMsg   是否展示加载框
     * @param map   输入map
     * @param heard 头部
     * @param requestType   类型（主要用于区别多次请求接口来源）
     */
    public void postRequesttoHead(Context context, String url, boolean showWaitMsg, Map<String,Object> map, String heard, final int requestType) {
        okhttp3Util.postMaptoHead(context,url,showWaitMsg,map,heard, new BaseCallBack() {
            @Override
            public void Successful(String success) {
                baseView.resultSucess(requestType,success);
            }
            @Override
            public void Failure(String fail) {
                baseView.resultFailure(requestType,fail);
            }
        });
    }

    /**
     * post方式提交Json
     * @param context   上下文
     * @param url   ip
     * @param showWaitMsg   是否展示加载框
     * @param json  输入json
     * @param successType    类型（主要用于区别多次请求接口来源）
     */
    public void jsonRequest(Context context, String url, boolean showWaitMsg, String json, final int successType) {
        okhttp3Util.postJson(context, url,showWaitMsg, json, new BaseCallBack() {
            @Override
            public void Successful(String success) {
                baseView.resultSucess(successType,success);
            }
            @Override
            public void Failure(String fail) {
                baseView.resultFailure(successType,fail);
            }
        });
    }

    /**
     *  post方式提交Json （有头部）
     * @param context 上下文
     * @param url   ip
     * @param showWaitMsg  是否展示加载框
     * @param json  输入json
     * @param head  头部
     * @param successType   类型（主要用于区别多次请求接口来源）
     */
    public void jsonRequesttoHead(Context context, String url, boolean showWaitMsg, String json, String head, final int successType) {
        okhttp3Util.postJsontoHead(context, url,showWaitMsg, json, head, new BaseCallBack() {
            @Override
            public void Successful(String success) {
                baseView.resultSucess(successType,success);
            }
            @Override
            public void Failure(String fail) {
                baseView.resultFailure(successType,fail);
            }
        });
    }
}
