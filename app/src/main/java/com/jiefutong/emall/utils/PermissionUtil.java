package com.jiefutong.emall.utils;

import android.Manifest;
import android.content.Context;

import com.jiefutong.emall.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * @Author l
 * @Date 2018/7/11
 */
public abstract class PermissionUtil {
    //相册
    public static String[] PHOTO = {Manifest.permission.READ_EXTERNAL_STORAGE};
    //照相
    public static String[] CAMERA = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    //电话
    public static String[] PHONE = {Manifest.permission.CALL_PHONE};
    //位置
    public static String[] LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};
    private Context ctx;

    public PermissionUtil(Context ctx, String[] permission) {
        this.ctx = ctx;
        requestPermission(permission);
    }

    public void requestPermission(String[] permission) {
        RxPermissions.getInstance(ctx)
                .request(permission)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            onsuccess();
                        } else {
                            ToastUtil.show(ctx, R.string.permission_refuse);
                        }
                    }
                });
    }

    public abstract void onsuccess();
}
