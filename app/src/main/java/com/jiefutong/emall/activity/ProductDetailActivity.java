package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.GoodsBuyNowBean;
import com.jiefutong.emall.bean.GoodsInfoDetailsBean;
import com.jiefutong.emall.bean.GoodsInfoPrice;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.ResourceUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.util.List;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private BannerViewPager mLoopViewpager;
    private ZoomIndicator mBottomScaleLayout;
    private TextView mTvName;
    private TextView mTvPrice;
    // private TextView mTvPriceOld;
    private TextView mTvPostPrice;
    private TextView mTvPostFree;
    private TextView mTvSize;
    private TextView mTvAddCar;
    private TextView mTvBuyNow;
    private ImageView mIvHeader;
    private TextView mTvNumBh;
    private TextView mTvPriceBh;
    private ImageButton mIbCancle;
    private RecyclerView mRlvCategory;
    private TextView mTvAdd;
    private TextView mTvNumBuy;
    private TextView mTvRemove;
    private TextView mBtBuy;
    private FrameLayout mBottomView;
    private BottomSheetBehavior behavior;
    private int num = 1;
    private boolean show;
    private String id, idinfo;
    private WebView mWeb;
    private GoodsInfoDetailsBean.DataMapBean dataMapBean;
    private TextView mTvShowNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        id = getIntent().getStringExtra("id");
        initView();
        settitlewhite();
        loaddata();
    }

    private void loaddata() {
        HttpParams params = new HttpParams();
        params.put("id", id);
        HttpUtils.getNetData(HttpUtils.GOODS_DETAIL_INFO, this, params, new MyStringCallBack(this) {
            @Override
            protected void dealdata(String data) {
                GoodsInfoDetailsBean bean = JsonUtil.parseObject(data, GoodsInfoDetailsBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        initdata(bean.dataMap);
                        initlistener();
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showDataError();
                }
            }
        });
    }

    private void initlistener() {
        mTvAddCar.setOnClickListener(this);
        mTvBuyNow.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        mTvRemove.setOnClickListener(this);
        mBtBuy.setOnClickListener(this);
        mIbCancle.setOnClickListener(this);
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("商品详情");
        mLoopViewpager = (BannerViewPager) findViewById(R.id.loop_viewpager);
        mBottomScaleLayout = (ZoomIndicator) findViewById(R.id.bottom_scale_layout);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPrice = (TextView) findViewById(R.id.tv_prices);
        // mTvPriceOld = (TextView) findViewById(R.id.tv_price_old);
        mTvPostPrice = (TextView) findViewById(R.id.tv_post_price);
        mTvPostFree = (TextView) findViewById(R.id.tv_post_free);
        mTvSize = (TextView) findViewById(R.id.tv_size);
        mTvSize.setOnClickListener(this);
        mTvAddCar = (TextView) findViewById(R.id.tv_add_car);
        mTvBuyNow = (TextView) findViewById(R.id.tv_buy_now);
        mIvHeader = (ImageView) findViewById(R.id.iv_header);
        mTvNumBh = (TextView) findViewById(R.id.tv_num_bh);
        mTvPriceBh = (TextView) findViewById(R.id.tv_price_bh);
        mIbCancle = (ImageButton) findViewById(R.id.ib_cancle);
        mRlvCategory = (RecyclerView) findViewById(R.id.rlv_category);
        mRlvCategory.setLayoutManager(new LinearLayoutManager(this));
        mTvAdd = (TextView) findViewById(R.id.tv_add);
        mTvNumBuy = (TextView) findViewById(R.id.tv_num_buy);
        mTvRemove = (TextView) findViewById(R.id.tv_remove);
        mBtBuy = (TextView) findViewById(R.id.bt_buy);
        mTvShowNum = (TextView) findViewById(R.id.tv_show_num);
        mBottomView = (FrameLayout) findViewById(R.id.bottom_view);
        behavior = BottomSheetBehavior.from(mBottomView);
        behavior.setHideable(true);
        mWeb = (WebView) findViewById(R.id.web);
        WebSettings settings = mWeb.getSettings();
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
    }

    private void initdata(GoodsInfoDetailsBean.DataMapBean dataMap) {
        initbanner(dataMap.imgUrl);
        mTvName.setText(dataMap.goodsName);
        mTvPrice.setText("￥" + dataMap.goodPrice);
        mWeb.loadDataWithBaseURL(null, dataMap.goodsUrl, "text/html", "utf-8", null);
        dataMapBean = dataMap;
        if (dataMapBean.postage == 0) {
            mTvPostFree.setVisibility(View.VISIBLE);
            mTvPostPrice.setVisibility(View.GONE);
        } else {
            mTvPostFree.setVisibility(View.GONE);
            mTvPostPrice.setVisibility(View.VISIBLE);
            mTvPostPrice.setText("邮费:" + dataMap.postage + "元");
        }

        mRlvCategory.setAdapter(new CategoryAdapter(R.layout.item_goods_category_detail, dataMap.category));
        mTvShowNum.setText("库存剩余:" + dataMap.stockNum);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_cancle:
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_buy:
                dealcategory();
                break;
            case R.id.tv_add:
                num++;
                setnum(num);
                break;
            case R.id.tv_remove:
                if (num != 1) {
                    num--;
                    setnum(num);
                }
            case R.id.tv_add_car:
                if (dataMapBean.category == null || dataMapBean.category.size() == 0) {
                    addcar(String.valueOf(num), "");
                    return;
                }
                if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED && !show) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    addcar(String.valueOf(num), idinfo);
                }
                break;
            case R.id.tv_buy_now:
                if (dataMapBean.category == null || dataMapBean.category.size() == 0) {
                    buynow(String.valueOf(num), "");
                    return;
                }
                if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED && !show) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    buynow(String.valueOf(num), idinfo);
                }
                break;
            case R.id.tv_size:
                if (dataMapBean.category == null || dataMapBean.category.size() == 0) {
                    return;
                }
                if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
        }
    }

    private void dealcategory() {
        idinfo = "";
        String cate = "";
        for (int i = 0; i < dataMapBean.category.size(); i++) {
            if (TextUtils.isEmpty(map.get(i))) {
                showToast("请选择商品类型");
                return;
            } else {
                idinfo += ids.get(i) + "_";
                cate += map.get(i) + " ";
            }
        }
        idinfo = idinfo.substring(0, idinfo.length() - 1);
        show = true;
        mTvSize.setText(cate);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void setnum(int count) {
        mTvNumBuy.setText(String.valueOf(count));
    }

    private void initbanner(List<GoodsInfoDetailsBean.DataMapBean.ImgUrlBean> data) {
        PageBean bean = new PageBean.Builder<GoodsInfoDetailsBean.DataMapBean.ImgUrlBean>()
                .setDataObjects(data)
                .setIndicator(mBottomScaleLayout)
                .builder();
        mLoopViewpager.setPageTransformer(false, new MzTransformer());
        mLoopViewpager.setPageListener(bean, R.layout.item_banner_pic, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                GoodsInfoDetailsBean.DataMapBean.ImgUrlBean imgUrlBean = (GoodsInfoDetailsBean.DataMapBean.ImgUrlBean) o;
                ImageView mpic = view.findViewById(R.id.iv_pic);
                GlideUtils.loadpic(context, mpic, imgUrlBean.goodImg);
            }
        });
    }

    class CategoryAdapter extends BaseQuickAdapter<GoodsInfoDetailsBean.DataMapBean.CategoryBean, BaseViewHolder> {

        public CategoryAdapter(int layoutResId, @Nullable List<GoodsInfoDetailsBean.DataMapBean.CategoryBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsInfoDetailsBean.DataMapBean.CategoryBean item) {
            helper.setText(R.id.tv_cg, item.categoryName);
            final int pos = helper.getLayoutPosition();
            RecyclerView rlv = (RecyclerView) helper.getView(R.id.rlv_category);
            rlv.setLayoutManager(new GridLayoutManager(mContext, 5));
            CategoryDetailAdapter adapter = new CategoryDetailAdapter(R.layout.item_category, item.contentList, pos);
            rlv.setAdapter(adapter);
        }
    }

    private ArrayMap<Integer, String> map = new ArrayMap<>();
    private ArrayMap<Integer, String> ids = new ArrayMap<>();

    class CategoryDetailAdapter extends BaseQuickAdapter<GoodsInfoDetailsBean.DataMapBean.CategoryBean.ContentListBean, BaseViewHolder> {
        private int postion;

        public CategoryDetailAdapter(int layoutResId, @Nullable List<GoodsInfoDetailsBean.DataMapBean.CategoryBean.ContentListBean> data, int pos) {
            super(layoutResId, data);
            postion = pos;
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodsInfoDetailsBean.DataMapBean.CategoryBean.ContentListBean item) {
            helper.setText(R.id.tv_cate, item.spName);
            TextView tv = helper.getView(R.id.tv_cate);
            String id = ids.get(postion);
            if (id != null && !TextUtils.isEmpty(id) && id.equals(item.id)) {
                tv.setBackgroundResource(R.drawable.shape_red_bg_circle);
                tv.setTextColor(ResourceUtils.getColor(mContext, R.color.white));
            } else {
                tv.setBackgroundResource(R.drawable.shape_gray_bg);
                tv.setTextColor(ResourceUtils.getColor(mContext, R.color.text_shop_desc));
            }
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.put(postion, item.spName);
                    ids.put(postion, item.id);
                    notifyDataSetChanged();
                    dealinfo();
                }
            });
        }
    }

    private void dealinfo() {
        String infos = "";
        for (int i = 0; i < dataMapBean.category.size(); i++) {
            if (TextUtils.isEmpty(map.get(i))) {
                return;
            } else {
                infos += ids.get(i) + "_";
            }
        }
        HttpParams params = new HttpParams();
        params.put("goodsId", id);
        params.put("categoryType", infos.substring(0, infos.length() - 1));
        HttpUtils.getNetData(HttpUtils.GOODS_INFO_PRICE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                GoodsInfoPrice bean = JsonUtil.parseObject(data, GoodsInfoPrice.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (!TextUtils.isEmpty(bean.dataMap.goodsImg)) {
                        GlideUtils.loadpic(context, mIvHeader, bean.dataMap.goodsImg);
                    }
                    mTvNumBh.setText(bean.dataMap.stockNum);
                    mTvPriceBh.setText("¥" + bean.dataMap.goodsPrice);
                }
            }
        });
    }

    private void addcar(String num, String idinfo) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("goodsNum", String.valueOf(num));
        params.put("categoryType", idinfo);
        HttpUtils.getNetData(HttpUtils.GOODS_ADD_CAR, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    showToast("添加成功");
                } else {
                    showToast("添加失败");
                }
            }
        });
    }

    private void buynow(String num, String idinfo) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("number", String.valueOf(num));
        params.put("categoryType", idinfo);
        HttpUtils.getNetData(HttpUtils.GOODS_BUY_NOW, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                GoodsBuyNowBean bean = JsonUtil.parseObject(data, GoodsBuyNowBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    openActivity(new Intent(context, ProductBuyInfoActivity.class).putExtra("bean", bean.dataMap));
                } else {
                    showToast("购买失败");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
