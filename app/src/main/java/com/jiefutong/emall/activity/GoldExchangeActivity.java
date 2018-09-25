package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class GoldExchangeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtPhone;
    private TextView mTvSure;
    private ImageView mIvHeader;
    private TextView mTvName;
    private EditText mEtGoldNum;
    private ImageView mIvSelect;
    private TextView mTvTicket;
    private TextView mTvSubmit;
    private TextView mTvPhone;
    private boolean select;
    private TextView mTvTake;
    private int nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_exchange);
        initView();
        settitlewhite();
        loaddata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("转账金币");
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvSure = (TextView) findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(this);
        mIvHeader = (ImageView) findViewById(R.id.iv_header);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mEtGoldNum = (EditText) findViewById(R.id.et_gold_num);
        mIvSelect = (ImageView) findViewById(R.id.iv_select);
        mIvSelect.setOnClickListener(this);
        mTvTicket = (TextView) findViewById(R.id.tv_ticket);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvTake = (TextView) findViewById(R.id.tv_take);
    }

    private void submit() {
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入对方手机号");
            return;
        }

        String num = mEtGoldNum.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            showToast("请输入金币数额");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userMobile", phone);
        params.put("goodIcon", num);
        if (select) {
            params.put("couponNum", String.valueOf(nums));
        } else {
            params.put("couponNum", String.valueOf(0));
        }
        HttpUtils.getNetData(HttpUtils.GOLD_EXCHANGE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                GoldExchangeBean bean = JsonUtil.parseObject(data, GoldExchangeBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        EventBusBean beans = new EventBusBean();
                        beans.cod = "gold";
                        EventBus.getDefault().post(beans);
                        openActivity(new Intent(context, AssetsExchageDetailActivity.class).putExtra("bean", bean.dataMap));
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
                    mTvTake.setText("当前可转" + bean.dataMap.goodIcon + "个");
                    mTvTicket.setText("我的兑换券" + bean.dataMap.Coupon + "张，免" + bean.dataMap.Coupon * 1000 + "金币手续费。");
                    nums = bean.dataMap.Coupon;
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
            case R.id.iv_select:
                select = !select;
                if (select) {
                    mIvSelect.setBackgroundResource(R.mipmap.btn_paystyle_selected);
                } else {

                    mIvSelect.setBackgroundResource(R.mipmap.btn_xz_default);
                }
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
