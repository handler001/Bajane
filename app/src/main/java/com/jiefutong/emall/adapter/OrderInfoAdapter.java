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
import com.jiefutong.emall.activity.MyOrederDetailActivity;
import com.jiefutong.emall.activity.OrderDetailActivity;
import com.jiefutong.emall.activity.RefundDetailsActivity;
import com.jiefutong.emall.bean.OrderInfoBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.ToastUtil;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/17
 */
public class OrderInfoAdapter extends BaseQuickAdapter<OrderInfoBean.DataMapBean.ContentBean, BaseViewHolder> {
    private int type;

    public OrderInfoAdapter(int layoutResId, @Nullable List<OrderInfoBean.DataMapBean.ContentBean> data, Context ctx, int type) {
        super(layoutResId, data);
        this.mContext = ctx;
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderInfoBean.DataMapBean.ContentBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    mContext.startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("id", item.orderNo));
                } else if (type == 1) {
                    mContext.startActivity(new Intent(mContext, MyOrederDetailActivity.class).putExtra("id", item.orderNo));
                } else {
                    mContext.startActivity(new Intent(mContext, RefundDetailsActivity.class).putExtra("id", item.id));
                }
            }
        });
        helper.setText(R.id.tv_order_code, "订单号: " + item.orderNo)
                .setText(R.id.tv_order_state, item.orderStatus == 4 ? "未验证" : "已验证")
                .setText(R.id.tv_order_price, "￥" + item.totalAmt)
                .setText(R.id.tv_num_total, "共" + item.totalNum + "件商品  总计:");
        RecyclerView rlv = helper.getView(R.id.prl_center);
        rlv.setLayoutManager(new LinearLayoutManager(mContext));
        rlv.setAdapter(new InfoAdapter(R.layout.item_goods_info_count, item.goods, item.orderNo, item.id));
    }

    class InfoAdapter extends BaseQuickAdapter<OrderInfoBean.DataMapBean.ContentBean.GoodsBean, BaseViewHolder> {
        private String orderno;
        private String id;

        public InfoAdapter(int layoutResId, @Nullable List<OrderInfoBean.DataMapBean.ContentBean.GoodsBean> data, String orderno, String id) {
            super(layoutResId, data);
            this.orderno = orderno;
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
                    if (type == 0) {
                        mContext.startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("id", orderno));
                    } else if (type == 1) {
                        mContext.startActivity(new Intent(mContext, MyOrederDetailActivity.class).putExtra("id", orderno));
                    } else {
                        mContext.startActivity(new Intent(mContext, RefundDetailsActivity.class).putExtra("id", id));
                    }
                }
            });
        }
    }
}
