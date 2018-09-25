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
import com.jiefutong.emall.fragment.BalanceDetailsFragment;
import com.jiefutong.emall.fragment.BaseFragment;
import com.jiefutong.emall.fragment.SalesAfterFragment;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BalanceDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TabIndicator mTab;
    private ViewPager mVpContent;
    private ArrayList<String> titles = new ArrayList<>();
    public Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_details);
        type = getIntent().getIntExtra("type", 0);
        initView();
        settitlewhite();
    }

    private void initView() {
        titles.add("全部");
        titles.add("收入");
        titles.add("支出");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        if (type == 0) {
            mTvTitle.setText("金币明细");
        } else if (type == 1) {
            mTvTitle.setText("银币明细");

        } else if (type == 2) {
            mTvTitle.setText("积分明细");

        } else if (type == 3) {
            mTvTitle.setText("兑换券明细");
        } else {
            mTvTitle.setText("余额明细");
        }
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
                fragment = new BalanceDetailsFragment(position,type);
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
