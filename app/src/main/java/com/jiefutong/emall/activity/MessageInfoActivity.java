package com.jiefutong.emall.activity;

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
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.MsgSysBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class MessageInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvSys;
    private RecyclerView mRlvMsg;
    private RecyclerView mRlvSys;
    private BaseQuickAdapter adapter;
    private BaseQuickAdapter sysadapter;
    private ArrayList<MsgSysBean.DataMapBean.ContentBean> msg = new ArrayList<>();
    private ArrayList<MsgSysBean.DataMapBean.ContentBean> sys = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);
        initView();
        settitlewhite();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.empty_goods_show, null);
        TextView tv = view.findViewById(R.id.tv_info);
        tv.setText("暂无消息");
        View view1 = View.inflate(context, R.layout.empty_goods_show, null);
        TextView tv1 = view1.findViewById(R.id.tv_info);
        tv1.setText("暂无公告");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("消息");
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mTvMsg.setOnClickListener(this);
        mTvSys = (TextView) findViewById(R.id.tv_sys);
        mTvSys.setOnClickListener(this);
        mRlvMsg = (RecyclerView) findViewById(R.id.rlv_msg);
        mRlvMsg.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MsgAdapter(R.layout.item_msg_info, msg);
        mRlvMsg.setAdapter(adapter);
        adapter.setEmptyView(view);
        mRlvSys = (RecyclerView) findViewById(R.id.rlv_sys);
        mRlvSys.setLayoutManager(new LinearLayoutManager(context));
        sysadapter = new MsgAdapter(R.layout.item_msg_info, sys);
        mRlvSys.setAdapter(sysadapter);
        sysadapter.setEmptyView(view1);
        dealposition(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_msg:
                dealposition(0);
                break;
            case R.id.tv_sys:
                dealposition(1);
                break;
        }
    }

    private void dealposition(int i) {
        if (i == 0) {
            position = 0;
            mTvMsg.setBackgroundResource(R.color.orange);
            mTvMsg.setTextColor(getcolor(R.color.white));
            mTvSys.setBackgroundResource(R.color.white);
            mTvSys.setTextColor(getcolor(R.color.text_black_3));
            mRlvMsg.setVisibility(View.VISIBLE);
            mRlvSys.setVisibility(View.GONE);
            if (msg.size() == 0) {
                getlist();
            }
        } else {
            position = 1;
            mTvMsg.setBackgroundResource(R.color.white);
            mTvMsg.setTextColor(getcolor(R.color.text_black_3));
            mTvSys.setBackgroundResource(R.color.orange);
            mTvSys.setTextColor(getcolor(R.color.white));
            mRlvMsg.setVisibility(View.GONE);
            mRlvSys.setVisibility(View.VISIBLE);
            if (sys.size() == 0) {
                getlist();
            }
        }
    }

    private class MsgAdapter extends BaseQuickAdapter<MsgSysBean.DataMapBean.ContentBean, BaseViewHolder> {
        public MsgAdapter(int layoutResId, @Nullable List<MsgSysBean.DataMapBean.ContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final MsgSysBean.DataMapBean.ContentBean item) {
            helper.setText(R.id.tv_title, item.title)
                    .setText(R.id.tv_content, item.msg)
                    .setText(R.id.tv_time, item.date);
            helper.getView(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delmsg(item.id);
                }
            });
        }
    }

    private void delmsg(String id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        HttpUtils.getNetData(HttpUtils.MSG_INFO_DELETE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    getlist();
                } else {
                    showToast("删除失败");
                }
            }
        });
    }

    private void getlist() {
        HttpParams params = new HttpParams();
        if (position == 0) {
            params.put("msgType", "single");
        } else {
            params.put("msgType", "all");
        }
        HttpUtils.getNetData(HttpUtils.MSG_INFO_LIST, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                MsgSysBean bean = JsonUtil.parseObject(data, MsgSysBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (position == 0) {
                        adapter.setNewData(bean.dataMap.content);
                    } else {
                        sysadapter.setNewData(bean.dataMap.content);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
