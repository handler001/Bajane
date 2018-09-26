package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.ShopListAdapter;
import com.jiefutong.emall.bean.BtnDataBean;
import com.jiefutong.emall.bean.ShopListDetailsBean;
import com.jiefutong.emall.bean.ShopListHomeBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class AllianceMerchantActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener {

    private ImageView mIvBack;
    private TextView mTvSearch;
    private TextView mTvAddress;
    private RecyclerView mRlvShop;
    private View header;
    private RecyclerView mRlvBtn;
    private TextView mTvShopAll;
    private ArrayList<ShopListDetailsBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alliance_merchant);
        initView();
        settitlewhite();
        initdata();
        initmap();
    }

    private void initView() {
        header = View.inflate(context, R.layout.header_merchant_alliance, null);
        mRlvBtn = header.findViewById(R.id.rlv_btn);
        mRlvBtn.setLayoutManager(new GridLayoutManager(context, 5));
        mTvShopAll = header.findViewById(R.id.tv_shop_all);
        mTvShopAll.setOnClickListener(this);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvSearch.setOnClickListener(this);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mRlvShop = (RecyclerView) findViewById(R.id.rlv_shop);
        mRlvShop.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ShopListAdapter(R.layout.item_shop_list, datas, context);
        mRlvShop.setAdapter(adapter);
        adapter.setHeaderView(header);
    }

    private void initdata() {
        HttpUtils.getNetData(HttpUtils.SHOP_CATEGORY_LIST, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                BtnDataBean bean = JsonUtil.parseObject(data, BtnDataBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    mRlvBtn.setAdapter(new BtnAdapter(R.layout.item_btn_header_alliance, bean.dataMap));
                }
            }
        });
    }

    private void initshop(String lat, String lng) {
        HttpParams params = new HttpParams();
        params.put("lat", lat);
        params.put("lng", lng);
        HttpUtils.getNetData(HttpUtils.SHOP_NEAR_LIST, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                ShopListHomeBean bean = JsonUtil.parseObject(data, ShopListHomeBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.addData(bean.dataMap.nearList);
                }
            }
        });

    }

    private void initmap() {
        mLocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // mLocationOption.setInterval(2000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setOnceLocation(true);
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                openActivity(ShopSearchActivity.class);
                break;
            case R.id.tv_address:
                openActivityForResult(LocationSelectActivity.class, 600);
                break;
            case R.id.tv_shop_all:
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mTvAddress.setText(aMapLocation.getCity());
                initshop(String.valueOf(aMapLocation.getLatitude()), String.valueOf(aMapLocation.getLongitude()));
            } else {
                Log.i("sss", "onLocationChanged: " + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }
    }

    class BtnAdapter extends BaseQuickAdapter<BtnDataBean.DataMapBean, BaseViewHolder> {
        public BtnAdapter(int layoutResId, @Nullable List<BtnDataBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final BtnDataBean.DataMapBean item) {
            helper.setText(R.id.tv_name, item.categoryType);
            GlideUtils.loadpic(context, (ImageView) helper.getView(R.id.iv_btn), item.categoryImg);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(new Intent(context, ShopSearchActivity.class).putExtra("id", item.id));
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 600 && resultCode == 200) {
            double lat = data.getDoubleExtra("lat", 0);
            double lng = data.getDoubleExtra("lng", 0);
            String city = data.getStringExtra("city");
            mTvAddress.setText(city);
            initshop(String.valueOf(lat), String.valueOf(lng));
        }
    }
}
