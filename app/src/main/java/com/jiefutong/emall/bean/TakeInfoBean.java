package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/8/24
 */
public class TakeInfoBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * payType : 1
         * money : 1.00
         * bankCardNo : 1003
         * remark : null
         * id : 157
         * payStatus : 1
         * flowId : 2018071200994827607
         */

        public int payType;
        public String money;
        public String bankCardNo;
        public String remark;
        public int id;
        public int payStatus;
        public String flowId;
        public String payTypeDesc;
    }
}
