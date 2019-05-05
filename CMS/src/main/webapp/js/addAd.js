function clearInput() {
	$('input[type="text"]').val('');
	$('input[type="datetime-local"]').val('');
}

function warm() {
	var adTitle = $('#adTitle').val();
	var adNote = $('#adNote').val();
	var adAddr = $('#adAddr').val();
	var adPrice = $('#adPrice').val();
	var adOwner = $('#adOwner').val();
	var adEffectTime = $('#adEffectTime').val();
	var adEndTime = $('#adEndTime').val();
	var effectTime = new Date($('#adEffectTime').val()).getTime();
	var endTime = new Date($('#adEndTime').val()).getTime();
	if (adTitle == '') {
		layer.msg('请填写广告标题！', {time:2000});
	} else if (adNote == '') {
		layer.msg('请填写广告备注！', {time:2000});
	} else if (adAddr == '') {
		layer.msg('请填写广告位置！', {time:2000});
	} else if (adPrice == '') {
		layer.msg('请填写广告价格！', {time:2000});
	} else if (adOwner == '') {
		layer.msg('请填写所属公司！', {time:2000});
	} else if (adEffectTime == '') {
		layer.msg('请填写生效时间！', {time:2000});
	} else if (adEndTime == '') {
		layer.msg('请填写结束时间！', {time:2000});
	} else if (parseInt(effectTime) >= parseInt(endTime)) {
		layer.msg('结束时间应在生效时间之后！', {time:2000});
	} else {
		//异步提交表单
		$.ajax({
			
			type: 'post',
			
			data: $('#addAd_form').serialize(),
			
			url: '/CMS/ad/addAd.do',
			
			cache: false,
			
			dataType: 'json',
			
			success: function(data) {
				if(data.result=='Y') {
					layer.msg(data.msg, {time:2000});
				} else {
					layer.msg(data.msg, {time:2000});
				}
			},
			error: function() {
				layer.msg('操作异常，请联系管理员', {time:2000}); 
			}
		});
	}
}