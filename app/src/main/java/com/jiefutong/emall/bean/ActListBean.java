package com.jiefutong.emall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author l
 * @Date 2018/9/6
 */
public class ActListBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean implements Serializable {
        /**
         * id : 1
         * title : 贝加尼三折优惠
         * content : 回馈新老客户
         * imgUrl : http://img.51cgcy.com/beijiani/uploads/image/20180823/ml094750_8453.png
         * url : http://bjn.leetx.net/bjnhtml/hebei/activities.html
         * agentId : 17
         */

        public String id;
        public String title;
        public String content;
        public String imgUrl;
        public String url;
        public String agentId;
    }
}
