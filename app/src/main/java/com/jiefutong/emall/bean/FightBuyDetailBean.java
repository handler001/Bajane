package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/12
 */
public class FightBuyDetailBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {

        public int create_at;
        public String goods_id;
        public String goods_name;
        public String goods_desc;
        public String goods_thumbnail_url;
        public String goods_image_url;
        public String sold_quantity;
        public int min_group_price;
        public int min_normal_price;
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
        public String coupon_start_time;
        public String coupon_end_time;
        public int promotion_rate;
        public double goods_eval_score;
        public int goods_eval_count;
        public int cat_id;
        public int avg_desc;
        public int avg_lgst;
        public int avg_serv;
        public double desc_pct;
        public double lgst_pct;
        public double serv_pct;
        public String item_url;
        public String share_url;
        public String commission_rate;
        public String anticipated_money;
        public String coupon_price;
        public String price;
        public String original_price;
        public String tpwd;
        public List<String> goods_gallery_urls;
        public List<Integer> opt_ids;
        public List<Integer> cat_ids;
        public List<FightBuyPddBean.DataMapBean> recommends;
    }
}
