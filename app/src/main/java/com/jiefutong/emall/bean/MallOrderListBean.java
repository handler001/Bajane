package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/13
 */
public class MallOrderListBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * content : [{"id":1,"pid":"1002679_25518003","orderSn":"1111225225","goodsThumbUrl":"www.123.com","goodsId":"10071567","goodsName":"话费优惠28","money":10,"paidAt":"2018-09-12 15:20:20","verifiedAt":null,"status":0,"customParameters":"测试","createdAt":"2018-09-12 15:20:20","updatedAt":"2018-09-12 15:20:20","userId":"81e3dc99bae04ebe8bb8f7eb2f9b5db4","statuDesc":"待返佣","promotionAmount":2,"orderType":1,"orderTypeDesc":"拼多多"}]
         * pageable : {"sort":{"sorted":true,"unsorted":false},"offset":0,"pageNumber":0,"pageSize":10,"paged":true,"unpaged":false}
         * last : true
         * totalPages : 1
         * totalElements : 1
         * number : 0
         * size : 10
         * sort : {"sorted":true,"unsorted":false}
         * numberOfElements : 1
         * first : true
         */

        public PageableBean pageable;
        public boolean last;
        public int totalPages;
        public int totalElements;
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
             * pid : 1002679_25518003
             * orderSn : 1111225225
             * goodsThumbUrl : www.123.com
             * goodsId : 10071567
             * goodsName : 话费优惠28
             * money : 10
             * paidAt : 2018-09-12 15:20:20
             * verifiedAt : null
             * status : 0
             * customParameters : 测试
             * createdAt : 2018-09-12 15:20:20
             * updatedAt : 2018-09-12 15:20:20
             * userId : 81e3dc99bae04ebe8bb8f7eb2f9b5db4
             * statuDesc : 待返佣
             * promotionAmount : 2
             * orderType : 1
             * orderTypeDesc : 拼多多
             */

            public int id;
            public String pid;
            public String orderSn;
            public String goodsThumbUrl;
            public String goodsId;
            public String goodsName;
            public String money;
            public String paidAt;
            public String verifiedAt;
            public int status;
            public String customParameters;
            public String createdAt;
            public String updatedAt;
            public String userId;
            public String statuDesc;
            public int promotionAmount;
            public int orderType;
            public String orderTypeDesc;
        }
    }
}
