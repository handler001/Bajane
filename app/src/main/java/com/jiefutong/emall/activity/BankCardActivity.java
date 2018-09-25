package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.BankCardBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;

public class BankCardActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvFinish;
    private LinearLayout mLlBankNot;
    private TextView mTvName;
    private TextView mTvNumber;
    private PercentRelativeLayout mPrlBank;
    private String id;
    private boolean take;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
        initView();
        settitlewhite();
        loaddata();
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.INFO_BANK_CARD, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                BankCardBean bean = JsonUtil.parseObject(data, BankCardBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap == null || bean.dataMap.id.equals("0")) {
                        id = "";
                        mPrlBank.setVisibility(View.GONE);
                        mLlBankNot.setVisibility(View.VISIBLE);
                        mTvFinish.setText("+ 添加银行卡");
                    } else {
                        id = bean.dataMap.id;
                        mPrlBank.setVisibility(View.VISIBLE);
                        mLlBankNot.setVisibility(View.GONE);
                        mTvName.setText(bean.dataMap.bankName);
                        mTvNumber.setText(bean.dataMap.bankCardNo);
                        mTvFinish.setText("解绑银行卡");
                    }
                } else {
                    showToast("银行卡信息获取失败");
                }
            }
        });
    }

    private void initView() {
        take = getIntent().getBooleanExtra("select", false);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("我的银行卡");
        mTvFinish = (TextView) findViewById(R.id.tv_finish);
        mTvFinish.setOnClickListener(this);
        mLlBankNot = (LinearLayout) findViewById(R.id.ll_bank_not);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvNumber = (TextView) findViewById(R.id.tv_number);
        mPrlBank = (PercentRelativeLayout) findViewById(R.id.prl_bank);
        mPrlBank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                if (!TextUtils.isEmpty(id)) {
                    showinfo();
                } else {
                    openActivityForResult(BankCardAddActivity.class, 100);
                }
                break;
            case R.id.prl_bank:
                if (take && !TextUtils.isEmpty(id)) {
                    EventBusBean bean = new EventBusBean();
                    bean.cod = "bank";
                    EventBus.getDefault().post(bean);
                }
                break;
        }
    }

    private void showinfo() {
        new DiaglogUtils(context, "您确定要解绑此卡吗？") {
            @Override
            public void sureMessage() {
                unBindBank();
            }
        };
    }

    private void unBindBank() {
        HttpParams params = new HttpParams();
        params.put("id", id);
        HttpUtils.getNetData(HttpUtils.UNBIND_BANK_CARD, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    EventBusBean eventBusBean = new EventBusBean();
                    eventBusBean.cod = "bank";
                    EventBus.getDefault().post(eventBusBean);
                    loaddata();
                } else {
                    showToast("解绑失败,请重新尝试");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            loaddata();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
