package com.yx.DBConnect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yx.dao.Common;

public class DBData {

	DBOpration dop = new DBOpration();

	public String hotelinfoInsert(HttpServletRequest request, List<String> msgList) {
		int hotelID = dop.excuteID(tableforAll("hotelinfo"));
		msgList.add("酒店ID：" + hotelID);
		String name = request.getParameter("name").trim();
		msgList.add("酒店名称：" + name);
		String roomNumber = request.getParameter("roomNumber").trim();
		if ("".equals(roomNumber)) {
			roomNumber = "0";
		}
		msgList.add("酒店房间数：" + roomNumber);
		String callMan = request.getParameter("callMan").trim();
		msgList.add("酒店联系人：" + callMan);
		String tel = request.getParameter("tel").trim();
		if ("".equals(tel)) {
			tel = "0";
		}
		msgList.add("酒店联系方式：" + tel);
		String remark = request.getParameter("remark").trim();
		msgList.add("备注：" + remark);
		String sql = "insert into hotelinfo (hotelId, name, roomNumber, callMan, tel, remark) values (" + hotelID + ",'"
				+ name + "', " + roomNumber + ", '" + callMan + "', " + tel + ", '" + remark + "')";
//		System.out.println(sql);
		return sql;
	}

	public String itemInsert(HttpServletRequest request, List<String> msgList) {
		int id = dop.excuteID(tableforAll("item"));
		msgList.add("录入明细序列号：" + id);
		String hotelId = request.getParameter("hotelId").trim();
		if ("".equals(hotelId)) {
			hotelId = "0";
		}
		msgList.add("关联酒店ID：" + hotelId);
		String occItem = request.getParameter("occItem").trim();
		msgList.add("房间出租率：" + occItem);
		String adrItem = request.getParameter("adrItem").trim();
		msgList.add("酒店平均房价：" + adrItem);
		String revPARItem = request.getParameter("revPARItem").trim();
		msgList.add("平均收入：" + revPARItem);
		Calendar cd = Calendar.getInstance();
		String loadTime = "'" + cd.get(Calendar.YEAR) + "-" + (cd.get(Calendar.MONTH) + 1) + "-" + cd.get(Calendar.DAY_OF_MONTH) + "'";
		msgList.add("录入时间：" + cd.get(Calendar.YEAR) + "-" + (cd.get(Calendar.MONTH) + 1) + "-" + cd.get(Calendar.DAY_OF_MONTH));
		String busTime = request.getParameter("busTime").trim();
		String busTime0 = "";
		if ("".equals(busTime)) {
			busTime0 = null;
		} else {
			busTime0 = "'" + busTime + "'";
		}
		msgList.add("业务时间：" + busTime);
		String loader = request.getParameter("loader").trim();
		msgList.add("录入人：" + loader);
		String sql = "insert into item (id, hotelId, occItem, adrItem, revPARItem, loadTime, busTime, loader) values ("
				+ id + "," + hotelId + ", '" + occItem + "', '" + adrItem + "', '" + revPARItem + "', " + loadTime + ", " + busTime0 + ", '" + loader + "')";
		return sql;
	}
	
	public String roomTypeInsert(HttpServletRequest request) {
		int id = dop.excuteID(tableforAll("roomtype"));
		String roomtype = request.getParameter("roomType").trim();
		String sql = "insert into roomtype (id, roomType) values (" + id + ",'" + roomtype + "')";
		return sql;
	}

	public String sqlSelect(HttpServletRequest request, String tableName) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		boolean flag = true;
		map = request.getParameterMap();
		Iterator<String> keySet = map.keySet().iterator();
		String sql = "select * from " + tableName + " where ";
		String busTime = "";
		String busTime1 = "";
		boolean busFlag = false;
		while (keySet.hasNext()) {
			String key = keySet.next();
			if (!"hiddenValue".equals(key) && !"".equals(map.get(key)[0]) && !"null".equals(map.get(key)[0]) && !"hotelIdHidden".equals(key) && !"idHidden".equals(key)) {
				if (("busTime".equals(key) || "busTime1".equals(key)) && !"".equals(map.get("busTime")[0]) && !"".equals(map.get("busTime1")[0])) {
					busTime = map.get("busTime")[0];
					busTime1 = map.get("busTime1")[0];
					busFlag = true;
					continue;
				}
				if (flag) {
					sql = sql + key + " like '%" + map.get(key)[0] + "%' ";
					flag = false;
				} else {
					sql = sql + "and " + key + " like '%" + map.get(key)[0] + "%' ";
				}
			}
		}
		if (busFlag && !flag) {
			sql = sql + " and busTime between '" + busTime + "' and '" + busTime1 + "'";
		} else if (busFlag && flag) {
			sql = sql + " busTime between '" + busTime + "' and '" + busTime1 + "'";
			flag = false;
		}
		if (flag) {
			Map<String, String> sqlMap = new HashMap<String, String>();
			sqlMap.put("busTime", new Common().sysDate("yyyy-MM-dd"));
			sql = this.sqlCreate("item", sqlMap);
		}
//		System.out.println(sql);
		return sql;
	}
	
	public String sqlUpdate(HttpServletRequest request, String tableName, String keyWord) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		Map<String, String> checkMap = new HashMap<String, String>();
		Map<String, String> checkListMap = new HashMap<String, String>();
		List<Map<String, String>> checkList = new ArrayList<Map<String, String>>();
		boolean flag = true;
		map = request.getParameterMap();
		checkMap.put(keyWord.substring(0, (keyWord.length() - 6)), map.get(keyWord)[0]);
		checkList = dop.excuteCheckUpdate(tableName, checkMap);
		if (checkList == null || checkList.size() != 1) {
			return "";
		}
		checkListMap = checkList.get(0);
		Iterator<String> keySet = map.keySet().iterator();
		String sql = "update " + tableName + " set ";
		String keyValue = "";
		Calendar cd = Calendar.getInstance();
		String systemTime = cd.get(Calendar.YEAR) + "-" + (cd.get(Calendar.MONTH) + 1) + "-" + cd.get(Calendar.DAY_OF_MONTH);
		while (keySet.hasNext()) {
			String key = keySet.next();
			String value = "";
			if (!"null".equals(map.get(key)[0])){
				value = map.get(key)[0];
			}
			if ("updateTime".equals(key)) {
				value = systemTime;
			}
			if (!"hiddenValue".equals(key) && !keyWord.equals(key) && !"".equals(value) && !value.equals(checkListMap.get(key))) {
				if (flag) {
					sql = sql + key + " = '" + value + "' ";
					flag = false;
				} else {
					sql = sql + ", " + key + " = '" + value + "' ";
				}
			} else if (keyWord.equals(key)) {
				keyValue = value;
			}
		}
		sql = sql + "where " + keyWord.substring(0, (keyWord.length() - 6)) + " = '" + keyValue + "'";
		if (flag) {
			sql = "";
		}
//		System.out.println(sql);
		return sql;
	}
	
	public String sqlDelete(HttpServletRequest request, String tableName, String keyWord) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		Map<String, String> checkMap = new HashMap<String, String>();
		List<Map<String, String>> checkList = new ArrayList<Map<String, String>>();
		map = request.getParameterMap();
		checkMap.put(keyWord.substring(0, (keyWord.length() - 6)), map.get(keyWord)[0]);
		checkList = new ArrayList<Map<String, String>>();
		checkList = dop.excuteCheckUpdate(tableName, checkMap);
		if (checkList == null || checkList.size() == 0) {
			return "";
		}
		String sql = "delete from " + tableName + " where " 
		    + keyWord.substring(0, (keyWord.length() - 6)) + " = '" + map.get(keyWord)[0] + "'";
		return sql;
	}

	public String sqlCreate(String tableName, Map<String, String> map) {
		String sql = "select * from " + tableName;
		if (map == null || map.isEmpty()) {
			return sql;
		}
		Iterator<String> it = map.keySet().iterator();
		boolean flag = true;
		sql = sql + " where ";
		while (it.hasNext()) {
			String key = it.next();
			if (flag) {
				sql = sql + key + " = '" + map.get(key) + "'";
				flag = false;
			} else {
				sql = sql + " and "+ key + " = '" + map.get(key) + "'";
			}
		}
		return sql;
	}
	
	public String busSql (HttpServletRequest request) {
		String sql = "select * from item where";
		String year = request.getParameter("year");
		String month = request.getParameter("month") + 1;
		String hotelId = request.getParameter("hotelId");
		sql = sql + " busTime like '" + year + "-" + month + "%'";
		if (!"".equals(hotelId)) {
			sql = sql + " and hotelId = " + hotelId;
		}
		return sql;
	} 
	
	public String tableforAll(String tableName) {
		String sql = "select * from " + tableName;
		return sql;
	}
}
