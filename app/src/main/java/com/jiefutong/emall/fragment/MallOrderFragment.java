package com.jiefutong.emall.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.MallOrderListBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author l
 * @Date 2018/9/13
 */

public class MallOrderFragment extends BaseFragment {
    private int position;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<MallOrderListBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private int page = 1;

    @SuppressLint("ValidFragment")
    public MallOrderFragment(int position) {
        super();
        this.position = position;
    }

    public MallOrderFragment() {

    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        HttpParams params = new HttpParams();
        params.put("page", String.valueOf(page));
        if (position == 0) {
            params.put("status", "");
        } else if (position == 1) {
            params.put("status", "0");
        } else {
            params.put("status", "1");
        }
        HttpUtils.getFraNetData(HttpUtils.LIST_MALL_ORDER, this, params, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                adapter.loadMoreComplete();
                MallOrderListBean bean = JsonUtil.parseObject(data, MallOrderListBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.addData(bean.dataMap.content);
                    if (bean.dataMap.content.size() < 10) {
                        adapter.loadMoreEnd();
                    }
                    page++;
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
        View empty = View.inflate(mctx, R.layout.empty_goods_show, null);
        TextView textView = empty.findViewById(R.id.tv_info);
        textView.setText("暂无订单");
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new OrderAdapter(R.layout.item_mall_order_list, datas);
        mRlvContent.setAdapter(adapter);
        adapter.setEmptyView(empty);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, mRlvContent);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_income_list;
    }

    class OrderAdapter extends BaseQuickAdapter<MallOrderListBean.DataMapBean.ContentBean, BaseViewHolder> {

        public OrderAdapter(int layoutResId, @Nullable List<MallOrderListBean.DataMapBean.ContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MallOrderListBean.DataMapBean.ContentBean item) {
            GlideUtils.loadpic(mctx, (ImageView) helper.getView(R.id.iv_pic), item.goodsThumbUrl);
            helper.setText(R.id.tv_type, item.orderTypeDesc)
                    .setText(R.id.tv_name, "订单号:" + item.orderSn)
                    .setText(R.id.tv_state, item.statuDesc)
                    .setText(R.id.tv_money, "付款" + item.money + "元")
                    .setText(R.id.tv_money_back, "返还" + item.promotionAmount + "元")
                    .setText(R.id.tv_time, "下单时间: " + item.createdAt);

        }
    }
}
