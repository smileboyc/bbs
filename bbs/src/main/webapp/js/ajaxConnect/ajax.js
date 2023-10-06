	  $(function(){
	   	$("#button1").bind("click",function(){ 	
	   	var url0=$("#form1").attr("action");
	    var entype0=$("#form1").attr("enctype")  //判断是普通还是流multipart/form-data
	   //var data0=$("#form1").serialize();  带文件的表单不能这样序列化了，因是流
	   	var data0=new FormData($("#form1")[0]); // 这里只是一个文件，如果是多个文件，要修改  	
	  	$.ajax( //下面所有代码都是ajax函数的各个参数
	  		  	{  		  		
	  		  	 type:"post",
	  		 	 url:url0,
	  		 	 cache:false,
	  		 	 processData:false,//需设置为false。因为data值是FormData对象，不需要对数据做处理
	  		 	 contentType:false,//需设置为false。因为是FormData对象，且已经声明了属性enctype="multipart/form-data"
	  		 	 data:data0,
	  		 	  dataType:'Json',
	  		  	success:function(re)
	  		  		{		 
					 JsonsTOTable("div2",re);
					
	  		  		 },		
	  		  		error:function(re)
	  		  		{ alert("no");  }
	  		  	})	
	  		  	
	
	   	}) 	
	   } )