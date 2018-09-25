package com.jiefutong.emall.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.MessageDetailActivity;
import com.jiefutong.emall.activity.MessageDetailOtherActivity;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.MessageListBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author l
 * @Date 2018/8/29
 */
public class MessageFragment extends BaseFragment {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvMessage;
    private BaseQuickAdapter adapter;
    private ArrayList<MessageListBean.DataMapBean> datas = new ArrayList<>();

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        HttpUtils.getFraNetData(HttpUtils.MSG_TYPE_LIST, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                MessageListBean bean = JsonUtil.parseObject(data, MessageListBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.setNewData(bean.dataMap);
                }
            }
        });
    }

    @Override
    protected void intiview(View view) {
        EventBus.getDefault().register(this);
        View empty = View.inflate(mctx, R.layout.empty_goods_show, null);
        TextView tv = empty.findViewById(R.id.tv_info);
        tv.setText("暂无消息");
        mIvBack = view.findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.GONE);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText("消息中心");
        mRlvMessage = view.findViewById(R.id.rlv_message);
        mRlvMessage.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new MessageAdapter(R.layout.item_frag_message, datas);
        mRlvMessage.setAdapter(adapter);
        adapter.setEmptyView(empty);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_message_list;
    }

    private class MessageAdapter extends BaseQuickAdapter<MessageListBean.DataMapBean, BaseViewHolder> {
        public MessageAdapter(int layoutResId, @Nullable List<MessageListBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final MessageListBean.DataMapBean item) {
            GlideUtils.loadCirclepic(mctx, (ImageView) helper.getView(R.id.iv_pic), item.icon);
            helper.setText(R.id.tv_name, item.title).setText(R.id.tv_content, item.desc).setText(R.id.tv_time, item.createDate);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.msgType.equals("notice") || item.msgType.equals("activity")) {
                        openActivity(new Intent(mctx, MessageDetailActivity.class)
                                .putExtra("name", item.title)
                                .putExtra("type", item.msgType));
                    } else {
                        openActivity(new Intent(mctx, MessageDetailOtherActivity.class)
                                .putExtra("name", item.title)
                                .putExtra("type", item.msgType));
                    }
                }
            });
            if (item.unReadNum == 0) {
                helper.getView(R.id.tv_dai_num).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.tv_dai_num).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_dai_num, String.valueOf(item.unReadNum));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean busBean) {
        if (busBean.cod.equals("message")) {
            loadData();
        }
    }
}
