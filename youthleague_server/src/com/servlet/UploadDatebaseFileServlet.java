package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dao.imp.UpdateDatabaseImp;

public class UploadDatebaseFileServlet extends HttpServlet {

	private static final long serialVersionUID = -8496590443792929638L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		String basePath = request.getSession().getServletContext().getRealPath("/db/");
		String path = request.getContextPath();
		
		PrintWriter pw = response.getWriter();
		
		UpdateDatabaseImp updateDatabaseImp = new UpdateDatabaseImp();
		String oldDbDate = updateDatabaseImp.getDbDate();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			Date lastDate = dateFormat.parse(oldDbDate);
			Calendar calender = Calendar.getInstance();
			calender.setTime(lastDate);
			
			Calendar calender2 = Calendar.getInstance();
			calender2.clear(Calendar.SECOND);
			
			if(calender.after(calender2)){
				printErrorInfo(pw, path, "数据库文件已经是最新版本，不需要上传!");
				return;
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File(basePath);
		if (!file.exists()) {
			if(!file.mkdirs()){
				printErrorInfo(pw, path, "上传失败");
				return;
			}
		}
		
		try {
			
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// Set factory constraints
			factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
			factory.setRepository(file);// 设置缓冲区目录
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// Set overall request size constraint
			upload.setSizeMax(8194304); // 设置最大文件尺寸，这里是4MB
			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			
			if(items!=null && items.size()>0){
				
				Iterator<FileItem> i = items.iterator();
				
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					String fileName = fi.getName();
					if (fileName != null) {
						File savedFile = new File(basePath, fi.getName());
						File destFile = new File(basePath, "sqlite.db");
						fi.write(savedFile);
						
						if(destFile.exists()){
							if(destFile.delete()){
								
							}
						}
						
						if(!savedFile.renameTo(destFile)){
							printErrorInfo(pw, path, "上传失败");
						}else{
							break;
						}
					}
				}
				
				printErrorInfo(pw, path, "上传成功");
				
				updateDatabaseImp.updateDbDate();
				
			}else{
				printErrorInfo(pw, path, "没有指定上传的文件");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			printErrorInfo(pw, path, "上传失败");
		}
		
	
			 
	}
	

	private void printErrorInfo(PrintWriter PW, String path, String info){
		
		PW.print("<script>alert('" + info + "');window.open('" + path
				+ "/admin/right.jsp','_self');</script>");
		PW.flush();
		PW.close();
	}
}