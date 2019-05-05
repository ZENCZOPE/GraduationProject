<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的比赛</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/gameOfMine.css">

</head>
<body onload="queryMyGame(1)">
	<div id="dataList">

	</div>

</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/gameOfMine.js"></script>

</html>