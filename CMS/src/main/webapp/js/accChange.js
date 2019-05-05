function clearInput() {
	$("input[type='text']").val("");
	$("input[type='datetime-local']").val("");
}

function warm() {
	var billType = $('#billType').val();
	var price = $('#price').val();
	var billNote = $('#billNote').val();
	var opId = $('#opId').val();
	var effectTime = $('#effectTime').val();
	var status = $('#status').val();
	if (billType == '') {
		layer.msg('请填写账单类型！', {time:2000});
	} else if (price == '') {
		layer.msg('请填写账单价格！', {time:2000});
	} else if (billNote == '') {
		layer.msg('请填写账单备注！', {time:2000});
	} else if (opId == '') {
		layer.msg('请填写负责人！', {time:2000});
	} else if (effectTime == '') {
		layer.msg('请填写生效日期！', {time:2000});
	} else if (status == '') {
		layer.msg('请填写账单状态！', {time:2000});
	} else {
		//异步提交表单
		$.ajax({
			
			type: 'post',
			
			data: $('#gameChange_form').serialize(), 
			
			url: '/CMS/account/updateAcc.do',
			
			cache: false,  
			
			dataType: 'json', 
			
			success: function (data) {
				if(data.result=='Y') {
					layer.msg(data.msg,{time:2000});
				} else{
					layer.msg(data.msg,{time:2000});
				}
			},
			error: function(data){
				layer.msg(data.msg,{time:2000}); 
			}
		});
	}
}