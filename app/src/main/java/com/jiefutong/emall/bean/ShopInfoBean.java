package com.jiefutong.emall.bean;

import java.io.Serializable;

/**
 * @Author l
 * @Date 2018/7/24
 */
public class ShopInfoBean {
    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean  implements Serializable{
        /**
         * id : 2
         * userId : 4afccd355e664cbf8b22000acba142ee
         * shopName : 健康店-豫茶大厦分店
         * shopAdress : 豫茶大厦112号
         * shopTel : 13253459610
         * lat : 111111
         * lng : 222222
         * createDate : null
         * updateDate : 2018-07-12 17:07:57
         * cityId : 203
         * imgUrl : http://bjn.hongzhuandaxue.com/uploads/image/20180718/ml095715_5715.png
         */

        public int id;
        public String userId;
        public String shopName;
        public String shopAdress;
        public String shopTel;
        public double lat;
        public double lng;
        public String createDate;
        public String updateDate;
        public String cityId;
        public String imgUrl;
        public String cityName;
    }
}
