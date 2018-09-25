package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.RefundHistoryActivity;
import com.jiefutong.emall.bean.RefundDetailsBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/9
 */
public class RefundDetailsAdapter extends BaseQuickAdapter<RefundDetailsBean.DataMapBean.HistoryListBean, BaseViewHolder> {
    public RefundDetailsAdapter(int layoutResId, @Nullable List<RefundDetailsBean.DataMapBean.HistoryListBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RefundDetailsBean.DataMapBean.HistoryListBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, RefundHistoryActivity.class).putExtra("id", item.id));
            }
        });
        helper.setText(R.id.tv_desc, item.refundText)
                .setText(R.id.tv_time, item.dealDate);
    }
}
