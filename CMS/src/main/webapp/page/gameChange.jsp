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

<link href="<%=request.getContextPath()%>/css/gameChange.css"
	rel="stylesheet">

</head>
<body>
	<form id="gameChange_form">
		<table class="table">

			<tr>
				<td><input type="hidden" name="gameId" id="gameId" value=${game.gameId}>
					赛程名称&nbsp;:&nbsp;
					<input type="text" name="gameName" id="gameName" value=${game.gameName}>
				</td>
				<td>赛程等级&nbsp;:&nbsp;
					<input type="text" name="gameLevel" id="gameLevel" value=${game.gameLevel} list="role_list" >
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
					<input type="text" name="gameJoinCount" id="gameJoinCount" value=${game.gameJoinCount}>
				</td>
				<td>
					晋级人数&nbsp;:&nbsp;
					<input type="text" name="gameUpCount" id="gameUpCount" value=${game.gameUpCount}>
				</td>
			</tr>
			<tr>
				<td>
					赛程日期&nbsp;:&nbsp;
					<input type="datetime-local" name="gameDate" id="gameDate" value=${gameDate}>
				</td>
				<td>
					赛程地址&nbsp;:&nbsp;
					<input type="text" name="gameAddr" id="gameAddr" value=${game.gameAddr}>
				</td>
			</tr>
			<tr>
				<td>
					结束日期&nbsp;:&nbsp;
					<input type="datetime-local" name="gameEndDate" id="gameEndDate" value=${gameEndDate}>
				</td>
				<td></td>
			</tr>
			
		</table>

	</form>
	<div id="bottom">
		<button class="submitcss" onclick="warm()">确定</button>
		<button class="buttoncss" onclick="clearInput()">清空</button>
	</div>
					
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/gameChange.js"></script>
</body>
</html>