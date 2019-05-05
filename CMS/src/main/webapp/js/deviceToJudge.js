function queryWorkerList(currPage) {
	var userName = $("#userName").val();
	var userAddr = $("#userAddr").val();
	var userStatus = $("#userStatus").val();
	if(userStatus=='未认证') {
		userStatus = 0;
	}else if(userStatus=='已认证') {
		userStatus = 1;
	}
	var jsonStr = {
		"userName" : userName,
		"userAddr" : userAddr,
		"userStatus" : userStatus,
		"currPage" : currPage
	}
	realCommitLog("/CMS/device/ajaxDeviceToJudge.do", jsonStr);
}

function realCommitLog(urls, string) {
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
	})
}

function showData(data) {
	var iemi = $('#iemi').val();
	var users = data.list;
	var content = "";
	// 表头
	content += "<table class='table'><tr>"
		+ "<td>用户ID</td><td>用户姓名</td><td>用户年龄</td><td style='width:140px;'>用户邮件</td>"
		+ "<td style='width:100px;'>用户电话</td><td style='width:80px;'>用户地址</td>"
		+ "<td>用户权限</td><td>用户状态</td><td style='width:140px;'>用户身份证</td><td>分配</td></tr>";
	// 循环展示数据
	$.each(users, function(i, item) {
		item=userConvert(item);
		content += "<tr><td>" + item.userId + "</td>"
		+ "<td>" + item.userName+ "</td>"
		+ "<td>" + item.userAge + "</td>"
		+ "<td>" + item.userMail + "</td>"
		+ "<td>" + item.userPhone + "</td>"
		+ "<td>" + item.userAddr + "</td>"
		+ "<td>" + item.userRole + "</td>"
		+ "<td>" + item.userStatus + "</td>"
		+ "<td>" + item.userIdcard + "</td>"
		+ "<td><button onclick='deviceDistribute(" + item.userId + ")'>分配</button></td></tr>";
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

	content += '<button onclick="queryWorkerList(1);"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>首页</button>';

	content += '<button onclick="queryWorkerList(' + (data.pagenum - 1) + ');"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>上页</button>';

	content += '<button onclick="queryWorkerList(' + (data.pagenum + 1) + ');"';
	if (data.pagenum == data.totalpage) {
		content += 'class="button"  disabled';
	} else if (data.pagenum < data.totalpage) {
		content += ' class="button";'
	}
	content += '>下页</button>';

	content += '<button onclick="queryWorkerList(' + data.totalpage + ');"';
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

function deviceDistribute(userId) {
	var iemi = $('#iemi').val();
	layer.confirm(
			'是否要分配给该用户？',
			{btn: ['确定','取消']},
			function(){
				var String = {
						'userId': userId,
						'iemi': iemi 
						};
				$.ajax({
					data: String,
				
					type: "POST",
					
					dataType: 'json',
					
					url: "/CMS/device/ajaxDeviceDistribute.do",
					
					success: function(data) {
						layer.msg(data.msg,{time:2000});
					},
					error: function() {
						layer.msg("操作异常，请联系管理员！",{time: 2000});
					}
				});
			}
		);
}

function userConvert(user){
	if(user.userStatus=='1'){
		user.userStatus='已认证';
	}else {
		user.userStatus='未认证';
	}
	user.userRole = '评委';
	return user;
}