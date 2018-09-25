package com.jiefutong.emall.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jiefutong.emall.R;


/**
 * 图片选择底部dialog工具类
 * @Author l
 * @Date 2018/3/16
 */

public class PhotoPickerDialogUtils {

    private TextView photopicker_tv_takephoto;
    private TextView photopicker_tv_choosephoto;
    private TextView photopicker_tv_cancel;
    private Dialog dialog;
    private View inflate;
    private Activity ctx;

    public PhotoPickerDialogUtils(Activity activity){
        ctx=activity;
    }
    /**
     * 显示选择图片的底部dialog
     */
    public void show() {
        dialog = new Dialog(ctx, R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(ctx).inflate(R.layout.dialog_bottom_photo_picker, null);
        photopicker_tv_takephoto = (TextView) inflate.findViewById(R.id.photopicker_tv_takephoto);
        photopicker_tv_choosephoto = (TextView) inflate.findViewById(R.id.photopicker_tv_choosephoto);
        photopicker_tv_cancel = (TextView) inflate.findViewById(R.id.photopicker_tv_cancel);
        photopicker_tv_takephoto.setOnClickListener((View.OnClickListener) ctx);
        photopicker_tv_choosephoto.setOnClickListener((View.OnClickListener) ctx);
        photopicker_tv_cancel.setOnClickListener((View.OnClickListener) ctx);

        dialog.setContentView(inflate);
        dialog.setCanceledOnTouchOutside(true);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.show();
        WindowManager windowManager = ctx.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp2 = dialog.getWindow().getAttributes();
        lp2.width = (int)(display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp2);
    }

    /**
     * 隐藏dialog
     */
    public void dismiss() {
        dialog.dismiss();
    }
}
