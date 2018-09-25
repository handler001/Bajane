package com.jiefutong.emall.bean;

import java.io.Serializable;

/**
 * @Author l
 * @Date 2018/7/28
 */
public class MoneyTakeBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean implements Serializable{
        /**
         * realAmt : 10
         * rate : 0.00
         * amt : 10
         */

        public String realAmt;
        public String rate;
        public String amt;
        public String msg;
    }
}
