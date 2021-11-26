package com.example.ntb.mvp.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.example.ntb.mvp.model.viewcallback.BaseCallBack;
import com.example.ntb.ui.util.LoadUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by ccy.
 * Date: 2021/8/27
 * Describe :
 */
public class Okhttp3Util {

    private static Okhttp3Util instance = new Okhttp3Util();//单例
    private Dialog dialog;
    private Context mContext;

    public static Okhttp3Util getInstance() {
        return instance;
    }
    /**
     * 超时设置
     */
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60 * 250, TimeUnit.MILLISECONDS)
            .readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
            .writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
            .build();
    /**
     * 异步get请求(没有头部)
     * @param context
     * @param showWaitMsg
     * @param url
     * @param callback
     */
    public void getSynchronized(final Context context, final boolean showWaitMsg, final String url  , final BaseCallBack callback) {
        mContext = context;
        if (showWaitMsg == true){
            LoadUtils.showWaitProgress(context,"");
        }
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException){
                    callback.Failure("异步get请求连接超时");
                }
                if(e instanceof ConnectException){
                    callback.Failure("异步get请求连接异常");
                }
                if (dialog != null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (dialog != null){
                    dialog.dismiss();
                }
                try {
                    if (response.isSuccessful() && response.code() == 200) {
                        String json = response.body().string();
                        Log.d("请求路径",url+"出参："+json);
                        if (!TextUtils.isEmpty(json)){
                            callback.Successful(json);
                        }
                    } else {
                        callback.Failure("数据格式有误!");
                    }
                }catch (Throwable throwable){
                    callback.Failure("数据格式有误");
                    Log.d("Catch",throwable.toString());
                }
            }
        });
    }
    /**
     * 异步get请求(有头部)
     * @param context
     * @param showWaitMsg
     * @param url
     * @param head
     * @param callback
     */
    public void getSynchronizedtoHead(final Context context, final boolean showWaitMsg, final String url , final String head , final BaseCallBack callback) {
        mContext = context;
        if (showWaitMsg == true){
            LoadUtils.showWaitProgress(context,"");
        }
        Request request = new Request.Builder().url(url).addHeader("Authorization",head).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException){ //判断超时异常
                    Log.d("异步get请求","连接超时"+url);
                    callback.Failure("连接超时");
                }
                if(e instanceof ConnectException){//判断连接异常
                    Log.d("异步get请求","连接异常"+url);
                    callback.Failure("连接异常");
                }
                if (dialog != null){
                    dialog.dismiss();
                }
                LoadUtils.dissmissWaitProgress();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (dialog != null){
                    dialog.dismiss();
                }
                LoadUtils.dissmissWaitProgress();
                try {
                    if (response.isSuccessful() && response.code() == 200) {
                        String json = response.body().string();
                        Log.d("请求路径",url+"出参："+json);
                        if (!TextUtils.isEmpty(json)){
                            callback.Successful(json);
                        }
                    } else {
                        callback.Failure("数据格式有误!");
                    }
                }catch (Throwable throwable){
                    callback.Failure("数据格式有误");
                    Log.d("Catch",throwable.toString());
                }

            }
        });
    }
    /**
     * post方式提交Map
     * @param context
     * @param url
     * @param map
     * @param callback
     */
    public void postMap(final Context context, final String url, boolean showWaitMsg, final Map<String,Object> map, final BaseCallBack callback) {

        mContext = context;
        if (showWaitMsg == true){
            LoadUtils.showWaitProgress(context,"");
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            //增强for循环遍历
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().post(formBody).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException){ //判断超时异常
                    Log.d("post方式提交Map","连接超时"+url);
                    callback.Failure("连接超时");
                }
                if(e instanceof ConnectException){//判断连接异常
                    Log.d("v","连接异常"+url);
                    callback.Failure("连接异常");
                }
                LoadUtils.dissmissWaitProgress();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadUtils.dissmissWaitProgress();
                String json = response.body().string();
                Log.d("请求路径",url+"====入参===="+map.toString()+"====出参===="+json);
                try {
                    if (response.isSuccessful() && response.code() == 200) {
                        if (!TextUtils.isEmpty(json)){
                            callback.Successful(json);
                        }
                    } else {
                        callback.Failure("数据格式有误!");
                    }
                }catch (Throwable throwable){
                    callback.Failure("数据格式有误");
                    Log.d("Catch",throwable.toString());
                }
            }
        });
    }
    /**
     * post方式提交Map(有头部)
     * @param context
     * @param url
     * @param map
     * @param head
     * @param callback
     */
    public void postMaptoHead(final Context context, final String url, boolean showWaitMsg, final Map<String, Object> map, String head, final BaseCallBack callback) {
        mContext = context;
        if (showWaitMsg == true){
            LoadUtils.showWaitProgress(context,"加载中，请稍后...");
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            //增强for循环遍历
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        FormBody formBody = builder.build();
        final Request request = new Request.Builder().post(formBody).url(url).addHeader("Authorization",head).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                try {
                    if(e instanceof SocketTimeoutException){ //判断超时异常
                        Log.d("post方式提交Map","连接超时"+url);
                        callback.Failure("连接超时");
                    }
                    if(e instanceof ConnectException){//判断连接异常
                        Log.d("post方式提交Map","连接异常"+url);
                        callback.Failure("连接异常");
                    }
                    LoadUtils.dissmissWaitProgress();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
            @Override
            public void onResponse (Call call, Response response) throws IOException {
                LoadUtils.dissmissWaitProgress();
                final String json = response.body().string();
                Log.d("请求是否成功",response.isSuccessful()+"===状态码==="+response.code()+"===请求路径==="+url+"====入参===="+map.toString()+"====出参===="+json);
                try {
                    if (response.isSuccessful() && response.code() == 200) {
                        if (!TextUtils.isEmpty(json)){
                            //{"timestamp":"2021-11-08T03:25:25.042+00:00","path":"/api-service/api/getMemberInfo","status":404,"error":"Not Found","message":null,"requestId":"781ada67-335643"}
                            if (json.contains("404")&&json.contains("Not Found")){
                                callback.Failure("数据格式有误!");
                            }else {
                                callback.Successful(json);
                            }
                        }
                    } else {
                        callback.Failure("数据格式有误!");
                    }
                }catch (Throwable throwable){
                    callback.Failure("数据格式有误");
                    Log.d("Catch",throwable.toString());
                }
            }
        });
    }

    /**
     * post方式提交Json
     * @param context
     * @param url
     * @param json
     * @param callback
     */
    public void postJson(final Context context, final String url, boolean showWaitMsg, String json, final BaseCallBack callback) {
        mContext = context;

        if (showWaitMsg == true){
            LoadUtils.showWaitProgress(context,"");
        }
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException){ //判断超时异常
                    Log.d("post方式提交Json","连接超时"+url);
                    callback.Failure("连接超时");
                }
                if(e instanceof ConnectException){//判断连接异常
                    Log.d("post方式提交Json","连接异常"+url);
                    callback.Failure("连接异常");
                }
                LoadUtils.dissmissWaitProgress();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadUtils.dissmissWaitProgress();
                try {
                    String json = response.body().string();

                    Log.d("请求是否成功",response.isSuccessful()+"===状态码==="+response.code()+"===请求路径==="+url+"====入参===="+json.toString()+"====出参===="+json);

                    Log.d("请求路径",url+"出参："+json);
                    if (response.isSuccessful() && response.code() == 200) {
                        if (!TextUtils.isEmpty(json)){
                            callback.Successful(json);
                        }
                    } else {
                        callback.Failure("数据格式有误!");
                    }
                }catch (Throwable throwable){
                    callback.Failure("数据格式有误");
                    Log.d("Catch",throwable.toString());
                }
            }
        });
    }
    /**
     * post方式提交Json （有头部）
     * @param context
     * @param url
     * @param Json
     * @param head
     * @param callback
     */
    public void postJsontoHead(final Context context, final String url, boolean showWaitMsg, final String Json, String head, final BaseCallBack callback) {

        mContext = context;
        if (showWaitMsg == true){
            LoadUtils.showWaitProgress(context,"");
        }
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), Json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization",head)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException){ //判断超时异常
                    Log.d("post提交json","连接超时"+url);
                    callback.Failure("连接超时");
                }
                if(e instanceof ConnectException){//判断连接异常
                    Log.d("post提交json","连接异常"+url);
                    callback.Failure("连接异常");
                }
                LoadUtils.dissmissWaitProgress();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadUtils.dissmissWaitProgress();
                try {
                    String json = response.body().string();
                    Log.d("请求是否成功",response.isSuccessful()+"===状态码==="+response.code()+"===请求路径==="+url+"====入参===="+Json+"====出参===="+json);
                    if (response.isSuccessful() && response.code() == 200) {
                        if (!TextUtils.isEmpty(json)){
                            callback.Successful(json);
                        }
                    } else {
                        callback.Failure("数据格式有误!");
                    }
                }catch (Throwable throwable){
                    callback.Failure("数据格式有误");
                    Log.d("Catch",throwable.toString());
                }
            }
        });
    }

    /**
     * 上传文件
     * @param context
     * @param url
     * @param file
     * @param callback
     */
    private void postFile(final Context context, final String url, boolean showWaitMsg, File file, final BaseCallBack callback) {
        mContext = context;
        if (showWaitMsg == true){
            LoadUtils.showWaitProgress(context,"");
        }
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException){ //判断超时异常
                    Log.d("上传文件","连接超时"+url);
                    callback.Failure("连接超时");
                }
                if(e instanceof ConnectException){//判断连接异常
                    Log.d("上传文件","连接异常"+url);
                    callback.Failure("连接异常");
                }
                LoadUtils.dissmissWaitProgress();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadUtils.dissmissWaitProgress();
                try {
                    String json = response.body().string();
                    Log.d("请求路径",url+"出参："+logjson(json));
                    if (response.isSuccessful() && response.code() == 200) {
                        if (!TextUtils.isEmpty(json)){
                            callback.Successful(json);
                        }
                    } else {
                        callback.Failure("数据格式有误!");
                    }
                }catch (Throwable throwable){
                    callback.Failure("数据格式有误");
                    Log.d("Catch",throwable.toString());
                }
            }
        });
    }
    /**
     * 上传文件(有头部)
     * @param context
     * @param url
     * @param file
     * @param head
     * @param callback
     */
    private void postFiletoHead(final Context context, final String url, boolean showWaitMsg, File file, String head, final BaseCallBack callback) {

        if (showWaitMsg == true){
            LoadUtils.showWaitProgress(context,"");
        }
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .header("Authorization",head)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException){ //判断超时异常
                    Log.d("post提交json","连接超时"+url);
                    callback.Failure("连接超时");
                }
                if(e instanceof ConnectException){//判断连接异常
                    Log.d("post提交json","连接异常"+url);
                    callback.Failure("连接异常");
                }
                LoadUtils.dissmissWaitProgress();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadUtils.dissmissWaitProgress();
                try {
                    final String json = response.body().string();
                    Log.d("请求路径",url+"出参："+json);
                    if (response.isSuccessful() && response.code() == 200) {
                        if (!TextUtils.isEmpty(json)){
                            callback.Successful(json);
                        }
                    } else {
                        callback.Failure("数据格式有误");
                    }
                }catch (Throwable throwable){
                    callback.Failure("数据格式有误");
                    Log.d("Catch",throwable.toString());
                }
            }
        });
    }
    public static String logjson(String message) {
        Logger.json(message);
        return message;
    }
}

