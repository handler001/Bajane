package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AssetsBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.GoldExchangeBean;
import com.jiefutong.emall.bean.PhoneInfoBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

public class TicketExchangeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtPhone;
    private TextView mTvSure;
    private ImageView mIvHeader;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvTake;
    private EditText mEtGoldNum;
    private TextView mTvTicket;
    private TextView mTvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_exchange);
        initView();
        settitlewhite();
        loaddata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("转兑换券");
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvSure = (TextView) findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(this);
        mIvHeader = (ImageView) findViewById(R.id.iv_header);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvTake = (TextView) findViewById(R.id.tv_take);
        mEtGoldNum = (EditText) findViewById(R.id.et_gold_num);
        mTvTicket = (TextView) findViewById(R.id.tv_ticket);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
    }

    private void submit() {
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入对方手机号");
            return;
        }

        String num = mEtGoldNum.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            showToast("请输入兑换券数量");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        params.put("couponNum", num);
        HttpUtils.getNetData(HttpUtils.TICKET_EXCHANGE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        EventBusBean beans = new EventBusBean();
                        beans.cod = "ticket";
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

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.MY_ASSETS, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                AssetsBean bean = JsonUtil.parseObject(data, AssetsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    mTvTake.setText("当前可转" + bean.dataMap.goodIcon + "张");
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
            case R.id.tv_submit:
                submit();
                break;
            case R.id.tv_sure:
                getfriendsinfo();
                break;
        }
    }

    private void getfriendsinfo() {
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入对方手机号");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        HttpUtils.getNetData(HttpUtils.GET_INFO_BY_PHONE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                PhoneInfoBean bean = JsonUtil.parseObject(data, PhoneInfoBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        GlideUtils.loadCirclepic(context, mIvHeader, bean.dataMap.headImgUrl);
                        mTvName.setText(bean.dataMap.realName);
                        mTvPhone.setText(bean.dataMap.userMobile);
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
        OkGo.getInstance().cancelTag(this);
    }
}
