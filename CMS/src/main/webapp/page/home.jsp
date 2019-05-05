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

<link href="<%=request.getContextPath()%>/css/home.css" rel="stylesheet">

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
		<li class = "first_menu">
			<span class="js_secondeMenu">用户管理<i class="arrow"></i></span>
			<ul id="second_menu1" class="second_menu hide" > 
	        <li onclick="openAddUser()">新建用户</li>
	        <li onclick="changePassword()">修改密码</li>
	      	</ul>
	    </li>
	    <li onclick="openPlayerList()"><span>选手管理</span></li>
	    <li onclick="openJudgeList()"><span>评委管理</span></li>
		<li onclick="openWorkerList()"><span>员工管理</span></li>
		<li onclick="resetPassword()"><span>重置密码</span></li>
		<li onclick="openCertify()"><span>认证管理</span></li>
		<li onclick="uploadPage()"><span>资质上传</span></li>
		<li onclick="openAddGame()"><span>添加赛程</span></li>
		<li onclick="gameAllocation()"><span>分配赛程</span></li>
		<li onclick="openViewGame()"><span>查看赛程</span></li>
		<li onclick="openGameManage()"><span>赛程管理 </span></li>
		<li onclick="openMyGame()"><span>我的赛程</span> </li>
		<li onclick="addDevice()"><span>添加设备</span></li>
		<li onclick="deviceManage()"><span>设备管理</span></li>
		<li>广告</li>
		<li onclick="addAd()"><span>添加广告</span></li>
		<li onclick="adManage()"><span>广告管理</span></li>
		<li>财会</li>
		<li onclick="addAccount()"><span>添加账单</span></li>
		<li onclick="accountList()"><span>我的账单</span></li>
		<li onclick="finance()"><span>财务统计</span></li>
		<li>评分</li>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/home.js"></script>
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