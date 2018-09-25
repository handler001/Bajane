package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/23
 */
public class GoodsCarBean {

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
             * userId : 4afccd355e664cbf8b22000acba142ee
             * goodsId : 1
             * goodsName : 小分子肽
             * goodsPrice : 688
             * goodsIcon : null
             * postage : 10
             * goodsNum : 1
             * stockNum : 1
             * category : M 红色
             */

            public String id;
            public String userId;
            public String goodsId;
            public String goodsName;
            public double goodsPrice;
            public String goodsIcon;
            public int postage;
            public int goodsNum;
            public int stockNum;
            public String category;
            public String categoryType;
        }
    }
}
