package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AliInfoBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.MoneyTakeBean;
import com.jiefutong.emall.bean.RateFeeBean;
import com.jiefutong.emall.bean.RateInfoBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.widget.PassValitationPopwindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AliTakeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvTakeAll;
    private EditText mEtMoney;
    private TextView mTvSubmit;
    private String money;
    private TextView mTvTake;
    private TextView mTvAliNot;
    private TextView mTvAliBind;
    private TextView mTvAliName;
    private TextView mTvAliAccount;
    private RelativeLayout mRlAli;
    private AliInfoBean.DataMapBean aliInfoBean;
    private TextView mTvFee;
    private TextView mTvFeeCount;
    private TextView mTvOne;
    private TextView mTvTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_take);
        initView();
        settitlewhite();
        EventBus.getDefault().register(this);
        loaddata();
        loadinfo();
    }

    private void initView() {
        money = getIntent().getStringExtra("money");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("支付宝提现");
        mTvTakeAll = (TextView) findViewById(R.id.tv_take_all);
        mTvTakeAll.setOnClickListener(this);
        mEtMoney = (EditText) findViewById(R.id.et_money);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
        mTvTake = (TextView) findViewById(R.id.tv_take);
        mTvTake.setText("当前可提" + money + "元");
        mTvAliNot = (TextView) findViewById(R.id.tv_ali_not);
        mTvAliBind = (TextView) findViewById(R.id.tv_ali_bind);
        mTvAliName = (TextView) findViewById(R.id.tv_ali_name);
        mTvAliAccount = (TextView) findViewById(R.id.tv_ali_account);
        mRlAli = (RelativeLayout) findViewById(R.id.rl_ali);
        mRlAli.setOnClickListener(this);
        mTvFee = (TextView) findViewById(R.id.tv_fee);
        mTvFeeCount = (TextView) findViewById(R.id.tv_fee_count);
        mTvFeeCount.setOnClickListener(this);
        mTvOne = (TextView) findViewById(R.id.tv_one);
        mTvTwo = (TextView) findViewById(R.id.tv_two);
    }

    private void submit() {
        final String moneys = mEtMoney.getText().toString().trim();
        if (TextUtils.isEmpty(moneys)) {
            showToast("请输入提现金额");
            return;
        }
        if (parsemoney(moneys) > parsemoney(money)) {
            showToast("余额不足");
            return;
        }

        if (!aliInfoBean.transPasswordFlag) {
            new DiaglogUtils(context, "请先设置交易密码") {
                @Override
                public void sureMessage() {
                    openActivity(TakePwdSetActivity.class);
                }
            };
            return;
        }
        new DiaglogUtils(context, "确认要提现到" + aliInfoBean.alipayName + "," + aliInfoBean.alipayAccount + "的支付宝账号，提现后将不予退回") {
            @Override
            public void sureMessage() {
                new PassValitationPopwindow(AliTakeActivity.this, 1, findViewById(R.id.tv_submit), new PassValitationPopwindow.OnInputNumberCodeCallback() {
                    @Override
                    public void onSuccess(String code) {
                        takeMoney(code, moneys);
                    }
                });
            }
        };
    }

    private double parsemoney(String money) {
        return Double.parseDouble(money);
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.INFO_ALI_GET, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                AliInfoBean bean = JsonUtil.parseObject(data, AliInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap != null) {
                        aliInfoBean = bean.dataMap;
                        mTvAliNot.setVisibility(View.GONE);
                        mTvAliBind.setVisibility(View.GONE);
                        mTvAliAccount.setVisibility(View.VISIBLE);
                        mTvAliName.setVisibility(View.VISIBLE);
                        mTvAliName.setText(bean.dataMap.alipayName);
                        mTvAliAccount.setText(bean.dataMap.alipayAccount);
                    } else {
                        mTvAliNot.setVisibility(View.VISIBLE);
                        mTvAliBind.setVisibility(View.VISIBLE);
                        mTvAliAccount.setVisibility(View.GONE);
                        mTvAliName.setVisibility(View.GONE);
                    }
                } else {
                    showToast("获取信息异常");
                }
            }
        });
    }

    private void loadinfo() {
        HttpParams params = new HttpParams();
        params.put("type", "alipay");
        HttpUtils.getNetData(HttpUtils.RATE_DESC, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                RateFeeBean bean = JsonUtil.parseObject(data, RateFeeBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    mTvOne.setText(bean.dataMap.date);
                    mTvTwo.setText(bean.dataMap.fee);
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
            case R.id.tv_take_all:
                mEtMoney.setText(money);
                break;
            case R.id.tv_submit:
                if (aliInfoBean == null) {
                    showToast("请选择绑定的支付宝");
                    return;
                }
                submit();
                break;
            case R.id.rl_ali:
                openActivity(AliBindingActivity.class);
                break;
            case R.id.tv_fee_count:
                feecount();
                break;
        }
    }

    private void feecount() {
        String money = mEtMoney.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            showToast("请输入提现金额");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("money", money);
        params.put("cashType", "1");
        HttpUtils.getNetData(HttpUtils.MONEY_TAKE_RATE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                RateInfoBean bean = JsonUtil.parseObject(data, RateInfoBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        mTvFee.setText("手续费:" + bean.dataMap.rate + "元");
                    } else {
                        showToast(bean.message);
                    }
                }
            }
        });
    }

    private void takeMoney(String pwd, final String money) {
        HttpParams params = new HttpParams();
        params.put("transPassword", pwd);
        params.put("cashType", "2");
        params.put("money", money);
        HttpUtils.getNetData(HttpUtils.MONEY_TAKE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                MoneyTakeBean bean = JsonUtil.parseObject(data, MoneyTakeBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        openActivity(new Intent(context, TakeCheckActivity.class).putExtra("bean", bean.dataMap));
                        EventBusBean beans = new EventBusBean();
                        beans.cod = "money";
                        EventBus.getDefault().post(beans);
                        finish();
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showToast("提现失败,请重试");
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean event) {
        if (event.cod.equals("bank")) {
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
