package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/20
 */
public class BalanceBean {

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
             * id : 177
             * userId : 4afccd355e664cbf8b22000acba142ee
             * flowId : 2018073110057495
             * money : 137200
             * flowType : 1
             * content : 订单号19兑换成功,赠送银币
             * userMobile : 15036011930
             * createDate : 2018-07-31 20:42:05
             * orderId : 19
             * coinType : 3
             * adminType : 2
             * viewMoney : 1372.00
             */

            public int id;
            public String userId;
            public String flowId;
            public String money;
            public String flowType;
            public String content;
            public String userMobile;
            public String createDate;
            public String orderId;
            public String coinType;
            public String adminType;
            public String viewMoney;
        }
    }
}
