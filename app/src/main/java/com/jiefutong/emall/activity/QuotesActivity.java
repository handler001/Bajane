package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.fragment.BaseFragment;
import com.jiefutong.emall.fragment.OrderListFragment;
import com.jiefutong.emall.fragment.QuotesListFragment;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuotesActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvSearch;
    private TabIndicator mTab;
    private ViewPager mVpContent;
    private ArrayList<String> titles = new ArrayList<>();
    public Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        initView();
        settitlewhite();
    }

    private void initView() {
        titles.add("TPC");
        titles.add("USDT");
        titles.add("BTC");
        titles.add("ETH");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("市场");
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mIvSearch.setVisibility(View.VISIBLE);
        mIvSearch.setOnClickListener(this);
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
        mVpContent.setCurrentItem(0);
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
                fragment = new QuotesListFragment(position);
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
