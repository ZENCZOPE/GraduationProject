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

function changePassword() {
	var userId = $('#userId').val();
	var userName = $('#userName').val();
	layer.open({
		title : [ '修改密码', 'font-weight:bold;' ],
		type : 2,
		area : [ '1000px', '550px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/login/getChangePasswordPage.do'
	});
}

function uploadPage() {
	layer.open({
		title : [ '资质上传', 'font-weight:bold;' ],
		type : 2,
		area : [ '500px', '300px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/file/getUploadPage.do'
	});
}

function openMyGame() {
	var userRole = $('#userRole').val();
	var userId = $('#userId').val();
	layer.open({
		title : [ '我的赛程', 'font-weight:bold;' ],
		type : 2,
		area : [ '1000px', '550px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/game/getMyGamePage.do'
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

function Return() {
	layer.confirm('您是否要退出登录？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			window.location.href='/CMS/login/returnToLoginPage.do';
		});
}

function move(a) {
	a.style.transition = 'all 0.6s';
}