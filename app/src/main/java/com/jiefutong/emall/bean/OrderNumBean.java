package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/8/24
 */
public class OrderNumBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * waitPayNum : 37
         * waitSendNum : 7
         * waitReceiveNum : 0
         */

        public int waitPayNum;
        public int waitSendNum;
        public int waitReceiveNum;
    }
}
