<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建用户</title>
<script 
	type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script 
	type="text/javascript" src="<%=request.getContextPath()%>/js/layer.js"></script>

<link 
	href="<%=request.getContextPath()%>/css/userAdd.css" rel="stylesheet">

</head>
<body>
	<form id="adduser_form">
		<table class="table">

			<tr>
				<td>用户姓名&nbsp;&nbsp;:&nbsp;
					<input type="text" name="userName" required="required" id="userName">
				</td>
				<td>用户权限&nbsp;&nbsp;:&nbsp;
					<select name="userRole" id="userRole">
					  <option value="worker">员工</option>
					  <option value="judge">评委</option>
					  <option value="player">选手</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>用户年龄&nbsp;&nbsp;:&nbsp;
					<input type="text" name="userAge" required="required" id="userAge">
				</td>
				<td>用户邮件&nbsp;&nbsp;:&nbsp;
					<input type="text" name="userMail" required="required" id="userMail">
				</td>
			</tr>
			<tr>
				<td>用户电话&nbsp;&nbsp;:&nbsp;
					<input type="text" name="userPhone" required="required" id="userPhone">
				</td>
				<td>用户地址&nbsp;&nbsp;:&nbsp;
					<input type="text" name="userAddr" required="required" id="userAddr">
				</td>
			</tr>
			<tr>
				<td>用户身份证:
					<input type="text" name="userIdcard" required="required" id="userIdcard">
				</td>
				<td></td>
			</tr>
			
		</table>

	</form>
	<div id="bottom">
		<table>
			<tr>
				<td>
					<button class="submitcss" onclick="warm()">确定</button>
				</td>
				<td>
					<button class="buttoncss" onclick="clearInput()">清空</button>
				</td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/userAdd.js"></script>

</body>

</html>