package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;

public class MoneyTransOutActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvHistory;
    private EditText mEtNum;
    private Button mBtNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_trans_out);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("转出");
        mTvHistory = (TextView) findViewById(R.id.tv_history);
        mTvHistory.setVisibility(View.VISIBLE);
        mTvHistory.setOnClickListener(this);
        mEtNum = (EditText) findViewById(R.id.et_num);
        mBtNext = (Button) findViewById(R.id.bt_next);
        mBtNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                showToast("下一步");
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_history:
                openActivity(MoneyTransHistoryActivity.class);
                break;
        }
    }

    private void submit() {
        String num = mEtNum.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            showToast("请输入手机号码/UID");
            return;
        }

    }
}
