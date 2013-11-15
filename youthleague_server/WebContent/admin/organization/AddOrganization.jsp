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

<title>增加机构</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="<%=path%>/js/jquery-1.7.1.js" type="text/javascript"></script>
<script src="<%=path%>/admin/organization/js/addOrganization.js"
	type="text/javascript"></script>

</head>

<body>
	<center>
		<h1>增加机构</h1>
		<a href="<%=path%>/admin/organization/Organization.jsp"
			style="color: red">查看所有机构</a>
		<p></p>
		<form action="OrganizationServlet?type=add" method="post">
			<table>
				<tr>
					<td>机构名称:</td>
					<td><input type="text" style="width: 180px" name="name"></td>
				</tr>
				<tr>
					<td>机构地址:</td>
					<td><textarea style="width: 180px" rows="5" name="address"></textarea></td>
				</tr>
				<tr>
					<td>上级机构:</td>
					<td><select name="super_id" style="width: 180px" id="option"></select></td>
				</tr>
				<tr />
				<tr>
					<td></td>
					<td><input type="submit" value=" 添 加 " /> <input type="reset"
						value=" 重 置 "></td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>
