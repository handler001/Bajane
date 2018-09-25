package com.jiefutong.emall.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiefutong.emall.R;


public class ToastUtil {

    private static Toast toast;
    private static TextView tv;
    private static Toast ctoast;

    /**
     * 静态toast
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text) {

        //1. 第一次调用2.自动消失的时候
        if (toast == null) {
            toast = new Toast(context);
            View view = View.inflate(context, R.layout.toast, null);
            tv = view.findViewById(R.id.tv);
            toast.setView(view);
            toast.setGravity(Gravity.BOTTOM, 0, 120);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        tv.setText(text);
        toast.show();
    }

    public static void show(Context context, int text) {
        //1. 第一次调用2.自动消失的时候
        if (toast == null) {
            toast = new Toast(context);
            View view = View.inflate(context, R.layout.toast, null);
            tv = view.findViewById(R.id.tv);
            toast.setView(view);
            toast.setGravity(Gravity.BOTTOM, 0, 150);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        tv.setText(ResourceUtils.getString(context, text));
        toast.show();
    }

    public static void showCenter(Context context, String text) {

        //1. 第一次调用2.自动消失的时候
        if (ctoast == null) {
            ctoast = new Toast(context);
            View view = View.inflate(context, R.layout.toast_center, null);
            tv = view.findViewById(R.id.tv);
            ctoast.setView(view);
            ctoast.setDuration(Toast.LENGTH_SHORT);
            ctoast.setGravity(Gravity.CENTER, 0, 0);
        }
        tv.setText(text);
        ctoast.show();
    }
}
