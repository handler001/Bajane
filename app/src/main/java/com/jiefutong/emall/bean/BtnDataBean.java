package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/25
 */
public class BtnDataBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {
        /**
         * id : 1
         * categoryType : 9.9包邮
         * categoryImg : http://img.51cgcy.com/egoushangcheng/uploads/image/20180925/ml110013_7366.png
         */

        public String id;
        public String categoryType;
        public String categoryImg;
    }
}
