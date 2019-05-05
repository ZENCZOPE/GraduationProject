function clearInput() {
	$('input[type="text"]').val('');
}

function warm() {
	var userName = $('#userName').val();
	var userRole = $('#userRole').val();
	var userAge = $('#userAge').val();
	var userMail = $('#userMail').val();
	var userPhone = $('#userPhone').val();
	var userAddr = $('#userAddr').val();
	var userIdcard = $('#userIdcard').val();
	if (userName == '') {
		layer.msg('请填写用户姓名！', {time:2000});
	} else if (userRole == '') {
		layer.msg('请填写用户权限！', {time:2000});
	} else if (userAge == '') {
		layer.msg('请填写用户年龄！', {time:2000});
	} else if (userMail == '') {
		layer.msg('请填写用户邮件！', {time:2000});
	} else if (userPhone == '') {
		layer.msg('请填写用户电话！', {time:2000});
	} else if (userAddr == '') {
		layer.msg('请填写用户地址！', {time:2000});
	} else if (userIdcard == '') {
		layer.msg('请填写用户身份证！', {time:2000});
	} else {
		//异步提交表单
		$.ajax({
			
			type: 'post',
			
			data: $('#adduser_form').serialize(),
			
			url: '/CMS/user/ajaxUserAdd.do',
			
			cache:false,
			
			dataType:'json',
			
			success: function(data) {
				if(data.result == 'Y') {
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