package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/7/24
 */
public class UserDetailsBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * realName : 哈哈
         * userStatus : 1
         * headImgUrl : http://img.51cgcy.com/futai/20180709064741257.jpg
         * transPasswordFlag : true
         * userMobile : 15036011930
         * registrationId : 170976fa8ad6f70d92f
         * userType : 2
         * viewUserType : 店主
         * userName : 15036011930
         * AlipayFlag : true
         */

        public String realName;
        public String userStatus;
        public String headImgUrl;
        public boolean transPasswordFlag;
        public String userMobile;
        public String registrationId;
        public String userType;
        public String viewUserType;
        public String userName;
        public boolean AlipayFlag;
        public int coinFlag;
        public int refundFlag;
    }
}
