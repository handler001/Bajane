package com.jiefutong.emall.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.BaseActivity;
import com.jiefutong.emall.activity.MapLocationActivity;
import com.jiefutong.emall.bean.GoodsShopBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.ResourceUtils;
import com.jiefutong.emall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/11
 */
public class GoodsShopAdapter extends BaseQuickAdapter<GoodsShopBean.DataMapBean.ContentBean, BaseViewHolder> {
    private int type;
    private Context ctx;

    public GoodsShopAdapter(int layoutResId, @Nullable List<GoodsShopBean.DataMapBean.ContentBean> data, Context ctx, int type) {
        super(layoutResId, data);
        this.ctx = ctx;
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsShopBean.DataMapBean.ContentBean item) {
        GlideUtils.loadpic(ctx, (ImageView) helper.getView(R.id.iv_pic), item.imgUrl);
        helper.setText(R.id.tv_name, item.shopName)
                .setText(R.id.tv_address, item.shopAdress)
                .setText(R.id.tv_phone, item.shopTel);
        helper.getView(R.id.tv_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(item);
            }
        });
        helper.getView(R.id.tv_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(item.shopTel);
            }
        });
        if (type == 1) {
            helper.getView(R.id.tv_select).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_select).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) ctx;
                    activity.setResult(200, new Intent().putExtra("bean", item));
                    activity.finish();
                }
            });
        } else {
            helper.getView(R.id.tv_select).setVisibility(View.GONE);
        }
    }

    private void openMap(final GoodsShopBean.DataMapBean.ContentBean item) {
        new PermissionUtil(mContext, PermissionUtil.LOCATION) {
            @Override
            public void onsuccess() {
                Intent intent = new Intent(ctx, MapLocationActivity.class);
                intent.putExtra("lat", item.lat);
                intent.putExtra("lng", item.lng);
                intent.putExtra("name", item.shopName);
                ctx.startActivity(intent);
            }
        };
    }

    private void callPhone(final String phone) {
        new PermissionUtil(ctx, PermissionUtil.PHONE) {
            @Override
            public void onsuccess() {
                new DiaglogUtils(ctx, ResourceUtils.getString(mContext, R.string.phone) + phone) {
                    @Override
                    public void sureMessage() {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(intent);
                    }
                };
            }
        };
    }
}
