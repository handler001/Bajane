package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/7/27
 */
public class DistCenterBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * allMoney : 0.00
         * realName :
         * wallet : 0.00
         * headImgUrl : http://img.51cgcy.com/futai/20180709064741257.jpg
         * friendsNum : 0
         * viewUserType : 店主
         */

        public String allMoney;
        public String realName;
        public String wallet;
        public String headImgUrl;
        public String friendsNum;
        public String viewUserType;
        public String linkUrl;
        public int imgFlag;
    }
}
