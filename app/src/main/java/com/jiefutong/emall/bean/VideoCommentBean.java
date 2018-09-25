package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/30
 */
public class VideoCommentBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {

        public PageableBean pageable;
        public int totalElements;
        public boolean last;
        public int totalPages;
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
             * paged : true
             * unpaged : false
             */

            public SortBean sort;
            public int offset;
            public int pageNumber;
            public int pageSize;
            public boolean paged;
            public boolean unpaged;

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
             * id : 1
             * userId : 81e3dc99bae04ebe8bb8f7eb2f9b5db4
             * videoId : 5
             * comment : 测试评论
             * createDate : 2018-08-30 15:20:20
             * dataFlag : 1
             * headImgUrl : http://img.51cgcy.com/beijiani/20180823063321981.jpg
             * realName : 黄书培
             */

            public int id;
            public String userId;
            public int videoId;
            public String comment;
            public String createDate;
            public int dataFlag;
            public String headImgUrl;
            public String realName;
        }
    }
}
