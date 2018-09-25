package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.MoneyTakeBean;

public class TakeCheckActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvTakeMoney;
    private TextView mTvTakeFee;
    private TextView mTvKnow;
    private TextView mTvMoney;
    private MoneyTakeBean.DataMapBean dataMapBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_check);
        initView();
        settitlewhite();
    }

    private void initView() {
        dataMapBean = (MoneyTakeBean.DataMapBean) getIntent().getSerializableExtra("bean");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("提现");
        mTvTakeMoney = (TextView) findViewById(R.id.tv_take_money);
        mTvTakeFee = (TextView) findViewById(R.id.tv_take_fee);
        mTvKnow = (TextView) findViewById(R.id.tv_know);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvKnow.setOnClickListener(this);
        if (dataMapBean != null) {
            mTvTakeMoney.setText("￥" + dataMapBean.amt);
            mTvTakeFee.setText("￥" + dataMapBean.rate);
            mTvMoney.setText("￥" + dataMapBean.realAmt);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_know:
                finish();
                openActivity(TakeDetailsActivity.class);
                break;
        }
    }
}
