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
<link href="<%=request.getContextPath()%>/css/accChange.css"
	rel="stylesheet">

</head>
<body>
	<form id="gameChange_form">
		<table class="table">

			<tr>
				<td><input type="hidden" name="billId" id="billId" value=${account.billId}>
					账单类型&nbsp;:&nbsp;
					<input type="text" name="billType" id="billType" value=${account.billType}>
				</td>
				<td>账单价格&nbsp;:&nbsp;
					<input type="text" name="price" id="price" value=${account.price}>
				</td>
			</tr>
			<tr>
				<td>
					账单备注&nbsp;:&nbsp;
					<input type="text" name="billNote" id="billNote" value=${account.billNote}>
				</td>
				<td>
					负责人&nbsp;:&nbsp;
					<input type="text" name="opId" id="opId" value=${account.opId}>
				</td>
			</tr>
			<tr>
				<td>
					生效日期&nbsp;:&nbsp;
					<input type="datetime-local" name="effectTime" id="effectTime" value=${effectTime}>
				</td>
				<td>
					账单状态&nbsp;:&nbsp;
					<input type="text" name="status" id="status" value=${account.status}>
				</td>
			</tr>
			
		</table>

	</form>
	<table class="table">
		<tr style="height:110px;">
			<td>
				<input type="button" class="submitcss" onclick="warm()" value="确定">
			</td>
			<td>
				<input type="button" class="buttoncss" onclick="clearInput()" value="清空">
			</td>
		</tr>
	</table>
					
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/accChange.js"></script>
</body>
</html>