package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/10
 */
public class GoodsShowBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * id : 23
         * goodIcon : http://img.51cgcy.com/futai//uploads/image/20180728/c1cf2710b89ab8356fd8fa19489095ae.jpg
         * goodPrice : 1200
         * goodsName : 哈哈17:53
         */

        public String id;
        public String goodIcon;
        public String goodPrice;
        public String goodsName;
    }
}
