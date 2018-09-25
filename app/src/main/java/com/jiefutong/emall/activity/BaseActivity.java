package com.jiefutong.emall.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.jiefutong.emall.R;
import com.jiefutong.emall.utils.AppManager;
import com.jiefutong.emall.utils.ResourceUtils;
import com.jiefutong.emall.utils.ToastUtil;
import com.jiefutong.emall.widget.CircleProgres;
import com.lzy.okgo.OkGo;
import com.umeng.socialize.UMShareAPI;

/**
 * @Author l
 * @Date 2018/7/9
 */
public class BaseActivity extends AppCompatActivity {
    public Context context;
    public CircleProgres progres;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        OkGo.getInstance().cancelTag(this);
    }

    //展示toast
    public void showToast(String msg) {
        ToastUtil.show(context, msg);
    }

    public void showToast(@IdRes int msg) {
        ToastUtil.show(context, ResourceUtils.getString(context, msg));
    }

    public void showPb() {
        if (progres == null) {
            progres = new CircleProgres(this);
        } else {
            progres.show();
        }
    }

    public void dismissPb() {
        if (progres != null && progres.isShowing()) {
            progres.dismiss();
        }
    }

    protected void openActivity(Class<?> pClass) {
        Intent intent = new Intent(this, pClass);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    //打开
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        //动画
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public void openActivity(Intent intent) {
        //动画
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public void openActivityForResult(Class<?> pClass, int code) {
        //动画
        Intent intent = new Intent(this, pClass);
        startActivityForResult(intent, code);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public void openActivityForResult(Intent intent, int code) {
        //动画
        startActivityForResult(intent, code);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public int getcolor(int id) {
        return ResourceUtils.getColor(this, id);
    }

    public void showDataError() {
        showToast(ResourceUtils.getString(this, R.string.data_error));
    }

    public void showNetError() {
        showToast(ResourceUtils.getString(this, R.string.net_error));
    }

    public void showmessage(int message) {
        showToast(ResourceUtils.getString(this, message));
    }

    public void settitlewhite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(this, ResourceUtils.getColor(this, R.color.bg_title_gray), 0);
        }
    }

    public void settitleOrange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(this, ResourceUtils.getColor(this, R.color.title_orange), 0);
        }
    }

    public void settitleRed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(this, ResourceUtils.getColor(this, R.color.title_bg_order), 0);
        }
    }

    public void settitleColor(@ColorRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(this, ResourceUtils.getColor(this, id), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
