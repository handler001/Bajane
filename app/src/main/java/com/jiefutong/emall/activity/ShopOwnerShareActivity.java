package com.jiefutong.emall.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.ShopInfoBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.ResourceUtils;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShopOwnerShareActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvShopOrder;
    private TextView mTvShopIncome;
    private ImageView mIvPic;
    private TextView mTvName;
    private TextView mTvAddress;
    private TextView mTvMap;
    private TextView mTvPhone;
    private ShopInfoBean.DataMapBean dataMapBean;
    private RelativeLayout mRlShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_share);
        initView();
        settitlewhite();
        loaddata();
        EventBus.getDefault().register(this);
    }


    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("店主专享");
        mTvShopOrder = (TextView) findViewById(R.id.tv_shop_order);
        mTvShopOrder.setOnClickListener(this);
        mTvShopIncome = (TextView) findViewById(R.id.tv_shop_income);
        mTvShopIncome.setOnClickListener(this);
        mIvPic = (ImageView) findViewById(R.id.iv_pic);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvMap = (TextView) findViewById(R.id.tv_map);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvMap.setOnClickListener(this);
        mTvPhone.setOnClickListener(this);
        mRlShop = (RelativeLayout) findViewById(R.id.rl_shop);
        mRlShop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_shop_order:
                openActivity(ShopOrderActivity.class);
                break;
            case R.id.tv_shop_income:
                openActivity(new Intent(context, IncomeListActivity.class).putExtra("type", 1));
                break;
            case R.id.rl_shop:
                if (dataMapBean != null) {
                    openActivityForResult(new Intent(context, StoreInfoActivity.class).putExtra("bean", dataMapBean), 100);
                }
                break;
            case R.id.tv_map:
                if (dataMapBean != null) {
                    Intent intent = new Intent(context, MapLocationActivity.class);
                    intent.putExtra("name", dataMapBean.shopName);
                    intent.putExtra("lat", dataMapBean.lat);
                    intent.putExtra("lng", dataMapBean.lng);
                    openActivity(intent);
                }
                break;
            case R.id.tv_phone:
                if (dataMapBean != null) {
                    new DiaglogUtils(context, ResourceUtils.getString(context, R.string.phone) + dataMapBean.shopTel) {
                        @Override
                        public void sureMessage() {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dataMapBean.shopTel));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            openActivity(intent);
                        }
                    };
                }
                break;
        }
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.MY_SHOP_INFO, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                ShopInfoBean bean = JsonUtil.parseObject(data, ShopInfoBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap != null) {
                        GlideUtils.loadpic(context, mIvPic, bean.dataMap.imgUrl);
                        mTvName.setText(bean.dataMap.shopName);
                        mTvAddress.setText(bean.dataMap.shopAdress);
                        mTvPhone.setText(bean.dataMap.shopTel);
                        dataMapBean = bean.dataMap;
                    }
                } else {
                    showDataError();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            loaddata();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean busBean) {
        if (busBean.cod.equals("pic")) {
            loaddata();
        }
    }
}
