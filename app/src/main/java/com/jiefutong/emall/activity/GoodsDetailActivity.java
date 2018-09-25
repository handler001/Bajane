package com.jiefutong.emall.activity;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jiefutong.emall.R;
import com.jiefutong.emall.adapter.GoodShowAdapter;
import com.jiefutong.emall.adapter.MallGoodsAdapter;
import com.jiefutong.emall.bean.MallGoodsBean;
import com.jiefutong.emall.bean.MallTaoBaoBean;
import com.jiefutong.emall.utils.FileUtil;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.jiefutong.emall.utils.QRCodeUtil;
import com.jiefutong.emall.utils.ResourceUtils;
import com.lzy.okgo.OkGo;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.io.File;
import java.util.List;

import rx.functions.Action1;


public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {
    private String id, coupon_id;
    public TextView tv_goods_name;
    public TextView tv_price;
    public TextView tv_price_old;
    private AppBarLayout mLayout;
    private Toolbar toolbar;
    public RecyclerView rlv_goods;
    private TextView tv_share;
    private TextView tv_copy;
    private TextView tv_ticket;
    private ImageView iv_back, iv_code, iv_home;
    private View header, ticketHeader, footview;
    private TextView tv_gold_num;
    private TextView tv_ticket_price;
    private TextView tv_ticket_time;
    private TextView tv_language;
    private TextView tv_discount;
    private TextView tv_rmb;
    private TextView tv_detail_share;
    private String command;
    private TextView tv_goods_info, tv_goods_detail, tv_goods_param;
    private MallGoodsAdapter adapter;
    private GridLayoutManager manager;
    private MallTaoBaoBean.DataBean dataBean;
    private File tmpFile;
    private boolean expends;
    private RelativeLayout rl_ticket;
    private LinearLayout ll_content;
    private TextView tv_ticket_coupon;
    private WebView rlweb;
    private BannerViewPager mLoopViewpager;
    private ZoomIndicator mBottomScaleLayout;
    private String data = "{\"status\":\"success\",\"data\":{\"title\":\"人本帆布鞋女2018新款学生平底小白鞋原宿百搭ulzzang休闲板鞋\",\"category_id\":403,\"small_title\":\"人本帆布鞋新款学生平底小白鞋原宿百搭板鞋\",\"small_images\":\"https://img.alicdn.com/i4/2972229602/TB2NUvhkHSYBuNjSspfXXcZCpXa_!!2972229602.jpg,https://img.alicdn.com/i3/2972229602/TB2Z9qLkGSWBuNjSsrbXXa0mVXa_!!2972229602-0-item_pic.jpg,https://img.alicdn.com/i2/2972229602/TB2qUXgk_tYBeNjy1XdXXXXyVXa_!!2972229602.jpg,https://img.alicdn.com/i4/2972229602/TB2zsIjtVooBKNjSZFPXXXa2XXa_!!2972229602.jpg\",\"taobao_item_url\":null,\"commission_rate\":\"24.00%\",\"official\":\"松紧带设计，优雅大方，舒适鞋头，皮质柔软，不易皱，耐折轻便，橡胶大底，简约美观，显瘦百搭，条纹装饰，多色选择。\",\"sales\":12,\"pic\":\"https://img.alicdn.com/imgextra/i2/2972229602/TB2c7TItVkoBKNjSZFEXXbrEVXa_!!2972229602-0-item_pic.jpg\",\"original_price\":\"69.00\",\"price\":\"59.00\",\"numIid\":\"567469138252\",\"coupon_id\":\"dddd968e2a8e4edfb88eb24c203969c4\",\"coupon_price\":10,\"coupon_start_time\":\"2018.09.07\",\"coupon_end_time\":\"2018.09.12\",\"integral\":14160,\"rate_val\":70,\"anticipated_money\":\"14.16\",\"share_url\":\"http://t.cn/RsDCjbw\",\"desc_url\":\"https://mdetail.tmall.com/templates/pages/desc?id=567469138252\",\"pics\":[\"https://img.alicdn.com/imgextra/i2/2972229602/TB2c7TItVkoBKNjSZFEXXbrEVXa_!!2972229602-0-item_pic.jpg\",\"https://img.alicdn.com/i4/2972229602/TB2NUvhkHSYBuNjSspfXXcZCpXa_!!2972229602.jpg\",\"https://img.alicdn.com/i3/2972229602/TB2Z9qLkGSWBuNjSsrbXXa0mVXa_!!2972229602-0-item_pic.jpg\",\"https://img.alicdn.com/i2/2972229602/TB2qUXgk_tYBeNjy1XdXXXXyVXa_!!2972229602.jpg\",\"https://img.alicdn.com/i4/2972229602/TB2zsIjtVooBKNjSZFPXXXa2XXa_!!2972229602.jpg\"],\"taobao_coupon_click_url\":\"https://uland.taobao.com/coupon/edetail?e=P1oBigodksMGQASttHIRqYJR2ueLwfMWSbK3aSOXncptieFIxLDWj1XhsSQq2PNuJil4tp1%2BBUhzknhDuH03pr9fwBwwUiqlbCZVHaAgFiNIR7InptNW9nYefz8NXcoYTJnbK5InWzloKXCUZd8BcHFf5RMoZqfAYwOD23XOnRHymNXcL2pzBBsyR32X8RbXgtt4YCNvacc%3D&traceId=0b09279315367204754727619e&thispid=mm_128591705_44256639_476896926&src=fklm_hltk&from=tool&sight=fklm\",\"tpwd\":\"人本帆布鞋女2018新款学生平底小白鞋原宿百搭ulzzang休闲板鞋\\n【在售价】69.00元\\n【券后价】59.00元\\n【下单链接】http://t.cn/RsDCjbw\\n---------------------------\\n推荐理由:松紧带设计，优雅大方，舒适鞋头，皮质柔软，不易皱，耐折轻便，橡胶大底，简约美观，显瘦百搭，条纹装饰，多色选择。\\n复制这条信息，€7pSPbeUT5uK€打开【手淘】即可查看\",\"recommends\":[{\"activity_start_time\":\"2018-09-08 13:00:00\",\"id\":566873572417,\"title\":\"独霸保罗男士皮带真皮自动扣腰带韩版潮休闲中年青年商务牛皮裤带\",\"small_title\":\"独霸保罗  男士真牛皮自动扣皮带\",\"cid\":403,\"official\":\"【专柜同等品质】精选真牛皮材质，自动扣设计，质感牛皮，双倍耐磨！送父亲，送男友，送老公，今天特价，这辈子都很难买到这么便宜的真皮皮带~热销几十万条~会持家的都会带一条走！\",\"sales\":156735,\"pic\":\"https://img.alicdn.com/imgextra/i3/3405808014/TB1igxtjkKWBuNjy1zjXXcOypXa_!!0-item_pic.jpg_400x400q100.jpg\",\"original_price\":\"25.10\",\"price\":\"10.10\",\"numIid\":\"566873572417\",\"activity_type\":\"\",\"coupon_id\":\"e91b451a9f0b4dfea6dd9a08da5fa2f9\",\"coupon_price\":15,\"commission_rate\":\"16.00%\",\"integral\":1616,\"update_at\":\"2018-09-08 19:11:01\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/566873572417.html\",\"anticipated_money\":\"1.62\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/566873572417.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/566873572417.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":566735640456,\"title\":\"韩国超仙显脸瘦耳环冷淡风耳钉个性网红女气质百搭简约人鱼姬耳坠\",\"small_title\":\"【降价啦】女长款百搭个性耳饰耳钉耳环\",\"cid\":403,\"official\":\"商家疯了！打架啦！干起来啦！直接2元秒杀，只为销量！！【金冠店】网红新款，仙气十足的耳钉，超百搭拉伸显脸神器，精选80款总有一款适合你，让你一秒变女神！简直不能再赞了！\",\"sales\":141405,\"pic\":\"https://img.alicdn.com/imgextra/i1/1094416687/TB2XE8EnWmWBuNjy1XaXXXCbXXa_!!1094416687.jpg_400x400q100.jpg\",\"original_price\":\"5.00\",\"price\":\"2.00\",\"numIid\":\"566735640456\",\"activity_type\":\"\",\"coupon_id\":\"e29c7e3e69eb46cdb93fd01e434902c2\",\"coupon_price\":3,\"commission_rate\":\"28.01%\",\"integral\":560,\"update_at\":\"2018-09-10 11:30:54\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/566735640456.html\",\"anticipated_money\":\"0.56\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/566735640456.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/566735640456.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":528478887510,\"title\":\"洗脸束发带女发绳韩国头饰甜美简约发饰百搭网红发箍超仙森女系\",\"small_title\":\"ins网红气质简约发箍头饰洗脸发带\",\"cid\":403,\"official\":\"打架拉，打架拉，1.8元直接秒杀，【五金冠店】拍三件送十枚发夹，柔软优质法兰绒布料，甜美可爱束发带，80多款多色可选，时尚百搭，宽边头箍防滑头饰，性价比高，质量好，亏本冲量包邮，七天无理由退款！\",\"sales\":130579,\"pic\":\"https://img.alicdn.com/imgextra/i3/2986913374/TB2fAVnwsuYBuNkSmRyXXcA3pXa_!!2986913374.jpg_400x400q100.jpg\",\"original_price\":\"2.80\",\"price\":\"1.80\",\"numIid\":\"528478887510\",\"activity_type\":\"\",\"coupon_id\":\"431b9c534cc042e4a6b29864ca969fb7\",\"coupon_price\":1,\"commission_rate\":\"24.96%\",\"integral\":448,\"update_at\":\"2018-09-10 11:25:18\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/528478887510.html\",\"anticipated_money\":\"0.45\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/528478887510.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/528478887510.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":10856316474,\"title\":\"韩国个性耳坠百搭耳饰气质女耳钉网红耳环长款简约潮人冷淡风吊坠\",\"small_title\":\"韩国气质百搭个性耳环耳饰80款\",\"cid\":403,\"official\":\"【血亏冲量】1.8元直接秒杀，80款任意挑选。个性时尚百搭款，医用级耳针，不过敏不掉色，采用精致的毛球，甜美又有个性，简约时尚，轻盈自然，超仙毛绒耳饰，让你遇到最美的自己~亏本包邮。\",\"sales\":125393,\"pic\":\"https://img.alicdn.com/imgextra/i4/612719672/TB2VVbunXGWBuNjy0FbXXb4sXXa_!!612719672.jpg_400x400q100.jpg\",\"original_price\":\"2.80\",\"price\":\"1.80\",\"numIid\":\"10856316474\",\"activity_type\":\"\",\"coupon_id\":\"160e2142c92441bfafaac94dd6d825b3\",\"coupon_price\":1,\"commission_rate\":\"28.16%\",\"integral\":504,\"update_at\":\"2018-09-10 11:25:18\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/10856316474.html\",\"anticipated_money\":\"0.50\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/10856316474.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/10856316474.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":569106173329,\"title\":\"韩国小清新发绳简约发圈个性扎头发橡皮筋可爱成人森女系头绳头饰\",\"small_title\":\"新券！可爱发绳橡皮筋头饰发圈8件套\",\"cid\":403,\"official\":\"小清新发绳8根，柔软且超强弹力，超值套装，30款可选，做精致女人，随意搭配，给你不一样的美！\",\"sales\":122463,\"pic\":\"https://img.alicdn.com/imgextra/i1/1094416687/TB2Nnwvf9cqBKNjSZFgXXX_kXXa_!!1094416687.jpg_400x400q100.jpg\",\"original_price\":\"6.80\",\"price\":\"4.80\",\"numIid\":\"569106173329\",\"activity_type\":\"\",\"coupon_id\":\"1cb19f100cda46e18d12c35ad1fb177e\",\"coupon_price\":2,\"commission_rate\":\"24.01%\",\"integral\":1152,\"update_at\":\"2018-09-08 09:07:11\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/569106173329.html\",\"anticipated_money\":\"1.15\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/569106173329.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/569106173329.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":568226358896,\"title\":\"头绳韩国小清新发绳森女系成人简约发圈头饰扎头发橡皮筋发饰个性\",\"small_title\":\"小清新发圈发绳头饰头饰\",\"cid\":403,\"official\":\"多组合可选，简约清新，珍珠水钻装饰，蝴蝶结花朵设计，透明盒子包装，多款可选，用心为您，装扮人生！\",\"sales\":109354,\"pic\":\"https://img.alicdn.com/imgextra/i1/2439384361/O1CN011i5NhGzvjxBu69J_!!2439384361.jpg_400x400q100.jpg\",\"original_price\":\"9.90\",\"price\":\"6.90\",\"numIid\":\"568226358896\",\"activity_type\":\"\",\"coupon_id\":\"c60798cd1257455796b49ac0189780a3\",\"coupon_price\":3,\"commission_rate\":\"24.00%\",\"integral\":1655,\"update_at\":\"2018-09-10 11:28:21\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/568226358896.html\",\"anticipated_money\":\"1.66\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/568226358896.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/568226358896.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":566396176879,\"title\":\"无孔圆扣女士皮带女简约百搭韩国复古时尚黑色裤腰带女韩版学生\",\"small_title\":\"无孔设计百搭休闲腰带女圆环扣\",\"cid\":403,\"official\":\"皮质纤维紧实，柔软强韧，时尚优雅，颜色随心百搭，经久耐用，简约大气，让你造型更生动有范，半年内用断只换不修。\",\"sales\":95634,\"pic\":\"https://img.alicdn.com/imgextra/i1/3254182661/TB1GSkjdLImBKNjSZFlXXc43FXa_!!0-item_pic.jpg_400x400q100.jpg\",\"original_price\":\"9.90\",\"price\":\"6.90\",\"numIid\":\"566396176879\",\"activity_type\":\"\",\"coupon_id\":\"b6e01dbcf97f46e9a0889fe38bc8ff63\",\"coupon_price\":3,\"commission_rate\":\"56.00%\",\"integral\":3864,\"update_at\":\"2018-09-10 11:36:33\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/566396176879.html\",\"anticipated_money\":\"3.86\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/566396176879.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/566396176879.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":534068049215,\"title\":\"保罗男士皮带正品真皮自动扣休闲中年裤带青年商务韩版潮牛皮腰带\",\"small_title\":\"【壹号保罗】男士自动扣真皮皮带\",\"cid\":403,\"official\":\"【超级爆款】真皮牛皮材质，品质保证，合金扣头，时尚大方，累计销售267万件，100万+好评，多色可选，6个月断裂免费换新，无敌了~这个价格抢到就偷笑了~【赠送运费险，免费试穿】\",\"sales\":91615,\"pic\":\"https://img.alicdn.com/imgextra/i3/1111450609/TB2.LwCqDdYBeNkSmLyXXXfnVXa_!!1111450609.jpg_400x400q100.jpg\",\"original_price\":\"20.10\",\"price\":\"10.10\",\"numIid\":\"534068049215\",\"activity_type\":\"\",\"coupon_id\":\"68962c353b014875bb37010c287e6a51\",\"coupon_price\":10,\"commission_rate\":\"16.00%\",\"integral\":1616,\"update_at\":\"2018-09-10 11:26:16\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/534068049215.html\",\"anticipated_money\":\"1.62\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/534068049215.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/534068049215.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":569465429363,\"title\":\"卡帝乐鳄鱼旅行箱男女学生密码拉杆箱万向轮24寸登机皮箱子行李箱\",\"small_title\":\"【卡帝乐鳄鱼】高品质旅行行李拉杆箱\",\"cid\":403,\"official\":\"旅行、开学必备，超大空间，够你装~【顺丰包邮】大牌高品质拉杆箱，坚韧耐磨防水防刮，360度万向轮，静音迅速滑轮，防盗密码锁，特殊工艺经久耐摔，柔韧性十足经久耐用，大气美观~【赠送运费险】\",\"sales\":77643,\"pic\":\"https://img.alicdn.com/imgextra/i1/3229147608/TB2pyKhq5MnBKNjSZFzXXc_qVXa_!!3229147608.jpg_400x400q100.jpg\",\"original_price\":\"199.00\",\"price\":\"79.00\",\"numIid\":\"569465429363\",\"activity_type\":\"\",\"coupon_id\":\"5bc88edef31c4fee944ab79850e9bfc4\",\"coupon_price\":120,\"commission_rate\":\"16.00%\",\"integral\":12640,\"update_at\":\"2018-09-10 11:25:35\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/569465429363.html\",\"anticipated_money\":\"12.64\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/569465429363.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/569465429363.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":525590211344,\"title\":\"甜美韩国可爱猫耳朵束发带宽边发饰洗脸发箍发卡超仙头巾女头饰品\",\"small_title\":\"猫耳朵束发带发饰发箍发卡头巾饰品\",\"cid\":403,\"official\":\"【拍2件6元3件8元】美少女气质，甜美可爱，手感顺滑，牢固耐用，时尚百搭，多款多色可选，百变风格，搭配你的百款发型，秀出你的灵动美！\",\"sales\":75910,\"pic\":\"https://img.alicdn.com/imgextra/i3/1094416687/TB2r9IFoiMnBKNjSZFCXXX0KFXa_!!1094416687.jpg_400x400q100.jpg\",\"original_price\":\"5.50\",\"price\":\"3.50\",\"numIid\":\"525590211344\",\"activity_type\":\"\",\"coupon_id\":\"5a91eebcaf8f4308bab9662deffe6647\",\"coupon_price\":2,\"commission_rate\":\"24.01%\",\"integral\":840,\"update_at\":\"2018-09-10 11:38:08\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/525590211344.html\",\"anticipated_money\":\"0.84\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/525590211344.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/525590211344.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":556059998410,\"title\":\"气质韩国耳环长款吊坠个性耳坠耳钉超仙网红简约百搭流苏耳饰品女\",\"small_title\":\"【3对装】网红流苏个性耳饰耳钉耳环\",\"cid\":403,\"official\":\"【拍第一个选项，发耳钉3对+耳钉盒1个+耳堵+消毒片】4金冠店，月销量超20万，好评超110万，八十款可选，一次买到爽，独家设计，百搭变美！\",\"sales\":72613,\"pic\":\"https://img.alicdn.com/imgextra/i3/2858168799/TB2JQjkh_lYBeNjSszcXXbwhFXa_!!2858168799.jpg_400x400q100.jpg\",\"original_price\":\"9.90\",\"price\":\"4.90\",\"numIid\":\"556059998410\",\"activity_type\":\"\",\"coupon_id\":\"6f78dbee911f4c2298466b92869cc7b4\",\"coupon_price\":5,\"commission_rate\":\"28.02%\",\"integral\":1376,\"update_at\":\"2018-09-10 11:26:16\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/556059998410.html\",\"anticipated_money\":\"1.38\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/556059998410.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/556059998410.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":560188634420,\"title\":\"韩国网红冷淡风耳环长款吊坠个性潮人耳坠气质简约耳钉百搭耳饰女\",\"small_title\":\"【网红爆款】冷淡风耳环长款耳钉\",\"cid\":403,\"official\":\"新款日韩耳环合辑，精选80款，三金冠品质保证，几块钱就能把你打扮成明星般的美丽~\",\"sales\":62640,\"pic\":\"https://img.alicdn.com/imgextra/i1/843758493/TB242gVqQSWBuNjSszdXXbeSpXa_!!843758493.jpg_400x400q100.jpg\",\"original_price\":\"8.99\",\"price\":\"5.99\",\"numIid\":\"560188634420\",\"activity_type\":\"\",\"coupon_id\":\"1affa5a9ed684753ac600435d41cc69a\",\"coupon_price\":3,\"commission_rate\":\"24.00%\",\"integral\":1440,\"update_at\":\"2018-09-10 11:40:08\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/560188634420.html\",\"anticipated_money\":\"1.44\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/560188634420.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/560188634420.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":565060674574,\"title\":\"简约头绳网红扎头发橡皮筋发绳韩国小清新森女系个性发圈成人头饰\",\"small_title\":\"月销6万无接缝不伤发头绳高弹力发圈皮筋\",\"cid\":403,\"official\":\"头绳这种东西总是莫名其妙越来越少，买一次用够好几年！纯色简约，不伤发，清新透气，高弹力无接缝，清新气质，轻松打造女神范儿！\",\"sales\":50442,\"pic\":\"https://img.alicdn.com/imgextra/i3/3689392221/TB2LzfxceUXBuNjt_a0XXcysXXa_!!3689392221-0-item_pic.jpg_400x400q100.jpg\",\"original_price\":\"6.80\",\"price\":\"4.80\",\"numIid\":\"565060674574\",\"activity_type\":\"\",\"coupon_id\":\"f6e9bfecbab848428e61b085af11f9fd\",\"coupon_price\":2,\"commission_rate\":\"32.00%\",\"integral\":1536,\"update_at\":\"2018-09-10 11:44:25\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/565060674574.html\",\"anticipated_money\":\"1.54\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/565060674574.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/565060674574.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":563642623297,\"title\":\"清新气质潮人假耳环无耳洞耳夹女耳饰耳钉韩国简约个性耳骨夹耳\",\"small_title\":\"【耳夹免打孔】抖音网红气质耳环饰品！\",\"cid\":403,\"official\":\"【一件包邮亏本】抖音网红爆款，无需打耳洞，80多款选择，精致仙女范儿耳夹，优雅气质，总有一款适合你，让你一秒变女神！性价比高质量好，七天无理由退款！\",\"sales\":48904,\"pic\":\"https://img.alicdn.com/imgextra/i1/75644901/TB2dT3Yw1uSBuNjSsziXXbq8pXa_!!75644901.jpg_400x400q100.jpg\",\"original_price\":\"6.00\",\"price\":\"4.00\",\"numIid\":\"563642623297\",\"activity_type\":\"\",\"coupon_id\":\"a894628da52d4cee8df2c11feda458c3\",\"coupon_price\":2,\"commission_rate\":\"24.00%\",\"integral\":960,\"update_at\":\"2018-09-10 11:23:54\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/563642623297.html\",\"anticipated_money\":\"0.96\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/563642623297.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/563642623297.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":17457326351,\"title\":\"耳钉女气质甜美韩国个性简约人鱼姬耳环百搭耳坠珍珠耳饰长款吊坠\",\"small_title\":\"【拍3件2.9元】简约百搭风耳环耳坠耳饰\",\"cid\":403,\"official\":\"【拍3件拍1.3元规格满3-1元】网红耳钉，单件包邮！血亏冲量！！四金冠老店，36W好评，80款可选，夏季快为你美美的耳朵添加一点点缀吧，时尚百搭、个性清新，告别单调，夏日女神必备。\",\"sales\":48740,\"pic\":\"https://img.alicdn.com/imgextra/i3/352333115/TB2XAlMqHZnBKNjSZFKXXcGOVXa_!!352333115.jpg_400x400q100.jpg\",\"original_price\":\"3.90\",\"price\":\"2.90\",\"numIid\":\"17457326351\",\"activity_type\":\"\",\"coupon_id\":\"07aa5203ce0d46dd99a5f2c40e5c4393\",\"coupon_price\":1,\"commission_rate\":\"24.00%\",\"integral\":696,\"update_at\":\"2018-09-10 11:26:16\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/17457326351.html\",\"anticipated_money\":\"0.70\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/17457326351.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/17457326351.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":570859741743,\"title\":\"头绳韩国小清新扎头发皮筋发圈简约森女系个性马尾发绳成人头饰女\",\"small_title\":\"小清新简约森女系发圈组合装\",\"cid\":403,\"official\":\"【任意选2件送30根皮筋】韩国小清新扎头发橡皮筋发圈，简约森女系个性马尾发绳成人头饰，数量多多的基础皮筋，选仿珍珠做点缀简单而时尚！\",\"sales\":41419,\"pic\":\"https://img.alicdn.com/imgextra/i3/2452094531/TB2FxoyFr1YBuNjSszhXXcUsFXa_!!2452094531.jpg_400x400q100.jpg\",\"original_price\":\"9.90\",\"price\":\"7.90\",\"numIid\":\"570859741743\",\"activity_type\":\"\",\"coupon_id\":\"5a8b02abccf84bdaa6180f965cde36d5\",\"coupon_price\":2,\"commission_rate\":\"24.00%\",\"integral\":1896,\"update_at\":\"2018-09-10 11:23:16\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/570859741743.html\",\"anticipated_money\":\"1.90\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/570859741743.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/570859741743.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":573719700006,\"title\":\"梦佰恩2018秋季新款透气飞织黑色红色跑步鞋青春潮流运动小白鞋男\",\"small_title\":\"【阿迪同款】超火情侣款椰子500街拍鞋\",\"cid\":403,\"official\":\"【阿迪同款】超火情侣款椰子鞋500，多款可选，防滑耐磨，全网面透气，舒适不闷脚，聚力回弹，减震缓震，按摩级舒适脚感，享受夏天清透运动【赠运险费】\",\"sales\":41252,\"pic\":\"https://img.alicdn.com/imgextra/i1/3512687542/TB2jAVav_dYBeNkSmLyXXXfnVXa_!!3512687542.jpg_400x400q100.jpg\",\"original_price\":\"129.90\",\"price\":\"29.90\",\"numIid\":\"573719700006\",\"activity_type\":\"\",\"coupon_id\":\"246508923c40413fa47b7f884ac55c5f\",\"coupon_price\":100,\"commission_rate\":\"24.00%\",\"integral\":7176,\"update_at\":\"2018-09-10 11:24:51\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/573719700006.html\",\"anticipated_money\":\"7.18\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/573719700006.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/573719700006.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":567283296725,\"title\":\"耳夹无耳洞女韩国气质简约清新百搭潮人冷淡风超仙精灵耳骨假耳环\",\"small_title\":\"【网红爆款】韩国个性气质简约超仙耳夹\",\"cid\":403,\"official\":\"网红爆款，80多款选择，精致仙女范儿耳环，时尚元素设计，闪耀水钻，古朴精美，优雅气质，总有一款适合你，让你一秒变女神！性价比高质量好，七天无理由退款\",\"sales\":38551,\"pic\":\"https://gd4.alicdn.com/imgextra/i4/732061145/TB2L7hIGKSSBuNjy0FlXXbBpVXa_!!732061145.jpg_400x400q100.jpg\",\"original_price\":\"5.90\",\"price\":\"4.90\",\"numIid\":\"567283296725\",\"activity_type\":\"\",\"coupon_id\":\"4347bfad576842e7afccca2c365ad7d3\",\"coupon_price\":1,\"commission_rate\":\"20.00%\",\"integral\":976,\"update_at\":\"2018-09-10 11:35:26\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/567283296725.html\",\"anticipated_money\":\"0.98\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/567283296725.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/567283296725.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":572980093678,\"title\":\"每人限购2件!棒球帽潮牌夏天韩版sup帽子男女休闲鸭舌帽嘻哈遮阳\",\"small_title\":\"superme 潮牌鸭舌帽遮阳帽棒球帽子\",\"cid\":403,\"official\":\"新潮又chic，四季可戴，时尚舒适，搭出街头范精致，瘦脸神器，百搭风格无论是约会，逛街，旅游都轻松应对，充分展现个性，让自信的你潮味满满，反戴也是超级达人~~~\",\"sales\":38286,\"pic\":\"https://img.alicdn.com/imgextra/i4/2677683303/TB2Zrfrm_mWBKNjSZFBXXXxUFXa_!!2677683303.jpg_400x400q100.jpg\",\"original_price\":\"89.90\",\"price\":\"29.90\",\"numIid\":\"572980093678\",\"activity_type\":\"\",\"coupon_id\":\"c78d2792a13a469aa85c5cd9f08a655a\",\"coupon_price\":60,\"commission_rate\":\"16.00%\",\"integral\":4784,\"update_at\":\"2018-09-10 11:25:04\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/572980093678.html\",\"anticipated_money\":\"4.78\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/572980093678.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/572980093678.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":576612728446,\"title\":\"纸手表 德国Papr Watch纸质防水黑科技智能手表新型创意抖音手表 男学生女情侣撕不烂cajiso新概念只纸做的表\",\"small_title\":\"纸手表 德国Papr Watch纸质防水黑科技\",\"cid\":403,\"official\":\"”纸“做手表，轻薄防水，跟纸一样轻薄，和薄膜一样防水，看是柔弱，却强大到超乎你的想象！\",\"sales\":37840,\"pic\":\"https://img.alicdn.com/imgextra/i2/2452581356/O1CN011Lt5WMR5wyE1Nht_!!2452581356.jpg_400x400q100.jpg\",\"original_price\":\"129.00\",\"price\":\"29.00\",\"numIid\":\"576612728446\",\"activity_type\":\"\",\"coupon_id\":\"39d90d28fe3c4e57bddc4d91bfb661bf\",\"coupon_price\":100,\"commission_rate\":\"32.80%\",\"integral\":9512,\"update_at\":\"2018-09-10 12:00:05\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/576612728446.html\",\"anticipated_money\":\"9.51\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/576612728446.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/576612728446.html?caogenid=\"}]},\"is_ios\":\"T\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        settitlewhite();
        id = getIntent().getStringExtra("id");
        initView();
        initListener();
        initdata();
    }

    private void initbanner(final List<String> dataMap) {
        PageBean bean = new PageBean.Builder<String>()
                .setDataObjects(dataMap)
                .setIndicator(mBottomScaleLayout)
                .builder();
        mLoopViewpager.setPageTransformer(false, new MzTransformer());
        mLoopViewpager.setPageListener(bean, R.layout.item_banner_pic, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                ImageView mpic = view.findViewById(R.id.iv_pic);
                GlideUtils.loadpic(context, mpic, (String) o);
            }
        });
    }

    protected void initView() {
        header = View.inflate(this, R.layout.item_header_mall_language, null);
        footview = View.inflate(this, R.layout.foot_mall_detail_info, null);
        ticketHeader = View.inflate(this, R.layout.item_header_mall_detail, null);
        rl_ticket = ticketHeader.findViewById(R.id.rl_ticket);
        rl_ticket.setOnClickListener(this);
        ll_content = ticketHeader.findViewById(R.id.ll_content);
        tv_language = ticketHeader.findViewById(R.id.tv_language);
        tv_gold_num = ticketHeader.findViewById(R.id.tv_gold_num);
        tv_ticket_price = ticketHeader.findViewById(R.id.tv_ticket_price);
        tv_ticket_time = ticketHeader.findViewById(R.id.tv_ticket_time);
        rlweb = new WebView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlweb.setLayoutParams(lp);
        rlweb.getSettings().setJavaScriptEnabled(true);
        rlweb.getSettings().setSupportZoom(false);
        rlweb.getSettings().setDisplayZoomControls(false);
        rlweb.getSettings().setLoadWithOverviewMode(true);
        rlweb.getSettings().setLoadsImagesAutomatically(true);
        rlweb.getSettings().setDomStorageEnabled(true);
        rlweb.getSettings().setUseWideViewPort(true);
        rlweb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        rlweb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        tv_ticket_coupon = findViewById(R.id.tv_ticket_coupon);
        tv_discount = findViewById(R.id.tv_discount);
        tv_rmb = findViewById(R.id.tv_rmb);
        iv_code = findViewById(R.id.iv_code);
        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        // iv_share = findViewById(R.id.iv_share);
        rlv_goods = findViewById(R.id.rlv_goods);
        tv_goods_name = findViewById(R.id.tv_goods_name);
        tv_price = findViewById(R.id.tv_price);
        tv_price_old = findViewById(R.id.tv_price_old);
        mBottomScaleLayout = findViewById(R.id.bottom_scale_layout);
        mLoopViewpager = findViewById(R.id.loop_viewpager);
        mLayout = findViewById(R.id.app_layout);
        toolbar = findViewById(R.id.toolbar);
        tv_share = findViewById(R.id.tv_bottom_share);
        tv_copy = findViewById(R.id.tv_copy);
        tv_ticket = findViewById(R.id.tv_ticket);
        tv_detail_share = findViewById(R.id.tv_detail_share);
        tv_goods_info = findViewById(R.id.tv_goods_info);
        tv_goods_detail = findViewById(R.id.tv_goods_detail);
        tv_goods_param = findViewById(R.id.tv_goods_param);
        manager = new GridLayoutManager(this, 2);
        rlv_goods.setLayoutManager(manager);
        dealposition(1);
    }

    private void dealposition(int i) {
        if (i == 0) {
            tv_goods_info.setTextColor(ResourceUtils.getColor(this, R.color.title_bg_red));
            tv_goods_detail.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
            tv_goods_param.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
        } else if (i == 1) {
            tv_goods_info.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
            tv_goods_detail.setTextColor(ResourceUtils.getColor(this, R.color.title_bg_red));
            tv_goods_param.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
        } else {
            tv_goods_info.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
            tv_goods_detail.setTextColor(ResourceUtils.getColor(this, R.color.text_black_3));
            tv_goods_param.setTextColor(ResourceUtils.getColor(this, R.color.title_bg_red));
        }
    }

    private void initListener() {
        iv_back.setOnClickListener(this);
        iv_code.setOnClickListener(this);
        iv_home.setOnClickListener(this);
        mLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) < mLayout.getTotalScrollRange()) {
                    toolbar.setVisibility(View.GONE);
                    tv_detail_share.setVisibility(View.GONE);
                    expends = true;
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                    tv_detail_share.setVisibility(View.VISIBLE);
                    expends = false;
                }
            }
        });
        tv_share.setOnClickListener(this);
        tv_ticket.setOnClickListener(this);
        tv_copy.setOnClickListener(this);
        tv_goods_info.setOnClickListener(this);
        tv_goods_detail.setOnClickListener(this);
        tv_goods_param.setOnClickListener(this);
    }

    private void initdata() {
        MallTaoBaoBean bean = JsonUtil.parseObject(data, MallTaoBaoBean.class);
        dealdata(bean.data);
    }

    private boolean detail;
    private boolean click;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_home:
                startActivity(new Intent(this, MainActivity.class).putExtra("position", 0));
                break;
//            case R.id.iv_share:
//                showToast("分享");
//                break;
            case R.id.iv_code:
                openpop();
                break;
            case R.id.tv_bottom_share:
                showToast("开发中");
//                startActivity(new Intent(this, ShareGoodsDetailActivity.class)
//                        .putExtra("id", id)
//                        .putExtra("tpwd", dataBean.tpwd)
//                        .putStringArrayListExtra("datas", dataBean.pics));
                break;
            case R.id.tv_copy:
//                if (!TextUtils.isEmpty(command)) {
//                    ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//                    cm.setPrimaryClip(ClipData.newPlainText(null, command));
//                    // 将文本内容放到系统剪贴板里。
//                    showToast("复制成功");
//                } else {
//                    showToast("复制失败,请重试");
//                }
                //  showPasswordpop();
                showToast("开发中");
                break;
            case R.id.tv_ticket:
            case R.id.rl_ticket:
//                if (AlibcLogin.getInstance().isLogin()) {
//                    Intent intent = new Intent(GoodsDetailActivity.this, MallWebviewActivity.class);
//                    intent.putExtra("id", id);
//                    intent.putExtra("coupon_id", coupon_id);
//                    intent.putExtra("share", dataBean.share_url);
//                    intent.putExtra("title", dataBean.small_title);
//                    intent.putExtra("pic", dataBean.pic);
//                    intent.putExtra("official", dataBean.official);
//                    intent.putExtra("url", dataBean.taobao_coupon_click_url);
//                    startActivity(intent);
//                } else {
//                    login();
//                }
                showToast("开发中");
                break;
            case R.id.tv_goods_info:
                click = true;
                detail = false;
                mLayout.setExpanded(true);
                rlv_goods.scrollToPosition(0);
                rlweb.scrollTo(0, 0);
                click = false;
                dealposition(1);
                break;
            case R.id.tv_goods_detail:
                click = true;
                detail = true;
                mLayout.setExpanded(false);
                rlv_goods.scrollToPosition(0);
                rlweb.scrollTo(0, 0);
                click = false;
                dealposition(1);
                break;
            case R.id.tv_goods_param:
                click = true;
                detail = false;
                mLayout.setExpanded(false);
                rlv_goods.scrollToPosition(1);
                click = false;
                dealposition(2);
                break;

        }
    }

    protected boolean isCover(View view) {
        boolean cover = false;
        Rect rect = new Rect();
        cover = view.getGlobalVisibleRect(rect);
        if (cover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                return !cover;
            }
        }
        return true;
    }

    private PopupWindow poppassword;

    private void showPasswordpop() {
        if (poppassword == null) {
            copypassword();
            View view = View.inflate(this, R.layout.pop_center_mall_password, null);
            poppassword = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    poppassword.dismiss();
                }
            });
            view.findViewById(R.id.ll_pop_password).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            TextView tvcontent = view.findViewById(R.id.tv_content);
            tvcontent.setText(command);
            view.findViewById(R.id.inCome_wxLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharepassword(SHARE_MEDIA.WEIXIN);
                }
            });
            view.findViewById(R.id.inCome_wxFriendLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharepassword(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
            });
            view.findViewById(R.id.inCome_qqLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareToQQ(command);
                }
            });
            view.findViewById(R.id.inCome_qqZoneLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharepassword(SHARE_MEDIA.QZONE);
                }
            });

            poppassword.showAtLocation(View.inflate(this, R.layout.activity_goods_detail, null), Gravity.CENTER, 0, 0);
        } else {
            poppassword.showAtLocation(View.inflate(this, R.layout.activity_goods_detail, null), Gravity.CENTER, 0, 0);
        }
    }

    private PopupWindow pop;

    //相机权限
    private void openpop() {
        RxPermissions.getInstance(this) // this = a Context
                .request(
//                        (读写ＳＤ卡权限)
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            showpop();
                            // PhotoUtils.photograph(UsermsgActivity.this);
                        } else {
                            // 最少有一个权限被拒绝
                            showToast("权限被拒绝，请重新选择");
                        }
                    }
                });
    }

    private void copypassword() {
        if (!TextUtils.isEmpty(command)) {
            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText(null, command));
        } else {
            showToast("复制失败,请重试");
        }
    }

    private void clearpassword() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    private void showpop() {
        if (pop == null) {
            final View view = View.inflate(this, R.layout.pop_goods_detail_code, null);
            pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.dismiss();
                }
            });
            final View rlpop = view.findViewById(R.id.rl_pop);
            rlpop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ImageView iv_goods = view.findViewById(R.id.iv_goods);
            ImageView iv_code = view.findViewById(R.id.iv_code);
            TextView tvcontent = view.findViewById(R.id.tv_content);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tv_price_old = view.findViewById(R.id.tv_price_old);
            TextView tvquan = view.findViewById(R.id.quanprice);
            TextView tv_quan = view.findViewById(R.id.tv_quan);
            if (dataBean != null) {
                if (!dataBean.coupon_price.equals("0")) {
                    tv_quan.setText("券后价");
                } else {
                    tv_quan.setText("折后价");
                }
                tvcontent.setText(dataBean.title);
                GlideUtils.loadpic(GoodsDetailActivity.this, iv_goods, dataBean.pic);
                tv_price.setText("现价：¥ " + dataBean.original_price);
                tvquan.setText("¥ " + dataBean.coupon_price);
                tv_price_old.setText("¥ " + dataBean.price);
                tmpFile = FileUtil.createFile("/images/", System.currentTimeMillis() + ".jpg");
                QRCodeUtil.createQRImage(dataBean.share_url, 240, 240, null, tmpFile.getAbsolutePath());
                GlideUtils.loadpic(GoodsDetailActivity.this, iv_code, tmpFile.getAbsolutePath());
            }
            view.findViewById(R.id.tv_pic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareurl(rlpop);
                    pop.dismiss();
                }
            });
            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels;     // 屏幕宽度（像素）
            int height = metric.heightPixels;   // 屏幕高度（像素）
            layoutView(rlpop, width, height);
            pop.showAtLocation(View.inflate(this, R.layout.activity_goods_detail, null), Gravity.CENTER, 0, 0);
        } else {
            pop.showAtLocation(View.inflate(this, R.layout.activity_goods_detail, null), Gravity.CENTER, 0, 0);
        }
    }

    private void layoutView(View v, int width, int height) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pop != null && pop.isShowing()) {
                pop.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //分享至qq
    public void shareToQQ(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.tencent.mobileqq");
        sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        try {
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("未安装QQ");
        }
    }

    public void shareurl(View view) {
        Bitmap bitmap = loadBitmapFromView(view);
//        Bitmap mark = BitmapFactory.decodeResource(getResources(), R.drawable.icon_shuiyin_bg);
//        Bitmap center = ImageUtils.setsize(mark, view, this);
//        Bitmap bp = ImageUtils.createWaterMaskLeftTop(this, bitmap, center, 0, 0);
        UMImage image = new UMImage(GoodsDetailActivity.this, bitmap);
        image.setThumb(image);
        new ShareAction((Activity) this)
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .open();
    }

    public void sharepassword(SHARE_MEDIA media) {
        new ShareAction(GoodsDetailActivity.this)
                .setPlatform(media)//传入平台
                .withText(command)
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            clearpassword();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t.getMessage().contains("2008")) {
                showToast("没有安装应用");
            }
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showToast("取消");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    private void dealdata(MallTaoBaoBean.DataBean info) {
        dataBean = info;
        coupon_id = info.coupon_id;
        if (info.pics != null) {
            initbanner(info.pics);
        }
        tv_goods_name.setText(info.title);
        if (!info.coupon_price.equals("0")) {
            tv_ticket_coupon.setText("券后价");
            rl_ticket.setVisibility(View.VISIBLE);
            tv_ticket.setText("领券购买");
            tv_ticket_price.setText(info.coupon_price);
            tv_ticket_time.setText(info.coupon_start_time + "-" + info.coupon_end_time);
        } else {
            tv_ticket.setText("立即购买");
            tv_ticket_coupon.setText("折后价");
            rl_ticket.setVisibility(View.GONE);
        }
        tv_price.setText("¥ " + info.price);
        tv_price_old.setText("¥ " + info.original_price);
        tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (TextUtils.isEmpty(info.official)) {
            ll_content.setVisibility(View.GONE);
        } else {
            ll_content.setVisibility(View.VISIBLE);
            tv_language.setText(info.official);
        }
        tv_gold_num.setText(info.anticipated_money + " 元");
        tv_discount.setText("+ " + info.anticipated_money + " 元");
        tv_rmb.setText(info.sales + " 人购买");
        tv_detail_share.setText("分享购买后可得" + info.anticipated_money + "元");
        adapter = new MallGoodsAdapter(R.layout.item_mall_goods_show, info.recommends, this);
        rlv_goods.setAdapter(adapter);
        command = info.tpwd;
        rlweb.loadUrl(info.desc_url);
        adapter.addHeaderView(ticketHeader);
        adapter.addHeaderView(rlweb);
        adapter.addHeaderView(header);
        adapter.addFooterView(footview);
        rlv_goods.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!detail && !click) {
                    if (!expends && isCover(ticketHeader) && isCover(header) && isCover(rlweb)) {
                        dealposition(1);
                    } else if (!expends && !isCover(ticketHeader)) {
                        dealposition(2);
                    }
                }
            }
        });
        rlv_goods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detail = false;
                return false;
            }
        });
    }
}
