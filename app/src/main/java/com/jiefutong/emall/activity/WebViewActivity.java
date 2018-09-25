package com.jiefutong.emall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.ActListBean;
import com.jiefutong.emall.utils.Constant;
import com.jiefutong.emall.utils.PreferencesUtils;
import com.jiefutong.emall.utils.ShareListener;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListenerAdapter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;


public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RelativeLayout mRlContent;
    private AgentWeb mAgentWeb;
    private String url;
    private int type;
    private TextView mTvSave;
    private TextView mTvProgress;
    private View view;
    private ImageView mIvShare;
    private ActListBean.DataMapBean dataMapBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        url = getIntent().getStringExtra("url");
        type = getIntent().getIntExtra("type", 0);
        dataMapBean = (ActListBean.DataMapBean) getIntent().getSerializableExtra("data");
        settitlewhite();
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mRlContent = (RelativeLayout) findViewById(R.id.rl_content);
        mTvSave = (TextView) findViewById(R.id.tv_save);
        view = findViewById(R.id.down);
        mTvProgress = (TextView) findViewById(R.id.tv_progress);
        if (type == 1) {
            mTvSave.setVisibility(View.VISIBLE);
            mTvSave.setText("参与记录");
            mTvSave.setOnClickListener(this);
        }
        if (dataMapBean != null) {
            url = dataMapBean.url;
            mIvShare = (ImageView) findViewById(R.id.iv_share);
            mIvShare.setVisibility(View.VISIBLE);
            mIvShare.setOnClickListener(this);
        }
        loadUrl();

    }

    public void loadUrl() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mRlContent, new RelativeLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setAgentWebWebSettings(getSettings())
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .additionalHttpHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .additionalHttpHeader("Cookie", PreferencesUtils.getString(context, Constant.KEY_COOKIE, ""))
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface());
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)) {
                mTvTitle.setText(title);
            }
        }
    };
    private DownloadListenerAdapter adapter = new DownloadListenerAdapter() {
        @Override
        public boolean onResult(String path, String url, Throwable throwable) {
            view.setVisibility(View.GONE);
            if (null == throwable) {
                //showToast("下载成功");
            } else {
                if (null == throwable) {
                    showToast("下载失败,请重试");
                } else {
                    showToast("下载成功,请点击安装");
                }
            }
            return false;
        }

        @Override
        public void onProgress(String url, long downloaded, long length, long usedTime) {
            super.onProgress(url, downloaded, length, usedTime);
            int mProgress = (int) ((downloaded) / Float.valueOf(length) * 100);
            view.setVisibility(View.VISIBLE);
            mTvProgress.setText(mProgress + " %");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                openActivity(ActHistoryJoinActivity.class);
                break;
            case R.id.iv_share:
                shareurl();
                break;
        }
    }

    private void shareurl() {
        UMWeb web = new UMWeb(url);
        web.setTitle(dataMapBean.title);
        web.setDescription(dataMapBean.content);
        web.setThumb(new UMImage(context, dataMapBean.imgUrl));
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                .withMedia(web)
                .setCallback(new ShareListener(context))//回调监听器
                .open();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class AndroidInterface {

        @JavascriptInterface
        public void leverUp(String id) {
            buynow(id);
        }

        @JavascriptInterface
        public void leverUp() {
            buynow("28");
        }

        @JavascriptInterface
        public void joinHistory() {
            openActivity(ActHistoryJoinActivity.class);
        }

    }

    private void buynow(String id) {
        if (dataMapBean != null) {
            openActivity(new Intent(context, ProductDetailActivity.class)
                    .putExtra("id", id)
                    .putExtra("actid", dataMapBean.id));
        } else {
            openActivity(new Intent(context, ProductDetailActivity.class).putExtra("id", id));
        }
    }

    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;

            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }

            /**
             * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
             * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
             * @param webView
             * @param downloadListener
             * @return WebListenerManager
             */
            @Override
            public WebListenerManager setDownloader(WebView webView, DownloadListener downloadListener) {
                return super.setDownloader(webView,
                        DefaultDownloadImpl
                                .create((Activity) webView.getContext(),
                                        webView,
                                        adapter,
                                        adapter,
                                        this.mAgentWeb.getPermissionInterceptor()));
            }
        };
    }
}
