package com.jiefutong.emall.bean;

import io.realm.RealmObject;

/**
 * @Author l
 * @Date 2018/7/9
 */
public class UserBean extends RealmObject {

    public int code;
    public String message;
    public UserInfoBean dataMap;
}
