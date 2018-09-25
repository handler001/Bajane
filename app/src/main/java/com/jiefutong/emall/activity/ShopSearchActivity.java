package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.ShopListAdapter;
import com.jiefutong.emall.bean.ShopListBean;

import java.util.ArrayList;

public class ShopSearchActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private EditText mEtSearch;
    private TextView mTvSearch;
    private TextView mTvAll;
    private ImageView mIvAll;
    private LinearLayout mLlAll;
    private TextView mTvAddress;
    private ImageView mIvAddress;
    private LinearLayout mLlAddress;
    private TextView mTvPx;
    private ImageView mIvPx;
    private LinearLayout mLlPx;
    private RecyclerView mRlvShop;
    private ArrayList<ShopListBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_search);
        initView();
        settitlewhite();
        initshop();
    }

    private void initView() {
        view = View.inflate(context, R.layout.view_order_empty, null);
        TextView textView = view.findViewById(R.id.tv_info);
        textView.setText("暂无相关商家");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvSearch.setOnClickListener(this);
        mTvAll = (TextView) findViewById(R.id.tv_all);
        mIvAll = (ImageView) findViewById(R.id.iv_all);
        mLlAll = (LinearLayout) findViewById(R.id.ll_all);
        mLlAll.setOnClickListener(this);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mIvAddress = (ImageView) findViewById(R.id.iv_address);
        mLlAddress = (LinearLayout) findViewById(R.id.ll_address);
        mLlAddress.setOnClickListener(this);
        mTvPx = (TextView) findViewById(R.id.tv_px);
        mIvPx = (ImageView) findViewById(R.id.iv_px);
        mLlPx = (LinearLayout) findViewById(R.id.ll_px);
        mLlPx.setOnClickListener(this);
        mRlvShop = (RecyclerView) findViewById(R.id.rlv_shop);
        mRlvShop.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ShopListAdapter(R.layout.item_shop_list, datas, context);
        mRlvShop.setAdapter(adapter);
        adapter.setEmptyView(view);
    }

    private void initshop() {
        for (int i = 0; i < 3; i++) {
            datas.add(new ShopListBean());
        }
    }

    private void submit() {
        String search = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            showToast("请输入搜索内容");
            return;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                submit();
                break;
            case R.id.ll_all:
                dealposition(0);
                break;
            case R.id.ll_address:
                dealposition(1);
                break;
            case R.id.ll_px:
                dealposition(2);
                break;
        }
    }

    private void dealposition(int i) {
        if (i == 0) {
            mTvAll.setTextColor(getcolor(R.color.title_org_sel));
            mIvAll.setBackgroundResource(R.mipmap.icon_upper_flei);
            mTvAddress.setTextColor(getcolor(R.color.text_black_3));
            mIvAddress.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvPx.setTextColor(getcolor(R.color.text_black_3));
            mIvPx.setBackgroundResource(R.mipmap.icon_downr_flei);
        } else if (i == 1) {
            mTvAll.setTextColor(getcolor(R.color.text_black_3));
            mIvAll.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvAddress.setTextColor(getcolor(R.color.title_org_sel));
            mIvAddress.setBackgroundResource(R.mipmap.icon_upper_flei);
            mTvPx.setTextColor(getcolor(R.color.text_black_3));
            mIvPx.setBackgroundResource(R.mipmap.icon_downr_flei);

        } else if (i == 2) {
            mTvAll.setTextColor(getcolor(R.color.text_black_3));
            mIvAll.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvAddress.setTextColor(getcolor(R.color.text_black_3));
            mIvAddress.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvPx.setTextColor(getcolor(R.color.title_org_sel));
            mIvPx.setBackgroundResource(R.mipmap.icon_upper_flei);
        } else {
            mTvAll.setTextColor(getcolor(R.color.text_black_3));
            mIvAll.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvAddress.setTextColor(getcolor(R.color.text_black_3));
            mIvAddress.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvPx.setTextColor(getcolor(R.color.text_black_3));
            mIvPx.setBackgroundResource(R.mipmap.icon_downr_flei);
        }
    }
}
