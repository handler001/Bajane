package com.jiefutong.emall.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx7a2905373c997b22");
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = baseResp.errCode;
            switch (code) {
                case 0:
                    ToastUtil.show(WXEntryActivity.this, "支付成功");
                    EventBusBean beans = new EventBusBean();
                    beans.cod = "success";
                    EventBus.getDefault().post(beans);
                    finish();
                    break;
                case -1:
                    ToastUtil.show(WXEntryActivity.this, "支付失败");
                    finish();
                    break;
                case -2:
                    ToastUtil.show(WXEntryActivity.this, "支付取消");
                    finish();
                    break;
                default:
                    ToastUtil.show(WXEntryActivity.this, "支付失败");
                    finish();
                    break;
            }
        }
    }
}