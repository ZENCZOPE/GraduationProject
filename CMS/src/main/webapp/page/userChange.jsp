<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建用户</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/userChange.css"
	rel="stylesheet">

</head>
<body>
	<form id="userChange_form">
		<table class="table">
			<tr>
				<td>用户姓名:
					<input type="hidden" id="userId" value=${user.userId}>
					<input type="text" name="userName" required="required" id="userName" value=${user.userName}>
				</td>
				<td>用户电话&nbsp;&nbsp;:&nbsp;
					<input type="text" name="userPhone" required="required" id="userPhone" value=${user.userPhone}>
				</td>
			</tr>

			<tr>
				<td>用户年龄:
					<input type="text" name="userAge" required="required" id="userAge" value=${user.userAge} >
				</td>
				<td>用户邮件&nbsp;&nbsp;:&nbsp;
					<input type="text" name="userMail" required="required" id="userMail" value=${user.userMail}>
				</td>
			</tr>

			<tr>
				<td>用户地址:
					<input type="text" name="userAddr" required="required" id="userAddr" value=${user.userAddr}>
				</td>
				<td>用户身份证:
					<input type="text" name="userIdcard" required="required" id="userIdcard" value=${user.userIdcard}>
				</td>
			</tr>
			
		</table>

	</form>
	<table class="table">
		<tr style="height:108px;">
			<td>
				<input type="button" class="submitcss" onclick="warm()" value="确定">
			</td>
			<td>
				<input type="button" class="buttoncss" onclick="clearInput()" value="清空">
			</td>
		</tr>
	</table>
					
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/userChange.js"></script>
</body>
</html>