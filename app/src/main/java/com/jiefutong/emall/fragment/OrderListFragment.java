package com.jiefutong.emall.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.OrderMeInfoAdapter;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.OrderInfoBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * @Author l
 * @Date 2018/7/19
 */
public class OrderListFragment extends BaseFragment {
    private int position;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<OrderInfoBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private int page = 1;
    private SwipeRefreshLayout msrl;

    @SuppressLint("ValidFragment")
    public OrderListFragment(int position) {
        this.position = position;
        EventBus.getDefault().register(this);
    }

    public OrderListFragment() {
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        HttpParams params = new HttpParams();
        params.put("page", page);
        if (position == 0) {
            params.put("orderStatus", "");
        } else if (position == 1) {
            params.put("orderStatus", "1");
        } else if (position == 2) {
            params.put("orderStatus", "2");
        } else if (position == 3) {
            params.put("orderStatus", "3");
        } else {
            params.put("orderStatus", "5");
        }
        HttpUtils.getFraNetData(HttpUtils.ORDER_LIST, this, params, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                OrderInfoBean bean = JsonUtil.parseObject(data, OrderInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.loadMoreComplete();
                    msrl.setRefreshing(false);
                    if (page == 1) {
                        adapter.setNewData(bean.dataMap.content);
                    } else {
                        adapter.addData(bean.dataMap.content);
                    }
                    if (bean.dataMap.content.size() < 10) {
                        adapter.loadMoreEnd(false);
                    }
                    ++page;
                } else {
                    adapter.loadMoreComplete();
                    adapter.loadMoreFail();
                    msrl.setRefreshing(false);
                }
            }
        });
    }

    @Override
    protected void intiview(View view) {
        View empty = View.inflate(mctx, R.layout.empty_goods_show, null);
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new  OrderMeInfoAdapter(R.layout.item_me_oredr_info, datas, mctx);
        msrl = view.findViewById(R.id.sfl);
        mRlvContent.setAdapter(adapter);
        adapter.setEmptyView(empty);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, mRlvContent);
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                adapter.setEnableLoadMore(true);
                loadData();
            }
        });
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_income_list;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean event) {
        if (event.cod.equals("order")) {
            page = 1;
            datas.clear();
            loadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }
}
