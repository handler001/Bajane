package com.jiefutong.emall.bean;

import java.io.Serializable;

/**
 * @Author l
 * @Date 2018/8/22
 */
public class BalanceExchangeBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean implements Serializable {
        /**
         * factAmt : 10
         * fee : 10
         * amt : 20
         */

        public String factAmt;
        public String fee;
        public String amt;
    }
}
