function loginOut(){
	if(window.confirm("是否退出用户？")){
		$.post("StaffServlet",{type:"exit"},function(data){
			if(data==1){
				if($.browser.msie){ top.window.location.href="../login.jsp";
				}else if($.browser.mozilla){ top.window.location.href="./login.jsp";
				}else{ top.window.location.href="./login.jsp";
				}
			}else{
				alert("退出失败！")
			}
		},type="html");
	//	window.location.href="AdminServlet?flag=loginOut";
	}
}