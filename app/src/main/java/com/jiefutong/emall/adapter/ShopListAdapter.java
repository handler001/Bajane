package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.ShopDetailActivity;
import com.jiefutong.emall.bean.ShopListBean;
import com.jiefutong.emall.bean.ShopListDetailsBean;
import com.jiefutong.emall.utils.GlideUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/25
 */
public class ShopListAdapter extends BaseQuickAdapter<ShopListDetailsBean, BaseViewHolder> {
    public ShopListAdapter(int layoutResId, @Nullable List<ShopListDetailsBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopListDetailsBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ShopDetailActivity.class).putExtra("id", item.shopId));
            }
        });
        GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.shopIcon);
        helper.setText(R.id.tv_name, item.shopName)
                .setText(R.id.tv_type, item.shopCategoryDesc)
                .setText(R.id.tv_address, item.addressDesc);
        if (TextUtils.isEmpty(item.distance)) {
            helper.getView(R.id.tv_discount).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_discount).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_discount, item.distance);
        }
        if (item.shopSignDesc.size() == 4) {
            helper.setText(R.id.tv_one, item.shopSignDesc.get(0))
                    .setText(R.id.tv_two, item.shopSignDesc.get(1))
                    .setText(R.id.tv_three, item.shopSignDesc.get(2))
                    .setText(R.id.tv_four, item.shopSignDesc.get(3));
        } else if (item.shopSignDesc.size() == 3) {
            helper.setText(R.id.tv_one, item.shopSignDesc.get(0))
                    .setText(R.id.tv_two, item.shopSignDesc.get(1))
                    .setText(R.id.tv_three, item.shopSignDesc.get(2));
            helper.getView(R.id.tv_four).setVisibility(View.GONE);
        } else if (item.shopSignDesc.size() == 2) {
            helper.setText(R.id.tv_one, item.shopSignDesc.get(0))
                    .setText(R.id.tv_two, item.shopSignDesc.get(1));
            helper.getView(R.id.tv_three).setVisibility(View.GONE);
            helper.getView(R.id.tv_four).setVisibility(View.GONE);
        } else if (item.shopSignDesc.size() == 1) {
            helper.setText(R.id.tv_one, item.shopSignDesc.get(0));
            helper.getView(R.id.tv_two).setVisibility(View.GONE);
            helper.getView(R.id.tv_three).setVisibility(View.GONE);
            helper.getView(R.id.tv_four).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_one).setVisibility(View.GONE);
            helper.getView(R.id.tv_two).setVisibility(View.GONE);
            helper.getView(R.id.tv_three).setVisibility(View.GONE);
            helper.getView(R.id.tv_four).setVisibility(View.GONE);
        }
    }
}
