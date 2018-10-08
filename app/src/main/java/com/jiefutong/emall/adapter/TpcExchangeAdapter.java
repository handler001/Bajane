package com.jiefutong.emall.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.bean.TpcExchangeBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/30
 */
public class TpcExchangeAdapter extends BaseQuickAdapter<TpcExchangeBean, BaseViewHolder> {
    public TpcExchangeAdapter(int layoutResId, @Nullable List<TpcExchangeBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, TpcExchangeBean item) {

    }
}
