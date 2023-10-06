function gerT(array1, obj) 
			 	{ var tb=document.getElementById(obj);
			 	 	var tr=document.createElement("div");
			 		tr.className="myTr1";
			 		for(var j=0;j<array1[1].length;j++)
			 	   { //解决一行内的每个单元格div
			 			var td=document.createElement("div");	
			 			if(i==array1.length-1)
			 			td.className="mytdButtom";
			 			td.innerText=array1[i][j];
			 			td.style.width=(100/array1[1].length-1)+"%";
			 			if(j==array1[1].length-1)							
			 			{ if(i==array1.length-1){
			 			td.className="mytdRight mytdButtom";}
			 			else{td.className="mytdRight";}	}
			 		tr.append(td);
			 	}tb.append(tr);}	
