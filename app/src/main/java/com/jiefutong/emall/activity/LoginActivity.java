package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.UserBean;
import com.jiefutong.emall.bean.UserInfoBean;
import com.jiefutong.emall.utils.Constant;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.PreferencesUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtPhone;
    private ImageView mIvClear;
    private EditText mEtPwd;
    private ImageView mIvShow;
    private TextView mTvLogin;
    private TextView mTvRegist;
    private TextView mTvPwdFind;
    private boolean show = true;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settitlewhite();
        realm = Realm.getDefaultInstance();
        initView();
    }

    private void initView() {
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvClear.setOnClickListener(this);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mIvShow = (ImageView) findViewById(R.id.iv_show);
        mIvShow.setOnClickListener(this);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        mTvLogin.setOnClickListener(this);
        mTvRegist = (TextView) findViewById(R.id.tv_regist);
        mTvRegist.setOnClickListener(this);
        mTvPwdFind = (TextView) findViewById(R.id.tv_pwd_find);
        mTvPwdFind.setOnClickListener(this);
        mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void submit() {
        // validate
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showmessage(R.string.edit_phone);
            return;
        }

        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            showmessage(R.string.edit_pwd);
            return;
        }
        login(phone, pwd);
    }

    private void login(String phone, String pwd) {
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        params.put("password", pwd);
        HttpUtils.getNetData(HttpUtils.LOGIN, this, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                UserBean bean = JsonUtil.parseObject(response.body(), UserBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        String cookie = response.headers().get("Set-Cookie").split(";")[0];
                        adduser(bean.dataMap);
                        PreferencesUtils.putString(context, Constant.KEY_COOKIE, cookie);
                        openActivity(MainSelectActivity.class);
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
            case R.id.tv_login:
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
            case R.id.tv_regist:
                openActivity(RegistActivity.class);
                break;
            case R.id.tv_pwd_find:
                openActivity(PwdFindActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        realm.close();
    }


    //    保存用户信息
    private void adduser(final UserInfoBean data) {
        final RealmResults<UserInfoBean> dataBeen = realm.where(UserInfoBean.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                dataBeen.deleteAllFromRealm();
                realm.copyToRealm(data);
            }
        });
    }
}
