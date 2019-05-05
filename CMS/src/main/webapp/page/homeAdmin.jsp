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

<link href="<%=request.getContextPath()%>/css/homeAdmin.css" rel="stylesheet">
</head>
<body>
<div id="head">
	<div id="head_left">
		综艺管理系统
	</div>
	<ul id="head_right">
		<li id="roleTest" class="icon-user"></li>
		<input type="hidden" id="role"/>
		<li id="quit" onclick="quit()"><a>退出</a></li>
	</ul>
</div>

<div id="left">
	<ul class="left_items">
		<li>
			<a onclick="spread(this)">用户管理<i class="arrow"></i></a>
			<ul id="second_menu1" class="second_menu" style="display: none;">
		        <li onclick="userAdd()"><a>新建用户</a></li>
		        <li onclick="workerList()"><a>员工管理</a></li>
		        <li onclick="judgeList()"><a>评委管理</a></li>
		        <li onclick="playerList()"><a>选手管理</a></li>
		        <li onclick="changePassword()"><a>密码修改</a></li>
		        <li onclick="resetPassword()"><a>重置密码</a></li>
	      	</ul>
	    </li>
		<li>
			<a onclick="spread(this)">认证管理<i class="arrow"></i></a>
			<ul id="second_menu1" class="second_menu" style="display: none;">
				<li onclick="openCertify()"><a>下载认证</a></li>
			</ul>
		</li>
		<li>
			<a onclick="spread(this)">设备管理<i class="arrow"></i></a>
			<ul id="second_menu1" class="second_menu" style="display: none;">
				<li onclick="addDevice()"><a>新增设备</a></li>
				<li onclick="deviceUse()"><a>分配设备</a></li>
				<li onclick="deviceTrouble()"><a>停用报损</a></li>
				<li onclick="deviceQuery()"><a>查询设备</a></li>
			</ul>
		</li>
		<li>
			<a onclick="spread(this)">赛程管理<i class="arrow"></i></a>
			<ul id="second_menu1" class="second_menu" style="display: none;">
				<li onclick="addGame()"><a>新增赛程</a></li>
				<li onclick="gameAllocation()"><a>分配赛程</a></li>
				<li onclick="gameManage()"><a>修改删除</a></li>
				<li onclick="gameQuery()"><a>查看赛程</a></li>
			</ul>
		</li>
		<li>
			<a onclick="spread(this)">广告管理<i class="arrow"></i></a>
			<ul id="second_menu1" class="second_menu" style="display: none;">
				<li onclick="addAd()"><a>新增广告</a></li>
				<li onclick="adManage()"><a>修改停用</a></li>
				<li onclick="adQuery()"><a>查询广告</a></li>
			</ul>
		</li>
		<li>
			<a onclick="spread(this)">财会管理<i class="arrow"></i></a>
			<ul id="second_menu1" class="second_menu" style="display: none;">
				<li onclick="addAccount()"><a>新增账单</a></li>
				<li onclick="accountList()"><a>修改作废</a></li>
				<li onclick="finance()"><a>财务统计</a></li>
			</ul>
		</li>
	</ul>
</div>

<div id="right">
	<iframe id="myiframe" frameborder="1" scrolling="no" src="/CMS/login/getMyInfoPage.do?userIdcard=${loginUserMsg.userIdcard}"></iframe>
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/homeAdmin.js"></script>
</html>