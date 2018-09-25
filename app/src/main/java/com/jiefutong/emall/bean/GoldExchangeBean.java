package com.jiefutong.emall.bean;

import java.io.Serializable;

/**
 * @Author l
 * @Date 2018/7/27
 */
public class GoldExchangeBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean implements Serializable{
        /**
         * couponNum : 0
         * fee : 0
         * realGoodIcon : 0
         * goodIcon : 0
         */

        public String couponNum;
        public String fee;
        public String realGoodIcon;
        public String goodIcon;
    }
}
