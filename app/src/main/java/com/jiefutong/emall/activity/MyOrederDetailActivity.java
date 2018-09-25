package com.jiefutong.emall.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.GoodsBean;
import com.jiefutong.emall.bean.OrderDetailBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;
import com.xuexiang.xqrcode.XQRCode;

import java.util.ArrayList;
import java.util.List;


public class MyOrederDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvCode;
    private TextView mTvCodeNum;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvAddress;
    //    private ImageView mIvPic;
//    private TextView mTvGoodsName;
//    private TextView mTvType;
//    private TextView mTvPrice;
    //   private TextView mTvReturn;
    private TextView mTvPayType;
    private TextView mTvPostPrice;
    private TextView mTvPayPrice;
    private TextView mTvOrderCode;
    private TextView mTvCopy;
    private TextView mTvOrderTimeStart;
    private TextView mTvOrderTimePay;
    private PopupWindow popupWindow;
    private String orderid;
    private RecyclerView mRlv;
    private String ordercode, code;
    private RelativeLayout mRlCode;
    private TextView mTvReturnMoney;
    private ArrayList<GoodsBean> datas;
    private TextView mTvPayPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_oreder_detail);
        settitlewhite();
        initView();
        loaddata();
    }

    private void initView() {
        orderid = getIntent().getStringExtra("id");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("订单详情");
        mIvCode = (ImageView) findViewById(R.id.iv_my_code);
        mIvCode.setOnClickListener(this);
        mTvCodeNum = (TextView) findViewById(R.id.tv_code_num);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
//        mIvPic = (ImageView) findViewById(R.id.iv_pic);
//        mTvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
//        mTvType = (TextView) findViewById(R.id.tv_type);
//        mTvPrice = (TextView) findViewById(R.id.tv_price);
//        mTvReturn = (TextView) findViewById(R.id.tv_return);
//        mTvReturn.setOnClickListener(this);
        mTvPayType = (TextView) findViewById(R.id.tv_pay_type);
        mTvPostPrice = (TextView) findViewById(R.id.tv_post_price);
        mTvPayPrice = (TextView) findViewById(R.id.tv_pay_price);
        mTvOrderCode = (TextView) findViewById(R.id.tv_order_code);
        mTvCopy = (TextView) findViewById(R.id.tv_copy);
        mTvCopy.setOnClickListener(this);
        mTvOrderTimeStart = (TextView) findViewById(R.id.tv_order_time_start);
        mTvOrderTimePay = (TextView) findViewById(R.id.tv_order_time_pay);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(context));
        mRlCode = (RelativeLayout) findViewById(R.id.rl_code);
        mTvReturnMoney = (TextView) findViewById(R.id.tv_return_money);
        mTvReturnMoney.setOnClickListener(this);
        mTvReturnMoney.setVisibility(View.GONE);
        mTvPayPost = (TextView) findViewById(R.id.tv_pay_post);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_my_code:
                showpop();
                break;
//            case R.id.tv_return:
//                openActivity(ReturnActivity.class);
//                break;
            case R.id.tv_copy:
                if (!TextUtils.isEmpty(ordercode)) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText(null, ordercode));
                    // 将文本内容放到系统剪贴板里。
                    showToast("复制成功");
                }
                break;
            case R.id.tv_return_money:
                if (datas != null && datas.size() != 0) {
                    openActivity(new Intent(context, GoodsSelectListActivity.class)
                            .putParcelableArrayListExtra("data", datas));
                    finish();
                }
                break;
        }
    }

    private void showpop() {
        if (popupWindow == null) {
            Bitmap qrCodeWithLogo = XQRCode.createQRCodeWithLogo(code, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_launcher));
            View view = View.inflate(context, R.layout.layout_code_show, null);
            TextView tv = view.findViewById(R.id.tv_pwd_code);
            ImageView iv = view.findViewById(R.id.iv_pwd_code);
            iv.setImageBitmap(qrCodeWithLogo);
            tv.setText("券码:" + code);
            popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            view.findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAtLocation(mIvBack, Gravity.CENTER, 0, 0);
        } else {
            popupWindow.showAtLocation(mIvBack, Gravity.CENTER, 0, 0);
        }
    }

    private void loaddata() {
        HttpParams params = new HttpParams();
        params.put("orderNo", orderid);
        HttpUtils.getNetData(HttpUtils.ORDER_INFO_DETAIL, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                OrderDetailBean bean = JsonUtil.parseObject(data, OrderDetailBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    datas = bean.dataMap.goods;
                    mRlv.setAdapter(new InfoAdapter(R.layout.item_goods_info_count, bean.dataMap.goods));
                    if (bean.dataMap.address != null) {
                        mTvName.setText(bean.dataMap.address.receiveName);
                        mTvPhone.setText(bean.dataMap.address.receiveTel);
                        mTvAddress.setText(bean.dataMap.address.receiveProvince
                                + bean.dataMap.address.receiveCity + bean.dataMap.address.receiveCountry
                                + bean.dataMap.address.receiveDetail);
                    }
                    if (bean.dataMap.shop != null) {
                        mTvName.setText(bean.dataMap.shop.shopName);
                        mTvPhone.setText(bean.dataMap.shop.shopTel);
                        mTvAddress.setText(bean.dataMap.shop.shopAdress);
                    }
                    mTvOrderCode.setText(bean.dataMap.orderNo);
                    ordercode = bean.dataMap.orderNo;
                    if (bean.dataMap.veriftyCode == null || TextUtils.isEmpty(bean.dataMap.veriftyCode)) {
                        mRlCode.setVisibility(View.GONE);
                    } else {
                        mRlCode.setVisibility(View.VISIBLE);
                        code = bean.dataMap.veriftyCode;
                        Bitmap qrCodeWithLogo = XQRCode.newQRCodeBuilder(bean.dataMap.veriftyCode).build();
                        mIvCode.setImageBitmap(qrCodeWithLogo);
                    }
                    if (("weChart").equals(bean.dataMap.alipayType)) {
                        mTvPayType.setText("微信支付");
                    } else if (("weChart").equals(bean.dataMap.alipayType)) {
                        mTvPayType.setText("支付宝支付");
                    } else {
                        mTvPayType.setText("余额支付");
                    }
                    mTvOrderTimeStart.setText(bean.dataMap.createDate);
                    mTvCodeNum.setText(bean.dataMap.veriftyCode);
                    mTvOrderTimePay.setText(bean.dataMap.payDate);
                    mTvPostPrice.setText("￥ " + bean.dataMap.amt);
                    mTvPayPost.setText("￥ " + bean.dataMap.postage);
                    mTvPayPrice.setText("￥ " + bean.dataMap.realAmt);
                } else {
                    showToast("详情获取异常");
                }
            }
        });

    }

    class InfoAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        public InfoAdapter(int layoutResId, @Nullable List<GoodsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.cover);
            helper.setText(R.id.tv_name, item.goodsName)
                    .setText(R.id.tv_price, "￥" + item.goodsPrice)
                    .setText(R.id.tv_count, "x" + item.goodsNum);
        }
    }
}
