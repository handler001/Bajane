package com.jiefutong.emall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.BaseActivity;
import com.jiefutong.emall.activity.VideoPlayActivity;
import com.jiefutong.emall.bean.VideoListBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.NetWorkUtil;
import com.jiefutong.emall.utils.ResourceUtils;
import com.jiefutong.emall.utils.ShareListener;
import com.jiefutong.emall.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMVideo;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/13
 */
public class VideoListAdapter extends BaseQuickAdapter<VideoListBean.DataMapBean.ContentBean, BaseViewHolder> {
    private Context mcontext;

    public VideoListAdapter(int layoutResId, @Nullable List<VideoListBean.DataMapBean.ContentBean> data, Context ctx) {
        super(layoutResId, data);
        this.mcontext = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoListBean.DataMapBean.ContentBean item) {
        helper.getView(R.id.iv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkUtil.isWifiNet(mcontext)) {
                    Intent intent = new Intent(mcontext, VideoPlayActivity.class);
                    intent.putExtra("info", item);
                    mcontext.startActivity(intent);
                } else {
                    new DiaglogUtils(mcontext, ResourceUtils.getString(mContext, R.string.net_use)) {
                        @Override
                        public void sureMessage() {
                            Intent intent = new Intent(mcontext, VideoPlayActivity.class);
                            intent.putExtra("info", item);
                            mcontext.startActivity(intent);
                        }
                    };
                }

            }
        });
        helper.setText(R.id.tv_name, item.author)
                .setText(R.id.tv_time, item.createDate)
                .setText(R.id.tv_desc, item.title)
                .setText(R.id.tv_duration, item.videoLength)
                .setText(R.id.tv_like, item.zanCount)
                .setText(R.id.tv_comment, item.commentCount)
                .setText(R.id.tv_watch, item.zanNum);
        GlideUtils.loadCirclepic(mcontext, (ImageView) helper.getView(R.id.iv_header), item.authorHeader);
        GlideUtils.loadpic(mcontext, (ImageView) helper.getView(R.id.iv_video), item.vedioImg);
        helper.getView(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMVideo video = new UMVideo(item.vedioUrl);
                video.setTitle(item.title);//视频的标题
                new ShareAction((BaseActivity) mcontext)
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .withMedia(video)
                        .setCallback(new ShareListener(mContext))//回调监听器
                        .open();
            }
        });
        if (item.zanFlag == 1) {
            helper.getView(R.id.iv_like).setBackgroundResource(R.mipmap.img_goodjob_red);
        } else {
            helper.getView(R.id.iv_like).setBackgroundResource(R.mipmap.img_goodjob);
        }
        helper.addOnClickListener(R.id.tv_like)
                .addOnClickListener(R.id.iv_like)
                .addOnClickListener(R.id.tv_comment)
                .addOnClickListener(R.id.iv_comment);
    }
}
