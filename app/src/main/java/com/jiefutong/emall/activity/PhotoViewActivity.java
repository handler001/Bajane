package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.widget.HackyViewPager;


import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private HackyViewPager mVpContent;
    private ArrayList<String> datas;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        settitleColor(R.color.black);
        initView();
    }

    private void initView() {
        pos = getIntent().getIntExtra("num", 0);
        datas = getIntent().getStringArrayListExtra("data");
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText((pos + 1) + "/" + datas.size());
        mVpContent = findViewById(R.id.vp_content);
        mVpContent.setAdapter(new PhotoAdapter());
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvTitle.setText((position + 1) + "/" + datas.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVpContent.setCurrentItem(pos);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    private class PhotoAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(context, R.layout.item_pic_photo, null);
//            PhotoView photoView = view.findViewById(R.id.pv);
            //    GlideUtils.loadpic(context, photoView, datas.get(position));
            ImageView imageView = view.findViewById(R.id.pv);
            GlideUtils.loadpic(context, imageView, datas.get(position));
            PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
            mAttacher.update();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
