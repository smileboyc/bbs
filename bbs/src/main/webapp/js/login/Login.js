 //判断电话号码是否正确
		function text2out(T,label) {
					 t2 = document.getElementById(T);
					 ss = /^0*(13|15|18)\d{9}$/;
					 rg = new RegExp(ss);
					 bo = rg.test(t2.value.toString());
					 if (!bo) {
						 document.getElementById(label).innerHTML="电话错误";
						 t2.value = "";
					 }
					 else
					 document.getElementById(label).innerHTML="";
				 }
		 //判断图片文件是否上传
         function givephoto(T) {
             //取得输入框File
             f1 = document.getElementById(T);
                if (f1.value != null) {
                 document.getElementById(T).src =f1.value.toString();
                 document.getElementById(T).style.display = "";  }
             else
                 alert("请先选择照片");
         }
        //判断是否密码符合要求
		function boolpassword(password ,label){
			var password0=document.getElementById(password).value;
			if(password0.length<6){
			
				var lable0=document.getElementById(label).innerHTML='密码少于六位';
			}
			
			else
			var lable0=document.getElementById(label).innerHTML='';
		}
        //判断两次密码是否相同
		function boolpassword2(password1 ,password2,label){
			var password=document.getElementById(password1).value;
			var password0=document.getElementById(password2).value;
			if(password0!=password)
			
			var lable0=document.getElementById(label).innerHTML='两次密码不相同';
			else
			var lable0=document.getElementById(label).innerHTML='';
		}
		//判断用户名
		function boolyhm(yhm, label_yhm){
			if(document.getElementById(yhm).value.length<3)
			{
				
				document.getElementById(label_yhm).innerHTML='不少于3个字符';
			}
			
			else
			document.getElementById(label_yhm).innerHTML='';}
			
			//生成验证码
		function changeCode(label) {
			var arrays = new Array('1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z');
			code = ''; //重新初始化验证码
			//alert(arrays.length);
			//随机从数组中获取四个元素组成验证码
			
			for(var i = 0; i < 4; i++)
			{
				//随机获取一个数组的下标
				var r = parseInt(Math.random() * arrays.length);
				code += arrays[r];
			}
			document.getElementById(label).innerHTML=code;
		}
		//判断验证码是否正确
		function boolyzm(yzm1,label){
			var yzm0=document.getElementById(yzm1).value;
			var label0=document.getElementById(label).innerHTML;
			if(yzm0!=label0){
				alert("验证码输入错误");
				changeCode(label);
			}		
		}
			