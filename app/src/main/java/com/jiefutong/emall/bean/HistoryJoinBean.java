package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/21
 */
public class HistoryJoinBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * orderNo : B180816152802693877
         * activityStatus : 排队中
         * activityTime : 2018-08-16 15:42:18
         * content : 您前面还有1人在排队
         */

        public String orderNo;
        public String activityStatus;
        public String activityTime;
        public String content;
    }
}
