package com.yx.fileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.yx.DBConnect.DataOpration;
import com.yx.dao.Common;

public class ExportRoom {

	DataOpration dop = new DataOpration();
	private String year;
	private String month;
	int rowNum = 0;
	HSSFCellStyle style1;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public ExportRoom(HttpServletRequest request, HttpServletResponse response) {

		this.setYear(request.getParameter("year"));
		this.setMonth(request.getParameter("month"));
		Map<Integer, Map<Integer, String[]>> excelData = this.monthNum(year, month);
		String fileTitle = year + "年" + new Common().addZero(String.valueOf((Integer.parseInt(month) + 1)), 1, 2) + "月" + "酒店房价趋势报表";
		String servletPath = request.getSession().getServletContext().getRealPath("temPath") + File.separator + fileTitle + ".xls";
		this.writeFile(excelData, servletPath, fileTitle);
		new Common().download(servletPath, response);
	}

	private Map<Integer, Map<Integer, String[]>> monthNum(String year, String month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, Integer.parseInt(month));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int index = 1;
		if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_WEEK) 
				&& calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			index = 0;
		}
		int monthNum = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int dayNum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, dayNum);
		if (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
			monthNum = monthNum + 1;
		}
		Map<Integer, Map<Integer, String[]>> excelData = new HashMap<Integer, Map<Integer, String[]>>();
		for (int i = index; i < monthNum; i++) {
			Map<Integer, String[]> weekMap 
			    = dop.weekDayOpt(dop.weekDay(Integer.parseInt(year), Integer.parseInt(month), i));
			excelData.put(i, this.sortMap(weekMap));
		}
		return excelData;
	}

	private void writeFile(Map<Integer, Map<Integer, String[]>> excelData, String docsPath, String fileTitle) {

		// 文件流
		OutputStream os = null;

		// 声明一个工作薄
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(fileTitle);

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();

		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 把字体应用到当前的样式
		style.setFont(font);

		// 生成一个样式
		style1 = workbook.createCellStyle();

		// 设置这些样式
		style1.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		// 生成一个字体
		HSSFFont font1 = workbook.createFont();
		font1.setColor(HSSFColor.VIOLET.index);
		font1.setFontHeightInPoints((short) 12);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 把字体应用到当前的样式
		style1.setFont(font1);

		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setColumnWidth(0, (256 * 30 + 184));

		int index = 1;
		int excelSize = excelData.size();
		if (excelData.containsKey(0)) {
			index = 0;
			excelSize = excelSize -1;
		}
		for (int i = index; i <= excelSize; i++) {
			Map<Integer, String[]> weekMap = excelData.get(i);
			Iterator<Integer> it = weekMap.keySet().iterator();

			String[] dateArray = weekMap.get(1000000);
			creatRow(sheet, style, dateArray, 1000000);
			String[] weekArray = weekMap.get(1000001);
			creatRow(sheet, style, weekArray, 1000001);
			sheet.addMergedRegion(new CellRangeAddress(rowNum - 2, rowNum - 1, 0, 0));

			while (it.hasNext()) {
				int key = it.next();
				if (key != 1000000 && key != 1000001) {
					String[] dataArray = weekMap.get(key);
					creatRow(sheet, style2, dataArray, key);
				}
			}

			String[] endArray = null;
			creatRow(sheet, style, endArray, 0);
		}
		try {
			os = new FileOutputStream(docsPath);
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			// 清理资源
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void creatRow(HSSFSheet sheet, HSSFCellStyle style, String[] dataArray, int key) {

		// 产生表格标题行
		HSSFRow row1 = sheet.createRow(rowNum);
		if (dataArray != null && dataArray.length != 0) {
			int colNum1 = 0;
			HSSFCell cell1 = row1.createCell(colNum1);
			cell1.setCellStyle(style1);
			String roomType = new DataOpration().rtType(key);
			if ("不存在该房型".equals(roomType)) {
				roomType = "房型";
			}

			HSSFRichTextString text1 = new HSSFRichTextString(roomType);
			cell1.setCellValue(text1);

			for (String data : dataArray) {
				colNum1 = colNum1 + 1;
				HSSFCell cell = row1.createCell(colNum1);
				cell.setCellStyle(style);

				HSSFRichTextString text = new HSSFRichTextString(data);
				cell.setCellValue(text);
			}
		}
		rowNum = rowNum + 1;
	}

	private Map<Integer, String[]> sortMap(Map<Integer, String[]> map) {
		TreeMap<Integer, String[]> sortMap = new TreeMap<Integer, String[]>();
		String[] dateArray = map.get(1000000);
		for (int i = 0; i < dateArray.length; i++) {
			if (!dateArray[i].startsWith(year + "-" + new Common().addZero(String.valueOf((Integer.parseInt(month) + 1)), 1, 2) + "-")) {
				Iterator<Integer> it = map.keySet().iterator();
				while (it.hasNext()) {
					int key = it.next();
					map.get(key)[i] = "";
				}
			}
		}

		Iterator<Integer> it1 = map.keySet().iterator();
		while (it1.hasNext()) {
			int key = it1.next();
			sortMap.put(key, map.get(key));
		}
		return sortMap;
	}
}
