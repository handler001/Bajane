package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AssetsBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AssetsDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvBalance;
    private TextView mTvShow;
    private TextView mTvCount;
    private TextView mTvTurn;
    private View mLine;
    private TextView mTvMall;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_details);
        settitleColor(R.color.text_total_money);
        type = getIntent().getIntExtra("type", 0);
        EventBus.getDefault().register(this);
        initView();
        initdata();
        loaddata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvBalance = (TextView) findViewById(R.id.tv_balance);
        mTvBalance.setOnClickListener(this);
        mTvShow = (TextView) findViewById(R.id.tv_show);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mTvTurn = (TextView) findViewById(R.id.tv_turn);
        mLine = (View) findViewById(R.id.line);
        mTvMall = (TextView) findViewById(R.id.tv_mall);
    }

    private void initdata() {
        if (type == 0) {
            mTvTitle.setText("金币");
            mTvBalance.setText("金币明细");
            mTvShow.setText("总金币（个）");
            mTvTurn.setText("转账");
            mTvTurn.setOnClickListener(this);
            mTvMall.setText("金币商城");
        } else if (type == 1) {
            mTvTitle.setText("银币");
            mTvBalance.setText("银币明细");
            mTvShow.setText("总银币（个）");
            mTvTurn.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
            mTvMall.setText("银币商城");
        } else if (type == 2) {
            mTvTitle.setText("积分");
            mTvBalance.setText("积分明细");
            mTvShow.setText("总积分");
            mTvTurn.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
            mTvMall.setText("积分商城");
        } else {
            mTvTitle.setText("兑换券");
            mTvBalance.setText("兑换券明细");
            mTvShow.setText("总兑换券（张）");
            mTvMall.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
            mTvTurn.setText("转兑换券");
            mTvTurn.setOnClickListener(this);
        }
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.MY_ASSETS, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                AssetsBean bean = JsonUtil.parseObject(data, AssetsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (type == 0) {
                        mTvCount.setText(bean.dataMap.goodIcon);
                    } else if (type == 1) {
                        mTvCount.setText(bean.dataMap.silverIcon);
                    } else if (type == 2) {
                        mTvCount.setText(bean.dataMap.integral);
                    } else {
                        mTvCount.setText(bean.dataMap.Coupon + "");
                    }
                } else {
                    showToast("资产获取失败");
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
            case R.id.tv_balance:
                openActivity(new Intent(context, BalanceDetailsActivity.class).putExtra("type", type));
                break;
            case R.id.tv_turn:
                if (type == 0) {
                    openActivity(GoldExchangeActivity.class);
                } else {
                    openActivity(TicketExchangeActivity.class);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean event) {
        if (event.cod.equals("gold") || event.cod.equals("ticket")) {
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
