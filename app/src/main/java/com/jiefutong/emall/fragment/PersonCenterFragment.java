package com.jiefutong.emall.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.AddressManagerActivity;
import com.jiefutong.emall.activity.AppSettingActivity;
import com.jiefutong.emall.activity.BankCardActivity;
import com.jiefutong.emall.activity.CompanyJoinActivity;
import com.jiefutong.emall.activity.DistributionSalesActivity;
import com.jiefutong.emall.activity.FeedBackActivity;
import com.jiefutong.emall.activity.GoodsCarManagerActivity;
import com.jiefutong.emall.activity.MallOrderListActivity;
import com.jiefutong.emall.activity.MyAssetsActivity;
import com.jiefutong.emall.activity.MyOrderActivity;
import com.jiefutong.emall.activity.OrderMeActivity;
import com.jiefutong.emall.activity.PersonInfoActivity;
import com.jiefutong.emall.activity.PromotionInfoActivity;
import com.jiefutong.emall.activity.SalesAfterActivity;
import com.jiefutong.emall.activity.WebViewActivity;
import com.jiefutong.emall.bean.EventBusBean;
import com.jiefutong.emall.bean.OrderNumBean;
import com.jiefutong.emall.bean.UserDetailsBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.HttpUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.MyStringCallBack;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @Author l
 * @Date 2018/7/10
 */
public class PersonCenterFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mIvSetting;
    private ImageView mIvCar;
    private TextView mTvNum;
    private ImageView mIvHeader;
    private TextView mTvName;
    private ImageView mIvEnter;
    private RelativeLayout mRlPayAll;
    private LinearLayout mLlPersonFx;
    private LinearLayout mLlPersonDl;
    private LinearLayout mLlPersonZc;
    private RelativeLayout mRlPayDai;
    private RelativeLayout mRlFh;
    private RelativeLayout mRlSh;
    private RelativeLayout mRlYz;
    private View msh;
    private TextView mTvTksh;
    private TextView mTvBank;
    private TextView mTvAddress;
    private TextView mTvJm;
    private TextView mTvTg;
    private TextView mTvKf;
    private TextView mTvVip;
    private TextView mTvOrder;
    private int flag;
    private TextView mTvDaiNum;
    private TextView mTvDaiGoods;
    private TextView mTvShouGoods;
    private ImageView lav;

    @Override
    protected void listener() {
        mIvSetting.setOnClickListener(this);
        mIvCar.setOnClickListener(this);
        mIvEnter.setOnClickListener(this);
        mRlPayAll.setOnClickListener(this);
        mLlPersonDl.setOnClickListener(this);
        mLlPersonFx.setOnClickListener(this);
        mLlPersonZc.setOnClickListener(this);
        mRlPayDai.setOnClickListener(this);
        mRlFh.setOnClickListener(this);
        mRlSh.setOnClickListener(this);
        mRlYz.setOnClickListener(this);
        mTvTksh.setOnClickListener(this);
        mTvBank.setOnClickListener(this);
        mTvAddress.setOnClickListener(this);
        mTvJm.setOnClickListener(this);
        mTvTg.setOnClickListener(this);
        mTvKf.setOnClickListener(this);
        lav.setOnClickListener(this);
        mTvOrder.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        HttpUtils.getFraNetData(HttpUtils.USER_INFO, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                UserDetailsBean bean = JsonUtil.parseObject(data, UserDetailsBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS && bean.dataMap != null) {
                    GlideUtils.loadCirclepic(mctx, mIvHeader, bean.dataMap.headImgUrl);
                    mTvName.setText(bean.dataMap.realName);
                    mTvVip.setText(bean.dataMap.viewUserType);
                    flag = bean.dataMap.refundFlag;
                    if (bean.dataMap.refundFlag == 1) {
                        msh.setVisibility(View.VISIBLE);
                        mTvTksh.setVisibility(View.VISIBLE);
                    } else {
                        msh.setVisibility(View.GONE);
                        mTvTksh.setVisibility(View.GONE);
                    }
//                    if (bean.dataMap.userType.equals("1")) {
//                        mTvVip.setText("消费者");
//                    } else if (bean.dataMap.userType.equals("2")) {
//                        mTvVip.setText("门店");
//                    } else {
//                        mTvVip.setText("分公司");
//                    }
                }
            }
        });
    }

    @Override
    protected void intiview(View view) {
        EventBus.getDefault().register(this);
        mIvSetting = view.findViewById(R.id.iv_setting);
        mIvCar = view.findViewById(R.id.iv_car);
        mTvNum = view.findViewById(R.id.tv_num);
        mIvHeader = view.findViewById(R.id.iv_header);
        mIvHeader.setOnClickListener(this);
        mTvName = view.findViewById(R.id.tv_name);
        mTvVip = view.findViewById(R.id.tv_vip);
        mIvEnter = view.findViewById(R.id.iv_enter);
        mRlPayAll = view.findViewById(R.id.rl_order_all);
        mLlPersonFx = view.findViewById(R.id.ll_person_fx);
        mLlPersonDl = view.findViewById(R.id.ll_person_dl);
        mLlPersonZc = view.findViewById(R.id.ll_person_zc);
        mRlPayDai = view.findViewById(R.id.rl_pay_dai);
        mRlFh = view.findViewById(R.id.rl_fh);
        mRlSh = view.findViewById(R.id.rl_sh);
        mRlYz = view.findViewById(R.id.rl_yz);
        mTvTksh = view.findViewById(R.id.tv_tksh);
        msh = view.findViewById(R.id.sh);
        mTvBank = view.findViewById(R.id.tv_bank);
        mTvOrder = view.findViewById(R.id.tv_mall_order);
        mTvAddress = view.findViewById(R.id.tv_address);
        mTvJm = view.findViewById(R.id.tv_jm);
        mTvTg = view.findViewById(R.id.tv_tg);
        mTvKf = view.findViewById(R.id.tv_kf);
        mTvDaiNum = view.findViewById(R.id.tv_dai_num);
        mTvDaiGoods = view.findViewById(R.id.tv_dai_goods);
        mTvShouGoods = view.findViewById(R.id.tv_shou_goods);
        lav = view.findViewById(R.id.lav);
        getnum();
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_person_center;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting:
                openActivity(AppSettingActivity.class);
                break;
            case R.id.iv_car:
                openActivity(GoodsCarManagerActivity.class);
                break;
            case R.id.iv_enter:
            case R.id.iv_header:
                openActivity(PersonInfoActivity.class);
                break;
            case R.id.rl_order_all:
                openActivity(new Intent(mctx, OrderMeActivity.class).putExtra("position", 0));
                break;
            case R.id.ll_person_fx:
                openActivity(DistributionSalesActivity.class);
                break;
            case R.id.ll_person_dl:
//                if (flag == 1) {
//                    openActivity(new Intent(mctx, WebViewActivity.class).putExtra("url", HttpUtils.MEMBER_PAGE));
//                } else {
//                    openActivity(new Intent(mctx, WebViewActivity.class).putExtra("url", HttpUtils.MEMBER_PAGE_new));
//                }
                break;
            case R.id.ll_person_zc:
                openActivity(MyAssetsActivity.class);
                break;
            case R.id.rl_pay_dai:
                openActivity(new Intent(mctx, OrderMeActivity.class).putExtra("position", 1));
                break;
            case R.id.rl_fh:
                openActivity(new Intent(mctx, OrderMeActivity.class).putExtra("position", 2));
                break;
            case R.id.rl_sh:
                openActivity(new Intent(mctx, OrderMeActivity.class).putExtra("position", 3));
                break;
            case R.id.rl_yz:
                openActivity(MyOrderActivity.class);
                break;
            case R.id.tv_tksh:
                openActivity(SalesAfterActivity.class);
                break;
            case R.id.tv_bank:
                openActivity(BankCardActivity.class);
                break;
            case R.id.tv_address:
                openActivity(AddressManagerActivity.class);
                break;
            case R.id.tv_jm:
                openActivity(CompanyJoinActivity.class);
                break;
            case R.id.tv_tg:
                openActivity(new Intent(mctx, PromotionInfoActivity.class).putExtra("flag", flag));
                break;
            case R.id.tv_kf:
                showpop();
                break;
            case R.id.lav:
                openActivity(FeedBackActivity.class);
                break;
            case R.id.tv_mall_order:
                openActivity(MallOrderListActivity.class);
                break;
        }
    }

    private PopupWindow pop;

    private void showpop() {
        if (pop == null) {
            View view = View.inflate(mctx, R.layout.layout_info_cuetomer, null);
            view.findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.dismiss();
                }
            });
            view.findViewById(R.id.prl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.dismiss();
                }
            });

            pop = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            pop.showAtLocation(mIvSetting, Gravity.CENTER, 0, 0);
        } else {
            pop.showAtLocation(mIvSetting, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (event.contains("http")) {
            GlideUtils.loadCirclepic(mctx, mIvHeader, event);
        } else {
            mTvName.setText(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusBean busBean) {
        if (busBean.cod.equals("order")) {
            getnum();
        }
    }

    private void getnum() {
        HttpUtils.getFraNetData(HttpUtils.ORDER_NUM, this, new MyStringCallBack(mctx) {
            @Override
            protected void dealdata(String data) {
                OrderNumBean bean = JsonUtil.parseObject(data, OrderNumBean.class);
                if (bean != null && bean.code == HttpUtils.SUCCESS) {
                    if (bean.dataMap.waitPayNum == 0) {
                        mTvDaiNum.setVisibility(View.GONE);
                    } else {
                        mTvDaiNum.setVisibility(View.VISIBLE);
                        mTvDaiNum.setText(String.valueOf(bean.dataMap.waitPayNum));
                    }

                    if (bean.dataMap.waitSendNum == 0) {
                        mTvDaiGoods.setVisibility(View.GONE);
                    } else {
                        mTvDaiGoods.setVisibility(View.VISIBLE);
                        mTvDaiGoods.setText(String.valueOf(bean.dataMap.waitSendNum));
                    }

                    if (bean.dataMap.waitReceiveNum == 0) {
                        mTvShouGoods.setVisibility(View.GONE);
                    } else {
                        mTvShouGoods.setVisibility(View.VISIBLE);
                        mTvShouGoods.setText(String.valueOf(bean.dataMap.waitReceiveNum));
                    }
                }
            }
        });
    }
}
