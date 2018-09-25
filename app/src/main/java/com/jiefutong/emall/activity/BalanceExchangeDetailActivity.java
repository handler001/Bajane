package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.BalanceExchangeBean;

public class BalanceExchangeDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvTakeMoney;
    private TextView mTvTakeFee;
    private TextView mTvKnow;
    private TextView mTvMoney;
    private BalanceExchangeBean.DataMapBean bean;
    private TextView mTvCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_exchange_detail);
        settitlewhite();
        initView();
    }


    private void initView() {
        bean = (BalanceExchangeBean.DataMapBean) getIntent().getSerializableExtra("bean");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("余额转账");
        mTvTakeMoney = (TextView) findViewById(R.id.tv_take_money);
        mTvTakeFee = (TextView) findViewById(R.id.tv_take_fee);
        mTvKnow = (TextView) findViewById(R.id.tv_know);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvKnow.setOnClickListener(this);
        if (bean != null) {
            mTvTakeMoney.setText("￥" + bean.amt);
            mTvTakeFee.setText("￥" + bean.fee);
            mTvMoney.setText("￥" + bean.factAmt);
        }
        mTvCheck = (TextView) findViewById(R.id.tv_check);
        mTvCheck.setText("余额转账成功");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_know:
                finish();
                break;
        }
    }
}
