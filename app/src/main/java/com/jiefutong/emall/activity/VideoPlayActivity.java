package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.VideoListBean;
import com.jiefutong.emall.utils.GlideUtils;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoPlayActivity extends BaseActivity {

    private JZVideoPlayerStandard mJzVideo;
    private VideoListBean.DataMapBean.ContentBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        bean = (VideoListBean.DataMapBean.ContentBean) getIntent().getSerializableExtra("info");
        initView();
    }

    private void initView() {
        mJzVideo = (JZVideoPlayerStandard) findViewById(R.id.jz_video);
        mJzVideo.setUp(bean.vedioUrl,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                bean.title);
        GlideUtils.loadpic(this, mJzVideo.thumbImageView, bean.vedioImg);
        mJzVideo.backButton.setVisibility(View.VISIBLE);
        mJzVideo.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        JZVideoPlayer.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
