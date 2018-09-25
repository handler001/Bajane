package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/8/7
 */
public class RateInfoBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * realAmt : 99
         * rate : 1
         */

        public double realAmt;
        public double rate;
    }
}
