package com.example.ntb.ui.vehiclemanagement.acticity;

import android.view.View;

import com.example.ntb.base.BaseActivity;
import com.example.ntb.ui.R;
import com.example.ntb.ui.vehiclemanagement.fragement.AddCarFragment;

import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe ://添加车辆
 */
public class AddCarActivity extends BaseActivity {
    @Override
    protected View getTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.set, new AddCarFragment()).commit();
    }

    @Override
    public void onClick(View view) {
    }
}
