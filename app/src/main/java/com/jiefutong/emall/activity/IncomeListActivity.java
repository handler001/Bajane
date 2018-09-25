package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.fragment.BaseFragment;
import com.jiefutong.emall.fragment.IncomeListFragment;
import com.jiefutong.emall.utils.HttpUtils;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IncomeListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private ImageView mIvMessage;
    private TextView mTvTitle;
    private ImageView mIvCar;
    private TextView mTvRight;
    private TabIndicator mTab;
    private ViewPager mVpContent;
    private ArrayList<String> titles = new ArrayList<>();
    public Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_list);
        initView();
        settitlewhite();
    }

    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        titles.add("日收益");
        titles.add("周收益");
        titles.add("月收益");
        titles.add("年收益");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mIvMessage = (ImageView) findViewById(R.id.iv_message);
        mIvMessage.setVisibility(View.GONE);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("收益榜");
        mIvCar = (ImageView) findViewById(R.id.iv_car);
        mIvCar.setVisibility(View.GONE);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("说明");
        mTvRight.setOnClickListener(this);
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
            case R.id.tv_right:
                openActivity(new Intent(context, WebViewActivity.class).putExtra("url", HttpUtils.INCOME_LIST_EXPLAIN_PAGE));
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
                fragment = new IncomeListFragment(position,type);
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
