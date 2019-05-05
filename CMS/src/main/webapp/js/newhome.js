window.onload = function() {
	var userRole = $('#userRole').val();
	if(userRole=='admin') {
		$('#u_R').html('用户权限：管理员');
	}else if (userRole=='player') {
		$('#u_R').html('用户权限：选手');
	}else {
		$('#u_R').html('用户权限：裁判');
	}
}

function spread() {
    var icon = document.getElementById('icon').innerHTML;
    if(icon == '+') {
    	document.getElementById('icon').innerHTML = '-';
    	second_menu1.style.display = 'block';
    }else if(icon == '-') {
    	second_menu1.style.display = 'none';
    	document.getElementById('icon').innerHTML = '+';
    }
}

function openPlayerList() {
	layer.open({
		title : [ '选手管理', 'font-weight:bold;' ],
		type : 2,
		area : [ '910px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/user/getPlayerListPage.do?currPage=1'
	});
}

function openJudgeList() {
	layer.open({
		title : [ '评委管理', 'font-weight:bold;' ],
		type : 2,
		area : [ '900px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/user/getJudgeListPage.do?currPage=1'
	});
}

function openWorkerList() {
	layer.open({
		title : [ '员工管理', 'font-weight:bold;' ],
		type : 2,
		area : [ '900px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/user/getWorkerListPage.do?currPage=1'
	});
}

function openCertify() {
	layer.open({
		title : [ '认证管理', 'font-weight:bold;' ],
		type : 2,
		area : [ '850px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/user/getCertifyListPage.do?currPage=1'
	});
}

function openAddUser() {
	layer.open({
		title : [ '新建用户', 'font-weight:bold;' ],
		type : 2,
		area : [ '700px', '450px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/user/getAddUserPage.do'
	});
}

function openAddGame() {
	layer.open({
		title : [ '添加赛程', 'font-weight:bold;' ],
		type : 2,
		area : [ '700px', '450px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/game/getAddGamePage.do'
	});
}

function openGameList() {
	layer.open({
		title : [ '赛程管理', 'font-weight:bold;' ],
		type : 2,
		area : [ '700px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/game/getGameListPage.do?currPage=1'
	});
}

function addDevice() {
	layer.open({
		title : [ '添加设备', 'font-weight:bold;' ],
		type : 2,
		area : [ '700px', '450px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/device/getAddDevicePage.do'
	});
}

function deviceManage() {
	layer.open({
		title : [ '设备管理', 'font-weight:bold;' ],
		type : 2,
		area : [ '700px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/device/getDeviceListPage.do'
	});
}

function changePassword() {
	var userId = $('#userId').val();
	var userName = $('#userName').val();
	layer.open({
		title : [ '修改密码', 'font-weight:bold;' ],
		type : 2,
		area : [ '700px', '450px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/login/getChangePasswordPage.do?userId=' + userId + '&userName=' + userName
	});
}

function resetPassword() {
	var userName = $('#userName').val();
	layer.open({
		title : [ '重置密码', 'font-weight:bold;' ],
		type : 2,
		area : [ '700px', '450px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '../page/resetPassword.jsp'
	});
}

function Return() {
	layer.confirm('您是否要退出登录？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			window.location.href='/CMS/login/returnToLoginPage.do';
		});
}