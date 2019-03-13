<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/laydate/laydate.js"></script>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.yx.dao.Hotelinfo"%>
<%@ page import="com.yx.dao.Item"%>
<title>武汉亚洲大酒店</title>
</head>
        <%
            String selectType = request.getParameter("selectType");
            List<Hotelinfo> hiList1 = (List<Hotelinfo>) request.getAttribute("hiList") == null?new ArrayList<Hotelinfo>():(List<Hotelinfo>) request.getAttribute("hiList");
            List<Hotelinfo> hiList2 = (List<Hotelinfo>) request.getAttribute("hiList") == null?new ArrayList<Hotelinfo>():(List<Hotelinfo>) request.getAttribute("hiList");
            List<Hotelinfo> hiList3 = (List<Hotelinfo>) request.getAttribute("hiList1") == null?new ArrayList<Hotelinfo>():(List<Hotelinfo>) request.getAttribute("hiList1");
            List<Item> itemList = (List<Item>) request.getAttribute("itemList") == null?new ArrayList<Item>():(List<Item>) request.getAttribute("itemList");
        %>
<body>
	<hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
	<form action="Init.action" method="post">
		<input type="hidden" name="hiddenValue" value="0" /> <span style="font-family: '宋体'; font-size: 19px;">请选择查询类型：</span>
		<select id="selectType" name="selectType" style="font-family: '宋体'; font-size: 19px;" onchange="onselectType(1)">
			<option style="width: 400px;font-family: '宋体'; font-size: 19px;" value="0">酒店基本信息查询</option>
			<option style="width: 400px;font-family: '宋体'; font-size: 19px;" value="1">酒店房价信息查询</option>
		</select> 
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" value="返回酒店信息添加" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" onclick="changeHidden(100)" value="返回首页" />
	</form>
	<hr id="0" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
	<form id="hotelInfoId" action="Init.action" method="post">
		<input type="hidden" name="hiddenValue" value="4" /> 
		<input id="hotelIdHidden" type="hidden" name="hotelIdHidden" value="" />
		<table border="0" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
			<tr>
				<td width="12%"><span style="font-family: '宋体'; font-size: 19px;">酒店名称:</span></td>
				<td width="38%"><select id="infoSelect" name="hotelId" style="width: 170px; height: 25px;font-family: '宋体'; font-size: 19px;">
						<option style="font-family: '宋体'; font-size: 19px;" value=""></option>
						<%
							for (int i = 0; i < hiList1.size(); i++) {
						%>
						<option style="font-family: '宋体'; font-size: 19px;" value="<%=hiList1.get(i).getHotelId()%>"><%=hiList1.get(i).getName()%></option>
						<%
							}
						%>
				</select></td>
				<td width="12%">酒店房间数:</td>
				<td width="38%"><input type="text" name="roomNumber" style="width: 167px; height: 20px" maxlength="4" /></td>
			</tr>
			<tr>
				<td width="12%">酒店联系人:</td>
				<td width="38%"><input type="text" name="callMan" style="width: 167px; height: 20px" maxlength="20" /></td>
				<td width="12%">酒店联系方式:</td>
				<td width="38%"><input type="tel" name="tel" style="width: 167px; height: 20px" maxlength="11" /></td>
			</tr>
			<tr>
				<td width="12%">备注:</td>
				<td width="38%"><input type="text" name="remark" style="width: 167px; height: 20px" maxlength="50" /></td>
				<td id="nameTitle" width="12%">酒店名称:</td>
				<td id="name" width="38%"><input type="text" name="name" style="width: 167px; height: 20px" maxlength="50" /></td>
			</tr>
			<tr>
				<td />
				<td />
				<td />
				<td />
			</tr>
		</table>
		<input type="submit" onclick="changeHidden(4)" value="查询酒店基本信息" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<input id="changeHotel" type="submit" onclick="changeHidden(6)" value="修改酒店基本信息" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<input id="deleteHotel" type="button" onclick="changeHidden(7)" value="删除酒店基本信息" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
        <input id="exportHotel" type="submit" onclick="changeHidden(10)" <% if (hiList3.size() == 0) { %>disabled="disabled" <%}%> value="导出酒店基本信息" />
	</form>
	<hr id="1" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
	<form id="itemId" action="Init.action" method="post">
		<input type="hidden" name="hiddenValue" value="5" /> <input id="idHidden" type="hidden" name="idHidden" value="" />
		<table border="0" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
			<tr>
				<td width="12%"><span style="font-family: '宋体'; font-size: 19px;">酒店名称:</span></td>
				<td width="38%"><select id="itemSelect" name="hotelId" style="width: 170px; height: 25px;font-family: '宋体'; font-size: 19px;">
						<option style="font-family: '宋体'; font-size: 19px;" value=""></option>
						<%
							for (int j = 0; j < hiList2.size(); j++) {
						%>
						<option style="font-family: '宋体'; font-size: 19px;" value="<%=hiList2.get(j).getHotelId()%>"><%=hiList2.get(j).getName()%></option>
						<%
							}
						%>
				</select></td>
				<td width="12%">房间出租率:</td>
				<td width="38%"><input type="text" name="occItem" onchange="aveIncome()" style="width: 167px; height: 20px" maxlength="4" />&nbsp;%</td>
			</tr>
			<tr>
				<td width="12%">酒店平均房价:</td>
				<td width="38%"><input type="text" name="adrItem" onchange="aveIncome()" style="width: 167px; height: 20px" maxlength="20" /></td>
				<td width="12%">平均收入:</td>
				<td width="38%"><input type="text" name="revPARItem" readonly="readonly" style="width: 167px; height: 20px" maxlength="11" /></td>
			</tr>
			<tr>
				<td width="12%">录入日期:</td>
				<td width="38%"><input type="text" name="loadTime" style="width: 167px; height: 20px" maxlength="10" /></td>
				<td width="12%">最新修改日期:</td>
				<td width="38%"><input type="text" name="updateTime" style="width: 167px; height: 20px" maxlength="10" /></td>
			</tr>
			<tr>
				<td width="12%">业务日期:</td>
				<td width="38%"><input name="busTime" class="laydate-icon-dahong" onclick="laydate()"/>
				&nbsp;&nbsp;-&nbsp;&nbsp;
				<input name="busTime1" class="laydate-icon-dahong" onclick="laydate()"/></td>
				<td width="12%">录入人:</td>
				<td width="38%"><input type="text" name="loader" style="width: 167px; height: 20px" maxlength="20" /></td>
			</tr>
			<tr>
				<td />
				<td />
				<td />
				<td />
			</tr>
		</table>
		<input id="selectItem" type="button" onclick="changeHidden(5)" value="查询酒店房价信息" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<input id="changeItem" type="submit" onclick="changeHidden(8)" value="修改酒店房价信息" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<input id="deleteItem" type="button" onclick="changeHidden(9)" value="删除酒店房价信息" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
        <input id="exportItem" type="submit" onclick="changeHidden(11)" <% if (itemList.size() == 0) { %>disabled="disabled" <%}%> value="导出酒店房价信息" />
	</form>
	<hr id="2" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
	<p align="center">
		<strong>查询信息显示</strong>
	</p>
	<%
		if (hiList3.size() != 0) {
	%>
	<table id="hotelinfoSelect" border="1" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
		<tr>
		</tr>
		<tr>
			<th width="10%">酒店ID</th>
			<th width="20%">酒店名称</th>
			<th width="10%">酒店房间数</th>
			<th width="10%">酒店联系人</th>
			<th width="15%">酒店联系方式</th>
			<th width="20%">备注</th>
			<th width="15%">操作</th>
		</tr>
		<%
			for (int i = 0; i < hiList3.size(); i++) {
		%>
		<tr>
			<td width="10%"><%=hiList3.get(i).getHotelId()%></td>
			<td width="20%"><%=hiList3.get(i).getName()%></td>
			<td width="10%"><%=hiList3.get(i).getRoomNumber()%></td>
			<td width="10%"><%=hiList3.get(i).getCallMan()%></td>
			<td width="15%"><%=hiList3.get(i).getTel()%></td>
			<td width="20%"><%=hiList3.get(i).getRemark()%></td>
			<td width="15%">&nbsp;&nbsp;<input id="change_<%=i%>" type="button" onclick="changeValue(this)" value="修改" /> &nbsp;&nbsp;
				<input id="delete_<%=i%>" type="submit" onclick="deleteValue(this)" value="删除" /></td>
		</tr>
		<%
			}
		%>
	</table>
	<br />
	<%
		}
		if (itemList.size() != 0) {
	%>
	<table id="iteminfoSelect" border="1" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
		<tr>
		</tr>
		<tr>
			<th>序号</th>
			<th>酒店名称</th>
			<th>房间出租率(%)</th>
			<th>酒店平均房价</th>
			<th>平均收入</th>
			<th>录入日期</th>
			<th>最新修改日期</th>
			<th>业务日期</th>
			<th>录入人</th>
			<th width="15%">操作</th>
		</tr>
		<%
			for (int i = 0; i < itemList.size(); i++) {
		%>
		<tr>
			<td><%=itemList.get(i).getId()%></td>
			<%
				for (int m = 0; m < hiList1.size(); m++) {
							if (hiList1.get(m).getHotelId() == itemList.get(i).getHotelId()) {
			%>
			<td><span><%=hiList1.get(m).getName()%></span><input type="hidden" value="<%=hiList1.get(m).getHotelId()%>" /></td>
			<%
				break;
							}
						}
			%>
			<td><%=itemList.get(i).getOccItem()%></td>
			<td><%=itemList.get(i).getAdrItem()%></td>
			<td><%=itemList.get(i).getRevPARItem()%></td>
			<td><%=itemList.get(i).getLoadTime()%></td>
			<td><% if (itemList.get(i).getUpdateTime() == null) {%><%} else { %><%=itemList.get(i).getUpdateTime()%><%} %></td>
			<td><%=itemList.get(i).getBusTime()%></td>
			<td><%=itemList.get(i).getLoader()%></td>
			<td width="15%">&nbsp;&nbsp;<input id="change_<%=i%>" type="button" onclick="changeItemValue(this)" value="修改" />
				&nbsp;&nbsp; <input id="delete_<%=i%>" type="submit" onclick="deleteItemValue(this)" value="删除" /></td>
		</tr>
		<%
			}
		%>
	</table>
	<br />
	<%
		}
	%>
	<hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
</body>
<script type="text/javascript">
	$(document).ready(function() {
		onselectType(0);
		$("#changeHotel").attr("disabled", true);
		$("#deleteHotel").attr("disabled", true);
		$("#changeItem").attr("disabled", true);
		$("#deleteItem").attr("disabled", true);
	});

	function onselectType(tag) {
		var selectType = "";
		$("#changeHotel").attr("disabled", true);
		$("#deleteHotel").attr("disabled", true);
		$("#changeItem").attr("disabled", true);
		$("#deleteItem").attr("disabled", true);
		if (tag == 1) {
			selectType = $("#selectType").val();
			$("#nameTitle").hide();
			$("#name").hide();
			$("#input[name=name]").val("");
		} else {
			selectType = <%=selectType%>;
	        $("#selectType").val(selectType);
			$("#nameTitle").hide();
			$("#name").hide();
			$("#input[name=name]").val("");
		}
		if (selectType == '0') {
			$("#1").hide();
			$("#itemId").hide();
			$("#iteminfoSelect").hide();
			$("#0").show();
			$("#hotelInfoId").show();
			$("#hotelinfoSelect").show();
		} else {
			$("#1").show();
			$("#itemId").show();
			$("#iteminfoSelect").show();
			$("#0").hide();
			$("#hotelInfoId").hide();
			$("#hotelinfoSelect").hide();
		}
	}

	function changeValue(obj) {
		var id = $(obj).parent().prev().prev().prev().prev().prev().prev().text();
		$("#hotelIdHidden").val(id);
		$("#infoSelect").children("option[value=" + id + "]").attr("selected", true);
		$("input[name=name]").val($(obj).parent().prev().prev().prev().prev().prev().text());
		$("input[name=roomNumber]").val($(obj).parent().prev().prev().prev().prev().text());
		$("input[name=callMan]").val($(obj).parent().prev().prev().prev().text());
		$("input[name=tel]").val($(obj).parent().prev().prev().text());
		$("input[name=remark]").val($(obj).parent().prev().text());
		$("#nameTitle").show();
		$("#name").show();
		$("#infoSelect").attr("disabled", true);
		$("#changeHotel").attr("disabled", false);
		$("#deleteHotel").attr("disabled", true);
	}

	function deleteValue(obj) {
		var id = $(obj).parent().prev().prev().prev().prev().prev().prev().text();
		$("#hotelIdHidden").val(id);
		$("#infoSelect").children("option[value=" + id + "]").attr("selected", true);
		$("input[name=name]").val($(obj).parent().prev().prev().prev().prev().prev().text());
		$("input[name=roomNumber]").val($(obj).parent().prev().prev().prev().prev().text());
		$("input[name=callMan]").val($(obj).parent().prev().prev().prev().text());
		$("input[name=tel]").val($(obj).parent().prev().prev().text());
		$("input[name=remark]").val($(obj).parent().prev().text());
		$("#nameTitle").show();
		$("#name").show();
		$("#infoSelect").attr("disabled", true);
		$("#changeHotel").attr("disabled", true);
		$("#deleteHotel").attr("disabled", false);
	}

	function changeItemValue(obj) {
		var id = $(obj).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().text();
		var hotelId = $(obj).parent().prev().prev().prev().prev().prev().prev().prev().prev().children("input").val();
		$("#idHidden").val(id);
		$("#itemSelect").children("option[value=" + hotelId + "]").attr("selected", true);
		$("input[name=occItem]").val($(obj).parent().prev().prev().prev().prev().prev().prev().prev().text());
		$("input[name=adrItem]").val($(obj).parent().prev().prev().prev().prev().prev().prev().text());
		$("input[name=revPARItem]").val($(obj).parent().prev().prev().prev().prev().prev().text());
		$("input[name=loadTime]").val($(obj).parent().prev().prev().prev().prev().text());
		$("input[name=busTime]").val($(obj).parent().prev().prev().text());
		$("input[name=busTime1]").val();
		$("input[name=loader]").val($(obj).parent().prev().text());
		$("input[name=loadTime]").attr("disabled", true);
		$("input[name=updateTime]").attr("readonly", true);
		$("#itemSelect").attr("disabled", true);
		$("#changeItem").attr("disabled", false);
		$("#deleteItem").attr("disabled", true);
	}

	function deleteItemValue(obj) {
		var id = $(obj).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().text();
		var hotelId = $(obj).parent().prev().prev().prev().prev().prev().prev().prev().prev().children("input").val();
		$("#idHidden").val(id);
		$("#itemSelect").children("option[value=" + hotelId + "]").attr("selected", true);
		$("input[name=occItem]").val($(obj).parent().prev().prev().prev().prev().prev().prev().prev().text());
		$("input[name=adrItem]").val($(obj).parent().prev().prev().prev().prev().prev().prev().text());
		$("input[name=revPARItem]").val($(obj).parent().prev().prev().prev().prev().prev().text());
		$("input[name=loadTime]").val($(obj).parent().prev().prev().prev().prev().text());
		$("input[name=updateTime]").val($(obj).parent().prev().prev().prev().text());
		$("input[name=busTime]").val($(obj).parent().prev().prev().text());
		$("input[name=loader]").val($(obj).parent().prev().text());
		$("input[name=loadTime]").attr("disabled", true);
		$("input[name=updateTime]").attr("readonly", false);
		$("#itemSelect").attr("disabled", true);
		$("#infoSelect").attr("disabled", true);
		$("#changeItem").attr("disabled", true);
		$("#deleteItem").attr("disabled", false);
	}

	function changeHidden(value) {
		$(this).parent().children($("input[name=hiddenValue]").val(value))
		if (value == 7) {
			if (confirm("确定删除这条酒店信息记录吗？")) {
				$("#hotelInfoId").submit();
			}
		} else if (value == 9) {
			if (confirm("确定删除这条酒店房价信息记录吗？")) {
				$("#itemId").submit();
			}
		} else if (value == 5) {
			if ($("input[name=busTime]").val() == "" && $("input[name=busTime1]").val() != "") {
				alert("查询时，请填写业务开始时间！！");
				return false;
			} else if ($("input[name=busTime]").val() != "" && $("input[name=busTime1]").val() != "" && $("input[name=busTime1]").val() < $("input[name=busTime]").val()) {
				alert("查询时，请填写正确的业务时间！！");
				return false;
			}else if ($("input[name=busTime1]").val() != "" && !($("input[name=busTime]").val().split("-")[0] == $("input[name=busTime1]").val().split("-")[0] 
			    && $("input[name=busTime]").val().split("-")[1] == $("input[name=busTime1]").val().split("-")[1])) {
				alert("只能查询相同年月的房价信息！！");
				return false;
			} else {
				$("#itemId").submit();
			}
		}
	}
	
	function aveIncome() {
        if ($("input[name=occItem]").val() != "" && $("input[name=adrItem]").val() != "") {
            var occItemValue = $("input[name=occItem]").val();
            var adrItemValue = $("input[name=adrItem]").val();
            $("input[name=revPARItem]").val(((occItemValue / 100) * (adrItemValue / 1)).toFixed(2));
        }
    }
</script>
</html>
