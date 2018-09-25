package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/20
 */
public class FriendInfoBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {

        /**
         * userName : 176****8330
         * userType : 会员
         * realName : 李*
         * headImgUrl : uploads/image/20180804/ml170317_6865.png
         * createDate : 2018-08-04
         * offerMoney : 68.60
         * spread_level : 直推
         */
        public String userName;
        public String userType;
        public String realName;
        public String headImgUrl;
        public String createDate;
        public String offerMoney;
        public String spread_level;
    }
}
