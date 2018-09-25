package com.jiefutong.emall.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.RefundListAdapter;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.RefundListBean;
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
public class SalesAfterFragment extends BaseFragment {
    private int position;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<RefundListBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private SwipeRefreshLayout msrl;

    @SuppressLint("ValidFragment")
    public SalesAfterFragment(int position) {
        this.position = position;
    }

    public SalesAfterFragment() {
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        HttpParams params = new HttpParams();
        params.put("refundStatus", String.valueOf(position + 1));
        HttpUtils.getFraNetData(HttpUtils.REFUND_LIST, this, params, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                RefundListBean bean = JsonUtil.parseObject(data, RefundListBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap.content != null) {
                        adapter.setNewData(bean.dataMap.content);
                    }
                } else {
                    showToast("数据异常,请重试");
                }
            }
        });
    }

    @Override
    protected void intiview(View view) {
        EventBus.getDefault().register(this);
        View empty = View.inflate(mctx, R.layout.empty_goods_show, null);
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new RefundListAdapter(R.layout.item_shop_oredr_info, datas, mctx);
        mRlvContent.setAdapter(adapter);
        adapter.setEmptyView(empty);
        msrl = view.findViewById(R.id.sfl);
        msrl.setEnabled(false);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_income_list;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean bean) {
        if (bean.cod.equals("refund")) {
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
