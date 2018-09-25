package com.jiefutong.emall.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.VideoListAdapter;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.VideoCommentBean;
import com.jiefutong.emall.bean.VideoListBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvConten;
    private ArrayList<VideoListBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private TextView mTvCommentNum;
    private ImageView mIvCancle;
    private RecyclerView mRlvComment;
    private EditText mEtComment;
    private ImageView mIvSend;
    private FrameLayout mBottomView;
    private BottomSheetBehavior behavior;
    private ArrayList<VideoCommentBean.DataMapBean.ContentBean> comments = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private String id;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        initView();
        settitlewhite();
        getNetdata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("视频集锦");
        mRlvConten = (RecyclerView) findViewById(R.id.rlv_conten);
        mRlvConten.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoListAdapter(R.layout.item_list_video_info, datas, this);
        mRlvConten.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_like:
                    case R.id.tv_like:
                        id = datas.get(position).id;
                        likeclick();
                        break;
                    case R.id.iv_comment:
                    case R.id.tv_comment:
                        comments.clear();
                        page = 1;
                        commentAdapter.setEnableLoadMore(true);
                        id = datas.get(position).id;
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        getcommentdata();
                        break;

                }
            }
        });
        mTvCommentNum = (TextView) findViewById(R.id.tv_comment_num);
        mIvCancle = (ImageView) findViewById(R.id.iv_cancle);
        mIvCancle.setOnClickListener(this);
        mRlvComment = (RecyclerView) findViewById(R.id.rlv_comment);
        mRlvComment.setLayoutManager(new LinearLayoutManager(context));
        mEtComment = (EditText) findViewById(R.id.et_comment);
        mIvSend = (ImageView) findViewById(R.id.iv_send);
        mIvSend.setOnClickListener(this);
        mBottomView = (FrameLayout) findViewById(R.id.bottom_view);
        mBottomView.setOnClickListener(this);
        behavior = BottomSheetBehavior.from(mBottomView);
        behavior.setHideable(true);
        View view = View.inflate(context, R.layout.empty_goods_show, null);
        TextView textView = view.findViewById(R.id.tv_info);
        textView.setText("暂无评论");
        commentAdapter = new CommentAdapter(R.layout.item_comment_video, comments);
        mRlvComment.setAdapter(commentAdapter);
        commentAdapter.setEmptyView(view);
        commentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getcommentdata();
            }
        }, mRlvComment);
    }

    private void getcommentdata() {
        HttpParams params = new HttpParams();
        params.put("videoId", id);
        params.put("page", String.valueOf(page));
        HttpUtils.getNetData(HttpUtils.VIDEO_COMMENT_LIST, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                commentAdapter.loadMoreComplete();
                VideoCommentBean bean = JsonUtil.parseObject(data, VideoCommentBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    comments.addAll(bean.dataMap.content);
                    commentAdapter.notifyDataSetChanged();
                    mTvCommentNum.setText(bean.dataMap.totalElements + "条评论");
                    if (bean.dataMap.content.size() < 10) {
                        commentAdapter.loadMoreEnd();
                    }
                    page++;
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                commentAdapter.loadMoreFail();
            }
        });
    }

    private void likeclick() {
        HttpParams params = new HttpParams();
        params.put("videoId", id);
        HttpUtils.getNetData(HttpUtils.VIDEO_LIKE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        getNetdata();
                    }
                    showToast(bean.message);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cancle:
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.iv_send:
                submitcomment();
                break;
        }
    }

    private void submitcomment() {
        String comment = mEtComment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            showToast("请输入要评论的内容");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("comment", comment);
        params.put("videoId", id);
        HttpUtils.getNetData(HttpUtils.VIDEO_COMMENT_SEND, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        page = 1;
                        comments.clear();
                        getcommentdata();
                        getNetdata();
                        mEtComment.setText("");
                        hideInput(context, mEtComment);
                    }
                    showToast(bean.message);
                }
            }
        });
    }

    private void getNetdata() {
        HttpUtils.getNetData(HttpUtils.VIDEO_LIST_INFO, this, new MyStringCallBack(this) {
            @Override
            protected void dealdata(String data) {
                VideoListBean bean = JsonUtil.parseObject(data, VideoListBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        datas.clear();
                        adapter.addData(bean.dataMap.content);
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showDataError();
                }
            }
        });
    }

    private class CommentAdapter extends BaseQuickAdapter<VideoCommentBean.DataMapBean.ContentBean, BaseViewHolder> {

        public CommentAdapter(int layoutResId, @Nullable List<VideoCommentBean.DataMapBean.ContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, VideoCommentBean.DataMapBean.ContentBean item) {
            GlideUtils.loadCirclepic(context, (ImageView) helper.getView(R.id.iv_header), item.headImgUrl);
            helper.setText(R.id.tv_name, item.realName)
                    .setText(R.id.tv_content, item.comment)
                    .setText(R.id.tv_time, item.createDate);
        }
    }

    /**
     * 强制隐藏输入法键盘
     *
     * @param context Context
     * @param view    EditText
     */
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
