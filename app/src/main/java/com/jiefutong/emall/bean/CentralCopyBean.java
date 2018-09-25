package com.jiefutong.emall.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author l
 * @Date 2018/8/27
 */
public class CentralCopyBean {

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
             * id : 7
             * title : 贝佳尼官方
             * synopsis : 11112222
             * img : ["uploads/image/20180828/699692cb339bb4ebe7f8d094b7d75532.jpg"]
             * shareNum : 100
             * rank : 0
             * show : 1
             * createDate : 2018-08-28 18:28:24
             * updateDate : null
             * imgList : ["http://img.51cgcy.com/beijiani/uploads/image/20180828/699692cb339bb4ebe7f8d094b7d75532.jpg"]
             */

            public String id;
            public String title;
            public String synopsis;
            public String img;
            public int shareNum;
            public int rank;
            public int show;
            public String createDate;
            public String updateDate;
            public String headImg;
            public ArrayList<String> imgList;
        }
    }
}
