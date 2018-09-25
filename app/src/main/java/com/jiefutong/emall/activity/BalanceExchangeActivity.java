package com.jiefutong.emall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AliInfoBean;
import com.jiefutong.emall.bean.BalanceExchangeBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.PhoneInfoBean;
import com.jiefutong.emall.bean.RateInfoBean;
import com.jiefutong.emall.bean.UserDetailsBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.ResourceUtils;
import com.jiefutong.emall.widget.PassValitationPopwindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BalanceExchangeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtPhone;
    private TextView mTvSure;
    private ImageView mIvHeader;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvTake;
    private EditText mEtGoldNum;
    private TextView mTvSubmit;
    private String money;
    private TextView mTvTicket;
    private TextView mTvTicketSure;
    private boolean flag;
    private String userphone, name, myuserphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_exchange);
        initView();
        settitlewhite();
        EventBus.getDefault().register(this);
        loadinfo();
        loaddata();
    }

    private void initView() {
        money = getIntent().getStringExtra("money");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("余额转账");
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvSure = (TextView) findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(this);
        mIvHeader = (ImageView) findViewById(R.id.iv_header);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvTake = (TextView) findViewById(R.id.tv_take);
        mEtGoldNum = (EditText) findViewById(R.id.et_gold_num);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
        mTvTake.setText("当前可转  " + money);
        mTvTicket = (TextView) findViewById(R.id.tv_ticket);
        mTvTicketSure = (TextView) findViewById(R.id.tv_ticket_sure);
        mTvTicketSure.setOnClickListener(this);
    }

    private void submit() {
        if (TextUtils.isEmpty(userphone)) {
            showToast("请输入对方手机号");
            return;
        }

        final String num = mEtGoldNum.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            showToast("请输入余额数");
            return;
        }
        if (parsemoney(num) > parsemoney(money)) {
            showToast("余额不足");
            return;
        }
        if (userphone.equals(myuserphone)) {
            new AlertDialog.Builder(context)
                    .setMessage("用户不能为自己转账")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
            return;
        }
        if (!flag) {
            new DiaglogUtils(context, "请先设置交易密码") {
                @Override
                public void sureMessage() {
                    openActivity(TakePwdSetActivity.class);
                }
            };
            return;
        }
        new DiaglogUtils(context, "确认要给昵称:" + name + "," + userphone + "转账么,转账成功后将不予退回") {
            @Override
            public void sureMessage() {
                new PassValitationPopwindow(BalanceExchangeActivity.this, 1, findViewById(R.id.tv_submit), new PassValitationPopwindow.OnInputNumberCodeCallback() {
                    @Override
                    public void onSuccess(String code) {
                        takeMoney(code, userphone, num);
                    }
                });
            }
        };
    }

    private double parsemoney(String money) {
        return Double.parseDouble(money);
    }

    private void takeMoney(String code, String phone, String num) {
        HttpParams params = new HttpParams();
        params.put("transPassword", code);
        params.put("userMobile", phone);
        params.put("balance", num);
        HttpUtils.getNetData(HttpUtils.BALANCE_EXCHANGE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                BalanceExchangeBean bean = JsonUtil.parseObject(data, BalanceExchangeBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        openActivity(new Intent(context, BalanceExchangeDetailActivity.class).putExtra("bean", bean.dataMap));
                        EventBusBean beans = new EventBusBean();
                        beans.cod = "money";
                        EventBus.getDefault().post(beans);
                        finish();
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showToast("转账失败,请重试");
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
            case R.id.tv_submit:
                submit();
                break;
            case R.id.tv_sure:
                getfriendsinfo();
                break;
            case R.id.tv_ticket_sure:
                feecount();
                break;
        }
    }

    private void feecount() {
        String money = mEtGoldNum.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            showToast("请输入转账金额");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("money", money);
        params.put("cashType", "1");
        HttpUtils.getNetData(HttpUtils.BALANCE_TAKE_RATE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                RateInfoBean bean = JsonUtil.parseObject(data, RateInfoBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        mTvTicket.setText("手续费:" + bean.dataMap.rate + "元");
                    } else {
                        showToast(bean.message);
                    }
                }
            }
        });
    }

    private void getfriendsinfo() {
        final String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入对方手机号");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        HttpUtils.getNetData(HttpUtils.GET_INFO_BY_PHONE_balance, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                PhoneInfoBean bean = JsonUtil.parseObject(data, PhoneInfoBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        GlideUtils.loadCirclepic(context, mIvHeader, bean.dataMap.headImgUrl);
                        mTvName.setText(bean.dataMap.realName);
                        mTvPhone.setText(bean.dataMap.userMobile);
                        name = bean.dataMap.realName;
                        userphone = bean.dataMap.userMobile;
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showToast("获取信息失败,请重试");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelTag(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean event) {
        if (event.cod.equals("bank")) {
            loaddata();
        }
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.INFO_ALI_GET, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                AliInfoBean bean = JsonUtil.parseObject(data, AliInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap != null) {
                        flag = bean.dataMap.transPasswordFlag;
                    }
                } else {
                    showToast("获取信息异常");
                }
            }
        });
    }

    private void loadinfo() {
        HttpUtils.getNetData(HttpUtils.USER_INFO, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                UserDetailsBean bean = JsonUtil.parseObject(data, UserDetailsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS && bean.dataMap != null) {
                    myuserphone = bean.dataMap.userMobile;
                }
            }
        });
    }
}
