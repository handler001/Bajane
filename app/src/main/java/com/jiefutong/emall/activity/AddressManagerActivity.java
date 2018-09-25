package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AddressInfosBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class AddressManagerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvAddress;
    private TextView mTvAdd;
    private ArrayList<AddressInfosBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private boolean select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);
        settitlewhite();
        initView();
    }

    private void initView() {
        select = getIntent().getBooleanExtra("select", false);
        View view = View.inflate(this, R.layout.view_empty, null);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("地址管理");
        mRlvAddress = (RecyclerView) findViewById(R.id.rlv_address);
        mRlvAddress.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressAdapter(R.layout.item_address_select, datas);
        mRlvAddress.setAdapter(adapter);
        mTvAdd = (TextView) findViewById(R.id.tv_add);
        mTvAdd.setOnClickListener(this);
        adapter.setEmptyView(view);
        getaddresslist();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                openActivityForResult(new Intent(this, AddressChangeActivity.class)
                                .putExtra("name", "地址添加")
                        , 100);
                break;
        }
    }

    private class AddressAdapter extends BaseQuickAdapter<AddressInfosBean.DataMapBean.ContentBean, BaseViewHolder> {

        public AddressAdapter(int layoutResId, @Nullable List<AddressInfosBean.DataMapBean.ContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final AddressInfosBean.DataMapBean.ContentBean item) {
            if (item.defaultAddr) {
                helper.setBackgroundRes(R.id.iv_select, R.mipmap.btn_paystyle_selected);
            } else {
                helper.setBackgroundRes(R.id.iv_select, R.mipmap.btn_paystyle_default);
            }
            helper.setText(R.id.tv_name, item.receiveName)
                    .setText(R.id.tv_phone, item.receiveTel)
                    .setText(R.id.tv_address, item.receiveProvince+item.receiveCity+item.receiveCountry+item.receiveDetail);
            helper.getView(R.id.iv_select).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!item.defaultAddr) {
                        changeaddress(item.id);
                    }
                }
            });

            helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivityForResult(new Intent(AddressManagerActivity.this, AddressChangeActivity.class)
                            .putExtra("name", "地址修改")
                            .putExtra("bean", item), 100);
                }
            });

            helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DiaglogUtils(AddressManagerActivity.this, "是否要删除该地址") {
                        @Override
                        public void sureMessage() {
                            deladdress(item.id);
                        }
                    };
                }
            });
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (select) {
                        setResult(100, new Intent().putExtra("bean", item));
                        finish();
                    }
                }
            });
        }
    }

    private void changeaddress(String id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        HttpUtils.getNetData(HttpUtils.ADDRESS_INFO_CHANGE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    getaddresslist();
                } else {
                    showToast("设置失败");
                }
            }
        });
    }

    private void deladdress(String id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        HttpUtils.getNetData(HttpUtils.ADDRESS_INFO_DELETE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    getaddresslist();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            getaddresslist();
        }
    }

    private void getaddresslist() {
        HttpUtils.getNetData(HttpUtils.ADDRESS_INFO_LIST, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                AddressInfosBean bean = JsonUtil.parseObject(data, AddressInfosBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.setNewData(bean.dataMap.content);
                } else {
                    showToast("获取失败");
                }
            }
        });
    }

}
