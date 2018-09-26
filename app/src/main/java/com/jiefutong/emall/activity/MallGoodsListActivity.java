package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.GoodsTypeBean;
import com.jiefutong.emall.utils.FragmentFactory;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import java.util.List;

public class MallGoodsListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvCategory;
    private BaseQuickAdapter adapter;
    private int curPosition;
    private Fragment mCurrentFrgment;
    private List<GoodsTypeBean.DataMapBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_goods_list);
        initView();
        settitlewhite();
        getNetdata();
    }

    private void getNetdata() {
        HttpUtils.getNetData(HttpUtils.GOODS_CATEGORY_LIST_TYPE, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                GoodsTypeBean bean = JsonUtil.parseObject(data, GoodsTypeBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    datas = bean.dataMap;
                    dealinfo(bean);
                }
            }
        });
    }

    private void dealinfo(GoodsTypeBean bean) {
        initadapter();
        dealposition(0, datas.get(0).id);
    }

    private void dealposition(int i, String id) {
        //事物管理
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏当前
        if (mCurrentFrgment != null) {
            transaction.hide(mCurrentFrgment);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(i));
        //为空添加
        if (fragment == null) {
            fragment = FragmentFactory.createCategory(i, this, id);
        }
        //不为空显示
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_content, fragment, String.valueOf(i));
        }
        mCurrentFrgment = fragment;
        transaction.commit();
    }


    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("分类");
        mRlvCategory = (RecyclerView) findViewById(R.id.rlv_category);
        mRlvCategory.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        FragmentFactory.categorys.clear();
    }

    private void initadapter() {
        if (adapter == null) {
            adapter = new MallGoodsCategoryAdapter(R.layout.item_goods_category, datas);
            mRlvCategory.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    curPosition = position;
                    initadapter();
                    dealposition(position, datas.get(position).id);
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public class MallGoodsCategoryAdapter extends BaseQuickAdapter<GoodsTypeBean.DataMapBean, BaseViewHolder> {


        public MallGoodsCategoryAdapter(int layoutResId, @Nullable List<GoodsTypeBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsTypeBean.DataMapBean item) {
            helper.setText(R.id.tv_name, item.name);
            int pos = helper.getLayoutPosition();
            if (pos == curPosition) {
                ((TextView) helper.getView(R.id.tv_name)).setTextColor(getResources().getColor(R.color.title_bg_red));
            } else {
                ((TextView) helper.getView(R.id.tv_name)).setTextColor(getResources().getColor(R.color.black));
            }
        }
    }
}
