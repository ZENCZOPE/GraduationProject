<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资质上传</title>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layer.js"></script>

<link href="<%=request.getContextPath()%>/css/uploadWorker.css" rel="stylesheet">

</head>
<body>
	<div id="circle">
		<input onchange="getFileURL(this)" type="file" id="file" name="myfile" />
		<div id="path"></div>
		<button onclick="selectFile()">上传文件</button>
		<button onclick="upload()">上传</button>
	</div>
	<div id="progress">
		<progress id="progressBar" value="0" max="100"></progress>
			<span id="percentage" ></span>
	</div>
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/uploadWorker.js"></script>
	
</body>
</html>