package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AliInfoBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

public class AliBindingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtName;
    private ImageView mIvName;
    private EditText mEtPhone;
    private ImageView mIvPhone;
    //    private EditText mEtPwd;
//    private TextView mTvGetsms;
    private TextView mTvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_binding);
        initView();
        settitlewhite();
        loaddata();
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.INFO_ALI_GET, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                AliInfoBean bean = JsonUtil.parseObject(data, AliInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap != null) {
                        mEtName.setText(bean.dataMap.alipayName);
                        mEtPhone.setText(bean.dataMap.alipayAccount);
                    }
                }
            }
        });
    }


    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("支付宝绑定");
        mEtName = (EditText) findViewById(R.id.et_name);
        mIvName = (ImageView) findViewById(R.id.iv_name);
        mIvName.setOnClickListener(this);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mIvPhone = (ImageView) findViewById(R.id.iv_phone);
        mIvPhone.setOnClickListener(this);
//        mEtPwd = (EditText) findViewById(R.id.et_pwd);
//        mTvGetsms = (TextView) findViewById(R.id.tv_getsms);
//        mTvGetsms.setOnClickListener(this);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
    }

    private void submit() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入支付宝用户名称");
            return;
        }

        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入支付宝账号");
            return;
        }
//        String pwd = mEtPwd.getText().toString().trim();
//        if (TextUtils.isEmpty(pwd)) {
//            showToast("请输入验证码");
//            return;
//        }
        HttpParams params = new HttpParams();
        params.put("accountName", name);
        params.put("alipayAccount", phone);
        HttpUtils.getNetData(HttpUtils.ALI_BINDING, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean commonBean = JsonUtil.parseObject(data, CommonBean.class);
                if (commonBean != null) {
                    if (commonBean.code == HttpUtils.SUCCESS) {
                        showToast("绑定成功");
                        EventBusBean bean = new EventBusBean();
                        bean.cod = "bank";
                        EventBus.getDefault().post(bean);
                        finish();
                    } else {
                        showToast(commonBean.message);
                    }
                } else {
                    showToast("绑定失败,请重试");
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
            case R.id.iv_name:
                mEtName.setText("");
                break;
            case R.id.iv_phone:
                mEtPhone.setText("");
                break;
            case R.id.tv_getsms:
                showToast("获取验证码");
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
