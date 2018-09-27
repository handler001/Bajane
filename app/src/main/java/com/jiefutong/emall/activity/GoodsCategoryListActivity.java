package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.GoodShowAdapter;
import com.jiefutong.emall.bean.GoodsShowBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

public class GoodsCategoryListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvGoods;
    private String id, name;
    private int page = 1;
    private ArrayList<GoodsShowBean.DataMapBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_category_list);
        initView();
        settitlewhite();
        loadData();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.view_order_empty, null);
        TextView tv = view.findViewById(R.id.tv_info);
        tv.setText("暂无该商品");
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(name);
        mRlvGoods = (RecyclerView) findViewById(R.id.rlv_goods);
        mRlvGoods.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new GoodShowAdapter(R.layout.item_frag_goods, datas, context);
        mRlvGoods.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, mRlvGoods);
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

    protected void loadData() {
        HttpParams params = new HttpParams();
        params.put("page", String.valueOf(page));
        params.put("categoryId", id);
        HttpUtils.getNetData(HttpUtils.GOODS_LIST_INFO, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                adapter.loadMoreComplete();
                GoodsShowBean bean = JsonUtil.parseObject(data, GoodsShowBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        page++;
                        datas.addAll(bean.dataMap);
                        adapter.notifyDataSetChanged();
                        if (bean.dataMap.size() < 10) {
                            adapter.loadMoreEnd();
                        }
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showDataError();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                adapter.loadMoreFail();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
