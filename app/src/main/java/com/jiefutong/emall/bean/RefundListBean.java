package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/10
 */
public class RefundListBean {



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

            public String id;
            public String orderNo;
            public int refundType;
            public String refundTel;
            public String refundReason;
            public int refundStatus;
            public String refundComment;
            public String startDate;
            public String autoDate;
            public String userId;
            public String spliteNo;
            public String postageAmt;
            public String totalNum;
            public String totalAmt;
            public List<GoodsListBean> goodsList;

            public static class GoodsListBean {
                /**
                 * id : 54
                 * orderNo : B180806173547788171
                 * cover : http://img.51cgcy.com/beijiani/uploads/image/20180807/d9211313164b8540ad165fc7069a90ee.jpg
                 * goodsName : 1盒装
                 * goodsPrice : 686
                 * goodsNum : 2
                 * categoryText :
                 * splitNo : F180808094618197592
                 * activityFlag : 0
                 * refundNo : B180806173547788171
                 * orderStatus : 2
                 */

                public String id;
                public String orderNo;
                public String cover;
                public String goodsName;
                public String goodsPrice;
                public String goodsNum;
                public String categoryText;
                public String splitNo;
                public String activityFlag;
                public String refundNo;
                public String orderStatus;
            }
        }
    }
}
