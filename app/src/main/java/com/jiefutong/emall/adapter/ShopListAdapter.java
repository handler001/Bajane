package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.activity.ShopDetailActivity;
import com.jiefutong.emall.bean.ShopListBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/25
 */
public class ShopListAdapter extends BaseQuickAdapter<ShopListBean, BaseViewHolder> {
    public ShopListAdapter(int layoutResId, @Nullable List<ShopListBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ShopDetailActivity.class));
            }
        });
    }
}
