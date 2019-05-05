<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建赛程</title>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>
	
<link href="<%=request.getContextPath()%>/css/addGame.css" rel="stylesheet">

</head>
<body>

<form id="addgame_form">
	<table class="table">
		<tr>
			<td>赛程名称&nbsp;:&nbsp;
				<input type="text" name="gameName" required="required" id="gameName">
			</td>
			<td>
				赛程等级&nbsp;:
				<input type="text" name="gameLevel" required="required" id="gameLevel" list="role_list">
				 <datalist id="role_list">
					<option value="1">
					<option value="2">
					<option value="3">
				</datalist>
			</td>
		</tr>
		<tr>
			<td>
				参加人数&nbsp;:&nbsp;
				<input type="text" name="gameJoinCount" required="required" id="gameJoinCount">
			</td>
			<td>
				晋级人数&nbsp;:&nbsp;
				<input type="text" name="gameUpCount" required="required" id="gameUpCount">
			</td>
		</tr>
		<tr>
			<td>
				赛程日期&nbsp;:&nbsp;
				<input type="datetime-local" name="gameDate" required="required" id="gameDate">
			</td>
			<td>
				赛程地址&nbsp;:&nbsp;
				<input type="text" name="gameAddr" required="required" id="gameAddr">
			</td>
		</tr>
		<tr>
			<td>
				结束日期&nbsp;:&nbsp;
				<input type="datetime-local" name="gameEndDate" required="required" id="gameEndDate">
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
	src="<%=request.getContextPath()%>/js/addGame.js"></script>
	
</body>
</html>