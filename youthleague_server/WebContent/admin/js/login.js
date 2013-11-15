
//check用户名
function c_username(){
	var username=$("#username").val();
	var s_username=$("#s_username");
	if(username==""){
		s_username.html("<img src=\"./admin/images/error.png \" /><font color=\"red\">手机号码不能为空！</font>");
	}else{
//		s_username.html("<img src=\"./admin/images/right.png \" />");
		s_username.html("");
	}
}

