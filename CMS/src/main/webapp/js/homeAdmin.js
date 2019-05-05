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

function spread(atag) {
	var a = $(atag).next('.second_menu').css('display');
	if(a == 'none') {
		$(atag).next('.second_menu').css('display', 'block');
		$(atag).children('.arrow').css('background-image', 'url(../img/reduce.png)');
	}else {
		$(atag).next('.second_menu').css('display', 'none');
		$(atag).children('.arrow').css('background-image', 'url(../img/add.png)');
	}
}
function userAdd() {
	document.getElementById('myiframe').src = '/CMS/user/getUserAdd.do';
}

function workerList() {
	document.getElementById('myiframe').src = '/CMS/user/getWorkerListPage.do';
}

function judgeList() {
	document.getElementById('myiframe').src = '/CMS/user/getJudgeListPage.do';
}

function playerList() {
	document.getElementById('myiframe').src = '/CMS/user/getPlayerListPage.do';
}

function changePassword() {
	var userId = $('#userId').val();
	var userName = $('#userName').val();
	document.getElementById('myiframe').src = '/CMS/login/getChangePasswordPage.do';
}

function resetPassword() {
	document.getElementById('myiframe').src = '/CMS/login/getResetPasswordPage.do';
}

function openCertify() {
	document.getElementById('myiframe').src = '/CMS/user/getCertifyListPage.do';
}

function addDevice() {
	document.getElementById('myiframe').src = '/CMS/device/getAddDevicePage.do';
}

function deviceUse() {
	document.getElementById('myiframe').src = '/CMS/device/getDeviceUsePage.do';
}

function deviceManage() {
	document.getElementById('myiframe').src = '/CMS/device/getDeviceListPage.do';
}

function deviceTrouble() {
	document.getElementById('myiframe').src = '/CMS/device/getDeviceTroublePage.do';
}

function deviceQuery() {
	document.getElementById('myiframe').src = '/CMS/device/getDeviceQueryPage.do';
}

function addGame() {
	document.getElementById('myiframe').src = '/CMS/game/getAddGamePage.do';
}

function gameAllocation() {
	document.getElementById('myiframe').src = '/CMS/game/getGameAllocation.do';
}

function gameManage() {
	document.getElementById('myiframe').src = '/CMS/game/getGameManagePage.do';
}

function gameQuery() {
	document.getElementById('myiframe').src = '/CMS/game/getGameQueryPage.do';
}

function addAd() {
	document.getElementById('myiframe').src = '/CMS/ad/getAddAccountPage.do';
}

function adManage() {
	document.getElementById('myiframe').src = '/CMS/ad/getAdManagePage.do';
}

function adQuery() {
	document.getElementById('myiframe').src = '/CMS/ad/getAdQueryPage.do';
}

function addAccount() {
	document.getElementById('myiframe').src = '/CMS/account/getAddAccountPage.do';
}

function accountList() {
	document.getElementById('myiframe').src = '/CMS/account/getAccListPage.do?currPage=1';
}

function finance() {
	document.getElementById('myiframe').src = '/CMS/account/getFinancePage.do?currPage=1';
}

function uploadPage() {
	layer.open({
		title : [ '资质上传', 'font-weight:bold;' ],
		type : 2,
		area : [ '600px', '400px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '../page/uploadFile.jsp'
	});
}

function openViewGame() {
	layer.open({
		title : [ '查看赛程', 'font-weight:bold;' ],
		type : 2,
		area : [ '800px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/game/getViewGamePage.do'
	});
}

function openMyGame() {
	var userRole = $('#userRole').val();
	var userId = $('#userId').val();
	layer.open({
		title : [ '我的赛程', 'font-weight:bold;' ],
		type : 2,
		area : [ '800px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/game/getMyGamePage.do'
	});
}

function quit() {
	layer.confirm('您是否要退出登录？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			window.location.href='/CMS/login/returnToLoginPage.do';
		});
}