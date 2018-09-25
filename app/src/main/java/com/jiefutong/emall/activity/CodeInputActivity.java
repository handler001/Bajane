package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.InfoCodeBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;

public class CodeInputActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtCode;
    private TextView mTvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_input);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("验证码输入");
        mEtCode = (EditText) findViewById(R.id.et_code);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
    }

    private void submit() {
        String code = mEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            showToast("请输入验证码");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("veriftyCode", code);
        HttpUtils.getNetData(HttpUtils.MY_SHOP_CODE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                InfoCodeBean bean = JsonUtil.parseObject(data, InfoCodeBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    openActivity(new Intent(context, SpendSureActivity.class).putExtra("bean", bean.dataMap));
                    finish();
                } else {
                    showToast("验证码输入错误");
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
