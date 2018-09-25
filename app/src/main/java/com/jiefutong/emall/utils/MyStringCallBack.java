package com.jiefutong.emall.utils;

import android.content.Context;
import android.util.Log;

import com.jiefutong.emall.R;
import com.jiefutong.emall.widget.CircleProgres;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * @Author l
 * @Date 2018/7/10
 */
public abstract class MyStringCallBack extends StringCallback {
    private Context ctx;
    public CircleProgres progres;

    public MyStringCallBack(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onSuccess(Response<String> response) {
        dealdata(response.body());
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        Log.i("sss", "onError: "+response.getException().getMessage());
        ToastUtil.show(ctx, ResourceUtils.getString(ctx, R.string.net_error));
    }

    protected abstract void dealdata(String data);

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        showPb();
    }

    @Override
    public void onFinish() {
        super.onFinish();
        dismissPb();
    }

    public void showPb() {
        if (progres == null) {
            progres = new CircleProgres(ctx);
        } else {
            progres.show();
        }
    }

    public void dismissPb() {
        if (progres != null && progres.isShowing()) {
            progres.dismiss();
        }
    }
}
