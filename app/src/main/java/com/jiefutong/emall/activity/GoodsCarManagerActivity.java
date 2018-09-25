package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.GoodsBuyNowBean;
import com.jiefutong.emall.bean.GoodsCarBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.internal.android.JsonUtils;

public class GoodsCarManagerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvSave;
    private RecyclerView mRlvCar;
    private ImageView mIvSelectAll;
    private TextView mTvSales;
    private TextView mTvTotalPrice;
    // private TextView mTvFee;
    private boolean manager;
    private boolean all;
    private ArrayList<GoodsCarBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private RelativeLayout mLlBottom;
    private ArrayList<String> goodsIdlist = new ArrayList<>();
    private View view;
    private List<GoodsCarBean.DataMapBean.ContentBean> totals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_car_manager);
        initView();
        settitlewhite();
        loaddata();
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.CAR_GOODS_LIST, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                GoodsCarBean bean = JsonUtil.parseObject(data, GoodsCarBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap.content.size() == 0) {
                        mLlBottom.setVisibility(View.GONE);
                    } else {
                        mLlBottom.setVisibility(View.VISIBLE);
                    }
                    totals = bean.dataMap.content;
                    adapter.setNewData(bean.dataMap.content);
                } else {
                    showToast("获取购物车信息失败");
                }
            }
        });
    }

    private void initView() {
        view = View.inflate(context, R.layout.empty_goods_show, null);
        TextView tv = view.findViewById(R.id.tv_info);
        tv.setText("暂无购物车商品哟");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("购物车");
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mTvSave.setVisibility(View.VISIBLE);
        mTvSave.setText("管理");
        mTvSave.setOnClickListener(this);
        mRlvCar = (RecyclerView) findViewById(R.id.rlv_car);
        mRlvCar.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShopCarAdapter(R.layout.item_goods_car_info_details, datas);
        mRlvCar.setAdapter(adapter);
        adapter.setEmptyView(view);
        mIvSelectAll = (ImageView) findViewById(R.id.iv_select_all);
        mIvSelectAll.setOnClickListener(this);
        mTvSales = (TextView) findViewById(R.id.tv_sales);
        mTvSales.setOnClickListener(this);
        mTvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        // mTvFee = (TextView) findViewById(R.id.tv_fee);
        mLlBottom = (RelativeLayout) findViewById(R.id.ll_bottom);
        mLlBottom.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                if (manager) {
                    mTvSave.setText("管理");
                    mTvSales.setText("结算");
                    mTvSave.setTextColor(getcolor(R.color.text_black_3));
                } else {
                    mTvSave.setText("完成");
                    mTvSales.setText("删除");
                    mTvSave.setTextColor(getcolor(R.color.red_finish));
                }
                manager = !manager;
                break;
            case R.id.iv_select_all:
                all = !all;
                if (all) {
                    for (int i = 0; i < totals.size(); i++) {
                        goodsIdlist.add(totals.get(i).goodsId);
                    }
                    mIvSelectAll.setBackgroundResource(R.mipmap.btn_paystyle_selected);
                    adapter.notifyDataSetChanged();
                } else {
                    goodsIdlist.clear();
                    mIvSelectAll.setBackgroundResource(R.mipmap.btn_paystyle_default);
                    adapter.notifyDataSetChanged();
                }
                totalmoney();
                break;
            case R.id.tv_sales:
                if (goodsIdlist.size() == 0) {
                    showToast("请选择商品");
                    return;
                }
                if (!manager) {
                    String ids = "";
                    String nums = "";
                    String infos = "";
                    String cartid = "";
                    for (int i = 0; i < totals.size(); i++) {
                        if (goodsIdlist.contains(totals.get(i).goodsId)) {
                            ids += totals.get(i).goodsId + ",";
                            nums += totals.get(i).goodsNum + ",";
                            if (!TextUtils.isEmpty(totals.get(i).categoryType)) {
                                infos += totals.get(i).categoryType + ",";
                            }
                            cartid += totals.get(i).id + ",";
                        }
                    }
                    if (infos.length() == 0) {
                        buynow(ids.substring(0, ids.length() - 1),
                                nums.substring(0, nums.length() - 1),
                                "",
                                cartid.substring(0, cartid.length() - 1));
                    } else {
                        buynow(ids.substring(0, ids.length() - 1),
                                nums.substring(0, nums.length() - 1),
                                infos.substring(0, infos.length() - 1),
                                cartid.substring(0, cartid.length() - 1));
                    }

                } else {
                    new DiaglogUtils(context, "是否要删除商品") {
                        @Override
                        public void sureMessage() {
                            String ids = "";
                            for (int i = 0; i < totals.size(); i++) {
                                if (goodsIdlist.contains(totals.get(i).goodsId)) {
                                    ids += totals.get(i).id + ",";
                                }
                            }
                            deleteids(ids.substring(0, ids.length() - 1), true);
                        }
                    };
                }
                break;
        }
    }

    private class ShopCarAdapter extends BaseQuickAdapter<GoodsCarBean.DataMapBean.ContentBean, BaseViewHolder> {

        public ShopCarAdapter(int layoutResId, @Nullable List<GoodsCarBean.DataMapBean.ContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final GoodsCarBean.DataMapBean.ContentBean item) {
            GlideUtils.loadpic(context, (ImageView) helper.getView(R.id.iv_pic), item.goodsIcon);
            helper.setText(R.id.tv_name, item.goodsName)
                    .setText(R.id.tv_type, item.category)
                    .setText(R.id.tv_price, "￥" + item.goodsPrice)
                    .setText(R.id.tv_num, String.valueOf(item.goodsNum));
            helper.getView(R.id.prl_pic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (goodsIdlist.contains(item.goodsId)) {
                        goodsIdlist.remove(item.goodsId);
                    } else {
                        goodsIdlist.add(item.goodsId);
                    }
                    notifyDataSetChanged();
                    if (goodsIdlist.size() == totals.size()) {
                        mIvSelectAll.setBackgroundResource(R.mipmap.btn_paystyle_selected);
                    } else {
                        mIvSelectAll.setBackgroundResource(R.mipmap.btn_paystyle_default);
                    }
                    totalmoney();
                }
            });
            if (goodsIdlist.contains(item.goodsId)) {
                helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.btn_paystyle_selected);
            } else {
                helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.btn_paystyle_default);
            }
            helper.getView(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.goodsNum += 1;
                    helper.setText(R.id.tv_num, String.valueOf(item.goodsNum));
                    totalmoney();
                }
            });

            helper.getView(R.id.tv_remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.goodsNum <= 1) {
                        showToast("数量不能小于1");
                        return;
                    } else {
                        item.goodsNum -= 1;
                        helper.setText(R.id.tv_num, String.valueOf(item.goodsNum));
                        totalmoney();
                    }
                }
            });

            helper.getView(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DiaglogUtils(context, "是否要删除商品") {
                        @Override
                        public void sureMessage() {
                            deleteids(item.id, false);
                        }
                    };
                }
            });
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("id", item.goodsId));
                }
            });
        }
    }

    private void deleteids(final String goodsId, final boolean all) {
        HttpParams params = new HttpParams();
        params.put("id", goodsId);
        HttpUtils.getNetData(HttpUtils.CAR_GOODS_DELETE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (goodsIdlist.contains(goodsId) && !all) {
                        goodsIdlist.remove(goodsId);
                    }
                    if (all) {
                        goodsIdlist.clear();
                    }
                    totalmoney();
                    loaddata();
                } else {
                    showToast("删除失败");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    private void buynow(String ids, String nums, String idinfos, String carid) {
        HttpParams params = new HttpParams();
        params.put("id", ids);
        params.put("number", nums);
        params.put("categoryType", idinfos);
        params.put("shopCartId", carid);
        HttpUtils.getNetData(HttpUtils.GOODS_BUY_NOW, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                GoodsBuyNowBean bean = JsonUtil.parseObject(data, GoodsBuyNowBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    loaddata();
                    totalmoney();
                    openActivity(new Intent(context, ProductBuyInfoActivity.class).putExtra("bean", bean.dataMap));
                } else {
                    showToast("购买失败");
                }
            }
        });
    }

    private void totalmoney() {
        double price = 0;
        for (int i = 0; i < totals.size(); i++) {
            if (goodsIdlist.contains(totals.get(i).goodsId)) {
                price += totals.get(i).goodsPrice * totals.get(i).goodsNum;
            }
        }
        mTvTotalPrice.setText("￥" + getTwoDecimal(price));
    }

    private double getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String yearString = dFormat.format(num);
        Double temp = Double.valueOf(yearString);
        return temp;
    }
}
