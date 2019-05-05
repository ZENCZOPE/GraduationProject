<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加设备</title>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link href="<%=request.getContextPath()%>/css/addDevice.css" rel="stylesheet">

</head>
<body>

<form id="addDevice_form">
	<table class="table">
		<tr>
			<td>设备序列号&nbsp;:&nbsp;
				<input type="text" name="iemi" required="required" id="iemi">
			</td>
			<td>
				设备名称&nbsp;:&nbsp;
				<input type="text" name="socImei" required="required" id="socImei" list="role_list">
				 <datalist id="role_list">
					<option value="1">
					<option value="2">
					<option value="3">
				</datalist>
			</td>
		</tr>
	</table>
		
</form>

<table class="table">
	<tr>
		<td>
			<button class="submitcss" onclick="warm()">确定</button>
		</td>
		<td>
			<button class="buttoncss" onclick="clearInput()">清空</button>
		</td>
	</tr>
</table>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/addDevice.js"></script>
	
</body>
</html>