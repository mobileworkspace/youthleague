<%@ page language="java" import="java.util.*,com.vo.*"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<base href="<%=basePath%>">
		<%
			Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
			if (staffSession == null) {
				response.sendRedirect(path + "/login.jsp");

			} else {
				if (staffSession.getIs_administrator() == 0) {
					response.sendRedirect(path + "/login.jsp");
				}
			}
		%>
		<title>管理中心</title>
		<meta http-equiv=Content-Type content=text/html;charset=utf-8>
	</head>
	
	<frameset rows="64,*" frameborder="NO" border="0" framespacing="0">
	
		<frame src="admin/admin_top.jsp" noresize="noresize" frameborder="0"
			name="topFrame" scrolling="no" marginwidth="0" marginheight="0"  />
			
		<frameset cols="200,*" rows="560,*" id="frame">
			<frame src="admin/left.html" name="leftFrame" noresize="noresize"
				marginwidth="0" marginheight="0" frameborder="0" scrolling="no"  />
				
			<frame src="admin/right.jsp" name="main" marginwidth="0"
				marginheight="0" frameborder="0" scrolling="auto" />
				
		</frameset>
	</frameset>
	
	<noframes>
		<body></body>
	</noframes>
</html>
