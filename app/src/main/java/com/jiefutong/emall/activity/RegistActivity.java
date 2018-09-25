package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class RegistActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtPhone;
    private ImageView mIvClear;
    private EditText mEtCode;
    private TextView mTvCodeShow;
    private EditText mEtPwd;
    private ImageView mIvShow;
    private EditText mEtPeople;
    private TextView mTvRegist;
    private boolean show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("注册");
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvClear.setOnClickListener(this);
        mEtCode = (EditText) findViewById(R.id.et_code);
        mTvCodeShow = (TextView) findViewById(R.id.tv_code_show);
        mTvCodeShow.setOnClickListener(this);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mIvShow = (ImageView) findViewById(R.id.iv_show);
        mIvShow.setOnClickListener(this);
        mEtPeople = (EditText) findViewById(R.id.et_people);
        mTvRegist = (TextView) findViewById(R.id.tv_regist);
        mTvRegist.setOnClickListener(this);
        mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    private void submit() {
        // validate
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showmessage(R.string.edit_phone);
            return;
        }

        String code = mEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            showmessage(R.string.edit_code);
            return;
        }

        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            showmessage(R.string.edit_pwd);
            return;
        }

        String people = mEtPeople.getText().toString().trim();
        if (TextUtils.isEmpty(people)) {
            showmessage(R.string.edit_people);
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        params.put("regCode", code);
        params.put("password", pwd);
        params.put("refer", people);
        HttpUtils.getNetData(HttpUtils.USER_REGIST, this, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                CommonBean bean = JsonUtil.parseObject(response.body(), CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        showToast(bean.message);
                        finish();
                    } else {
                        showToast(bean.message);
                    }
                } else {
                   showDataError();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                showNetError();
            }

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                showPb();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissPb();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                mEtPhone.setText("");
                break;
            case R.id.tv_regist:
                submit();
                break;
            case R.id.iv_show:
                if (show) {
                    //显示
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mIvShow.setBackgroundResource(R.mipmap.btn_xianshi);
                } else {
                    //隐藏
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mIvShow.setBackgroundResource(R.mipmap.btn_guanbi);
                }
                show = !show;
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_code_show:
                getcode();
                break;
        }
    }

    private void getcode() {
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showmessage(R.string.edit_phone);
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        HttpUtils.getNetData(HttpUtils.CODE_REGIST_GET, this, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                CommonBean bean = JsonUtil.parseObject(response.body(), CommonBean.class);
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
                        showToast(bean.message);
                    }
                } else {
                    showDataError();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
               showNetError();
            }

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                showPb();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissPb();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
