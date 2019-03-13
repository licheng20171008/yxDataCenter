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
<title>武汉亚洲大酒店</title>
</head>
<%
	String addType = request.getParameter("addType");
    List<Hotelinfo> hiList = (List<Hotelinfo>) request.getAttribute("hiList") == null?new ArrayList<Hotelinfo>():(List<Hotelinfo>) request.getAttribute("hiList");
%>
<body>
	<hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
	<span style="font-family: '宋体'; font-size: 19px;">请选择添加类型：</span>
	<select id="addType" name="addType" style="font-family: '宋体'; font-size: 19px;" onchange="onaddType(1)">
		<option style="width: 400px;font-family: '宋体'; font-size: 19px;" value="0">酒店基本信息添加</option>
		<option style="width: 400px;font-family: '宋体'; font-size: 19px;" value="1">酒店房价信息添加</option>
	</select>
	<hr id="0" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
	<form id="hotelInfoId" action="Init.action" method="post">
		<input type="hidden" name="hiddenValue" value="1" />
		<table border="0" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
			<tr>
				<td width="12%">酒店名称:</td>
				<td width="38%"><input type="text" name="name" style="width: 167px; height: 20px" maxlength="50" required="required" /></td>
				<td width="12%">酒店房间数:</td>
				<td width="38%"><input type="text" name="roomNumber" style="width: 167px; height: 20px" maxlength="4" required="required" /></td>
			</tr>
			<tr>
				<td width="12%">酒店联系人:</td>
				<td width="38%"><input type="text" name="callMan" style="width: 167px; height: 20px" maxlength="20" /></td>
				<td width="12%">酒店联系方式:</td>
				<td width="38%"><input type="tel" name="tel" style="width: 167px; height: 20px" maxlength="50" /></td>
			</tr>
			<tr>
				<td width="12%">备注:</td>
				<td width="38%"><input type="text" name="remark" style="width: 167px; height: 20px" maxlength="2000" /></td>
				<td />
				<td />
			</tr>
			<tr>
				<td />
				<td />
				<td />
				<td />
			</tr>
		</table>
		<input type="button" value="提交酒店基本信息" onclick="checkHotelInfo()" />
	</form>
	<hr id="1" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
	<form id="itemId" action="Init.action" method="post">
		<input type="hidden" name="hiddenValue" value="2" />
		<table border="0" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
			<tr>
				<td width="12%">酒店名称:</td>
				<td width="38%"><select name="hotelId" style="width: 170px; height: 28px;font-family: '宋体'; font-size: 19px;">
						<%
							for (int i = 0; i < hiList.size(); i++) {
						%>
						<option style="font-family: '宋体'; font-size: 19px;" value="<%=hiList.get(i).getHotelId()%>"><%=hiList.get(i).getName()%></option>
						<%
							}
						%>
				</select></td>
				<td width="12%">房间出租率:</td>
				<td width="38%"><input type="text" name="occItem" onchange="aveIncome()" style="width: 167px; height: 20px" maxlength="4" required="required" />&nbsp;%</td>
			</tr>
			<tr>
				<td width="12%">酒店平均房价:</td>
				<td width="38%"><input type="text" name="adrItem" onchange="aveIncome()" style="width: 167px; height: 20px" maxlength="20" required="required" /></td>
				<td width="12%">平均收入:</td>
				<td width="38%"><input type="tel" name="revPARItem" style="width: 167px; height: 20px" maxlength="11" required="required" /></td>
			</tr>
			<tr>
				<td width="12%">业务日期:</td>
				<td width="38%"><input name="busTime" class="laydate-icon-dahong" onclick="laydate()" required="required" /></td>
				<td width="12%">录入人:</td>
				<td width="38%"><input type="text" name="loader" style="width: 167px; height: 20px" maxlength="20" /></td>
			</tr>
			<tr>
				<td />
				<td />
			</tr>
		</table>
		<input type="button" value="提交酒店房价信息" onclick="checkItem()" />
	</form>
	<hr id="2" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
	<form id="returnForm" action="Init.action" method="post">
		<input id="returnHidden" type="hidden" name="hiddenValue" />
		<table id="infoSelect" border="1" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
			<tr>
				<td align="center" colspan="2">添加信息显示</td>
			</tr>
			<tr>
				<td width="80%">
					<%
						String message = request.getParameter("message");
						if (!"".equals(message) && message != null) {
							message = message.replace("[", " ").replace("]", " ").trim();
							String[] msgList = message.split(",");
					%><span id="message">
						<%
							for (int i = 0; i < msgList.length; i++) {
									String msg = msgList[i];
						%><%=msg%><br />
						<%
							}
						%>
				</span> <%
 	}
 %>
				</td>
				<td width="20%"><input type="button" onclick="returnIndex(3)" value="详细信息查询" /> <br/><br/><input type="button" onclick="returnIndex(100)" value="返回首页" /></td>
			</tr>
		</table>
	</form>
	<hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
</body>
<script type="text/javascript">
	$(document).ready(function() {
		onaddType(0);
	});

	function onaddType(tag) {
		var addType = "";
		if (tag == 1) {
			$("#message").text("");
			addType = $("#addType").val();
		} else {
			addType = <%=addType%>;
	        $("#addType").val(addType);
		}
		if (addType == '0') {
			$("#2").hide();
			$("#itemId").hide();
			$("#1").show();
			$("#hotelInfoId").show();
		} else {
			$("#2").show();
			$("#itemId").show();
			$("#1").hide();
			$("#hotelInfoId").hide();
		}
	}

	function checkHotelInfo() {
		if ($("input[name=name]").val() == ""
				&& $("input[name=roomNumber]").val() == ""
				&& $("input[name=callMan]").val() == ""
				&& $("input[name=tel]").val() == ""
				&& $("input[name=remark]").val() == "") {
			alert("请填入酒店信息！！");
			return false;
		} else if ($("input[name=name]").val() == "") {
			alert("请填入酒店名称！！");
			return false;
		} else if ($("input[name=roomNumber]").val() == "") {
			alert("请填入酒店房间数！！");
			return false;
		} else {
			$("#hotelInfoId").submit();
		}
	}

	function checkItem() {
		if ($("input[name=occItem]").val() == ""
				&& $("input[name=adrItem]").val() == ""
				&& $("input[name=revPARItem]").val() == ""
				&& $("input[name=busTime]").val() == ""
				&& $("input[name=loader]").val() == "") {
			alert("请填入房价信息！！");
			return false;
		} else if ($("input[name=occItem]").val() == "") {
			alert("请填入房间出租率！！");
			return false;
		} else if ($("input[name=adrItem]").val() == "") {
			alert("请填入酒店平均房价！！");
			return false;
		} else if ($("input[name=revPARItem]").val() == "") {
			alert("请填入平均收入！！");
			return false;
		} else if ($("input[name=busTime]").val() == "") {
			alert("请填入业务时间！！");
			return false;
		} else {
			$("#itemId").submit();
		}
	}

	function aveIncome() {
		if ($("input[name=occItem]").val() != "" && $("input[name=adrItem]").val() != "") {
			var occItemValue = $("input[name=occItem]").val();
			var adrItemValue = $("input[name=adrItem]").val();
			$("input[name=revPARItem]").val(((occItemValue / 100) * (adrItemValue / 1)).toFixed(2));
		}
	}
	
	function returnIndex(value) {
		$("#returnHidden").val(value);
		$("#returnForm").submit();
	}
</script>
</html>
