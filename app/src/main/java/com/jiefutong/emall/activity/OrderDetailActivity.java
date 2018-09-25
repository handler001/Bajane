package com.jiefutong.emall.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.GoodsBean;
import com.jiefutong.emall.bean.GoodsBuyNowBean;
import com.jiefutong.emall.bean.OrderDetailBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvState;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvAddress;
    private TextView mTvGoodsPrice;
    private TextView mTvPostPrice;
    private TextView mTvPayPrice;
    private TextView mTvOrderCode;
    private TextView mTvCopy;
    private TextView mTvOrderTimeStart;
    private TextView mTvOrderTimePay;
    private TextView mTvOrderTimeGet;
    private String id, ordercode;
    private RecyclerView mPrlCenter;
    private TextView mTvPostName;
    private TextView mTvPostNumber;
    private LinearLayout mLlPost;
    private LinearLayout mLlFk;
    private LinearLayout mLlFh;
    private TextView mTvOrderTimeSh;
    private LinearLayout mLlSh;
    // private TextView mTvRefund;
    private ArrayList<GoodsBean> datas;
    private TextView mTvOrderPay;
    private TextView mTvOrderCancle;
    private RelativeLayout mRlOrder;
    private int state, flag;
    private TextView mTvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        id = getIntent().getStringExtra("id");
        initView();
        settitleRed();
        loaddata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvState = (TextView) findViewById(R.id.tv_state);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvGoodsPrice = (TextView) findViewById(R.id.tv_goods_price);
        mTvPostPrice = (TextView) findViewById(R.id.tv_post_price);
        mTvPayPrice = (TextView) findViewById(R.id.tv_pay_price);
        mTvOrderCode = (TextView) findViewById(R.id.tv_order_code);
        mTvCopy = (TextView) findViewById(R.id.tv_copy);
        mTvCopy.setOnClickListener(this);
        mTvOrderTimeStart = (TextView) findViewById(R.id.tv_order_time_start);
        mTvOrderTimePay = (TextView) findViewById(R.id.tv_order_time_pay);
        mTvOrderTimeGet = (TextView) findViewById(R.id.tv_order_time_get);
        mPrlCenter = (RecyclerView) findViewById(R.id.prl_center);
        mPrlCenter.setLayoutManager(new LinearLayoutManager(context));
        mTvPostName = (TextView) findViewById(R.id.tv_post_name);
        mTvPostNumber = (TextView) findViewById(R.id.tv_post_number);
        mLlPost = (LinearLayout) findViewById(R.id.ll_post);
        mLlFk = (LinearLayout) findViewById(R.id.ll_fk);
        mLlFh = (LinearLayout) findViewById(R.id.ll_fh);
        mTvOrderTimeSh = (TextView) findViewById(R.id.tv_order_time_sh);
        mLlSh = (LinearLayout) findViewById(R.id.ll_sh);
//        mTvRefund = (TextView) findViewById(R.id.tv_refund);
//        mTvRefund.setOnClickListener(this);
        mTvOrderPay = (TextView) findViewById(R.id.tv_order_pay);
        mTvOrderPay.setOnClickListener(this);
        mTvOrderCancle = (TextView) findViewById(R.id.tv_order_cancle);
        mTvOrderCancle.setOnClickListener(this);
        mRlOrder = (RelativeLayout) findViewById(R.id.rl_order);
        mTvTime = (TextView) findViewById(R.id.tv_time);
    }

    private void loaddata() {
        HttpParams params = new HttpParams();
        params.put("orderNo", id);
        HttpUtils.getNetData(HttpUtils.ORDER_INFO_DETAIL, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                OrderDetailBean bean = JsonUtil.parseObject(data, OrderDetailBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap.address != null) {
                        mTvName.setText(bean.dataMap.address.receiveName);
                        mTvPhone.setText(bean.dataMap.address.receiveTel);
                        mTvAddress.setText(bean.dataMap.address.receiveProvince
                                + bean.dataMap.address.receiveCity + bean.dataMap.address.receiveCountry
                                + bean.dataMap.address.receiveDetail);
                    }
                    if (bean.dataMap.shop != null) {
                        mTvName.setText(bean.dataMap.shop.shopName);
                        mTvPhone.setText(bean.dataMap.shop.shopTel);
                        mTvAddress.setText(bean.dataMap.shop.shopAdress);
                    }
                    mTvGoodsPrice.setText("￥" + bean.dataMap.amt);
                    mTvPostPrice.setText("￥" + bean.dataMap.postage);
                    mTvPayPrice.setText("￥" + bean.dataMap.realAmt);
                    mTvOrderCode.setText(bean.dataMap.orderNo);
                    ordercode = bean.dataMap.orderNo;
                    mTvOrderTimeStart.setText(bean.dataMap.createDate);
                    if (TextUtils.isEmpty(bean.dataMap.payDate)) {
                        mLlFk.setVisibility(View.GONE);
                    } else {
                        mLlFk.setVisibility(View.VISIBLE);
                        mTvOrderTimePay.setText(bean.dataMap.payDate);
                    }
                    if (TextUtils.isEmpty(bean.dataMap.sendDate)) {
                        mLlFh.setVisibility(View.GONE);
                    } else {
                        mLlFh.setVisibility(View.VISIBLE);
                        mTvOrderTimeGet.setText(bean.dataMap.sendDate);
                    }
                    if (TextUtils.isEmpty(bean.dataMap.finishDate)) {
                        mLlSh.setVisibility(View.GONE);
                    } else {
                        mLlSh.setVisibility(View.VISIBLE);
                        mTvOrderTimeSh.setText(bean.dataMap.finishDate);
                    }
                    state = bean.dataMap.orderStatus;
                    flag = bean.dataMap.refundFlag;
                    datas = bean.dataMap.goods;
                    mPrlCenter.setAdapter(new InfoAdapter(R.layout.item_goods_info_count, bean.dataMap.goods));
                    if (bean.dataMap.orderStatus == 1) {
                        mTvState.setText("待付款");
                        mTvTime.setVisibility(View.VISIBLE);
                        mTvTime.setText("若您在"+bean.dataMap.banlanceTime+"内未支付,订单将被取消");
                        mRlOrder.setVisibility(View.VISIBLE);
                    } else if (bean.dataMap.orderStatus == 2) {
                        // mTvRefund.setVisibility(View.VISIBLE);
                        mRlOrder.setVisibility(View.VISIBLE);
                        mTvState.setText("待发货 ");
                        mTvOrderCancle.setVisibility(View.GONE);
                        mTvOrderPay.setText("批量退款");
                        if (bean.dataMap.refundFlag == 1) {
                            mTvOrderPay.setVisibility(View.VISIBLE);
                        } else {
                            mTvOrderPay.setVisibility(View.GONE);
                            mRlOrder.setVisibility(View.GONE);
                        }
                    } else if (bean.dataMap.orderStatus == 4) {
                        //mTvRefund.setVisibility(View.VISIBLE);
                        mTvState.setText("待验证 ");
                        mRlOrder.setVisibility(View.GONE);
                    } else if (bean.dataMap.orderStatus == 3) {
                        //mTvRefund.setVisibility(View.VISIBLE);
                        mTvState.setText("待收货");
                        mRlOrder.setVisibility(View.VISIBLE);
                        if (bean.dataMap.refundFlag == 1) {
                            mTvOrderPay.setVisibility(View.VISIBLE);
                            mTvOrderPay.setText("批量退款");
                            mTvOrderCancle.setText("确认收货");
                        } else {
                            mTvOrderCancle.setVisibility(View.GONE);
                            mTvOrderPay.setText("确认收货");
                        }
                    } else if (bean.dataMap.orderStatus == 5) {
                        mTvState.setText("已完成");
                        mRlOrder.setVisibility(View.GONE);
                        //mTvRefund.setVisibility(View.GONE);
                    } else {
                        mTvState.setText("已取消");
                        mRlOrder.setVisibility(View.GONE);
                        //mTvRefund.setVisibility(View.GONE);
                    }
                    if (bean.dataMap.orderStatus == 3 || bean.dataMap.orderStatus == 5) {
                        if (!TextUtils.isEmpty(bean.dataMap.postageName) && !TextUtils.isEmpty(bean.dataMap.postageNo)) {
                            mLlPost.setVisibility(View.VISIBLE);
                            mTvPostName.setText("快递名称: " + bean.dataMap.postageName);
                            mTvPostNumber.setText("快递编号: " + bean.dataMap.postageNo);
                        } else {
                            mLlPost.setVisibility(View.GONE);
                        }
                    } else {
                        mLlPost.setVisibility(View.GONE);
                    }
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
            case R.id.tv_copy:
                if (!TextUtils.isEmpty(ordercode)) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText(null, ordercode));
                    // 将文本内容放到系统剪贴板里。
                    showToast("复制成功");
                }
                break;
            case R.id.tv_refund:
                if (datas != null) {
                    openActivity(new Intent(context, GoodsSelectListActivity.class).putParcelableArrayListExtra("data", datas));
                }
                finish();
                break;
            case R.id.tv_order_pay:
                if (state == 1) {
                    payOrder();
                } else if (state == 2 && flag == 1) {
                    if (datas != null) {
                        openActivity(new Intent(context, GoodsSelectListActivity.class).putParcelableArrayListExtra("data", datas));
                    }
                    finish();
                } else if (state == 3) {
                    if (flag == 1) {
                        if (datas != null) {
                            openActivity(new Intent(context, GoodsSelectListActivity.class).putParcelableArrayListExtra("data", datas));
                        }
                        finish();
                    } else {
                        finishOrder();
                    }
                } else {

                }
                break;
            case R.id.tv_order_cancle:
                if (state == 1) {
                    cancleOrder();
                } else if (state == 3 && flag == 1) {
                    finishOrder();
                } else {

                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    class InfoAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        public InfoAdapter(int layoutResId, @Nullable List<GoodsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.cover);
            helper.setText(R.id.tv_name, item.goodsName)
                    .setText(R.id.tv_price, "￥" + item.goodsPrice)
                    .setText(R.id.tv_count, "x" + item.goodsNum);
        }
    }

    private void cancleOrder() {
        new DiaglogUtils(context, "是否要取消该订单") {
            @Override
            public void sureMessage() {
                HttpParams params = new HttpParams();
                params.put("orderNo", ordercode);
                HttpUtils.getNetData(HttpUtils.ORDER_PAY_CANClE, OrderDetailActivity.this, params, new MyStringCallBack(context) {
                    @Override
                    protected void dealdata(String data) {
                        CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                        if (bean != null && bean.code == HttpUtils.SUCCESS) {
                            EventBusBean beans = new EventBusBean();
                            beans.cod = "order";
                            EventBus.getDefault().post(beans);
                            showToast("取消订单成功");
                            finish();
                        } else {
                            showToast("取消订单失败");
                        }
                    }
                });
            }
        };
    }

    private void payOrder() {
        HttpParams params = new HttpParams();
        params.put("orderNo", ordercode);
        HttpUtils.getNetData(HttpUtils.ORDER_PAY_NOW, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                GoodsBuyNowBean bean = JsonUtil.parseObject(data, GoodsBuyNowBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    EventBusBean beans = new EventBusBean();
                    beans.cod = "order";
                    EventBus.getDefault().post(beans);
                    startActivity(new Intent(context, ProductBuyInfoActivity.class)
                            .putExtra("bean", bean.dataMap)
                            .putExtra("orderNo", ordercode));
                    finish();
                } else {
                    showToast("支付订单失败");
                }
            }
        });
    }

    private void finishOrder() {
        new DiaglogUtils(context, "确认收货么") {
            @Override
            public void sureMessage() {
                HttpParams params = new HttpParams();
                params.put("orderNo", ordercode);
                HttpUtils.getNetData(HttpUtils.ORDER_INFO_GET, OrderDetailActivity.this, params, new MyStringCallBack(context) {
                    @Override
                    protected void dealdata(String data) {
                        CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                        if (bean != null && bean.code == HttpUtils.SUCCESS) {
                            EventBusBean beans = new EventBusBean();
                            beans.cod = "order";
                            EventBus.getDefault().post(beans);
                            finish();
                        } else {
                            showToast("确认失败,请重试");
                        }
                    }
                });
            }
        };
    }

}

