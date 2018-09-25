package com.jiefutong.emall.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

public class ResourceUtils {
    public static int getColor(Context ctx, int resId) {
        return ContextCompat.getColor(ctx, resId);
    }

    @NonNull
    public static String getString(Context ctx, int resId) {
        return ctx.getString(resId);
    }

    @NonNull
    public static float getDimen(Context ctx, int resId) {
        return ctx.getResources().getDimension(resId);
    }


}
