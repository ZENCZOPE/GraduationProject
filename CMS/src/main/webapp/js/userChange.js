function clearInput (){
	$("input[type='text']").val("");
}

function warm(){
	//异步提交表单
	var userId = $('#userId').val();
	$.ajax({		
		type: 'post',
		
		data: $('#userChange_form').serialize(), 
		
		url: '/CMS/user/ajaxUserChange.do?userId=' + userId,
		
		cache:false,  
		
		dataType:'json', 
		
		success: function (data) {
			if(data.result=='Y'){
				layer.msg(data.msg,{time:2000});
			}else{
				layer.msg(data.msg,{time:2000});
			}
		},
		error:function(data){
			layer.msg("操作异常，请联系管理员",{time:2000}); 
		}
	});
}