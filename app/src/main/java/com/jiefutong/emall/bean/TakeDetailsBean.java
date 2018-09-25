package com.jiefutong.emall.bean;

import java.util.List;

/**
 * @Author l
 * @Date 2018/7/20
 */
public class TakeDetailsBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        public List<DataListBean> dataList;

        public static class DataListBean {
            /**
             * status : 1
             * money : 10.00
             * createDate : 2018-07-28 10:37:30
             * flowId : 2018072801342712763
             * payType : 2
             * payStatus : 1
             */
            public String id;
            public String status;
            public String money;
            public String createDate;
            public String flowId;
            public int payType;
            public int payStatus;
        }
    }
}
