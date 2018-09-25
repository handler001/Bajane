package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/11
 */
public class MallGoodsBean {

    public String status;
    public DataBean data;
    public String is_ios;

    public static class DataBean {
        public List<BannerBean> banner;
        public List<FlashSaleBean> flash_sale;
        public List<AdsBean> ads;
        public List<GoodsDataBean> recommends;

        public static class BannerBean {
            /**
             * url : https://www.cgwlkj.cn/images/banner1.png
             * title : 邀请有奖
             * link : https://s.click.taobao.com/jkDnGRw
             */

            public String url;
            public String title;
            public String link;
        }

        public static class FlashSaleBean {
            /**
             * activity_start_time : 1970-01-01 08:00:00
             * id : 549239069755
             * title : 南极人袜子男中筒棉袜黑防臭长短袜薄款夏季男袜运动短筒低帮船袜
             * small_title : 【南极人15双】高档时尚精品棉袜
             * cid : 241
             * official : 南极人四季棉袜，精选优质面料，抗菌防臭，柔软透气吸汗，穿出您的风格与品位~速度抢
             * sales : 609712
             * pic : https://img.alicdn.com/bao/uAploaded/i1/2106525799/TB10DxZSFXXXXXqaXXXXXXXXXXX_!!0-item_pic.jpg_400x400q100.jpg
             * original_price : 16.80
             * price : 13.80
             * numIid : 549239069755
             * activity_type :
             * coupon_id : 80cdd17639f14a3ea6ae92068112245d
             * coupon_price : 3
             * commission_rate : 24.00%
             * integral : 3311
             * update_at : 2018-09-11 06:27:11
             * taobao_coupon_click_url : https://www.cgwlkj.cn/goods/coupon/item/549239069755.html
             * anticipated_money : 3.31
             * item_url : https://www.cgwlkj.cn/goods/tb/item/549239069755.html?caogenid=
             * share_url : https://www.cgwlkj.cn/goods/tb/item/549239069755.html?caogenid=
             */

            public String activity_start_time;
            public long id;
            public String title;
            public String small_title;
            public int cid;
            public String official;
            public int sales;
            public String pic;
            public String original_price;
            public String price;
            public String numIid;
            public String activity_type;
            public String coupon_id;
            public int coupon_price;
            public String commission_rate;
            public int integral;
            public String update_at;
            public String taobao_coupon_click_url;
            public String anticipated_money;
            public String item_url;
            public String share_url;
        }

        public static class AdsBean {
            /**
             * url : http://api.51cgcy.com/images/pic1.jpg
             * title : 工兵铲子情侣礼物多功能战术
             * link :
             */

            public String url;
            public String title;
            public String link;
        }
    }
}
