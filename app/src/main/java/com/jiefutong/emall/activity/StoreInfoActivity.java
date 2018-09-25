package com.jiefutong.emall.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.AreaCityBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.ShopInfoBean;
import com.jiefutong.emall.utils.DiaglogUtils;
import com.jiefutong.emall.utils.FileUtil;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.PhotoPickerDialogUtils;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StoreInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvSave;
    private ImageView mIvPic;
    private EditText mEtName;
    private EditText mEtPhone;
    private TextView mEtAddress;
    private ShopInfoBean.DataMapBean dataMapBean;
    private PhotoPickerDialogUtils dialogUtils;
    private String picurl;
    private TextView mEtAddressArea;
    private ArrayList<AreaCityBean.DataMapBean> city = new ArrayList<>();
    private String areaid, addressname;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        settitlewhite();
        dataMapBean = (ShopInfoBean.DataMapBean) getIntent().getSerializableExtra("bean");
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("门店信息");
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mTvSave.setVisibility(View.VISIBLE);
        mTvSave.setOnClickListener(this);
        mIvPic = (ImageView) findViewById(R.id.iv_pic);
        mIvPic.setOnClickListener(this);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtAddress = findViewById(R.id.et_address);
        mEtAddress.setOnClickListener(this);
        mEtAddressArea = (TextView) findViewById(R.id.et_address_area);
        mEtAddressArea.setOnClickListener(this);
        if (dataMapBean != null) {
            mEtName.setText(dataMapBean.shopName);
            mEtPhone.setText(dataMapBean.shopTel);
            mEtAddress.setText(dataMapBean.shopAdress);
            mEtAddressArea.setText(dataMapBean.cityName);
            GlideUtils.loadpic(context, mIvPic, dataMapBean.imgUrl);
        }
    }

    private void submit() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入店铺名称");
            return;
        }

        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入门店电话");
            return;
        }

        String address = mEtAddressArea.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            showToast("请输入门店区域");
            return;
        }
        String details = mEtAddress.getText().toString().trim();
        if (TextUtils.isEmpty(details)) {
            showToast("请输入门店地址");
            return;
        }
        if (TextUtils.isEmpty(addressname)) {
            addressname = dataMapBean.shopAdress;
            lat = dataMapBean.lat;
            lng = dataMapBean.lng;
        }
        if (TextUtils.isEmpty(areaid)) {
            areaid = dataMapBean.cityId;
        }

        if (TextUtils.isEmpty(picurl)) {
            picurl = dataMapBean.imgUrl;
        }
        HttpParams params = new HttpParams();
        params.put("id", dataMapBean.id);
        params.put("shopName", name);
        params.put("shopAdress", addressname);
        params.put("shopTel", phone);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("cityId", areaid);
        params.put("imgUrl", picurl);
        HttpUtils.getNetData(HttpUtils.MY_SHOP_EDIT, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    setResult(100);
                    finish();
                } else {
                    showToast("修改失败");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (!TextUtils.isEmpty(picurl)) {
                    setResult(100);
                }
                finish();
                break;
            case R.id.iv_pic:
                if (dialogUtils == null) {
                    dialogUtils = new PhotoPickerDialogUtils(this);
                }
                dialogUtils.show();
                break;
            case R.id.tv_save:
                submit();
                break;
            case R.id.et_address:
                openActivityForResult(LocationSelectActivity.class, 100);
                break;
            case R.id.photopicker_tv_takephoto://相机
                processcamera();
                dialogUtils.dismiss();
                break;
            case R.id.photopicker_tv_choosephoto://图库
                processPhotos();
                dialogUtils.dismiss();
                break;
            case R.id.photopicker_tv_cancel://取消
                dialogUtils.dismiss();
                break;
            case R.id.et_address_area:
                if (pop == null) {
                    getcity();
                } else {
                    showPop();
                }
                break;
        }
    }

    private void getcity() {
        HttpUtils.getNetData(HttpUtils.AREA_LIST_INFO, this, new MyStringCallBack(this) {
            @Override
            protected void dealdata(String data) {
                AreaCityBean bean = JsonUtil.parseObject(data, AreaCityBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        city.addAll(bean.dataMap);
                        showPop();
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showDataError();
                }
            }
        });
    }

    private PopupWindow pop;

    private void showPop() {
        if (pop == null) {
            View view = View.inflate(this, R.layout.layout_city_item_select, null);
            RecyclerView rlv = view.findViewById(R.id.rlv_city);
            rlv.setLayoutManager(new LinearLayoutManager(context));
            rlv.setAdapter(new CityAdapter(R.layout.item_city_name_area, city));
            pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pop.setFocusable(true);
            pop.setOutsideTouchable(true);
            pop.setBackgroundDrawable(new BitmapDrawable());
            setBackgroundAlpha(0.4f);
            pop.showAtLocation(mEtAddressArea, Gravity.CENTER, 0, 0);
            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1f);
                }
            });
        } else {
            pop.showAtLocation(mEtAddressArea, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && null != data) {
            Uri uri = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(uri, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            File file = new File(imagePath);
            dealfile(file);
            c.close();
        }
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (tmpFile != null && tmpFile.length() > 0) {
                dealfile(tmpFile);
            }
        }

        if (requestCode == 100 && resultCode == 200 && data != null) {
            lat = data.getDoubleExtra("lat", dataMapBean.lat);
            lng = data.getDoubleExtra("lng", dataMapBean.lng);
            addressname = data.getStringExtra("address");
            mEtAddress.setText(addressname);
        }
    }

    //压缩图片
    private void dealfile(File file) {
        File fls = FileUtil.createFile();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), getBitmapOption(2));
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fls));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GlideUtils.loadpic(context, mIvPic, fls.getAbsolutePath());
        HttpParams params = new HttpParams();
        params.put("attach", fls);
        params.put("type", "2");
        HttpUtils.getNetData(HttpUtils.MY_SHOP_PIC_UOLOAD, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    picurl = bean.dataMap;
                    EventBusBean busBean = new EventBusBean();
                    busBean.cod = "pic";
                    EventBus.getDefault().post(busBean);
                }
            }
        });
    }

    //压缩图片
    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    //图库
    private void processPhotos() {
        new PermissionUtil(context, PermissionUtil.PHOTO) {
            @Override
            public void onsuccess() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
                openActivityForResult(intent, 200);
            }
        };
    }

    //拍照
    private void processcamera() {
        new PermissionUtil(context, PermissionUtil.CAMERA) {
            @Override
            public void onsuccess() {
                openCamera();
            }
        };
    }

    private File tmpFile;
    private Uri sourceUri;

    //打开相机
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
        tmpFile = FileUtil.createFile();
        if (Build.VERSION.SDK_INT >= 24) {
            sourceUri = FileProvider.getUriForFile(this, "com.jiefutong.bajane.fileprovider", tmpFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            sourceUri = Uri.fromFile(tmpFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, sourceUri);
        startActivityForResult(intent, 100);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !TextUtils.isEmpty(picurl)) {
            setResult(100);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class CityAdapter extends BaseQuickAdapter<AreaCityBean.DataMapBean, BaseViewHolder> {
        public CityAdapter(int layoutResId, @Nullable List<AreaCityBean.DataMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final AreaCityBean.DataMapBean item) {
            helper.setText(R.id.tv_city, item.areaName);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEtAddressArea.setText(item.areaName);
                    areaid = item.areaId;
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                    }
                }
            });
        }
    }


    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
