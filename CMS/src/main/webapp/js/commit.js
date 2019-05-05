function commitLog(){
	
	//dates=$("#dates").val();
	//string.Format("{0:d}",dates);
	var dates=$("#dates").val();
	var sure=$("input[name='sure']:checked").val();
	var thing=$("#thing1").val();
	var msg="日期:"+dates+"  sure:"+sure+"  thing:"+thing+"  你确定提交？";
	var string={
	    	"dates":dates,
	    	"sure":sure,
	    	"thing":thing					
	     };
	if(confirm(msg)==true){
		realCommitLog("ajax/addLog",string);
	}
	else{return;}
	
	}

function commitBill(){
	var billDate=$("#dates").val();
	var billUseful=$("#thing1").val();
	var billMoney=$("#number1").val();
	var billType=$("#number2").val();
	var msg="日期:"+billDate+"  金额:"+billMoney+"  用途:"+billUseful+"  使用类型:"+billType+"  你确定提交？";
	var string={
	    	"billDate":billDate,
	    	"billMoney":billMoney,
	    	"billUseful":billUseful,
	    	"billType":billType
	     };
	if(confirm(msg)==true){
		realCommitLog("ajax/addBill",string);
		
	}
	else{return;}
	
	
}

function realCommitLog(urls,string){
	$.ajax({ 

	     data:string, 

	     type:"POST", 

	     dataType: 'json', 

	     url:urls, 

	     success:function(data){ 
	    	
	        alert(data.msg);
	        fresh();

	      },

	     error:function(data){ 

	         alert(data.msg); 

	     }
})
}
