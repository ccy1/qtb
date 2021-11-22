package com.example.ntb.ui.scan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import com.example.ntb.ui.R;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import java.util.List;
import static com.example.ntb.ui.util.StatusBarUtil.setAndroidNativeLightStatusBar;
import static com.example.ntb.ui.util.StatusBarUtil.setStatusBarColor;

/**
 * Created by ccy.
 * Date: 2021/10/20
 * Describe :扫码
 */
public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    private CaptureManager camera;
    private DecoratedBarcodeView bv_barcode;
    private RadioButton rb_light;
    private boolean flag = true;//是否开启了闪光灯

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        setStatusBarColor(ScanActivity.this,R.color.colorBackground);
        setAndroidNativeLightStatusBar(ScanActivity.this,true);//修改状态栏文字颜色

        bv_barcode = (DecoratedBarcodeView) findViewById(R.id.bv_barcode);
        rb_light =(RadioButton) findViewById(R.id.rb_light);
        rb_light.setOnClickListener(this);
        camera = new CaptureManager(this, bv_barcode);
        camera.initializeFromIntent(getIntent(), savedInstanceState);
        camera.decode();
        bv_barcode.decodeContinuous(barcodeCallback);
    }
    @Override
    protected void onResume() {
        super.onResume();
        camera.onResume();
        bv_barcode.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.onPause();
        bv_barcode.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        camera.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return bv_barcode.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    }
    private BarcodeCallback barcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            bv_barcode.pause();
            if (result != null){
                vibrate();//打开震动模式
                bv_barcode.resume();
            }
        }
        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_light:
                light();//开启关闭闪光灯
                break;
        }
    }
    /**
     * 开启关闭闪光灯
     */
    private void light() {
        if (flag) {
            flag = false;
            // 开闪光灯
            bv_barcode.setTorchOn();
            rb_light.setChecked(true);
        } else {
            flag = true;
            // 关闪光灯
            bv_barcode.setTorchOff();
            rb_light.setChecked(false);
        }
    }
    /**
     * 扫码成功开启震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
