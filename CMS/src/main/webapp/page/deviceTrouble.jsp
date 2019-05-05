<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>停用报损</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/deviceTrouble.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/base.css">

</head>
<body onload="queryDeviceList(1)">
	<div class="pd-10 myinput" id="queryId">
		设备序列号：<input type="text" id="iemi" name="iemi">
		设备名称：<input type="text" id="socImei" name="socImei">
		<input type="hidden" id="currPage" name="currPage" value="1">
		<button type="button" onclick="queryDeviceList(1)">查询</button>
	</div>
	
	<div id="dataList">
	</div>

</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/deviceTrouble.js"></script>

</html>