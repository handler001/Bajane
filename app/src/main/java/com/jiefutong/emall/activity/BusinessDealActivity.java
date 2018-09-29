package com.jiefutong.emall.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.BargainMoneyAdapter;
import com.jiefutong.emall.bean.BargainMoneyBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;

public class BusinessDealActivity extends BaseActivity implements View.OnClickListener {
    private int type;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvHeaderTitle;
    private TextView mTvHeaderTitleMoney;
    private RecyclerView mRlvMoney;
    private Button mBtSubmit;
    private Button mBtAdd;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_deal);
        initView();
        type = getIntent().getIntExtra("type", 0);
        settitlewhite();
        loadData();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvHeaderTitle = (TextView) findViewById(R.id.tv_header_title);
        mTvHeaderTitleMoney = (TextView) findViewById(R.id.tv_header_title_money);
        mRlvMoney = (RecyclerView) findViewById(R.id.rlv_money);
        mRlvMoney.setLayoutManager(new GridLayoutManager(context, 3));
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mBtAdd = (Button) findViewById(R.id.bt_add);

        mBtSubmit.setOnClickListener(this);
        mBtAdd.setOnClickListener(this);
        mBtAdd.setVisibility(View.GONE);
        if (type == 0) {
            mTvTitle.setText("买入");
            mTvHeaderTitle.setText("请选择对应数量买入");
        } else {
            mTvTitle.setText("卖出");
            mTvHeaderTitle.setText("请选择对应数量卖出");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_submit:
                if (type == 1) {
                    showpop();
                } else {
                    openActivity(OrderPayActivity.class);
                }
                break;
            case R.id.bt_add:

                break;
        }
    }

    private void showpop() {
        if (popupWindow == null) {
            View view = View.inflate(context, R.layout.pop_bue_deal_center, null);
            view.findViewById(R.id.bt_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setalpha(1.0f);
                }
            });
            popupWindow.showAtLocation(View.inflate(context, R.layout.activity_business_deal, null), Gravity.CENTER, 0, 0);
            setalpha(0.3f);
        } else {
            popupWindow.showAtLocation(View.inflate(context, R.layout.activity_business_deal, null), Gravity.CENTER, 0, 0);
            setalpha(0.3f);
        }
    }

    protected void loadData() {
        HttpUtils.getNetData(HttpUtils.TPC_LIST_PRO, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                final BargainMoneyBean bean = JsonUtil.parseObject(data, BargainMoneyBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    mTvHeaderTitleMoney.setText("￥" + bean.dataMap.get(0).totalAmt);
                    final BargainMoneyAdapter moneyAdapter = new BargainMoneyAdapter(R.layout.item_bar_money_list, bean.dataMap);
                    mRlvMoney.setAdapter(moneyAdapter);
                    moneyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            moneyAdapter.setposition(position);
                            mTvHeaderTitleMoney.setText("￥" + bean.dataMap.get(position).totalAmt);
                        }
                    });
                }
            }
        });
    }

    private void setalpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }
}
