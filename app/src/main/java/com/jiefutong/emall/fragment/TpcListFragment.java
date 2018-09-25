package com.jiefutong.emall.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.lzy.okgo.OkGo;

/**
 * @Author l
 * @Date 2018/9/21
 */
public class TpcListFragment extends BaseFragment {
    private ImageView mIvBack;
    private TextView mTvTitle;

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void intiview(View view) {
        mIvBack = (ImageView) view.findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.GONE);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText("tpc专区");
    }

    @Override
    protected int getViewId() {
        return R.layout.main_fragment_mall;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
