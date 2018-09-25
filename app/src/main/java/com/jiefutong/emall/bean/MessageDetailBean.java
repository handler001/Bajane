package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/29
 */
public class MessageDetailBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {

        public PageableBean pageable;
        public int totalPages;
        public int totalElements;
        public boolean last;
        public int number;
        public int size;
        public SortBeanX sort;
        public int numberOfElements;
        public boolean first;
        public List<ContentBean> content;

        public static class PageableBean {
            /**
             * sort : {"sorted":true,"unsorted":false}
             * offset : 0
             * pageNumber : 0
             * pageSize : 10
             * unpaged : false
             * paged : true
             */

            public SortBean sort;
            public int offset;
            public int pageNumber;
            public int pageSize;
            public boolean unpaged;
            public boolean paged;

            public static class SortBean {
                /**
                 * sorted : true
                 * unsorted : false
                 */

                public boolean sorted;
                public boolean unsorted;
            }
        }

        public static class SortBeanX {
            /**
             * sorted : true
             * unsorted : false
             */

            public boolean sorted;
            public boolean unsorted;
        }

        public static class ContentBean {
            /**
             * id : 2
             * userId : 81e3dc99bae04ebe8bb8f7eb2f9b5db4
             * msg : <p>122</p>
             * title : 111
             * appId : 0
             * userMobile : 13243368888
             * createDate : 2018-08-30 10:37:33
             * readFlag : 0
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
            public String iconUrl;
        }
    }
}
