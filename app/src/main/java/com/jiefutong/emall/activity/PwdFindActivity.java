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
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class PwdFindActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtPhone;
    private ImageView mIvClear;
    private EditText mEtCode;
    private TextView mTvCodeShow;
    private EditText mEtPwd;
    private ImageView mIvShow;
    private EditText mEtPwdSure;
    private ImageView mIvPwdSure;
    private TextView mTvSure;
    private boolean one, two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_find);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("找回密码");
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvClear.setOnClickListener(this);
        mEtCode = (EditText) findViewById(R.id.et_code);
        mTvCodeShow = (TextView) findViewById(R.id.tv_code_show);
        mTvCodeShow.setOnClickListener(this);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mIvShow = (ImageView) findViewById(R.id.iv_show);
        mIvShow.setOnClickListener(this);
        mEtPwdSure = (EditText) findViewById(R.id.et_pwd_sure);
        mIvPwdSure = (ImageView) findViewById(R.id.iv_pwd_sure);
        mIvPwdSure.setOnClickListener(this);
        mTvSure = (TextView) findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(this);
        mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mEtPwdSure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    private void submit() {
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

        String sure = mEtPwdSure.getText().toString().trim();
        if (TextUtils.isEmpty(sure)) {
            showmessage(R.string.pwd_sure);
            return;
        }
        if (!pwd.equals(sure)) {
            showmessage(R.string.pwd_two_err);
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        params.put("code", code);
        params.put("password", pwd);
        HttpUtils.getNetData(HttpUtils.PWD_FIND_BACK, this, params, new MyStringCallBack(this) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
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
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                mEtPhone.setText("");
                break;
            case R.id.tv_sure:
                submit();
                break;
            case R.id.iv_show:
                if (one) {
                    //显示
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mIvShow.setBackgroundResource(R.mipmap.btn_xianshi);
                } else {
                    //隐藏
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mIvShow.setBackgroundResource(R.mipmap.btn_guanbi);
                }
                one = !one;
                break;
            case R.id.iv_pwd_sure:
                if (two) {
                    //显示
                    mEtPwdSure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mIvPwdSure.setBackgroundResource(R.mipmap.btn_xianshi);
                } else {
                    //隐藏
                    mEtPwdSure.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mIvPwdSure.setBackgroundResource(R.mipmap.btn_guanbi);
                }
                two = !two;
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
        HttpUtils.getNetData(HttpUtils.CODE_Find_GET, this, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                CommonBean bean = JsonUtil.parseObject(response.body(), CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        CountDownTimer timer = new CountDownTimer(120000, 1000) {
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
}
