        //填充年份（参数1：下拉列表的ID（年））
		 function fillYear(year){	
	        document.getElementById(year).options.add(new Option('--请选择--','--请选择--'));
			 for(var i=2022;i>1900;i--){
			document.getElementById(year).options.add(new Option(i,i));
			 }
		 }
		 //填充月份（参数1：下拉列表的ID（月））
		 function fillMonth(month){
			  document.getElementById(month).options.length=0;
			 for(var i=1;i<=12;i++){
			 document.getElementById(month).options.add(new Option(i,i));
			  }
		 }
		 
		 //填充日期（参数1：下拉列表的ID（年），（参数2：下拉列表的ID（月）），（参数3：下拉列表的ID（日）））
		 function fillDay(year,month,day){
			  document.getElementById(day).options.length=0;
			 document.getElementById(day).options=0;
			 var month0=1+document.getElementById(month).selectedIndex;
			 var year0=2023-document.getElementById(year).selectedIndex;
			 var count;
			 if(boolyear(year0)&&month0==2)
			 count=29;
			 else
			 count=28;
			 if(month0==1||month0==3||month0==5||month0==7||month0==8||month0==10||month0==12)
			 count=31;
			 if(month0==4||month0==6||month0==9||month0==11)
			 count=30;
			 for(var i=1;i<=count;i++){
				 document.getElementById(day).options.add(new Option(i,i));
			 }		     		 
		 }
		 //判断是否闰年（参数1：下拉列表的ID（年））
		 function boolyear(year){
			 var flag=0;
			 if((year%4==0&&year%100!=0)||year%400==0)
			 flag=1;
			 return flag;
		 }