package com.jiefutong.emall.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.fragment.BaseFragment;
import com.jiefutong.emall.fragment.SalesAfterFragment;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SalesAfterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TabIndicator mTab;
    private ViewPager mVpContent;
    private ArrayList<String> titles = new ArrayList<>();
    public Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_after);
        initView();
        settitlewhite();
    }

    private void initView() {
        titles.add("退款中");
        titles.add("完成退款");
        titles.add("拒绝退款");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("退款/售后");
        mTab = (TabIndicator) findViewById(R.id.tab);
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mVpContent.setAdapter(new CusAdapter(getSupportFragmentManager()));
        mTab.setViewPagerSwitchSpeed(mVpContent, 600);
        mTab.setTabData(mVpContent, titles, new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int i) {
                mVpContent.setCurrentItem(i);
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

    class CusAdapter extends FragmentPagerAdapter {

        public CusAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = fragments.get(position);
            if (fragment == null) {
                fragment = new SalesAfterFragment(position);
                fragments.put(position, fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragments.clear();
    }
}
