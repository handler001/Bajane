package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/14
 */
public class ProductDescBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * id : 1
         * assortmentImg : http://img.51cgcy.com/beijiani/img_bajane_one.png
         * assortmentTitle : 贝佳尼肽
         * assortmentDesc : 小分子肽的简介
         */

        public String id;
        public String assortmentImg;
        public String assortmentTitle;
        public String assortmentDesc;
    }
}
