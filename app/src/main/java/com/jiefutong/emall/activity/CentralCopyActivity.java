package com.jiefutong.emall.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiefutong.emall.R;
import com.jiefutong.emall.bean.CentralCopyBean;
import com.jiefutong.emall.bean.CommonBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.jiefutong.emall.utils.QQWXUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CentralCopyActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlvContent;
    private ArrayList<CentralCopyBean.DataMapBean.ContentBean> datas = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private Dialog dialog;
    private int page = 1;
    private int curposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_copy);
        initView();
        settitlewhite();
        loaddata(page);
    }

    private void initView() {
        View view = View.inflate(context, R.layout.view_order_empty, null);
        TextView textView = view.findViewById(R.id.tv_info);
        textView.setText("暂无相关信息");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("中央文案");
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CentraCopyAdapter(R.layout.item_central_copy_list, datas);
        mRlvContent.setAdapter(adapter);
        adapter.setEmptyView(view);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loaddata(page);
            }
        }, mRlvContent);
    }

    private void loaddata(final int pages) {
        HttpParams params = new HttpParams();
        params.put("page", String.valueOf(pages));
        HttpUtils.getNetData(HttpUtils.CENTRA_COPY_LIST, this, params, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                adapter.loadMoreComplete();
                CentralCopyBean bean = JsonUtil.parseObject(data, CentralCopyBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    adapter.addData(bean.dataMap.content);
                    if (bean.dataMap.content.size() < 10) {
                        adapter.loadMoreEnd();
                    }
                    page++;
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                adapter.loadMoreFail();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private List<String> pics;

    private class CentraCopyAdapter extends BaseQuickAdapter<CentralCopyBean.DataMapBean.ContentBean, BaseViewHolder> {

        public CentraCopyAdapter(int layoutResId, @Nullable List<CentralCopyBean.DataMapBean.ContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final CentralCopyBean.DataMapBean.ContentBean item) {
            helper.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curposition = helper.getLayoutPosition();
                    if (item.imgList.size() == 1) {
                        dealOnepic(item.synopsis, item.imgList);
                    } else {
                        pics = item.imgList;
                        dealMorepic(item.synopsis);
                    }
                }
            });
            GlideUtils.loadCirclepic(context, (ImageView) helper.getView(R.id.iv_header), item.headImg);
            helper.setText(R.id.tv_name, item.title)
                    .setText(R.id.tv_time, item.createDate)
                    .setText(R.id.tv_share, String.valueOf(item.shareNum))
                    .setText(R.id.tv_desc, item.synopsis);
            ImageView imageView = helper.getView(R.id.iv_pic);
            RecyclerView recyclerView = helper.getView(R.id.rlv_pic);
            if (item.imgList.size() == 1) {
                imageView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                GlideUtils.loadpic(context, imageView, item.imgList.get(0));
            } else {
                imageView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
                recyclerView.setAdapter(new PicAdapter(R.layout.item_pic_select, item.imgList));
            }
        }
    }

    private void dealOnepic(String content, List<String> pic) {
        if (!TextUtils.isEmpty(content)) {
            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText(null, content));
            // 将文本内容放到系统剪贴板里。
            showToast("文案复制成功");
        }
        UMImage image = new UMImage(this, pic.get(0));
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                .withMedia(image)
                .setCallback(new ShareListener())//回调监听器
                .open();

    }

    private void dealMorepic(String content) {
        if (!TextUtils.isEmpty(content)) {
            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText(null, content));
            // 将文本内容放到系统剪贴板里。
            showToast("文案复制成功");
        }
        showdialog();
    }

    private void showdialog() {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
            View inflate = View.inflate(context, R.layout.layout_share_pic, null);
            inflate.findViewById(R.id.inCome_wxLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(0);
                }
            });
            inflate.findViewById(R.id.inCome_wxFriendLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(1);
                }
            });
            inflate.findViewById(R.id.inCome_qqLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(2);
                }
            });
            inflate.findViewById(R.id.inCome_qqZoneLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UMImage[] images = new UMImage[pics.size()];
                    for (int i1 = 0; i1 < pics.size(); i1++) {
                        UMImage image = new UMImage(context, pics.get(i1));
                        images[i1] = image;
                    }
                    new ShareAction(CentralCopyActivity.this)
                            .withMedias(images)
                            .setPlatform(SHARE_MEDIA.QZONE)
                            .withText("分享到")
                            .setCallback(new ShareListener())
                            .share();
                    dialog.dismiss();

                }
            });
            inflate.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setContentView(inflate);
            dialog.setCanceledOnTouchOutside(true);

            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialog.show();
            WindowManager windowManager = this.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp2 = dialog.getWindow().getAttributes();
            lp2.width = (int) (display.getWidth()); //设置宽度
            dialog.getWindow().setAttributes(lp2);
        } else {
            dialog.show();
        }
    }

    private void share(int i) {
        if (i == 0 || i == 1) {
            if (!QQWXUtils.isAppAvilible(context, "com.tencent.mm")) {
                showToast("您还没有安装微信");
                return;
            } else {
                dealshare(i);
            }
        } else {
            if (!QQWXUtils.isAppAvilible(context, "com.tencent.mobileqq")) {
                showToast("您还没有安装QQ");
                return;
            } else {
                dealshare(i);
            }
        }
        dialog.dismiss();
    }

    private void dealshare(final int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
                    for (int i1 = 0; i1 < pics.size(); i1++) {
                        File file = saveImageToSdCard(context, pics.get(i1));
                        Uri uri = Uri.fromFile(file);
                        imageUris.add(uri);
                    }
                    Intent intent = new Intent();
                    if (i == 0) {
                        intent.setPackage("com.tencent.mm");
                        intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                    } else if (i == 1) {
                        intent.setPackage("com.tencent.mm");
                        intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                    } else {
                        intent.setPackage("com.tencent.mobileqq");
                        intent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                    }
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intent.setType("image/*");
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                    intent.putExtra("Kdescription", "分享到");
                    startActivity(intent);
                    refreshadapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static String IMAGE_NAME = "iv_share_";
    public static int i = 0;

    //根据网络图片url路径保存到本地
    public final File saveImageToSdCard(Context context, String image) {
        boolean success = false;
        File file = null;
        try {
            file = createStableImageFile(context);

            Bitmap bitmap = null;
            URL url = new URL(image);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            FileOutputStream outStream;

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            return file;
        } else {
            return null;
        }
    }

    //创建本地保存路径
    public File createStableImageFile(Context context) throws IOException {
        i++;
        String imageFileName = IMAGE_NAME + i + ".jpg";
        File storageDir = context.getExternalCacheDir();
        File image = new File(storageDir, imageFileName);
        return image;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    class PicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private ArrayList<String> data;

        public PicAdapter(int layoutResId, @Nullable ArrayList<String> data) {
            super(layoutResId, data);
            this.data = data;
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final int pos = helper.getLayoutPosition();
            GlideUtils.loadpic(context, (ImageView) helper.getView(R.id.iv_pic), item);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(new Intent(context, PhotoViewActivity.class)
                            .putStringArrayListExtra("data", data)
                            .putExtra("num", pos));
                }
            });
        }
    }

    class ShareListener implements UMShareListener {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            refreshadapter();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (throwable.getMessage().contains("2008")) {
                showToast("没有安装应用");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    }

    private void refreshadapter() {
        final HttpParams httpParams = new HttpParams();
        httpParams.put("id", datas.get(curposition).id);
        HttpUtils.getNetData(HttpUtils.CENTRA_COPY_LIST_SHARE, this, httpParams, new MyStringCallBack(context) {
            @Override
            protected void dealdata(String data) {
                CommonBean bean = JsonUtil.parseObject(data, CommonBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    datas.get(curposition).shareNum += 1;
                    adapter.notifyItemChanged(curposition);
                }
            }
        });
    }
}
