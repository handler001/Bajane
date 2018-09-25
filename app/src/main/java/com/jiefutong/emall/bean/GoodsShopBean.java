package com.jiefutong.emall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author l
 * @Date 2018/7/11
 */
public class GoodsShopBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * content : [{"id":1,"userId":"6a726a4d6a2e4ae5b1a3eae01306988b","shopName":"马俊芳小店","shopAdress":"金水区68号","shopTel":"15036011930","imgUrl":"http://img.51cgcy.com/beijiani/shop_title_1.png","lat":"111","lng":"222","createDate":"2018-07-06 00:00:00","updateDate":"2018-07-06 00:00:00","cityId":2}]
         * pageable : {"sort":{"sorted":true,"unsorted":false},"offset":0,"pageSize":10,"pageNumber":0,"unpaged":false,"paged":true}
         * totalElements : 1
         * totalPages : 1
         * last : true
         * number : 0
         * size : 10
         * sort : {"sorted":true,"unsorted":false}
         * numberOfElements : 1
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

        public static class ContentBean  implements Serializable{
            /**
             * id : 1
             * userId : 6a726a4d6a2e4ae5b1a3eae01306988b
             * shopName : 马俊芳小店
             * shopAdress : 金水区68号
             * shopTel : 15036011930
             * imgUrl : http://img.51cgcy.com/beijiani/shop_title_1.png
             * lat : 111
             * lng : 222
             * createDate : 2018-07-06 00:00:00
             * updateDate : 2018-07-06 00:00:00
             * cityId : 2
             */

            public String id;
            public String userId;
            public String shopName;
            public String shopAdress;
            public String shopTel;
            public String imgUrl;
            public double lat;
            public double lng;
            public String createDate;
            public String updateDate;
            public String cityId;
        }
    }
}
