package com.jiefutong.emall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author l
 * @Date 2018/8/8
 */
public class GoodsBean implements Parcelable {
    public GoodsBean() {
    }

    /**
     * id : 1
     * name : 贝佳尼分子肽
     * disc : 消除疲劳，增强体能。5分钟进血液，10分钟转换成体能，抑制肌肉力量下降，长期维持充沛体能。
     * classify : null
     * weight : null
     * cover : /uploads/image/20180730/b01fdcecc268b88893d59c8a658b7348.jpg
     * detail : <p><img src="http://bjn.xvtts.com//ueditor/php/upload/image/20180730/1532942064877378.jpg" title="1532942064877378.jpg" alt="贝佳尼详情.jpg"/></p>
     * moduleId : 0
     * rank : 0
     * createDate : 1532942084000
     * isShow : 1
     * isPinkage : 1
     * isUpdate : 1
     * buyNum : 2
     * categoryType :
     * categoryText :
     * goodsPrice : 686
     * activityFlag : 0
     */

    public String id;
    public String name;
    public String disc;
    public String classify;
    public String weight;
    public String cover;
    public String detail;
    public int moduleId;
    public int rank;
    public String createDate;
    public int isShow;
    public int isPinkage;
    public int isUpdate;
    public int buyNum;
    public int goodsNum;
    public String categoryType;
    public String categoryText;
    public double goodsPrice;
    public int activityFlag;
    public String orderNo;
    public String goodsName;
    public String splitNo;

    protected GoodsBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        disc = in.readString();
        classify = in.readString();
        weight = in.readString();
        cover = in.readString();
        detail = in.readString();
        moduleId = in.readInt();
        rank = in.readInt();
        createDate = in.readString();
        isShow = in.readInt();
        isPinkage = in.readInt();
        isUpdate = in.readInt();
        buyNum = in.readInt();
        goodsNum = in.readInt();
        categoryType = in.readString();
        categoryText = in.readString();
        goodsPrice = in.readDouble();
        activityFlag = in.readInt();
        orderNo = in.readString();
        goodsName = in.readString();
        splitNo = in.readString();
    }

    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel in) {
            return new GoodsBean(in);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(disc);
        dest.writeString(classify);
        dest.writeString(weight);
        dest.writeString(cover);
        dest.writeString(detail);
        dest.writeInt(moduleId);
        dest.writeInt(rank);
        dest.writeString(createDate);
        dest.writeInt(isShow);
        dest.writeInt(isPinkage);
        dest.writeInt(isUpdate);
        dest.writeInt(buyNum);
        dest.writeInt(goodsNum);
        dest.writeString(categoryType);
        dest.writeString(categoryText);
        dest.writeDouble(goodsPrice);
        dest.writeInt(activityFlag);
        dest.writeString(orderNo);
        dest.writeString(goodsName);
        dest.writeString(splitNo);
    }
}
