package com.jiefutong.emall.utils;

import android.support.v7.util.DiffUtil;

import com.jiefutong.emall.bean.GoodsShowBean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/31
 */
public class DiffDatasDeal extends DiffUtil.Callback {
    List<GoodsShowBean.DataMapBean> news;
    List<GoodsShowBean.DataMapBean> olds;

    public DiffDatasDeal(List<GoodsShowBean.DataMapBean> news,
                         List<GoodsShowBean.DataMapBean> olds) {
        this.news = news;
        this.olds = olds;
    }

    @Override
    public int getOldListSize() {
        return olds.size();
    }

    @Override
    public int getNewListSize() {
        return news.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return olds.get(oldItemPosition).getClass().equals(news.get(newItemPosition).getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return olds.get(oldItemPosition).id.equals(news.get(newItemPosition).id);
    }
}
