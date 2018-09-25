package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/9/20
 */
public class GoodsCategoryBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        public BannerBean banner;
        public List<DataBean> data;

        public static class BannerBean {
            /**
             * link :
             * title : 内衣
             * url : https://www.cgwlkj.cn/Uploads/2018-04-13/1523586289183285001419366456.jpg
             */

            public String link;
            public String title;
            public String url;
        }

        public static class DataBean {
            public String name;
            public int id;
            public List<ChildrenBean> children;

            public static class ChildrenBean {
                /**
                 * name : 女士睡衣
                 * id : 408
                 * pic : https://www.cgwlkj.cn/Uploads/2018-04-12/152352102196637500827091613.png
                 */

                public String name;
                public int id;
                public String pic;
            }
        }
    }
}
