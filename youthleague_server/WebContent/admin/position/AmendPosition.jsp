<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>修改职位信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<script src="<%=path%>/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="<%=path%>/admin/position/js/amendPosition.js" type="text/javascript"></script>
		
	</head>

	<body>
		<center>

			<h1>修改职位信息</h1>
			
			<a href="<%=path%>/admin/position/Position.jsp" style="color: red">查看所有职位</a>
			<p></p>
				
			<form action="<%=path%>/PositionServlet?type=amend" method="post">
			
				<input type="hidden" value='<%=request.getParameter("id")%>' id="idval" name="id" />
			
				<table>

						<tr>
							<td>职务名称：</td>
							<td>
								<input type="text" name="name" id="name" style="width:180px">
							</td>
						</tr>
						
						<tr>
							<td>备注：</td>
							<td>
								<textarea cols="27" rows="5" name="note" id="note" style="width:180px"></textarea>
							</td>
						</tr>
						
						<tr/>
						
						<tr>
							<td></td>
							<td>
								<input type="submit" value="确认修改">
							</td>
						</tr>
						
					</table>
					
				</form>

		</center>
	</body>
</html>
