package com.jiefutong.emall.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.jiefutong.emall.R;


/**
 * Created by xcl on 2017/8/2.
 */

public class CircleProgres extends Dialog {

    private LottieAnimationView lav;

    public CircleProgres(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.setContentView(R.layout.circle_pro);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCancelable(false);
    }


    public CircleProgres(@NonNull Context context) {
        this(context, R.style.circle_pro);
        initview(context);
    }

    private void initview(Context context) {
        View view = View.inflate(context, R.layout.circle_pro, null);
        lav = (LottieAnimationView) view.findViewById(R.id.lav);

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return true;
            }
        });
    }
}
