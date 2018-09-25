package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.TakeDetailsInfoActivity;
import com.jiefutong.emall.bean.TakeDetailsBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/20
 */
public class TakeDetailsAdapter extends BaseQuickAdapter<TakeDetailsBean.DataMapBean.DataListBean, BaseViewHolder> {
    public TakeDetailsAdapter(int layoutResId, @Nullable List<TakeDetailsBean.DataMapBean.DataListBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TakeDetailsBean.DataMapBean.DataListBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, TakeDetailsInfoActivity.class).putExtra("id",item.id));
            }
        });
        helper.setText(R.id.tv_order_code, "订单编号：" + item.flowId)
                .setText(R.id.tv_time, item.createDate)
                .setText(R.id.tv_type, item.payType == 1 ? "银行卡" : "支付宝")
                .setText(R.id.tv_money, item.money)
                .setText(R.id.tv_info, item.payStatus == 1 ? "审核中" : item.payStatus == 2 ? "已通过" : "未通过")
                .setTextColor(R.id.tv_info, item.payStatus == 1 ? mContext.getResources().getColor(R.color.take_details_check)
                        : item.payStatus == 2 ? mContext.getResources().getColor(R.color.take_details_pass)
                        : mContext.getResources().getColor(R.color.take_details_nopass));
    }
}
