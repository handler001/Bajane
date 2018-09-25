package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/8/1
 */
public class AliPayBean {

    public AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
    public String sign;
    public String sign_type;

    public static class AlipayTradeAppPayResponseBean {
        /**
         * code : 10000
         * msg : Success
         * app_id : 2018072460756222
         * auth_app_id : 2018072460756222
         * charset : UTF-8
         * timestamp : 2018-08-01 18:20:42
         * total_amount : 0.01
         * trade_no : 2018080121001004880507280059
         * seller_id : 2088821494681825
         * out_trade_no : B180801181936102419
         */

        public String code;
        public String msg;
        public String app_id;
        public String auth_app_id;
        public String charset;
        public String timestamp;
        public String total_amount;
        public String trade_no;
        public String seller_id;
        public String out_trade_no;
    }
}
