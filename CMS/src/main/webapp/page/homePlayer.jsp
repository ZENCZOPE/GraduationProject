<%@ page language = "java" import = "java.util.*"  pageEncoding = "UTF-8" %>   
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/layer.js"></script>

<link href="<%=request.getContextPath()%>/css/homePlayer.css" rel="stylesheet">

</head>
<body>
<video autoplay muted loop src="../videos/1.mp4"></video>
<div id="head" class="header">
	<div id="head_left">
		综艺管理系统
	</div>
	<ul id="head_right">
		<li id="roleTest" class="icon-user"></li>
		<input type="hidden" id="role"/>
		<li style="cursor:pointer" onclick="Return()"><a>退出</a></li>	
	</ul>
</div>

<div id="left">
	<ul class="left_items">
		<div class="circle" onclick="changePassword()"><a onmouseout="move(this)">修改密码</a></div>
		<div class="circle" onclick="uploadPage()"><a onmouseout="move(this)">资质上传</a></div>
		<div class="circle" onclick="openMyGame()"><a onmouseout="move(this)">我的赛程</a></div>
	</ul>
</div>

<div id="right">
<div id="personal_imformation">
<div id="head_portrait"></div>
<table>
	<tr>
		<td><input id="upload" type="button" value="上传头像"></td>
		<td id="a">用户姓名：${loginUserMsg.userName}</td>
		<input type="hidden" id = "userId" value = ${loginUserMsg.userId}>
		<input type="hidden" id = "userName" value = ${loginUserMsg.userName}>
	</tr>
	<tr>
		<td>用户邮件：${loginUserMsg.userMail}</td>
		<td>用户电话：${loginUserMsg.userPhone}</td>
	</tr>
	<tr>
		<td>用户地址：${loginUserMsg.userAddr}</td>
		<input type="hidden" id = "userRole" value = ${loginUserMsg.userRole}>
		<td id="u_R"></td>
	</tr>
	<tr>
		<td>用户身份证：${loginUserMsg.userIdcard}</td>
		<td>用户年龄：${loginUserMsg.userAge}</td>
	</tr>
</table>
</div>
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/homePlayer.js"></script>
<script>
   $(function(){
        $(".js_secondeMenu").click(function(){
             $(this).next(".second_menu").toggleClass("hide");
             $(this).toggleClass("translate");
        });
        $(".left_items li").click(function(){
           $(this).addClass("on");
           $(this).siblings().removeClass("on");
        });
   })
</script>
</html>