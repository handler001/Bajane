package com.jiefutong.emall.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.GoodsCarManagerActivity;
import com.jiefutong.emall.activity.WebViewActivity;
import com.jiefutong.emall.bean.ActListBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author l
 * @Date 2018/9/6
 */
public class ActListFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageView mIvCar;
    private RecyclerView mRlvContent;
    private ArrayList<ActListBean.DataMapBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;

    @Override
    protected void listener() {
        mIvCar.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        HttpUtils.getFraNetData(HttpUtils.ACT_LIST_NEW, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                ActListBean bean = JsonUtil.parseObject(data, ActListBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.addData(bean.dataMap);
                }
            }
        });

    }

    @Override
    protected void intiview(View view) {
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText("活动");
        mIvCar = view.findViewById(R.id.iv_car);
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new ActListAdapter(R.layout.item_frag_list_act, datas);
        mRlvContent.setAdapter(adapter);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_act_details;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_car:
                openActivity(GoodsCarManagerActivity.class);
                break;
        }
    }

    class ActListAdapter extends BaseQuickAdapter<ActListBean.DataMapBean, BaseViewHolder> {

        public ActListAdapter(int layoutResId, @Nullable List<ActListBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ActListBean.DataMapBean item) {
            helper.setText(R.id.tv_title, item.title)
                    .setText(R.id.tv_desc, item.content);
            GlideUtils.loadpic(mctx, (ImageView) helper.getView(R.id.iv_pic), item.imgUrl);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(new Intent(mctx, WebViewActivity.class)
                            .putExtra("data", item));
                }
            });
        }
    }
}
