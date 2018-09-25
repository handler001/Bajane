package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.ProductDescAdapter;
import com.jiefutong.emall.bean.ProductDescBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

public class ProductDescActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<ProductDescBean.DataMapBean> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc);
        settitlewhite();
        initView();
        loaddata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("产品介绍");
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductDescAdapter(R.layout.item_pro_desc_info, datas, this);
        mRlvContent.setAdapter(adapter);
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.PRO_LIST_DETAIL, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                ProductDescBean bean = JsonUtil.parseObject(data, ProductDescBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        adapter.addData(bean.dataMap);
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showDataError();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
