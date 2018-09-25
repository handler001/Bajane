package com.jiefutong.emall.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.OrderInfoAdapter;
import com.jiefutong.emall.bean.AddressInfosBean;
import com.jiefutong.emall.bean.AliPayBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.GoodsBuyNowBean;
import com.jiefutong.emall.bean.GoodsShopBean;
import com.jiefutong.emall.bean.PayResult;
import com.jiefutong.emall.bean.WeiXinBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.widget.PassValitationPopwindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ProductBuyInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvHome;
    private TextView mTvSelf;
    private RelativeLayout mRlAddLocation;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvAddress;
    private RelativeLayout mRlLocation;
    private ImageView mIvWxCheck;
    private LinearLayout mLlWx;
    private ImageView mIvAliCheck;
    private LinearLayout mLlAli;
    private TextView mTvGoldNum;
    private ImageView mIvGoldCheck;
    private LinearLayout mLlGold;
    private EditText mEtContent;
    private TextView mTvGoodsPrice;
    private TextView mTvPostPrice;
    private TextView mTvPayPrice;
    private TextView mTvMoneyTotal;
    private TextView mTvMoneyPay;
    private GoodsBuyNowBean.DataMapBean bean;
    private String addressid = "";
    private String shopId = "";
    private RecyclerView mRlvGoods;
    private double moneyTotal;
    private int position;
    private String paymethod = "weChart";
    private RelativeLayout mRlShopSelf;
    private TextView mTvShopName;
    private TextView mTvShopPhone;
    private TextView mTvShopAddress;
    private RelativeLayout mRlShopSelfAddress;
    private IWXAPI mWxApi;
    private static final int SDK_PAY_FLAG = 1;
    private String orderNo;
    private String pwd;
    private String goodsNo;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_buy_info);
        mWxApi = WXAPIFactory.createWXAPI(this, "wx7a2905373c997b22", false);
        // 将该app注册到微信
        mWxApi.registerApp("wx7a2905373c997b22");
        EventBus.getDefault().register(this);
        settitlewhite();
        initView();
    }

    private void initView() {
        bean = (GoodsBuyNowBean.DataMapBean) getIntent().getSerializableExtra("bean");
        goodsNo = getIntent().getStringExtra("orderNo");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("立即购买");
        mTvHome = (TextView) findViewById(R.id.tv_home);
        mTvHome.setOnClickListener(this);
        mTvSelf = (TextView) findViewById(R.id.tv_self);
        mTvSelf.setOnClickListener(this);
        mRlAddLocation = (RelativeLayout) findViewById(R.id.rl_add_location);
        mRlAddLocation.setOnClickListener(this);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mRlLocation = (RelativeLayout) findViewById(R.id.rl_location);
        mRlLocation.setOnClickListener(this);
        mIvWxCheck = (ImageView) findViewById(R.id.iv_wx_check);
        mLlWx = (LinearLayout) findViewById(R.id.ll_wx);
        mLlWx.setOnClickListener(this);
        mIvAliCheck = (ImageView) findViewById(R.id.iv_ali_check);
        mLlAli = (LinearLayout) findViewById(R.id.ll_ali);
        mLlAli.setOnClickListener(this);
        mTvGoldNum = (TextView) findViewById(R.id.tv_gold_num);
        mTvGoldNum.setText("(¥" + bean.wallet + ")");
        mIvGoldCheck = (ImageView) findViewById(R.id.iv_gold_check);
        mLlGold = (LinearLayout) findViewById(R.id.ll_gold);
        mLlGold.setOnClickListener(this);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mTvGoodsPrice = (TextView) findViewById(R.id.tv_goods_price);
        mTvPostPrice = (TextView) findViewById(R.id.tv_post_price);
        mTvPayPrice = (TextView) findViewById(R.id.tv_pay_price);
        mTvMoneyTotal = (TextView) findViewById(R.id.tv_money_total);
        mTvMoneyPay = (TextView) findViewById(R.id.tv_money_pay);
        mTvMoneyPay.setOnClickListener(this);
        mRlvGoods = (RecyclerView) findViewById(R.id.rlv_goods);
        mRlvGoods.setLayoutManager(new LinearLayoutManager(context));
        mRlvGoods.setAdapter(new OrderInfoAdapter(R.layout.item_goods_buy_now, bean.goods));
        mRlShopSelf = (RelativeLayout) findViewById(R.id.rl_shop_self);
        mRlShopSelf.setOnClickListener(this);
        mTvShopName = (TextView) findViewById(R.id.tv_shop_name);
        mTvShopPhone = (TextView) findViewById(R.id.tv_shop_phone);
        mTvShopAddress = (TextView) findViewById(R.id.tv_shop_address);
        mRlShopSelfAddress = (RelativeLayout) findViewById(R.id.rl_shop_self_address);
        mRlShopSelfAddress.setOnClickListener(this);
        flag = bean.transPasswordFlag;
        if (bean != null && bean.address == null) {
            dealposition(1);
        } else {
            dealposition(0);
        }
        dealMoneyPrice();
    }

    private void dealMoneyPrice() {
        double price = 0.0;
        for (int i = 0; i < bean.goods.size(); i++) {
            price += bean.goods.get(i).goodsPrice * bean.goods.get(i).goodsNum;
        }
        if (position == 0) {
            moneyTotal = getTwoDecimal(price) + bean.postage;
        } else {
            moneyTotal = getTwoDecimal(price);
        }
        mTvGoodsPrice.setText(String.valueOf(getTwoDecimal(price)));
        mTvPayPrice.setText(String.valueOf(moneyTotal));
        mTvMoneyTotal.setText(String.valueOf(moneyTotal));
    }

    /**
     * 将数据保留两位小数
     */
    private double getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String yearString = dFormat.format(num);
        Double temp = Double.valueOf(yearString);
        return temp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_home:
                dealposition(0);
                break;
            case R.id.tv_self:
                dealposition(1);
                break;
            case R.id.rl_location:
            case R.id.rl_add_location:
                openActivityForResult(new Intent(context, AddressManagerActivity.class).putExtra("select", true), 100);
                break;
            case R.id.rl_shop_self:
            case R.id.rl_shop_self_address:
                openActivityForResult(new Intent(context, GoodsShopActivity.class).putExtra("type", 1), 200);
                break;
            case R.id.tv_money_pay:
                pwd = "";
                if (paymethod.equals("banlance")) {
                    if (bean.wallet < moneyTotal) {
                        showToast("余额不足");
                    } else {
                        submit();
                    }
                } else {
                    payGoodsMoney();
                }
                break;
            case R.id.ll_wx:
                dealpay(0);
                break;
            case R.id.ll_ali:
                dealpay(1);
                break;
            case R.id.ll_gold:
                dealpay(2);
                break;
        }
    }

    private void payGoodsMoney() {
        if ((TextUtils.isEmpty(addressid) && position == 0 && bean.address == null) && (TextUtils.isEmpty(shopId) && position == 1 && bean.shop == null)) {
            showToast("请选择地址信息");
            return;
        }

        String content = mEtContent.getText().toString().trim();
        String ids = "";
        String nums = "";
        String types = "";
        for (int i = 0; i < bean.goods.size(); i++) {
            ids += bean.goods.get(i).id + ",";
            nums += bean.goods.get(i).goodsNum + ",";
            if (!TextUtils.isEmpty(bean.goods.get(i).categoryType)) {
                types += bean.goods.get(i).categoryType + ",";
            }
        }
        HttpParams params = new HttpParams();
        if (TextUtils.isEmpty(goodsNo)) {
            params.put("goodsId", ids.substring(0, ids.length() - 1));
            params.put("goodsNum", nums.substring(0, nums.length() - 1));
        } else {
            params.put("orderNo", goodsNo);
        }
        if (types.length() == 0) {
            params.put("categoryType", "");
        } else {
            params.put("categoryType", types.substring(0, types.length() - 1));
        }
        if (position == 0) {
            params.put("orderType", "1");
            if (TextUtils.isEmpty(addressid) && bean.address != null) {
                params.put("addressId", bean.address.id);
            } else {
                params.put("addressId", addressid);
            }
        } else {
            params.put("orderType", "2");
            if (TextUtils.isEmpty(shopId) && bean.shop != null) {
                params.put("shopId", bean.shop.id);
            } else {
                params.put("shopId", shopId);
            }

        }
        params.put("payType", paymethod);
        params.put("comment", content);
        if (!TextUtils.isEmpty(pwd)) {
            params.put("transPassword", pwd);
        }
        HttpUtils.getNetData(HttpUtils.ORDER_ADD, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        if (paymethod.equals("weChart")) {
                            wxpay(bean.dataMap);
                        } else if (paymethod.equals("alipay")) {
                            aliPay(bean.dataMap);
                        } else {
                            showToast("支付成功");
                            EventBusBean beans = new EventBusBean();
                            beans.cod = "order";
                            EventBus.getDefault().post(beans);
                            openActivity(new Intent(context, PayFinishActivity.class).putExtra("orderno", bean.dataMap));
                            finish();
                        }
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showToast("支付失败,请重试");
                }
            }
        });
    }

    private void dealpay(int i) {
        if (i == 0) {
            paymethod = "weChart";
            mIvWxCheck.setBackgroundResource(R.mipmap.btn_paystyle_selected);
            mIvAliCheck.setBackgroundResource(R.mipmap.btn_paystyle_default);
            mIvGoldCheck.setBackgroundResource(R.mipmap.btn_paystyle_default);
        } else if (i == 1) {
            paymethod = "alipay";
            mIvWxCheck.setBackgroundResource(R.mipmap.btn_paystyle_default);
            mIvAliCheck.setBackgroundResource(R.mipmap.btn_paystyle_selected);
            mIvGoldCheck.setBackgroundResource(R.mipmap.btn_paystyle_default);
        } else {
            paymethod = "banlance";
            mIvWxCheck.setBackgroundResource(R.mipmap.btn_paystyle_default);
            mIvAliCheck.setBackgroundResource(R.mipmap.btn_paystyle_default);
            mIvGoldCheck.setBackgroundResource(R.mipmap.btn_paystyle_selected);
        }
    }

    private void hasAddress() {
        if (!TextUtils.isEmpty(addressid)) {
            mRlLocation.setVisibility(View.VISIBLE);
            mRlAddLocation.setVisibility(View.GONE);
        } else if (bean.address != null && TextUtils.isEmpty(addressid)) {
            mRlLocation.setVisibility(View.VISIBLE);
            mRlAddLocation.setVisibility(View.GONE);
            mTvName.setText(bean.address.receiveName);
            mTvPhone.setText(bean.address.receiveTel);
            mTvAddress.setText(bean.address.receiveProvince + bean.address.receiveCity + bean.address.receiveCountry + bean.address.receiveDetail);
        } else {
            mRlLocation.setVisibility(View.GONE);
            mRlAddLocation.setVisibility(View.VISIBLE);
        }
    }

    private void dealposition(int i) {
        if (i == 0) {
            position = 0;
            mTvPostPrice.setText(String.valueOf(bean.postage));
            mTvHome.setBackgroundResource(R.drawable.shape_orange_bg_post_stroke);
            mTvHome.setTextColor(getcolor(R.color.text_total_money));
            mTvSelf.setBackgroundResource(R.drawable.shape_gray_bg_post_stroke);
            mTvSelf.setTextColor(getcolor(R.color.post_nor));
            mRlShopSelf.setVisibility(View.GONE);
            mRlShopSelfAddress.setVisibility(View.GONE);
            dealMoneyPrice();
            hasAddress();
        } else {
            position = 1;
            dealMoneyPrice();
            mTvPostPrice.setText(String.valueOf(0));
            mTvSelf.setBackgroundResource(R.drawable.shape_orange_bg_post_stroke);
            mTvSelf.setTextColor(getcolor(R.color.text_total_money));
            mTvHome.setBackgroundResource(R.drawable.shape_gray_bg_post_stroke);
            mTvHome.setTextColor(getcolor(R.color.post_nor));
            mRlLocation.setVisibility(View.GONE);
            mRlAddLocation.setVisibility(View.GONE);
            if (TextUtils.isEmpty(shopId) && bean.shop != null) {
                mRlShopSelf.setVisibility(View.GONE);
                mRlShopSelfAddress.setVisibility(View.VISIBLE);
                mRlLocation.setVisibility(View.GONE);
                mRlAddLocation.setVisibility(View.GONE);
                mTvShopName.setText(bean.shop.shopName);
                mTvShopPhone.setText(bean.shop.shopTel);
                mTvShopAddress.setText(bean.shop.shopAdress);
            } else if (!TextUtils.isEmpty(shopId)) {
                mRlShopSelf.setVisibility(View.GONE);
                mRlShopSelfAddress.setVisibility(View.VISIBLE);
            } else {
                mRlShopSelf.setVisibility(View.VISIBLE);
                mRlShopSelfAddress.setVisibility(View.GONE);
                mRlLocation.setVisibility(View.GONE);
                mRlAddLocation.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100 && data != null) {
            AddressInfosBean.DataMapBean.ContentBean bean = (AddressInfosBean.DataMapBean.ContentBean) data.getSerializableExtra("bean");
            dealaddress(bean);
        }
        if (requestCode == 200 && resultCode == 200 && data != null) {
            GoodsShopBean.DataMapBean.ContentBean bean = (GoodsShopBean.DataMapBean.ContentBean) data.getSerializableExtra("bean");
            shopId = bean.id;
            mRlShopSelf.setVisibility(View.GONE);
            mRlShopSelfAddress.setVisibility(View.VISIBLE);
            mTvShopName.setText(bean.shopName);
            mTvShopPhone.setText(bean.shopTel);
            mTvShopAddress.setText(bean.shopAdress);
        }
    }

    private void dealaddress(AddressInfosBean.DataMapBean.ContentBean bean) {
        addressid = bean.id;
        mRlLocation.setVisibility(View.VISIBLE);
        mRlAddLocation.setVisibility(View.GONE);
        mTvName.setText(bean.receiveName);
        mTvPhone.setText(bean.receiveTel);
        mTvAddress.setText(bean.receiveProvince + bean.receiveCity + bean.receiveCountry + bean.receiveDetail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

    private class OrderInfoAdapter extends BaseQuickAdapter<GoodsBuyNowBean.DataMapBean.GoodsBean, BaseViewHolder> {

        public OrderInfoAdapter(int layoutResId, @Nullable List<GoodsBuyNowBean.DataMapBean.GoodsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final GoodsBuyNowBean.DataMapBean.GoodsBean item) {
            GlideUtils.loadpic(context, (ImageView) helper.getView(R.id.iv_pics), item.cover);
            helper.setText(R.id.tv_content, item.goodsName)
                    .setText(R.id.tv_color, item.categoryText)
                    .setText(R.id.tv_price, "¥" + item.goodsPrice)
                    .setText(R.id.tv_num_buy, String.valueOf(item.goodsNum));
            if (!TextUtils.isEmpty(goodsNo)) {
                helper.getView(R.id.tv_add).setVisibility(View.GONE);
                helper.getView(R.id.tv_remove).setVisibility(View.GONE);
                helper.getView(R.id.tv_num_buy_new).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_num_buy).setVisibility(View.GONE);
                helper.setText(R.id.tv_num_buy_new, "x" + String.valueOf(item.goodsNum));
            } else {
                helper.getView(R.id.tv_num_buy_new).setVisibility(View.GONE);
                helper.getView(R.id.tv_num_buy).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_add).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_remove).setVisibility(View.VISIBLE);
            }
            helper.getView(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.goodsNum = item.goodsNum + 1;
                    dealMoneyPrice();
                    helper.setText(R.id.tv_num_buy, String.valueOf(item.goodsNum));
                }
            });

            helper.getView(R.id.tv_remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.goodsNum <= 1) {
                        showToast("数量最低为1");
                    } else {
                        item.goodsNum = item.goodsNum - 1;
                    }
                    helper.setText(R.id.tv_num_buy, String.valueOf(item.goodsNum));
                    dealMoneyPrice();
                }
            });
        }
    }

    private void wxpay(String message) {
        if (!mWxApi.isWXAppInstalled()) {
            showToast("您还未安装微信客户端");
            return;
        }
        WeiXinBean wx = JsonUtil.parseObject(message, WeiXinBean.class);
        if (wx == null) {
            showToast("支付失败,请重试");
            return;
        }
        PayReq request = new PayReq();
        //应用ID
        request.appId = wx.appid;
        //商户号
        request.partnerId = wx.partnerid;
        //预支付交易会话ID
        request.prepayId = wx.prepayid;
        //扩展字段
        request.packageValue = "Sign=WXPay";
        //随机字符串
        request.nonceStr = wx.noncestr;
        //时间戳
        request.timeStamp = wx.timestamp;
        //签名
        request.sign = wx.sign;
        orderNo = wx.orderNo;
        mWxApi.sendReq(request);
    }

    private void aliPay(final String info) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ProductBuyInfoActivity.this);
                Map<String, String> result = alipay.payV2(info, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showToast("支付成功");
                        AliPayBean bean = JsonUtil.parseObject(resultInfo, AliPayBean.class);
                        EventBusBean beans = new EventBusBean();
                        beans.cod = "order";
                        EventBus.getDefault().post(beans);
                        openActivity(new Intent(context, PayFinishActivity.class).putExtra("orderno", bean.alipay_trade_app_pay_response.out_trade_no));
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                    }
                    break;
                }
            }
        }
    };


    private void submit() {
        if (!flag) {
            new DiaglogUtils(context, "请先设置交易密码") {
                @Override
                public void sureMessage() {
                    openActivity(TakePwdSetActivity.class);
                }
            };
            return;
        }
        new PassValitationPopwindow(this, 1, findViewById(R.id.tv_money_pay),
                new PassValitationPopwindow.OnInputNumberCodeCallback() {
                    @Override
                    public void onSuccess(String code) {
                        pwd = code;
                        payGoodsMoney();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean bean) {
        if (bean.cod.equals("success")) {
            EventBusBean beans = new EventBusBean();
            beans.cod = "order";
            EventBus.getDefault().post(beans);
            if (!TextUtils.isEmpty(orderNo)) {
                openActivity(new Intent(context, PayFinishActivity.class).putExtra("orderno", orderNo));
            }
            finish();
        }

        if (bean.cod.equals("bank")) {
            flag = true;
        }
    }

}
