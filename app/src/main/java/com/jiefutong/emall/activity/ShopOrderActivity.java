package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.OrderInfoAdapter;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.InfoCodeBean;
import com.jiefutong.emall.bean.OrderInfoBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ShopOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvEdit;
    private ImageView mIvCode;
    private TextView mTvCheckNot;
    private View mLineNot;
    private TextView mTvCheck;
    private View mLineCheck;
    private RecyclerView mRlvCheck;
    private RecyclerView mRlvCheckNot;
    private ArrayList<OrderInfoBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private ArrayList<OrderInfoBean.DataMapBean.ContentBean> mdatas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private BaseQuickAdapter madapter;
    private View view2;
    private View view1;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order);
        initView();
        settitlewhite();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("店铺订单");
        mIvEdit = (ImageView) findViewById(R.id.iv_edit);
        mIvEdit.setVisibility(View.VISIBLE);
        mIvEdit.setOnClickListener(this);
        mIvCode = (ImageView) findViewById(R.id.iv_code);
        mIvCode.setVisibility(View.VISIBLE);
        mIvCode.setOnClickListener(this);
        mTvCheckNot = (TextView) findViewById(R.id.tv_check_not);
        mTvCheckNot.setOnClickListener(this);
        mLineNot = (View) findViewById(R.id.line_not);
        mTvCheck = (TextView) findViewById(R.id.tv_check);
        mTvCheck.setOnClickListener(this);
        mLineCheck = (View) findViewById(R.id.line_check);
        //验证
        view1 = View.inflate(context, R.layout.view_order_empty, null);
        mRlvCheck = (RecyclerView) findViewById(R.id.rlv_check);
        mRlvCheck.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderInfoAdapter(R.layout.item_shop_oredr_info, datas, context, 0);
        mRlvCheck.setAdapter(adapter);
        adapter.setEmptyView(view1);
        //未验证
        view2 = View.inflate(context, R.layout.view_order_empty, null);
        mRlvCheckNot = (RecyclerView) findViewById(R.id.rlv_check_not);
        mRlvCheckNot.setLayoutManager(new LinearLayoutManager(this));
        madapter = new OrderInfoAdapter(R.layout.item_shop_oredr_info, mdatas, context, 0);
        mRlvCheckNot.setAdapter(madapter);
        madapter.setEmptyView(view2);
        dealPotion(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_check:
                dealPotion(1);
                break;
            case R.id.tv_check_not:
                dealPotion(0);
                break;
            case R.id.iv_code:
                new PermissionUtil(context, PermissionUtil.CAMERA) {
                    @Override
                    public void onsuccess() {
                        Intent intent = new Intent(context, CaptureActivity.class);
                        openActivityForResult(intent, 100);
                    }
                };
                break;
            case R.id.iv_edit:
                openActivity(CodeInputActivity.class);
                break;
        }
    }

    private void dealPotion(int i) {
        if (i == 0) {
            pos = 0;
            mTvCheckNot.setTextColor(getcolor(R.color.shop_red_seclect));
            mTvCheck.setTextColor(getcolor(R.color.shop_red_seclect_not));
            mRlvCheckNot.setVisibility(View.VISIBLE);
            mRlvCheck.setVisibility(View.GONE);
            mLineNot.setVisibility(View.VISIBLE);
            mLineCheck.setVisibility(View.INVISIBLE);
            if (mdatas.size() == 0) {
                getdata("4");
            }
        } else {
            pos = 1;
            mTvCheckNot.setTextColor(getcolor(R.color.shop_red_seclect_not));
            mTvCheck.setTextColor(getcolor(R.color.shop_red_seclect));
            mRlvCheckNot.setVisibility(View.GONE);
            mRlvCheck.setVisibility(View.VISIBLE);
            mLineNot.setVisibility(View.INVISIBLE);
            mLineCheck.setVisibility(View.VISIBLE);
            if (datas.size() == 0) {
                getdata("5");
            }
        }
    }

    private void getdata(final String type) {
        HttpParams params = new HttpParams();
        params.put("orderStatus", type);
        HttpUtils.getNetData(HttpUtils.SHOP_ORDER_LIST, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                OrderInfoBean bean = JsonUtil.parseObject(data, OrderInfoBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        if (type.equals("4")) {
                            madapter.setNewData(bean.dataMap.content);
                            if (bean.dataMap.content.size() != 0) {
                                mTvCheckNot.setText("未验证(" + bean.dataMap.content.size() + ")");
                            }
                        } else {
                            adapter.setNewData(bean.dataMap.content);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 100) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    checkcode(result);
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    showToast("解析二维码失败");
                }
            }
        }
    }

    private void checkcode(String result) {
        HttpParams params = new HttpParams();
        params.put("veriftyCode", result);
        HttpUtils.getNetData(HttpUtils.MY_SHOP_CODE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                InfoCodeBean bean = JsonUtil.parseObject(data, InfoCodeBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        openActivity(new Intent(context, SpendSureActivity.class).putExtra("bean", bean.dataMap));
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showToast("验证码信息错误");
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean bean) {
        if (bean.cod.equals("check")) {
            mdatas.clear();
            datas.clear();
            dealPotion(pos);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }
}
