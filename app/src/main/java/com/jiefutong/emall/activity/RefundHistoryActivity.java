package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.RefundHistoryAdapter;
import com.jiefutong.emall.bean.RefundHistoryBean;

import java.util.ArrayList;

public class RefundHistoryActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private String id;
    private RecyclerView mRlv;
    private ArrayList<RefundHistoryBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_history);
        initView();
        settitlewhite();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.view_empty, null);
        id = getIntent().getStringExtra("id");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("退款历史");
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(context));
        adapter = new RefundHistoryAdapter(R.layout.item_refund_history, datas, context);
        mRlv.setAdapter(adapter);
        adapter.setEmptyView(view);
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
