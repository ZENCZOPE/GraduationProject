<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
	
<link href="<%=request.getContextPath()%>/css/myInfo.css" rel="stylesheet">
</head>
<body>
<div id="personal_info">
<table>
	<tr>
		<h1>个人信息</h1>
	</tr>
	<hr />
	<tr>
		<td>用户姓名：${user.userName}</td>
		<td>用户电话：${user.userPhone}</td>
		
	</tr>
	<tr>
		<td>用户地址：${user.userAddr}</td>
		<td>用户邮件：${user.userMail}</td>
	</tr>
	<tr>
		
		<td>用户年龄：${user.userAge}</td>
		<td>用户身份证：${user.userIdcard}</td>
	</tr>
</table>
</div>

</body>

</html>