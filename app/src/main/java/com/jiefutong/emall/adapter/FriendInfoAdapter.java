package com.jiefutong.emall.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.FriendInfoBean;
import com.jiefutong.emall.utils.GlideUtils;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/20
 */
public class FriendInfoAdapter extends BaseQuickAdapter<FriendInfoBean.DataMapBean, BaseViewHolder> {
    public FriendInfoAdapter(int layoutResId, @Nullable List<FriendInfoBean.DataMapBean> data, Context ctx) {
        super(layoutResId, data);
        this.mContext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendInfoBean.DataMapBean item) {
        GlideUtils.loadCirclepic(mContext, (ImageView) helper.getView(R.id.iv_header), item.headImgUrl);
        helper.setText(R.id.tv_name, item.realName)
                .setText(R.id.tv_type, item.userType)
                .setText(R.id.tv_phone, item.userName)
                .setText(R.id.tv_time, item.createDate)
                .setText(R.id.tv_money, "贡献金额: ￥" + item.offerMoney)
                .setText(R.id.tv_level, item.spread_level);

    }
}
