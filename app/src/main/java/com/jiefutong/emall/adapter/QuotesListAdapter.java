package com.jiefutong.emall.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.bean.QuotesListBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/30
 */
public class QuotesListAdapter extends BaseQuickAdapter<QuotesListBean, BaseViewHolder> {
    public QuotesListAdapter(int layoutResId, @Nullable List<QuotesListBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, QuotesListBean item) {

    }
}
