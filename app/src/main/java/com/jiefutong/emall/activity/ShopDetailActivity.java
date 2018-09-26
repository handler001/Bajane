package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.ShopListAdapter;
import com.jiefutong.emall.bean.ShopListBean;
import com.jiefutong.emall.bean.ShopListDetailsBean;

import java.util.ArrayList;

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvPic;
    private TextView mTvShopName;
    private TextView mTvOne;
    private TextView mTvTwo;
    private TextView mTvThree;
    private TextView mTvFour;
    private TextView mTvFive;
    private TextView mTvShopAddress;
    private TextView mTvSpe;
    private TextView mTvIntro;
    private RecyclerView mRlvShop;
    private ImageView mIvPhone;
    private ArrayList<ShopListDetailsBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        initView();
        settitlewhite();
        initshop();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("商家详情");
        mIvPic = (ImageView) findViewById(R.id.iv_pic);
        mTvShopName = (TextView) findViewById(R.id.tv_shop_name);
        mTvOne = (TextView) findViewById(R.id.tv_one);
        mTvTwo = (TextView) findViewById(R.id.tv_two);
        mTvThree = (TextView) findViewById(R.id.tv_three);
        mTvFour = (TextView) findViewById(R.id.tv_four);
        mTvFive = (TextView) findViewById(R.id.tv_five);
        mTvShopAddress = (TextView) findViewById(R.id.tv_shop_address);
        mTvSpe = (TextView) findViewById(R.id.tv_spe);
        mTvIntro = (TextView) findViewById(R.id.tv_intro);
        mRlvShop = (RecyclerView) findViewById(R.id.rlv_shop);
        mRlvShop.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ShopListAdapter(R.layout.item_shop_list, datas, context);
        mRlvShop.setAdapter(adapter);
        mIvPhone = (ImageView) findViewById(R.id.iv_phone);
        mIvPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_phone:
                showToast("电话");
                break;
        }
    }

    private void initshop() {

    }
}
