function clearInput() {
	$('input[type="text"]').val('');
	$('input[type="datetime-local"]').val('');
}

function warm() {
	var iemi = $('#iemi').val();
	var socImei = $('#socImei').val();
	if (iemi == '') {
		layer.msg('请填写设备序列号！', {time:2000});
	} else if (socImei == '') {
		layer.msg('请填写设备名称！', {time:2000});
	} else {
		//异步提交表单
		$.ajax({
			
			type: 'post',
			
			data: $('#addDevice_form').serialize(),
			
			url: '/CMS/device/ajaxAddDevice.do',
			
			cache: false,
			
			dataType: 'json',
			
			success: function(data) {
				if(data.result=='Y') {
					layer.msg(data.msg, {time:2000});
				} else {
					layer.msg(data.msg, {time:2000});
				}
			},
			error: function(data) {
				layer.msg(data.msg, {time:2000}); 
			}
		});
	}
}