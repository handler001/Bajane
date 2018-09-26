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

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void intiview(View view) {

    }

    @Override
    protected int getViewId() {
        return R.layout.frag_tpc_list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
