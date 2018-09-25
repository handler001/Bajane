package com.jiefutong.emall.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Author l
 * @Date 2018/6/6
 */
public class WeiXinBean {


    /**
     * nonce_str : 665f556c902e4ef6b6d88c825a1d99f3
     * package : Sign=WXPay
     * appid : wx97c8a0ddf826968c
     * sign : BFB4CB8D594BB0310C86E9DAE315C7C4
     * prepayid : wx261157493685174d9cd96bb01208792849
     * mch_id : 1500286922
     * timestamp : 1532577476132
     */

    public String noncestr;
    @SerializedName("package")
    public String packageX;
    public String appid;
    public String sign;
    public String prepayid;
    public String partnerid;
    public String timestamp;
    public String orderNo;
}
