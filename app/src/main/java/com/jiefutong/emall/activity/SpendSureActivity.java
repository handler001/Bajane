package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.GoodsBean;
import com.jiefutong.emall.bean.InfoCodeBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SpendSureActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvSubmit;
    private InfoCodeBean.DataMapBean dataMapBean;
    private TextView mTvOrderCode;
    private TextView mTvOrderState;
    private RecyclerView mPrlCenter;
    private TextView mTvOrderPrice;
    private TextView mTvNum;
    private TextView mTvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_sure);
        dataMapBean = (InfoCodeBean.DataMapBean) getIntent().getSerializableExtra("bean");
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("确认消费");
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
        mTvOrderCode = (TextView) findViewById(R.id.tv_order_code);
        mTvOrderState = (TextView) findViewById(R.id.tv_order_state);
        mPrlCenter = (RecyclerView) findViewById(R.id.prl_center);
        mPrlCenter.setLayoutManager(new LinearLayoutManager(context));
        mTvOrderPrice = (TextView) findViewById(R.id.tv_order_price);
        mTvNum = (TextView) findViewById(R.id.tv_num_total);
        mTvCode = (TextView) findViewById(R.id.tv_code);
        if (dataMapBean != null) {
            mTvOrderCode.setText("订单号:" + dataMapBean.orderNo);
            mTvCode.setText(dataMapBean.veriftyCode);
            if (dataMapBean.orderStatus == 4) {
                mTvOrderState.setText("待验证");
            } else {
                mTvOrderState.setText("已验证");
            }
            mPrlCenter.setAdapter(new InfoAdapter(R.layout.item_goods_info_count, dataMapBean.goods));
            mTvNum.setText("共" + dataMapBean.totalNum + "件商品  总计:");
            mTvOrderPrice.setText("￥" + dataMapBean.totalAmt);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    private void submit() {
        HttpParams params = new HttpParams();
        params.put("orderNo", dataMapBean.orderNo);
        params.put("veriftyCode", dataMapBean.veriftyCode);
        HttpUtils.getNetData(HttpUtils.MY_SHOP_CODE_CHECK, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    showToast(bean.message);
                    EventBusBean busBean = new EventBusBean();
                    busBean.cod = "check";
                    EventBus.getDefault().post(busBean);
                    finish();
                } else {
                    showToast("验证失败,请重试");
                }
            }
        });

    }

    class InfoAdapter extends BaseQuickAdapter<InfoCodeBean.DataMapBean.GoodsBean, BaseViewHolder> {

        public InfoAdapter(int layoutResId, @Nullable List<InfoCodeBean.DataMapBean.GoodsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, InfoCodeBean.DataMapBean.GoodsBean item) {
            GlideUtils.loadpic(mContext, (ImageView) helper.getView(R.id.iv_pic), item.cover);
            helper.setText(R.id.tv_name, item.goodsName)
                    .setText(R.id.tv_price, "￥" + item.goodsPrice)
                    .setText(R.id.tv_count, "x" + item.goodsNum);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
