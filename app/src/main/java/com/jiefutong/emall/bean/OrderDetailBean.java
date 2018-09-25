package com.jiefutong.emall.bean;

import java.util.ArrayList;

/**
 * @Author l
 * @Date 2018/7/27
 */
public class OrderDetailBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        public int postage;
        public String realAmt;
        public AddressBean address;
        public ShopBean shop;
        public String orderNo;
        public String amt;
        public int orderStatus;
        public ArrayList<GoodsBean> goods;
        public String finishDate;
        public String createDate;
        public String sendDate;
        public String payDate;
        public String postageName;
        public String postageNo;
        public String totalNum;
        public String veriftyCode;
        public String alipayType;
        public int refundFlag;
        public String banlanceTime;

        public static class AddressBean {
            /**
             * id : 1
             * userId : 6a726a4d6a2e4ae5b1a3eae01306988b
             * receiveName : 马俊芳
             * receiveTel : 15036011930
             * receiveProvince : 河南生
             * receiveCity : 郑州市
             * receiveCountry : 金水区
             * receiveDetail : 豫茶大厦102室
             * defaultAddr : true
             */

            public int id;
            public String userId;
            public String receiveName;
            public String receiveTel;
            public String receiveProvince;
            public String receiveCity;
            public String receiveCountry;
            public String receiveDetail;
            public boolean defaultAddr;
        }

        public static class ShopBean {
            /**
             * id : 24
             * userId : 1ba164dd52764a00bdce8130b4f8e200
             * shopName : CC小店
             * shopAdress : 郑州市中原区万科星光广场
             * shopTel : 13673991339
             * lat : 34.808665
             * lng : 113.517419
             * createDate : 2018-08-06 23:18:47
             * updateDate : 2018-08-06 23:18:47
             * cityId : 202
             * imgUrl : http://img.51cgcy.com/beijiani/20180806111807084.jpg
             * cityName :
             */

            public int id;
            public String userId;
            public String shopName;
            public String shopAdress;
            public String shopTel;
            public String lat;
            public String lng;
            public String createDate;
            public String updateDate;
            public int cityId;
            public String imgUrl;
            public String cityName;
        }
    }
}
