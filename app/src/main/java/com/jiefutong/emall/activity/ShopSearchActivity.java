package com.jiefutong.emall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.ShopListAdapter;
import com.jiefutong.emall.bean.ShopListBean;
import com.jiefutong.emall.bean.ShopListDetailsBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class ShopSearchActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private EditText mEtSearch;
    private TextView mTvSearch;
    private TextView mTvAll;
    private ImageView mIvAll;
    private LinearLayout mLlAll;
    private TextView mTvAddress;
    private ImageView mIvAddress;
    private LinearLayout mLlAddress;
    private TextView mTvPx;
    private ImageView mIvPx;
    private LinearLayout mLlPx;
    private RecyclerView mRlvShop;
    private ArrayList<ShopListDetailsBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private View view;
    private PopupWindow popupWindow;
    private ArrayList<String> lists = new ArrayList<>();
    private ListAdapter listAdapter;
    private String curposition;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_search);
        id = getIntent().getStringExtra("id");
        initView();
        settitlewhite();
        initshop();
        initlist();
    }

    private void initView() {
        view = View.inflate(context, R.layout.view_order_empty, null);
        TextView textView = view.findViewById(R.id.tv_info);
        textView.setText("暂无相关商家");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvSearch.setOnClickListener(this);
        mTvAll = (TextView) findViewById(R.id.tv_all);
        mIvAll = (ImageView) findViewById(R.id.iv_all);
        mLlAll = (LinearLayout) findViewById(R.id.ll_all);
        mLlAll.setOnClickListener(this);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mIvAddress = (ImageView) findViewById(R.id.iv_address);
        mLlAddress = (LinearLayout) findViewById(R.id.ll_address);
        mLlAddress.setOnClickListener(this);
        mTvPx = (TextView) findViewById(R.id.tv_px);
        mIvPx = (ImageView) findViewById(R.id.iv_px);
        mLlPx = (LinearLayout) findViewById(R.id.ll_px);
        mLlPx.setOnClickListener(this);
        mRlvShop = (RecyclerView) findViewById(R.id.rlv_shop);
        mRlvShop.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ShopListAdapter(R.layout.item_shop_list, datas, context);
        mRlvShop.setAdapter(adapter);
        adapter.setEmptyView(view);
    }

    private void initshop() {
        HttpParams params = new HttpParams();
        if (id == null || TextUtils.isEmpty(id)) {
            params.put("shopCategoryId", "");
        } else {
            params.put("shopCategoryId", id);
        }
        HttpUtils.getNetData(HttpUtils.SHOP_LIST, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                ShopListBean bean = JsonUtil.parseObject(data, ShopListBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.addData(bean.dataMap);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    private void initlist() {
        for (int i = 0; i < 4; i++) {
            lists.add("测试" + i);
        }
    }

    private void submit() {
        String search = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            showToast("请输入搜索内容");
            return;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                submit();
                break;
            case R.id.ll_all:
                dealposition(0);
                break;
            case R.id.ll_address:
                dealposition(1);
                break;
            case R.id.ll_px:
                dealposition(2);
                break;
        }
    }

    private void dealposition(int i) {
        if (i == 0) {
            mTvAll.setTextColor(getcolor(R.color.title_org_sel));
            mIvAll.setBackgroundResource(R.mipmap.icon_upper_flei);
            mTvAddress.setTextColor(getcolor(R.color.text_black_3));
            mIvAddress.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvPx.setTextColor(getcolor(R.color.text_black_3));
            mIvPx.setBackgroundResource(R.mipmap.icon_downr_flei);
            showpop();
        } else if (i == 1) {
            mTvAll.setTextColor(getcolor(R.color.text_black_3));
            mIvAll.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvAddress.setTextColor(getcolor(R.color.title_org_sel));
            mIvAddress.setBackgroundResource(R.mipmap.icon_upper_flei);
            mTvPx.setTextColor(getcolor(R.color.text_black_3));
            mIvPx.setBackgroundResource(R.mipmap.icon_downr_flei);
            showpop();
        } else if (i == 2) {
            mTvAll.setTextColor(getcolor(R.color.text_black_3));
            mIvAll.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvAddress.setTextColor(getcolor(R.color.text_black_3));
            mIvAddress.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvPx.setTextColor(getcolor(R.color.title_org_sel));
            mIvPx.setBackgroundResource(R.mipmap.icon_upper_flei);
            showpop();
        } else {
            mTvAll.setTextColor(getcolor(R.color.text_black_3));
            mIvAll.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvAddress.setTextColor(getcolor(R.color.text_black_3));
            mIvAddress.setBackgroundResource(R.mipmap.icon_downr_flei);
            mTvPx.setTextColor(getcolor(R.color.text_black_3));
            mIvPx.setBackgroundResource(R.mipmap.icon_downr_flei);
        }
    }

    private void showpop() {
        if (popupWindow == null) {
            View view = View.inflate(context, R.layout.pop_shop_list, null);
            RecyclerView recyclerView = view.findViewById(R.id.rlv_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            listAdapter = new ListAdapter(R.layout.item_list_pop_shop, lists);
            recyclerView.setAdapter(listAdapter);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealposition(4);
                    popupWindow.dismiss();
                }
            });
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            showPop(mLlAll);
        } else {
            listAdapter.notifyDataSetChanged();
            showPop(mLlAll);
        }
    }

    class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public ListAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final String item) {
            if (item.equals(curposition)) {
                helper.setTextColor(R.id.tv_name, getcolor(R.color.title_org_sel));
                helper.getView(R.id.iv_show).setVisibility(View.VISIBLE);
            } else {
                helper.setTextColor(R.id.tv_name, getcolor(R.color.text_black_3));
                helper.getView(R.id.iv_show).setVisibility(View.INVISIBLE);
            }
            helper.setText(R.id.tv_name, item);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curposition = item;
                    popupWindow.dismiss();
                    dealposition(4);
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
