package com.yx.DBConnect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.yx.dao.Common;
import com.yx.dao.Item;
import com.yx.dao.Pdroom;
import com.yx.dao.Roomtype;

public class DataOpration extends DBData {

	DBOpration dbo = new DBOpration();
	public List<Roomtype> returnRTList () {
		List<Roomtype> rtList = new ArrayList<Roomtype>();
		String sql = super.tableforAll("roomtype");
		List<Object> objList = dbo.excuteSQL(sql, Roomtype.class);
		for (Object obj : objList) {
			rtList.add((Roomtype) obj);
		}
		return rtList;
	}
	
	public List<Pdroom> returnPDList (String sql) {
		List<Pdroom> pdList = new ArrayList<Pdroom>();
		List<Object> objList = dbo.excuteSQL(sql, Pdroom.class);
		for (Object obj : objList) {
			pdList.add((Pdroom) obj);
		}
		return pdList;
	}
	
	public List<Item> returnItemList (String sql) {
		List<Item> ItemList = new ArrayList<Item>();
		List<Object> objList = dbo.excuteSQL(sql, Item.class);
		for (Object obj : objList) {
			ItemList.add((Item) obj);
		}
		return ItemList;
	}
	
	public String sqlUpdate(HttpServletRequest request, String tableName, String keyWord) {
		String laterroomTypeID = request.getParameter("laterroomTypeID");
		String laterroomType = request.getParameter("laterroomType");
		String sql = "update " + tableName + " set roomType = '" + laterroomType + "' where id = " + laterroomTypeID;
		return sql;
	}
	
	public String sqlUpdatePdroom(HttpServletRequest request, String tableName) {
		String pdroomdate = request.getParameter("pdroomdate");
		String sql = "select * from " + tableName + " where date = '" + pdroomdate + "'";
		return sql;
	}
	
	public void sqlPdroom(HttpServletRequest request) {
		String pdroomdate = request.getParameter("pdroomdate");
		List<Pdroom> pdList = new ArrayList<Pdroom>();
		List<Pdroom> pdListCheck = new ArrayList<Pdroom>();
		Map<String, String[]> map = request.getParameterMap();
		map = request.getParameterMap();
		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		int maxNum = 0;
		String key = "";
		while (it.hasNext()) {
			key = it.next();
			if (key.split("_").length > 1) {
				maxNum = maxNum > Integer.parseInt(key.split("_")[1])?maxNum:Integer.parseInt(key.split("_")[1]);
			}
		}
		int index = 0;
		String sql = "";
		String paramName = "pddateroomid_" + index;
		while(index <= maxNum) {
			if (keySet.contains(paramName)) {
				String pddateroomidParam = "pddateroomid_" + index;
				String pddateroomid = request.getParameter(pddateroomidParam);
				String pdroompriceParam = "pdroomprice_" + index;
				String pdroomprice = request.getParameter(pdroompriceParam);
				String idParam = "id_" + index;
				String id = request.getParameter(idParam);
				Pdroom pr = new Pdroom();
				if ("".equals(id)) {
					pr.setId(0);
				} else {
					pr.setId(Integer.parseInt(id));
				}
				pr.setPddateroomid(Integer.parseInt(pddateroomid));
				pr.setPdroomprice(pdroomprice);
				pdList.add(pr);
			}
			index++;
			paramName = "pddateroomid_" + index;
		}
		
		String sqlCheck = "select * from pdroom where date = '" + pdroomdate + "'";
		List<Object> objListCheck = dbo.excuteSQL(sqlCheck, Pdroom.class);
		for (Object objCheck : objListCheck) {
			pdListCheck.add((Pdroom) objCheck);
		}
		
		for(Pdroom pdrCheck : pdListCheck) {
			boolean checkFlag = true;
			for (Pdroom pdr : pdList) {
				if (pdr.getPddateroomid() == pdrCheck.getPddateroomid()) {
					checkFlag = false;
					break;
				}
			}
			
			if (checkFlag) {
				sql = "delete from pdroom where date = '" + pdroomdate + "' and pddateroomid = " + pdrCheck.getPddateroomid();
				dbo.excuteUpdate(sql);
			}
		}
		for (Pdroom pdr : pdList) {
			sql = "select * from pdroom where id = " + pdr.getId() + " and date = '" + pdroomdate + "'";
			if (dbo.excuteSize(sql)) {
				sql = "update pdroom set pdroomprice = " + pdr.getPdroomprice() + ", pddateroomtype = (select roomType from roomtype where id = " + pdr.getPddateroomid() + ") where id = " + pdr.getId() + " and date = '" + pdroomdate + "'";
				dbo.excuteUpdate(sql);
			} else {
				int id = dop.excuteID(tableforAll("pdroom"));
				sql = "insert into pdroom (id, date, pddateroomid, pddateroomtype, pdroomprice) values (" + id + ", '" + pdroomdate + "', " + pdr.getPddateroomid() + ", (select roomType from roomtype where id = " + pdr.getPddateroomid() + "), " + pdr.getPdroomprice() + ")";
				dbo.excuteInsert(sql);
			}
		}
	}
	
	public Map<Integer, String[]> paWeekMap(HttpServletRequest request) {
		
		List<String> weekDayList = new ArrayList<String>();
		String pdroomdate = request.getParameter("pdroomdate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(pdroomdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			weekOfMonth = weekOfMonth - 1;
		}
		weekDayList = this.weekDay(year, month, weekOfMonth);
		return this.weekDayOpt(weekDayList);
	}
	
	public List<String> weekDay (int year, int month, int weekOfMonth) {
		List<String> weekDayList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.WEEK_OF_MONTH, weekOfMonth);
		if (weekOfMonth == 1) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
			int wYear = calendar.get(Calendar.YEAR);
			int wMonth = calendar.get(Calendar.MONTH) + 1;
			int wday = calendar.get(Calendar.DAY_OF_MONTH);
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			
			weekDayList.add(wYear + "-" + new Common().addZero(String.valueOf(wMonth), 1, 2) + "-" 
			    + new Common().addZero(String.valueOf(wday), 1, 2) + "|" + week);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return weekDayList;
	}
	
	public Map<Integer, String[]> weekDayOpt (List<String> weekDayList) {
		Map<Integer, String[]> returnMap = new HashMap<Integer, String[]>();
		List<Integer> rtList = new ArrayList<Integer>();
		for (String weekDay : weekDayList) {
			List<Object> daoList = new ArrayList<Object>();
			String sql = "select * from pdroom where date = '" + weekDay.split("\\|")[0] + "'";
			daoList = dbo.excuteSQL(sql, Pdroom.class);
			for (Object obj : daoList) {
				Pdroom pd = (Pdroom) obj;
				if (!rtList.contains(pd.getPddateroomid())) {
					rtList.add(pd.getPddateroomid());
				}
			}
		}
		
		for (int pddateroomid : rtList) {
			List<Object> pddaoList = new ArrayList<Object>();
			String[] priceArray = new String[7];
			String pricesql = "select * from pdroom where STR_TO_DATE(date,'%Y-%m-%d') BETWEEN '" + 
			    weekDayList.get(0).split("\\|")[0] + "' and '" + weekDayList.get(weekDayList.size() - 1).split("\\|")[0] 
			        + "' and pddateroomid = " + pddateroomid;
			pddaoList = dbo.excuteSQL(pricesql, Pdroom.class);
			int index = 0;
			for (Object priceObj : pddaoList) {
				Pdroom pdPrice = (Pdroom) priceObj;
				for (String weekDay : weekDayList ) {
					if (pdPrice.getDate().equals(weekDay.split("\\|")[0])) {
						index = Integer.parseInt(weekDay.split("\\|")[1]) - 2;
						if (index < 0) {
							index = 6;
						}
						break;
					}
				}
				priceArray[index] = pdPrice.getPdroomprice();
			}
			
			returnMap.put(pddateroomid, priceArray);
			
		}
		String[] dateArray = new String[7];
		for (String weekDay : weekDayList ) {
			String date = weekDay.split("\\|")[0];
			int week = Integer.parseInt(weekDay.split("\\|")[1]) - 2;
			if (week < 0) {
				week = 6;
			}
			dateArray[week] = date;
		}
		returnMap.put(1000000, dateArray);
		returnMap.put(1000001, new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"});
		return returnMap;
	}
	
	public String rtType(int pddateroomid) {
		String sql = "select * from roomtype where id = " + pddateroomid;
		List<Object> rtList = new ArrayList<Object>();
		rtList = dbo.excuteSQL(sql, Roomtype.class);
		if (rtList.size() > 0) {
			Roomtype rt = (Roomtype) rtList.get(0);
			return rt.getRoomType();
		}
		return "不存在该房型";
	}
}