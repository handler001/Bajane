package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.HistoryJoinBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class ActHistoryJoinActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlv;
    private BaseQuickAdapter adapter;
    private int page = 1;
    private ArrayList<HistoryJoinBean.DataMapBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_history_join);
        initView();
        settitlewhite();
        loaddata();
    }

    private void loaddata() {
        HttpParams params = new HttpParams();
        params.put("page", page);
        HttpUtils.getNetData(HttpUtils.HISTORY_JOIN, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                HistoryJoinBean bean = JsonUtil.parseObject(data, HistoryJoinBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.loadMoreComplete();
                    adapter.addData(bean.dataMap);
                    page++;
                    if (bean.dataMap.size() < 10) {
                        adapter.loadMoreEnd();
                    }
                } else {
                    showDataError();
                }
            }
        });
    }

    private void initView() {
        View view = View.inflate(context, R.layout.view_order_empty, null);
        TextView tv = view.findViewById(R.id.tv_info);
        tv.setText("暂无参与记录");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("参与记录");
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(context));
        adapter = new HistoryJoinAdapter(R.layout.item_history_join, datas);
        mRlv.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loaddata();
            }
        }, mRlv);
        adapter.setEmptyView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    class HistoryJoinAdapter extends BaseQuickAdapter<HistoryJoinBean.DataMapBean, BaseViewHolder> {

        public HistoryJoinAdapter(int layoutResId, @Nullable List<HistoryJoinBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HistoryJoinBean.DataMapBean item) {
            helper.setText(R.id.tv_number, "订单号:" + item.orderNo)
                    .setText(R.id.tv_type, item.activityStatus)
                    .setText(R.id.tv_content, item.content)
                    .setText(R.id.tv_time, item.activityTime);
            helper.setTextColor(R.id.tv_type, item.activityStatus.equals("排队中") ? getcolor(R.color.history_blue) : getcolor(R.color.history_green));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
