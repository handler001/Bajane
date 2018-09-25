package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.BannerWebActivity;
import com.jiefutong.emall.activity.WebViewActivity;
import com.jiefutong.emall.bean.ProductInfosBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.ToastUtil;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/14
 */
public class ProInfosAdapter extends BaseQuickAdapter<ProductInfosBean.DataMapBean.ContentBean, BaseViewHolder> {
    public ProInfosAdapter(int layoutResId, @Nullable List<ProductInfosBean.DataMapBean.ContentBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProductInfosBean.DataMapBean.ContentBean item) {
        helper.setText(R.id.tv_name, item.title)
                .setText(R.id.tv_time, item.createDate);
        GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.imgUrl);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BannerWebActivity.class).putExtra("url", item.content));
            }
        });
    }
}
