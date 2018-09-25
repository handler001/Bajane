package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.BalanceDetailsAdapter;
import com.jiefutong.emall.bean.BalanceBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

public class MyIncomeListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlv;
    private ArrayList<BalanceBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private View empty;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_income_list);
        settitlewhite();
        initView();
        loaddata();
    }

    private void loaddata() {
        final HttpParams params = new HttpParams();
        params.put("flowType", String.valueOf(1));
        params.put("page", page);
        params.put("coinType", "1");
        HttpUtils.getNetData(HttpUtils.BALANCE_INFO_DETAILS, this, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                BalanceBean bean = JsonUtil.parseObject(response.body(), BalanceBean.class);
                adapter.loadMoreComplete();
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    ++page;
                    if (bean.dataMap.content.size() < bean.dataMap.pageable.pageSize) {
                        adapter.loadMoreEnd();
                    }
                    adapter.addData(bean.dataMap.content);
                } else {
                    adapter.loadMoreFail();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                adapter.loadMoreFail();
            }
        });
    }

    private void initView() {
        empty = View.inflate(context, R.layout.empty_goods_show, null);
        TextView info = empty.findViewById(R.id.tv_info);
        info.setText("暂无盈利");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("盈利详情");
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BalanceDetailsAdapter(R.layout.item_frag_balance_info, datas);
        mRlv.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loaddata();
            }
        }, mRlv);
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
}
