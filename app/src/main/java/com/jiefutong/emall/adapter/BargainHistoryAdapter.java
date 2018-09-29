package com.jiefutong.emall.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.BargainHistoryBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/28
 */
public class BargainHistoryAdapter extends BaseQuickAdapter<BargainHistoryBean, BaseViewHolder> {
    private int type;

    public BargainHistoryAdapter(int layoutResId, @Nullable List<BargainHistoryBean> data, Context ctx, int type) {
        super(layoutResId, data);
        this.mContext = ctx;
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, BargainHistoryBean item) {
        helper.setText(R.id.tv_bargain, type == 0 ? "买入" : "卖出");
    }
}
