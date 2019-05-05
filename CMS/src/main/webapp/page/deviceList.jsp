<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备管理</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/deviceList.css">

</head>
<body onload="queryDeviceList(1)">
	<div id = "queryId">
		设备序列号：<input type="text" id="iemi" name="iemi" size="10">
		设备名称：<input type="text" id="socImei" name="socImei" size="10">
		设备状态：<input type="text" id="status" name="status" size="10" list="data">
		<!-- <datalist id = "data">
			<option>未损坏</option>
			<option>已损坏</option>
		</datalist> -->
		<button type="button" onclick="queryDeviceList(1)">查询</button>
	</div>
	
	<div id="dataList">
	</div>

</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/deviceList.js"></script>

</html>