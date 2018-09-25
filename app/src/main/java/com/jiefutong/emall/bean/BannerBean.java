package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/27
 */
public class BannerBean {

    public int code;
    public String message;
    public List<DataMapBean> dataMap;

    public static class DataMapBean {

        /**
         * src : http://img.51cgcy.com/beijiani/uploads/image/20180730/ml170058_4479.png
         * link : <p><img src="http://bjn.xvtts.com//ueditor/php/upload/image/20180730/1532941256777661.png" title="1532941256777661.png" alt="活动详情 (2).png"/></p>
         * id : 3
         * type : 2
         */

        public String src;
        public String link;
        public String id;
        public int type;
    }
}
