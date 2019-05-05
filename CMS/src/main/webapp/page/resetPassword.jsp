<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/layer.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/resetPassword.css">

</head>
<body onload="queryWorkerList(1)">
	<div class="pd-10 myinput" id = "queryId">
		用户姓名：<input type="text" id="userName" name="userName" size="10">
		用户电话：<input type="text" id="userPhone" name="userPhone" size="10">
		用户状态：<select id="userRole" name="userRole">
				  <option value="worker">员工</option>
				  <option value="judge">评委</option>
				  <option value="player">选手</option>
				</select>
		<input type="hidden" id="currPage" name="currPage" value="1">
		<button type="button" onclick="queryWorkerList(1)">查询</button>
	</div>
	
	<div id="dataList">
	</div>

</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/resetPassword.js"></script>

</html>