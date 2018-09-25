package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/8/11
 */
public class HeaderInfoBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * realName : 贾素珍
         * money : 48030
         * viewUserType : 会员
         * headerImg : http://img.51cgcy.com/beijiani/uploads/image/20180810/ml091519_7471.png
         */

        public String realName;
        public String money;
        public String viewUserType;
        public String headerImg;
    }
}
