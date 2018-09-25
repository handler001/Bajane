package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/8/23
 */
public class RateFeeBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * date : 1:付款到账时效为1-3日，最快次日到账
         * fee : 2:每笔按付款金额收取手续费，按金额10.0%收取,最低10.0
         */

        public String date;
        public String fee;
    }
}
