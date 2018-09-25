package com.jiefutong.emall.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.GoodsReturnAdapter;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.GoodsBean;
import com.jiefutong.emall.bean.RefundPriceBean;
import com.jiefutong.emall.utils.FileUtil;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.PhotoPickerDialogUtils;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReturnActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvSave;
    private TextView mTvReturnMoney;
    private TextView mTvReturn;
    private LinearLayout mLlMoney;
    private EditText mEtInfo;
    private EditText mEtPhone;
    private RecyclerView mRlvPic;
    private PhotoPickerDialogUtils dialogUtils;
    private ArrayList<String> newpics = new ArrayList<>();
    private ArrayList<File> newfiles = new ArrayList<>();
    private PicAdapter adapter;
    private RecyclerView mRlv;
    private TextView mTvMoneyNum;
    private ArrayList<GoodsBean> datas;
    private String price, postamt;
    private int position = 1;
    private String orders="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        initView();
        settitlewhite();
        loaddata();
    }

    private void initView() {
        datas = getIntent().getParcelableArrayListExtra("data");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("退款/售后");
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mTvSave.setVisibility(View.VISIBLE);
        mTvSave.setText("提交");
        mTvSave.setOnClickListener(this);
        mTvReturnMoney = (TextView) findViewById(R.id.tv_return_money);
        mTvReturnMoney.setOnClickListener(this);
        mTvReturn = (TextView) findViewById(R.id.tv_return);
        mTvReturn.setOnClickListener(this);
        mLlMoney = (LinearLayout) findViewById(R.id.ll_money);
        mEtInfo = (EditText) findViewById(R.id.et_info);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mRlvPic = (RecyclerView) findViewById(R.id.rlv_pic);
        mRlvPic.setLayoutManager(new GridLayoutManager(context, 4));
        newpics.add("");
        adapter = new PicAdapter(R.layout.item_order_pic_select, newpics);
        mRlvPic.setAdapter(adapter);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mRlv.setAdapter(new GoodsReturnAdapter(R.layout.item_return_goods, datas, context));
        mTvMoneyNum = (TextView) findViewById(R.id.tv_money_num);
        for (int i = 0; i < datas.size(); i++) {
            orders += datas.get(i).splitNo + ",";
        }
    }

    private void loaddata() {
        HttpParams params = new HttpParams();
        Log.i("sss", "loaddata: "+orders);
        params.put("splitNo", orders.substring(0, orders.length() - 1));
        params.put("orderNo", datas.get(0).orderNo);
        HttpUtils.getNetData(HttpUtils.REFUND_PRICE_GET, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                RefundPriceBean bean = JsonUtil.parseObject(data, RefundPriceBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    price = bean.dataMap.amt;
                    postamt = bean.dataMap.postage;
                    mTvMoneyNum.setText("￥" + price);
                } else {
                    showDataError();
                }
            }
        });
    }

    private void submit() {
        HttpParams params = new HttpParams();
        params.put("orderNo", datas.get(0).orderNo);
        params.put("refundType", String.valueOf(position));
        String info = mEtInfo.getText().toString().trim();
        if (!TextUtils.isEmpty(info)) {
            params.put("refundReason", info);
        }
        String phone = mEtPhone.getText().toString().trim();
        if (!TextUtils.isEmpty(phone)) {
            params.put("refundTel", phone);
        }
        params.put("postageAmt", postamt);
        params.put("splitNo", orders.substring(0, orders.length() - 1));
        params.put("refundAmt", price);
        if (newfiles.size() != 0) {
            params.putFileParams("files", newfiles);
        }
        HttpUtils.getNetData(HttpUtils.REFUND_SAVE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    showToast("提交成功");
                    EventBusBean eventBusBean = new EventBusBean();
                    eventBusBean.cod = "return";
                    EventBus.getDefault().post(eventBusBean);
                    finish();
                } else {
                    showToast("提交失败,请重试");
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
            case R.id.tv_save:
                if (!TextUtils.isEmpty(price)) {
                    submit();
                }
                break;
            case R.id.tv_return_money:
                dealposition(0);
                break;
            case R.id.tv_return:
                dealposition(1);
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
        }

    }

    private void dealposition(int i) {
        if (i == 0) {
            position = 1;
            mTvReturnMoney.setBackgroundResource(R.drawable.shape_orange_return_bg);
            mTvReturnMoney.setTextColor(getcolor(R.color.white));
            mTvReturn.setBackgroundResource(R.drawable.shape_gray_return_bg);
            mTvReturn.setTextColor(getcolor(R.color.text_shop_desc));
            mLlMoney.setVisibility(View.VISIBLE);
        } else {
            position = 2;
            mLlMoney.setVisibility(View.VISIBLE);
            mTvReturnMoney.setBackgroundResource(R.drawable.shape_gray_return_bg);
            mTvReturnMoney.setTextColor(getcolor(R.color.text_shop_desc));
            mTvReturn.setBackgroundResource(R.drawable.shape_orange_return_bg);
            mTvReturn.setTextColor(getcolor(R.color.white));
        }
    }

    class PicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public PicAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final int pos = helper.getLayoutPosition();
            ImageView iv = helper.getView(R.id.iv_pic);
            if (pos == 0) {
                helper.setGone(R.id.iv_cancle, false);
                GlideUtils.loadpicId(context, iv, R.mipmap.btn_addphototh);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (newfiles.size() >= 3) {
                            showToast("最多上传3张图片");
                            return;
                        }
                        if (dialogUtils == null) {
                            dialogUtils = new PhotoPickerDialogUtils(ReturnActivity.this);
                        }
                        dialogUtils.show();
                    }
                });
            } else {
                GlideUtils.loadpic(context, iv, item);
                helper.setGone(R.id.iv_cancle, true);
                helper.getView(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newpics.remove(pos);
                        newfiles.remove(pos - 1);
                        notifyDataSetChanged();
                    }
                });
            }
        }
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
        newfiles.add(fls);
        newpics.add(1, fls.getAbsolutePath());
        adapter.notifyDataSetChanged();
    }

    //压缩图片
    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
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
    }
}
