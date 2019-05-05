function ajaxTest1(){ 
	/*var datas;
	datas.put("name","小红");
	datas.add("age",21);*/

     $.ajax({ 

     data:{
    	"name":"小红",
    	"age":$("#dates").val()
				
     }, 

     type:"POST", 

     dataType: 'json', 

     url:"ajax/test1", 

     success:function(data){ 

        alert("success");

        alert(data.name);

        alert(data.age);

      },

     error:function(data){ 

         alert("fail"); 

     }

}); 

} 

function ajaxTest2(){ 

	  

    $.ajax({ 

    data:{"name":"小红","age":21},

    type:"POST", 

    dataType: 'json',

    url:"ajax/test2", 

    success:function(data){ 

      alert("success");

      alert(data.name);

      alert(data.age);

    },

     error:function(data){ 

        alert("fail"); 

    }

    }); 

} 

function ajaxTest3(){ 

    var data = {"name":"小红","age":12};

    $.ajax({

      url: "ajax/test3",

      data: JSON.stringify(data),

      contentType: "application/json;charset=utf-8",

      type: "POST",

      success:function(data){ 

       alert("success");

       alert(data.name);

       alert(data.age);

     },

      error:function(data,b,c){ 

         alert("fail"); 

         alert(b);

     }

    });

} 