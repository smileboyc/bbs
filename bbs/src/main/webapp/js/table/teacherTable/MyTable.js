
 function gerT2(divName,data0)//data0是竖线间隔的多行数据串
 {   var tb=document.getElementById(divName);
    var data=data0.split("|");
	var rows=4;  //行列数，以后根据需要，变量化
    var cols=data.length;
	var  colWidth=100/(cols)-1;//以后决定列宽百分比
  for(var i=0;i<4;i++)//4要根据数据多个人的行数来
  { //解决行DIV  
   //var tr="<div class='myTr1'>";
   var tr=document.createElement("div");
   tr.className="myTr1";
   tb.appendChild(tr)
    var td=""; 
 	for(var j=0;j<cols;j++)
    { //解决一行内的每个单元格div
 		 var td=document.createElement("div");
	 td.className="myTd"; td.style.width=colWidth+"%";
	 td.innerText=data[j];
 	 if(i==(rows-1)) //表示是最后一行
	  td.className="myTd mytdBottom"; 	 
 	if(j==(cols-1))//表示最后一列
	  td.className="myTd mytdRight"; 	  
 	if(j==(cols-1) && i==(rows-1))//表示最右下角单元格
	  td.className="myTd mytdRight mytdBottom"
 	 tr.appendChild(td); 	 	
 	}  	  
  } 	 
 }
 
 function JsonsTOdivTable(divName,re)//re是jsonArray
 { //把json数组数据展示在一个由div布局的“表格”中
   var tb=document.getElementById(divName);
   var keys=Object.keys(re[0]);
   var rows=re.length;  //行列数，以后根据需要，变量化
   var cols=keys.length;
   var  colWidth=100/(cols)-1;//以后决定列宽百分比
  for(var i=0;i<rows;i++)// 要根据数据多个人的行数来
  { //解决行DIV      
   var tr=document.createElement("div");
   tr.className="myTr1";
   tb.appendChild(tr)
    var td=""; 
 	for(var j=0;j<cols;j++)//解决一行内的每个单元格div
    { var td=document.createElement("div");
	// td.className="myTd1"; td.style.width=colWidth+"%";
	 td.innerText=re[i][keys[j]];
 	 if(i==(rows-1)) //表示是最后一行
	  td.className="myTd1 mytdBottom"; 	 
 	if(j==(cols-1))//表示最后一列
	  td.className="myTd1 mytdRight"; 	  
 	if(j==(cols-1) && i==(rows-1))//表示最右下角单元格
	  td.className="myTd mytdRight mytdBottom"
 	 tr.appendChild(td); 	 	
 	}  	  
  } 	 
 }
 
 function JsonsTOTable(divn,json)// 是jsonArray
 { //把json数组数据展示在一个“表格”中
  var s1=$("<table border='1px' width='55%'>");
		$("#"+divn).append(s1);
		//先输出表头，任何json都能通用
		var keys0=Object.keys(json[0]);//随便取任意一行，得到键名称列表
		var trHead=$("<tr>");s1.append(trHead);
		for(var i=0;i<keys0.length;i++)
		{
		 var tdHead=$("<td>"+keys0[i]+"</td>");trHead.append(tdHead);
		}
		var trEndHead=$("</tr>");s1.append(trEndHead);
		
		for(var i=0;i<json.length;i++)//处理每行
		{ 
		 var tr0=$("<tr>"); s1.append(tr0);
		 var keys=Object.keys(json[i]);//得到每个json的键名
		 for(var j=0;j<keys.length;j++)
		 {if(json[i][keys[j]]==null)
		  var td0=$("<td>&nbsp</td>");
		  else
			 var td0=$("<td>"+json[i][keys[j]]+"</td>"); //输出每i行的第j个键值
		 
		 tr0.append(td0);
		 }   
		 var trend0=$("</tr>"); s1.append(trend0);
		}
		var send=$("</table>");    $("#"+divn).append(send);
		
 }