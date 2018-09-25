package com.jiefutong.emall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author l
 * @Date 2018/7/23
 */
public class GoodsBuyNowBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean implements Serializable {
        /**
         * postage : 10
         * address : {"id":1,"userId":"6a726a4d6a2e4ae5b1a3eae01306988b","receiveName":"马俊芳","receiveTel":"15036011930","receiveProvince":"河南生","receiveCity":"郑州市","receiveCountry":"金水区","receiveDetail":"豫茶大厦102室","defaultAddr":true}
         * buyNum : 3
         * totalAmt : 2064
         * factAmt : 2074
         * goods : {"id":1,"goodsName":"小分子肽","goodsUrl":"http://www.beijiani.com/a/product/p1/24.html","goodsPrice":688,"goodIcon":"http://www.beijiani.com/a/product/p1/24.html","postage":10}
         */

        public double postage;
        public AddressBean address;
        public ShopBean shop;
        public int buyNum;
        public double totalAmt;
        public double factAmt;
        public double wallet;
        public boolean transPasswordFlag;
        public List<GoodsBean> goods;

        public static class AddressBean implements Serializable {
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

        public static class GoodsBean implements Serializable {

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
             * buyNum : 1
             * categoryType :
             * categoryText :
             * goodsPrice : 686
             */


            public String id;
            public String name;
            public String disc;
            public String classify;
            public String weight;
            public String cover;
            public String detail;
            public int moduleId;
            public int rank;
            public String createDate;
            public int isShow;
            public int isPinkage;
            public int isUpdate;
            public int buyNum;
            public int goodsNum;
            public String categoryType;
            public String categoryText;
            public double goodsPrice;
            public int activityFlag;
            public String orderNo;
            public String goodsName;
            public String splitNo;
        }

        public static class ShopBean implements Serializable {
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
