package com.jiefutong.emall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author l
 * @Date 2018/7/13
 */
public class VideoListBean {

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

        public static class ContentBean implements Serializable {
            /**
             * id : 1
             * author : 官方出品
             * authorHeader : 1018.png
             * title : 什么是小分子肽
             * vedioUrl : http://img.51cgcy.com/beijiani/what_tai.qlv
             * zanNum : 20
             * vedioImg : http://img.51cgcy.com/beijiani/shop_title_1.png
             * videoLength : 04:30
             * createDate : 2018-07-09 17:21:41
             */

            public String id;
            public String author;
            public String authorHeader;
            public String title;
            public String vedioUrl;
            public String zanNum;
            public String vedioImg;
            public String videoLength;
            public String createDate;
            public String zanCount;
            public String commentCount;
            public int zanFlag;
        }
    }
}
