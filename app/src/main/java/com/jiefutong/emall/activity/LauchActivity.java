package com.jiefutong.emall.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.UserInfoBean;
import com.jiefutong.emall.utils.Constant;
import com.jiefutong.emall.utils.PreferencesUtils;

import java.lang.ref.WeakReference;

import io.realm.Realm;

public class LauchActivity extends BaseActivity {

    private ImageView mIvWelcome;
    private UIHandler handler;
    private String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_lauch);
        cookie = PreferencesUtils.getString(context, Constant.KEY_COOKIE, "");
        initView();
        showWindow();
    }

    private void initView() {
        mIvWelcome = (ImageView) findViewById(R.id.iv_welcome);
    }

    private void showWindow() {
        showDeleayNext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIvWelcome != null) {
            mIvWelcome.setBackgroundResource(0);
        }
    }

    private void showDeleayNext() {
        handler = new UIHandler(this) {
        };
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO 自动生成的方法存根
                showNext();
            }
        }, 1000);
    }

    private void showNext() {
        if (TextUtils.isEmpty(cookie)) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            openActivity(MainSelectActivity.class);
            finish();
        }
    }

    private static class UIHandler extends Handler {

        private WeakReference<Activity> mact;

        private UIHandler(Activity act) {
            super();
            // TODO Auto-generated constructor stub
            mact = new WeakReference<Activity>(act);
        }
    }
}
