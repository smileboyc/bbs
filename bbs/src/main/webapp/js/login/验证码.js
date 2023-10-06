//生成验证码（输入放验证码的标签label）
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
		//判断验证码是否正确（参数1：输入验证码的文本框的ID；参数2：标签的ID）
		function boolyzm(yzm1,label){
			var yzm0=document.getElementById(yzm1).value;
			var label0=document.getElementById(label).innerHTML;
			if(yzm0!=label0){
				alert("验证码输入错误");
				changeCode(label);
			}		
		}