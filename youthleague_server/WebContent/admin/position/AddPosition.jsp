<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

		<title>增加职位信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
		-->

	</head>

	<body>
		<center>
		
			<h1>增加职位</h1>
			
			<a href="<%=path%>/admin/position/Position.jsp" style="color: red">查看所有职位</a>
			<p>
			
			<form action="PositionServlet?type=add" method="post">
			
				<table>
				
					<tr>
						<td>职务名称：</td>
						<td><input type="text" name="name" style="width:180px"></td>
					</tr>
					
					<tr>
						<td>备注：</td>
						<td><textarea rows="5" name="note" style="width:180px"></textarea></td>
					</tr>
					
					<tr/>
					<tr/>
					<tr>
						<td></td>
						<td>
							<input type="submit" value=" 添 加 " />
							<input type="reset" value=" 重 置 ">
						</td>
					</tr>
				</table>
			</form>
			
		</center>
	</body>
</html>
