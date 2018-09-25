package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/8/10
 */
public class RefundPriceBean {

    /**
     * code : 200
     * message : 请求成功
     * dataMap : {"postage":0,"amt":686}
     */

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * postage : 0
         * amt : 686
         */

        public String postage;
        public String amt;
    }
}
