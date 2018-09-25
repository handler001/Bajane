package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/7/25
 */
public class BankCardBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * id : 486
         * bankName : 农业银行
         * bankCardNo : 11111111
         * bankCode : 1005
         */
        public boolean transPasswordFlag;
        public String id;
        public String bankName;
        public String bankCardNo;
        public String bankCode;
    }
}
