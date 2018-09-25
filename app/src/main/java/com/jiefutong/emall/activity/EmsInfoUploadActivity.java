package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;

public class EmsInfoUploadActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtName;
    private EditText mEtOrder;
    private ImageView mIvCode;
    private TextView mTvSubmit;
    private String orderno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ems_info_upload);
        initView();
        settitlewhite();
    }

    private void initView() {
        orderno = getIntent().getStringExtra("order");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("上传物流信息");
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtOrder = (EditText) findViewById(R.id.et_order);
        mIvCode = (ImageView) findViewById(R.id.iv_code_ems);
        mIvCode.setOnClickListener(this);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
    }

    private void submit() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入物流名称");
            return;
        }

        String order = mEtOrder.getText().toString().trim();
        if (TextUtils.isEmpty(order)) {
            showToast("请输入订单号");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("orderNo", orderno);
        params.put("postageNo", order);
        params.put("postageName", name);
        HttpUtils.getNetData(HttpUtils.REFUND_EMS_INFO, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    finish();
                    showToast("物流信息上传成功");
                } else {
                    showToast("物流信息上传失败,请重试");
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
            case R.id.iv_code_ems:
                new PermissionUtil(context, PermissionUtil.CAMERA) {
                    @Override
                    public void onsuccess() {
                        Intent intent = new Intent(context, CaptureActivity.class);
                        openActivityForResult(intent, 100);
                    }
                };
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 100) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    mEtOrder.setText(result);
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    showToast("解析二维码失败");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
