package com.jiefutong.emall.bean;

/**
 * @Author l
 * @Date 2018/7/28
 */
public class AliInfoBean {

    public int code;
    public String message;
    public DataMapBean dataMap;

    public static class DataMapBean {
        /**
         * transPasswordFlag : true
         * alipayName : 马俊芳
         * alipayAccount : 406513879@qq.com
         */

        public boolean transPasswordFlag;
        public String alipayName;
        public String alipayAccount;
    }
}
