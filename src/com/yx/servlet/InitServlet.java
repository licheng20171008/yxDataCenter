package com.yx.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yx.DBConnect.DBData;
import com.yx.DBConnect.DBOpration;
import com.yx.dao.Common;
import com.yx.dao.Hotelinfo;
import com.yx.dao.Item;
import com.yx.fileService.ExportExcel;

/**
 * 初始化添加页面
 */
public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		String hiddenValue = request.getParameter("hiddenValue");
		DBData dd = new DBData();
		DBOpration du = new DBOpration();
		String sql = "";
		if ("0".equals(hiddenValue)) {
			List<Hotelinfo> hiList = new ArrayList<Hotelinfo>();
			hiList = du.excuteHotelinfo(dd.tableforAll("hotelinfo"));
			request.setAttribute("hiList", hiList);
			session.removeAttribute("hiList");
			session.setAttribute("hiList", hiList);
			request.getRequestDispatcher("/jsp/yxHotelAdd.jsp?addType=0").forward(request, response);
		} else if ("1".equals(hiddenValue)) {
			List<String> msgList = new ArrayList<String>();
			sql = dd.hotelinfoInsert(request, msgList);
			boolean flag = true;
			String message = "";
			flag = du.excuteInsert(sql);
			if (!flag) {
				message = "数据上传失败";
			} else {
				message = "数据上传成功";
			}
			msgList.add(message);
			List<Hotelinfo> hiList = new ArrayList<Hotelinfo>();
			sql = dd.tableforAll("hotelinfo");
			hiList = du.excuteHotelinfo(sql);
			request.setAttribute("hiList", hiList);
			session.removeAttribute("hiList");
			session.setAttribute("hiList", hiList);
			request.getRequestDispatcher("/jsp/yxHotelAdd.jsp?message=" + msgList + "&addType=0").forward(request, response);
		} else if ("2".equals(hiddenValue)) {
			List<String> msgList = new ArrayList<String>();
			sql = dd.itemInsert(request, msgList);
			boolean flag = true;
			String message = "";
			flag = du.excuteInsert(sql);
			if (!flag) {
				message = "数据上传失败";
			} else {
				message = "数据上传成功";
			}
			msgList.add(message);
			request.setAttribute("hiList", session.getAttribute("hiList"));
			request.getRequestDispatcher("/jsp/yxHotelAdd.jsp?message=" + msgList + "&addType=1").forward(request, response);
		} else if ("3".equals(hiddenValue)) {
			List<Hotelinfo> hiList1 = new ArrayList<Hotelinfo>();
			List<Item> itemList = new ArrayList<Item>();
			sql = dd.tableforAll("hotelinfo");
			hiList1 = du.excuteHotelinfo(sql);
			session.removeAttribute("hiList1");
			session.setAttribute("hiList1", hiList1);
			Map<String, String> sqlMap = new HashMap<String, String>();
			sqlMap.put("busTime", new Common().sysDate("yyyy-MM-dd"));
			sql = dd.sqlCreate("item", sqlMap);
			itemList = du.excuteItem(sql);
			session.removeAttribute("itemList");
			session.setAttribute("itemList", itemList);
			request.setAttribute("hiList", session.getAttribute("hiList"));
			request.setAttribute("hiList1", session.getAttribute("hiList1"));
			request.setAttribute("itemList", session.getAttribute("itemList"));
			request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=0").forward(request, response);
		} else if ("4".equals(hiddenValue)) {
			List<Hotelinfo> hiList1 = new ArrayList<Hotelinfo>();
			request.setAttribute("hiList", session.getAttribute("hiList"));
			sql = dd.sqlSelect(request, "hotelinfo");
			hiList1 = du.excuteHotelinfo(sql);
			session.removeAttribute("hiList1");
			session.setAttribute("hiList1", hiList1);
			request.setAttribute("hiList1", session.getAttribute("hiList1"));
			request.setAttribute("itemList", session.getAttribute("itemList"));
			request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=0").forward(request, response);
		} else if ("5".equals(hiddenValue)) {
			List<Item> itemList = new ArrayList<Item>();
			request.setAttribute("hiList", session.getAttribute("hiList"));
			sql = dd.sqlSelect(request, "item");
			itemList = du.excuteItem(sql);
			session.removeAttribute("itemList");
			session.setAttribute("itemList", itemList);
			request.setAttribute("hiList1", session.getAttribute("hiList1"));
			request.setAttribute("itemList", session.getAttribute("itemList"));
			request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=1").forward(request, response);
		} else if ("6".equals(hiddenValue)) {
			List<Hotelinfo> hiList = new ArrayList<Hotelinfo>();
			List<Hotelinfo> hiList1 = new ArrayList<Hotelinfo>();
			sql = dd.sqlUpdate(request, "hotelinfo", "hotelIdHidden");
			du.excuteUpdate(sql);
			sql = dd.tableforAll("hotelinfo");
			hiList1 = du.excuteHotelinfo(sql);
			session.removeAttribute("hiList1");
			session.setAttribute("hiList1", hiList1);
			sql = dd.tableforAll("hotelinfo");
			hiList = du.excuteHotelinfo(sql);
			session.removeAttribute("hiList");
			session.setAttribute("hiList", hiList);
			request.setAttribute("hiList", session.getAttribute("hiList"));
			request.setAttribute("hiList1", session.getAttribute("hiList1"));
			request.setAttribute("itemList", session.getAttribute("itemList"));
			request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=0").forward(request, response);
		} else if ("7".equals(hiddenValue)) {
			List<Hotelinfo> hiList = new ArrayList<Hotelinfo>();
			List<Hotelinfo> hiList1 = new ArrayList<Hotelinfo>();
			sql = dd.sqlDelete(request, "hotelinfo", "hotelIdHidden");
			if (!"".equals(sql)) {
				du.excuteUpdate(sql);
				sql = dd.sqlDelete(request, "item", "hotelIdHidden");
				if (!"".equals(sql)) {
					du.excuteUpdate(sql);
				}
			}
			sql = dd.tableforAll("hotelinfo");
			hiList1 = du.excuteHotelinfo(sql);
			session.removeAttribute("hiList1");
			session.setAttribute("hiList1", hiList1);
			sql = dd.tableforAll("hotelinfo");
			hiList = du.excuteHotelinfo(sql);
			session.removeAttribute("hiList");
			session.setAttribute("hiList", hiList);
			request.setAttribute("hiList", session.getAttribute("hiList"));
			request.setAttribute("hiList1", session.getAttribute("hiList1"));
			request.setAttribute("itemList", session.getAttribute("itemList"));
			request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=0").forward(request, response);
		} else if ("8".equals(hiddenValue)) {
			List<Item> itemList = new ArrayList<Item>();
			sql = dd.sqlUpdate(request, "item", "idHidden");
			du.excuteUpdate(sql);
			Map<String, String> sqlMap = new HashMap<String, String>();
			sqlMap.put("busTime", new Common().sysDate("yyyy-MM-dd"));
			sql = dd.sqlCreate("item", sqlMap);
			itemList = du.excuteItem(sql);
			session.removeAttribute("itemList");
			session.setAttribute("itemList", itemList);
			request.setAttribute("hiList", session.getAttribute("hiList"));
			request.setAttribute("hiList1", session.getAttribute("hiList1"));
			request.setAttribute("itemList", session.getAttribute("itemList"));
			request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=1").forward(request, response);
		} else if ("9".equals(hiddenValue)) {
			List<Item> itemList = new ArrayList<Item>();
			sql = dd.sqlDelete(request, "item", "idHidden");
			if (!"".equals(sql)) {
				du.excuteUpdate(sql);
				Map<String, String> sqlMap = new HashMap<String, String>();
				sqlMap.put("busTime", new Common().sysDate("yyyy-MM-dd"));
				sql = dd.sqlCreate("item", sqlMap);
				itemList = du.excuteItem(sql);
				session.removeAttribute("itemList");
				session.setAttribute("itemList", itemList);
			}
			request.setAttribute("hiList", session.getAttribute("hiList"));
			request.setAttribute("hiList1", session.getAttribute("hiList1"));
			request.setAttribute("itemList", session.getAttribute("itemList"));
			request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=1").forward(request, response);
		} else if ("10".equals(hiddenValue) || "11".equals(hiddenValue)) {
			List<?> commonList = null;
			String[] itemArray = null;
			String fileTitle = "";
			
			// 系统时间
			String sysDate = new Common().sysDate("yyyy-MM-dd");
			if ("10".equals(hiddenValue)) {
				commonList = (List<?>) session.getAttribute("hiList1");
				itemArray = new String[6];
				itemArray[0] = "酒店ID";
				itemArray[1] = "酒店名称";
				itemArray[2] = "酒店房间数";
				itemArray[3] = "酒店联系人";
				itemArray[4] = "酒店联系方式";
				itemArray[5] = "备注";
				fileTitle = "酒店基本信息" + sysDate;
			} else if ("11".equals(hiddenValue)) {
				commonList = (List<?>) session.getAttribute("itemList");
				itemArray = new String[9];
				itemArray[0] = "序号";
				itemArray[1] = "酒店名称";
				itemArray[2] = "房间出租率";
				itemArray[3] = "酒店平均房价";
				itemArray[4] = "平均收入";
				itemArray[5] = "录入日期";
				itemArray[6] = "最新修改日期";
				itemArray[7] = "业务日期";
				itemArray[8] = "录入人";
				fileTitle = "酒店房价基本信息" + sysDate;
			}
			String servletPath = request.getSession().getServletContext().getRealPath("temPath") + File.separator + fileTitle + ".xls";
			ExportExcel ee = new ExportExcel();
			ee.writeFile(commonList, servletPath,itemArray,fileTitle);
			new Common().download(servletPath, response);
			request.setAttribute("hiList", session.getAttribute("hiList"));
			request.setAttribute("hiList1", session.getAttribute("hiList1"));
			request.setAttribute("itemList", session.getAttribute("itemList"));
			if ("10".equals(hiddenValue)) {
				request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=0");
			} else if ("11".equals(hiddenValue)) {
				request.getRequestDispatcher("/jsp/yxHotelInfo.jsp?selectType=1");
			}
		} else {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		this.doGet(request, response);
	}
}