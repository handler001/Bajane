package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/12
 */
public class FightBuyPddBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * create_at : 1536511174
         * goods_id : 2878575645
         * goods_name : 【单件/两件装】秋季新歀长袖t恤男士圆领休恤春季上衣服男装潮流
         * goods_desc : null
         * goods_thumbnail_url : http://t00img.yangkeduo.com/goods/images/2018-09-10/51a6b3dadd8837ba0c2e4b57150b4a72.jpeg
         * goods_image_url :
         * goods_gallery_urls : null
         * sold_quantity : 5547
         * min_group_price : 1980
         * min_normal_price : 3100
         * mall_name : 翰林文艺男
         * merchant_type : 1
         * category_id : 743
         * category_name : 男装
         * opt_id : 743
         * opt_name : 男装
         * mall_cps : 1
         * has_coupon : true
         * coupon_min_order_amount : 1000
         * coupon_discount : 1000
         * coupon_total_quantity : 50000
         * coupon_remain_quantity : 48890
         * coupon_start_time : 1536508800
         * coupon_end_time : 1537199999
         * promotion_rate : 500
         * goods_eval_score : 0
         * goods_eval_count : 0
         * cat_id : 0
         * avg_desc : 463
         * avg_lgst : 466
         * avg_serv : 466
         * desc_pct : 0.2986
         * lgst_pct : 0.3215
         * serv_pct : 0.3074
         * opt_ids : [225,2577,743,12,350,2575]
         * cat_ids : [239,246,435,0]
         */

        public int create_at;
        public String goods_id;
        public String goods_name;
        public String goods_desc;
        public String goods_thumbnail_url;
        public String goods_image_url;
        public String goods_gallery_urls;
        public int sold_quantity;
        public int min_group_price;
        public String min_normal_price;
        public String mall_name;
        public int merchant_type;
        public int category_id;
        public String category_name;
        public int opt_id;
        public String opt_name;
        public int mall_cps;
        public boolean has_coupon;
        public int coupon_min_order_amount;
        public int coupon_discount;
        public int coupon_total_quantity;
        public int coupon_remain_quantity;
        public int coupon_start_time;
        public int coupon_end_time;
        public int promotion_rate;
        public int goods_eval_score;
        public int goods_eval_count;
        public int cat_id;
        public int avg_desc;
        public int avg_lgst;
        public int avg_serv;
        public double desc_pct;
        public double lgst_pct;
        public double serv_pct;
        public List<Integer> opt_ids;
        public List<Integer> cat_ids;
        public String original_price;
        public String price;
        public String anticipated_money;
        public String commission_rate;
        public String coupon_price;
    }
}
