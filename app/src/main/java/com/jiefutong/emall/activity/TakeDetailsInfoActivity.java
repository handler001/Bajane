package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.TakeInfoBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;

public class TakeDetailsInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvTxMoney;
    private TextView mTvTxType;
    private TextView mTvCodeNum;
    private TextView mTvMethod;
    private TextView mTvAccount;
    private TextView mTvDesc;
    private String id;
    private LinearLayout mLlDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_details_info);
        initView();
        settitlewhite();
        loaddata();
    }

    private void initView() {
        id = getIntent().getStringExtra("id");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("明细详情");
        mTvTxMoney = (TextView) findViewById(R.id.tv_tx_money);
        mTvTxType = (TextView) findViewById(R.id.tv_tx_type);
        mTvCodeNum = (TextView) findViewById(R.id.tv_code_num);
        mTvMethod = (TextView) findViewById(R.id.tv_method);
        mTvAccount = (TextView) findViewById(R.id.tv_account);
        mTvDesc = (TextView) findViewById(R.id.tv_desc);
        mLlDesc = (LinearLayout) findViewById(R.id.ll_desc);
    }

    private void loaddata() {
        HttpParams params = new HttpParams();
        params.put("id", id);
        HttpUtils.getNetData(HttpUtils.TAKE_OREDR_DETAILS, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                TakeInfoBean bean = JsonUtil.parseObject(data, TakeInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    mTvTxMoney.setText(bean.dataMap.money + "元");
                    if (bean.dataMap.payStatus == 1) {
                        mTvTxType.setText("审核中");
                    } else if (bean.dataMap.payStatus == 2) {
                        mTvTxType.setText("已通过");
                    } else {
                        mTvTxType.setText("未通过");
                    }
                    mTvCodeNum.setText(bean.dataMap.flowId);
//                    if (bean.dataMap.payType == 1) {
//                        mTvMethod.setText("银行卡");
//                    } else {
//                        mTvMethod.setText("支付宝");
//                    }
                    mTvMethod.setText(bean.dataMap.payTypeDesc);
                    mTvAccount.setText(bean.dataMap.bankCardNo);
                    if (TextUtils.isEmpty(bean.dataMap.remark)) {
                        mLlDesc.setVisibility(View.GONE);
                    } else {
                        mLlDesc.setVisibility(View.VISIBLE);
                        mTvDesc.setText(bean.dataMap.remark);
                    }
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
        }
    }
}
