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
	href="<%=request.getContextPath()%>/css/deviceToWorker.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/base.css">

</head>
<body onload="queryWorkerList(1)">
	<input type="hidden" id="iemi" value=${iemi}>
	<div class="pd-10 myinput" id="queryId">
		用户姓名：<input type="text" id="userName" name="userName" size="10" required="required">
		用户地址：<input type="text" id="userAddr" name="userAddr" size="10">
		用户状态：<input type="text" id="userStatus" name="userStatus" size="10" list="status_id">
				<datalist id="status_id">
					<option>已认证</option>
					<option>未认证</option>
				</datalist>
		<button type="button" onclick="queryWorkerList(1)">查询</button>
	</div>
	
	<div id="dataList">
	</div>

</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/deviceToWorker.js"></script>

</html>