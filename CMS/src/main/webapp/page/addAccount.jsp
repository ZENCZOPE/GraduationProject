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

<link href="<%=request.getContextPath()%>/css/addAccount.css" rel="stylesheet">

</head>
<body>

<form id="addAccount_form">
	<table class="table">
		<tr>
			<td>账单类型&nbsp;:&nbsp;
				<input type="text" name="billType" id="billType">
			</td>
			<td>
				账单价格&nbsp;:&nbsp;
				<input type="text" name="price" id="price">
			</td>
		</tr>
		<tr>
			<td>账单备注&nbsp;:&nbsp;
				<input type="text" name="billNote" id="billNote">
			</td>
			<td>
				生效日期&nbsp;:&nbsp;
				<input type="datetime-local" name="effectTime" id="effectTime">
			</td>
		</tr>
	</table>
		
</form>

<div id="bottom">
		<button class="submitcss" onclick="warm()">确定</button>
		<button class="buttoncss" onclick="clearInput()">清空</button>
</div>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/addAccount.js"></script>
	
</body>
</html>