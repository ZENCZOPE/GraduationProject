<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分配赛程</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/gameAllocation.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/base.css">

</head>
<body onload="queryGameList(1)">
	<div class="pd-10 myinput" id="queryId">
		赛程名称：<input type="text" id="gameName" name="gameName" size="10">
		赛程等级：<input type="text" id="gameLevel" name="gameLevel" size="10">
		赛程地址：<input type="text" id="gameAddr" name="gameAddr" size="10">
		<button type="button" onclick="queryGameList(1)">查询</button>
	</div>
	<div id="dataList">

	</div>

</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/gameAllocation.js"></script>

</html>