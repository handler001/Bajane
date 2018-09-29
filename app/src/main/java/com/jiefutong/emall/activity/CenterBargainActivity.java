package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.fragment.BargainHistoryFragment;
import com.jiefutong.emall.fragment.BaseFragment;
import com.jiefutong.emall.fragment.OrderListFragment;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CenterBargainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TabIndicator mTab;
    private ViewPager mVpContent;
    private TextView mTvOrderCreat;
    private TextView mTvMyOrder;
    private int type;
    private ArrayList<String> titles = new ArrayList<>();
    public Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_bargain);
        type = getIntent().getIntExtra("type", 0);
        initView();
        settitlewhite();
    }

    private void initView() {
        titles.add("买入");
        titles.add("卖出");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("交易");
        mTab = (TabIndicator) findViewById(R.id.tab);
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mVpContent.setAdapter(new CusAdapter(getSupportFragmentManager()));
        mTab.setViewPagerSwitchSpeed(mVpContent, 600);
        mTab.setTabData(mVpContent, titles, new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int i) {
                mVpContent.setCurrentItem(i);
                type = i;
            }
        });
        mVpContent.setCurrentItem(type);
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                type = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTvOrderCreat = (TextView) findViewById(R.id.tv_order_creat);
        mTvOrderCreat.setOnClickListener(this);
        mTvMyOrder = (TextView) findViewById(R.id.tv_my_order);
        mTvMyOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_order_creat:
                openActivity(new Intent(context, BusinessDealActivity.class).putExtra("type", type));
                break;
            case R.id.tv_my_order:
                openActivity(TpcOrderMeActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragments.clear();
    }

    class CusAdapter extends FragmentPagerAdapter {

        public CusAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = fragments.get(position);
            if (fragment == null) {
                fragment = new BargainHistoryFragment(position);
                fragments.put(position, fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.size();
        }
    }
}
