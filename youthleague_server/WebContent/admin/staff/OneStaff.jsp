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

		<title>修改员工信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<script src="<%=path%>/js/jquery-1.7.1.js" type="text/javascript"></script>
		<script src="<%=path%>/admin/staff/js/logonStaff.js" type="text/javascript"></script>
		<script src="<%=path%>/admin/staff/js/oneStaff.js" type="text/javascript"></script>
	</head>

	<body>
	<center>

		<h1>修改员工信息</h1>
		<p></p>
		<form action="<%=path%>/StaffServlet?type=oneStaffupdate" method="post">

			<input type="hidden" id="idval"	value='${Staff.id}' name="id">
			<input type="hidden" value='${Staff.is_administrator}' name="is_administrator">
			<input type="hidden" value='${Staff.is_departure}' name="is_departure">
			<input type="hidden" value='${Staff.is_warrant}' name="is_warrant">
			<input type="hidden" value='${Staff.password}' name="password">

			<table>
				<tr>
					<td>用户名称：</td>
					<td><input type="text" name="name" id="name" value="${Staff.name}"
						style="width: 180px" /></td>
				</tr>

				<tr>
					<td>手机号码：</td>
					<td><input type="text" name="mobile" id="mobile" value="${Staff.mobile}"
						style="width: 180px" /></td>
				</tr>
				<tr>
					<td>座机号码：</td>
					<td><input type="text" name="phone" id="phone" value="${Staff.phone}"
						style="width: 180px" /></td>
				</tr>

				<tr>
					<td>职务:</td>
					<td><select name="position_id" id="position_id"
						style="width: 180px"></select></td>
				</tr>

				<tr>
					<td>所属科室:</td>
					<td><select name="department_id" id="department_id"
						style="width: 180px"></select></td>
				</tr>

				<tr>
					<td>所属机构:</td>
					<td><select name="organization_id" id="organization_id"
						style="width: 180px"></select></td>
				</tr>
				
				<tr>
					<td>原始密码:</td>
					<td><span style="color: red;">${Staff.password}</span></td>
				</tr>
				
				<tr>
					<td>修改密码：</td>
					<td><input type="password" name="password1" id="password1" style="width: 180px"></td>
				</tr>

				<tr>
					<td>确认密码：</td>
					<td><input type="password" name="password2" id="password2" style="width: 180px"></td>
				</tr>

				<tr />

				<tr>
					<td></td>
					<td><input type="submit" value=" 修 改  "
						onclick="return checkPassword()"> <input type="reset"
						value=" 重 置 "></td>
				</tr>
				
			</table>
		</form>
	</center>
</body>
</html>
