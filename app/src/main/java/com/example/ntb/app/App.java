package com.example.ntb.app;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.amap.api.navi.model.NaviLatLng;
import com.example.ntb.ui.db.DaoMaster;
import com.example.ntb.ui.db.DaoSession;
import com.example.ntb.ui.my.bean.PersonInfo;

/**
 * Created by ccy.
 * Date: 2021/10/22
 * Describe :
 */
public class App extends Application{

    private static App mInstance;
    private static Context context;
    private String phoneNo;//用户手机号
    public boolean isFirstLoc = true;//是否已定位到当前位置
    public NaviLatLng whereLocation;//用户所在的位置
    private PersonInfo PersonInfo;
    public DaoSession daoSession;//本地数据库管理
    public String whereCityName;//用户所在城市名称
    public String token;//token

    public String getToken(){
        return token;
    }

    public void setToken (String token){
        this.token = token;
    }

    public PersonInfo getPersonInfoTwo() {
        return PersonInfo;
    }

    public void setPersonInfoTwo(PersonInfo personInfoTwo) {
        this.PersonInfo = personInfoTwo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mInstance = this;
        setupDatabase();
    }

    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "cdw.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    /**
     * 获取 app
     * @return
     */
    public static App getInstance() {
        if (mInstance == null){
            mInstance = new App();
        }
        return mInstance;
    }
}

