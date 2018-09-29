package com.jiefutong.emall.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.BargainMoneyBean;
import com.jiefutong.emall.utils.ResourceUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/28
 */
public class BargainMoneyAdapter extends BaseQuickAdapter<BargainMoneyBean.DataMapBean, BaseViewHolder> {
    private int pos = 0;

    public BargainMoneyAdapter(int layoutResId, @Nullable List<BargainMoneyBean.DataMapBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BargainMoneyBean.DataMapBean item) {
        int position = helper.getLayoutPosition();
        if (position == pos) {
            helper.setTextColor(R.id.tv_money, ResourceUtils.getColor(mContext, R.color.white));
            helper.setBackgroundRes(R.id.tv_money, R.drawable.shape_orange_bg_tpc_money);
        } else {
            helper.setTextColor(R.id.tv_money, ResourceUtils.getColor(mContext, R.color.title_org_sel));
            helper.setBackgroundRes(R.id.tv_money, R.drawable.shape_orange_bg_post_stroke);
        }
        helper.setText(R.id.tv_money, item.productNum);
    }

    public void setposition(int pos) {
        this.pos = pos;
        notifyDataSetChanged();
    }
}
