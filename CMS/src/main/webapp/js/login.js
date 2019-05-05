function login() {
	var userIdcard=$("#userIdcard").val();
	var password=hex_md5($("#password").val());
	var string={
			"userIdcard":userIdcard,
			"password":password
	};
	commit("/CMS/login/ajaxLogin.do", string);
}

function commit(urls,string){
	$.ajax({ 

	     data:string, 

	     type:"POST", 

	     dataType: 'json', 

	     url:urls, 

	     success:function(data){ 
	    	
	        if(data.result=='Y'){
	        	success();
	        }else{
	        	fail(data);
	        }
	      },
	     error:function(data){
	    	 alert("操作异常，请联系管理员"); 
	     }
	});
}

function success() {
	$("#user_form").submit();
}

function fail(data) {
	var userIdcard = $("#userIdcard").val();
    var password = $("#password").val();
    if(userIdcard == '') {
        $('.page-container form').find('.error').fadeOut('fast', function(){
            $(this).css('top', '27px');
        });
        $('.page-container form').find('.error').fadeIn('fast', function(){
            $(this).parent().find('.userIdcard').focus();
        });
        layer.msg('请填写身份证号码',{time:2000});
        return false;
    }
    if(password == '') {
        $('.page-container form').find('.error').fadeOut('fast', function(){
            $(this).css('top', '96px');
        });
        $('.page-container form').find('.error').fadeIn('fast', function(){
            $(this).parent().find('.password').focus();
        });
        layer.msg('请填写密码',{time:2000});
        return false;
    }
    $('.page-container form .userIdcard, .page-container form .password').keyup(function(){
        $('.page-container form').parent().find('.error').fadeOut('fast');
    });
    layer.msg(data.msg+'，请5秒后再次操作',{time:2000});
    $(user_form.elements).attr("disabled",true);
    window.setTimeout(function(){
    	$(user_form.elements).attr("disabled",false);
    },5000);
}

function keyLogin() {
	if (event.keyCode==13){//回车键的键值为13 
		login();
		} 
}

function warm() {
	var userName = $('#userName').val();
	var userAge = $('#userAge').val();
	var userMail = $('#userMail').val();
	var userPhone = $('#userPhone').val();
	var userAddr = $('#userAddr').val();
	var userCard = $('#userCard').val();
	var newPassword = $('#newPassword').val();
	var againPassword = $('#againPassword').val();
	if (userName == '') {
		layer.msg('请填写姓名！', {time:2000});
	} else if (userAge == '') {
		layer.msg('请填写年龄！', {time:2000});
	} else if (userMail == '') {
		layer.msg('请填写邮件！', {time:2000});
	} else if (userPhone == '') {
		layer.msg('请填写电话！', {time:2000});
	} else if (userAddr == '') {
		layer.msg('请填写住址！', {time:2000});
	} else if (userCard == '') {
		layer.msg('请填写身份证号码！', {time:2000});
	} else if (newPassword == '') {
		layer.msg('请填写密码！', {time:2000});
	} else if (againPassword == '') {
		layer.msg('请再次填写密码！', {time:2000});
	} else {
		//异步提交表单
		$.ajax({
			
			type: 'post',
			
			data: $('#register').serialize(),
			
			url: '/CMS/login/ajaxRegister.do',
			
			cache: false,
			
			dataType: 'json',
			
			success: function(data) {
				if(data.result == 'Y') {
					layer.msg(data.msg, {time:2000});
					setTimeout(function() {
						$(".contmove").animate({left: "0"}, 500);
						var a = $('#userCard').val();
						$('#userIdcard').val(a);
					},2000);
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

function back() {
	$(".contmove").animate({left: "0"}, 500);
}

function turnRight() {
	$('.contmove').animate({left: '-100%'}, 500);
	$('.error').hide();
}