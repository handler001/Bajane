package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;

public class MoneyTransInActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvCode;
    private TextView mTvRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_trans_in);
        initView();
        settitleColor(R.color.tpc_trans_in_bg);
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("转入");
        mIvCode = (ImageView) findViewById(R.id.iv_code);
        mTvRecord = (TextView) findViewById(R.id.tv_record);
        mTvRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_record:
                openActivity(new Intent(context, MoneyTransHistoryActivity.class).putExtra("name", "转入记录"));
                break;
        }
    }
}
