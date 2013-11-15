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

		<title>注册员工</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<script src="<%=path%>/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="<%=path%>/admin/staff/js/logonStaff.js" type="text/javascript"></script>
	</head>

	<body>
	
	<center>
	
		<h1>注册员工</h1>
		
		<form action="<%=path%>/StaffServlet?type=logon" method="post">
		
			<table>
				<tr>
					<td>用户名称：</td>
					<td><input type="text" name="name" style="width: 180px"></td>
				</tr>
				<tr>
					<td>手机号码:</td>
					<td><input type="text" name="mobile" id="mobile" style="width: 180px"></td>
				</tr>
				<tr>
					<td>座机号码：</td>
					<td><input type="text" name="phone" id="phone" style="width: 180px"> </td>
				</tr>
				<tr>
					<td>职务:</td>
					<td><select name="position_id" id="position_id" style="width: 180px"></select></td>
				</tr>

				<tr>
					<td>所属科室:</td>
					<td><select name="department_id" id="department_id" style="width: 180px"></select></td>
				</tr>

				<tr>
					<td>所属机构:</td>
					<td><select name="organization_id" id="organization_id" style="width: 180px"></select></td>
				</tr>
				<tr>
					<td>默认密码：</td>
					<td><input type="text" name="password"
						style="width: 180px; color: red;" value="123456"
						onfocus="this.value=''"
						onblur="if(this.value==''){this.value='123456'}">
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value=" 注  册 "> <input type="reset" value=" 重  置 "></td>
				</tr>
			</table>
		</form>

	</center>
</body>
</html>
