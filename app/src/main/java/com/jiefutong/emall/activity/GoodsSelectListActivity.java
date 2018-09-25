package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.GoodsBean;
import com.jiefutong.emall.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


public class GoodsSelectListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlv;
    private ImageView mIvAll;
    private TextView mTvSubmit;
    private ArrayList<GoodsBean> datas;
    private BaseQuickAdapter adapter;
    private ArrayList<String> goodsIdlist = new ArrayList<>();
    private ArrayList<GoodsBean> beans = new ArrayList<>();
    private boolean all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_select_list);
        initView();
        settitlewhite();
    }

    private void initView() {
        datas = getIntent().getParcelableArrayListExtra("data");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("选择商品列表");
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(context));
        mIvAll = (ImageView) findViewById(R.id.iv_all);
        mIvAll.setOnClickListener(this);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
        adapter = new GoodsAdapter(R.layout.item_goods_list_select, datas);
        mRlv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_all:
                all = !all;
                if (all) {
                    for (int i = 0; i < datas.size(); i++) {
                        goodsIdlist.add(datas.get(i).id);
                    }
                    mIvAll.setBackgroundResource(R.mipmap.btn_paystyle_selected);
                    adapter.notifyDataSetChanged();
                } else {
                    goodsIdlist.clear();
                    mIvAll.setBackgroundResource(R.mipmap.btn_paystyle_default);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_submit:
                if (goodsIdlist.size() == 0) {
                    showToast("请选择要退款的商品");
                    return;
                }
                beans.clear();
                for (int i = 0; i < datas.size(); i++) {
                    if (goodsIdlist.contains(datas.get(i).id)) {
                        beans.add(datas.get(i));
                    }
                }
                openActivity(new Intent(context, ReturnActivity.class)
                        .putParcelableArrayListExtra("data", beans));
                finish();
                break;
        }
    }

    class GoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
        public GoodsAdapter(int layoutResId, @Nullable List<GoodsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodsBean item) {
            GlideUtils.loadpic(context, (ImageView) helper.getView(R.id.iv_pic), item.cover);
            helper.setText(R.id.tv_name, item.goodsName)
                    .setText(R.id.tv_type, "￥" + item.goodsPrice)
                    .setText(R.id.tv_num, "x" + item.goodsNum);
            helper.getView(R.id.iv_select).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (goodsIdlist.contains(item.id)) {
                        goodsIdlist.remove(item.id);
                    } else {
                        goodsIdlist.add(item.id);
                    }
                    notifyDataSetChanged();
                    if (goodsIdlist.size() == datas.size()) {
                        mIvAll.setBackgroundResource(R.mipmap.btn_paystyle_selected);
                    } else {
                        mIvAll.setBackgroundResource(R.mipmap.btn_paystyle_default);
                    }
                }
            });
            if (goodsIdlist.contains(item.id)) {
                helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.btn_paystyle_selected);
            } else {
                helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.btn_paystyle_default);
            }
        }
    }
}
