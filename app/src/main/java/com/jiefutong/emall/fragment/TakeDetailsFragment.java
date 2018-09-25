package com.jiefutong.emall.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.TakeDetailsAdapter;
import com.jiefutong.emall.bean.BalanceBean;
import com.jiefutong.emall.bean.TakeDetailsBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;

/**
 * @Author l
 * @Date 2018/7/20
 */
public class TakeDetailsFragment extends BaseFragment {
    private int position;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<TakeDetailsBean.DataMapBean.DataListBean> datas = new ArrayList<>();
    private SwipeRefreshLayout msrl;

    @SuppressLint("ValidFragment")
    public TakeDetailsFragment(int position) {
        this.position = position;
    }

    public TakeDetailsFragment() {
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        HttpParams params = new HttpParams();
        params.put("payStatus", String.valueOf(position + 1));
        HttpUtils.getFraNetData(HttpUtils.TAKE_DETAILS, this, params, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                TakeDetailsBean bean = JsonUtil.parseObject(data, TakeDetailsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS && bean.dataMap.dataList != null) {
                    adapter.addData(bean.dataMap.dataList);
                }
            }
        });
    }

    @Override
    protected void intiview(View view) {
        View empty = View.inflate(mctx, R.layout.empty_goods_show, null);
        ImageView iv = empty.findViewById(R.id.iv_empty);
        TextView info = empty.findViewById(R.id.tv_info);
        empty.setBackgroundResource(R.color.bg_act_gray);
        iv.setBackgroundResource(R.mipmap.img_fxtxmx_null);
        info.setText("暂时没有此项数据哦~");
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new TakeDetailsAdapter(R.layout.item_frag_take_details, datas, mctx);
        mRlvContent.setAdapter(adapter);
        msrl = view.findViewById(R.id.sfl);
        adapter.setEmptyView(empty);
        msrl.setEnabled(false);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_income_list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
