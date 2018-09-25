package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/9/3
 */
public class MsgDetailBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * id : 3
         * userId : 81e3dc99bae04ebe8bb8f7eb2f9b5db4
         * msg : <p>1122</p>
         * title : 公司公告
         * appId : 0
         * userMobile : 13243368888
         * createDate : 2018-08-30 11:29:51
         * readFlag : 1
         * msgType : notice
         */

        public int id;
        public String userId;
        public String msg;
        public String title;
        public String appId;
        public String userMobile;
        public String createDate;
        public int readFlag;
        public String msgType;
    }
}
