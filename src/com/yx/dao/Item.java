package com.yx.dao;

/**
 * 录入明细表
 */
import java.util.Date;

public class Item {

    // 录入明细序列号
    private int id;

    // 关联酒店ID
    private int hotelId;

    // 房间出租率
    private String occItem;

    // 酒店平均房价
    private String adrItem;

    // 平均收入
    private String revPARItem;

    // 录入时间
    private Date loadTime;

    // 最新修改时间
    private Date updateTime;

    // 业务时间
    private Date busTime;

    // 录入人
    private String loader;

    // 取得录入明细序列号
    public int getId() {
        return id;
    }

    // 设置录入明细序列号
    public void setId(int id) {
        this.id = id;
    }
    
    // 取得关联酒店ID
    public int getHotelId() {
        return hotelId;
    }

    // 设置关联酒店ID
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    // 取得房间出租率
    public String getOccItem() {
        return occItem;
    }

    // 设置房间出租率
    public void setOccItem(String occItem) {
        this.occItem = occItem;
    }

    // 取得酒店平均房价
    public String getAdrItem() {
        return adrItem;
    }

    // 设置酒店平均房价
    public void setAdrItem(String adrItem) {
        this.adrItem = adrItem;
    }

    // 取得平均收入
    public String getRevPARItem() {
        return revPARItem;
    }

    // 设置平均收入
    public void setRevPARItem(String revPARItem) {
        this.revPARItem = revPARItem;
    }

    // 取得录入时间
    public Date getLoadTime() {
        return loadTime;
    }

    // 设置录入时间
    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    // 取得最新修改时间
    public Date getUpdateTime() {
        return updateTime;
    }

    // 设置最新修改时间
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    // 取得业务时间
    public Date getBusTime() {
        return busTime;
    }

    // 设置业务时间
    public void setBusTime(Date busTime) {
        this.busTime = busTime;
    }

    // 取得录入人
    public String getLoader() {
        return loader;
    }

    // 设置录入人
    public void setLoader(String loader) {
        this.loader = loader;
    }
}
