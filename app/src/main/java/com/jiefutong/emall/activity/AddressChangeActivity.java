package com.jiefutong.emall.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AddressInfosBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;


public class AddressChangeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtName;
    private EditText mEtPhone;
    private TextView mTvAddress;
    private ImageView mIvAddressEnter;
    private EditText mEtAddressDetail;
    private TextView mTvSave;
    private String name;
    private AddressInfosBean.DataMapBean.ContentBean bean;
    private CityPickerView mPicker;
    private String pro, cy, dis;
    private Switch mShMsg;
    private boolean defalut;
    private RelativeLayout mRlDef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_change);
        settitlewhite();
        mPicker = new CityPickerView();
        mPicker.init(this);
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);
        initView();
    }

    private void initView() {
        name = getIntent().getStringExtra("name");
        bean = (AddressInfosBean.DataMapBean.ContentBean) getIntent().getSerializableExtra("bean");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(name);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mIvAddressEnter = (ImageView) findViewById(R.id.iv_address_enter);
        mIvAddressEnter.setOnClickListener(this);
        mEtAddressDetail = (EditText) findViewById(R.id.et_address_detail);
        mTvSave = (TextView) findViewById(R.id.tv_submit);
        mTvSave.setOnClickListener(this);
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                if (province != null && district != null && district != null) {
                    pro = province.getName();
                    cy = city.getName();
                    dis = district.getName();
                    mTvAddress.setText(province.getName() + "-" + city.getName() + "-" + district.getName());
                }
            }

            @Override
            public void onCancel() {

            }
        });
        mShMsg = (Switch) findViewById(R.id.sh_msg);
        mShMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                defalut = isChecked;
            }
        });
        mShMsg.setChecked(defalut);
        mRlDef = (RelativeLayout) findViewById(R.id.rl_def);
        if (bean != null) {
            mRlDef.setVisibility(View.GONE);
            mEtName.setText(bean.receiveName);
            mEtPhone.setText(bean.receiveTel);
            mTvAddress.setText(bean.receiveProvince + "-" + bean.receiveCity + "-" + bean.receiveCountry);
            mEtAddressDetail.setText(bean.receiveDetail);
        }
    }

    private void submit() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请填写收货人姓名");
            return;
        }

        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请填写可以联系到您的电话");
            return;
        }

        String city = mTvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(city)) {
            showToast("请选择省市区");
            return;
        }

        String detail = mEtAddressDetail.getText().toString().trim();
        if (TextUtils.isEmpty(detail)) {
            showToast("请填写详细地址，具体到门牌号");
            return;
        }
        if (bean == null) {
            addAddress(name, phone, detail);
        } else {
            changeaddress(name, phone, detail);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                submit();
                break;
            case R.id.iv_address_enter:
            case R.id.tv_address:
                showInputMethod(mEtPhone);
                mPicker.showCityPicker();
                break;
        }
    }

    private void showInputMethod(View view) {
        //自动弹出键盘
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //强制隐藏Android输入法窗口
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    private void addAddress(String name, String phone, String detail) {
        HttpParams params = new HttpParams();
        params.put("receiveName", name);
        params.put("receiveTel", phone);
        params.put("receiveDetail", detail);
        params.put("receiveProvince", pro);
        params.put("receiveCity", cy);
        params.put("receiveCountry", dis);
        params.put("defaultAddr", defalut);
        HttpUtils.getNetData(HttpUtils.ADDRESS_INFO_ADD, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    setResult(100);
                    finish();
                } else {
                    showToast("添加失败");
                }
            }
        });
    }

    private void changeaddress(String name, String phone, String detail) {
        HttpParams params = new HttpParams();
        params.put("id", bean.id);
        params.put("receiveName", name);
        params.put("receiveTel", phone);
        params.put("receiveDetail", detail);
        params.put("receiveProvince", pro);
        params.put("receiveCity", cy);
        params.put("receiveCountry", dis);
        params.put("defaultAddr", bean.defaultAddr);
        HttpUtils.getNetData(HttpUtils.ADDRESS_INFO_EDIT, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    setResult(100);
                    finish();
                } else {
                    showToast("修改失败");
                }
            }
        });
    }
}
