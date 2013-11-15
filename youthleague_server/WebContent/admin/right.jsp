<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<link href="admin/images/skin.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #EEF2FB;
}
-->
</style>

</head>

<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="17" valign="top" background="admin/images/mail_leftbg.gif">
				<img src="admin/images/left-top-right.gif" width="17" height="29" />
			</td>
			<td valign="top" background="admin/images/content-bg.gif">
				<table width="100%" height="31" border="0" cellpadding="0"
					cellspacing="0" class="left_topbg" id="table2">
					<tr>
						<td height="31">
							<div class="titlebt">欢迎界面</div>
						</td>
					</tr>
				</table>
			</td>
			<td width="16" valign="top"
				background="admin/images/mail_rightbg.gif"><img
				src="admin/images/nav-right-bg.gif" width="16" height="29" /></td>
		</tr>
		<tr>
			<td valign="middle" background="admin/images/mail_leftbg.gif">
				&nbsp;</td>
			<td valign="top" bgcolor="#F7F8F9">
				<table width="98%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td><img src="admin/images/tuanwei/welcome_bg.jpg" width="1120" height="500"/></td>
					</tr>
				</table>
			</td>


			<td background="admin/images/mail_rightbg.gif">&nbsp;</td>
		</tr>
		<tr>
			<td valign="bottom" background="admin/images/mail_leftbg.gif"><img
				src="admin/images/buttom_left2.gif" width="17" height="17" /></td>
			<td background="admin/images/buttom_bgs.gif"><img
				src="admin/images/buttom_bgs.gif" width="17" height="17"></td>
			<td valign="bottom" background="admin/images/mail_rightbg.gif">
				<img src="admin/images/buttom_right2.gif" width="16" height="17" />
			</td>
		</tr>
	</table>
</body>
</html>
