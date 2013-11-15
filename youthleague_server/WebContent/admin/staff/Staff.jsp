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

<title>员工信息列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />

<script src="<%=path%>/js/jquery-1.7.1.js" type="text/javascript"></script>
<script src="<%=path%>/js/jquery.myPagination.js" type="text/javascript"></script>
<script src="<%=path%>/admin/staff/js/staff.js" type="text/javascript"></script>
</head>

<body>
	<center>
		<input type="hidden" id="id" value="<%=request.getParameter("id")%>" />
		<h1>所有员工信息</h1>
		<form action="<%=path%>/admin/staff/Staff.jsp" method="post">
			查找员工： <input type="text" name="id" placeholder="可以根据用户名称/手机号码/座机号码进行模糊查询" size="50px" /> 
			<input type="submit" value="查找" /> 
			<input type="button" value="显示所有" onclick="show_all()" />
		</form>
		<a href="<%=path%>/admin/staff/AddStaff.jsp" style="color: red">点击添加员工</a>
		<p></p>
		<table border="1" align="center">
			<tr align="center">
				<th>用户名称</th>
				<th>手机号码</th>
				<th>座机号码</th>
				<th>所属机构</th>
				<th>所属科室</th>
				<th>职务</th>
				<th>管理员</th>
				<th>离职</th>
				<th>授权</th>
				<th>修改</th>
				<th>删除</th>
			</tr>
			<tbody id="content" align="center">
			</tbody>
		</table>
		<div id="paging"></div>
	</center>
</body>
</html>
