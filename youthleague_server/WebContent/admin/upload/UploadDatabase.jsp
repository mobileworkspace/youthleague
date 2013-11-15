<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'uploadDatabase.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=path%>/js/jquery-1.7.1.js" type="text/javascript"></script>
		
	</head>

	<body>
       <form name="myform" action="UploadDatebaseFileServlet" method="post" enctype="multipart/form-data">
                                 请选择文件: 
             <input type="file" name="myfile" style="width:250px"><br>
             <p>
             <input type="submit" name="submit" value=" 上 传 ">
        </form>
	</body>
	
</html>
