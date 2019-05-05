function queryCertifyList(currPage) {
	var userName = $("#userName").val();
	var userAddr = $("#userAddr").val();
	var userRole = $("#userRole").val();
	if (userRole=='选手') {
		userRole = 'player';
	} else if (userRole=='裁判') {
		userRole = 'judge';
	} else if (userRole=='管理员') {
		userRole = 'admin';
	}
	var jsonStr = {
		"userName" : userName,
		"userAddr" : userAddr,
		"userRole" : userRole,
		"currPage" : currPage
	}
	realCommitLog("/CMS/user/ajaxGetCertifyList.do", jsonStr);
}

function realCommitLog (urls, string) {
	$.ajax({

		data : string,

		type : "POST",

		dataType : 'json',

		url : urls,

		success : function(data) {
			showData(data);
		},
		error : function(data) {
			alert(data.msg);
		}
	});
}

function showData(data) {
	var users = data.list;
	var content = "";
	// 表头
	content += "<table class='table'><tr><td>用户ID</td><td>用户姓名</td><td>用户年龄</td><td>用户邮件</td>"
		+ "<td>用户电话</td><td>用户地址</td><td>用户权限</td><td>用户状态</td><td>用户身份证</td><td>下载/认证</td></tr>";
	// 循环展示数据
	$.each(users, function(i, item) {
		item=userConvert(item);
		if(item.isFile == 1){
			content += "<tr><td>" + item.userId + "</td>" + "<td>" + item.userName + "</td>"
			+ "<td>" + item.userAge + "</td>"
			+ "<td>" + item.userMail + "</td>"
			+ "<td>" + item.userPhone + "</td>"
			+ "<td>" + item.userAddr + "</td>"
			+ "<td>" + item.userRole + "</td>" 
			+ "<td>" + item.userStatus + "</td>"
			+ "<td>" + item.userIdcard + "</td>"
			+ "<td><button style='width: 70px;' onclick='download(" + item.userId + ")'>资料下载</button>"
			+ "<button onclick='certify(" + item.userId + ")'>认证</button></td></tr>";
		}else{
			content += "<tr><td>" + item.userId + "</td>" + "<td>" + item.userName + "</td>"
			+ "<td>" + item.userAge + "</td>"
			+ "<td style='width:138px;'>" + item.userMail + "</td>"
			+ "<td>" + item.userPhone + "</td>"
			+ "<td style='width:100px;'>" + item.userAddr + "</td>"
			+ "<td>" + item.userRole + "</td>" 
			+ "<td>" + item.userStatus + "</td>"
			+ "<td>" + item.userIdcard + "</td>"
			+ "<td>未上传</td></tr>";
		}
	});
	// 表尾
	content += "</table>";
	content += '<div id="pagecontrol">';
	content += '<div class="pageinfo">';
	content += '共<span><b>' + data.totalpage + '</b></span>页，每页<span>'
			+ data.pagesize + '</span>条，当前是第<span><b>' + data.pagenum
			+ '</b></span>页';
	content += '</div>';

	content += '<div class="pagebar">';

	content += '<button onclick="queryCertifyList(1);"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>首页</button>';

	content += '<button onclick="queryCertifyList(' + (data.pagenum - 1) + ');"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>上页</button>';

	content += '<button onclick="queryCertifyList(' + (data.pagenum + 1) + ');"';
	if (data.pagenum == data.totalpage) {
		content += 'class="button"  disabled';
	} else if (data.pagenum < data.totalpage) {
		content += ' class="button";'
	}
	content += '>下页</button>';

	content += '<button onclick="queryCertifyList(' + data.totalpage + ');"';
	if (data.pagenum == data.totalpage) {
		content += 'class="button"  disabled';
	} else if (data.pagenum < data.totalpage) {
		content += ' class="button";'
	}
	content += '>末页</button>';

	content += '</div>';
	content += '</div>';

	// 展示数据
	$("#dataList").html(content);
}

function download(userId) {
	window.location.href = '/CMS/file/Download.do?userId=' + userId;
}

function certify(userId) {
	var String = { "userId": userId };
	$.ajax({
		
		data: String,
	
		type: "POST",
		
		dataType: 'json',
		
		url: "/CMS/user/ajaxCertify.do",
		
		success: function(data) {
			layer.msg(data.msg,{time:2000});
		},
		error: function() {
			layer.msg("操作异常，请联系管理员！",{time: 2000});
		}
	});
}

function userConvert(user) {
	if (user.userStatus=='1') {
		user.userStatus='已认证';
	} else {
		user.userStatus='未认证';
	}
	if (user.userRole=='admin') {
		user.userRole='管理员';
	} else if (user.userRole=='player') {
		user.userRole='选手';
	} else if (user.userRole=='judge') {
		user.userRole='裁判';
	} else if (user.userRole=='worker') {
		user.userRole='员工';
	}
	return user;
}