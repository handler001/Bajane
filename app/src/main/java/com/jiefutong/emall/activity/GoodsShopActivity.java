package com.jiefutong.emall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.GoodsShopAdapter;
import com.jiefutong.emall.bean.AreaCityBean;
import com.jiefutong.emall.bean.GoodsShopBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;


import java.util.ArrayList;
import java.util.List;

public class GoodsShopActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private ImageView mIvMessage;
    private TextView mTvTitle;
    private ImageView mIvCar;
    private TextView mTvRight;
    private RecyclerView mRlvContent;
    private ArrayList<GoodsShopBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private ArrayList<AreaCityBean.DataMapBean> city = new ArrayList<>();
    private BaseQuickAdapter cityAdapter;
    private PopupWindow pop;
    private View title;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_shop);
        settitlewhite();
        type = getIntent().getIntExtra("type", 0);
        initView();
        loaddata();
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.SHOP_LIST_INFO, this, new MyStringCallBack(this) {
            @Override
            protected void dealdata(String data) {
                GoodsShopBean bean = JsonUtil.parseObject(data, GoodsShopBean.class);
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

    private void initView() {
        View view = View.inflate(context, R.layout.empty_goods_show, null);
        TextView textView = view.findViewById(R.id.tv_info);
        textView.setText("暂无店铺");
        title = findViewById(R.id.rl_title);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mIvMessage = (ImageView) findViewById(R.id.iv_message);
        mIvMessage.setVisibility(View.GONE);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("实体店");
        mIvCar = (ImageView) findViewById(R.id.iv_car);
        mIvCar.setVisibility(View.GONE);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("全部");
        mTvRight.setOnClickListener(this);
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoodsShopAdapter(R.layout.item_goods_shop, datas, this, type);
        mRlvContent.setAdapter(adapter);
        adapter.setEmptyView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                if (city.size() == 0) {
                    getcity();
                } else {
                    showPop();
                }
                break;

        }
    }

    private void showPop() {
        if (pop == null) {
            View view = View.inflate(this, R.layout.layout_city_select, null);
            RecyclerView rlv = view.findViewById(R.id.rlv_area);
            rlv.setLayoutManager(new GridLayoutManager(this, 4));
            cityAdapter = new CityAdapter(R.layout.item_city_name, city);
            rlv.setAdapter(cityAdapter);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.dismiss();
                }
            });
            view.findViewById(R.id.ll_area).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            pop = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            pop.setFocusable(true);
            showdown(pop);
        } else {
            showdown(pop);
        }
    }

    private void showdown(PopupWindow pop) {
        if (android.os.Build.VERSION.SDK_INT == 24) {
            int[] a = new int[2];
            title.getLocationInWindow(a);
            pop.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, a[1] + title.getHeight());
        } else {
            pop.showAsDropDown(title);
        }
    }


    private void getcity() {
        HttpUtils.getNetData(HttpUtils.AREA_LIST_INFO, this, new MyStringCallBack(this) {
            @Override
            protected void dealdata(String data) {
                AreaCityBean bean = JsonUtil.parseObject(data, AreaCityBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        city.addAll(bean.dataMap);
                        showPop();
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
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    private class CityAdapter extends BaseQuickAdapter<AreaCityBean.DataMapBean, BaseViewHolder> {
        public CityAdapter(int layoutResId, @Nullable List<AreaCityBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final AreaCityBean.DataMapBean item) {
            helper.setText(R.id.tv_city, item.areaName);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTvRight.setText(item.areaName);
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                    }
                }
            });
        }
    }
}
