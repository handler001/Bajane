package com.jiefutong.emall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.TpcOrderAdapter;
import com.jiefutong.emall.bean.TpcOrderBean;

import java.util.ArrayList;
import java.util.List;

public class TpcOrderMeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvAll;
    private ImageView mIvAll;
    private LinearLayout mLlAll;
    private TextView mTvState;
    private ImageView mIvState;
    private LinearLayout mLlState;
    private RecyclerView mRlvOrder;
    private ArrayList<String> alls = new ArrayList<>();
    private ArrayList<String> states = new ArrayList<>();
    private PopupWindow popupWindow;
    private ListAdapter listAdapter;
    private String all = "全部类型";
    private String state = "全部状态";
    private int pos;
    private ArrayList<TpcOrderBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpc_order_me);
        initView();
        settitlewhite();
    }

    private void initView() {
        alls.add("全部类型");
        alls.add("买入");
        alls.add("卖出");
        states.add("全部状态");
        states.add("未完成");
        states.add("待确认");
        states.add("已完成");
        for (int i = 0; i < 4; i++) {
            datas.add(new TpcOrderBean());
        }
        View view = View.inflate(context, R.layout.view_order_empty, null);
        TextView textView = view.findViewById(R.id.tv_info);
        textView.setText("暂无该类订单");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("我的订单");
        mTvAll = (TextView) findViewById(R.id.tv_all);
        mIvAll = (ImageView) findViewById(R.id.iv_all);
        mLlAll = (LinearLayout) findViewById(R.id.ll_all);
        mLlAll.setOnClickListener(this);
        mTvState = (TextView) findViewById(R.id.tv_state);
        mIvState = (ImageView) findViewById(R.id.iv_state);
        mLlState = (LinearLayout) findViewById(R.id.ll_state);
        mLlState.setOnClickListener(this);
        mRlvOrder = (RecyclerView) findViewById(R.id.rlv_order);
        mRlvOrder.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TpcOrderAdapter(R.layout.item_tpc_order_list, datas,context);
        mRlvOrder.setAdapter(adapter);
        adapter.setEmptyView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_all:
                dealposition(0);
                break;
            case R.id.ll_state:
                dealposition(1);
                break;
        }
    }

    private void dealposition(int i) {
        if (i == 0) {
            pos = 0;
            mTvAll.setTextColor(getcolor(R.color.title_org_sel));
            mTvState.setTextColor(getcolor(R.color.text_black_3));
            mIvAll.setBackgroundResource(R.mipmap.btn_upward);
            mIvState.setBackgroundResource(R.mipmap.btn_down_xiangxia);
            showpop(i);
        } else if (i == 1) {
            pos = 1;
            mTvAll.setTextColor(getcolor(R.color.text_black_3));
            mTvState.setTextColor(getcolor(R.color.title_org_sel));
            mIvAll.setBackgroundResource(R.mipmap.btn_down_xiangxia);
            mIvState.setBackgroundResource(R.mipmap.btn_upward);
            showpop(i);
        } else {
            mTvAll.setTextColor(getcolor(R.color.text_black_3));
            mTvState.setTextColor(getcolor(R.color.text_black_3));
            mIvAll.setBackgroundResource(R.mipmap.btn_down_xiangxia);
            mIvState.setBackgroundResource(R.mipmap.btn_down_xiangxia);
        }
    }

    private void showpop(int i) {
        if (popupWindow == null) {
            View view = View.inflate(context, R.layout.pop_shop_list, null);
            RecyclerView recyclerView = view.findViewById(R.id.rlv_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if (i == 0) {
                listAdapter = new ListAdapter(R.layout.item_list_pop_order, alls);
            } else {
                listAdapter = new ListAdapter(R.layout.item_list_pop_order, states);
            }
            recyclerView.setAdapter(listAdapter);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealposition(3);
                    popupWindow.dismiss();
                }
            });
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            showPop(mLlAll);
        } else {
            if (i == 0) {
                listAdapter.setNewData(alls);
            } else {
                listAdapter.setNewData(states);
            }
            showPop(mLlAll);
        }
    }

    class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public ListAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final String item) {
            if (item.equals(all) || item.equals(state)) {
                helper.setTextColor(R.id.tv_name, getcolor(R.color.title_org_sel));
            } else {
                helper.setTextColor(R.id.tv_name, getcolor(R.color.text_black_3));
            }
            helper.setText(R.id.tv_name, item);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pos == 0) {
                        all = item;
                        mTvAll.setText(item);
                    } else {
                        state = item;
                        mTvState.setText(item);
                    }
                    popupWindow.dismiss();
                    dealposition(3);
                }
            });
        }
    }

    private void showPop(View v) {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            int[] a = new int[2];
            v.getLocationInWindow(a);
            popupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, a[1] + v.getHeight());
        } else {
            popupWindow.showAsDropDown(v);
        }
    }
}
