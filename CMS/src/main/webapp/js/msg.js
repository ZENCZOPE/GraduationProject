$(document).ready(function() {
	getLastConsume();
	getLastWeekConsume();
	getLogMsg();
	});
	function getLastConsume(){
		$.ajax({ 

		     data:1, 

		     type:"POST", 

		     dataType: 'json', 

		     url:'ajax/getLastConsume', 

		     success:function(data){ 

		       $('#msg1').html(data.msg);


		      },

		     error:function(data){ 

		         alert(data.msg); 

		     }
	})}
	
	function getLastWeekConsume(){
		$.ajax({ 

		     data:1, 

		     type:"POST", 

		     dataType: 'json', 

		     url:'ajax/getLastWeekConsume', 

		     success:function(data){ 

		       $('#msg2').html(data.msg);


		      },

		     error:function(data){ 

		         alert(data.msg); 

		     }
	})}
	
	function getLogMsg(){
		$.ajax({ 

		     data:1, 

		     type:"POST", 

		     dataType: 'json', 

		     url:'ajax/getLogMsg', 

		     success:function(data){ 

		       $('#msg3').html(data.msg);


		      },

		     error:function(data){ 

		         alert(data.msg); 

		     }
	})}
	
	function fresh(){
	getLastConsume();
	getLastWeekConsume();
	getLogMsg();
	}