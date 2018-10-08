package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.TpcExchangeAdapter;
import com.jiefutong.emall.bean.ActContentBean;
import com.jiefutong.emall.bean.TpcExchangeBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class ExchangeAreaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvExchange;
    private ArrayList<TpcExchangeBean> datas = new ArrayList<>();
    private ArrayList<Integer> banners = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private View header;
    private BannerViewPager mLoopViewpager;
    private ZoomIndicator mBottomScaleLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_area);
        initView();
        settitlewhite();
    }

    private void initView() {
        for (int i = 0; i < 6; i++) {
            datas.add(new TpcExchangeBean());
            banners.add(R.mipmap.icon_banner);
        }
        View view = View.inflate(context, R.layout.view_order_empty, null);
        TextView textView = view.findViewById(R.id.tv_info);
        textView.setText("暂无兑换信息");
        header = View.inflate(context, R.layout.header_act_mz_banner, null);
        mBottomScaleLayout = header.findViewById(R.id.bottom_scale_layout);
        mLoopViewpager = header.findViewById(R.id.loop_viewpager);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("兑换专区");
        mRlvExchange = (RecyclerView) findViewById(R.id.rlv_exchange);
        mRlvExchange.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TpcExchangeAdapter(R.layout.item_tpc_exchange_area, datas, context);
        mRlvExchange.setAdapter(adapter);
        adapter.addHeaderView(header);
        adapter.setEmptyView(view);
        initbanner(banners);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initbanner(List<Integer> banner) {
        PageBean bean = new PageBean.Builder<Integer>()
                .setDataObjects(banner)
                .setIndicator(mBottomScaleLayout)
                .builder();
        mLoopViewpager.setPageTransformer(false, new MzTransformer());
        mLoopViewpager.setPageListener(bean, R.layout.item_act_banner_pic, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                final Integer bean = (Integer) o;
                ImageView mpic = view.findViewById(R.id.iv_pic);
                GlideUtils.loadpicId(context, mpic, bean);
            }
        });
    }
}
