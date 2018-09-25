package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.GoodsPddDetailActivity;
import com.jiefutong.emall.bean.FightBuyPddBean;
import com.jiefutong.emall.utils.GlideUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/5/17
 */
public class FightBuyAdapter extends BaseQuickAdapter<FightBuyPddBean.DataMapBean, BaseViewHolder> {

    public FightBuyAdapter(int layoutResId, @Nullable List<FightBuyPddBean.DataMapBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final FightBuyPddBean.DataMapBean item) {

        GlideUtils.loadpropic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.goods_thumbnail_url, (LottieAnimationView) helper.getView(R.id.lav));
        helper.setText(R.id.tv_name, item.goods_name)
                .setText(R.id.tv_price, item.price)
                .setText(R.id.tv_get_money, item.anticipated_money)
                .setText(R.id.tv_sales, "销量 " + item.sold_quantity);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, GoodsPddDetailActivity.class).putExtra("id", item.goods_id));
            }
        });

    }

}
