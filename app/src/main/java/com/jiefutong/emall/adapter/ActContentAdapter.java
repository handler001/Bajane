package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.ProductDetailActivity;
import com.jiefutong.emall.bean.ActContentBean;
import com.jiefutong.emall.utils.ResourceUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/11
 */
public class ActContentAdapter extends BaseQuickAdapter<ActContentBean.DataMapBean.DataBean, BaseViewHolder> {
    private int type;

    public ActContentAdapter(int layoutResId, @Nullable List<ActContentBean.DataMapBean.DataBean> data, Context ctx) {
        super(layoutResId, data);
        mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ActContentBean.DataMapBean.DataBean item) {
        helper.setText(R.id.tv_price, "￥" + item.price)
                .setText(R.id.tv_buy, item.num + "盒 立即购买")
                .setText(R.id.tv_name, item.activityName)
                .setText(R.id.tv_desc, item.activityTitle)
                .setGone(R.id.iv_two, item.activityNum > 1)
                .setGone(R.id.iv_first, item.activityNum > 0)
                .setText(R.id.tv_three, "直推" + item.directPush + " 间推" + item.indirectPush);
        if (type == 0 || TextUtils.isEmpty(item.glodCoinMore) || TextUtils.isEmpty(item.integralMore) || TextUtils.isEmpty(item.silverCoinMore)) {
            helper.setGone(R.id.tv_one, false);
        } else {
            helper.setGone(R.id.tv_one, true);
            helper.setText(R.id.tv_one, "多送" + item.glodCoinMore + "金币，多送" + item.silverCoinMore + "银币，多送" + item.integralMore + "积分");
        }
        if (type == 1) {
            String two = "到手" + item.glodCoin + "金币、" + item.silverCoin + "银币、" + item.integral + "积分、" + item.ticket + "张兑换券";
            SpannableStringBuilder gold = new SpannableStringBuilder(two);
            gold.setSpan(new ForegroundColorSpan(ResourceUtils.getColor(mContext, R.color.text_blue)),
                    2, item.glodCoin.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            gold.setSpan(new ForegroundColorSpan(ResourceUtils.getColor(mContext, R.color.text_blue)),
                    item.glodCoin.length() + 5, item.glodCoin.length() + 5 + item.silverCoin.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            gold.setSpan(new StyleSpan(Typeface.BOLD), item.glodCoin.length() + 8 + item.silverCoin.length(),
                    item.glodCoin.length() + 8 + item.silverCoin.length() + item.integral.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            gold.setSpan(new StyleSpan(Typeface.BOLD), item.glodCoin.length() + 11 + item.silverCoin.length() + item.integral.length(),
                    item.glodCoin.length() + 11 + item.silverCoin.length() + item.integral.length() + item.ticket.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            helper.setGone(R.id.tv_two,true);
            helper.setText(R.id.tv_two, gold);
        } else {
            helper.setGone(R.id.tv_two,false);
        }
        if (TextUtils.isEmpty(item.activityDesc)) {
            helper.setGone(R.id.tv_content, false);
        } else {
            helper.setGone(R.id.tv_content, true);
            helper.setText(R.id.tv_content, item.activityDesc);
        }
        helper.getView(R.id.tv_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("id", item.goodsId));
            }
        });
    }

    public void settype(int coinFlag) {
        this.type = coinFlag;
    }
}
