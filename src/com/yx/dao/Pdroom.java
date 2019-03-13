package com.yx.dao;

public class Pdroom {

	// 每日房型价格ID
	private int id;
	
	// 日期
	private String date;
	
	// 每日房型ID
	private int pddateroomid;
		
	// 每日房型
	private String pddateroomtype;
	
	// 每日房型价格
	private String pdroomprice;

	// 取得每日房型价格ID
	public int getId() {
		return id;
	}

	// 设置每日房型价格ID
	public void setId(int id) {
		this.id = id;
	}

	// 取得日期
	public String getDate() {
		return date;
	}

	// 设置日期
	public void setDate(String date) {
		this.date = date;
	}
	
	// 取得每日房型价格ID
	public int getPddateroomid() {
		return pddateroomid;
	}

	// 设置每日房型价格ID
	public void setPddateroomid(int pddateroomid) {
		this.pddateroomid = pddateroomid;
	}
	
	// 取得每日房型
	public String getPddateroomtype() {
		return pddateroomtype;
	}

	// 设置每日房型
	public void setPddateroomtype(String pddateroomtype) {
		this.pddateroomtype = pddateroomtype;
	}

	// 取得每日房型价格
	public String getPdroomprice() {
		return pdroomprice;
	}

	// 设置每日房型价格
	public void setPdroomprice(String pdroomprice) {
		this.pdroomprice = pdroomprice;
	}
}
