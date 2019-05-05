<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加广告</title>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link href="<%=request.getContextPath()%>/css/addAd.css" rel="stylesheet">

</head>
<body>

<form id="addAd_form">
	<table class="table">
		<tr>
			<td>广告标语&nbsp;:&nbsp;
				<input type="text" name="adTitle" id="adTitle">
			</td>
			<td>
				广告备注&nbsp;:&nbsp;
				<input type="text" name="adNote" id="adNote">
			</td>
		</tr>
		<tr>
			<td>广告位置&nbsp;:&nbsp;
				<input type="text" name="adAddr" id="adAddr">
			</td>
			<td>
				广告价格&nbsp;:&nbsp;
				<input type="text" name="adPrice" id="adPrice">
			</td>
		</tr>
		<tr>
			<td>所属公司&nbsp;:&nbsp;
				<input type="text" name="adOwner" id="adOwner">
			</td>
			<td>
				生效时间&nbsp;:&nbsp;
				<input type="datetime-local" name="adEffectTime" id="adEffectTime">
			</td>
		</tr>
		<tr>
			<td>结束时间&nbsp;:&nbsp;
				<input type="datetime-local" name="adEndTime" id="adEndTime">
			</td>
			<td>
			</td>
		</tr>
	</table>
		
</form>

<div id="bottom">
	<button class="submitcss" onclick="warm()">确定</button>
	<button class="buttoncss" onclick="clearInput()">清空</button>
</div>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/addAd.js"></script>
	
</body>
</html>