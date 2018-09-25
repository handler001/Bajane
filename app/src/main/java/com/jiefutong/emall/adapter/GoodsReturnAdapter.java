package com.jiefutong.emall.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.GoodsBean;
import com.jiefutong.emall.utils.GlideUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/8
 */
public class GoodsReturnAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    public GoodsReturnAdapter(int layoutResId, @Nullable List<GoodsBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        helper.setText(R.id.tv_price, "￥" + item.goodsPrice);
        GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.cover);
    }
}
