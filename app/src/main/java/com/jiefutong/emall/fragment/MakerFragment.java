package com.jiefutong.emall.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.PinDuoDuoActivity;
import com.jiefutong.emall.activity.WebViewActivity;
import com.jiefutong.emall.utils.HttpUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

/**
 * @Author l
 * @Date 2018/9/21
 */
public class MakerFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mIvFm;
    private PercentRelativeLayout mRlSearch;
    private LinearLayout mLlPost;
    private LinearLayout mLlJhs;
    private LinearLayout mLlMz;
    private LinearLayout mLlMy;
    private LinearLayout mLlJj;
    private LinearLayout mLlNz;
    private LinearLayout mLlNvz;
    private LinearLayout mLlMore;
    private LinearLayout mLlJd;
    private LinearLayout mLlPdd;
    private LinearLayout mLlFz;
    private LinearLayout mLlWy;
    private LinearLayout mLlMother;
    private LinearLayout mLlMlh;
    private LinearLayout mLlQcs;
    private LinearLayout mLlTcly;

    @Override
    protected void listener() {
        mIvFm.setOnClickListener(this);
        mRlSearch.setOnClickListener(this);
        mLlPost.setOnClickListener(this);
        mLlJhs.setOnClickListener(this);
        mLlMz.setOnClickListener(this);
        mLlMy.setOnClickListener(this);
        mLlJj.setOnClickListener(this);
        mLlNz.setOnClickListener(this);
        mLlNvz.setOnClickListener(this);
        mLlMore.setOnClickListener(this);
        mLlJd.setOnClickListener(this);
        mLlPdd.setOnClickListener(this);
        mLlFz.setOnClickListener(this);
        mLlWy.setOnClickListener(this);
        mLlMother.setOnClickListener(this);
        mLlMlh.setOnClickListener(this);
        mLlQcs.setOnClickListener(this);
        mLlTcly.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void intiview(View view) {
        mIvFm = view.findViewById(R.id.iv_fm);
        mRlSearch = view.findViewById(R.id.rl_search);
        mLlPost = view.findViewById(R.id.ll_post);
        mLlJhs = view.findViewById(R.id.ll_jhs);
        mLlMz = view.findViewById(R.id.ll_mz);
        mLlMy = view.findViewById(R.id.ll_my);
        mLlJj = view.findViewById(R.id.ll_jj);
        mLlNz = view.findViewById(R.id.ll_nz);
        mLlNvz = view.findViewById(R.id.ll_nvz);
        mLlMore = view.findViewById(R.id.ll_more);
        mLlJd = view.findViewById(R.id.ll_jd);
        mLlPdd = view.findViewById(R.id.ll_pdd);
        mLlFz = view.findViewById(R.id.ll_fz);
        mLlWy = view.findViewById(R.id.ll_wy);
        mLlMother = view.findViewById(R.id.ll_mother);
        mLlMlh = view.findViewById(R.id.ll_mlh);
        mLlQcs = view.findViewById(R.id.ll_qcs);
        mLlTcly = view.findViewById(R.id.ll_tcly);

    }

    @Override
    protected int getViewId() {
        return R.layout.frag_maker_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fm:
                openActivity(new Intent(mctx, WebViewActivity.class).putExtra("url", HttpUtils.RETURN_MONEY_TEACH));
                break;
            case R.id.rl_search:
                showToast("开发中");
                break;
            case R.id.ll_pdd:
                openActivity(PinDuoDuoActivity.class);
                break;
        }
    }
}
