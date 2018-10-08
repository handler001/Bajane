package com.jiefutong.emall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.BannerWebActivity;
import com.jiefutong.emall.activity.CenterBargainActivity;
import com.jiefutong.emall.activity.ExchangeAreaActivity;
import com.jiefutong.emall.activity.MoneyTransInActivity;
import com.jiefutong.emall.activity.MoneyTransOutActivity;
import com.jiefutong.emall.activity.ProductDetailActivity;
import com.jiefutong.emall.activity.QuotesActivity;
import com.jiefutong.emall.activity.TpcAssetsActivity;
import com.jiefutong.emall.activity.TpcFriendShareActivity;
import com.jiefutong.emall.activity.WebViewActivity;
import com.jiefutong.emall.bean.BannerBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.lzy.okgo.OkGo;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.realm.internal.Context;

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
        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.mipmap.icon_banner);
        initbanner(data);
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
                openActivity(MoneyTransInActivity.class);
                break;
            case R.id.ll_buy:
                openActivity(new Intent(mctx, CenterBargainActivity.class).putExtra("type", 0));
                break;
            case R.id.ll_sell:
                openActivity(QuotesActivity.class);
                break;
            case R.id.ll_money:
                openActivity(TpcAssetsActivity.class);
                break;
            case R.id.ll_mall:
                break;
            case R.id.ll_share:
                openActivity(TpcFriendShareActivity.class);
                break;
            case R.id.ll_exchange:
                break;
            case R.id.ll_buss:
                break;
        }
    }

    private void initbanner(final List<Integer> dataMap) {
        PageBean bean = new PageBean.Builder<Integer>()
                .setDataObjects(dataMap)
                .setIndicator(mBottomScaleLayout)
                .builder();
        mLoopViewpager.setPageTransformer(false, new MzTransformer());
        mLoopViewpager.setPageListener(bean, R.layout.item_banner_pic, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                ImageView mpic = view.findViewById(R.id.iv_pic);
                Integer dataMapBean = (Integer) o;
                GlideUtils.loadpicId(mctx, mpic, dataMapBean);
            }
        });
    }
}
