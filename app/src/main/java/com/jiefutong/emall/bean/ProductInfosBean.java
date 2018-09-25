package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/14
 */
public class ProductInfosBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * content : [{"id":7,"title":"Adasddd","content":"<p>撒娇的设计费几十块凤凰军事地方化<br/><\/p>","imgUrl":null,"articleType":null}]
         * pageable : {"sort":{"sorted":true,"unsorted":false},"offset":0,"pageNumber":0,"pageSize":10,"unpaged":false,"paged":true}
         * totalElements : 2
         * totalPages : 1
         * last : true
         * number : 0
         * size : 10
         * sort : {"sorted":true,"unsorted":false}
         * numberOfElements : 2
         * first : true
         */

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
             * id : 7
             * title : Adasddd
             * content : <p>撒娇的设计费几十块凤凰军事地方化<br/></p>
             * imgUrl : null
             * articleType : null
             */

            public String id;
            public String title;
            public String content;
            public String imgUrl;
            public String articleType;
            public String createDate;
        }
    }
}
