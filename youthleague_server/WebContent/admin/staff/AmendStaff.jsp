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

<script src="<%=path%>/admin/staff/js/addStaff.js"
	type="text/javascript"></script>
<script src="<%=path%>/admin/staff/js/amendStaff.js"
	type="text/javascript"></script>
</head>

<body>
	<center>

		<h1>修改员工信息</h1>
		<a href="<%=path%>/admin/staff/Staff.jsp" style="color: red">查看所有员工</a>
		<p>
		
		<form action="<%=path%>/StaffServlet?type=update" method="post">

			<input type="hidden" id="idval"
				value='<%=request.getParameter("id")%>' name="id">

			<table>
				<tr>
					<td>用户名称：</td>
					<td><input type="text" name="name" id="name"
						style="width: 180px" /></td>
				</tr>

				<tr>
					<td>手机号码：</td>
					<td><input type="text" name="mobile" id="mobile"
						style="width: 180px" /></td>
				</tr>
				<tr>
					<td>座机号码：</td>
					<td><input type="text" name="phone" id="phone"
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
					<td>修改密码：</td>
					<td><input type="password" name="password1" id="password1"
						style="width: 180px"></td>
				</tr>

				<tr>
					<td>确认密码：</td>
					<td><input type="password" name="password2" id="password2"
						style="width: 180px"></td>
				</tr>

				<tr>
					<td>是否是管理员:</td>
					<td><select name="is_administrator" id="is_administrator" style="width: 180px">
					</select></td>
				</tr>
				<tr>
					<td>是否已离职:</td>
					<td><select name="is_departure" id="is_departure" style="width: 180px">
					</select></td>
				</tr>

				<tr>
					<td>是否有授权:</td>
					<td><select name="is_warrant" id="is_warrant" style="width: 180px">
					</select></td>
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
