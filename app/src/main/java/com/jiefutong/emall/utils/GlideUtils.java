package com.jiefutong.emall.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jiefutong.emall.R;


/**
 * @Author l
 * @Date 2018/4/17
 */
public class GlideUtils {
    public static void loadpic(Context ctx, ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url) && url != null) {
            Glide.with(ctx).load(url).error(R.mipmap.icon_default_bg).into(imageView);
        }
    }

    public static void loadpicId(Context ctx, ImageView imageView, int id) {
        Glide.with(ctx).load(id).error(R.mipmap.icon_default_bg).into(imageView);
    }

    public static void loadCirclepic(Context ctx, ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url) && url != null) {
            Glide.with(ctx).load(url).transform(new GlideCircleTransform(ctx)).error(R.mipmap.header_default).into(imageView);
        }
    }

    public static void loadCirclepicId(Context ctx, ImageView imageView, int id) {
        Glide.with(ctx).load(id).transform(new GlideCircleTransform(ctx)).error(R.mipmap.header_default).into(imageView);
    }

    public static void loadpropic(Context ctx, final ImageView imageView, String url, final LottieAnimationView lav) {
        Glide.with(ctx).load(url).error(R.mipmap.icon_default_bg).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                lav.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                lav.setVisibility(View.GONE);
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                imageView.setImageDrawable(resource);
                lav.setVisibility(View.GONE);
            }
        });
    }
}
