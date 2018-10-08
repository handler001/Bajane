package com.jiefutong.emall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.QuotesListAdapter;
import com.jiefutong.emall.bean.QuotesListBean;

import java.util.ArrayList;

/**
 * @Author l
 * @Date 2018/9/30
 */
public class QuotesListFragment extends BaseFragment {
    private int pos;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<QuotesListBean> datas = new ArrayList<>();
    private View empty;
    private SwipeRefreshLayout msrl;

    public QuotesListFragment() {

    }

    @SuppressLint("ValidFragment")
    public QuotesListFragment(int position) {
        this.pos = position;
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 4; i++) {
            datas.add(new QuotesListBean());
        }
    }

    @Override
    protected void intiview(View view) {
        empty = View.inflate(mctx, R.layout.empty_goods_show, null);
        TextView tv = empty.findViewById(R.id.tv_info);
        tv.setText("暂无该市场信息");
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new QuotesListAdapter(R.layout.item_quotes_list_info, datas, mctx);
        mRlvContent.setAdapter(adapter);
        adapter.setEmptyView(empty);
        msrl = view.findViewById(R.id.sfl);
        msrl.setEnabled(false);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_income_list;
    }
}
