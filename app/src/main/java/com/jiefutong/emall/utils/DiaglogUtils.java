package com.jiefutong.emall.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jiefutong.emall.R;

/**
 * @Author l
 * @Date 2018/7/11
 */
public abstract class DiaglogUtils {
    public DiaglogUtils(Context ctx, String message) {
        showdialog(ctx, message);
    }

    public void showdialog(Context ctx, String message) {
        new AlertDialog.Builder(ctx)
                .setTitle(ResourceUtils.getString(ctx, R.string.remind))
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        sureMessage();
                    }
                })
                .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    public abstract void sureMessage();
}
