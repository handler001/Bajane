package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/23
 */
public class GoodsInfoDetailsBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        public String goodsImg;
        public double postage;
        public String goodsUrl;
        public String stockNum;
        public String id;
        public String goodPrice;
        public String goodsName;
        public List<ImgUrlBean> imgUrl;
        public List<CategoryBean> category;

        public static class ImgUrlBean {
            /**
             * id : 20
             * goodImg : http://img.51cgcy.com/futai//uploads/image/20180725\7404166cdec7e3b69a3f04c9ecda6b83.jpg
             * goodId : 11
             */

            public int id;
            public String goodImg;
            public int goodId;
        }

        public static class CategoryBean {

            public String categoryName;
            public List<ContentListBean> contentList;

            public static class ContentListBean {
                /**
                 * id : 10
                 * spName : x
                 * goodSpId : 6
                 * goodsIcon : http://img.51cgcy.com/futai//uploads/image/20180725\9bc89901eb2cb951d01eb07fb295c564.jpg
                 */

                public String id;
                public String spName;
                public String goodSpId;
                public String goodsIcon;
            }
        }
    }
}
