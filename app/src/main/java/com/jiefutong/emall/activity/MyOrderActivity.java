package com.jiefutong.emall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.OrderInfoAdapter;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.OrderInfoBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvEdit;
    private ImageView mIvCode;
    private TextView mTvCheckNot;
    private View mLineNot;
    private TextView mTvCheck;
    private View mLineCheck;
    private RecyclerView mRlvCheck;
    private RecyclerView mRlvCheckNot;
    private ArrayList<OrderInfoBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private ArrayList<OrderInfoBean.DataMapBean.ContentBean> mdatas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private BaseQuickAdapter madapter;
    private int pos;
    private int page = 1;
    private int mpage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order);
        initView();
        settitlewhite();
        EventBus.getDefault().register(this);
        dealPotion(0);
    }

    private void initView() {
        View view = View.inflate(context, R.layout.empty_goods_show, null);
        TextView tv = view.findViewById(R.id.tv_info);
        tv.setText("暂无订单");
        View view1 = View.inflate(context, R.layout.empty_goods_show, null);
        TextView tv1 = view1.findViewById(R.id.tv_info);
        tv1.setText("暂无订单");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("我的订单");
        mTvCheckNot = (TextView) findViewById(R.id.tv_check_not);
        mTvCheckNot.setOnClickListener(this);
        mLineNot = (View) findViewById(R.id.line_not);
        mTvCheck = (TextView) findViewById(R.id.tv_check);
        mTvCheck.setOnClickListener(this);
        mLineCheck = (View) findViewById(R.id.line_check);
        //验证
        mRlvCheck = (RecyclerView) findViewById(R.id.rlv_check);
        mRlvCheck.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderInfoAdapter(R.layout.item_shop_oredr_info, datas, context, 1);
        mRlvCheck.setAdapter(adapter);
        adapter.setEmptyView(view);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loaddata(page);
            }
        }, mRlvCheck);
        //未验证
        mRlvCheckNot = (RecyclerView) findViewById(R.id.rlv_check_not);
        mRlvCheckNot.setLayoutManager(new LinearLayoutManager(this));
        madapter = new OrderInfoAdapter(R.layout.item_shop_oredr_info, mdatas, context, 1);
        mRlvCheckNot.setAdapter(madapter);
        madapter.setEmptyView(view1);
        madapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loaddata(mpage);
            }
        }, mRlvCheckNot);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_check:
                dealPotion(1);
                break;
            case R.id.tv_check_not:
                dealPotion(0);
                break;
        }
    }

    private void loaddata(final int i) {
        final HttpParams params = new HttpParams();
        if (i == 0) {
            params.put("orderStatus", "4");
            params.put("page", mpage);
        } else {
            params.put("orderStatus", "5");
            params.put("page", page);
        }
        HttpUtils.getNetData(HttpUtils.ORDER_CHECK_LIST, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                OrderInfoBean bean = JsonUtil.parseObject(data, OrderInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (i == 0) {
                        madapter.loadMoreComplete();
                        mpage++;
                        madapter.addData(bean.dataMap.content);
                        if (bean.dataMap.content.size() < 10) {
                            madapter.loadMoreEnd(false);
                        }
                    } else {
                        adapter.loadMoreComplete();
                        page++;
                        adapter.addData(bean.dataMap.content);
                        if (bean.dataMap.content.size() < 10) {
                            adapter.loadMoreEnd(false);
                        }
                    }
                } else {
                    if (i == 0) {
                        madapter.loadMoreComplete();
                        madapter.loadMoreFail();
                    } else {
                        adapter.loadMoreComplete();
                        adapter.loadMoreFail();
                    }
                    showDataError();
                }
            }
        });
    }

    private void dealPotion(int i) {
        if (i == 0) {
            pos = 0;
            mTvCheckNot.setTextColor(getcolor(R.color.shop_red_seclect));
            mTvCheck.setTextColor(getcolor(R.color.shop_red_seclect_not));
            mRlvCheckNot.setVisibility(View.VISIBLE);
            mRlvCheck.setVisibility(View.GONE);
            mLineNot.setVisibility(View.VISIBLE);
            mLineCheck.setVisibility(View.INVISIBLE);
            if (mdatas.size() == 0) {
                loaddata(i);
            }
        } else {
            pos = 1;
            mTvCheckNot.setTextColor(getcolor(R.color.shop_red_seclect_not));
            mTvCheck.setTextColor(getcolor(R.color.shop_red_seclect));
            mRlvCheckNot.setVisibility(View.GONE);
            mRlvCheck.setVisibility(View.VISIBLE);
            mLineNot.setVisibility(View.INVISIBLE);
            mLineCheck.setVisibility(View.VISIBLE);
            if (datas.size() == 0) {
                loaddata(i);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean bean) {
        if (bean.cod.equals("return")) {
            mdatas.clear();
            datas.clear();
            dealPotion(pos);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }
}
