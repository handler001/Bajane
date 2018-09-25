package com.jiefutong.emall.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.IncomeListAdapter;
import com.jiefutong.emall.bean.IncomeListBean;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;

/**
 * @Author l
 * @Date 2018/7/13
 */
public class IncomeListFragment extends BaseFragment {
    private int position;
    private int type;
    private RecyclerView mRlvContent;
    private BaseQuickAdapter adapter;
    private ArrayList<IncomeListBean.DataMapBean> datas = new ArrayList<>();
    private View view1;
    private SwipeRefreshLayout msrl;

    public IncomeListFragment() {
    }

    @SuppressLint("ValidFragment")
    public IncomeListFragment(int i, int type) {
        super();
        this.position = i;
        this.type = type;
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void loadData() {
        HttpParams params = new HttpParams();
        params.put("topType", String.valueOf(position + 1));
        String url = "";
        if (type == 0) {
            url = HttpUtils.INCOME_LIST_INFO;
        } else {
            url = HttpUtils.INCOME_SHOP_LIST_INFO;
        }
        HttpUtils.getFraNetData(url, this, params, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                IncomeListBean bean = JsonUtil.parseObject(data, IncomeListBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        adapter.addData(bean.dataMap);
                        adapter.setEmptyView(view1);
                    } else {
                        showToast(bean.message);
                    }
                } else {
                    showDataError();
                }
            }
        });
    }

    @Override
    protected void intiview(View view) {
        view1 = View.inflate(mctx, R.layout.empty_goods_show, null);
        TextView tv = view1.findViewById(R.id.tv_info);
        tv.setText("暂无收益信息");
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
        adapter = new IncomeListAdapter(R.layout.item_frag_income, datas, mctx);
        mRlvContent.setAdapter(adapter);
        msrl = view.findViewById(R.id.sfl);
        msrl.setEnabled(false);
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_income_list;
    }

}
