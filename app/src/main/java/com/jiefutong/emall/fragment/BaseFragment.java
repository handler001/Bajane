package com.jiefutong.emall.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiefutong.emall.R;
import com.jiefutong.emall.utils.ResourceUtils;
import com.jiefutong.emall.utils.ToastUtil;
import com.jiefutong.emall.widget.CircleProgres;


/**
 * Created by xcl on 2017/4/7.
 */
public abstract class BaseFragment extends Fragment {
    public View view;
    public CircleProgres progres;
    public Context mctx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mctx = getActivity();
        if (view == null) {
            view = inflater.inflate(getViewId(), null);
            this.intiview(view);
            this.listener();
            this.loadData();
        }
        return view;
    }

    protected abstract void listener();

    protected abstract void loadData();

    protected abstract void intiview(View view);

    protected abstract int getViewId();

    public void showPb(Context mctx) {
        if (progres == null) {
            progres = new CircleProgres(mctx);
        }
        progres.show();
    }

    public void dismissPb() {
        if (progres != null && progres.isShowing()) {
            progres.dismiss();
        }
    }

    //展示toast
    public void showToast(String msg) {
        ToastUtil.show(getActivity(), msg);
    }

    public void showToast(int msg) {
        ToastUtil.show(getActivity(), ResourceUtils.getString(getActivity(), msg));
    }

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    //打开
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        //动画
        getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }

    public void openActivity(Intent intent) {
        //动画
        getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }

    public void showmessage(int message) {
        showToast(ResourceUtils.getString(mctx, message));
    }

    public int getcolor(int id) {
        return ResourceUtils.getColor(mctx, id);
    }

    public void showDataError() {
        showToast(ResourceUtils.getString(mctx, R.string.data_error));
    }

    public void showNetError() {
        showToast(ResourceUtils.getString(mctx, R.string.net_error));
    }
}
