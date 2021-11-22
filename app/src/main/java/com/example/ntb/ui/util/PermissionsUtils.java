package com.example.ntb.ui.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by ccy.
 * Date: 2021/10/25
 * Describe :权限列表
 */
public class PermissionsUtils {
    public static final int REQUEST_CODE = 1111;
    /**
     * 录音需要的权限
     */
    public static String[]permissionsRecord={
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     *  读写权限
     */
    public static String[]permissionsREAD={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    /**
     *  读写权限+相机权限
     */
    public static String[]permissionsREADAndCamera={
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    /**
     *  定位权限
     */
    public static String[]permissionsPositioning={
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    /**
     *  拨打电话权限
     */
    public static String[]permissionsCallPhone={
            Manifest.permission.CALL_PHONE,
    };

    private String[]mPermissions;

    private static Activity mActivity;

    public PermissionsUtils(Activity activity) {
        mActivity = activity;
    }

    public static PermissionsUtils from(Activity activity){
        return new PermissionsUtils(activity);
    }

    public boolean setPermissions(String[] permissions) {
        this.mPermissions = permissions;
        return lacksPermissions();
    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示权限已开启  false-表示没有改权限
     */
    public boolean lacksPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : mPermissions) {
                if (lacksPermission(permission)) {
//                    ActivityCompat.requestPermissions(mActivity, mPermissions, REQUEST_CODE);
//                    forResult();
                    return false;
                }
            }
            return true;
        }else {
            return true;
        }
    }

    /**
     * 判断是否缺少权限
     */
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_DENIED;
    }

    public void forResult(){
        ActivityCompat.requestPermissions(mActivity,mPermissions,REQUEST_CODE);
    }

    /**
     * 回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param isStart 是否跳转开启权限界面  true跳转  false 不跳转
     * @return
     */


    public static boolean onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, boolean isStart) {
        boolean bool = true;
        if (requestCode == PermissionsUtils.REQUEST_CODE){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1){
                    bool = false;
                    if (TextUtils.equals(permissions[i],Manifest.permission.READ_EXTERNAL_STORAGE)
                            || TextUtils.equals(permissions[i],Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        Toast.makeText(mActivity, "请开启手机读写权限", Toast.LENGTH_SHORT).show();
                    }else if ( TextUtils.equals(permissions[i],Manifest.permission.CAMERA)){
                        Toast.makeText(mActivity, "请开启手机相机权限", Toast.LENGTH_SHORT).show();
                    }else if ( TextUtils.equals(permissions[i],Manifest.permission.ACCESS_COARSE_LOCATION)
                            ||  TextUtils.equals(permissions[i],Manifest.permission.ACCESS_FINE_LOCATION)){
                        Toast.makeText(mActivity, "请开启手机定位权限", Toast.LENGTH_SHORT).show();
                    }else if ( TextUtils.equals(permissions[i],Manifest.permission.CALL_PHONE)){
                        Toast.makeText(mActivity, "请开启手机拨号权限", Toast.LENGTH_SHORT).show();
                    }
                    if (isStart==true){
                        start();
                    }
                    break;
                }
            }
            if (grantResults.length>0&&bool){
                bool = true;
            }else {
                bool = false;
            }
        }else {
            bool = false;
        }
        return bool;
    }

    private static void start(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        mActivity.startActivity(intent);
    }

}

