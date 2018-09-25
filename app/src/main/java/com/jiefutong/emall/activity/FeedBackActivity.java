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
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.utils.FileUtil;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.PhotoPickerDialogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private EditText mEtQuestion;
    private RecyclerView mRlvPic;
    private EditText mEtPhone;
    private TextView mTvSubmit;
    private PhotoPickerDialogUtils dialogUtils;
    private ArrayList<String> newpics = new ArrayList<>();
    private ArrayList<File> newfiles = new ArrayList<>();
    private PicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("问题反馈");
        mEtQuestion = (EditText) findViewById(R.id.et_question);
        mRlvPic = (RecyclerView) findViewById(R.id.rlv_pic);
        mRlvPic.setLayoutManager(new GridLayoutManager(context, 4));
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
        newpics.add("");
        adapter = new PicAdapter(R.layout.item_pic_select, newpics);
        mRlvPic.setAdapter(adapter);
    }

    private void submit() {
        String question = mEtQuestion.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(question) && newfiles.size() == 0) {
            showToast("请选择要反馈的问题");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("problemText", question);
        params.put("userMobile", phone);
        params.putFileParams("files", newfiles);
        HttpUtils.getNetData(HttpUtils.PROBLEM_FEED_BACK, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    showToast("反馈成功");
                    finish();
                } else {
                    showToast("问题反馈失败,请重试");
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

    //图库
    private void processPhotos() {
        new PermissionUtil(context, PermissionUtil.PHOTO) {
            @Override
            public void onsuccess() {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
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
            sourceUri = FileProvider.getUriForFile(this, "com.jiefutong.emall.fileprovider", tmpFile);
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
                GlideUtils.loadpicId(context, iv, R.mipmap.bg_add_image);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogUtils == null) {
                            dialogUtils = new PhotoPickerDialogUtils(FeedBackActivity.this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
