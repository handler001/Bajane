package com.jiefutong.emall.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

/**
 * Java对象和JSON字符串相互转化工具类
 *
 * @author pangjun
 * @date 2013-08-10
 */
public final class JsonUtil {

    private JsonUtil() {
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T parseObject(String str, Class<T> type) {
        try {
            return JSON.parseObject(str, type);
        } catch (Exception e) {
            return null;
        }
    }

}
