package com.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.imp.UpdateDatabaseImp;

public class GetDatabaseVersionServlet extends HttpServlet {

	private static final long serialVersionUID = -340506965136749435L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("type");

		if ("new_dateTime".equals(type)) {

			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();

			String basePath = request.getSession().getServletContext()
					.getRealPath("/db/");
			System.out.println(basePath + "\\sqlite.db");

			UpdateDatabaseImp updateDatabaseImp = new UpdateDatabaseImp();
			String result = updateDatabaseImp.getDbDate();

			File file = new File(basePath);
			if (!file.exists()) {
				result = result + "0";
			} else {

				if (new File(basePath + "\\sqlite.db").exists()) {
					result = result + "1";
				} else {
					result = result + "0";
				}
			}

			pw.write(result);

			pw.close();

		} else if ("get_file".equals(type)) {

			downFile("sqlite.db", request, response);

		} else if ("android_file".equals(type)) {

			downFile("android_app.apk", request, response);

		} else if ("ios_file".equals(type)) {

			downFile("ios.ipa", request, response);
		}

	}

	private void downFile(String stringFileName, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		String basePath = request.getSession().getServletContext()
				.getRealPath("/db/");
		File file = new File(basePath + "\\" + stringFileName);

		// 设置response的编码方式
		response.setContentType("application/x-download");

		// 写明要下载的文件的大小
		response.setContentLength((int) file.length());

		// 读出文件到i/o流
		FileInputStream fis;
		
		try {
			
			fis = new FileInputStream(file);

			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[1024];// 相当于我们的缓存

			long k = 0;// 该值用于计算当前实际下载了多少字节

			// 从response对象中得到输出流,准备下载

			OutputStream myout = response.getOutputStream();

			// 开始循环下载
			while (k < file.length()) {

				int j = buff.read(b, 0, 1024);
				k += j;

				// 将b中的数据写到客户端的内存
				myout.write(b, 0, j);

			}

			// 将写入到客户端的内存的数据,刷新到磁盘
			myout.flush();
			
			myout.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
