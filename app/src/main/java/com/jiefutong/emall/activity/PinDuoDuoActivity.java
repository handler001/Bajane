package com.jiefutong.emall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.FightBuyAdapter;
import com.jiefutong.emall.bean.FightBuyPddBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

public class PinDuoDuoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvAlls;
    private TextView mTvTicket;
    private RecyclerView mRlvList;
    private RecyclerView mRlvListTicket;
    private String search;
    private FightBuyAdapter alladapter;
    private FightBuyAdapter adapter;
    private ArrayList<FightBuyPddBean.DataMapBean> all = new ArrayList<>();
    private ArrayList<FightBuyPddBean.DataMapBean> dataBeans = new ArrayList<>();
    private View empty, empty2;
    private EditText mEtSearch;
    private TextView mTvContentSearch;
    private boolean ticket;
    private int allposition = 1;
    private int position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_duo_duo);
        initView();
        settitlewhite();
    }

    private void initView() {
        empty = View.inflate(this, R.layout.empty_goods_show, null);
        empty2 = View.inflate(this, R.layout.empty_goods_show, null);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvAlls = (TextView) findViewById(R.id.tv_alls);
        mTvAlls.setOnClickListener(this);
        mTvTicket = (TextView) findViewById(R.id.tv_ticket);
        mTvTicket.setOnClickListener(this);
        mRlvList = (RecyclerView) findViewById(R.id.rlv_list);
        mRlvList.setLayoutManager(new GridLayoutManager(context, 2));
        mRlvListTicket = (RecyclerView) findViewById(R.id.rlv_list_ticket);
        mRlvListTicket.setLayoutManager(new GridLayoutManager(context, 2));
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTvContentSearch = (TextView) findViewById(R.id.tv_content_search);
        mTvContentSearch.setOnClickListener(this);
        alladapter = new FightBuyAdapter(R.layout.item_fight_buy, all, this);
        adapter = new FightBuyAdapter(R.layout.item_fight_buy, dataBeans, this);
        mRlvList.setAdapter(alladapter);
        mRlvListTicket.setAdapter(adapter);
        adapter.setEmptyView(empty);
        alladapter.setEmptyView(empty2);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initdata("true");
            }
        }, mRlvListTicket);

        alladapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initdata("false");
            }
        }, mRlvListTicket);
        initdata("false");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_alls:
                if (!ticket) {
                    return;
                }
                ticket = false;
                initdata("false");
                mTvAlls.setTextColor(getResources().getColor(R.color.title_bg_red));
                mTvTicket.setTextColor(getResources().getColor(R.color.text_black_6));
                mRlvList.setVisibility(View.VISIBLE);
                mRlvListTicket.setVisibility(View.GONE);
                break;
            case R.id.tv_ticket:
                if (ticket) {
                    return;
                }
                ticket = true;
                initdata("true");
                mTvAlls.setTextColor(getResources().getColor(R.color.text_black_6));
                mTvTicket.setTextColor(getResources().getColor(R.color.title_bg_red));
                mRlvList.setVisibility(View.GONE);
                mRlvListTicket.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_content_search:
                submit();
                break;
        }
    }

    private void initdata(final String s) {
        final HttpParams params = new HttpParams();
        if ("true".equals(s)) {
            params.put("page", String.valueOf(position));
        } else {
            params.put("page", String.valueOf(allposition));
        }
        if (TextUtils.isEmpty(search)) {
            params.put("keywords", "");
        } else {
            params.put("keywords", search);
        }
        params.put("has_coupon", s);
        HttpUtils.getNetData(HttpUtils.LIST_PDD, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                adapter.loadMoreComplete();
                alladapter.loadMoreComplete();
                FightBuyPddBean fightBuyPddBean = JsonUtil.parseObject(data, FightBuyPddBean.class);
                if (fightBuyPddBean != null && fightBuyPddBean.code == HttpUtils.SUCCESS) {
                    if ("true".equals(s)) {
                        if (position == 1) {
                            adapter.setNewData(fightBuyPddBean.dataMap);
                        } else {
                            adapter.addData(fightBuyPddBean.dataMap);
                        }
                        if (fightBuyPddBean.dataMap.size() < 20) {
                            adapter.loadMoreEnd();
                        }
                        position++;
                    } else {
                        if (allposition == 1) {
                            alladapter.setNewData(fightBuyPddBean.dataMap);
                        } else {
                            alladapter.addData(fightBuyPddBean.dataMap);
                        }
                        if (fightBuyPddBean.dataMap.size() < 20) {
                            alladapter.loadMoreEnd();
                        }
                        allposition++;
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if ("true".equals(s)) {
                    adapter.loadMoreFail();
                } else {
                    alladapter.loadMoreFail();
                }
            }
        });
    }

    private void submit() {
        hideSoftKeyboard(this);
        String content = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("请输入关键词");
            return;
        }
        search = content;
        position = 1;
        allposition = 1;
        if (ticket) {
            initdata("true");
        } else {
            initdata("false");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelAll();
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
