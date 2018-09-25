package com.jiefutong.emall.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.ResourceUtils;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

public class CompanyJoinActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvCount;
    private EditText mEtName;
    private EditText mEtPhone;
    private TextView mEtCity;
    private TextView mTvSubmit;
    private CityPickerView mPicker;
    private String pro, mcity, dist;
    private TextView mTvPhoneFree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_join);
        mPicker = new CityPickerView();
        mPicker.init(this);
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("商家合作");
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtCity = (TextView) findViewById(R.id.et_city);
        mEtCity.setOnClickListener(this);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                if (province != null && district != null && district != null) {
                    pro = province.getName();
                    mcity = city.getName();
                    dist = district.getName();
                    mEtCity.setText(province.getName() + "-" + city.getName() + "-" + district.getName());
                }
            }

            @Override
            public void onCancel() {

            }
        });
        mTvPhoneFree = (TextView) findViewById(R.id.tv_phone_free);
        mTvPhoneFree.setOnClickListener(this);
    }

    private void submit() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入您的店铺名");
            return;
        }

        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入您的手机号");
            return;
        }

        String city = mEtCity.getText().toString().trim();
        if (TextUtils.isEmpty(city)) {
            showToast("请选择您的城市");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("agentName", name);
        params.put("agentTel", phone);
        params.put("province", pro);
        params.put("city", mcity);
        params.put("country", dist);
        HttpUtils.getNetData(HttpUtils.AGENT_ADD, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    showToast("提交成功，会尽快联系您!");
                    finish();
                } else {
                    showToast("添加失败,请重试");
                }
            }
        });


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
            case R.id.et_city:
                showInputMethod(mEtPhone);
                mPicker.showCityPicker();
                break;
            case R.id.tv_phone_free:
                //callPhone("0371-56789997");
                break;
        }
    }

    private void callPhone(final String phone) {
        new PermissionUtil(context, PermissionUtil.PHONE) {
            @Override
            public void onsuccess() {
                new DiaglogUtils(context, ResourceUtils.getString(context, R.string.phone) + phone) {
                    @Override
                    public void sureMessage() {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                };
            }
        };
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
}
