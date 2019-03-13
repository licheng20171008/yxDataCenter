<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/laydate/laydate.js"></script>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.yx.dao.Roomtype"%>
<%@ page import="com.yx.dao.Pdroom"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.yx.DBConnect.DataOpration"%>
<title>武汉亚洲大酒店</title>
</head>
<%
    String message = request.getParameter("message");
    String addType = request.getParameter("addType");
    String pdroomdate = (String)request.getAttribute("pdroomdate");
    Map<Integer, String[]> pdWeekMap = (Map<Integer, String[]>) request.getAttribute("pdWeekMap") == null?new HashMap<Integer, String[]>():(Map<Integer, String[]>) request.getAttribute("pdWeekMap");
    List<Roomtype> rtList = (List<Roomtype>) request.getAttribute("roomtype") == null?new ArrayList<Roomtype>():(List<Roomtype>) request.getAttribute("roomtype");
    List<Pdroom> pdList = (List<Pdroom>) request.getAttribute("pdList") == null?new ArrayList<Pdroom>():(List<Pdroom>) request.getAttribute("pdList");
    int index = pdList.size();
    int rtIndex = rtList.size();
    boolean pdWeekFlag = pdWeekMap.containsKey(1000000);
    Iterator<Integer> pdIt = pdWeekMap.keySet().iterator();
    DataOpration dop = new DataOpration();
%>
<body>
    <input type="hidden" id="index" name="index" value="<%=index %>" />
    <hr id="messageHR" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
    <span id="messageContext" style="color:red"><strong>消息：<%=message %></strong></span>
    <hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
    <form action="Report.action" method="post">
    &nbsp;&nbsp;&nbsp;<span style="font-family: '宋体'; font-size: 19px;">请选择添加类型：</span>
    <select id="addType" name="addType" style="font-family: '宋体'; font-size: 19px;" onchange="onaddType(2)">
        <option style="width: 400px;font-family: '宋体'; font-size: 18px;" value="0">酒店房型信息操作</option>
        <option style="width: 400px;font-family: '宋体'; font-size: 18px;" value="1">酒店每日房型房价</option>
    </select>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" value="返回首页" />
    </form>
    <hr id="addroomtypeHR" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
    <form id="addroomtype_form" action="Report.action" method="post">
        <input type="hidden" name="hiddenValue" value="1" />
        <table border="0" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 20px;">
            <tr>
                <td width="18%">酒店房型名称填写：</td>
                <td width="32%">
                    <input id="roomType" type="text" name="roomType" style="width: 300px;height: 20px;" maxlength="100" required="required" />
                </td>
                <td width="50%" align="right">
                    <input type="submit" value="添加酒店房型" />
                </td>
            </tr>
        </table>
    </form>
    <hr id="changeroomtypeHR" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
    <form id="changeroomtype_form" action="Report.action" method="post">
        <input type="hidden" name="hiddenValue" value="2" />
        <table border="0" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 20px;">
            <tr>
                <td width="20%">修改前酒店房型名称：</td>
                <td width="30%">
                    <select id="preroomtype" name="preroomtype" style="font-family: '宋体'; font-size: 20px;width:300px;" onchange="changeroomtype(this)">
                        <option style="font-family: '宋体'; font-size: 20px;" value=""></option>
                        <% 
                            for (Roomtype rt : rtList) {
                        %> 
                            <option value="<%=rt.getId() %>" style="font-family: '宋体'; font-size: 20px;"><%=rt.getRoomType() %></option>
                        <%}%>
                    </select>
                </td>
                <td width="20%">修改后酒店房型名称：</td>
                <td width="30%">
                    <input id="laterroomTypeID" type="hidden" name="laterroomTypeID" value=""/>
                    <input id="laterroomType" type="text" name="laterroomType" style="width: 300px; height: 20px" maxlength="100" required="required" />
                </td>
                <td>
                    <input type="submit" value="修改酒店房型名称" />
                </td>
            </tr>
        </table>
    </form>
    <hr id="pdroomtypeHR" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
    <form id="pdroomtype_form" action="Report.action" method="post">
        <input id="pdroomtypeHidden" type="hidden" name="hiddenValue" value="3" />
        <div>
            酒店房型日期选择：<input id="dateIcon" name="pdroomdate" class="laydate-icon-dahong" onclick="laydate()" onfocus="changeDate(2)" value="<%=pdroomdate %>" required="required" />
        </div>
        <div align="right">
            <input id="selectItem" type="submit" value="搜索酒店每日房型" onclick="selectroomtype()"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="addItem" type="button" value="添加酒店每日房型" onclick="addroomtype()"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="deleteItem" type="button" value="删除酒店每日房型" onclick="deleteroomtype()"/>
        </div>
        <br/>
        <table id="roomType_id" border="1" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 20px;">
            <tr align="center">
                <td width="15%">选项</td>
                <td width="45%">房型</td>
                <td width="40%">房型价格</td>
            </tr>
            <%
                for (int i = 0; i < pdList.size(); i++) {
            %>
            <tr>
                <td align="center">
                    <input id="box_<%=i %>" type="checkbox" value="" onclick="checkon(this)"/>
                </td>
                <td>
                    <input id="id_<%=i %>" name="id_<%=i %>" type="hidden" value="<%=pdList.get(i).getId() %>" />
                    <input id="pddateroomid_<%=i %>" name="pddateroomid_<%=i %>" type="hidden" value="<%=pdList.get(i).getPddateroomid() %>" />
                    <span id="pddateroomtype_<%=i %>"><%=pdList.get(i).getPddateroomtype() %></span>
                </td>
                <td>
                    <input id="pdroomprice_<%=i %>" name="pdroomprice_<%=i %>" type="text" style="border:0px;width:100%;height:100%;" value="<%=pdList.get(i).getPdroomprice() %>" required="required" />
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <br/>
        <div><input id="saveButton" type="submit" value="保存酒店每日房型" /></div>
    </form>
    <hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
    <%
        if (rtIndex > 0) {
    %>
    <table id="rtTable" border="1" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
        <tr>
        </tr>
        <tr>
            <th width="35%">房型编号</th>
            <th width="65%">房型名称</th>
        </tr>
        <%
            for (int i = 0; i < rtList.size(); i++) {
        %>
        <tr>
            <td width="35%"><%=rtList.get(i).getId()%></td>
            <td width="65%"><%=rtList.get(i).getRoomType()%></td>
        </tr>
        <%
            }
        %>
    </table>
    <br />
    <%
        }
    if (pdWeekFlag) {
        %>
        <table id="pdTable" border="1" cellpadding="10" width="100%" cellspacing="0" style="font-family: '宋体'; font-size: 19px;">
        <tr align="center">
            <td rowspan="2" align="center" style="width:200px;"><strong>房型</strong></td>
            <%
                String[] dateArray = pdWeekMap.get(1000000);
                for (int i = 0; i < dateArray.length; i++) {
                    %>
                    <th><%=dateArray[i] %></th>
                    <%
                }
            %>
        </tr>
        <tr align="center">
            <%
                String[] weekArray = pdWeekMap.get(1000001);
                for (int j = 0; j < weekArray.length; j++) {
                    %>
                    <th><%=weekArray[j] %></th>
                    <%
                }
            %>
        </tr>
        <%
            while(pdIt.hasNext()){
                int pddateroomid = pdIt.next();
                if (pddateroomid != 1000000 && pddateroomid != 1000001) {
                    String[] priceArray = pdWeekMap.get(pddateroomid);
                    String rtType = dop.rtType(pddateroomid);
                    %>
                    <tr>
                        <td><%=rtType %></td>
                        <%
                            for (int k = 0; k < priceArray.length; k++) {
                                String price = priceArray[k] == null?"":priceArray[k];
                            	%><td><%=price %></td><%
                            }
                        %>
                    </tr>
                    <%
                }
            }
        %>
        </table>
        <%
    }
    %>
    <hr id="lastLine" style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
</body>
<script type="text/javascript">
    $(document).ready(function() {
        msgOpt();
        checkboxChange();
        onaddType(1);
        changeDate(1);
    });
    
    function addroomtype(){
        var index = $("#index").val();
        var html = $("#roomType_id").children().html();
        html += "<tr>";
        html += "<td align='center'><input id='box_" + index + "' type='checkbox' value='' onclick='checkon(this)'/></td>";
        html += "<td>";
        html += "<input id='id_" + index + "' name='id_" + index + "' type='hidden' value='' />";
        html += "<select id='pddateroomid_" + index + "' name='pddateroomid_" + index + "' style='border:100px;width:100%;height:100%;'>";
        <% 
        for (Roomtype rt : rtList) {
        %> 
        html += "<option value='" + <%=rt.getId() %> + "' style='height:100%;font-family: '宋体'; font-size: 20px;'>" + '<%=rt.getRoomType() %>' + "</option>";
        <%}%>
        html += "</select>";
        html += "</td>";
        html += "<td>";
        html += "<input id='pdroomprice_" + index + "' name='pdroomprice_" + index + "' type='text' style='border:0px;width:100%;height:100%;' required='required' />";
        html += "</td>";
        html += "</tr>";
        $("#roomType_id").children().html(html);
        index++;
        $("#index").val(index);
        $("#saveButton").attr("disabled", false);
    }
    
    function deleteroomtype(){ 
        $("input").filter("[id^=box_]").each(function(){
            if ($(this).val() == "1") {
                $(this).parent().parent().remove();
            }
        });
        $("#saveButton").attr("disabled", false);
    }
    
    function checkon(obj) {
        if ($(obj).val() == "") {
            $(obj).val("1");
            $(obj).attr("checked", true);
        } else {
            $(obj).val("");
            $(obj).attr("checked", false);
        }
    }
    
    function checkboxChange() {
        $("input").filter("[id^=box_]").each(function(){
            if ($(this).val() == "") {
                $(this).attr("checked",false);
            } else {
                $(this).attr("checked",true);
            }
        });
    }
    
    function msgOpt() {
        var message = '<%=message%>';
        if (message == "") {
            $("#messageHR").hide();
            $("#messageContext").hide();
        } else {
            $("#messageHR").show();
            $("#messageContext").show();
        }
    }
    
    function onaddType(value) {
        var rtIndex = '<%=rtIndex%>';
        var pdWeekFlag = '<%=pdWeekFlag%>';
        if (value == "2") {
        	var addType = $("#addType").val();
        } else {
            var addType = '<%=addType%>';
        }
        
        $("#addType").children().each(function() {
            if($(this).val() == addType) {
                $(this).attr("selected", true);
            }
        });
        
        if (addType == "0") {
            $("#addroomtypeHR").show();
            $("#addroomtype_form").show();
            $("#changeroomtypeHR").show();
            $("#changeroomtype_form").show();
            $("#rtTable").show();
            $("#pdTable").hide();
            $("#pdroomtypeHR").hide();
            $("#pdroomtype_form").hide();
            if (value == "2") {
                $("#messageHR").hide();
                $("#messageContext").hide();
            }
            
            if (rtIndex > 0) {
                $("#lastLine").show();
            } else {
                $("#lastLine").hide();
            }
        } else if (addType == "1") {
            $("#addroomtypeHR").hide();
            $("#addroomtype_form").hide();
            $("#changeroomtypeHR").hide();
            $("#changeroomtype_form").hide();
            $("#rtTable").hide();
            $("#pdTable").show();
            $("#pdroomtypeHR").show();
            $("#pdroomtype_form").show();
            if (value == "2") {
                $("#messageHR").hide();
                $("#messageContext").hide();
            }
            
            if (pdWeekFlag == "true") {
                $("#lastLine").show();
            } else {
                $("#lastLine").hide();
            }
        }
    }
    
    function changeroomtype(obj){
        $("#laterroomTypeID").val($(obj).val());
        $(obj).children().each(function(){
            if ($(this).val() == $(obj).val()) {
                $("#laterroomType").val($(this).html());
            }
        });
    }
    
    function changeDate(value) {
        var date = $("#dateIcon").val();
        if (date == "") {
            $("#addItem").attr("disabled", true);
            $("#deleteItem").attr("disabled", true);
        } else {
            $("#addItem").attr("disabled", false);
            $("#deleteItem").attr("disabled", false);
        }
        if (value == "2") {
            $("#saveButton").attr("disabled", true);
        }
    }
    
    function selectroomtype() {
        $("#pdroomtypeHidden").val("4");
        $("#saveButton").attr("disabled", false);
    }
</script>
</html>
