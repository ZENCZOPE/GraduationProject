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

<link href="<%=request.getContextPath()%>/css/adChange.css"
	rel="stylesheet">

</head>
<body>
	<form id="adChange_form">
		<table class="table">

			<tr>
				<td><input type="hidden" name="adId" id="adId" value=${ad.adId}>
					广告标题&nbsp;:&nbsp;
					<input type="text" name="adTitle" id="adTitle" value=${ad.adTitle}>
				</td>
				<td>
					广告备注&nbsp;:&nbsp;
					<input type="text" name="adNote" id="adNote" value=${ad.adNote}>
				</td>
			</tr>
			<tr>
				<td>广告位置&nbsp;:&nbsp;
					<input type="text" name="adAddr" id="adAddr" value=${ad.adAddr}>
				</td>
				<td>
					广告价格&nbsp;:&nbsp;
					<input type="text" name="adPrice" id="adPrice" value=${ad.adPrice}>
				</td>
			</tr>
			<tr>
				<td>
					所属公司&nbsp;:&nbsp;
					<input type="text" name="adOwner" id="adOwner" value=${ad.adOwner}>
				</td>
				<td>
					生效时间&nbsp;:&nbsp;
					<input type="datetime-local" name="adEffectTime" id="adEffectTime" value=${adEffectTime}>
				</td>
			</tr>
			<tr>
				<td>
					结束时间&nbsp;:&nbsp;
					<input type="datetime-local" name="adEndTime" id="adEndTime" value=${adEndTime}>
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
		src="<%=request.getContextPath()%>/js/adChange.js"></script>
</body>
</html>