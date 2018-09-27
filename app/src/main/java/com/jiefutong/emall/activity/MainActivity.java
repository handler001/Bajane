package com.jiefutong.emall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.VersionBean;
import com.jiefutong.emall.utils.BottomNavigationViewHelper;
import com.jiefutong.emall.utils.Constant;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.FragmentFactory;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PreferencesUtils;
import com.jiefutong.emall.utils.ResourceUtils;
import com.jiefutong.emall.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class MainActivity extends BaseActivity {
    int[][] states = new int[][]{
            new int[]{-android.R.attr.state_checked},
            new int[]{android.R.attr.state_checked}
    };
    private BottomNavigationView mBnvMenu;
    private Fragment mCurrentFrgment;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        position = getIntent().getIntExtra("position", 0);
        initOkgo();
        initView();
        checkversion();
        addmsgid();
    }

    private void addmsgid() {
        String id = JPushInterface.getRegistrationID(this);
        if (null != id && !TextUtils.isEmpty(id)) {
            HttpParams params = new HttpParams();
            params.put("registrationId", id);
            HttpUtils.getNetData(HttpUtils.MSG_ADD_ID, this, params, new MyStringCallBack(context) {
                @Override
                protected void dealdata(String data) {
                    CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                    if (bean == null || bean.code != HttpUtils.SUCCESS) {
                        showToast("获取推送用户失败");
                    }
                }
            });
        }
    }

    private void checkversion() {
        HttpParams params = new HttpParams();
        params.put("appName", getPackageName());
        HttpUtils.getNetData(HttpUtils.UPDATE_VERSION, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                final VersionBean bean = JsonUtil.parseObject(data, VersionBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    int code = getVersioncode();
                    if (code < bean.dataMap.appCode) {
                        new AlertDialog.Builder(context)
                                .setTitle("升级更新")
                                .setMessage(bean.dataMap.appDesc)
                                .setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        openActivity(new Intent(context, WebViewActivity.class).putExtra("url", bean.dataMap.appUrl));
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setCancelable(false)
                                .create()
                                .show();
                    }
                }
            }
        });
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public int getVersioncode() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void initView() {
        int[] colors = new int[]{ResourceUtils.getColor(this, R.color.tab_nor),
                ResourceUtils.getColor(this, R.color.tab_sel)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        mBnvMenu = (BottomNavigationView) findViewById(R.id.bnv_menu);
        mBnvMenu.setItemTextColor(csl);
        mBnvMenu.setItemIconTintList(csl);
        mBnvMenu.setOnNavigationItemSelectedListener(listener);
        if (position == 4) {
            settitleOrange();
        } else {
            settitlewhite();
        }
        mBnvMenu.getMenu().getItem(position).setChecked(true);
        dealposition(position);
        BottomNavigationViewHelper.disableShiftMode(mBnvMenu);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    settitlewhite();
                    mBnvMenu.getMenu().getItem(0).setChecked(true);
                    dealposition(0);
                    BottomNavigationViewHelper.disableShiftMode(mBnvMenu);
                    break;
                case R.id.act:
                    settitlewhite();
                    mBnvMenu.getMenu().getItem(1).setChecked(true);
                    dealposition(1);
                    BottomNavigationViewHelper.disableShiftMode(mBnvMenu);
                    break;
                case R.id.tpc:
                    settitleColor(R.color.tpc_bg);
                    mBnvMenu.getMenu().getItem(2).setChecked(true);
                    dealposition(2);
                    BottomNavigationViewHelper.disableShiftMode(mBnvMenu);
                    break;
                case R.id.message:
                    settitlewhite();
                    mBnvMenu.getMenu().getItem(3).setChecked(true);
                    BottomNavigationViewHelper.disableShiftMode(mBnvMenu);
                    dealposition(3);
                    break;
                case R.id.person:
                    settitleOrange();
                    mBnvMenu.getMenu().getItem(4).setChecked(true);
                    BottomNavigationViewHelper.disableShiftMode(mBnvMenu);
                    dealposition(4);
                    break;
            }
            return false;
        }
    };

    private void dealposition(int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏当前
        if (mCurrentFrgment != null) {
            transaction.hide(mCurrentFrgment);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(i));
        //为空添加
        if (fragment == null) {
            fragment = FragmentFactory.create(i);
        }
        //不为空显示
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_content, fragment, String.valueOf(i));
        }
        mCurrentFrgment = fragment;
        transaction.commit();
    }

    //退出时的时间
    private long mExitTime;
    //对返回键进行监听

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            exit();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showmessage(R.string.exit);
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    public void initOkgo() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.put("Cookie", PreferencesUtils.getString(context, Constant.KEY_COOKIE, ""));
        OkGo.getInstance().init(getApplication()).addCommonHeaders(headers).setOkHttpClient(client);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        FragmentFactory.clear();
    }
}
