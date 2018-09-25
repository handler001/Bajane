package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/24
 */
public class MsgSysBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        public PageableBean pageable;
        public int totalElements;
        public int totalPages;
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
             * pageSize : 10
             * pageNumber : 0
             * unpaged : false
             * paged : true
             */

            public SortBean sort;
            public int offset;
            public int pageSize;
            public int pageNumber;
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
             * id : 52
             * msgType : all
             * userId : null
             * msg : 哈哈哈，群消息接受

             * title : 哈哈哈，群消息
             * date : 2018-06-29 19:33:46
             * appId : a39db6e839c3478b8bcafa27e52ca609
             * userMobile : null
             */

            public String id;
            public String msgType;
            public String userId;
            public String msg;
            public String title;
            public String date;
            public String appId;
            public String userMobile;
        }
    }
}
