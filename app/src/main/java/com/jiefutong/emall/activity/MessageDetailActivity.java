package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.MessageDetailBean;
import com.jiefutong.emall.bean.MsgDetailBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MessageDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvContent;
    private String name;
    private String type;
    private BaseQuickAdapter adapter;
    private ArrayList<MessageDetailBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        initView();
        settitlewhite();
        loaddata();
    }

    private void initView() {
        View empty = View.inflate(context, R.layout.empty_goods_show, null);
        TextView tv = empty.findViewById(R.id.tv_info);
        tv.setText("暂无消息");
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(name);
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MessageDetailAdapter(R.layout.item_msg_detail, datas);
        mRlvContent.setAdapter(adapter);
        adapter.setEmptyView(empty);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loaddata();
            }
        }, mRlvContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void loaddata() {
        final HttpParams params = new HttpParams();
        params.put("msgType", type);
        params.put("page", page);
        HttpUtils.getNetData(HttpUtils.MSG_DETAIL_LIST, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                MessageDetailBean bean = JsonUtil.parseObject(data, MessageDetailBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.loadMoreComplete();
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

    private class MessageDetailAdapter extends BaseQuickAdapter<MessageDetailBean.DataMapBean.ContentBean, BaseViewHolder> {

        public MessageDetailAdapter(int layoutResId, @Nullable List<MessageDetailBean.DataMapBean.ContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final MessageDetailBean.DataMapBean.ContentBean item) {
            helper.setText(R.id.tv_title, item.title)
                    .setText(R.id.tv_time, item.createDate);
            GlideUtils.loadpic(context, (ImageView) helper.getView(R.id.iv_pic), item.iconUrl);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpDetail(item);
                }
            });
            if (item.readFlag == 0) {
                helper.getView(R.id.tv_read).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.tv_read).setVisibility(View.GONE);
            }
        }
    }

    private void jumpDetail(final MessageDetailBean.DataMapBean.ContentBean item) {
        HttpParams params = new HttpParams();
        params.put("id", item.id);
        HttpUtils.getNetData(HttpUtils.MSG_DETAIL_INFO, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                MsgDetailBean bean = JsonUtil.parseObject(data, MsgDetailBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        openActivity(new Intent(context, BannerWebActivity.class).putExtra("url", bean.dataMap.msg));
                        EventBusBean eventBusBean = new EventBusBean();
                        eventBusBean.cod = "message";
                        EventBus.getDefault().post(eventBusBean);
                        item.readFlag = 1;
                        adapter.notifyDataSetChanged();
                    } else {
                        showToast(bean.message);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
