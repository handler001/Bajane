package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.ProInfosAdapter;
import com.jiefutong.emall.bean.ProductDescBean;
import com.jiefutong.emall.bean.ProductInfosBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;

public class ProductInfosActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<ProductInfosBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_infos);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        initView();
        settitlewhite();
        loaddata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(name);
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProInfosAdapter(R.layout.item_pro_info_detail, datas, this);
        mRlvContent.setAdapter(adapter);
    }

    private void loaddata() {
        HttpParams params = new HttpParams();
        params.put("type", id);
        HttpUtils.getNetData(HttpUtils.ARTICLE_LIST_DETAIL, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                ProductInfosBean bean = JsonUtil.parseObject(data, ProductInfosBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        adapter.addData(bean.dataMap.content);
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
}
