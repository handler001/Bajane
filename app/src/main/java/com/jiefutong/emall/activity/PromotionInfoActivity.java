package com.jiefutong.emall.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.ProInfoBean;
import com.jiefutong.emall.bean.VersionBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.ShareListener;
import com.lzy.okgo.model.HttpParams;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xuexiang.xqrcode.XQRCode;

public class PromotionInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvTgsj;
    private ImageView mIvTgrw;
    private LinearLayout mLlCode;
    private LinearLayout mLlPage;
    private LinearLayout mLlDown;
    private LinearLayout mLlCenter;
    private String url;
    private View viewShare;
    //二维码
    private ImageView codeIV;
    private String downurl;
    private int downcode;
    private PopupWindow popupWindow;
    private int flag;
    private int flags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_info);
        initView();
        settitlewhite();
        loaddata();
    }

    private void initView() {
        flags = getIntent().getIntExtra("flag", 0);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("分享推广");
        mIvTgsj = (ImageView) findViewById(R.id.iv_tgsj);
        mIvTgsj.setOnClickListener(this);
        mIvTgrw = (ImageView) findViewById(R.id.iv_tgrw);
        mIvTgrw.setOnClickListener(this);
        mLlCode = (LinearLayout) findViewById(R.id.ll_code);
        mLlCode.setOnClickListener(this);
        mLlPage = (LinearLayout) findViewById(R.id.ll_page);
        mLlPage.setOnClickListener(this);
        mLlDown = (LinearLayout) findViewById(R.id.ll_down);
        mLlDown.setOnClickListener(this);
        mLlCenter = (LinearLayout) findViewById(R.id.ll_center);
        mLlCenter.setOnClickListener(this);
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.PAGE_SHARE_LINK, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                ProInfoBean bean = JsonUtil.parseObject(data, ProInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    url = bean.dataMap.linkUrl;
                    flag = bean.dataMap.imgFlag;
                    DisplayMetrics metric = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metric);
                    int width = metric.widthPixels;     // 屏幕宽度（像素）
                    int height = metric.heightPixels;   // 屏幕高度（像素）
//                    if (bean.dataMap.imgFlag == 2) {
//                        viewShare = LayoutInflater.from(context).inflate(R.layout.layout_share_pic_code, null, false);
//                    } else {
//                        viewShare = LayoutInflater.from(context).inflate(R.layout.layout_share_code, null, false);
//                    }
                    viewShare = LayoutInflater.from(context).inflate(R.layout.layout_share_code, null, false);
                    layoutView(viewShare, width, height);
                    codeIV = viewShare.findViewById(R.id.iv_code);
                } else {
                    showToast("信息获取失败");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_tgsj:
//                if (flags == 1) {
//                    openActivity(new Intent(context, WebViewActivity.class).putExtra("url", HttpUtils.MEMBER_PAGE));
//                } else {
//                    openActivity(new Intent(context, WebViewActivity.class).putExtra("url", HttpUtils.MEMBER_PAGE_new));
//                }
                showToast("开发中");
                break;
            case R.id.ll_code:
                if (!TextUtils.isEmpty(url)) {
                    new PermissionUtil(context, PermissionUtil.PHOTO) {
                        @Override
                        public void onsuccess() {
                            showPop();
                        }
                    };
                }
                break;
            case R.id.ll_page:
                if (!TextUtils.isEmpty(url)) {
                    sharepage();
                }
                break;
            case R.id.ll_down:
//                if (!TextUtils.isEmpty(url)) {
////                    openActivity(new Intent(context, WebViewActivity.class).putExtra("url", url));
////                }
                if (!TextUtils.isEmpty(downurl) && downcode != 0) {
                    if (getVersioncode() < downcode) {
                        openActivity(new Intent(context, WebViewActivity.class).putExtra("url", downurl));
                    } else {
                        showToast("已是最新版本");
                    }
                } else {
                    checkversion();
                }
                break;
            case R.id.ll_center:
                openActivity(CentralCopyActivity.class);
                break;
        }
    }

    private void showPop() {
        if (popupWindow == null) {
            View view = View.inflate(context, R.layout.share_pic_code, null);
//            if (flag == 2) {
//                view = View.inflate(context, R.layout.share_pic_code_new, null);
//            } else {
//                view = View.inflate(context, R.layout.share_pic_code, null);
//            }
            popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            Bitmap bitmap = XQRCode.createQRCodeWithLogo(url, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_new));
            ((ImageView) view.findViewById(R.id.iv_code)).setImageBitmap(bitmap);
            view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    creadcode();
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAtLocation(mLlCenter, Gravity.CENTER, 0, 0);

        } else {
            popupWindow.showAtLocation(mLlCenter, Gravity.CENTER, 0, 0);
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
                    downcode = bean.dataMap.appCode;
                    downurl = bean.dataMap.appUrl;
                    if (code < bean.dataMap.appCode) {
                        openActivity(new Intent(context, WebViewActivity.class).putExtra("url", bean.dataMap.appUrl));
                    } else {
                        showToast("已是最新版本");
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

    private void creadcode() {
        Bitmap bitmap = XQRCode.createQRCodeWithLogo(url, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_new));
        codeIV.setImageBitmap(bitmap);
        Bitmap sharebitmap = loadBitmapFromView(viewShare);
        UMImage image = new UMImage(context, sharebitmap);
        image.setTitle("e购商城");
        image.setTitle("购物就选e购商城");
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                .withMedia(image)
                .setCallback(new ShareListener(this))//回调监听器
                .open();
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }


    private void layoutView(View v, int width, int height) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    private void sharepage() {
        UMWeb web = new UMWeb(url);
        web.setTitle("e购商城");
        web.setDescription("购物就选e购商城");
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                .withMedia(web)
                .setCallback(new ShareListener(this))//回调监听器
                .open();
    }
}
