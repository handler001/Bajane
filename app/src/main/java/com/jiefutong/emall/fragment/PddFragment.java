package com.jiefutong.emall.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.FightBuyAdapter;
import com.jiefutong.emall.bean.FightBuyPddBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

/**
 * @Author l
 * @Date 2018/9/19
 */
public class PddFragment extends BaseFragment {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlv;
    private BaseQuickAdapter adapter;
    private SwipeRefreshLayout srl;
    private View empty;
    private ArrayList<FightBuyPddBean.DataMapBean> dataBeans = new ArrayList<>();
    private int position = 1;

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        initdata();
    }

    @Override
    protected void intiview(View view) {
        empty = View.inflate(mctx, R.layout.empty_goods_show, null);
        mIvBack = (ImageView) view.findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.GONE);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText("赚客");
        mRlv = view.findViewById(R.id.rlv_goods);
        mRlv.setLayoutManager(new GridLayoutManager(mctx, 2));
        adapter = new FightBuyAdapter(R.layout.item_fight_buy, dataBeans, mctx);
        mRlv.setAdapter(adapter);
        adapter.setEmptyView(empty);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        },mRlv);
        srl = view.findViewById(R.id.srl);
        srl.setEnabled(false);
    }

    @Override
    protected int getViewId() {
        return R.layout.main_fragment_mall;
    }


    private void initdata() {
        final HttpParams params = new HttpParams();
        params.put("page", String.valueOf(position));
        params.put("has_coupon", "true");
        HttpUtils.getFraNetData(HttpUtils.LIST_PDD, this, params, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                adapter.loadMoreComplete();
                FightBuyPddBean fightBuyPddBean = JsonUtil.parseObject(data, FightBuyPddBean.class);
                if (fightBuyPddBean != null && fightBuyPddBean.code == HttpUtils.SUCCESS) {
                    if (position == 1) {
                        adapter.setNewData(fightBuyPddBean.dataMap);
                    } else {
                        adapter.addData(fightBuyPddBean.dataMap);
                    }
                    if (fightBuyPddBean.dataMap.size() < 20) {
                        adapter.loadMoreEnd();
                    }
                    position++;
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                adapter.loadMoreFail();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
