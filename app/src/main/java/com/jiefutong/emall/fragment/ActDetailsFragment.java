package com.jiefutong.emall.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.BannerWebActivity;
import com.jiefutong.emall.activity.GoodsCarManagerActivity;
import com.jiefutong.emall.activity.ProductDetailActivity;
import com.jiefutong.emall.activity.WebViewActivity;
import com.jiefutong.emall.adapter.ActContentAdapter;
import com.jiefutong.emall.bean.ActContentBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author l
 * @Date 2018/7/10
 */
public class ActDetailsFragment extends BaseFragment implements View.OnClickListener {
   // private ImageView mIvMessage;
    private TextView mTvTitle;
    private ImageView mIvCar;
    private TextView mTvNum;
    private RecyclerView mRlvContent;
    private BannerViewPager mLoopViewpager;
    private ArrayList<ActContentBean.DataMapBean.DataBean> datas = new ArrayList<>();
    private ActContentAdapter adapter;
    private View header;

    @Override
    protected void listener() {
       // mIvMessage.setOnClickListener(this);
        mIvCar.setOnClickListener(this);
        adapter = new ActContentAdapter(R.layout.item_act_content, datas, mctx);
        mRlvContent.setAdapter(adapter);
        adapter.addHeaderView(header);
    }

    @Override
    protected void loadData() {
        HttpUtils.getFraNetData(HttpUtils.ACT_LIST_INFO, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                ActContentBean bean = JsonUtil.parseObject(data, ActContentBean.class);
                if (bean != null) {
                    if (bean.code == HttpUtils.SUCCESS) {
                        adapter.settype(bean.dataMap.coinFlag);
                        adapter.addData(bean.dataMap.data);
                        initbanner(bean.dataMap.banner);
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
        header = View.inflate(mctx, R.layout.header_act_mz_banner, null);
        mLoopViewpager = header.findViewById(R.id.loop_viewpager);
      //  mIvMessage = view.findViewById(R.id.iv_message);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText("活动");
        mIvCar = view.findViewById(R.id.iv_car);
        mTvNum = view.findViewById(R.id.tv_num);
        mRlvContent = view.findViewById(R.id.rlv_content);
        mRlvContent.setLayoutManager(new LinearLayoutManager(mctx));
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_act_details;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_message:
              //  openActivity(MessageInfoActivity.class);
                break;
            case R.id.iv_car:
                openActivity(GoodsCarManagerActivity.class);
                break;
        }
    }

    private void initbanner(List<ActContentBean.DataMapBean.BannerBean> banner) {
        PageBean bean = new PageBean.Builder<ActContentBean.DataMapBean.BannerBean>()
                .setDataObjects(banner)
                .builder();
        mLoopViewpager.setPageTransformer(false, new MzTransformer());
        mLoopViewpager.setPageListener(bean, R.layout.item_act_banner_pic, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                final ActContentBean.DataMapBean.BannerBean bean = (ActContentBean.DataMapBean.BannerBean) o;
                ImageView mpic = view.findViewById(R.id.iv_pic);
                GlideUtils.loadpic(mctx, mpic, bean.src);
                mpic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.type == 1) {
                            openActivity(new Intent(mctx, WebViewActivity.class).putExtra("url", bean.link)
                                    .putExtra("type", 1));
                        } else if (bean.type == 2) {
                            openActivity(new Intent(mctx, BannerWebActivity.class).putExtra("url", bean.link));
                        } else if (bean.type == 3) {
                            openActivity(new Intent(mctx, ProductDetailActivity.class).putExtra("id", bean.id));
                        } else {

                        }
                    }
                });
            }
        });
    }
}
