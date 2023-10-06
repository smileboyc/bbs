		function addfile(){
			var td_photo=document.getElementById("td_photo");
			var photo=document.createElement("input");
		    photo.name="photo";
			photo.type="file";
			td_photo.appendChild(photo);
		}
        function Submit(){
			var studnet_pwd1=document.getElementById("studnet_pwd1").value;
			var studnet_pwd2=document.getElementById("studnet_pwd2").value;
			if(studnet_pwd1==studnet_pwd2)
			document.getElementById("form1").submit();
			else{
				alert("密码不一致，请重新输入")
			}
		}