package com.jiefutong.emall.utils;

import com.jiefutong.emall.activity.BaseActivity;
import com.jiefutong.emall.fragment.BaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

/**
 * @Author l
 * @Date 2018/7/9
 */
public class HttpUtils {
    public static final int SUCCESS = 200;
    //public static String BASE_URL = "http://2090z201k9.iask.in:44158";
    public static String BASE_URL = "http://egou.leetx.net";
    //登录
    public static String LOGIN = "/user/login";
    //退出登录
    public static String LOGOUT = "/app/user/logout";
    //获取验证码
    public static String CODE_REGIST_GET = "/user/sendCode";
    //密码找回验证码
    public static String CODE_Find_GET = "/user/forget/send";
    //注册
    public static String USER_REGIST = "/user/register";
    //密码找回
    public static String PWD_FIND_BACK = "/user/forget/form";
    //登录密码修改
    public static String PWD_CHANGE = "/app/user/resetPsw";
    //个人信息
    public static String USER_INFO = "/app/user/userInfo";
    //商品列表
    public static String GOODS_LIST_INFO = "/app/adGoods/list";
    //商品详情
    public static String GOODS_DETAIL_INFO = "/app/adGoods/detail";
    //根据规格获取商品价格
    public static String GOODS_INFO_PRICE = "/app/adGoods/getPrice";
    //加入购物车
    public static String GOODS_ADD_CAR = "/app/shopcart/add";
    //立即购买
    public static String GOODS_BUY_NOW = "/app/goods/goBuy";
    //实体店列表
    public static String SHOP_LIST_INFO = "/app/shop/list";
    //活动
    public static String ACT_LIST_INFO = "/app/acitvity/list";
    //实体店地区接口
    public static String AREA_LIST_INFO = "/app/shop/areaList";
    //视频锦集列表
    public static String VIDEO_LIST_INFO = "/app/vedio/list";
    //收益排行榜
    public static String INCOME_LIST_INFO = "/app/user/top";
    //店主收益排行榜
    public static String INCOME_SHOP_LIST_INFO = "/app/user/shopList";
    //加盟分公司
    public static String AGENT_ADD = "/app/agent/add";
    //产品介绍列表
    public static String PRO_LIST_DETAIL = "/app/article/assortmentList";
    //产品二级文章列表
    public static String ARTICLE_LIST_DETAIL = "/app/article/list";
    //我的店铺订单
    public static String SHOP_ORDER_LIST = "/app/order/shopList";
    //我的店铺
    public static String MY_SHOP_INFO = "/app/shop/myShop";
    //店铺新增
    public static String MY_SHOP_ADD = "/app/shop/add";
    //店铺信息编辑
    public static String MY_SHOP_EDIT = "/app/shop/edit";
    //输入验证码获取订单接口
    public static String MY_SHOP_CODE = "/app/order/checkCode";
    //验证接口
    public static String MY_SHOP_CODE_CHECK = "/app/order/enterCheck";
    //上传店铺图片
    public static String MY_SHOP_PIC_UOLOAD = "/app/user/uploadImg";
    //新增地址
    public static String ADDRESS_INFO_ADD = "/app/address/save";
    //收货地址列表
    public static String ADDRESS_INFO_LIST = "/app/address/list";
    //更改默认地址
    public static String ADDRESS_INFO_CHANGE = "/app/address/change";
    //编辑地址
    public static String ADDRESS_INFO_EDIT = "/app/address/edit";
    //删除地址
    public static String ADDRESS_INFO_DELETE = "/app/address/delete";
    //消息列表
    public static String MSG_INFO_LIST = "/app/msg/list";
    //消息删除
    public static String MSG_INFO_DELETE = "/app/msg/delete";
    //上传头像
    public static String INFO_HEADER_CHANGE = "/app/user/uploadImg";
    //我的银行卡
    public static String INFO_BANK_CARD = "/app/user/myCard";
    //解绑银行卡
    public static String UNBIND_BANK_CARD = "/app/user/unbindCard";
    //添加银行卡
    public static String ADD_BANK_CARD = "/app/user/bindCard";
    //银行卡列表
    public static String BANK_CARD_CODE_LIST = "/app/bank/wxList";
    //问题反馈
    public static String PROBLEM_FEED_BACK = "/app/problem/add";
    //我的资产
    public static String MY_ASSETS = "/app/user/wallet";
    //我的好友
    public static String MY_FRIENDS_INFO = "/app/user/friends";
    //累计盈利列表
    public static String BALANCE_INFO_DETAILS = "/app/user/accountStatement";
    //修改真实姓名
    public static String NAME_CHANGE = "/app/user/resetRealName";
    //新增订单
    public static String ORDER_ADD = "/app/order/save";
    //订单列表
    public static String ORDER_LIST = "/app/order/list";
    //订单详情
    public static String ORDER_INFO_DETAIL = "/app/order/detail";
    //订单立即支付
    public static String ORDER_PAY_NOW = "/app/order/goBuy";
    //确认收货
    public static String ORDER_INFO_GET = "/app/order/enter";
    //订单取消
    public static String ORDER_PAY_CANClE = "/app/order/cancle";
    //购物车列表
    public static String CAR_GOODS_LIST = "/app/shopcart/list";
    //购物车删除功能
    public static String CAR_GOODS_DELETE = "/app/shopcart/delete";
    //首页轮播图
    public static String HOME_PAGE_BANNER = "/app/acitvity/slideList";
    //设置交易密码发送验证码
    public static String PWD_CODE_GET = "/app/user/send";
    //设置交易密码
    public static String PWD_EXCHANGE_SET = "/app/user/setTransPsw";
    //根据手机号码获取转给谁的信息
    public static String GET_INFO_BY_PHONE = "/app/account/getUser";
    //根据手机号码确认转账给谁
    public static String GET_INFO_BY_PHONE_balance = "/app/account/getUserBalance";
    //转金币
    public static String GOLD_EXCHANGE = "/app/account/changeGold";
    //转余额
    public static String BALANCE_EXCHANGE = "/app/account/changeBalance";
    //转兑换券
    public static String TICKET_EXCHANGE = "/app/account/changeCoupon";
    //分销中心
    public static String CENTER_FX = "/app/user/fenXiao";
    //提现
    public static String MONEY_TAKE = "/app/cash/apply";
    //提现算手续费
    public static String MONEY_TAKE_RATE = "/app/cash/rate";
    //提现余额算手续费
    public static String BALANCE_TAKE_RATE = "/app/account/rate";
    //获取支付宝账号
    public static String INFO_ALI_GET = "/app/user/getAlipay";
    //绑定支付宝
    public static String ALI_BINDING = "/app/user/bindAlipay";
    //提现列表
    public static String TAKE_DETAILS = "/app/user/userCash";
    //提现列表详情
    public static String TAKE_OREDR_DETAILS = "/app/user/userCashDetail";
    //我要推广  二维码和网页链接
    public static String PAGE_SHARE_LINK = "/app/user/share/link";
    //升级接口
    public static String UPDATE_VERSION = "/user/upGrade";
    //待验证和验证列表
    public static String ORDER_CHECK_LIST = "/app/order/checkList";
    //发起退款
    public static String REFUND_SAVE = "/app/refund/save";
    //退款详情
    public static String REFUND_DETAIL = "/app/refund/detail";
    //退款列表
    public static String REFUND_LIST = "/app/refund/list";
    //获取退款金额
    public static String REFUND_PRICE_GET = "/app/refund/getPrice";
    //收入前三名
    public static String INCOMNE_LIST_TOP = "/app/user/mainTop";
    //退货提供物流信息
    public static String REFUND_EMS_INFO = "/app/refund/addPostage";
    //取消退款
    public static String REFUND_CANCLE = "/app/refund/cancleRefund";
    //参与记录
    public static String HISTORY_JOIN = "/app/order/findList";
    //到账日期和手续费显示
    public static String RATE_DESC = "/app/cash/rateDesc";
    //未读订单个数
    public static String ORDER_NUM = "/app/order/orderNum";
    //中央文案
    public static String CENTRA_COPY_LIST = "/app/official/list";
    //点赞新增个数
    public static String CENTRA_COPY_LIST_SHARE = "/app/official/shareNum";
    //消息分类列表
    public static String MSG_TYPE_LIST = "/app/msg/typeList";
    //消息列表
    public static String MSG_DETAIL_LIST = "/app/msg/list";
    //消息详情
    public static String MSG_DETAIL_INFO = "/app/msg/detail";
    //添加极光唯一Id
    public static String MSG_ADD_ID = "/app/user/registration";
    //视频点赞和取消点赞
    public static String VIDEO_LIKE = "/app/vedio/zanAdd";
    //视频评论列表
    public static String VIDEO_COMMENT_LIST = "/app/vedio/commentList";
    //新增评论
    public static String VIDEO_COMMENT_SEND = "/app/vedio/commentAdd";
    //新改版的活动列表
    public static String ACT_LIST_NEW = "/app/acitvity/newList";
    //拼多多列表
    public static String LIST_PDD = "/app/mall/duoList";
    //拼多多详情
    public static String LIST_PDD_DETAIL = "/app/mall/duoDetail";
    //多多订单列表
    public static String LIST_MALL_ORDER = "/app/mall/duoOrderList";
    //商品一级分类
    public static String GOODS_CATEGORY_LIST_TYPE = "/app/category/typeList";
    //商品分类
    public static String GOODS_CATEGORY_LIST = "/app/category/list";
    //分类列表
    public static String SHOP_CATEGORY_LIST = "/app/shop/categoryList";
    //商家列表
    public static String SHOP_LIST = "/app/shop/list";
    //附近商家
    public static String SHOP_NEAR_LIST = "/app/shop/nearList";
    //h5页面信息
    //收益榜说明
    public static String INCOME_LIST_EXPLAIN_PAGE = "http://bjn.leetx.net/bjnhtml/IncomeList.html";
    //活动页
    public static String ACT_PAGE = "http://bjn.leetx.net/bjnhtml/activities.html";
    //会员
    public static String MEMBER_PAGE = "http://bjn.leetx.net/bjnhtml/agent.html";
    public static String MEMBER_PAGE_new = "http://bjn.leetx.net/bjnhtml/agent2.html";
    //服务协议
    public static String SERVICE_AGREEMENT = "http://bjn.leetx.net/bjnhtml/agreement.html";
    //技术支持
    public static String TECHNOLOFY_SUPPORT = "http://www.jiefutong.net";
    //返佣教程
    public static String RETURN_MONEY_TEACH = "http://h5.egou.leetx.net/egouhtml/tutorial.html";

    public static void getNetData(String url, BaseActivity activity, HttpParams params, StringCallback callback) {
        OkGo.<String>post(BASE_URL + url)
                .tag(activity)
                .params(params)
                .execute(callback);
    }

    public static void getNetData(String url, BaseActivity activity, StringCallback callback) {
        OkGo.<String>get(BASE_URL + url)
                .tag(activity)
                .execute(callback);
    }

    public static void getFraNetData(String url, BaseFragment fragment, HttpParams params, StringCallback callback) {
        OkGo.<String>post(BASE_URL + url)
                .tag(fragment)
                .params(params)
                .execute(callback);
    }

    public static void getFraNetData(String url, BaseFragment fragment, StringCallback callback) {
        OkGo.<String>get(BASE_URL + url)
                .tag(fragment)
                .execute(callback);
    }
}
