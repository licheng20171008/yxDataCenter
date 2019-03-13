package com.yx.dao;

/**
 * 所有酒店的信息
 */
public class Hotelinfo {

    // 酒店序列号
    private int hotelId;

    // 酒店名称
    private String name;

    // 酒店房间数
    private int roomNumber;

    // 酒店联系人
    private String callMan;

    // 酒店联系方式
    private String tel;

    // 备注
    private String remark;

    // 取得酒店序列号
    public int getHotelId() {
        return hotelId;
    }

    // 设置酒店序列号
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    // 取得酒店名称
    public String getName() {
        return name;
    }

    // 设置酒店名称
    public void setName(String name) {
        this.name = name;
    }

    // 取得酒店房间数
    public int getRoomNumber() {
        return roomNumber;
    }

    // 设置酒店房间数
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    // 取得酒店联系人
    public String getCallMan() {
        return callMan;
    }

    // 设置酒店联系人
    public void setCallMan(String callMan) {
        this.callMan = callMan;
    }

    // 取得酒店联系方式
    public String getTel() {
        return tel;
    }

    // 设置酒店联系方式
    public void setTel(String tel) {
        this.tel = tel;
    }

    // 取得备注
    public String getRemark() {
        return remark;
    }

    // 设置备注
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
