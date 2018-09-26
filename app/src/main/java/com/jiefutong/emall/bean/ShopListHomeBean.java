package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/26
 */
public class ShopListHomeBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        public List<ShopListDetailsBean> nearList;
    }
}
