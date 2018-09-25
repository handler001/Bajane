package com.jiefutong.emall.utils;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;

import java.lang.reflect.Field;

/**
 * Created by xcl on 2017/7/25.
 */

public class BottomNavigationViewHelper {
    private static int[] nor = new int[]{R.mipmap.btn_tab_home_nor, R.mipmap.btn_tab_hd_nor, R.mipmap.btn_tab_tpc_nor, R.mipmap.btn_tab_news, R.mipmap.btn_tab_me_nor};
    private static int[] sel = new int[]{R.mipmap.btn_tab_home_sel, R.mipmap.btn_tab_hd_sel, R.mipmap.btn_tab_tpc_sel, R.mipmap.btn_tab_news_sel, R.mipmap.btn_tab_me_sel};

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
                TextView small = item.findViewById(R.id.smallLabel);
                TextView lar = item.findViewById(R.id.largeLabel);
                ImageView mIcon = item.findViewById(R.id.icon);
                small.setTextSize(10);
                lar.setTextSize(12);
                if (item.getItemData().isChecked()) {
                    mIcon.setBackgroundResource(sel[i]);
                } else {
                    mIcon.setBackgroundResource(nor[i]);
                }
            }
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
    }
}
