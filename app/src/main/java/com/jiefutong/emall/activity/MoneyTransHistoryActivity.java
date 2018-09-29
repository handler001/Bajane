package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;

import java.util.ArrayList;
import java.util.List;

public class MoneyTransHistoryActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlv;
    private View empty;
    private ArrayList<String> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_trans_history);
        name = getIntent().getStringExtra("name");
        initView();
        settitlewhite();
    }

    private void initView() {
        empty = View.inflate(context, R.layout.view_order_empty, null);
        TextView info = empty.findViewById(R.id.tv_info);
        info.setText("暂时没有记录哦~");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(name);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(context));
        adapter = new HistoryAdapter(R.layout.item_frag_balance_info, datas);
        mRlv.setAdapter(adapter);
        adapter.setEmptyView(empty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    class HistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public HistoryAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
