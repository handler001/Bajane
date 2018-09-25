package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/17
 */
public class OrderInfoBean {


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
        public boolean first;
        public int numberOfElements;
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
            public String id;
            public String userId;
            public String orderNo;
            public int orderStatus;
            public double totalAmt;
            public int postage;
            public String createDate;
            public String updateDate;
            public int addressId;
            public String userMobile;
            public Object receiveAddress;
            public int orderType;
            public int shopId;
            public String veriftyCode;
            public String comment;
            public String payDate;
            public Object finishDate;
            public int profit;
            public String goodsId;
            public String goodsNum;
            public String categoryType;
            public Object retained;
            public List<GoodsBean> goods;
            public String totalNum;

            public static class GoodsBean {
                /**
                 * id : 1
                 * name : 贝佳尼分子肽
                 * disc : 消除疲劳，增强体能。5分钟进血液，10分钟转换成体能，抑制肌肉力量下降，长期维持充沛体能。
                 * classify : null
                 * weight : null
                 * cover : /uploads/image/20180730/b01fdcecc268b88893d59c8a658b7348.jpg
                 * detail : <p><img src="http://bjn.xvtts.com//ueditor/php/upload/image/20180730/1532942064877378.jpg" title="1532942064877378.jpg" alt="贝佳尼详情.jpg"/></p>
                 * moduleId : 0
                 * rank : 0
                 * createDate : 1532942084000
                 * isShow : 1
                 * isPinkage : 1
                 * isUpdate : 1
                 * buyNum : 0
                 * categoryType :
                 * categoryText :
                 * goodsPrice : 0
                 * orderNo: "B180806145217213869",
                 * goodsName: "贝佳尼小分子肽10盒报单",
                 * goodsNum: 1,
                 * splitNo: "F180806145218312507"
                 */

                public int id;
                public String name;
                public String disc;
                public String classify;
                public String weight;
                public String cover;
                public String detail;
                public int moduleId;
                public int rank;
                public long createDate;
                public int isShow;
                public int isPinkage;
                public int isUpdate;
                public int buyNum;
                public int goodsNum;
                public String categoryType;
                public String categoryText;
                public double goodsPrice;
                public String orderNo;
                public String goodsName;
                public String splitNo;
                public String orderStatus;
                public String refundNo;
            }
        }
    }
}
