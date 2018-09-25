package com.jiefutong.emall.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.BalanceBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/20
 */
public class BalanceDetailsAdapter extends BaseQuickAdapter<BalanceBean.DataMapBean.ContentBean, BaseViewHolder> {
    public BalanceDetailsAdapter(int layoutResId, @Nullable List<BalanceBean.DataMapBean.ContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BalanceBean.DataMapBean.ContentBean item) {
        helper.setText(R.id.tv_goos_name, item.content)
                .setText(R.id.tv_goos_time, item.createDate)
                .setText(R.id.tv_goos_num, item.viewMoney);

    }
}
