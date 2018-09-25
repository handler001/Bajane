package com.jiefutong.emall.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.IncomeListBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.ResourceUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/13
 */
public class IncomeListAdapter extends BaseQuickAdapter<IncomeListBean.DataMapBean, BaseViewHolder> {
    public IncomeListAdapter(int layoutResId, @Nullable List<IncomeListBean.DataMapBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeListBean.DataMapBean item) {
        int position = helper.getLayoutPosition();
        if (position < 3) {
            helper.setTextColor(R.id.tv_num, ResourceUtils.getColor(mContext, R.color.text_black_3));
            helper.setTextColor(R.id.tv_income, ResourceUtils.getColor(mContext, R.color.text_income_sel));
            helper.setGone(R.id.iv_bg, true);
            if (position == 0) {
                helper.setBackgroundRes(R.id.iv_bg, R.mipmap.img_sybgold);
            } else if (position == 1) {
                helper.setBackgroundRes(R.id.iv_bg, R.mipmap.img_sybsilv);
            } else {
                helper.setBackgroundRes(R.id.iv_bg, R.mipmap.img_sybtong);
            }
        } else {
            helper.setTextColor(R.id.tv_num, ResourceUtils.getColor(mContext, R.color.text_gray_income));
            helper.setTextColor(R.id.tv_income, ResourceUtils.getColor(mContext, R.color.text_income_nor));
            helper.setGone(R.id.iv_bg, false);
        }
        GlideUtils.loadCirclepic(mContext, (ImageView) helper.getView(R.id.iv_header), item.headerImg);
        helper.setText(R.id.tv_name, item.realName)
                .setText(R.id.tv_income, "￥" + item.money)
                .setText(R.id.tv_num, String.valueOf(position + 1));
        if (TextUtils.isEmpty(item.viewUserType)) {
            helper.getView(R.id.tv_desc).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_desc).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_desc, item.viewUserType);
        }
    }
}
