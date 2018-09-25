package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/8/1
 */
public class VersionBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * appName : com.jiefutong.bajane
         * appVersion : 1.0.0
         * appCode : 1
         * appUrl : http://bjn.leetx.net/bjnhtml/activeDetails.html
         * id : 1
         * appDesc : 新增消息推送
         * appAlias : 贝佳尼
         */

        public String appName;
        public String appVersion;
        public int appCode;
        public String appUrl;
        public int id;
        public String appDesc;
        public String appAlias;
    }
}
