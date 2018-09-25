package com.jiefutong.emall.utils;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.jiefutong.emall.fragment.ActListFragment;
import com.jiefutong.emall.fragment.HomePageFragment;
import com.jiefutong.emall.fragment.MakerFragment;
import com.jiefutong.emall.fragment.MallCategoryDetailFragment;
import com.jiefutong.emall.fragment.MallListFragment;
import com.jiefutong.emall.fragment.MessageFragment;
import com.jiefutong.emall.fragment.PddFragment;
import com.jiefutong.emall.fragment.PersonCenterFragment;
import com.jiefutong.emall.fragment.TpcListFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author l
 * @Date 2018/7/10
 */
public class FragmentFactory {
    public static Map<Integer, Fragment> fragments = new HashMap<Integer, Fragment>();
    public static Map<Integer, Fragment> categorys = new HashMap<Integer, Fragment>();

    public static Fragment create(int position) {
        // 1. 先去内存中判断 是否存在fragment对象
//            1.存在,直接返回
//            2.不存在, 创建fragment对象 ,添加到内存中 ,再返回回去
        Fragment fragment = fragments.get(position);
        if (fragment != null) {
            return fragment;
        }
        if (position == 0) {
            fragment = new HomePageFragment();
        } else if (position == 1) {
            // fragment = new ActListFragment();
            // fragment = new MallListFragment();
            //fragment = new PddFragment();
            fragment = new MakerFragment();
        } else if (position == 2) {
            fragment = new TpcListFragment();
        } else if (position == 3) {
            fragment = new MessageFragment();
        } else {
            fragment = new PersonCenterFragment();
        }

        if (fragment != null) {
            fragments.put(position, fragment);
        }
        return fragment;
    }

    public static Fragment createCategory(int position, Context ctx, String bean) {
        // 1. 先去内存中判断 是否存在fragment对象
//            1.存在,直接返回
//            2.不存在, 创建fragment对象 ,添加到内存中 ,再返回回去
        Fragment fragment = categorys.get(position);
        if (fragment != null) {
            return fragment;
        }
        fragment = new MallCategoryDetailFragment(ctx, bean);
        if (fragment != null) {
            categorys.put(position, fragment);
        }
        return fragment;

    }

    public static void clear() {
        fragments.clear();
        categorys.clear();
    }
}
