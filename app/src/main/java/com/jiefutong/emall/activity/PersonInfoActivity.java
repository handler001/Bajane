package com.jiefutong.emall.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.HeaderBean;
import com.jiefutong.emall.bean.UserDetailsBean;
import com.jiefutong.emall.utils.FileUtil;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.PermissionUtil;
import com.jiefutong.emall.utils.PhotoPickerDialogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvHeader;
    private ImageView mIvNameEnter;
    private TextView mTvName;
    private RelativeLayout mRlName;
    private TextView mTvPhone;
    private RelativeLayout mRlPhone;
    private TextView mTvPwd;
    private RelativeLayout mRlPwd;
    private TextView mTvAli;
    private RelativeLayout mRlAli;
    private ImageView mIvHeaderEnter;
    private PhotoPickerDialogUtils dialogUtils;
    private TextView mTvPwdPay;
    private RelativeLayout mRlPwdPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        EventBus.getDefault().register(this);
        initView();
        settitlewhite();
        loaddata();
    }

    private void loaddata() {
        HttpUtils.getNetData(HttpUtils.USER_INFO, this, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                UserDetailsBean bean = JsonUtil.parseObject(data, UserDetailsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS && bean.dataMap != null) {
                    GlideUtils.loadCirclepic(context, mIvHeader, bean.dataMap.headImgUrl);
                    mTvName.setText(bean.dataMap.realName);
                    mTvPhone.setText(bean.dataMap.userMobile);
                    if (bean.dataMap.AlipayFlag) {
                        mTvAli.setText("已绑定");
                    } else {
                        mTvAli.setText("未绑定");
                    }
                    if (bean.dataMap.transPasswordFlag) {
                        mTvPwdPay.setText("已设置");
                    } else {
                        mTvPwdPay.setText("未设置");
                    }
                }
            }
        });
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("个人资料");
        mIvHeader = (ImageView) findViewById(R.id.iv_header);
        mIvHeader.setOnClickListener(this);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mRlName = (RelativeLayout) findViewById(R.id.rl_name);
        mRlName.setOnClickListener(this);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mRlPhone = (RelativeLayout) findViewById(R.id.rl_phone);
        mRlPhone.setOnClickListener(this);
        mTvPwd = (TextView) findViewById(R.id.tv_pwd);
        mRlPwd = (RelativeLayout) findViewById(R.id.rl_pwd);
        mRlPwd.setOnClickListener(this);
        mTvAli = (TextView) findViewById(R.id.tv_ali);
        mRlAli = (RelativeLayout) findViewById(R.id.rl_ali);
        mRlAli.setOnClickListener(this);
        mIvHeaderEnter = (ImageView) findViewById(R.id.iv_header_enter);
        mIvHeaderEnter.setOnClickListener(this);
        mTvPwdPay = (TextView) findViewById(R.id.tv_pwd_pay);
        mRlPwdPay = (RelativeLayout) findViewById(R.id.rl_pwd_pay);
        mRlPwdPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_header:
            case R.id.iv_header_enter:
                if (dialogUtils == null) {
                    dialogUtils = new PhotoPickerDialogUtils(this);
                }
                dialogUtils.show();
                break;
            case R.id.rl_name:
                openActivityForResult(NameChangeActivity.class, 100);
                break;
            case R.id.rl_pwd:
                openActivity(PwdChangeActivity.class);
                break;
            case R.id.rl_ali:
                openActivity(AliBindingActivity.class);
                break;
            case R.id.rl_pwd_pay:
                openActivity(TakePwdSetActivity.class);
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
            case R.id.rl_phone:
               // openActivity(PhoneBindActivity.class);
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

    private File fl;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && null != data) {
            fl = FileUtil.createFile();
            startPhotoZoom(data.getData(),
                    Uri.fromFile(fl));

        }
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (tmpFile != null && tmpFile.length() > 0) {
                fl = FileUtil.createFile();
                startPhotoZoom(sourceUri,
                        Uri.fromFile(fl));
            }
        }
        if (requestCode == 300 && resultCode == -1 && fl.length() > 0) {
            changeHeader();
        }

        if (requestCode == 100 && resultCode == 200 && data != null) {
            String name = data.getStringExtra("name");
            mTvName.setText(name);
            EventBus.getDefault().post(name);
        }
    }

    public void startPhotoZoom(Uri uri, Uri destUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 480);
        intent.putExtra("noFaceDetection", true); //关闭人脸检測
        intent.putExtra("return-data", false);//假设设为true则返回bitmap
        intent.putExtra(MediaStore.EXTRA_OUTPUT, destUri);//输出文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 300);
    }

    private void changeHeader() {
        HttpParams params = new HttpParams();
        params.put("attach", fl);
        params.put("type", "1");
        HttpUtils.getNetData(HttpUtils.INFO_HEADER_CHANGE, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                HeaderBean bean = JsonUtil.parseObject(data, HeaderBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    GlideUtils.loadCirclepic(context, mIvHeader, fl.getAbsolutePath());
                    EventBus.getDefault().post(bean.dataMap.domainImgUrl);
                } else {
                    showToast("头像修改失败");
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean bean) {
        if (bean.cod.equals("bank")) {
            loaddata();
        }
    }
}
