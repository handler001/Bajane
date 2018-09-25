package com.jiefutong.emall.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.FightBuyAdapter;
import com.jiefutong.emall.bean.FightBuyDetailBean;
import com.jiefutong.emall.utils.FileUtil;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.QRCodeUtil;
import com.jiefutong.emall.utils.ResourceUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.io.File;
import java.util.List;

import rx.functions.Action1;

public class GoodsPddDetailActivity extends BaseActivity implements View.OnClickListener {
    private String id, coupon_id;
    public TextView tv_goods_name;
    public TextView tv_price;
    public TextView tv_price_old;
    private AppBarLayout mLayout;
    private Toolbar toolbar;
    public RecyclerView rlv_goods;
    private TextView tv_share;
    private TextView tv_copy;
    private TextView tv_ticket;
    private ImageView iv_back, iv_code, iv_home;
    private View header, ticketHeader, footview;
    private TextView tv_gold_num;
    private TextView tv_ticket_price;
    private TextView tv_ticket_time;
    private TextView tv_language;
    private TextView tv_discount;
    private TextView tv_rmb;
    private TextView tv_detail_share;
    private String command;
    private TextView tv_goods_info, tv_goods_detail, tv_goods_param;
    private TextView tv_ticket_coupon;
    private FightBuyAdapter adapter;
    private GridLayoutManager manager;
    private FightBuyDetailBean.DataMapBean dataBean;
    private File tmpFile;
    private boolean expends;
    private RelativeLayout rl_ticket;
    private LinearLayout ll_content;
    private BannerViewPager mLoopViewpager;
    private ZoomIndicator mBottomScaleLayout;
    private boolean detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_pdd_detail);
        settitlewhite();
        id = getIntent().getStringExtra("id");
        initView();
        initListener();
        initdata();
    }

    protected void initView() {
        header = View.inflate(this, R.layout.item_header_mall_language, null);
        footview = View.inflate(this, R.layout.foot_mall_detail_info, null);
        ticketHeader = View.inflate(this, R.layout.item_header_mall_detail, null);
        rl_ticket = ticketHeader.findViewById(R.id.rl_ticket);
        rl_ticket.setOnClickListener(this);
        ll_content = ticketHeader.findViewById(R.id.ll_content);
        tv_language = ticketHeader.findViewById(R.id.tv_language);
        tv_gold_num = ticketHeader.findViewById(R.id.tv_gold_num);
        tv_ticket_price = ticketHeader.findViewById(R.id.tv_ticket_price);
        tv_ticket_time = ticketHeader.findViewById(R.id.tv_ticket_time);
        tv_ticket_coupon = findViewById(R.id.tv_ticket_coupon);
        tv_discount = findViewById(R.id.tv_discount);
        tv_rmb = findViewById(R.id.tv_rmb);
        iv_code = findViewById(R.id.iv_code);
        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        rlv_goods = findViewById(R.id.rlv_goods);
        tv_goods_name = findViewById(R.id.tv_goods_name);
        tv_price = findViewById(R.id.tv_price);
        tv_price_old = findViewById(R.id.tv_price_old);
        mLayout = findViewById(R.id.app_layout);
        toolbar = findViewById(R.id.toolbar);
        tv_share = findViewById(R.id.tv_bottom_share);
        tv_copy = findViewById(R.id.tv_copy);
        tv_ticket = findViewById(R.id.tv_ticket);
        tv_detail_share = findViewById(R.id.tv_detail_share);
        tv_goods_info = findViewById(R.id.tv_goods_info);
        tv_goods_detail = findViewById(R.id.tv_goods_detail);
        tv_goods_param = findViewById(R.id.tv_goods_param);
        manager = new GridLayoutManager(this, 2);
        rlv_goods.setLayoutManager(manager);
        mBottomScaleLayout = findViewById(R.id.bottom_scale_layout);
        mLoopViewpager = findViewById(R.id.loop_viewpager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_home:
                openActivity(new Intent(this, MainActivity.class).putExtra("position",0));
                break;
            case R.id.iv_code:
                openpop();
                break;
            case R.id.tv_bottom_share:
//                startActivity(new Intent(this, ShareGoodsDetailActivity.class)
//                        .putExtra("id", id)
//                        .putExtra("tpwd", dataBean.tpwd)
//                        .putStringArrayListExtra("datas", dataBean.goods_gallery_urls));
                break;
            case R.id.tv_copy:
                showPasswordpop();
                break;
            case R.id.tv_ticket:
            case R.id.rl_ticket:
                startActivity(new Intent(this, WebViewActivity.class)
                        .putExtra("url", dataBean.item_url));
                break;
            case R.id.tv_goods_info:
                detail = false;
                mLayout.setExpanded(true);
                rlv_goods.smoothScrollToPosition(0);
                dealposition(0);
                break;
            case R.id.tv_goods_detail:
                detail = true;
                mLayout.setExpanded(false);
                rlv_goods.smoothScrollToPosition(0);
                dealposition(1);
                break;
            case R.id.tv_goods_param:
                detail = false;
                mLayout.setExpanded(false);
                rlv_goods.smoothScrollToPosition(1);
                dealposition(2);
                break;

        }
    }

    private void initdata() {
        HttpParams params = new HttpParams();
        params.put("goods_id_list", id);
        HttpUtils.getNetData(HttpUtils.LIST_PDD_DETAIL, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                FightBuyDetailBean fightBuyDetailBean = JsonUtil.parseObject(data, FightBuyDetailBean.class);
                if (fightBuyDetailBean != null && fightBuyDetailBean.code == HttpUtils.SUCCESS) {
                    dealdatas(fightBuyDetailBean.dataMap);
                }
            }
        });
    }

    private void initbanner(final List<String> dataMap) {
        PageBean bean = new PageBean.Builder<String>()
                .setDataObjects(dataMap)
                .setIndicator(mBottomScaleLayout)
                .builder();
        mLoopViewpager.setPageTransformer(false, new MzTransformer());
        mLoopViewpager.setPageListener(bean, R.layout.item_banner_pic, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                ImageView mpic = view.findViewById(R.id.iv_pic);
                GlideUtils.loadpic(context, mpic, (String) o);
            }
        });
    }

    private void dealdatas(final FightBuyDetailBean.DataMapBean info) {
        dataBean = info;
        coupon_id = info.goods_id;
        initbanner(info.goods_gallery_urls);
        tv_goods_name.setText(info.goods_name);
        if (info.has_coupon) {
            tv_ticket_coupon.setText("券后价");
            rl_ticket.setVisibility(View.VISIBLE);
            tv_ticket.setText("领券购买");
            tv_ticket_price.setText(info.coupon_price);
            tv_ticket_time.setText(info.coupon_start_time + "-" + info.coupon_end_time);
        } else {
            tv_ticket.setText("立即购买");
            tv_ticket_coupon.setText("折后价");
            rl_ticket.setVisibility(View.GONE);
        }
        tv_price.setText("¥ " + info.price);
        tv_price_old.setText("¥ " + info.original_price);
        tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (TextUtils.isEmpty(info.goods_desc)) {
            ll_content.setVisibility(View.GONE);
        } else {
            ll_content.setVisibility(View.VISIBLE);
            tv_language.setText(info.goods_desc);
        }
        tv_gold_num.setText(info.anticipated_money + " 元");
        tv_discount.setText("+ " + info.anticipated_money + " 元");
        tv_rmb.setText(info.sold_quantity + " 人购买");
        tv_detail_share.setText("分享购买后可得" + info.anticipated_money + "元");
        adapter = new FightBuyAdapter(R.layout.item_fight_buy, info.recommends, this);
        rlv_goods.setAdapter(adapter);
        command = info.tpwd;
        adapter.addHeaderView(ticketHeader);
        adapter.addHeaderView(header);
        adapter.addFooterView(footview);
        rlv_goods.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!detail) {
                    if (!expends && isCover(ticketHeader) && !isCover(header)) {
                        dealposition(1);
                    } else if (!expends && !isCover(ticketHeader)) {
                        dealposition(2);
                    }
                }
            }
        });
        rlv_goods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detail = false;
                return false;
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(GoodsPddDetailActivity.this, GoodsPddDetailActivity.class).putExtra("id", info.recommends.get(position).goods_id));
            }
        });
    }

    protected void initListener() {
        iv_back.setOnClickListener(this);
        iv_code.setOnClickListener(this);
        iv_home.setOnClickListener(this);
//        iv_share.setOnClickListener(this);
        mLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) < mLayout.getTotalScrollRange()) {
                    toolbar.setVisibility(View.GONE);
                    tv_detail_share.setVisibility(View.GONE);
                    expends = true;
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                    tv_detail_share.setVisibility(View.VISIBLE);
                    expends = false;
                }
            }
        });
        tv_share.setOnClickListener(this);
        tv_ticket.setOnClickListener(this);
        tv_copy.setOnClickListener(this);
        tv_goods_info.setOnClickListener(this);
        tv_goods_detail.setOnClickListener(this);
        tv_goods_param.setOnClickListener(this);
    }

    protected boolean isCover(View view) {
        boolean cover = false;
        Rect rect = new Rect();
        cover = view.getGlobalVisibleRect(rect);
        if (cover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                return !cover;
            }
        }
        return true;
    }

    private PopupWindow poppassword;

    private void showPasswordpop() {
        if (poppassword == null) {
            copypassword();
            View view = View.inflate(this, R.layout.pop_center_mall_password, null);
            TextView title = view.findViewById(R.id.tv_title);
            title.setText("券口令已复制");
            poppassword = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    poppassword.dismiss();
                }
            });
            view.findViewById(R.id.ll_pop_password).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            TextView tvcontent = view.findViewById(R.id.tv_content);
            tvcontent.setText(command);
            view.findViewById(R.id.inCome_wxLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharepassword(SHARE_MEDIA.WEIXIN);
                }
            });
            view.findViewById(R.id.inCome_wxFriendLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharepassword(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
            });
            view.findViewById(R.id.inCome_qqLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareToQQ(command);
                }
            });
            view.findViewById(R.id.inCome_qqZoneLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharepassword(SHARE_MEDIA.QZONE);
                }
            });

            poppassword.showAtLocation(View.inflate(this, R.layout.activity_goods_detail, null), Gravity.CENTER, 0, 0);
        } else {
            poppassword.showAtLocation(View.inflate(this, R.layout.activity_goods_detail, null), Gravity.CENTER, 0, 0);
        }
    }

    private void copypassword() {
        if (!TextUtils.isEmpty(command)) {
            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText(null, command));
        } else {
            showToast("复制失败,请重试");
        }
    }

    private void clearpassword() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    private void dealposition(int i) {
        if (i == 0) {
            tv_goods_info.setTextColor(ResourceUtils.getColor(this, R.color.title_bg_red));
            tv_goods_detail.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
            tv_goods_param.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
        } else if (i == 1) {
            tv_goods_info.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
            tv_goods_detail.setTextColor(ResourceUtils.getColor(this, R.color.title_bg_red));
            tv_goods_param.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
        } else {
            tv_goods_info.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
            tv_goods_detail.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
            tv_goods_param.setTextColor(ResourceUtils.getColor(this, R.color.title_bg_red));
        }
    }

    private PopupWindow pop;

    //相机权限
    private void openpop() {
        RxPermissions.getInstance(this) // this = a Context
                .request(
//                        (读写ＳＤ卡权限)
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            showpop();
                            // PhotoUtils.photograph(UsermsgActivity.this);
                        } else {
                            // 最少有一个权限被拒绝
                            showToast("权限被拒绝，请重新选择");
                        }
                    }
                });
    }

    private void showpop() {
        if (pop == null) {
            final View view = View.inflate(this, R.layout.pop_goods_detail_code, null);
            pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.dismiss();
                }
            });
            final View rlpop = view.findViewById(R.id.rl_pop);
            rlpop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ImageView iv_goods = view.findViewById(R.id.iv_goods);
            ImageView iv_code = view.findViewById(R.id.iv_code);
            TextView tvcontent = view.findViewById(R.id.tv_content);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tv_price_old = view.findViewById(R.id.tv_price_old);
            TextView tvquan = view.findViewById(R.id.quanprice);
            TextView tv_quan = view.findViewById(R.id.tv_quan);
            if (dataBean != null) {
//                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.img_baoyou);
//                ImageSpan imgSpan = new ImageSpan(this,b);
//                SpannableString spanString = new SpannableString("icon");
//                spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                tvcontent.setText(spanString);
                if (dataBean.has_coupon) {
                    tv_quan.setText("券后价");
                } else {
                    tv_quan.setText("折后价");
                }
                tvcontent.setText(dataBean.goods_name);
                GlideUtils.loadpic(GoodsPddDetailActivity.this, iv_goods, dataBean.goods_thumbnail_url);
                tv_price.setText("现价：¥ " + dataBean.original_price);
                tvquan.setText("¥ " + dataBean.coupon_price);
                tv_price_old.setText("¥ " + dataBean.price);
                tmpFile = FileUtil.createFile("/jft/images/", System.currentTimeMillis() + ".jpg");
                QRCodeUtil.createQRImage(dataBean.share_url, 240, 240, null, tmpFile.getAbsolutePath());
                GlideUtils.loadpic(GoodsPddDetailActivity.this, iv_code, tmpFile.getAbsolutePath());
            }
            view.findViewById(R.id.tv_pic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareurl(rlpop);
                    pop.dismiss();
                }
            });
            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels;     // 屏幕宽度（像素）
            int height = metric.heightPixels;   // 屏幕高度（像素）
            layoutView(rlpop, width, height);
            pop.showAtLocation(View.inflate(this, R.layout.activity_goods_detail, null), Gravity.CENTER, 0, 0);
        } else {
            pop.showAtLocation(View.inflate(this, R.layout.activity_goods_detail, null), Gravity.CENTER, 0, 0);
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pop != null && pop.isShowing()) {
                pop.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    //分享至qq
    public void shareToQQ(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.tencent.mobileqq");
        sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        try {
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("未安装QQ");
        }
    }

    public void shareurl(View view) {
        Bitmap bitmap = loadBitmapFromView(view);
        UMImage image = new UMImage(GoodsPddDetailActivity.this, bitmap);
        image.setThumb(image);
        new ShareAction((Activity) this)
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .open();
    }

    public void sharepassword(SHARE_MEDIA media) {
        new ShareAction(GoodsPddDetailActivity.this)
                .setPlatform(media)//传入平台
                .withText(command)
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            clearpassword();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t.getMessage().contains("2008")) {
                showToast("没有安装应用");
            }
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showToast("取消");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
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
}
