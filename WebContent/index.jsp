<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
<title>武汉亚洲大酒店</title>
</head>
<body>
<hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
<form id="init" action="" method="post">
<input type="hidden" name="hiddenValue" value="0"/>
<input type="submit" onclick="submitForm(0)" value="欢迎进入酒店信息管理"/>
<br/>
<br/>
<input type="submit" onclick="submitForm(1)" value="欢迎进入酒店房价趋势报表"/>
<br/>
<br/>
<input type="submit" onclick="submitForm(2)" value="报表导出"/>
</form>
<hr style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3)" width="100%" color="#987cb9" size="3" />
</body>
<script type="text/javascript">
    function submitForm(value) {
    	if (value == '0') {
    		$("#init").attr("action", "Init.action");
    	} else if (value == '1') {
    		$("#init").attr("action", "Report.action");
    	} else if (value == '2') {
            $("#init").attr("action", "Export.action");
        }
    }
</script>
</html>