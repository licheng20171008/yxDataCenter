<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.yx.dao.Hotelinfo"%>
<title>武汉亚洲大酒店</title>
</head>
<%
    int currentYear = (Integer) request.getAttribute("currentYear");
    int currentMonth = (Integer) request.getAttribute("currentMonth");
    List<Integer> yearList = (List<Integer>)request.getAttribute("yearList") == null?new ArrayList<Integer>():(List<Integer>)request.getAttribute("yearList");
    List<Hotelinfo> hiList = (List<Hotelinfo>) request.getAttribute("hiList") == null?new ArrayList<Hotelinfo>():(List<Hotelinfo>) request.getAttribute("hiList");
%>
<body>
<hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
<form action="Export.action" method="post">
<input type="hidden" id="hiddenValue" name="hiddenValue" value="1" />
<div>
<span>酒店名称：</span> 
<select name="hotelId" style="width: 170px; height: 25px; font-family: '宋体'; font-size: 19px;">
<option style="font-family: '宋体'; font-size: 19px;"  value=""/>
				<%
					for (int i = 0; i < hiList.size(); i++) {
				%>
				<option style="font-family: '宋体'; font-size: 19px;" value="<%=hiList.get(i).getHotelId()%>"><%=hiList.get(i).getName()%></option>
				<%
					}
				%>
			</select> 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<span>年度：</span>
<select id="year" name="year" style="width: 170px; height: 25px; font-family: '宋体'; font-size: 19px;">
    <%
    for (int year : yearList) {
    	%>
    	<option value="<%=year %>" style="font-family: '宋体'; font-size: 19px;"><%=year %></option>
    	<%
    }
    %>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<span>月份：</span>
<select id="month" name="month" style="width: 170px; height: 25px; font-family: '宋体'; font-size: 19px;">
    <option value="0" style="font-family: '宋体'; font-size: 19px;">1</option>
    <option value="1" style="font-family: '宋体'; font-size: 19px;">2</option>
    <option value="2" style="font-family: '宋体'; font-size: 19px;">3</option>
    <option value="3" style="font-family: '宋体'; font-size: 19px;">4</option>
    <option value="4" style="font-family: '宋体'; font-size: 19px;">5</option>
    <option value="5" style="font-family: '宋体'; font-size: 19px;">6</option>
    <option value="6" style="font-family: '宋体'; font-size: 19px;">7</option>
    <option value="7" style="font-family: '宋体'; font-size: 19px;">8</option>
    <option value="8" style="font-family: '宋体'; font-size: 19px;">9</option>
    <option value="9" style="font-family: '宋体'; font-size: 19px;">10</option>
    <option value="10" style="font-family: '宋体'; font-size: 19px;">11</option>
    <option value="11" style="font-family: '宋体'; font-size: 19px;">12</option>
</select>
</div>
<br>
<div>
<input type="submit" value="酒店房价趋势报表导出" onclick="changeValue(1)"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" value="酒店房价信息报表导出" onclick="changeValue(2)"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" value="返回首页" onclick="changeValue(3)"/>
</div>
</form>
<hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
</body>
<script type="text/javascript">
$(document).ready(function() {
	var currentYear = '<%=currentYear %>';
	var currentMonth = '<%=currentMonth %>';
	$("#year").children().each(function(){
		if ($(this).val() == currentYear) {
			$(this).attr("selected", true);
			return false;
		}
	});
	
	$("#month").children().each(function(){
		if ($(this).val() == currentMonth) {
            $(this).attr("selected", true);
            return false;
        }
	});
});

function changeValue(value) {
    $("#hiddenValue").val(value);
}
</script>
</html>