package com.jiefutong.emall.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.bean.RefundHistoryBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/9
 */
public class RefundHistoryAdapter extends BaseQuickAdapter<RefundHistoryBean, BaseViewHolder> {
    public RefundHistoryAdapter(int layoutResId, @Nullable List<RefundHistoryBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundHistoryBean item) {

    }
}
