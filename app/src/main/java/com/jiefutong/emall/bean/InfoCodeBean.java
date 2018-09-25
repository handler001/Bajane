package com.jiefutong.emall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author l
 * @Date 2018/7/25
 */
public class InfoCodeBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean implements Serializable{

        public String orderNo;
        public double totalAmt;
        public int totalNum;
        public int orderStatus;
        public String veriftyCode;
        public List<GoodsBean> goods;

        public static class GoodsBean implements Serializable{
            /**
             * id : 671
             * orderNo : B180821102333716409
             * cover : http://img.51cgcy.com/beijiani/uploads/image/20180811/cf9585ba01790ebb611c1961949e1d59.jpg
             * goodsName : 【1盒装】贝佳尼小分子肽--引领21世纪国民营养革命
             * goodsPrice : 686
             * goodsNum : 1
             * categoryText :
             * splitNo : F180821102333294247
             * activityFlag : 0
             * refundNo : null
             * orderStatus : 4
             */

            public int id;
            public String orderNo;
            public String cover;
            public String goodsName;
            public double goodsPrice;
            public int goodsNum;
            public String categoryText;
            public String splitNo;
            public int activityFlag;
            public String refundNo;
            public int orderStatus;
        }
    }
}
