package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AssetsBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyAssetsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvAssets;
    private TextView mTvGoldNum;
    private LinearLayout mLlGold;
    private TextView mTvAgNum;
    private LinearLayout mLlAg;
    private TextView mTvMarkNum;
    private LinearLayout mLlMark;
    private TextView mTvQuanNum;
    private LinearLayout mLlQuan;
    private AssetsBean.DataMapBean dataMapBean;
    private LinearLayout mLlFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assets);
        EventBus.getDefault().register(this);
        initView();
        settitlewhite();
        loaddata();
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.MY_ASSETS, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                AssetsBean bean = JsonUtil.parseObject(data, AssetsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    dataMapBean = bean.dataMap;
                    mTvAssets.setText(bean.dataMap.wallet);
                    mTvGoldNum.setText(bean.dataMap.goodIcon);
                    mTvAgNum.setText(bean.dataMap.silverIcon);
                    mTvMarkNum.setText(bean.dataMap.integral);
                    mTvQuanNum.setText(bean.dataMap.Coupon + "");
                    if (bean.dataMap.iconFlag == 1) {
                        mLlFour.setVisibility(View.VISIBLE);
                    } else {
                        mLlFour.setVisibility(View.GONE);
                    }
                } else {
                    showToast("资产获取失败");
                }
            }
        });
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("我的资产");
        mTvAssets = (TextView) findViewById(R.id.tv_assets);
        mTvAssets.setOnClickListener(this);
        mTvGoldNum = (TextView) findViewById(R.id.tv_gold_num);
        mLlGold = (LinearLayout) findViewById(R.id.ll_gold);
        mLlGold.setOnClickListener(this);
        mTvAgNum = (TextView) findViewById(R.id.tv_ag_num);
        mLlAg = (LinearLayout) findViewById(R.id.ll_ag);
        mLlAg.setOnClickListener(this);
        mTvMarkNum = (TextView) findViewById(R.id.tv_mark_num);
        mLlMark = (LinearLayout) findViewById(R.id.ll_mark);
        mLlMark.setOnClickListener(this);
        mTvQuanNum = (TextView) findViewById(R.id.tv_quan_num);
        mLlQuan = (LinearLayout) findViewById(R.id.ll_quan);
        mLlQuan.setOnClickListener(this);
        mLlFour = (LinearLayout) findViewById(R.id.ll_four);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_gold:
                if (dataMapBean != null) {
                    openActivity(new Intent(context, AssetsDetailsActivity.class)
                            .putExtra("type", 0)
                    );
                }
                break;
            case R.id.ll_ag:
                if (dataMapBean != null) {
                    openActivity(new Intent(context, AssetsDetailsActivity.class)
                            .putExtra("type", 1)
                    );
                }
                break;
            case R.id.ll_mark:
                if (dataMapBean != null) {
                    openActivity(new Intent(context, AssetsDetailsActivity.class)
                            .putExtra("type", 2)
                    );
                }
                break;
            case R.id.ll_quan:
                if (dataMapBean != null) {
                    openActivity(new Intent(context, AssetsDetailsActivity.class)
                            .putExtra("type", 3)
                    );
                }
                break;
            case R.id.tv_assets:
                openActivity(WalletActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean event) {
        if (event.cod.equals("gold") || event.cod.equals("ticket") || event.cod.equals("money")) {
            loaddata();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelTag(this);
    }
}
