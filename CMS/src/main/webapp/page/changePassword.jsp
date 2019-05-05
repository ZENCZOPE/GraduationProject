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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/md5.js"></script>

<link href="<%=request.getContextPath()%>/css/changePassword.css" rel="stylesheet">

</head>
<body>
<div id="all">
	<div id="right">
		<form class="pd-10 myinput" id="password_form">
			<input type="hidden" id="userIdcard" name="userIdcard" value=${userIdcard}>
			旧密码:<br>
			<input type="password" id="oldPassword" name="oldPassword" placeholder="若有字母，请注意大小写">
			<br>新密码:<br>
			<input type="password" id="newPassword" name="newPassword" placeholder="若有字母，请注意大小写">
			<br>再次输入新密码:<br>
			<input type="password" id="repeatPassword" name="repeatPassword" placeholder="若有字母，请注意大小写">
		</form>
		<button onclick="changePassword()">确定</button>
	</div>
</div>
</body>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/changePassword.js"></script>

</html>