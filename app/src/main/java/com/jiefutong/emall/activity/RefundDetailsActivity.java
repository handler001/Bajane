package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.RefundDetailsAdapter;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.RefundDetailsBean;
import com.jiefutong.emall.bean.RefundListBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class RefundDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private String id;
    private TextView mTvStateInfo;
    private TextView mTvTime;
    private TextView mTvFinish;
    private RecyclerView mPrlContent;
    private TextView mTvGoodsPrice;
    private TextView mTvPro;
    private TextView mTvTimeShow;
    private TextView mTvApplyMoney;
    private TextView mTvApplyReasonContent;
    //    private ImageView mIvPicOne;
//    private ImageView mIvPicTwo;
//    private ImageView mIvPicThree;
//    private PercentLinearLayout mPllPic;
    private RecyclerView mRlvHistory;
    private TextView mTvEms;
    private TextView mTvApplyCancle;
    private RefundDetailsAdapter adapter;
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details);
        id = getIntent().getStringExtra("id");
        settitlewhite();
        initView();
        loaddata();
    }


    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("退款详情");
        mTvStateInfo = (TextView) findViewById(R.id.tv_state_info);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvFinish = (TextView) findViewById(R.id.tv_finish);
        mPrlContent = (RecyclerView) findViewById(R.id.prl_content);
        mPrlContent.setLayoutManager(new LinearLayoutManager(context));
        mTvGoodsPrice = (TextView) findViewById(R.id.tv_goods_price);
        mTvPro = (TextView) findViewById(R.id.tv_pro);
        mTvTimeShow = (TextView) findViewById(R.id.tv_time_show);
        mTvApplyMoney = (TextView) findViewById(R.id.tv_apply_money);
        mTvApplyReasonContent = (TextView) findViewById(R.id.tv_apply_reason_content);
//        mIvPicOne = (ImageView) findViewById(R.id.iv_pic_one);
//        mIvPicTwo = (ImageView) findViewById(R.id.iv_pic_two);
//        mIvPicThree = (ImageView) findViewById(R.id.iv_pic_three);
//        mPllPic = (PercentLinearLayout) findViewById(R.id.pll_pic);
        mRlvHistory = (RecyclerView) findViewById(R.id.rlv_history);
        mRlvHistory.setLayoutManager(new LinearLayoutManager(context));
        mTvEms = (TextView) findViewById(R.id.tv_ems);
        mTvEms.setOnClickListener(this);
        mTvApplyCancle = (TextView) findViewById(R.id.tv_apply_cancle);
        mTvApplyCancle.setOnClickListener(this);
    }

    private void loaddata() {
        HttpParams params = new HttpParams();
        params.put("id", id);
        HttpUtils.getNetData(HttpUtils.REFUND_DETAIL, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                RefundDetailsBean bean = JsonUtil.parseObject(data, RefundDetailsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    orderNo = bean.dataMap.refundNo;
                    adapter = new RefundDetailsAdapter(R.layout.item_refund_detail_show, bean.dataMap.historyList, context);
                    mRlvHistory.setAdapter(adapter);
                    mTvStateInfo.setText(bean.dataMap.refundDesc);
                    mTvTime.setText(bean.dataMap.balanceTime);
                    mTvGoodsPrice.setText("￥" + bean.dataMap.totalAmt);
                    mTvPro.setText(bean.dataMap.applyProject);
                    mTvTimeShow.setText(bean.dataMap.applyDate);
                    mTvApplyMoney.setText("￥" + bean.dataMap.applyAmt);
                    mTvApplyReasonContent.setText(bean.dataMap.applyReason);
                    mPrlContent.setAdapter(new InfoAdapter(R.layout.item_goods_info_count, bean.dataMap.goodsList));
                } else {
                    showToast("详情获取异常");
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
            case R.id.tv_ems:
                if (!TextUtils.isEmpty(orderNo)) {
                    openActivity(new Intent(context, EmsInfoUploadActivity.class).putExtra("order", orderNo));
                }
                break;
            case R.id.tv_apply_cancle:
                if (!TextUtils.isEmpty(orderNo)) {
                    cancleOrder();
                }
                break;
        }
    }

    private void cancleOrder() {
        HttpParams params = new HttpParams();
        params.put("refundNo", orderNo);
        HttpUtils.getNetData(HttpUtils.REFUND_CANCLE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    EventBusBean eventBusBean = new EventBusBean();
                    eventBusBean.cod = "refund";
                    EventBus.getDefault().post(eventBusBean);
                    finish();
                } else {
                    showToast("取消失败");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    class InfoAdapter extends BaseQuickAdapter<RefundDetailsBean.DataMapBean.GoodsListBean, BaseViewHolder> {

        public InfoAdapter(int layoutResId, @Nullable List<RefundDetailsBean.DataMapBean.GoodsListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RefundDetailsBean.DataMapBean.GoodsListBean item) {
            GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.cover);
            helper.setText(R.id.tv_name, item.goodsName)
                    .setText(R.id.tv_price, "￥" + item.goodsPrice)
                    .setText(R.id.tv_count, "x" + item.goodsNum);
        }
    }
}
