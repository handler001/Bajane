package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/12
 */
public class AreaCityBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * id : 1
         * areaId : 201
         * areaName : 全部
         */
        public int id;
        public String areaId;
        public String areaName;
    }
}
