package com.jiefutong.emall.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.GoodsCategoryAdapter;
import com.jiefutong.emall.bean.GoodsCategoryBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/20
 */
public class MallCategoryDetailFragment extends BaseFragment {
    private Context ctx;
    private String id;
    private BaseQuickAdapter adapter;
    private View header;
    private RecyclerView mrlv;
    private ImageView mivheader;

    public MallCategoryDetailFragment() {
    }

    @SuppressLint("ValidFragment")
    public MallCategoryDetailFragment(Context ctx, String bean) {
        super();
        this.ctx = ctx;
        id = bean;
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        HttpParams params = new HttpParams();
        params.put("id", id);
        HttpUtils.getFraNetData(HttpUtils.GOODS_CATEGORY_LIST, this, params, new MyStringCallBack(ctx) {
            @Override
            protected void dealdata(String data) {
                GoodsCategoryBean bean = JsonUtil.parseObject(data, GoodsCategoryBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    GlideUtils.loadpic(ctx, mivheader, bean.dataMap.banner.url);
                    initAdapter(bean.dataMap.data);
                }
            }
        });
    }

    private void initAdapter(List<GoodsCategoryBean.DataMapBean.DataBean> data) {
        if (adapter == null) {
            adapter = new GoodsCategoryAdapter(R.layout.item_goods_catrgory_detail, data, ctx);
            mrlv.setAdapter(adapter);
            adapter.addHeaderView(header);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void intiview(View view) {
        mrlv = view.findViewById(R.id.rlv_category);
        mrlv.setLayoutManager(new LinearLayoutManager(ctx));
        header = View.inflate(ctx, R.layout.frag_category_detail, null);
        mivheader = header.findViewById(R.id.iv_pic);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_category_detail_header;
    }
}
