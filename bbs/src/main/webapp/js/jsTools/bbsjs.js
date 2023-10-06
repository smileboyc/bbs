	//设置控件value值
function getRe(con,id)
{var t= getQueryString(id);
 document.getElementById(con).value=t;
}
	
	//使用正则表达式去url中值 参数：Name
function getQueryString(name) {//如果不会正则法，也可以自己下定义本函数，以在串中查找多个“=”来取出各参数和值
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

	function bbsDisplayTz(tzId){
		var re0=new Object;
		$.ajax( //下面所有代码都是ajax函数的各个参数
		{  		  		
			type:"post",
			url:"bbsDisplayTz",
			data:{"tzId":tzId},
			dataType:'json',
			async:false,//设置为同步
			success:function(re){	
			re0=re;	
			},		
			error:function(re){ 
			alert("no"); }
			})
		return re0;
		}
		
	function bbsRemarkDisplay(tzId){
		var re0=new Object;
		$.ajax( //下面所有代码都是ajax函数的各个参数
		{  		  		
			type:"post",
			url:"remarkDisplay",
			data:{"tzId":tzId},
			dataType:'json',
			async:false,//设置为同步
			success:function(re){	
			re0=re;	
			},		
			error:function(re){ 
			alert("no"); }
			})
		return re0;
		}
	//后退
	function back(){
		window.history.back();
	}
	//跳转到主页面
	function skip(){
	window.location.href="toutiao.html"
	}
	//跳转到发布页面
	function fb(){
	window.location.href="edit.html"
	}
	//跳转到个人中心页面
	function grzx(){
	window.location.href="personalCenter.html"	
	}

	//访问数据库获取帖子
function  bbs()
	{  var re0=new Object();
		$.ajax({
		  type:"post",//提交数据方式post/get	
		  url:"bbsindex", 
		  data:{"xx":""},//随便给一个无法参数
		  dateType:"json", //从后台向前台传数据时的格式json
		  async:false,//设置为同步
		  success:function(re)   
		  {   re0=re;
			
		   },
		  error:function(re)
		  { //alert("no");
	     }    
	   })
	   return re0;
	}
	//填充主页面(三张图片)
      function display (div,re0){
		$("#"+div).empty();
		for(var i=0;i<re0.length;i++){
		var keys0=Object.keys(re0[i]);//目的是列出数据的键名，形成 1个数组 			
		//图片内容部分
		var  textContent= $ ("<div class='textContent'>")     
		var pic=re0[i][keys0[3]].split("*");
		var num=pic.length;
		if(re0[i][keys0[3]].length<2)
		 num=0
	
		switch(num){
			case 3:
			var tdTitle=$("<div class='titleContent'><a href='displayTz.html?id="+ re0[i][keys0[0]]+"'><p>"+re0[i][keys0[1]]+"</p></a></div>");
		 	textContent.append(tdTitle);
			var imgContent=$("<div class='imgContent'> <div class='img'><img src='/loadFile/bbs/"+ pic[0]+"'></div> <div class='img img1'><img src='/loadFile/bbs/"+ pic[1]+"'></div> <div class='img'><img src='/loadFile/bbs/"+ pic[2]+"'></div></div>")
			textContent.append(imgContent);
			break;	
			case 1:
			var tdTitle=$("<div class='titleContent1'><a href='displayTz.html?id="+ re0[i][keys0[0]]+"'><p>"+re0[i][keys0[1]]+"</p></a></div>");
		 	textContent.append(tdTitle);	
			var imgContent=$("<div class='imgContent1'> <div class='img2'><img src='/loadFile/bbs/"+ pic[0]+"'></div></div>")
			textContent.append(imgContent);
			break;		
			case 0:
			var tdTitle=$("<div class='titleContent'><a href='displayTz.html?id="+ re0[i][keys0[0]]+"'><p>"+re0[i][keys0[1]]+"</p></a></div>");
		 	textContent.append(tdTitle);	
		 		break;			
		}
		//评论部分
		var buttom=$("<div class='remark'><div class='bottomLeft'><span>"+re0[i][keys0[4]]+"</span><span>点回:"+re0[i][keys0[2]]+"</span></div><div class='bottomRight'><span>"+re0[i][keys0[5]]+"</span></div></div>")
	    textContent.append(buttom);
	    $("#"+div).append(textContent)
	}
	}
	function getSession()
				{   $.ajax({
					  type:"post",//提交数据方式post/get	
					  url:"getSession", 
					  data:{"xx":""},//随便给一个无法参数
					  dateType:"Text", //从后台向前台传数据时的格式json
					  success:function(re)   
					  {  
					   	document.getElementById("p1").innerHTML="您好！"+re;
					   },
					  error:function(re)
					  { alert("no");
				     }    
				   })
				}
				
	function getNum()
				{   $.ajax({
					  type:"post",//提交数据方式post/get	
					  url:"getNum", 
					  data:{"xx":""},//随便给一个无法参数
					  dateType:"Text", //从后台向前台传数据时的格式json
					  success:function(re)   
					  {  
					   document.getElementById("p2").innerHTML=re;
					   },
					  error:function(re)
					  { alert("no");
				     }    
				   })
				}		
		function update(){
							$.ajax( //下面所有代码都是ajax函数的各个参数
							{  		  		
							type:"post",
							url:"getUers",
							data:{},
							dataType:'json',
							async:false,
							success:function(re){
							var string="";						
							for(var i=0;i<re.length;i++){
								
								var key=Object.keys(re[i]);
								string=string+re[i][key[0]]+" , ";
							}
							string=string.substring(0,string.length-2)
							 document.getElementById("p3").innerHTML=string;
							
							},		
							error:function(re){ 
							alert("no"); }
							})						
				}
	//展示帖子内容
	function Tzdisplay(div,re0){
			   $("#"+div).empty();
			   var keys0=Object.keys(re0[0]);//目的是列出数据的键名，形成 1个数组
			   
			  var tdTitle=$("<div class='Tztitle'>"+ re0[0][keys0[1]]+" </div>");
			   $("#"+div).append(tdTitle);	
			   var tdFbr=$("<div class='TzFbr'><div style='width: 70%; text-align: left;float: left;'> <img src='./img/灰熊.jpg' class='imgTp' ><span class='Fbr'>"+ re0[0]["作者"]+"</span><span class='Fbr'>"+re0[0]["发布日期"]+"</span></div> <div style='width: 27%; text-align: right;float: right;'><button class='bt1'>关注</button></div></div>");
			   $("#"+div).append(tdFbr);	
			 if(re0[0]["照片"].toString().trim()!="")//表示有照片
			{	 var tdzp=$("<div class='TzZp'><img style='width:100%;' src='/loadFile/bbs/"+ re0[0]["照片"].toString().trim()+"'/> </div>");
			 $("#"+div).append(tdzp);	   }
				 var tdCont=$("<div class='TzCont'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+re0[0][keys0[2]]+"</div>");
				 $("#"+div).append(tdCont);

				 $("#div3").append($("<a href=replTz.html?id="+re0[0]["id"]+" style='color:red;'>回复贴主</a>"))
		}
		//展示帖子内容(增加了一个div)
	function Tzdisplay1(div,re0,div1){
			   $("#"+div1).empty();
			   
			   $("#"+div).empty();
			   var keys0=Object.keys(re0[0]);//目的是列出数据的键名，形成 1个数组
			   var nav=$("<button class='nav_a nav_a_left'  onclick=displayRbox('remarkBox')><label  class='iconfont icon-bi iconfont1'></label>&nbsp;&nbsp;友善评论</button><button class='nav_a ' ><label  class='iconfont icon-pinglun iconfont1'></label>"+ re0[0]["评论"]+"</button><button onclick='insertCollect(tzid)' class='nav_a' ><label  class='iconfont icon-shoucang iconfont1'></label></button><button onclick='insertLike(tzid)' class='nav_a' ><label  class='iconfont icon-dianzan iconfont1'></label>"+ re0[0]["点赞"]+"</button><button class='nav_a nav_a_right' ><label  class='iconfont icon-zhuanfa1 iconfont1'></label></button>");
			   $("#"+div1).append(nav);
			  var pic=re0[0]["照片"].split("*");
			  var num=pic.length;			  
			 if(re0[0]["照片"].length<2)
				 num=0	 		
		   switch(num){ 
			case 1:
			  var tdTitle=$("<div class='Tztitle'>"+ re0[0][keys0[1]]+" </div>");
			  $("#"+div).append(tdTitle);	
			  var tdFbr=$("<div class='TzFbr'><div style='width: 70%; text-align: left;float: left;'> <img src='./img/灰熊.jpg' class='imgTp' ><span class='Fbr'>"+ re0[0]["作者"]+"</span><span class='Fbr'>"+re0[0]["发布日期"]+"</span></div> <div style='width: 27%; text-align: right;float: right;'><button class='bt1'>关注</button></div></div>");
			  $("#"+div).append(tdFbr);
			  var tdzp=$("<div class='TzZp'><img style='width:100%;' src='/loadFile/bbs/"+ pic[0].toString().trim()+"'/> </div>");
			  $("#"+div).append(tdzp);
			  var tdCont=$("<div class='TzCont'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+re0[0][keys0[2]]+"</div>");
			  $("#"+div).append(tdCont);
			
			  break;
			case 3:
			  var tdTitle=$("<div class='Tztitle'>"+ re0[0][keys0[1]]+" </div>");
			  $("#"+div).append(tdTitle);	
			  var tdFbr=$("<div class='TzFbr'><div style='width: 70%; text-align: left;float: left;'> <img src='./img/灰熊.jpg' class='imgTp' ><span class='Fbr'>"+ re0[0]["作者"]+"</span><span class='Fbr'>"+re0[0]["发布日期"]+"</span></div> <div style='width: 27%; text-align: right;float: right;'><button class='bt1'>关注</button></div></div>");
			  $("#"+div).append(tdFbr);
			  var tdzp=$("<div class='TzZp'><div id='carousel-example-generic' class='carousel slide' data-ride='carousel'> <ol class='carousel-indicators'><li data-target='#carousel-example-generic' data-slide-to='0' class='active'></li><li data-target='#carousel-example-generic' data-slide-to='1'></li><li data-target='#carousel-example-generic' data-slide-to='2'></li></ol><div class='carousel-inner' role='listbox'><div class='item active'> <img  src='/loadFile/bbs/"+ pic[0].toString().trim()+"'/></div><div class='item'> <img  src='/loadFile/bbs/"+ pic[1].toString().trim()+"'/></div>	<div class='item'><img  src='/loadFile/bbs/"+ pic[2].toString().trim()+"'/> </div> </div></div></div>");
			  $("#"+div).append(tdzp);			  		  
			  var tdCont=$("<div class='TzCont'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+re0[0][keys0[2]]+"</div>");
			  $("#"+div).append(tdCont);
			  
			break;
			case 0:
			var tdTitle=$("<div class='Tztitle'>"+ re0[0][keys0[1]]+" </div>");
			  $("#"+div).append(tdTitle);	
			  var tdFbr=$("<div class='TzFbr'><div style='width: 70%; text-align: left;float: left;'> <img src='./img/灰熊.jpg' class='imgTp' ><span class='Fbr'>"+ re0[0]["作者"]+"</span><span class='Fbr'>"+re0[0]["发布日期"]+"</span></div> <div style='width: 27%; text-align: right;float: right;'><button class='bt1'>关注</button></div></div>");
			  $("#"+div).append(tdFbr);
			  var tdCont=$("<div class='TzCont'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+re0[0][keys0[2]]+"</div>");
			  $("#"+div).append(tdCont);
			 
			 break;
		}
		}
		
			//展示帖子内容
	function Tzdisplay0(div,re0){
			   $("#"+div).empty();
			   var keys0=Object.keys(re0[0]);//目的是列出数据的键名，形成 1个数组
			  var pic=re0[0]["照片"].split("*");
			  var num=pic.length;			  
			 if(re0[0]["照片"].length<2)
				 num=0	 		
		   switch(num){ 
			case 1:
			  var tdTitle=$("<div class='Tztitle'>"+ re0[0][keys0[1]]+" </div>");
			  $("#"+div).append(tdTitle);	
			  var tdFbr=$("<div class='TzFbr'><div style='width: 70%; text-align: left;float: left;'> <img src='./img/灰熊.jpg' class='imgTp' ><span class='Fbr'>"+ re0[0]["作者"]+"</span><span class='Fbr'>"+re0[0]["发布日期"]+"</span></div> <div style='width: 27%; text-align: right;float: right;'><button class='bt1'>关注</button></div></div>");
			  $("#"+div).append(tdFbr);
			  var tdzp=$("<div class='TzZp'><img style='width:100%;' src='/loadFile/bbs/"+ pic[0].toString().trim()+"'/> </div>");
			  $("#"+div).append(tdzp);
			  var tdCont=$("<div class='TzCont'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+re0[0][keys0[2]]+"</div>");
			  $("#"+div).append(tdCont);			 
			  break;
			case 3:
			  var tdTitle=$("<div class='Tztitle'>"+ re0[0][keys0[1]]+" </div>");
			  $("#"+div).append(tdTitle);	
			  var tdFbr=$("<div class='TzFbr'><div style='width: 70%; text-align: left;float: left;'> <img src='./img/灰熊.jpg' class='imgTp' ><span class='Fbr'>"+ re0[0]["作者"]+"</span><span class='Fbr'>"+re0[0]["发布日期"]+"</span></div> <div style='width: 27%; text-align: right;float: right;'><button class='bt1'>关注</button></div></div>");
			  $("#"+div).append(tdFbr);
			  var tdzp=$("<div class='TzZp'><div id='carousel-example-generic' class='carousel slide' data-ride='carousel'> <ol class='carousel-indicators'><li data-target='#carousel-example-generic' data-slide-to='0' class='active'></li><li data-target='#carousel-example-generic' data-slide-to='1'></li><li data-target='#carousel-example-generic' data-slide-to='2'></li></ol><div class='carousel-inner' role='listbox'><div class='item active'> <img  src='/loadFile/bbs/"+ pic[0].toString().trim()+"'/></div><div class='item'> <img  src='/loadFile/bbs/"+ pic[1].toString().trim()+"'/></div>	<div class='item'><img  src='/loadFile/bbs/"+ pic[2].toString().trim()+"'/> </div> </div></div></div>");
			  $("#"+div).append(tdzp);			  		  
			  var tdCont=$("<div class='TzCont'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+re0[0][keys0[2]]+"</div>");
			  $("#"+div).append(tdCont);			 
				break;
			case 0:
			var tdTitle=$("<div class='Tztitle'>"+ re0[0][keys0[1]]+" </div>");
			  $("#"+div).append(tdTitle);	
			  var tdFbr=$("<div class='TzFbr'><div style='width: 70%; text-align: left;float: left;'> <img src='./img/灰熊.jpg' class='imgTp' ><span class='Fbr'>"+ re0[0]["作者"]+"</span><span class='Fbr'>"+re0[0]["发布日期"]+"</span></div> <div style='width: 27%; text-align: right;float: right;'><button class='bt1'>关注</button></div></div>");
			  $("#"+div).append(tdFbr);
			  var tdCont=$("<div class='TzCont'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+re0[0][keys0[2]]+"</div>");
			  $("#"+div).append(tdCont);
			
			 break;
		}
		}
	
	//展示帖子评论
	function displayRep(div,re0)
	 {   
		  //测试一下返回的究竟是什么类型，如果是串，则转换 jsonarray
	   	 $("#"+div).empty();
	    var keys0=Object.keys(re0[0]);//目的是列出数据的键名，形成 1个数组
		var repNo=keys0.length;
		for(i=0;i<repNo;i++)
		{ var tdFbr=$("<div class='Tzplr'><div style='width: 70%; text-align: left;float: left;'><img class='imgTp'  src='/loadFile/bbs/"+re0[i]["phone"] + "' width='35px'/><span class='Fbr'>"+ re0[i]["repL"]+"</span><span class='Fbr'>"+re0[i]["repDate"]+"</span></div> <div class='Tzdz'> <a href=''><label for='' class='iconfont icon-dianzan'></label>"+re0[i]["clickNO"]+" </a>&nbsp;&nbsp;<button name='huifuBt' class='huifuBt1' id='"+re0[i]["repL"]+"'> <label  class='iconfont icon-huifu'>1</label></button>"+"</div></div>");
		 $("#"+div).append(tdFbr);	
		 var tdCont=$("<div class='TzRepl'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+re0[i]["content"]+"</div>");
		 $("#"+div).append(tdCont);
		}
		
	 }
	 //添加评论
	 function insertRemark(tzid){
	    var contentBox=	document.getElementById("contentBox").innerHTML;    
			   $.ajax({
			   	  type:"post",//提交数据方式post/get	
			   	  url:"remark", 
			   	  data:{"tzid":tzid,"contentBox":contentBox},//随便给一个无法参数
			   	  dateType:"text", //从后台向前台传数据时的格式json
			   	  success:function(re)   
			   	  {  
			window.location.reload();
			   	   },
			   	  error:function(re)
			   	  { alert("no");
			        }    
			      })
	 	}
	 		 //回复评论
	 function insertRemark0(tzid,content){	
			   $.ajax({
			   	  type:"post",//提交数据方式post/get	
			   	  url:"remark", 
			   	  data:{"tzid":tzid,"contentBox":content},//随便给一个无法参数
			   	  dateType:"text", //从后台向前台传数据时的格式json
			   	  success:function(re)   
			   	  {  
			    window.location.reload();
			   	   },
			   	  error:function(re)
			   	  { alert("no");
			        }    
			      })
	 	}
		//添加浏览记录
		function insertRecord(tzid){
					   $.ajax({
					   	  type:"post",//提交数据方式post/get	
					   	  url:"Record", 
					   	  data:{"tzid":tzid},//随便给一个无法参数
					   	  dateType:"text", //从后台向前台传数据时的格式json
					   	  success:function(re)   
					   	  {  
					      
					   	   },
					   	  error:function(re)
					   	  { alert("no");
					        }    
					      })
			}
				//添加收藏
		function insertCollect(tzid){
			event.currentTarget.style.color="red";
					   $.ajax({
					   	  type:"post",//提交数据方式post/get	
					   	  url:"Collect", 
					   	  data:{"tzid":tzid},//随便给一个无法参数
					   	  dateType:"text", //从后台向前台传数据时的格式json
					   	  success:function(re)   
					   	  {        
					   	   },
					   	  error:function(re)
					   	  { alert("no");
					        }    
					      })
			}
				//点赞
		function insertLike(tzid){
			 event.currentTarget.style.color="red";
					   $.ajax({
					   	  type:"post",//提交数据方式post/get	
					   	  url:"insertLike", 
					   	  data:{"tzid":tzid},//随便给一个无法参数
					   	  dateType:"text", //从后台向前台传数据时的格式json
					   	  success:function(re)   
					   	  {   
						window.location.reload();     
					   	   },
					   	  error:function(re)
					   	  { alert("no");
					        }    
					      })
			}
//获取历史记录信息
function getRecord(div)
	{  var re0=new Object();
	    
		$.ajax({
		  type:"post",//提交数据方式post/get	
		  url:"recordDisplay", 
		  data:{"xx":""},//随便给一个无法参数
		  dateType:"json", //从后台向前台传数据时的格式json
		  async:false,//设置为同步
		  success:function(re)   
		  {  document.getElementById("div1").innerHTML="";
			  
			re0=re;
		
			var array=fieldLength0(re);
			
		   },
		  error:function(re)
		  { //alert("no");
	     }    
	   })
	   return re0;
	}
	
	//获取收藏信息
function getCollect(div)
	{  var re0=new Object();
	     
		$.ajax({
		  type:"post",//提交数据方式post/get	
		  url:"collectDisplay", 
		  data:{"xx":""},//随便给一个无法参数
		  dateType:"json", //从后台向前台传数据时的格式json
		  async:false,//设置为同步
		  success:function(re)   
		  {document.getElementById("div1").innerHTML=""; 
			 re0=re;
			alert(re0)
			var array=fieldLength0(re);
			alert(array)
		   },
		  error:function(re)
		  { //alert("no");
	     }    
	   })
	   return re0;
	}
	
//表格数据自适应 参数JSON对象数组
function fieldLength0(re){
		var keys=Object.keys(re[0]); 		
		var rows=re.length;  //行列数，以后根据需要，变量化	
		var lengthArray=new Array(keys.length);	//记录每一列的最长长度
		var sumlength=0;//总长度	
		//将列名的长度设置为数组初始值
		for(var i=0;i<keys.length;i++){
		   lengthArray[i]=keys[i].length;
		}				
		for(var i=0;i<rows;i++){				
			for(var j=0;j<keys.length;j++){	
			  if(re[i][keys[j]].length>=lengthArray[j]) {
				 lengthArray[j]=re[i][keys[j]].length;				 
			}			  		
			}
		}	
		for(var i=0;i<keys.length;i++){
			sumlength=sumlength+lengthArray[i] ;
		}		
		for(var i=0;i<keys.length;i++){
		lengthArray[i]=parseInt(lengthArray[i]/sumlength*100);			
		}
         reToTable("div1",re,lengthArray)
		 return lengthArray
	}		
	//每一列数据宽度差距不大
	function  reToTable(divId,re,lengthArray){
				var div1=document.getElementById(divId);
					var table0=document.createElement("table");
			        table0.className="tb0"
					div1.append(table0);	
				    var keys0=Object.keys(re[0]);//随便取任意一行，得到键名称列表			
					//表头部分
					var trHead=document.createElement("tr");
					table0.append(trHead);
					var tdHead=document.createElement("td");
					tdHead.innerHTML="<input type='checkbox' id='checkbox2' class='checkbox1'>"
					tdHead.className="tdF tdH"
					trHead.append(tdHead);			
					for(var i=0;i<keys0.length;i++)
					{	
					var tdh0=document.createElement("td");
					 tdh0.style.width=lengthArray[j]+"%";
					 tdh0.innerText=keys0[i];
					 tdh0.className="tdH"
					 trHead.append(tdh0) 
					}
					var tdh0=document.createElement("td");
					tdh0.innerHTML="操作"
					tdh0.className="tdL tdH"
					trHead.append(tdh0)	
				//内容部分
					for(var i=0;i<re.length;i++){
					 var tr0=document.createElement("tr");
					 var tdF=document.createElement("td");
					   tdF.innerHTML="<input type='checkbox' name='"+re[i][keys0[0]]+"' class='checkbox1'>"	   
					   tdF.className="tdF td0";
					   tr0.append(tdF)
						for(var j=0;j<keys0.length;j++){						
						var td0=document.createElement("td");
						td0.innerText=re[i][keys0[j]]
						td0.className="td0"		
						tr0.append(td0);
						}
					 var tdL=document.createElement("td");	
					 tdL.innerHTML="<button type='button' name='"+re[i][keys0[0]]+"' class='bt'>删除</button>"
					 tdL.className="td0 tdL"
					 tr0.append(tdL)
					 table0.append(tr0)
					}				
			}
			// 修改密码
			function xgPassword(div){
			document.getElementById(div).innerHTML="";		
			 var titile=$("<div ><h1>设置新密码</h1></div>");
			 $("#"+div).append(titile);
				 
			 var password1=$("<div class='password1'><span>新密码</span><input type='password' placeholder='请设置新密码' id='password1'><hr></div>")
			 $("#"+div).append(password1);
			
			var password2=$("<div class='password1'><span>确认密码</span><input type='password' placeholder='请再次输入新密码'><hr></div>")		  
			 $("#"+div).append(password2);
			 
			var text1=$("<div><ul><li>密码由8-16位数字、字母、或符号组成</li><li>至少含2种以上字符</li></ul></div>")
			$("#"+div).append(text1);
			
			var tj=$("<div class='tj'><button class='tj' onclick='updatePassword()'>确定</button></div>")
			$("#"+div).append(tj);
			
			var ts=$("<div class='ts'><label >安全提示：新密码请勿与旧密码过于相似</label></div>")
				$("#"+div).append(ts);
			}
		