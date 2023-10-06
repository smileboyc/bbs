//后台JSON数据传到前台通过表格显示（参数1：div的ID；参数2：JSON数据）
function displayJsonToTable(divName,data){

				var table1=document.createElement('table');	
				table1.style.border="1px red solid";
				table1.style.width="90%";
				table1.style.padding="5px";
				table1.style.borderCollapse="collapse";
				//准备测试data的行列数
				var rows=data.length;//最后完工还要考虑data是空的
				var keys=Object.keys(data[0]);
				var cols=keys.length;
        for(var r=0;r<rows;r++){//处理一行
			var tr1=document.createElement('tr');
			for(var c=0;c<cols;c++){
				var td1=document.createElement('td');
				td1.id="td"+r.toString().trim()+c.toString().trim();
				td1.innerText=data[r][keys[c]];
				td1.style.border="1px solid black";
				tr1.appendChild(td1);
			}
			table1.appendChild(tr1);
		}
			document.getElementById(divName).appendChild(table1)
}