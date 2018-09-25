package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.ShopListAdapter;
import com.jiefutong.emall.bean.BtnDataBean;
import com.jiefutong.emall.bean.ShopListBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;

import java.util.ArrayList;
import java.util.List;

public class AllianceMerchantActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvSearch;
    private TextView mTvAddress;
    private RecyclerView mRlvShop;
    private View header;
    private RecyclerView mRlvBtn;
    private TextView mTvShopAll;
    private ArrayList<ShopListBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alliance_merchant);
        initView();
        settitlewhite();
        initdata();
        initshop();
    }

    private void initView() {
        header = View.inflate(context, R.layout.header_merchant_alliance, null);
        mRlvBtn = header.findViewById(R.id.rlv_btn);
        mRlvBtn.setLayoutManager(new GridLayoutManager(context, 5));
        mTvShopAll = header.findViewById(R.id.tv_shop_all);
        mTvShopAll.setOnClickListener(this);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvSearch.setOnClickListener(this);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mRlvShop = (RecyclerView) findViewById(R.id.rlv_shop);
        mRlvShop.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ShopListAdapter(R.layout.item_shop_list, datas, context);
        mRlvShop.setAdapter(adapter);
        adapter.setHeaderView(header);
    }

    private void initdata() {
        HttpUtils.getNetData(HttpUtils.SHOP_CATEGORY_LIST, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                BtnDataBean bean = JsonUtil.parseObject(data, BtnDataBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    mRlvBtn.setAdapter(new BtnAdapter(R.layout.item_btn_header_alliance, bean.dataMap));
                }
            }
        });
    }

    private void initshop() {
        for (int i = 0; i < 3; i++) {
            datas.add(new ShopListBean());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                openActivity(ShopSearchActivity.class);
                break;
            case R.id.tv_address:
                showToast("地址");
                break;
            case R.id.tv_shop_all:
                break;
        }
    }

    class BtnAdapter extends BaseQuickAdapter<BtnDataBean.DataMapBean, BaseViewHolder> {
        public BtnAdapter(int layoutResId, @Nullable List<BtnDataBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BtnDataBean.DataMapBean item) {
            helper.setText(R.id.tv_name, item.categoryType);
            GlideUtils.loadpic(context, (ImageView) helper.getView(R.id.iv_btn), item.categoryImg);
        }
    }
}
