package com.jiefutong.emall.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.BannerWebActivity;
import com.jiefutong.emall.activity.CompanyJoinActivity;
import com.jiefutong.emall.activity.GoodsCarManagerActivity;
import com.jiefutong.emall.activity.GoodsShopActivity;
import com.jiefutong.emall.activity.IncomeListActivity;
import com.jiefutong.emall.activity.MallGoodsListActivity;
import com.jiefutong.emall.activity.ProductDescActivity;
import com.jiefutong.emall.activity.ProductDetailActivity;
import com.jiefutong.emall.activity.PromotionInfoActivity;
import com.jiefutong.emall.activity.ShopOwnerShareActivity;
import com.jiefutong.emall.activity.VideoListActivity;
import com.jiefutong.emall.activity.WebViewActivity;
import com.jiefutong.emall.adapter.GoodShowAdapter;
import com.jiefutong.emall.bean.BannerBean;
import com.jiefutong.emall.bean.GoodsShowBean;
import com.jiefutong.emall.bean.HeaderInfoBean;
import com.jiefutong.emall.bean.UserDetailsBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author l
 * @Date 2018/7/10
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {
    // private ImageView mIvMessage;
    private TextView mTvTitle;
    private ImageView mIvCar;
    private TextView mTvNum;
    private RecyclerView mRlvContent;
    private View header;
    private BannerViewPager mLoopViewpager;
    private ZoomIndicator mBottomScaleLayout;
    private LinearLayout mLlGiftNew;
    private LinearLayout mLlGoodsShop;
    private LinearLayout mLlVideo;
    private LinearLayout mLlIntro;
    private LinearLayout mLlAgent;
    private LinearLayout mLlTuig;
    private LinearLayout mLlJoin;
    private LinearLayout mLlCaogen;
    private ImageView mIvFirst;
    private ImageView mIvTwo;
    private ImageView mIvThree;
    private ImageView mIvGoldArea;
    private ImageView mIvAgarea;
    private ImageView mIvNewArea;
    private ImageView mIvmore;
    private BaseQuickAdapter adapter;
    private RelativeLayout mllIncome;
    private ArrayList<GoodsShowBean.DataMapBean> datas = new ArrayList<>();
    private String type;
    private SwipeRefreshLayout refreshLayout;
    private View area;
    private int flag;

    @Override
    protected void listener() {
        //mIvMessage.setOnClickListener(this);
        mIvCar.setOnClickListener(this);
        mLlGiftNew.setOnClickListener(this);
        mLlGoodsShop.setOnClickListener(this);
        mLlVideo.setOnClickListener(this);
        mLlIntro.setOnClickListener(this);
        mLlAgent.setOnClickListener(this);
        mLlTuig.setOnClickListener(this);
        mLlJoin.setOnClickListener(this);
        mLlCaogen.setOnClickListener(this);
        mllIncome.setOnClickListener(this);
        mIvGoldArea.setOnClickListener(this);
        mIvAgarea.setOnClickListener(this);
        mIvNewArea.setOnClickListener(this);
        mIvmore.setOnClickListener(this);
        mRlvContent.setLayoutManager(new GridLayoutManager(mctx, 2));
        adapter = new GoodShowAdapter(R.layout.item_frag_goods, datas, mctx);
        mRlvContent.setAdapter(adapter);
        adapter.addHeaderView(header);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, mRlvContent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpParams params = new HttpParams();
                params.put("page", String.valueOf(1));
                HttpUtils.getFraNetData(HttpUtils.GOODS_LIST_INFO, HomePageFragment.this, params, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        refreshLayout.setRefreshing(false);
                        GoodsShowBean bean = JsonUtil.parseObject(response.body(), GoodsShowBean.class);
                        if (bean != null) {
                            if (bean.code == HttpUtils.SUCCESS) {
                                deletedata(bean.dataMap);
                            } else {
                                showToast(bean.message);
                            }
                        } else {
                            showDataError();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void deletedata(List<GoodsShowBean.DataMapBean> dataMap) {
        // DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffDatasDeal(dataMap, datas), true);
        // diffResult.dispatchUpdatesTo(adapter);
        ArrayList<GoodsShowBean.DataMapBean> newslist = new ArrayList<>();
        newslist.addAll(dataMap);
        for (int i = 0; i < datas.size(); i++) {
            for (int size = newslist.size() - 1; size >= 0; size--) {
                if (datas.get(i).id.equals(newslist.get(size).id)) {
                    newslist.remove(size);
                }
            }
        }
        datas.addAll(0, newslist);
        adapter.notifyDataSetChanged();
    }

    private int page = 1;

    @Override
    protected void loadData() {
        HttpParams params = new HttpParams();
        params.put("page", String.valueOf(page));
        HttpUtils.getFraNetData(HttpUtils.GOODS_LIST_INFO, this, params, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                adapter.loadMoreComplete();
                GoodsShowBean bean = JsonUtil.parseObject(data, GoodsShowBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        page++;
                        datas.addAll(bean.dataMap);
                        adapter.notifyDataSetChanged();
                        if (bean.dataMap.size() < 10) {
                            adapter.loadMoreEnd();
                        }
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showDataError();
                }
            }
        });
    }

    private void initbanner(final List<BannerBean.DataMapBean> dataMap) {
        PageBean bean = new PageBean.Builder<BannerBean.DataMapBean>()
                .setDataObjects(dataMap)
                .setIndicator(mBottomScaleLayout)
                .builder();
        mLoopViewpager.setPageTransformer(false, new MzTransformer());
        mLoopViewpager.setPageListener(bean, R.layout.item_banner_pic, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                ImageView mpic = view.findViewById(R.id.iv_pic);
                final BannerBean.DataMapBean dataMapBean = (BannerBean.DataMapBean) o;
                GlideUtils.loadpic(mctx, mpic, dataMapBean.src);
                mpic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataMapBean.type == 1) {
                            openActivity(new Intent(mctx, WebViewActivity.class)
                                    .putExtra("url", dataMapBean.link)
                                    .putExtra("type", 1));
                        } else if (dataMapBean.type == 2) {
                            openActivity(new Intent(mctx, BannerWebActivity.class).putExtra("url", dataMapBean.link));
                        } else if (dataMapBean.type == 3) {
                            openActivity(new Intent(mctx, ProductDetailActivity.class).putExtra("id", dataMapBean.id));
                        } else {

                        }
                    }
                });
            }
        });
    }

    @Override
    protected void intiview(View view) {
        header = View.inflate(mctx, R.layout.header_frag_homepage, null);
        mBottomScaleLayout = header.findViewById(R.id.bottom_scale_layout);
        mLoopViewpager = header.findViewById(R.id.loop_viewpager);
        mLlGiftNew = header.findViewById(R.id.ll_gift_new);
        mLlGoodsShop = header.findViewById(R.id.ll_goods_shop);
        mLlVideo = header.findViewById(R.id.ll_video);
        mLlIntro = header.findViewById(R.id.ll_intro);
        mLlAgent = header.findViewById(R.id.ll_agent);
        mLlTuig = header.findViewById(R.id.ll_tuig);
        mLlJoin = header.findViewById(R.id.ll_join);
        mLlCaogen = header.findViewById(R.id.ll_caogen);
        mIvFirst = header.findViewById(R.id.iv_first);
        mIvTwo = header.findViewById(R.id.iv_two);
        mIvThree = header.findViewById(R.id.iv_three);
        mllIncome = header.findViewById(R.id.ll_income);
        mIvGoldArea = header.findViewById(R.id.iv_gold_area);
        mIvAgarea = header.findViewById(R.id.iv_agarea);
        mIvNewArea = header.findViewById(R.id.iv_new_area);
        area = header.findViewById(R.id.area);
        mIvmore = header.findViewById(R.id.iv_more);
        // mIvMessage = view.findViewById(R.id.iv_message);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText("首页");
        mIvCar = view.findViewById(R.id.iv_car);
        mTvNum = view.findViewById(R.id.tv_num);
        mRlvContent = view.findViewById(R.id.rlv_content);
        refreshLayout = view.findViewById(R.id.sfl_fresh);
        loadinfo();
        loadbanner();
        loadPic();
    }

    private void loadPic() {
        HttpUtils.getFraNetData(HttpUtils.INCOMNE_LIST_TOP, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                HeaderInfoBean bean = JsonUtil.parseObject(data, HeaderInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap.size() > 2) {
                        GlideUtils.loadCirclepic(mctx, mIvFirst, bean.dataMap.get(0).headerImg);
                        GlideUtils.loadCirclepic(mctx, mIvTwo, bean.dataMap.get(1).headerImg);
                        GlideUtils.loadCirclepic(mctx, mIvThree, bean.dataMap.get(2).headerImg);
                    } else if (bean.dataMap.size() == 2) {
                        GlideUtils.loadCirclepic(mctx, mIvFirst, bean.dataMap.get(0).headerImg);
                        GlideUtils.loadCirclepic(mctx, mIvTwo, bean.dataMap.get(1).headerImg);
                    } else if (bean.dataMap.size() == 1) {
                        GlideUtils.loadCirclepic(mctx, mIvFirst, bean.dataMap.get(0).headerImg);
                    } else {

                    }
                }
            }
        });
    }

    private void loadbanner() {
        HttpUtils.getFraNetData(HttpUtils.HOME_PAGE_BANNER, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                BannerBean bean = JsonUtil.parseObject(data, BannerBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS && bean.dataMap != null) {
                    initbanner(bean.dataMap);
                }
            }
        });
    }

    private void loadinfo() {
        HttpUtils.getFraNetData(HttpUtils.USER_INFO, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                UserDetailsBean bean = JsonUtil.parseObject(data, UserDetailsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS && bean.dataMap != null) {
                    type = bean.dataMap.userType;
                    flag = bean.dataMap.refundFlag;
                    if (bean.dataMap.coinFlag == 1) {
                        area.setVisibility(View.VISIBLE);
                    } else {
                        area.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_home_page;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_message:
                // openActivity(MessageInfoActivity.class);
                break;
            case R.id.iv_car:
                openActivity(GoodsCarManagerActivity.class);
                break;
            case R.id.ll_gift_new:
                openActivity(MallGoodsListActivity.class);
                break;
            case R.id.ll_goods_shop:
                openActivity(GoodsShopActivity.class);
                break;
            case R.id.ll_video:
                openActivity(VideoListActivity.class);
                break;
            case R.id.ll_intro:
                openActivity(ProductDescActivity.class);
                break;
            case R.id.ll_agent:
//                if (flag == 1) {
//                    openActivity(new Intent(mctx, WebViewActivity.class).putExtra("url", HttpUtils.MEMBER_PAGE));
//                } else {
//                    openActivity(new Intent(mctx, WebViewActivity.class).putExtra("url", HttpUtils.MEMBER_PAGE_new));
//                }
                showToast("开发中");
                break;
            case R.id.ll_tuig:
                openActivity(new Intent(mctx, PromotionInfoActivity.class).putExtra("flag", flag));
                break;
            case R.id.ll_join:
                openActivity(CompanyJoinActivity.class);
                break;
            case R.id.ll_caogen:
                if ("2".equals(type)) {
                    openActivity(ShopOwnerShareActivity.class);
                } else {
                    showToast("您还还不是店主,请先升级");
                }
                break;
            case R.id.ll_income:
                openActivity(new Intent(mctx, IncomeListActivity.class).putExtra("type", 0));
                break;
            case R.id.iv_gold_area:
                // showToast("金币");
                break;
            case R.id.iv_new_area:
                // showToast("积分");
                break;
            case R.id.iv_agarea:
                // showToast("银币");
                break;
            case R.id.iv_more:
                // showToast("更多");
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
