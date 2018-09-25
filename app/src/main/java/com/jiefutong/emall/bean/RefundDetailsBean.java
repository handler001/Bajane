package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/9
 */
public class RefundDetailsBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        public String refundDesc;
        public String totalAmt;
        public String totalNum;
        public String applyReason;
        public String balanceTime;
        public String applyDate;
        public String applyProject;
        public String refundNo;
        public int applyAmt;
        public List<HistoryListBean> historyList;
        public List<GoodsListBean> goodsList;

        public static class HistoryListBean {
            /**
             * id : 17
             * orderNo : B180806173547788171
             * refundType : 1
             * refundText : 买家发起退款申请
             * dealDate : 2018-08-08 15:04:12
             */

            public int id;
            public String orderNo;
            public int refundType;
            public String refundText;
            public String dealDate;
        }

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

            public int id;
            public String orderNo;
            public String cover;
            public String goodsName;
            public int goodsPrice;
            public int goodsNum;
            public String categoryText;
            public String splitNo;
            public int activityFlag;
            public String refundNo;
            public int orderStatus;
        }
    }
}
