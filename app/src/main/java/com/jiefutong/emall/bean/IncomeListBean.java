package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/13
 */
public class IncomeListBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * realName : 马俊芳
         * money : 40.26
         * viewUserType : 店主
         * headerImg : http://img.51cgcy.com/futai/20180709064741257.jpg
         */

        public String realName;
        public String money;
        public String viewUserType;
        public String headerImg;
    }
}
