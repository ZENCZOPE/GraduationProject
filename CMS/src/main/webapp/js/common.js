$(document).ready(function() {
	var a=getUserAuth();
	});
	
function getUserAuth(){
	$.ajax({ 

	     data:"{}", 

	     type:"POST", 

	     dataType: 'json', 
	     async:false,
	     url:"/CMS/user/getUserAuth.do", 

	     success:function(data){ 
	    	 $("#roleTest").html(data.Role);
	    	 $("#role").val(data.Role);
	    	 
	      },

	     error:function(data){ 

	         return  data.Role;

	     }
})
}