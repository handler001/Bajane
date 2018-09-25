package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.GoldExchangeBean;

public class AssetsExchageDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvTakeGold;
    private TextView mTvTakeFee;
    private TextView mTvTakeTicket;
    private TextView mTvGold;
    private TextView mTvKnow;
    private GoldExchangeBean.DataMapBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_exchage_detail);
        settitlewhite();
        bean = (GoldExchangeBean.DataMapBean) getIntent().getSerializableExtra("bean");
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("金币转账");
        mTvTakeGold = (TextView) findViewById(R.id.tv_take_gold);
        mTvTakeFee = (TextView) findViewById(R.id.tv_take_fee);
        mTvTakeTicket = (TextView) findViewById(R.id.tv_take_ticket);
        mTvGold = (TextView) findViewById(R.id.tv_gold);
        mTvKnow = (TextView) findViewById(R.id.tv_know);
        mTvKnow.setOnClickListener(this);
        mTvTakeGold.setText(bean.goodIcon);
        mTvTakeFee.setText(bean.fee);
        mTvGold.setText(bean.realGoodIcon);
        mTvTakeTicket.setText(bean.couponNum+"张");
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
