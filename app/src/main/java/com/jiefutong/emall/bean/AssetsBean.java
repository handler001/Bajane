package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/7/25
 */
public class AssetsBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * Coupon : 0
         * wallet : 9.26
         * silverIcon : 0
         * integral : 0
         * goodIcon : 0
         */

        public int Coupon;
        public String wallet;
        public String silverIcon;
        public String integral;
        public String goodIcon;
        public int iconFlag;
    }
}
