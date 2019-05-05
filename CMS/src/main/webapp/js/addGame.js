function clearInput() {
	$('input[type="text"]').val('');
	$('input[type="datetime-local"]').val('');
}

function warm() {
	var gameName = $('#gameName').val();
	var gameLevel = $('#gameLevel').val();
	var gameJoinCount = $('#gameJoinCount').val();
	var gameUpCount = $('#gameUpCount').val();
	var gameDate = $('#gameDate').val();
	var gameAddr = $('#gameAddr').val();
	var gameEndDate = $('#gameEndDate').val();
	var date = new Date($('#gameDate').val()).getTime();
	var endDate = new Date($('#gameEndDate').val()).getTime();
	if (gameName == '') {
		layer.msg('请填写赛程名称！', {time:2000});
	} else if (gameLevel == '') {
		layer.msg('请填写赛程等级！', {time:2000});
	} else if (gameJoinCount == '') {
		layer.msg('请填写参加人数！', {time:2000});
	} else if (gameUpCount == '') {
		layer.msg('请填写晋级人数！', {time:2000});
	} else if (gameDate == '') {
		layer.msg('请填写赛程日期！', {time:2000});
	} else if (gameAddr == '') {
		layer.msg('请填写赛程地址！', {time:2000});
	} else if (gameEndDate == '') {
		layer.msg('请填写结束日期！', {time:2000});
	} else if (gameUpCount > gameJoinCount) {
		layer.msg('晋级人数不得大于参加人数！', {time:2000});
	} else if (parseInt(date) >= parseInt(endDate)) {
		layer.msg('结束日期应在起始日期之后！', {time:2000});
	} else {
		//异步提交表单
		$.ajax({
			
			type: 'post',
			
			data: $('#addgame_form').serialize(),
			
			url: '/CMS/game/ajaxAddGame.do',
			
			cache:false,
			
			dataType:'json',
			
			success: function(data) {
				if(data.result=='Y') {
					layer.msg(data.msg, {time:2000});
				} else {
					layer.msg(data.msg, {time:2000});
				}
			},
			error: function(data) {
				layer.msg('请完善信息！', {time:2000}); 
			}
		});
	}
}