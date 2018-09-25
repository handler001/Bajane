package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.ProductInfosActivity;
import com.jiefutong.emall.bean.ProductDescBean;
import com.jiefutong.emall.utils.GlideUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/14
 */
public class ProductDescAdapter extends BaseQuickAdapter<ProductDescBean.DataMapBean, BaseViewHolder> {
    public ProductDescAdapter(int layoutResId, @Nullable List<ProductDescBean.DataMapBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProductDescBean.DataMapBean item) {
        helper.getView(R.id.tv_watch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ProductInfosActivity.class)
                        .putExtra("id", item.id)
                        .putExtra("name", item.assortmentTitle));
            }
        });
        helper.setText(R.id.tv_name, item.assortmentTitle)
                .setText(R.id.tv_desc, item.assortmentDesc);
        GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.assortmentImg);
    }
}
