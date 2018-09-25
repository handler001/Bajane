package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.ProductDescActivity;
import com.jiefutong.emall.activity.ProductDetailActivity;
import com.jiefutong.emall.bean.GoodsShowBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.ToastUtil;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/10
 */
public class GoodShowAdapter extends BaseQuickAdapter<GoodsShowBean.DataMapBean, BaseViewHolder> {
    public GoodShowAdapter(int layoutResId, @Nullable List<GoodsShowBean.DataMapBean> data, Context mctx) {
        super(layoutResId, data);
        this.mContext = mctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsShowBean.DataMapBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("id", item.id));
            }
        });
        GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.goodIcon);
        helper.setText(R.id.tv_name, item.goodsName)
                .setText(R.id.tv_price, "￥" + item.goodPrice);
    }
}
