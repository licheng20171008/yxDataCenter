package com.yx.servlet;

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

import com.yx.DBConnect.DBOpration;
import com.yx.DBConnect.DataOpration;
import com.yx.dao.Pdroom;
import com.yx.dao.Roomtype;

/**
 * 初始化添加页面
 */
public class ReportServlet extends HttpServlet {

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
		DBOpration du = new DBOpration();
		DataOpration dop = new DataOpration();
		List<Roomtype> rtList = new ArrayList<Roomtype>();
		rtList = dop.returnRTList();
		String sql = "";
		String pdroomdate = request.getParameter("pdroomdate")==null?"":request.getParameter("pdroomdate");
		request.setAttribute("pdroomdate", pdroomdate);
		if ("".equals(pdroomdate)) {
			session.removeAttribute("pdList");
		}
		session.removeAttribute("roomtype");
		session.setAttribute("roomtype", rtList);
		String message = "";
		if ("0".equals(hiddenValue)) {
			request.setAttribute("roomtype", session.getAttribute("roomtype"));
			request.getRequestDispatcher("/jsp/yxHotelReport.jsp?addType=0&message=" + message).forward(request, response);
		} else if ("1".equals(hiddenValue)) {
			sql = dop.roomTypeInsert(request);
			if (du.excuteInsert(sql)) {
				message = "房型添加成功！！";
			} else {
				message = "房型添加失败！！";
			}
			rtList.clear();
			rtList = dop.returnRTList();
			session.removeAttribute("roomtype");
			session.setAttribute("roomtype", rtList);
			request.setAttribute("roomtype", session.getAttribute("roomtype"));
			request.setAttribute("pdList", session.getAttribute("pdList"));
			request.getRequestDispatcher("/jsp/yxHotelReport.jsp?addType=0&message=" + message).forward(request, response);
		} else if ("2".equals(hiddenValue)) {
			sql = dop.sqlUpdate(request, "roomtype", "id");
			du.excuteUpdate(sql);
			rtList.clear();
			rtList = dop.returnRTList();
			session.removeAttribute("roomtype");
			session.setAttribute("roomtype", rtList);
			request.setAttribute("roomtype", session.getAttribute("roomtype"));
			request.setAttribute("pdList", session.getAttribute("pdList"));
			request.getRequestDispatcher("/jsp/yxHotelReport.jsp?addType=0&message=" + message).forward(request, response);
		} else if ("3".equals(hiddenValue)) {
			dop.sqlPdroom(request);
			sql = dop.sqlUpdatePdroom(request, "pdroom");
			List<Pdroom> pdList = new ArrayList<Pdroom>();
			Map<Integer, String[]> pdWeekMap = new HashMap<Integer, String[]>();
			pdList = dop.returnPDList(sql);
			session.removeAttribute("pdList");
			session.setAttribute("pdList", pdList);
			pdWeekMap = dop.paWeekMap(request);
			session.removeAttribute("pdWeekMap");
			session.setAttribute("pdWeekMap", pdWeekMap);
			request.setAttribute("pdWeekMap", session.getAttribute("pdWeekMap"));
			request.setAttribute("pdList", session.getAttribute("pdList"));
			request.setAttribute("roomtype", session.getAttribute("roomtype"));
			request.getRequestDispatcher("/jsp/yxHotelReport.jsp?addType=1&message=" + message).forward(request, response);
		} else if ("4".equals(hiddenValue)) {
			sql = dop.sqlUpdatePdroom(request, "pdroom");
			List<Pdroom> pdList = new ArrayList<Pdroom>();
			Map<Integer, String[]> pdWeekMap = new HashMap<Integer, String[]>();
			pdList = dop.returnPDList(sql);
			session.removeAttribute("pdList");
			session.setAttribute("pdList", pdList);
			pdWeekMap = dop.paWeekMap(request);
			session.removeAttribute("pdWeekMap");
			session.setAttribute("pdWeekMap", pdWeekMap);
			request.setAttribute("pdWeekMap", session.getAttribute("pdWeekMap"));
			request.setAttribute("pdList", session.getAttribute("pdList"));
			request.setAttribute("roomtype", session.getAttribute("roomtype"));
			request.getRequestDispatcher("/jsp/yxHotelReport.jsp?addType=1&message=" + message).forward(request, response);
		} else {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		this.doGet(request, response);
	}
}