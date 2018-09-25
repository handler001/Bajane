package com.jiefutong.emall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author l
 * @Date 2018/7/24
 */
public class AddressInfosBean {
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
             * userId : 6a726a4d6a2e4ae5b1a3eae01306988b
             * receiveName : 马俊芳
             * receiveTel : 15036011930
             * receiveProvince : 河南生
             * receiveCity : 郑州市
             * receiveCountry : 金水区
             * receiveDetail : 豫茶大厦102室
             * defaultAddr : false
             */

            public String id;
            public String userId;
            public String receiveName;
            public String receiveTel;
            public String receiveProvince;
            public String receiveCity;
            public String receiveCountry;
            public String receiveDetail;
            public boolean defaultAddr;
        }
    }
}
