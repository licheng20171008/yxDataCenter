package com.yx.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yx.DBConnect.DBOpration;
import com.yx.DBConnect.DataOpration;
import com.yx.dao.Common;
import com.yx.dao.Hotelinfo;
import com.yx.dao.Item;
import com.yx.fileService.ExportExcel;
import com.yx.fileService.ExportRoom;

/**
 * 初始化添加页面
 */
public class RoomExportServlet extends HttpServlet {

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
		DataOpration dop = new DataOpration();
		DBOpration du = new DBOpration();
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		List<Integer> yearList = new ArrayList<Integer>();
		for (int i = -5; i < 6; i++) {
			yearList.add(currentYear + i);
		}
		session.removeAttribute("yearList");
		session.setAttribute("yearList", yearList);
		request.setAttribute("currentYear", currentYear);
		request.setAttribute("currentMonth", currentMonth);
		request.setAttribute("yearList", session.getAttribute("yearList"));
		List<Hotelinfo> hiList = new ArrayList<Hotelinfo>();
		hiList = du.excuteHotelinfo(dop.tableforAll("hotelinfo"));
		request.setAttribute("hiList", hiList);
		if ("0".equals(hiddenValue)) {
			request.getRequestDispatcher("/jsp/yxRoomExport.jsp").forward(request, response);
		} else if ("1".equals(hiddenValue)) {
			new ExportRoom(request, response);
			request.getRequestDispatcher("/jsp/yxRoomExport.jsp");
		} else if ("2".equals(hiddenValue)) {

			String sql = dop.busSql(request);
			List<Item> itemList = new ArrayList<Item>();
			itemList = dop.returnItemList(sql);
			session.setAttribute("itemList", itemList);
			
			// 系统时间
			String sysDate = new Common().sysDate("yyyy-MM-dd");
			List<?> commonList = (List<?>) session.getAttribute("itemList");
			String[] itemArray = new String[9];
			itemArray[0] = "序号";
			itemArray[1] = "酒店名称";
			itemArray[2] = "房间出租率";
			itemArray[3] = "酒店平均房价";
			itemArray[4] = "平均收入";
			itemArray[5] = "录入日期";
			itemArray[6] = "最新修改日期";
			itemArray[7] = "业务日期";
			itemArray[8] = "录入人";
			String fileTitle = "酒店房价基本信息" + sysDate;
			String servletPath = request.getSession().getServletContext().getRealPath("temPath") + File.separator + fileTitle + ".xls";
			ExportExcel ee = new ExportExcel();
			ee.writeFile(commonList, servletPath,itemArray,fileTitle);
			new Common().download(servletPath, response);
			request.getRequestDispatcher("/jsp/yxRoomExport.jsp");
		} else {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		this.doGet(request, response);
	}
}