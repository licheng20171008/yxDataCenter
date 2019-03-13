package com.yx.DBConnect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yx.dao.Hotelinfo;
import com.yx.dao.Item;

public class DBOpration {

	public boolean excuteInsert(String sql) {

		Connection conn = DBConnect.getConn();
		Statement stmt = DBConnect.getStatement(conn);
		DBConnect.getResultSetInsert(stmt, sql);
		try {
			// 关闭连接
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Hotelinfo> excuteHotelinfo(String sql) {

		List<Hotelinfo> hiList = new ArrayList<Hotelinfo>();
		Connection conn = DBConnect.getConn();
		Statement stmt = DBConnect.getStatement(conn);
		ResultSet rs = DBConnect.getResultSet(stmt, sql);
		try {
			while (rs.next()) {
				Hotelinfo hi = new Hotelinfo();
				hi.setName(rs.getString("name"));
				hi.setHotelId(rs.getInt("hotelId"));
				hi.setRoomNumber(rs.getInt("roomNumber"));
				hi.setCallMan(rs.getString("callMan"));
				hi.setTel(rs.getString("tel"));
				hi.setRemark(rs.getString("remark"));
				hiList.add(hi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hiList;
	}

	public List<Item> excuteItem(String sql) {

		List<Item> itemList = new ArrayList<Item>();
		Connection conn = DBConnect.getConn();
		Statement stmt = DBConnect.getStatement(conn);
		ResultSet rs = DBConnect.getResultSet(stmt, sql);
		try {
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setHotelId(rs.getInt("hotelId"));
				item.setOccItem(rs.getString("occItem"));
				item.setAdrItem(rs.getString("adrItem"));
				item.setRevPARItem(rs.getString("revPARItem"));
				item.setLoadTime(rs.getDate("loadTime"));
				item.setUpdateTime(rs.getDate("updateTime"));
				item.setBusTime(rs.getDate("busTime"));
				item.setLoader(rs.getString("loader"));
				itemList.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemList;
	}

	public List<Object> excuteSQL(String sql, Class<?> clazz) {

		Field[] fs = clazz.getDeclaredFields();
		Method[] me = clazz.getMethods();
		List<Object> daoList = new ArrayList<Object>();
		Connection conn = DBConnect.getConn();
		Statement stmt = DBConnect.getStatement(conn);
		ResultSet rs = DBConnect.getResultSet(stmt, sql);
		try {
			while (rs.next()) {
				try {
					Object cla = clazz.newInstance();
					for (Field field : fs) {
						field.setAccessible(true);
						String name = field.getName();
						String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
						Method method = null;
						for(Method m : me) {
							if (("set" + methodName).equals(m.getName())) {
								method = m;
								break;
							}
						}
						Object value = null;
						if ("String".equals(field.getType().getSimpleName())) {
							value = rs.getString(name);
						} else if ("int".equals(field.getType().getName())) {
							value = rs.getInt(name);
						} else if ("Date".equals(field.getType().getSimpleName())) {
							value = rs.getDate(name);
						}
						method.invoke(cla, value);
					}
					daoList.add(cla);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return daoList;
	}
	
	public int excuteID(String sql) {

		Connection conn = DBConnect.getConn();
		Statement stmt = DBConnect.getStatement(conn);
		ResultSet rs = DBConnect.getResultSet(stmt, sql);
		int index = 1;
		try {
			while (rs.next()) {
				int rowIndex = rs.getInt(rs.getMetaData().getColumnName(1));
				if (rowIndex == index) {
					index = index + 1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return index;
	}
	
	public List<Map<String, String>> excuteCheckUpdate(String tableName, Map<String, String> map) {

		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		String sql = "select * from " + tableName + " where ";
		if (map.isEmpty()) {
			return null;
		}
		Iterator<String> it = map.keySet().iterator();
		boolean flag = true;
		while (it.hasNext()) {
			String key = it.next();
			if (flag) {
				sql = sql + key + " = '" + map.get(key) + "'";
				flag = false;
			} else {
				sql = sql + " and "+ key + " = '" + map.get(key) + "'";
			}
		}
		
		Connection conn = DBConnect.getConn();
		Statement stmt = DBConnect.getStatement(conn);
		ResultSet rs = DBConnect.getResultSet(stmt, sql);
		try {
			while(rs.next()) {
				Map<String, String> returnMap = new HashMap<String, String>();
				for (int i = 1; i < (rs.getMetaData().getColumnCount() + 1); i++) {
					returnMap.put(rs.getMetaData().getColumnName(i), rs.getString(rs.getMetaData().getColumnName(i)));
				}
				returnList.add(returnMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnList;
	}
	
	public void excuteUpdate(String sql) {

		Connection conn = DBConnect.getConn();
		Statement stmt = DBConnect.getStatement(conn);
		DBConnect.getResultSetInsert(stmt, sql);
		try {
			// 关闭连接
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean excuteSize(String sql) {

		Connection conn = DBConnect.getConn();
		Statement stmt = DBConnect.getStatement(conn);
		ResultSet rs = DBConnect.getResultSet(stmt, sql);
		boolean flag = false;
		try {
			
			if (rs.next()) {
				flag = true;
			}
			
			// 关闭连接
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
