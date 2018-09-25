package com.jiefutong.emall.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.DistCenterBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.ShareListener;
import com.lzy.okgo.OkGo;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xuexiang.xqrcode.XQRCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DistributionSalesActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvHeader;
    private TextView mTvVip;
    private TextView mTvType;
    private TextView mTvWalletMoney;
    private LinearLayout mLlWallet;
    private LinearLayout mLlShare;
    private TextView mTvIncomeMoney;
    private LinearLayout mLlIncome;
    private TextView mTvFriend;
    private LinearLayout mLlFriend;
    private String shareurl;
    private View viewShare;
    //二维码
    private ImageView codeIV;
    private PopupWindow popupWindow;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution_sales);
        EventBus.getDefault().register(this);
        initView();
        settitlewhite();
        loaddata();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("分销中心");
        mIvHeader = (ImageView) findViewById(R.id.iv_header);
        mTvVip = (TextView) findViewById(R.id.tv_vip);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvWalletMoney = (TextView) findViewById(R.id.tv_wallet_money);
        mLlWallet = (LinearLayout) findViewById(R.id.ll_wallet);
        mLlWallet.setOnClickListener(this);
        mLlShare = (LinearLayout) findViewById(R.id.ll_share);
        mLlShare.setOnClickListener(this);
        mTvIncomeMoney = (TextView) findViewById(R.id.tv_income_money);
        mLlIncome = (LinearLayout) findViewById(R.id.ll_income);
        mLlIncome.setOnClickListener(this);
        mTvFriend = (TextView) findViewById(R.id.tv_friend);
        mLlFriend = (LinearLayout) findViewById(R.id.ll_friend);
        mLlFriend.setOnClickListener(this);
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.CENTER_FX, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                DistCenterBean bean = JsonUtil.parseObject(data, DistCenterBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    GlideUtils.loadCirclepic(context, mIvHeader, bean.dataMap.headImgUrl);
                    mTvVip.setText(bean.dataMap.viewUserType);
                    mTvType.setText("推荐人: " + bean.dataMap.realName);
                    shareurl = bean.dataMap.linkUrl;
                    flag = bean.dataMap.imgFlag;
                    mTvWalletMoney.setText(bean.dataMap.wallet + "元");
                    mTvIncomeMoney.setText(bean.dataMap.allMoney + "元");
                    mTvFriend.setText(bean.dataMap.friendsNum + "人");
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
                    showToast("数据获取失败");
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
            case R.id.ll_wallet:
                openActivity(WalletActivity.class);
                break;
            case R.id.ll_share:
                if (!TextUtils.isEmpty(shareurl)) {
                    new PermissionUtil(context, PermissionUtil.PHOTO) {
                        @Override
                        public void onsuccess() {
                            showPop();
                        }
                    };
                }
                break;
            case R.id.ll_income:
                openActivity(MyIncomeListActivity.class);
                break;
            case R.id.ll_friend:
                openActivity(FriendInfoActivity.class);
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
            Bitmap bitmap = XQRCode.createQRCodeWithLogo(shareurl, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_launcher));
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
            popupWindow.showAtLocation(mLlShare, Gravity.CENTER, 0, 0);

        } else {
            popupWindow.showAtLocation(mLlShare, Gravity.CENTER, 0, 0);
        }
    }

    private void creadcode() {
        Bitmap bitmap = XQRCode.createQRCodeWithLogo(shareurl, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_launcher));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean event) {
        if (event.cod.equals("gold") || event.cod.equals("ticket") || event.cod.equals("money")) {
            loaddata();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelTag(this);
    }
}
