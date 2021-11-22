package com.example.ntb.ui.home.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ntb.ui.R;
import com.example.ntb.ui.home.fragment.HomePageFragment;
import com.example.ntb.ui.my.fragment.MyFragment;
import com.example.ntb.ui.site.fragment.SiteListFragment;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.NoScrollViewPager;
import com.example.ntb.ui.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import static com.example.ntb.ui.util.StatusBarUtil.setAndroidNativeLightStatusBar;
import static com.example.ntb.ui.util.StatusBarUtil.setStatusBarColor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NoScrollViewPager viewPager;
    private LinearLayout ll_home,ll_map,ll_my;
    private RadioButton rb_home,rb_map,rb_my;

    private List<Fragment> arrayList = new ArrayList<>();
    private HomePageFragment homePageFragment = new HomePageFragment();//首页
    private SiteListFragment siteListFragment = new SiteListFragment();//站点列表
    private MyFragment myFragment = new MyFragment();//站点列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        setStatusBarColor(MainActivity.this,R.color.colorBackground);//修改状态栏颜色
        setAndroidNativeLightStatusBar(MainActivity.this,true);//修改状态栏文字颜色
    }

    private void initListener() {
        ll_home.setOnClickListener(this);
        rb_home.setOnClickListener(this);
        ll_map.setOnClickListener(this);
        rb_map.setOnClickListener(this);
        ll_my.setOnClickListener(this);
        rb_my.setOnClickListener(this);
    }

    /**
     * 初始化View
     */
    private void initView() {

        ll_home =(LinearLayout) findViewById(R.id.ll_home);
        rb_home =(RadioButton) findViewById(R.id.rb_home);
        ll_map =(LinearLayout) findViewById(R.id.ll_map);
        rb_map =(RadioButton) findViewById(R.id.rb_map);
        ll_my =(LinearLayout) findViewById(R.id.ll_my);
        rb_my =(RadioButton) findViewById(R.id.rb_my);
        viewPager =(NoScrollViewPager) findViewById(R.id.viewPager);
        viewPager.setPagingEnabled(false);
        viewPager.setOffscreenPageLimit(3);
        arrayList.add(homePageFragment);
        arrayList.add(siteListFragment);
        arrayList.add(myFragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), arrayList));
        initFragment(0);
    }

    private void initFragment(int i) {
        switch (i){
            case 0:
                rb_home.setChecked(true);
                rb_map.setChecked(false);
                rb_my.setChecked(false);
                viewPager.setCurrentItem(0);
                break;
            case 1:
                rb_map.setChecked(true);
                rb_home.setChecked(false);
                rb_my.setChecked(false);
                viewPager.setCurrentItem(1);
                break;
            case 2:
                rb_my.setChecked(true);
                rb_home.setChecked(false);
                rb_map.setChecked(false);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
            case R.id.rb_home:
                initFragment(0);
                setStatusBarColor(MainActivity.this,R.color.colorBackground);
                setAndroidNativeLightStatusBar(MainActivity.this,true);//修改状态栏文字颜色
                break;
            case R.id.ll_map:
            case R.id.rb_map:
                initFragment(1);
                setStatusBarColor(MainActivity.this,R.color.colorWhite);
                setAndroidNativeLightStatusBar(MainActivity.this,true);//修改状态栏文字颜色
                break;
            case R.id.ll_my:
            case R.id.rb_my:
                initFragment(2);
                setStatusBarColor(MainActivity.this,R.color.colorTheme);
                setAndroidNativeLightStatusBar(MainActivity.this,false);//修改状态栏文字颜色
                break;
        }
    }
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*判断用户是否点击了“返回键”*/
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast(MainActivity.this,"再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
