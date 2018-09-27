package com.jiefutong.emall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.MoneyTransInActivity;
import com.jiefutong.emall.activity.MoneyTransOutActivity;
import com.lzy.okgo.OkGo;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;
import com.zhy.android.percent.support.PercentLinearLayout;

/**
 * @Author l
 * @Date 2018/9/21
 */
public class TpcListFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mIvScan;
    private TextView mTvTpcMoney;
    private TextView mTvTpcTest;
    private TextView mTvTpcScore;
    private BannerViewPager mLoopViewpager;
    private ZoomIndicator mBottomScaleLayout;
    private PercentLinearLayout mLlTrans;
    private PercentLinearLayout mLlOver;
    private PercentLinearLayout mLlBuy;
    private PercentLinearLayout mLlSell;
    private PercentLinearLayout mLlMoney;
    private PercentLinearLayout mLlMall;
    private PercentLinearLayout mLlShare;
    private PercentLinearLayout mLlExchange;
    private PercentLinearLayout mLlBuss;

    @Override
    protected void listener() {
        mIvScan.setOnClickListener(this);
        mLlTrans.setOnClickListener(this);
        mLlOver.setOnClickListener(this);
        mLlBuy.setOnClickListener(this);
        mLlSell.setOnClickListener(this);
        mLlMoney.setOnClickListener(this);
        mLlMall.setOnClickListener(this);
        mLlShare.setOnClickListener(this);
        mLlExchange.setOnClickListener(this);
        mLlBuss.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void intiview(View view) {
        mIvScan = view.findViewById(R.id.iv_scan);
        mTvTpcMoney = view.findViewById(R.id.tv_tpc_money);
        mTvTpcTest = view.findViewById(R.id.tv_tpc_test);
        mTvTpcScore = view.findViewById(R.id.tv_tpc_score);
        mLoopViewpager = view.findViewById(R.id.loop_viewpager);
        mBottomScaleLayout = view.findViewById(R.id.bottom_scale_layout);
        mLlTrans = view.findViewById(R.id.ll_trans);
        mLlOver = view.findViewById(R.id.ll_over);
        mLlBuy = view.findViewById(R.id.ll_buy);
        mLlSell = view.findViewById(R.id.ll_sell);
        mLlMoney = view.findViewById(R.id.ll_money);
        mLlMall = view.findViewById(R.id.ll_mall);
        mLlShare = view.findViewById(R.id.ll_share);
        mLlExchange = view.findViewById(R.id.ll_exchange);
        mLlBuss = view.findViewById(R.id.ll_buss);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_tpc_list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:
                break;
            case R.id.ll_trans:
                openActivity(MoneyTransOutActivity.class);
                break;
            case R.id.ll_over:
                //openActivity(MoneyTransInActivity.class);
                break;
            case R.id.ll_buy:
                break;
            case R.id.ll_sell:
                break;
            case R.id.ll_money:
                break;
            case R.id.ll_mall:
                break;
            case R.id.ll_share:
                break;
            case R.id.ll_exchange:
                break;
            case R.id.ll_buss:
                break;


        }
    }
}
