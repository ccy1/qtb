package com.example.ntb.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :工具
 */
public class Utils {
    /**
     * 判断是否是车牌号
     *
     * @param CarNum
     * @return false 正确 true 不正确
     */
    public static boolean isCarNo(String CarNum) {
        //匹配第一位汉字
        String str = "京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼甲乙丙己庚辛壬寅辰戍午未申";
        if (!(CarNum == null || CarNum.equals(""))) {
            String s1 = CarNum.substring(0, 1);//获取字符串的第一个字符
            if (str.contains(s1)) {
                String s2 = CarNum.substring(1, CarNum.length());
                //不包含I O i o的判断
                if (s2.contains("I") || s2.contains("i") || s2.contains("O") || s2.contains("o")) {
                    return false;
                } else {
                    if (CarNum.matches("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5,6}$")) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * @param carnumber 车牌号码
     * @return true 正确 、false 不正确
     */
    public static boolean isCarnumberNO(String carnumber) {
       /*
       1.常规车牌号：仅允许以汉字开头，后面可录入六个字符，由大写英文字母和阿拉伯数字组成。如：粤B12345；
       2.武警车牌：允许前两位为大写英文字母，后面可录入五个或六个字符，由大写英文字母和阿拉伯数字组成，其中第三位可录汉字也可录大写英文字母及阿拉伯数字，第三位也可空，如：WJ警00081、WJ京1234J、WJ1234X。
       3.最后一个为汉字的车牌：允许以汉字开头，后面可录入六个字符，前五位字符，由大写英文字母和阿拉伯数字组成，而最后一个字符为汉字，汉字包括“挂”、“学”、“警”、“军”、“港”、“澳”。如：粤Z1234港。
       4.新军车牌：以两位为大写英文字母开头，后面以5位阿拉伯数字组成。如：BA12345。
       */
        String carnumRegex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$";
        if (TextUtils.isEmpty(carnumber)) return false;
        else return carnumber.matches(carnumRegex);
    }


    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * 设置光标位置
     *
     * @param editText
     */
    public static void setSelection(EditText editText) {
        editText.setSelection(editText.getText().length());
    }

    /**
     * 隐藏软键盘
     *
     * @param editText 输入框
     * @param activity
     */
    public static void hideSoftKeyBoard(EditText editText, Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 打印
     *
     * @param tag
     * @param str
     */
    public static void out(String tag, String str) {
        System.out.println("----===---------" + tag + "====" + str);
    }


    /**
     * 获取当前应用的版本号
     *
     * @return
     */
    public static String getVersionCode(Context context) {
        try {
            // 取得包管理器
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    /**
     * 替换号码中间4位*字符
     *
     * @param pNumber
     * @return
     */
    public static String replaceNum2Star(String pNumber) {
        if (!TextUtils.isEmpty(pNumber) && pNumber.length() == 11) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return pNumber;
    }

    /**
     * 获取屏幕宽
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        return screenWidth;
    }

    /**
     * 获取屏幕高
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        return screenHeight;
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * bitmap转流
     *
     * @param bm
     * @return
     */
    private InputStream Bitmap2IS(Bitmap bm) {
        ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, mBaos);
        InputStream mSbs = new ByteArrayInputStream(mBaos.toByteArray());
        return mSbs;
    }

    /**
     * 读取流作为字符串返回
     *
     * @param is 流
     * @return
     * @throws IOException
     */
    public static String readFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {// 等于-1时，读取完毕
            baos.write(buffer, 0, len);
        }
        String result = baos.toString("utf-8");
        is.close();
        baos.close();
        return result;
    }

    /**
     * 获取当前日期
     */
    public static String getData() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }


    /**
     * 获取当前时间
     */
    public static String getTime() {
        return new SimpleDateFormat("HHmmss").format(new Date());
    }


    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }


    public static String splitDateString(String date) {
        //1942年
        return date.split(" ")[0];
    }

    /**
     * 将dip转换为px
     *
     * @return
     */
    public static int dip2Px(Context context, float dip) {
        return (int) (context.getResources().getDisplayMetrics().density * dip);
    }

    /**
     * 将px转换为dip
     *
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 手机号验证
     *
     * @param //str
     * @return 验证通过返回true
     */
    public static boolean isMobelPhone(String tel) {
        if (tel == null || tel.length() < 11) {
            return false;
        }
        Pattern p = Pattern
                .compile("^1[3|4|5|6|7|8|9]\\d{9}$");
        Matcher m = p.matcher(tel);
        return m.matches();
    }


    /**
     * 判断当前日期是星期几
     *
     * @param pTime 格式如2012-09-08
     * @return
     */
    public static String getWeek(String pTime) {
        String Week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 1) {
            Week += "天";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 2) {
            Week += "一";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 3) {
            Week += "二";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 4) {
            Week += "三";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 5) {
            Week += "四";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 6) {
            Week += "五";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 7) {
            Week += "六";
        }
        return Week;
    }

    /**
     * 检查应用是否有安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        PackageManager pageManage = context.getPackageManager();
        List<PackageInfo> packages = pageManage.getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            String appName = packageInfo.applicationInfo.loadLabel(
                    context.getPackageManager()).toString();
            String pagName = packageInfo.packageName;
            if (appName.contains(packageName)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isPackageInstalled(Context mContext, String packagename) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        } finally {
            return packageInfo == null ? false : true;
        }
    }


    /**
     * @author Administrator
     * @time 2019/3/19  13:34
     * @describe 百度转高德（百度坐标bd09ll–>火星坐标gcj02ll）
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat, tempLon};
        return gps;
    }

    /**
     * @author Administrator
     * @time 2019/3/19  13:35
     * @describe 高德转百度（火星坐标gcj02ll–>百度坐标bd09ll）
     */
    public static double[] gaoDeToBaidu(double gd_lat, double gd_lon) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat, tempLon};
        return gps;
    }

    /**
     * 纯手动加载JSON
     *"SiteElectricInfo.json"    BespeakListInfo.json
     * @return json
     */
    public static String InitJsonData(Context context,String stringjson) {
        StringBuffer sb = new StringBuffer();
        AssetManager mAssetManager = context.getAssets();
        try {
            InputStream is = mAssetManager.open(stringjson+".json");
            byte[] data = new byte[is.available()];
            int len = -1;
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len, "utf-8"));
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void idd(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.d(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.d(tag, msg);
    }


    public static boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }

}

