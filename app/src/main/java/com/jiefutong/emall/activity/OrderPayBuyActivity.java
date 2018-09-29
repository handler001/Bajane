package com.jiefutong.emall.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.utils.FileUtil;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.PhotoPickerDialogUtils;

import java.io.File;

public class OrderPayBuyActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvPicSelect;
    private Button mBtSubmit;
    private PhotoPickerDialogUtils dialogUtils;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_buy);
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("待确认");
        mIvPicSelect = (ImageView) findViewById(R.id.iv_pic_select);
        mIvPicSelect.setOnClickListener(this);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);

        mBtSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_pic_select:
                if (dialogUtils == null) {
                    dialogUtils = new PhotoPickerDialogUtils(this);
                }
                dialogUtils.show();
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
            sourceUri = FileProvider.getUriForFile(this, "com.jiefutong.emall.fileprovider", tmpFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            sourceUri = Uri.fromFile(tmpFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, sourceUri);
        startActivityForResult(intent, 100);
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
            img = c.getString(columnIndex);
            GlideUtils.loadpic(context, mIvPicSelect, img);
            c.close();

        }
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (tmpFile != null && tmpFile.length() > 0) {
                img = tmpFile.getAbsolutePath();
                GlideUtils.loadpic(context, mIvPicSelect, img);
            }
        }
    }
}
