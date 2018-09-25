package com.jiefutong.emall.utils;

import android.content.Context;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @Author l
 * @Date 2018/7/24
 */
public class ShareListener implements UMShareListener {
    private Context ctx;

    public ShareListener(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        if (throwable.getMessage().contains("2008")) {
            ToastUtil.show(ctx, "没有安装应用");
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
