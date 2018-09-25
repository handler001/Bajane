package com.jiefutong.emall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.GoodsDetailActivity;
import com.jiefutong.emall.bean.GoodsDataBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.ShareListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/11
 */
public class MallGoodsAdapter extends BaseQuickAdapter<GoodsDataBean, BaseViewHolder> {
    public int num = -1;

    public MallGoodsAdapter(int layoutResId, @Nullable List<GoodsDataBean> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GoodsDataBean item) {
        final int position = helper.getLayoutPosition();
        GlideUtils.loadpropic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.pic, (LottieAnimationView) helper.getView(R.id.lav));
        helper.setText(R.id.tv_name, item.small_title)
                .setText(R.id.tv_price, item.price)
                .setText(R.id.tv_sales, "销量 " + item.sales)
                .setText(R.id.tv_ticket_price, item.coupon_price + "元券")
                .setText(R.id.tv_get_money, item.anticipated_money)
                .setText(R.id.tv_ticket_share_gold, "购买预计赚取" + item.anticipated_money + "元");
        if (TextUtils.isEmpty(item.official)) {
            helper.setText(R.id.tv_content_detail, item.title);
        } else {
            helper.setText(R.id.tv_content_detail, item.official);
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = -1;
                if (helper.getView(R.id.rl_show).getVisibility() == View.VISIBLE) {
                    helper.getView(R.id.rl_show).setVisibility(View.GONE);
                } else {
                    notifyDataSetChanged();
                    mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", item.numIid));
                }

            }
        });
        if (num == position) {
            helper.getView(R.id.rl_show).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.rl_show).setVisibility(View.GONE);
        }
        helper.getView(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = position;
                helper.getView(R.id.rl_show).setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });

        helper.getView(R.id.tv_ticket_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = -1;
                helper.getView(R.id.rl_show).setVisibility(View.GONE);
            }
        });

        helper.getView(R.id.tv_ticket_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = -1;
                helper.getView(R.id.rl_show).setVisibility(View.GONE);
                shareurl(item);
            }
        });
    }

    public void shareurl(GoodsDataBean item) {
        UMWeb web = new UMWeb(item.share_url);
        web.setTitle(item.small_title);
        web.setDescription(item.official);
        web.setThumb(new UMImage(mContext, item.pic));
        new ShareAction((Activity) mContext)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new ShareListener(mContext))
                .open();
    }
}
