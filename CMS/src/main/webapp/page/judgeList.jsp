<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/judgeList.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/base.css">

</head>
<body onload="queryJudgeList(1)">
	<div class="pd-10 myinput" id="queryId">
		用户姓名：<input type="text" id="userName" name="userName" size="10">
		用户电话：<input type="text" id="userPhone" name="userPhone" size="10">
		用户状态：<select id="userStatus" name="userStatus">
					<option value="1">已认证</option>
					<option value="0">未认证</option>
				</select>
		<input type="hidden" id="currPage" name="currPage" value="1">
		<button type="button" onclick="queryJudgeList(1)">查询</button>
	</div>
	
	<div id="dataList">
	</div>

</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/judgeList.js"></script>

</html>