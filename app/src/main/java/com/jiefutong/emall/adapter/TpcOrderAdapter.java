package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.activity.OrderPayBuyActivity;
import com.jiefutong.emall.bean.TpcOrderBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/28
 */
public class TpcOrderAdapter extends BaseQuickAdapter<TpcOrderBean, BaseViewHolder> {
    public TpcOrderAdapter(int layoutResId, @Nullable List<TpcOrderBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TpcOrderBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, OrderPayBuyActivity.class));
            }
        });
    }
}
