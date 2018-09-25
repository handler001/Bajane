package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.MallGoodsListActivity;
import com.jiefutong.emall.bean.GoodsCategoryBean;
import com.jiefutong.emall.utils.GlideUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/3/26
 */

public class GoodsCategoryAdapter extends BaseQuickAdapter<GoodsCategoryBean.DataMapBean.DataBean, BaseViewHolder> {
    private GoodsCategoryDetailAdapter adapter;

    public GoodsCategoryAdapter(int layoutResId, @Nullable List<GoodsCategoryBean.DataMapBean.DataBean> data, Context ctx) {
        super(layoutResId, data);
        mData = data;
        mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCategoryBean.DataMapBean.DataBean item) {
        helper.setText(R.id.tv_name, item.name);
        adapter = new GoodsCategoryDetailAdapter(R.layout.item_goods_category_detail_next, item.children);
        RecyclerView rlv = helper.getView(R.id.rlv_detail);
        rlv.setLayoutManager(new GridLayoutManager(mContext, 3));
        rlv.setAdapter(adapter);
    }

    public class GoodsCategoryDetailAdapter extends BaseQuickAdapter<GoodsCategoryBean.DataMapBean.DataBean.ChildrenBean, BaseViewHolder> {
        public GoodsCategoryDetailAdapter(int layoutResId, @Nullable List<GoodsCategoryBean.DataMapBean.DataBean.ChildrenBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodsCategoryBean.DataMapBean.DataBean.ChildrenBean item) {
            helper.setText(R.id.tv_name, item.name);
            GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_detail), item.pic);
        }
    }
}
