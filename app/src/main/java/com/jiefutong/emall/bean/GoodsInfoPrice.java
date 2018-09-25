package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/7/25
 */
public class GoodsInfoPrice {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * categoryType : 9_11
         * goodsImg : http://www.bjn.com/uploads/image/20180725\9bc89901eb2cb951d01eb07fb295c564.jpg
         * goodsPrice : 1222
         * stockNum : 100
         * categoryText : xl_green
         * imgBase : http://img.51cgcy.com/futai/
         */

        public String categoryType;
        public String goodsImg;
        public String goodsPrice;
        public String stockNum;
        public String categoryText;
        public String imgBase;
    }
}
