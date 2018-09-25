package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.FriendInfoAdapter;
import com.jiefutong.emall.bean.FriendInfoBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

public class FriendInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<FriendInfoBean.DataMapBean> datas = new ArrayList<>();
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        settitlewhite();
        initView();
        loaddata();
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.MY_FRIENDS_INFO, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                FriendInfoBean bean = JsonUtil.parseObject(data, FriendInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.addData(bean.dataMap);
                    adapter.setEmptyView(view);
                } else {
                    showToast("获取好友信息失败");
                }
            }
        });
    }

    private void initView() {
        view = View.inflate(context, R.layout.empty_goods_show, null);
        TextView tv = view.findViewById(R.id.tv_info);
        tv.setText("暂无好友");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("我的好友");
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendInfoAdapter(R.layout.item_friend_info, datas, context);
        mRlvContent.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
