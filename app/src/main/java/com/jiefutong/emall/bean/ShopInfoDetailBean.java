package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/27
 */
public class ShopInfoDetailBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * nearList : [{"shopSignDesc":["可刷卡"],"distance":"0米","addressDesc":"河南省郑州市金水区豫茶大厦1520","shopCategoryDesc":"美食","shopIcon":"http://img.51cgcy.com/egoushangcheng/uploads/image/20180919/ml115950_6051.png","shopName":"测试店铺","shopId":1}]
         * shopSignDesc : ["可刷卡"]
         * shopIntroduce : 描述
         * addressDesc : 河南省郑州市金水区豫茶大厦1520
         * shopCategoryDesc : 美食
         * shopIcon : http://img.51cgcy.com/egoushangcheng/uploads/image/20180919/ml115950_6051.png
         * shopName : 测试店铺
         * shopId : 1
         * shopPoint : 特色
         */

        public String shopIntroduce;
        public String addressDesc;
        public String shopCategoryDesc;
        public String shopIcon;
        public String shopName;
        public String shopId;
        public String shopTel;
        public String shopPoint;
        public List<ShopListDetailsBean> nearList;
        public List<String> shopSignDesc;

    }
}
