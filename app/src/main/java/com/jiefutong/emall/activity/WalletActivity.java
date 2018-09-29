package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.DistCenterBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WalletActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvBank;
    private TextView mTvAli;
    private TextView mTvDetail;
    private TextView tv_balance;
    private TextView mTvMoney;
    private String money;
    private TextView mTvBalanceExchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        EventBus.getDefault().register(this);
        initView();
        settitleColor(R.color.text_total_money);
        loaddata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvBank = (TextView) findViewById(R.id.tv_bank);
        mTvBank.setOnClickListener(this);
        mTvAli = (TextView) findViewById(R.id.tv_ali);
        mTvAli.setOnClickListener(this);
        mTvDetail = (TextView) findViewById(R.id.tv_detail);
        mTvDetail.setOnClickListener(this);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_balance.setOnClickListener(this);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvBalanceExchange = (TextView) findViewById(R.id.tv_balance_exchange);
        mTvBalanceExchange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_balance:
                openActivity(new Intent(context, BalanceDetailsActivity.class).putExtra("type", 4));
                break;
            case R.id.tv_bank:
                if (!TextUtils.isEmpty(money)) {
                    //openActivity(new Intent(context, BankTakeActivity.class).putExtra("money", money));
                }
                break;
            case R.id.tv_ali:
//                if (!TextUtils.isEmpty(money)) {
//                    openActivity(new Intent(context, AliTakeActivity.class).putExtra("money", money));
//                }
                break;
            case R.id.tv_detail:
                openActivity(TakeDetailsActivity.class);
                break;
            case R.id.tv_balance_exchange:
                if (!TextUtils.isEmpty(money)) {
                    openActivity(new Intent(context, BalanceExchangeActivity.class).putExtra("money", money));
                }
                break;
        }
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.CENTER_FX, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                DistCenterBean bean = JsonUtil.parseObject(data, DistCenterBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    mTvMoney.setText(bean.dataMap.wallet);
                    money = bean.dataMap.wallet;
                } else {
                    showToast("数据获取失败");
                }
            }
        });
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
