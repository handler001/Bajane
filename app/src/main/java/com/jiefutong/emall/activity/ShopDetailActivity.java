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
import com.jiefutong.emall.bean.ShopInfoDetailBean;
import com.jiefutong.emall.bean.ShopListBean;
import com.jiefutong.emall.bean.ShopListDetailsBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;

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
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        initView();
        settitlewhite();
        initshop();
    }

    private void initView() {
        id = getIntent().getStringExtra("id");
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
        HttpParams params = new HttpParams();
        params.put("shopId", id);
        HttpUtils.getNetData(HttpUtils.SHOP_INFO_DETAIL, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                ShopInfoDetailBean bean = JsonUtil.parseObject(data, ShopInfoDetailBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    showshop(bean.dataMap);
                }
            }
        });
    }

    private void showshop(ShopInfoDetailBean.DataMapBean dataMap) {
        GlideUtils.loadpic(context, mIvPic, dataMap.shopIcon);
        mTvShopName.setText(dataMap.shopName);
        mTvShopAddress.setText(dataMap.addressDesc);
        adapter.addData(dataMap.nearList);
        mTvSpe.setText(dataMap.shopPoint);
        mTvIntro.setText(dataMap.shopIntroduce);
        if (dataMap.shopSignDesc.size() == 5) {
            mTvOne.setText(dataMap.shopSignDesc.get(0));
            mTvTwo.setText(dataMap.shopSignDesc.get(1));
            mTvThree.setText(dataMap.shopSignDesc.get(2));
            mTvFour.setText(dataMap.shopSignDesc.get(3));
            mTvFive.setText(dataMap.shopSignDesc.get(4));
        } else if (dataMap.shopSignDesc.size() == 4) {
            mTvOne.setText(dataMap.shopSignDesc.get(0));
            mTvTwo.setText(dataMap.shopSignDesc.get(1));
            mTvThree.setText(dataMap.shopSignDesc.get(2));
            mTvFour.setText(dataMap.shopSignDesc.get(3));
            mTvFive.setVisibility(View.GONE);
        } else if (dataMap.shopSignDesc.size() == 3) {
            mTvOne.setText(dataMap.shopSignDesc.get(0));
            mTvTwo.setText(dataMap.shopSignDesc.get(1));
            mTvThree.setText(dataMap.shopSignDesc.get(2));
            mTvFour.setVisibility(View.GONE);
            mTvFive.setVisibility(View.GONE);
        } else if (dataMap.shopSignDesc.size() == 2) {
            mTvOne.setText(dataMap.shopSignDesc.get(0));
            mTvTwo.setText(dataMap.shopSignDesc.get(1));
            mTvThree.setVisibility(View.GONE);
            mTvFour.setVisibility(View.GONE);
            mTvFive.setVisibility(View.GONE);
        } else if (dataMap.shopSignDesc.size() == 1) {
            mTvOne.setText(dataMap.shopSignDesc.get(0));
            mTvTwo.setVisibility(View.GONE);
            mTvThree.setVisibility(View.GONE);
            mTvFour.setVisibility(View.GONE);
            mTvFive.setVisibility(View.GONE);
        } else {
            mTvOne.setVisibility(View.GONE);
            mTvTwo.setVisibility(View.GONE);
            mTvThree.setVisibility(View.GONE);
            mTvFour.setVisibility(View.GONE);
            mTvFive.setVisibility(View.GONE);
        }
    }


}
