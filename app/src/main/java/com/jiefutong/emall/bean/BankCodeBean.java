package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/25
 */
public class BankCodeBean {


    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * id : 1
         * wxBankId : 1002
         * wxBankName : 工商银行
         */

        public String id;
        public String wxBankId;
        public String wxBankName;
    }
}
