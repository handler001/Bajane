package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/28
 */
public class BargainMoneyBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * id : 1
         * productPrice : 1
         * coinType : TCP
         * productNum : 500
         * totalAmt : 500
         */

        public String id;
        public String productPrice;
        public String coinType;
        public String productNum;
        public String totalAmt;
    }
}
