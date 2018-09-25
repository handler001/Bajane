package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.BaseActivity;
import com.jiefutong.emall.activity.OrderDetailActivity;
import com.jiefutong.emall.activity.ProductBuyInfoActivity;
import com.jiefutong.emall.bean.BannerBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.GoodsBuyNowBean;
import com.jiefutong.emall.bean.OrderInfoBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.ToastUtil;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpRetryException;
import java.util.List;

/**
 * @Author l
 * @Date 2018/7/17
 */
public class OrderMeInfoAdapter extends BaseQuickAdapter<OrderInfoBean.DataMapBean.ContentBean, BaseViewHolder> {
    public Context mContext;

    public OrderMeInfoAdapter(int layoutResId, @Nullable List<OrderInfoBean.DataMapBean.ContentBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderInfoBean.DataMapBean.ContentBean item) {
        helper.setText(R.id.tv_order_code, "订单号:" + item.orderNo)
                .setText(R.id.tv_goods_total, "共" + item.totalNum + "件商品  总计:")
                .setText(R.id.tv_order_price, "￥" + item.totalAmt);
        if (item.orderStatus == 1) {
            helper.setText(R.id.tv_order_state, "待付款");
            helper.getView(R.id.tv_order_pay).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_order_cancle).setVisibility(View.VISIBLE);
            helper.getView(R.id.rl_order).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_order_pay, "支付订单").setText(R.id.tv_order_cancle, "取消订单");
            helper.getView(R.id.tv_order_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DiaglogUtils(mContext, "是否要取消该订单") {
                        @Override
                        public void sureMessage() {
                            HttpParams params = new HttpParams();
                            params.put("orderNo", item.orderNo);
                            HttpUtils.getNetData(HttpUtils.ORDER_PAY_CANClE, (BaseActivity) mContext, params, new MyStringCallBack(mContext) {
                                @Override
                                protected void dealdata(String data) {
                                    CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                                    if (bean != null && bean.code == HttpUtils.SUCCESS) {
                                        EventBusBean beans = new EventBusBean();
                                        beans.cod = "order";
                                        EventBus.getDefault().post(beans);
                                        ToastUtil.show(mContext, "取消订单成功");
                                    } else {
                                        ToastUtil.show(mContext, "取消订单失败");
                                    }
                                }
                            });
                        }
                    };
                }
            });
            helper.getView(R.id.tv_order_pay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpParams params = new HttpParams();
                    params.put("orderNo", item.orderNo);
                    HttpUtils.getNetData(HttpUtils.ORDER_PAY_NOW, (BaseActivity) mContext, params, new MyStringCallBack(mContext) {
                        @Override
                        protected void dealdata(String data) {
                            GoodsBuyNowBean bean = JsonUtil.parseObject(data, GoodsBuyNowBean.class);
                            if (bean != null && bean.code == HttpUtils.SUCCESS) {
                                EventBusBean beans = new EventBusBean();
                                beans.cod = "order";
                                EventBus.getDefault().post(beans);
                                mContext.startActivity(new Intent(mContext, ProductBuyInfoActivity.class)
                                        .putExtra("bean", bean.dataMap)
                                        .putExtra("orderNo", item.orderNo));
                            } else {
                                ToastUtil.show(mContext, "支付订单失败");
                            }
                        }
                    });
                }
            });
        } else if (item.orderStatus == 2) {
            helper.setText(R.id.tv_order_state, "待发货");
            helper.getView(R.id.tv_order_pay).setVisibility(View.GONE);
            helper.getView(R.id.tv_order_cancle).setVisibility(View.GONE);
            helper.getView(R.id.rl_order).setVisibility(View.GONE);
        } else if (item.orderStatus == 3) {
            helper.setText(R.id.tv_order_state, "待收货");
            helper.getView(R.id.rl_order).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_order_pay).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_order_cancle).setVisibility(View.GONE);
            helper.setText(R.id.tv_order_pay, "确认收货");
            //        helper.setText(R.id.tv_order_cancle, "查看物流");
//            helper.getView(R.id.tv_order_cancle).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.show(mContext, "物流");
//                }
//            });
            helper.getView(R.id.tv_order_pay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DiaglogUtils(mContext, "确认收货么") {
                        @Override
                        public void sureMessage() {
                            HttpParams params = new HttpParams();
                            params.put("orderNo", item.orderNo);
                            HttpUtils.getNetData(HttpUtils.ORDER_INFO_GET, (BaseActivity) mContext, params, new MyStringCallBack(mContext) {
                                @Override
                                protected void dealdata(String data) {
                                    CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                                    if (bean != null && bean.code == HttpUtils.SUCCESS) {
                                        EventBusBean beans = new EventBusBean();
                                        beans.cod = "order";
                                        EventBus.getDefault().post(beans);
                                    } else {
                                        ToastUtil.show(mContext, "确认失败,请重试");
                                    }
                                }
                            });
                        }
                    };
                }
            });
        } else if (item.orderStatus == 4) {
            helper.getView(R.id.rl_order).setVisibility(View.GONE);
            helper.setText(R.id.tv_order_state, "待验证");
            helper.getView(R.id.tv_order_pay).setVisibility(View.GONE);
            helper.getView(R.id.tv_order_cancle).setVisibility(View.GONE);
        } else if (item.orderStatus == 5) {
            helper.getView(R.id.rl_order).setVisibility(View.GONE);
            helper.setText(R.id.tv_order_state, "已完成");
            helper.getView(R.id.tv_order_pay).setVisibility(View.GONE);
            helper.getView(R.id.tv_order_cancle).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.rl_order).setVisibility(View.GONE);
            helper.setText(R.id.tv_order_state, "已取消");
            helper.getView(R.id.tv_order_pay).setVisibility(View.GONE);
            helper.getView(R.id.tv_order_cancle).setVisibility(View.GONE);
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("id", item.orderNo));
            }
        });
        RecyclerView rlv = helper.getView(R.id.prl_center);
        rlv.setLayoutManager(new LinearLayoutManager(mContext));
        rlv.setAdapter(new InfoAdapter(R.layout.item_goods_info_count, item.goods, item.orderNo));
    }

    class InfoAdapter extends BaseQuickAdapter<OrderInfoBean.DataMapBean.ContentBean.GoodsBean, BaseViewHolder> {
        private String id;

        public InfoAdapter(int layoutResId, @Nullable List<OrderInfoBean.DataMapBean.ContentBean.GoodsBean> data, String id) {
            super(layoutResId, data);
            this.id = id;
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderInfoBean.DataMapBean.ContentBean.GoodsBean item) {
            GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.cover);
            helper.setText(R.id.tv_name, item.goodsName)
                    .setText(R.id.tv_price, "￥" + item.goodsPrice)
                    .setText(R.id.tv_count, "x" + item.goodsNum);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("id", id));
                }
            });
        }
    }
}
