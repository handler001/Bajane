package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.RefundDetailsActivity;
import com.jiefutong.emall.bean.RefundListBean;
import com.jiefutong.emall.utils.GlideUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/10
 */
public class RefundListAdapter extends BaseQuickAdapter<RefundListBean.DataMapBean.ContentBean, BaseViewHolder> {
    public RefundListAdapter(int layoutResId, @Nullable List<RefundListBean.DataMapBean.ContentBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RefundListBean.DataMapBean.ContentBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, RefundDetailsActivity.class).putExtra("id", item.id));
            }
        });
        helper.setText(R.id.tv_order_code, "订单号: " + item.orderNo)
                .setText(R.id.tv_order_state, item.refundStatus == 1 ? "退款中" : item.refundStatus == 2 ? "退款成功 " : "退款失败")
                .setText(R.id.tv_num_total, "共" + item.totalNum + "件商品  总计:")
                .setText(R.id.tv_order_price, "￥" + item.totalAmt);
        RecyclerView rlv = helper.getView(R.id.prl_center);
        rlv.setLayoutManager(new LinearLayoutManager(mContext));
        rlv.setAdapter(new InfoAdapter(R.layout.item_goods_info_count, item.goodsList, item.id));
    }

    class InfoAdapter extends BaseQuickAdapter<RefundListBean.DataMapBean.ContentBean.GoodsListBean, BaseViewHolder> {
        private String id;

        public InfoAdapter(int layoutResId, @Nullable List<RefundListBean.DataMapBean.ContentBean.GoodsListBean> data, String orderno) {
            super(layoutResId, data);
            this.id = orderno;
        }

        @Override
        protected void convert(BaseViewHolder helper, RefundListBean.DataMapBean.ContentBean.GoodsListBean item) {
            GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.cover);
            helper.setText(R.id.tv_name, item.goodsName)
                    .setText(R.id.tv_price, "￥" + item.goodsPrice)
                    .setText(R.id.tv_count, "x" + item.goodsNum);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, RefundDetailsActivity.class).putExtra("id", id));
                }
            });
        }
    }
}
