<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广告管理</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/adQuery.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/base.css">

</head>
<body onload="queryAdList(1)">
	<div class="pd-10 myinput" id="queryId">
		广告标题：<input type="text" id="adTitle" name="adTitle" size="10">
		广告备注：<input type="text" id="adNote" name="adNote" size="10">
		所属公司：<input type="text" id="adOwner" name="adOwner" size="10">
		<button type="button" onclick="queryAdList(1)">查询</button>
	</div>
	<div id="dataList">

	</div>

</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/adQuery.js"></script>

</html>