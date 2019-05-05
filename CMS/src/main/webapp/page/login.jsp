<%@ page language = "java" import = "java.util.*"  pageEncoding = "UTF-8" %>   
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/layer.js"></script>

        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/supersized.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css">
        <style>
        	.hide{
        		display: none;
        	}
        	.contparent {
        		width: 60%;
        		position: relative;
        		overflow: hidden;
        		margin: 0 auto;
        	}
        	.contmove{
        		width: 200%;
        		position: relative;
        	}
        	.half-cont{
        		width: 50%;
        		float: left;
        	}
        	.contparent form{
        		width: 50%;
        	}
        	.cf:before,
			.cf:after {
			    content: "";
			    display: table;
			    clear: both;
			}
			.underline-a{
				padding: 10px;
				color: #ff730e;
				cursor: pointer;
				text-decoration: underline;
			}
			.register-cont .register-cont{
				width: 100%;
			}
			.register-cont .register-cont input{
				width: 30%;
				float: left;
				margin: 20px 8%;
			}
        </style>
</head>
<body onkeydown="keyLogin()">
<div class="page-container">
	<font size="7">综艺管理系统</font>
	<br /><br />

	<div class="contparent">
		<div class="contmove cf">
			<div class="login-cont half-cont">
				<form action="/CMS/login/login.do" id="user_form" class="form-group" method="post">
					<input type="text" name="userIdcard" id="userIdcard" class="userIdcard" placeholder="身份证号码">
					<input type="password" name="password" id="password" class="password" placeholder="密码">
					<button type="button" style="display: block;margin: 0 auto;margin-top: 25px;" onclick="login()">登录</button>
					<p style="color: white;margin-top:25px ;">没有账号？<a onclick="turnRight()" class="underline-a">注册</a></p>
					<div class="error">
						<span>+</span>
					</div>
				</form>
			</div>
			<div class="register-cont half-cont" >
				<form class="register-cont cf" id="register">
					<input type="text" name="userName" id="userName" placeholder="请输入您的姓名">
					<input type="text" name="userAge" id="userAge" placeholder="请输入您的年龄">
					<input type="text" name="userMail" id="userMail" placeholder="请输入您的邮箱">
					<input type="text" name="userPhone" id="userPhone" placeholder="请输入您的电话">
					<input type="text" name="userAddr" id="userAddr" placeholder="请输入您的住址">
					<input type="text" name="userCard" id="userCard" placeholder="请输入您的身份证号码">
					<input type="password" name="newPassword" id="newPassword" placeholder="请输入您的密码">
					<input type="password" name="againPassword" id="againPassword" placeholder="请再次输入密码">
					<div class="error">
						<span>+</span>
					</div>
				</form>
				<button style="width: 200px;" type="button" onclick="warm()">注册</button>
				<button style="width: 200px;" type="button" onclick="back()">返回登录</button>
			</div>
		</div>
	</div>
	
</div>

        <script src="<%=request.getContextPath()%>/js/supersized.3.2.7.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/supersized-init.js"></script>
        <script src="<%=request.getContextPath()%>/js/scripts.js"></script>
        
</body>
  
<script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>

</html>