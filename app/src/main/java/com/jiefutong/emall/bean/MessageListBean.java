package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/29
 */
public class MessageListBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * msgType : notice
         * unReadNum : 0
         * icon : http://img.51cgcy.com/beijiani/uploads/image/20180829/ml181054_9973.png
         * id : 1
         * title : 公司公告
         * createDate : 2018-08-30 09:43:44
         * desc : 11
         */

        public String msgType;
        public int unReadNum;
        public String icon;
        public String id;
        public String title;
        public String createDate;
        public String desc;
    }
}
