package com.jiefutong.emall.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.UserBean;
import com.jiefutong.emall.utils.AppManager;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.FragmentFactory;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PreferencesUtils;

import io.realm.Realm;
import io.realm.RealmResults;

public class AppSettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvCode;
    private TextView mTvService;
    private TextView mTvSupport;
    private TextView mTvFeed;
    private RelativeLayout mRlExit;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);
        realm = Realm.getDefaultInstance();
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("设置");
        mTvCode = (TextView) findViewById(R.id.tv_code);
        mTvCode.setText(String.valueOf(getVersionName()));
        mTvService = (TextView) findViewById(R.id.tv_service);
        mTvService.setOnClickListener(this);
        mTvSupport = (TextView) findViewById(R.id.tv_support);
        mTvSupport.setOnClickListener(this);
        mTvFeed = (TextView) findViewById(R.id.tv_feed);
        mTvFeed.setOnClickListener(this);
        mRlExit = (RelativeLayout) findViewById(R.id.rl_exit);
        mRlExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_service:
                openActivity(new Intent(context,WebViewActivity.class).putExtra("url",HttpUtils.SERVICE_AGREEMENT));
                break;
            case R.id.tv_support:
               // openActivity(new Intent(context,WebViewActivity.class).putExtra("url",HttpUtils.TECHNOLOFY_SUPPORT));
                break;
            case R.id.tv_feed:
                openActivity(FeedBackActivity.class);
                break;
            case R.id.rl_exit:
                new DiaglogUtils(context, "是否要退出该应用") {
                    @Override
                    public void sureMessage() {
                        HttpUtils.getNetData(HttpUtils.LOGOUT, AppSettingActivity.this, new MyStringCallBack(context) {
                            @Override
                            protected void dealdata(String data) {
                                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                                    AppManager.getAppManager().finishAllActivity();
                                    FragmentFactory.clear();
                                    clearUser();
                                    PreferencesUtils.clearSP(context);
                                    openActivity(LoginActivity.class);
                                } else {
                                    showToast("退出失败,请重试");
                                }
                            }
                        });
                    }
                };
                break;
        }
    }

    //    清理数据
    private void clearUser() {
        final RealmResults<UserBean> dataBeen = realm.where(UserBean.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //删除个人信息
                dataBeen.deleteAllFromRealm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public String getVersionName() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
