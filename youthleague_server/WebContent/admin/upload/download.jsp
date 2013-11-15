<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.io.*" %>  
<%
	String basePath = request.getSession().getServletContext().getRealPath("/app_file/");
	String name = request.getParameter("name");

// example:  <a href="download.jsp?path=img/&name=test.gif">download image</a> 

    response.setContentType("application/x-download");//设置为下载application/x-download
 	response.addHeader("content-disposition", "attachment;filename=\"" + name + "\""); 

	OutputStream  os = null;
	FileInputStream fis = null;
	
	try { 
	
	 	os = response.getOutputStream(); 
	    fis = new FileInputStream(basePath + "\\" + name); 
	
	 	byte[] b = new byte[1024]; 
	 	int i = 0; 
	
	 	while ( (i = fis.read(b)) > 0 ) { 
	 		os.write(b, 0, i); 
	 	} 
	
	 	os.flush(); 
	 	
	 	
	 } catch ( Exception e ) { 
	 	e.getStackTrace();
	 } finally{
	 
	 	if(fis!=null){
	 		fis.close();
	 		fis = null;
	 	}
	 	if(os!=null){
	 		os.close();
	 		os = null;
	 	}
	 }
	 
	 out.clear(); 
 	 out = pageContext.pushBody(); 
%>
