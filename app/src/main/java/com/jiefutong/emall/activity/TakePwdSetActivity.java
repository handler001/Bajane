package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

public class TakePwdSetActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtPhone;
    private EditText mEtCode;
    private TextView mTvCodeShow;
    private EditText mEtPwd;
    private EditText mEtPwdSure;
    private TextView mTvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pwd_set);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("交易密码设置");
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtCode = (EditText) findViewById(R.id.et_code);
        mTvCodeShow = (TextView) findViewById(R.id.tv_code_show);
        mTvCodeShow.setOnClickListener(this);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mEtPwdSure = (EditText) findViewById(R.id.et_pwd_sure);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
    }

    private void submit() {
        String code = mEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            showToast("请输入验证码");
            return;
        }

        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            showToast("请输入6位交易密码");
            return;
        }

        String sure = mEtPwdSure.getText().toString().trim();
        if (TextUtils.isEmpty(sure)) {
            showToast("请再次输入6位交易密码");
            return;
        }

        if (!sure.equals(pwd)) {
            showToast("2次交易密码不一致,请重新输入");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("code", code);
        params.put("transPassword", sure);
        HttpUtils.getNetData(HttpUtils.PWD_EXCHANGE_SET, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    showToast("设置成功");
                    EventBusBean beans = new EventBusBean();
                    beans.cod = "bank";
                    EventBus.getDefault().post(beans);
                    finish();
                } else {
                    showToast("设置失败,请重试");
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
            case R.id.tv_code_show:
                getpwdcode();
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    private void getpwdcode() {
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        HttpUtils.getNetData(HttpUtils.PWD_CODE_GET, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        new CountDownTimer(120000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                mTvCodeShow.setEnabled(false);
                                mTvCodeShow.setText(millisUntilFinished / 1000 + "秒后重试");
                            }

                            @Override
                            public void onFinish() {
                                mTvCodeShow.setEnabled(true);
                                mTvCodeShow.setText("获取验证码");

                            }
                        }.start();
                    } else {
                        showToast(bean.code);
                    }
                } else {
                    showToast("获取失败,请重试");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
