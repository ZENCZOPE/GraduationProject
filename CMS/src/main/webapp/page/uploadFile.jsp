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

<link href="<%=request.getContextPath()%>/css/uploadFile.css" rel="stylesheet">

</head>
<body>
	<div id="right"><a id="right" onclick="upload()" onmouseout="move(this)">上传</a></div>
	<div id="circle">
		<a onclick="selectFile()" onmouseout="move(this)">上传文件
			<input onchange="getFileURL(this)" type="file" id="file" name="myfile" />
		</a>
		<div id="path"></div>
	</div>
	<div id="progress">
		<progress id="progressBar" value="0" max="100"></progress>
			<span id="percentage" ></span>
	</div>
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/uploadFile.js"></script>
	
</body>
</html>