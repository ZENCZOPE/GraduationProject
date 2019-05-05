function changePassword () {
	var userIdcard = $('#userIdcard').val();
	var oldPassword = $('#oldPassword').val();
	var newPassword = $('#newPassword').val();
	var repeatPassword = $('#repeatPassword').val();
	if (oldPassword == '') {
		layer.msg('请输入旧密码！', {time:2000});
	} else if (newPassword == '') {
		layer.msg('请输入新密码！', {time:2000});
	} else if (repeatPassword == '') {
		layer.msg('请再次输入新密码！', {time:2000});
	} else if (newPassword != repeatPassword) {
		layer.msg('两次密码不一致！', {time:2000});
	} else {
		oldPassword = hex_md5($('#oldPassword').val());
		var string = {
				'userIdcard' : userIdcard,
				'oldPassword' : oldPassword
		};
		commit('/CMS/login/ajaxPasswordMatch.do', string);
	}
}

function commit(urls,string) {
	$.ajax({ 

	     data: string, 

	     type: 'POST', 

	     dataType: 'json', 

	     url: urls, 

	     success: function(data) { 
	    	
	        if(data.result=='Y') {
	        	success();
	        } else {
	        	layer.msg(data.msg,{time:2000});
	        }
	      },
	     error:function(data){ 
	    	 layer.msg(data.msg,{time:2000}); 
	     }
	});
}

function success() {
	//异步提交表单
	$.ajax({		
		type: 'post', 
		data: $('#password_form').serialize(), 
		url: '/CMS/login/ajaxChangePassword.do',
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
        layer.msg("请完善信息！",{time:2000}); 
		}
	});
}