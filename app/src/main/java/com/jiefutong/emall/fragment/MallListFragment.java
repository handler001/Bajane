package com.jiefutong.emall.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiefutong.emall.R;
import com.jiefutong.emall.activity.PinDuoDuoActivity;
import com.jiefutong.emall.adapter.MallGoodsAdapter;
import com.jiefutong.emall.bean.GoodsDataBean;
import com.jiefutong.emall.bean.MallGoodsBean;
import com.jiefutong.emall.utils.GlideUtils;
import com.jiefutong.emall.utils.JsonUtil;
import com.lzy.okgo.OkGo;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author l
 * @Date 2018/9/11
 */
public class MallListFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private RecyclerView mRlv;
    private BaseQuickAdapter adapter;
    private SwipeRefreshLayout srl;
    private View header;
    private ArrayList<GoodsDataBean> goods = new ArrayList<>();
    private BannerViewPager mLoopViewpager;
    private ZoomIndicator mBottomScaleLayout;
    private LinearLayout mLlAll;
    private LinearLayout mLlDiscount;
    private LinearLayout mLlMeizhuang;
    private LinearLayout mLlMuying;
    private LinearLayout mLlHome;
    private LinearLayout mLlMan;
    private LinearLayout mLlWoman;
    private LinearLayout mLlSheet;
    private LinearLayout mLlNeiyi;
    private LinearLayout mLlEtc;
    public PercentRelativeLayout prl_fight_buy;
    private String datas = "{\"status\":\"success\",\"data\":{\"banner\":[{\"url\":\"https://www.cgwlkj.cn/images/banner1.png\",\"title\":\"邀请有奖\",\"link\":\"https://s.click.taobao.com/jkDnGRw\"},{\"url\":\"https://www.cgwlkj.cn/images/banner2.png\",\"title\":\"天猫福利\",\"link\":\"https://s.click.taobao.com/tA0nGRw\"},{\"url\":\"https://www.cgwlkj.cn/images/banner3.png\",\"title\":\"飞猪旅游\",\"link\":\"https://s.click.taobao.com/34jmGRw\"}],\"flash_sale\":[{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":549239069755,\"title\":\"南极人袜子男中筒棉袜黑防臭长短袜薄款夏季男袜运动短筒低帮船袜\",\"small_title\":\"【南极人15双】高档时尚精品棉袜\",\"cid\":241,\"official\":\"南极人四季棉袜，精选优质面料，抗菌防臭，柔软透气吸汗，穿出您的风格与品位~速度抢\",\"sales\":609712,\"pic\":\"https://img.alicdn.com/bao/uAploaded/i1/2106525799/TB10DxZSFXXXXXqaXXXXXXXXXXX_!!0-item_pic.jpg_400x400q100.jpg\",\"original_price\":\"16.80\",\"price\":\"13.80\",\"numIid\":\"549239069755\",\"activity_type\":\"\",\"coupon_id\":\"80cdd17639f14a3ea6ae92068112245d\",\"coupon_price\":3,\"commission_rate\":\"24.00%\",\"integral\":3311,\"update_at\":\"2018-09-11 06:27:11\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/549239069755.html\",\"anticipated_money\":\"3.31\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/549239069755.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/549239069755.html?caogenid=\"},{\"activity_start_time\":\"0000-00-00 00:00:00\",\"id\":570891200627,\"title\":\"肌玉正品2KG瓶装薰衣草洗衣液 深层洁净 去除顽固污渍 不含荧光剂\",\"small_title\":\"肌玉正品2KG瓶装薰衣草洗衣液 深层洁净 去除顽固污渍 不含荧光剂\",\"cid\":0,\"official\":\"\",\"sales\":480581,\"pic\":\"https://img.alicdn.com/bao/uploaded/i3/2734492582/TB2B53SEHGYBuNjy0FoXXciBFXa_!!2734492582.jpg_400x400q100.jpg\",\"original_price\":\"19.90\",\"price\":\"9.90\",\"numIid\":\"570891200627\",\"activity_type\":\"\",\"coupon_id\":\"5f1c720f428f4df68e0b46723248809c\",\"coupon_price\":10,\"commission_rate\":\"12.00%\",\"integral\":2384,\"update_at\":\"2018-09-06 21:42:04\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/570891200627.html\",\"anticipated_money\":\"2.38\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/570891200627.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/570891200627.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":560280718756,\"title\":\"吉登电动牙刷成人家用非充电式声波自动软毛牙刷防水电池情侣牙刷\",\"small_title\":\"【吉登】声波电动牙刷免充电\",\"cid\":494,\"official\":\"【爆款返场】情侣必“BUY”！全身水洗，IPX7级防水/3档调节/智能提醒，美国杜邦刷毛，低耗能免充电，360度清洁口腔无死角，美白防蛀防口臭，送家人首选！【1年以换代修】\",\"sales\":412901,\"pic\":\"https://img.alicdn.com/imgextra/i1/42843867/TB2WEKBH79WBuNjSspeXXaz5VXa_!!42843867.jpg_400x400q100.jpg\",\"original_price\":\"121.90\",\"price\":\"11.90\",\"numIid\":\"560280718756\",\"activity_type\":\"\",\"coupon_id\":\"31c3d420edaa4d9b8ef48a940c03919c\",\"coupon_price\":110,\"commission_rate\":\"24.00%\",\"integral\":2856,\"update_at\":\"2018-09-10 11:30:10\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/560280718756.html\",\"anticipated_money\":\"2.86\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/560280718756.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/560280718756.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":566274514615,\"title\":\"日式室内家用软底拖鞋浴室洗澡防滑情侣外穿凉拖鞋女夏季男家居鞋\",\"small_title\":\"【五金冠】日式室内软底情侣拖鞋\",\"cid\":494,\"official\":\"5金冠店，累计好评1000万+拖鞋，值得信赖！夏季防滑拖鞋，多种款式可选，【2.8】质量炒鸡好，这个价绝对低到没朋友了，错过拍大腿！\",\"sales\":386149,\"pic\":\"https://gd2.alicdn.com/imgextra/i2/849090736/TB29B7YxVuWBuNjSszbXXcS7FXa_!!849090736.jpg_400x400q100.jpg\",\"original_price\":\"4.80\",\"price\":\"2.80\",\"numIid\":\"566274514615\",\"activity_type\":\"\",\"coupon_id\":\"ce65267027f54b6b958f5f6a73048ea7\",\"coupon_price\":2,\"commission_rate\":\"24.00%\",\"integral\":672,\"update_at\":\"2018-09-08 15:06:34\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/566274514615.html\",\"anticipated_money\":\"0.67\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/566274514615.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/566274514615.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":559680679226,\"title\":\"碧c婴儿湿巾新生儿宝宝湿纸巾手口屁专用80抽5包100成人批发带盖\",\"small_title\":\"爆款疯抢【碧C】湿巾80抽*5包\",\"cid\":484,\"official\":\"【全网热销碧C大品牌】源自英国，亲肤柔棉，无荧光无香精，温和水润，抑菌洁净，安全到可以“吃”的湿巾，全网热销~！\",\"sales\":332226,\"pic\":\"https://img.alicdn.com/imgextra/i1/3087060159/TB2ydpoe6ihSKJjy0FlXXadEXXa_!!3087060159.jpg_400x400q100.jpg\",\"original_price\":\"18.90\",\"price\":\"15.90\",\"numIid\":\"559680679226\",\"activity_type\":\"\",\"coupon_id\":\"a0c2aab2648e4f54bd71e5c89311253b\",\"coupon_price\":3,\"commission_rate\":\"16.00%\",\"integral\":2544,\"update_at\":\"2018-09-10 11:32:12\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/559680679226.html\",\"anticipated_money\":\"2.54\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/559680679226.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/559680679226.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":541418255867,\"title\":\"蟑螂药蟑螂屋胶饵灭蟑螂无毒厨房家用强力灭蟑清杀蟑螂克星全窝端\",\"small_title\":\"【灭蟑爆款】安全强力蟑螂克星药胶饵\",\"cid\":494,\"official\":\"KFC，必胜客都在用的灭蟑神器，一只取食，全巢消灭，安全无害，无特殊气味，蟑螂不死，七天无理由退货。【40W买家好评认可】\",\"sales\":317607,\"pic\":\"https://img.alicdn.com/imgextra/i2/2832398634/TB2dCbpb9uJ.eBjy0FgXXXBBXXa_!!2832398634.jpg_400x400q100.jpg\",\"original_price\":\"16.80\",\"price\":\"6.80\",\"numIid\":\"541418255867\",\"activity_type\":\"\",\"coupon_id\":\"47451543a5ac4167a331ac1d34466cc2\",\"coupon_price\":10,\"commission_rate\":\"32.00%\",\"integral\":2176,\"update_at\":\"2018-09-10 11:26:16\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/541418255867.html\",\"anticipated_money\":\"2.18\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/541418255867.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/541418255867.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":564722884180,\"title\":\"小白鞋神器一擦白清洁剂白鞋清洗去黄边增白擦鞋洗鞋喷雾刷鞋去污\",\"small_title\":\"【2瓶装】小白鞋清洁剂\",\"cid\":494,\"official\":\"神奇去污去黄，神器清洁，活性环保配方，高效去污，一涂一擦，免水洗，两步搞定，想白就是这么简单！就是这么简单\",\"sales\":313212,\"pic\":\"https://img.alicdn.com/imgextra/i3/1014255606/TB2MDrBboOWBKNjSZKzXXXfWFXa_!!1014255606.jpg_400x400q100.jpg\",\"original_price\":\"15.80\",\"price\":\"5.80\",\"numIid\":\"564722884180\",\"activity_type\":\"\",\"coupon_id\":\"b515f7454f49466cabcc0ec3ba4f7bd6\",\"coupon_price\":10,\"commission_rate\":\"24.00%\",\"integral\":1392,\"update_at\":\"2018-09-10 11:23:33\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/564722884180.html\",\"anticipated_money\":\"1.39\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/564722884180.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/564722884180.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":541089966583,\"title\":\"嘻螺会广西螺蛳粉柳州特产正宗螺丝粉方便面米线螺狮粉速食装包邮\",\"small_title\":\"【买1送1】嘻螺会广西柳州特产正宗螺蛳粉\",\"cid\":493,\"official\":\"精选食材，配料齐全，酸辣鲜爽，Q弹劲道，爽滑入味，健康营养，舌尖上的中国特别推荐美食，让你回味无穷，买一包送一包！\",\"sales\":276884,\"pic\":\"https://img.alicdn.com/imgextra/i2/3023094052/TB2yM7UeYtlpuFjSspfXXXLUpXa_!!3023094052.jpg_400x400q100.jpg\",\"original_price\":\"12.98\",\"price\":\"9.98\",\"numIid\":\"541089966583\",\"activity_type\":\"\",\"coupon_id\":\"3cd3df6fd4ac4417917d12b65ff550e0\",\"coupon_price\":3,\"commission_rate\":\"16.01%\",\"integral\":1600,\"update_at\":\"2018-09-10 11:30:49\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/541089966583.html\",\"anticipated_money\":\"1.60\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/541089966583.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/541089966583.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":540790849058,\"title\":\"langtian浪天电动牙刷成人款家用非充电式声波自动牙刷软毛防水\",\"small_title\":\"【浪天】电动牙刷非充电式声波\",\"cid\":494,\"official\":\"厉害了！【闭着眼也要抢系列】名副其实【爆售180万+】全网销量爆款冠军，美国杜邦刷毛，全身水洗，高效清洁无死角，清新口气，洁白牙齿，送家人首选【1年以换代修】售后无忧\",\"sales\":273569,\"pic\":\"https://img.alicdn.com/imgextra/i3/2635214986/TB26u26uYGYBuNjy0FoXXciBFXa_!!2635214986.jpg_400x400q100.jpg\",\"original_price\":\"120.90\",\"price\":\"10.90\",\"numIid\":\"540790849058\",\"activity_type\":\"\",\"coupon_id\":\"8c31dfcc7cb04cc390bc2554913dba65\",\"coupon_price\":110,\"commission_rate\":\"24.08%\",\"integral\":2624,\"update_at\":\"2018-09-10 11:25:27\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/540790849058.html\",\"anticipated_money\":\"2.62\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/540790849058.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/540790849058.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":37016930337,\"title\":\"居家凉拖鞋女夏季室内防滑男家居软底浴室洗澡家用外穿拖鞋情侣\",\"small_title\":\"【12年五金冠】居家情侣防滑拖鞋\",\"cid\":494,\"official\":\"【12年五金冠店铺】抢疯了！！抢疯了！！五皇冠店铺！！热销600万双！！99万评价！！买过都说好！福利好货-居家亲子凉拖鞋，款式繁多，数量有限速来请购！抢到就是赚到！！\",\"sales\":269768,\"pic\":\"https://img.alicdn.com/imgextra/i2/3727542779/O1CN011WOp7MZDheBnLbE_!!3727542779.jpg_400x400q100.jpg\",\"original_price\":\"4.90\",\"price\":\"3.90\",\"numIid\":\"37016930337\",\"activity_type\":\"\",\"coupon_id\":\"0dff7c7868f044a8abdcb949b51c65f9\",\"coupon_price\":1,\"commission_rate\":\"24.00%\",\"integral\":936,\"update_at\":\"2018-09-10 11:30:01\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/37016930337.html\",\"anticipated_money\":\"0.94\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/37016930337.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/37016930337.html?caogenid=\"}],\"ads\":[{\"url\":\"http://api.51cgcy.com/images/pic1.jpg\",\"title\":\"工兵铲子情侣礼物多功能战术\",\"link\":\"\"},{\"url\":\"http://api.51cgcy.com/images/pic2.jpg\",\"title\":\"工兵铲子情侣礼物多功能战术\",\"link\":\"\"},{\"url\":\"http://api.51cgcy.com/images/pic3.jpg\",\"title\":\"工兵铲子情侣礼物多功能战术\",\"link\":\"\"}],\"recommends\":[{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":530285374249,\"title\":\"万圣节服装化妆舞会cos海盗成人女巫师服巫婆女皇服装鬼新娘王后\",\"small_title\":\"詹妮【万圣节】化妆舞会服装\",\"cid\":484,\"official\":\"成人儿童披风多款可选，引领万圣服饰潮流，巫婆斗篷披风套装，适用舞台化妆舞会表演，精选优质材料，炫彩缤纷，让自己留下一个欢快的记忆【赠运费险】\",\"sales\":48,\"pic\":\"https://img.alicdn.com/imgextra/i3/TB1LiayNpXXXXalaFXXXXXXXXXX_!!0-item_pic.jpg_400x400q100.jpg\",\"original_price\":\"75.00\",\"price\":\"25.00\",\"numIid\":\"530285374249\",\"activity_type\":\"\",\"coupon_id\":\"09ca4f447b26443e99159715930b03b4\",\"coupon_price\":50,\"commission_rate\":\"24.00%\",\"integral\":6000,\"update_at\":\"2018-09-11 16:16:44\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/530285374249.html\",\"anticipated_money\":\"6.00\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/530285374249.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/530285374249.html?caogenid=\"},{\"activity_start_time\":\"0000-00-00 00:00:00\",\"id\":570599149106,\"title\":\"智能机器人小超儿童早教伴读机器人语音对话儿童学习声控小智帅哥\",\"small_title\":\"智能机器人小超儿童早教伴读机器人语音对话儿童学习声控小智帅哥\",\"cid\":0,\"official\":\"\",\"sales\":16,\"pic\":\"https://img.alicdn.com/bao/uploaded/i2/929149030/TB2d3KGBqmWBuNjy1XaXXXCbXXa_!!929149030.jpg_400x400q100.jpg\",\"original_price\":\"599.00\",\"price\":\"499.00\",\"numIid\":\"570599149106\",\"activity_type\":\"\",\"coupon_id\":\"2ce6701fc89a41818e99b0acef43b6e1\",\"coupon_price\":100,\"commission_rate\":\"18.42%\",\"integral\":110311,\"update_at\":\"2018-09-11 12:15:59\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/570599149106.html\",\"anticipated_money\":\"110.31\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/570599149106.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/570599149106.html?caogenid=\"},{\"activity_start_time\":\"0000-00-00 00:00:00\",\"id\":571274100996,\"title\":\"小智伴语音人工智能对话机器人男女儿童早教故事机高科技学习玩具\",\"small_title\":\"小智伴语音人工智能对话机器人男女儿童早教故事机高科技学习玩具\",\"cid\":0,\"official\":\"\",\"sales\":1,\"pic\":\"https://img.alicdn.com/bao/uploaded/i3/1725280184/TB2ZDxCy4SYBuNjSsphXXbGvVXa_!!1725280184.jpg_400x400q100.jpg\",\"original_price\":\"858.00\",\"price\":\"808.00\",\"numIid\":\"571274100996\",\"activity_type\":\"\",\"coupon_id\":\"85e34b43dd444cb8962c17a908bde009\",\"coupon_price\":50,\"commission_rate\":\"6.40%\",\"integral\":54912,\"update_at\":\"2018-09-11 12:15:59\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/571274100996.html\",\"anticipated_money\":\"54.91\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/571274100996.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/571274100996.html?caogenid=\"},{\"activity_start_time\":\"0000-00-00 00:00:00\",\"id\":573532967174,\"title\":\"ZIB智伴智能机器人儿童玩具对话高科技家庭陪伴早教机小智伴正品\",\"small_title\":\"ZIB智伴智能机器人儿童玩具对话高科技家庭陪伴早教机小智伴正品\",\"cid\":0,\"official\":\"\",\"sales\":2,\"pic\":\"https://img.alicdn.com/bao/uploaded/i3/1039824478/TB2zuHHDuuSBuNjy1XcXXcYjFXa_!!1039824478.jpg_400x400q100.jpg\",\"original_price\":\"858.00\",\"price\":\"848.00\",\"numIid\":\"573532967174\",\"activity_type\":\"\",\"coupon_id\":\"8dbdc1ee9c5c4cc5a97bb8e3c384402e\",\"coupon_price\":10,\"commission_rate\":\"4.00%\",\"integral\":34320,\"update_at\":\"2018-09-11 12:15:59\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/573532967174.html\",\"anticipated_money\":\"34.32\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/573532967174.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/573532967174.html?caogenid=\"},{\"activity_start_time\":\"0000-00-00 00:00:00\",\"id\":574902136138,\"title\":\"小智伴智能机器人官网大帅小正教育5.0正品高科技唱歌跳舞早教机\",\"small_title\":\"小智伴智能机器人官网大帅小正教育5.0正品高科技唱歌跳舞早教机\",\"cid\":0,\"official\":\"\",\"sales\":10,\"pic\":\"https://img.alicdn.com/bao/uploaded/i1/1963612114/O1CN011RUFje2s2tsDCRO_!!1963612114.jpg_400x400q100.jpg\",\"original_price\":\"648.00\",\"price\":\"568.00\",\"numIid\":\"574902136138\",\"activity_type\":\"\",\"coupon_id\":\"71267c9a0f3c48cb906608f67fc005ea\",\"coupon_price\":80,\"commission_rate\":\"4.00%\",\"integral\":25920,\"update_at\":\"2018-09-11 12:15:59\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/574902136138.html\",\"anticipated_money\":\"25.92\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/574902136138.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/574902136138.html?caogenid=\"},{\"activity_start_time\":\"0000-00-00 00:00:00\",\"id\":576429175257,\"title\":\"乐源小智伴儿童智能机器人玩具对话聊天跳舞高科技小哈早教机正品\",\"small_title\":\"乐源小智伴儿童智能机器人玩具对话聊天跳舞高科技小哈早教机正品\",\"cid\":0,\"official\":\"\",\"sales\":3,\"pic\":\"https://img.alicdn.com/bao/uploaded/i3/1963612114/O1CN011RUFjnhGyWgLX7m_!!1963612114.jpg_400x400q100.jpg\",\"original_price\":\"1680.00\",\"price\":\"1480.00\",\"numIid\":\"576429175257\",\"activity_type\":\"\",\"coupon_id\":\"f1991ed7ce064289b9b620b507cc024b\",\"coupon_price\":200,\"commission_rate\":\"4.00%\",\"integral\":67200,\"update_at\":\"2018-09-11 12:15:59\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/576429175257.html\",\"anticipated_money\":\"67.20\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/576429175257.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/576429175257.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":569957102763,\"title\":\"板鞋男2018秋季潮牌男鞋韩版百搭小白鞋真皮透气小脏鞋男士休闲鞋\",\"small_title\":\"小白鞋真皮透气小脏鞋男士休闲鞋\",\"cid\":403,\"official\":\"我们只卖真头层牛皮小白鞋，你的鞋柜至少要有一双真头层牛皮小白鞋，鞋面全部采用真头层牛皮，上脚柔软，舒适透气，包边内缝线设计，整体线条感更流畅，更简约时尚！\",\"sales\":100,\"pic\":\"https://img.alicdn.com/imgextra/i4/187417756/TB24A4ZrYGYBuNjy0FoXXciBFXa_!!187417756.jpg_400x400q100.jpg\",\"original_price\":\"298.00\",\"price\":\"278.00\",\"numIid\":\"569957102763\",\"activity_type\":\"\",\"coupon_id\":\"ee18d97b1449420399564b477524a139\",\"coupon_price\":20,\"commission_rate\":\"12.00%\",\"integral\":33360,\"update_at\":\"2018-09-11 09:52:18\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/569957102763.html\",\"anticipated_money\":\"33.36\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/569957102763.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/569957102763.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":567469138252,\"title\":\"人本帆布鞋女2018新款学生平底小白鞋原宿百搭ulzzang休闲板鞋\",\"small_title\":\"人本帆布鞋新款学生平底小白鞋原宿百搭板鞋\",\"cid\":403,\"official\":\"松紧带设计，优雅大方，舒适鞋头，皮质柔软，不易皱，耐折轻便，橡胶大底，简约美观，显瘦百搭，条纹装饰，多色选择。\",\"sales\":12,\"pic\":\"https://img.alicdn.com/imgextra/i2/2972229602/TB2c7TItVkoBKNjSZFEXXbrEVXa_!!2972229602-0-item_pic.jpg_400x400q100.jpg\",\"original_price\":\"69.00\",\"price\":\"59.00\",\"numIid\":\"567469138252\",\"activity_type\":\"\",\"coupon_id\":\"dddd968e2a8e4edfb88eb24c203969c4\",\"coupon_price\":10,\"commission_rate\":\"24.00%\",\"integral\":14160,\"update_at\":\"2018-09-11 09:51:00\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/567469138252.html\",\"anticipated_money\":\"14.16\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/567469138252.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/567469138252.html?caogenid=\"},{\"activity_start_time\":\"1970-01-01 08:00:00\",\"id\":571150266685,\"title\":\"女士内裤女100%纯棉裆大码中腰全棉质性感蕾丝少女无痕抗菌三角裤\",\"small_title\":\"黛尔佳人旗舰店【纯棉中腰内裤】7条\",\"cid\":241,\"official\":\"95%纯棉面料，舒适亲肤，甜美小清新，穿着好看不闷热【买5条送2条】一条不到3块钱！给力到上天！\",\"sales\":228655,\"pic\":\"https://img.alicdn.com/imgextra/i4/1810380706/TB21wUbb4UaBuNjt_iGXXXlkFXa_!!1810380706-0-item_pic.jpg_400x400q100.jpg\",\"original_price\":\"29.80\",\"price\":\"19.80\",\"numIid\":\"571150266685\",\"activity_type\":\"\",\"coupon_id\":\"036cec2a687440aa8a54fd2a3e48b71d\",\"coupon_price\":10,\"commission_rate\":\"16.00%\",\"integral\":3168,\"update_at\":\"2018-09-11 08:08:10\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/571150266685.html\",\"anticipated_money\":\"3.17\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/571150266685.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/571150266685.html?caogenid=\"},{\"activity_start_time\":\"0000-00-00 00:00:00\",\"id\":17011320904,\"title\":\"乐事薯片桶装无限翡翠黄瓜味104g休闲食品零食小吃膨化食品下午茶\",\"small_title\":\"乐事薯片桶装无限翡翠黄瓜味104g休闲食品零食小吃膨化食品下午茶\",\"cid\":0,\"official\":\"\",\"sales\":57144,\"pic\":\"https://img.alicdn.com/bao/uploaded/i1/725677994/TB1VShlnxPI8KJjSspoXXX6MFXa_!!0-item_pic.jpg_400x400q100.jpg\",\"original_price\":\"8.90\",\"price\":\"5.90\",\"numIid\":\"17011320904\",\"activity_type\":\"\",\"coupon_id\":\"bd6a508e903443749a569343b58b414f\",\"coupon_price\":3,\"commission_rate\":\"1.60%\",\"integral\":144,\"update_at\":\"2018-09-11 07:37:34\",\"taobao_coupon_click_url\":\"https://www.cgwlkj.cn/goods/coupon/item/17011320904.html\",\"anticipated_money\":\"0.14\",\"item_url\":\"https://www.cgwlkj.cn/goods/tb/item/17011320904.html?caogenid=\",\"share_url\":\"https://www.cgwlkj.cn/goods/tb/item/17011320904.html?caogenid=\"}]},\"is_ios\":\"T\"}";

    @Override
    protected void listener() {
        mLlAll.setOnClickListener(this);
        mLlDiscount.setOnClickListener(this);
        mLlMeizhuang.setOnClickListener(this);
        mLlMuying.setOnClickListener(this);
        mLlHome.setOnClickListener(this);
        mLlMan.setOnClickListener(this);
        mLlWoman.setOnClickListener(this);
        mLlSheet.setOnClickListener(this);
        mLlNeiyi.setOnClickListener(this);
        mLlEtc.setOnClickListener(this);
        prl_fight_buy.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        MallGoodsBean bean = JsonUtil.parseObject(datas, MallGoodsBean.class);
        adapter.addData(bean.data.recommends);
        initbanner(bean.data.banner);
    }

    @Override
    protected void intiview(View view) {
        header = View.inflate(mctx, R.layout.mall_frag_header, null);
        mBottomScaleLayout = header.findViewById(R.id.bottom_scale_layout);
        mLoopViewpager = header.findViewById(R.id.loop_viewpager);
        mLlAll = header.findViewById(R.id.ll_all);
        mLlDiscount = header.findViewById(R.id.ll_discount);
        mLlMeizhuang = header.findViewById(R.id.ll_meizhuang);
        mLlMuying = header.findViewById(R.id.ll_muying);
        mLlHome = header.findViewById(R.id.ll_home);
        mLlMan = header.findViewById(R.id.ll_man);
        mLlWoman = header.findViewById(R.id.ll_woman);
        mLlSheet = header.findViewById(R.id.ll_sheet);
        mLlNeiyi = header.findViewById(R.id.ll_neiyi);
        mLlEtc = header.findViewById(R.id.ll_etc);
        mIvBack = view.findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.GONE);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText("商城");
        mRlv = view.findViewById(R.id.rlv_goods);
        mRlv.setLayoutManager(new GridLayoutManager(mctx, 2));
        srl = view.findViewById(R.id.srl);
        adapter = new MallGoodsAdapter(R.layout.item_mall_goods_show, goods, mctx);
        mRlv.setAdapter(adapter);
        adapter.addHeaderView(header);
        srl.setEnabled(false);
        prl_fight_buy = header.findViewById(R.id.prl_fight_buy);
    }

    @Override
    protected int getViewId() {
        return R.layout.main_fragment_mall;
    }

    private void initbanner(final List<MallGoodsBean.DataBean.BannerBean> dataMap) {
        PageBean bean = new PageBean.Builder<MallGoodsBean.DataBean.BannerBean>()
                .setDataObjects(dataMap)
                .setIndicator(mBottomScaleLayout)
                .builder();
        mLoopViewpager.setPageTransformer(false, new MzTransformer());
        mLoopViewpager.setPageListener(bean, R.layout.item_banner_pic, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                ImageView mpic = view.findViewById(R.id.iv_pic);
                final MallGoodsBean.DataBean.BannerBean dataMapBean = (MallGoodsBean.DataBean.BannerBean) o;
                GlideUtils.loadpic(mctx, mpic, dataMapBean.url);
                mpic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_etc:
                showToast("开发中");
                break;
            case R.id.ll_all:
                showToast("开发中");
                break;
            case R.id.ll_meizhuang:
                showToast("开发中");
                break;
            case R.id.ll_muying:
                showToast("开发中");
                break;
            case R.id.ll_home:
                showToast("开发中");
                break;
            case R.id.ll_man:
                showToast("开发中");
                break;
            case R.id.ll_woman:
                showToast("开发中");
                break;
            case R.id.ll_neiyi:
                showToast("开发中");
                break;
            case R.id.ll_sheet:
                showToast("开发中");
                break;
            case R.id.ll_discount:
                showToast("开发中");
                break;
            case R.id.prl_fight_buy:
                startActivity(new Intent(getActivity(), PinDuoDuoActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
