package com.example.ntb.ui.vehiclemanagement.fragement;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ntb.base.BaseFragment;
import com.example.ntb.base.RequestURL;
import com.example.ntb.mvp.presenter.BasePresenter;
import com.example.ntb.mvp.view.BaseView;
import com.example.ntb.ui.R;
import com.example.ntb.ui.home.bean.JsonBase;
import com.example.ntb.ui.util.EventBlack;
import com.example.ntb.ui.util.LoadUtils;
import com.example.ntb.ui.util.SPUtils;
import com.example.ntb.ui.util.ToastUtil;
import com.example.ntb.ui.util.Utils;
import com.example.ntb.ui.vehiclemanagement.bean.CarEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccy.
 * Date: 2021/11/23
 * Describe :添加车辆
 */
public class AddCarFragment extends BaseFragment implements BaseView {

    @BindView(R.id.tv_car_type)
    TextView tv_car_type;

    @BindView(R.id.et_province)
    EditText et_province;//省份

    @BindView(R.id.et_letter)
    EditText et_letter;//字母

    @BindView(R.id.et_number)
    EditText et_number;//数字

    @BindView(R.id.et_vin)
    EditText et_vin;//VIN

    @BindView(R.id.et_mileage)
    EditText et_mileage;//里程


    private BasePresenter basePresenter = new BasePresenter(getActivity(),this);

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_car;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.ll_add_car,R.id.btn_save,R.id.iv_close})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_add_car:
                switchContentAndAddToBackStack(new CarTypeFrament(), "CarTypeFrament");
                break;
            case R.id.iv_close:
                getActivity().finish();
                break;
            case R.id.btn_save:
                String mileage = et_mileage.getText().toString().trim();
                String vin = et_vin.getText().toString();
                String province = et_province.getText().toString();
                String letter = et_letter.getText().toString();
                String number = et_number.getText().toString();
                String new_plateno = province+letter+number;

                if (TextUtils.isEmpty(brandId)) {
                    ToastUtil.showToast(getActivity(),"请选择汽车品牌!");
                    return;
                }
                if (TextUtils.isEmpty(province)||TextUtils.isEmpty(letter)||TextUtils.isEmpty(number)) {
                    ToastUtil.showToast(getActivity(),"请填写车牌号码!");
                    return;
                }
                if (new_plateno.length() < 7 || new_plateno.length() > 8){
                    ToastUtil.showToast(getActivity(),"请输入正确的车牌!");
                    return;
                }

                if (!Utils.isCarNo(new_plateno)) {
                    ToastUtil.showToast(getActivity(),"请输入正确的车牌!");
                    return;
                }
                if (TextUtils.isEmpty(mileage)) {
                    ToastUtil.showToast(getActivity(),"请填写续航里程!");
                    return;
                }

                if (Double.parseDouble(mileage) <= 0){
                    ToastUtil.showToast(getActivity(),"里程数必须大于0!");
                    return;
                }

                if (TextUtils.isEmpty(vin)){
                    ToastUtil.showToast(getActivity(),"请填写VIN码");
                    return;
                }


                String Url = "http://"+RequestURL.new_Url+":"+RequestURL.new_Port+RequestURL.API+RequestURL.saveVehicle;// 添加车辆
                Map<String, Object> map = new HashMap<>();
                map.put("plateNo",new_plateno);//车牌号
                map.put("brandId",brandId+"");//汽车品牌ID
                map.put("brandName",brandName+"");//汽车品牌名称
                map.put("carTypeName",carTypeName+"");//汽车品牌名称
                map.put("carTypeId",carTypeId+"");//汽车车型ID
//                map.put("productionTimeStr",productionTime+"");//汽车生产年份
                map.put("mileage",Integer.parseInt(et_number.getText().toString())+"");//续航里程
//                map.put("vehicleType",vehicleType+"");//车辆类型
                map.put("vin",et_vin.getText().toString()+"");//vin码
                LoadUtils.showWaitProgress(getActivity(),"正在添加车辆...");
                basePresenter.postRequesttoHead(getActivity(),Url,false,map,SPUtils.getSharedStringData(getActivity(),"token")+"",1);
                break;
        }
    }
    /**
     * 汽车品牌ID
     */
    public String brandId;
    /**
     * 汽车品牌名称
     */
    public String brandName;
    /**
     * 汽车车型ID
     */
    public String carTypeId;
    /**
     * 汽车品牌名称
     */
    public String carTypeName;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Subscribe
    public void onCarEvent(CarEvent event) {
        brandId = event.brandId;
        brandName = event.brandName;
        carTypeId = event.carTypeId;
        carTypeName = event.carTypeName;
        tv_car_type.setText(event.brandName + "-" + event.carTypeName);
    }
    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void resultSucess(final int type, final String json) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 1){
                    if (!TextUtils.isEmpty(json)){
                        Gson gson = new Gson();
                        JsonBase jsonBase = gson.fromJson(json.trim(),new TypeToken<JsonBase>(){}.getType());
                        if (jsonBase.code == 0){
                            EventBus.getDefault().post(new EventBlack(3));//添加车辆成功
                            ToastUtil.showToast(getActivity(),jsonBase.msg+"");
                            getActivity().finish();
                        }else {
                            ToastUtil.showToast(getActivity(),jsonBase.msg+"");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void resultFailure(int type, String Msg) {

    }
}
