<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

		<title>所有科室</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		
		<script src="<%=path%>/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="<%=path%>/js/jquery.myPagination.js" type="text/javascript"></script>
		<script src="<%=path%>/admin/department/js/department.js" type="text/javascript"></script>

	

	</head>

	<body>
		<center>


			<!--  显示所有科室-->
			<h1>--------显示所有科室------</h1>
			
			<a href="<%=path%>/admin/department/AddDepartment.jsp" style="color: red">点击增加科室</a>
			<p>
			
			<table border="1" align="center" width="500px">
				<tr align="center">
					<th>科室名称</th>
					<th> 备 注 </th>
					<th> 修 改 </th>
					<th> 删 除 </th>
				</tr>
				<tbody id="content" align="center">
				</tbody>
			</table>
			<div id="paging"></div>
		</center>
	</body>
</html>
