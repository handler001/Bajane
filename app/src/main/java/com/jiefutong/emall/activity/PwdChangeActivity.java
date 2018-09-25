package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;

public class PwdChangeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtPwd;
    private EditText mEtPwdNew;
    private EditText mEtPwdSure;
    private TextView mTvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_change);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("密码修改");
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mEtPwdNew = (EditText) findViewById(R.id.et_pwd_new);
        mEtPwdSure = (EditText) findViewById(R.id.et_pwd_sure);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
    }

    private void submit() {
        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            showToast("请输入原密码");
            return;
        }

        String pwdnew = mEtPwdNew.getText().toString().trim();
        if (TextUtils.isEmpty(pwdnew)) {
            showToast("请输入新密码");
            return;
        }

        String sure = mEtPwdSure.getText().toString().trim();
        if (TextUtils.isEmpty(sure)) {
            showToast("请再次输入新密码");
            return;
        }

        if (!pwdnew.equals(sure)) {
            showToast("2次密码输入不一致,请确认");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("newPsw", sure);
        params.put("oldPsw", pwd);
        HttpUtils.getNetData(HttpUtils.PWD_CHANGE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        showToast("密码修改成功");
                        finish();
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showToast("密码修改失败,请重试");
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
        }
    }
}
