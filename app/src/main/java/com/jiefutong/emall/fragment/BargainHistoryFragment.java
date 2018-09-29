package com.jiefutong.emall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.BargainHistoryAdapter;
import com.jiefutong.emall.adapter.BargainMoneyAdapter;
import com.jiefutong.emall.bean.BargainHistoryBean;
import com.jiefutong.emall.bean.BargainMoneyBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

/**
 * @Author l
 * @Date 2018/9/28
 */
public class BargainHistoryFragment extends BaseFragment {
    private int type;
    private RecyclerView mRlvBar;
    private RecyclerView mRlvtitle;
    private TextView mTvtitle;
    private BaseQuickAdapter adapter;
    private ArrayList<BargainHistoryBean> datas = new ArrayList<>();
    private TextView mTvHeaderTitleMoney;

    public BargainHistoryFragment() {

    }

    @SuppressLint("ValidFragment")
    public BargainHistoryFragment(int type) {
        this.type = type;
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        HttpUtils.getFraNetData(HttpUtils.TPC_LIST_PRO, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                final BargainMoneyBean bean = JsonUtil.parseObject(data, BargainMoneyBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    mTvHeaderTitleMoney.setText("￥" + bean.dataMap.get(0).totalAmt);
                    final BargainMoneyAdapter moneyAdapter = new BargainMoneyAdapter(R.layout.item_bar_money_list, bean.dataMap);
                    mRlvtitle.setAdapter(moneyAdapter);
                    moneyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            moneyAdapter.setposition(position);
                            showToast(bean.dataMap.get(position).productNum);
                            mTvHeaderTitleMoney.setText("￥" + bean.dataMap.get(position).totalAmt);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void intiview(View view) {
        View empty = View.inflate(mctx, R.layout.view_order_empty, null);
        TextView tv = empty.findViewById(R.id.tv_info);
        tv.setText("没找到相关记录~");
        mTvtitle = view.findViewById(R.id.tv_header_title);
        if (type == 0) {
            mTvtitle.setText("请选择对应数量买入");
        } else {
            mTvtitle.setText("请选择对应数量卖出");
        }
        mTvHeaderTitleMoney = view.findViewById(R.id.tv_header_title_money);
        mRlvtitle = view.findViewById(R.id.rlv_money);
        mRlvtitle.setLayoutManager(new GridLayoutManager(mctx, 3));
        mRlvBar = view.findViewById(R.id.rlv_bar);
        mRlvBar.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new BargainHistoryAdapter(R.layout.item_bargain_list_his, datas, mctx, type);
        mRlvBar.setAdapter(adapter);
        adapter.setEmptyView(empty);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_bargain_history;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
