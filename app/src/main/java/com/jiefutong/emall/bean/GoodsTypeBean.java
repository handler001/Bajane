package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/20
 */
public class GoodsTypeBean {
    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * name : 女装
         * id : 41
         */

        public String name;
        public String id;
    }
}
