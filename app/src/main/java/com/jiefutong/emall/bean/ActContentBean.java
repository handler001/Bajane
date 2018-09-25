package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/11
 */
public class ActContentBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        public List<DataBean> data;
        public List<BannerBean> banner;
        public int coinFlag;

        public static class DataBean {
            /**
             * id : 1
             * glodCoin : 686
             * silverCoin : 1372
             * integral : 137
             * directPush : 10%
             * indirectPush : 3%
             * price : 686
             * num : 1
             * glodCoinMore :
             * silverCoinMore : null
             * integralMore : null
             * ticket : 1
             * activityName : 贝佳尼
             * activityTitle : 小分子活性肽
             * activityDesc : null
             * activityNum : 2
             * imageEntityList : null
             */

            public String id;
            public String glodCoin;
            public String silverCoin;
            public String integral;
            public String directPush;
            public String indirectPush;
            public String price;
            public String num;
            public String glodCoinMore;
            public String silverCoinMore;
            public String integralMore;
            public String ticket;
            public String activityName;
            public String activityTitle;
            public String activityDesc;
            public int activityNum;
            public String imageEntityList;
            public String goodsId;
        }

        public static class BannerBean {

            /**
             * src : http://img.51cgcy.com/beijiani/http://img.51cgcy.com/beijiani/uploads/image/20180730/ml170058_4479.png
             * link : <p><img src="http://bjn.xvtts.com//ueditor/php/upload/image/20180730/1532941256777661.png" title="1532941256777661.png" alt="活动详情 (2).png"/></p>
             * id : 3
             * type : 2
             */

            public String src;
            public String link;
            public int id;
            public int type;
        }
    }
}
