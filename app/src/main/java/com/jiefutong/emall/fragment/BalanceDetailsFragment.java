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
import com.jiefutong.emall.adapter.BalanceDetailsAdapter;
import com.jiefutong.emall.bean.BalanceBean;
import com.jiefutong.emall.bean.OrderInfoBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

/**
 * @Author l
 * @Date 2018/7/20
 */
public class BalanceDetailsFragment extends BaseFragment {
    private int position, type;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<BalanceBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private View empty;
    private int page = 1;
    private SwipeRefreshLayout msrl;

    @SuppressLint("ValidFragment")
    public BalanceDetailsFragment(int position, int type) {
        this.position = position;
        this.type = type;
    }

    public BalanceDetailsFragment() {
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        final HttpParams params = new HttpParams();
        if (position == 0) {
            params.put("flowType", "");
        } else {
            params.put("flowType", String.valueOf(position));
        }
        params.put("page", page);
        if (type == 4) {
            params.put("coinType", "1");
        } else {
            params.put("coinType", String.valueOf(type + 2));
        }
        HttpUtils.getFraNetData(HttpUtils.BALANCE_INFO_DETAILS, this, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                BalanceBean bean = JsonUtil.parseObject(response.body(), BalanceBean.class);
                adapter.loadMoreComplete();
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    ++page;
                    if (bean.dataMap.content.size() < bean.dataMap.pageable.pageSize) {
                        adapter.loadMoreEnd();
                    }
                    adapter.addData(bean.dataMap.content);
                } else {
                    adapter.loadMoreFail();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                adapter.loadMoreFail();
            }
        });
    }

    @Override
    protected void intiview(View view) {
        empty = View.inflate(mctx, R.layout.empty_goods_show, null);
        TextView info = empty.findViewById(R.id.tv_info);
        info.setText("暂无余额明细");
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new BalanceDetailsAdapter(R.layout.item_frag_balance_info, datas);
        mRlvContent.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, mRlvContent);
        adapter.setEmptyView(empty);
        msrl = view.findViewById(R.id.sfl);
        msrl.setEnabled(false);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_income_list;
    }

}
