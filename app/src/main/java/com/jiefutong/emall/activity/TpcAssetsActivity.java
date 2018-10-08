package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.TpcExchangeAdapter;
import com.jiefutong.emall.bean.TpcExchangeBean;

import java.util.ArrayList;

public class TpcAssetsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvAssets;
    private ArrayList<TpcExchangeBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private TextView mTvTicketNum;
    private TextView mTvMoneyNum;
    private TextView mTvOut;
    private TextView mTvExchane;
    private TextView mTvShop;
    private TextView mTvMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpc_assets);
        initView();
        settitlewhite();
    }

    private void initView() {
        for (int i = 0; i < 6; i++) {
            datas.add(new TpcExchangeBean());
        }
        View view = View.inflate(context, R.layout.view_order_empty, null);
        TextView textView = view.findViewById(R.id.tv_info);
        textView.setText("暂无资产信息");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("我的资产");
        mTvTicketNum = (TextView) findViewById(R.id.tv_ticket_num);
        mTvMoneyNum = (TextView) findViewById(R.id.tv_money_num);
        mTvOut = (TextView) findViewById(R.id.tv_out);
        mTvOut.setOnClickListener(this);
        mTvExchane = (TextView) findViewById(R.id.tv_exchane);
        mTvExchane.setOnClickListener(this);
        mTvShop = (TextView) findViewById(R.id.tv_shop);
        mTvShop.setOnClickListener(this);
        mTvMarket = (TextView) findViewById(R.id.tv_market);
        mTvMarket.setOnClickListener(this);
        mRlvAssets = (RecyclerView) findViewById(R.id.rlv_assets);
        mRlvAssets.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TpcExchangeAdapter(R.layout.item_tpc_exchange_area, datas, context);
        mRlvAssets.setAdapter(adapter);
        adapter.setEmptyView(view);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                adapter.loadMoreEnd();
            }
        }, mRlvAssets);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_out:
                break;
            case R.id.tv_exchane:
                openActivity(ExchangeAreaActivity.class);
                break;
            case R.id.tv_shop:
                break;
            case R.id.tv_market:
                break;
        }
    }
}
