package com.jiefutong.emall.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AreaCityBean;
import com.jiefutong.emall.bean.BankCardBean;
import com.jiefutong.emall.bean.BankCodeBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lljjcoder.style.citythreelist.CityAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class BankCardAddActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtName;
    //  private EditText mEtCardNumber;
    private EditText mEtBankNumber;
    private TextView mEtBankAddress;
    // private EditText mEtPhone;
    private TextView mTvSubmit;
    private ArrayList<BankCodeBean.DataMapBean> dataMapBeans = new ArrayList<>();
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_add);
        settitlewhite();
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("银行卡添加");
        mEtName = (EditText) findViewById(R.id.et_name);
        // mEtCardNumber = (EditText) findViewById(R.id.et_card_number);
        mEtBankNumber = (EditText) findViewById(R.id.et_bank_number);
        mEtBankAddress = (TextView) findViewById(R.id.et_bank_address);
        mEtBankAddress.setOnClickListener(this);
        // mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
    }

    private void submit() {

        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入姓名");
            return;
        }

//        String number = mEtCardNumber.getText().toString().trim();
//        if (TextUtils.isEmpty(number)) {
//            showToast("请输入身份证号码");
//            return;
//        }

        String bank = mEtBankNumber.getText().toString().trim();
        if (TextUtils.isEmpty(bank)) {
            showToast("输入银行卡卡号");
            return;
        }
        if (TextUtils.isEmpty(id)) {
            showToast("请输入发卡行名称");
            return;
        }

//        String phone = mEtPhone.getText().toString().trim();
//        if (TextUtils.isEmpty(phone)) {
//            showToast("输入银行预留手机号码");
//            return;
//        }
        HttpParams params = new HttpParams();
        params.put("realName", name);
        params.put("bankCardNo", bank);
        params.put("bankCode", id);
        HttpUtils.getNetData(HttpUtils.ADD_BANK_CARD, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        setResult(100);
                        EventBusBean eventBusBean = new EventBusBean();
                        eventBusBean.cod = "bank";
                        EventBus.getDefault().post(eventBusBean);
                        finish();
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showToast("添加失败,请重试");
                }
            }
        });

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
            case R.id.et_bank_address:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEtBankAddress.getWindowToken(), 0);
                if (dataMapBeans.size() == 0) {
                    getBankCode();
                } else {
                    showpop();
                }
                break;
        }
    }

    private void getBankCode() {
        HttpUtils.getNetData(HttpUtils.BANK_CARD_CODE_LIST, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                BankCodeBean bean = JsonUtil.parseObject(data, BankCodeBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    dataMapBeans.addAll(bean.dataMap);
                    showpop();
                } else {
                    showToast("获取列表失败");
                }
            }
        });
    }

    private PopupWindow pop;

    private void showpop() {
        if (pop == null) {
            View view = View.inflate(this, R.layout.layout_city_item_select, null);
            RecyclerView rlv = view.findViewById(R.id.rlv_city);
            rlv.setLayoutManager(new GridLayoutManager(context, 2));
            rlv.setAdapter(new BankAdapter(R.layout.item_city_name_area, dataMapBeans));
            pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pop.setFocusable(true);
            pop.setOutsideTouchable(true);
            pop.setBackgroundDrawable(new BitmapDrawable());
            setBackgroundAlpha(0.4f);
            pop.showAtLocation(mEtBankAddress, Gravity.CENTER, 0, 0);
            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1f);
                }
            });
        } else {
            pop.showAtLocation(mEtBankAddress, Gravity.CENTER, 0, 0);
        }
    }

    private class BankAdapter extends BaseQuickAdapter<BankCodeBean.DataMapBean, BaseViewHolder> {
        public BankAdapter(int layoutResId, @Nullable List<BankCodeBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final BankCodeBean.DataMapBean item) {
            helper.setText(R.id.tv_city, item.wxBankName);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEtBankAddress.setText(item.wxBankName);
                    id = item.wxBankId;
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
