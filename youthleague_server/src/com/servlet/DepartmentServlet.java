package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.dao.imp.DepartmentImp;
import com.dao.imp.UpdateDatabaseImp;
import com.vo.Department;
import com.vo.Staff;

public class DepartmentServlet extends HttpServlet {

	private int pageSize = 10;
	private int page = 1;
	
	private static final long serialVersionUID = -2375131195094399211L;

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
		request.setCharacterEncoding("utf-8");
		String path = request.getContextPath();
		PrintWriter pw = response.getWriter();
		
		DepartmentImp departmentimp = new DepartmentImp();
		UpdateDatabaseImp updateDatabaseImp = new UpdateDatabaseImp();
		
		String type = request.getParameter("type");
		
		System.out.println("操作类型type=" + type);
		
		if ("add".equals(type)) {
			addDepartment(path, pw, departmentimp, updateDatabaseImp, request);
		} else if ("amend".equals(type)) {
			updateDepartment(path, pw, departmentimp, updateDatabaseImp, request);
		} else if ("delete".equals(type)) {
			deleteDepartment(path, pw, departmentimp, updateDatabaseImp, request);
		} else if ("object".equals(type)) {
			try {
				getDepartmentById(path, pw, departmentimp, request);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("nextPage".equals(type)) {
			try {
				getNextPageDepartment(path, request, pw, departmentimp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("list".equals(type)) {
			try {
				getAllDepartment(pw, departmentimp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		pw.flush();
		pw.close();
	}

	private void addDepartment(String path, PrintWriter pw,
			DepartmentImp departmentimp, UpdateDatabaseImp updateDatabaseImp,
			HttpServletRequest request) {
		
		Staff loginStaff = (Staff) request.getSession().getAttribute("Staff");
		if (loginStaff == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path	+ "/login.jsp','_blank');</script>");
			return;
		}
		
		String name = request.getParameter("name");
		String note = request.getParameter("note");
		
		System.out.println("name=" + name + ",note=" + note);
		
		Department department = new Department();
		department.setName(name);
		department.setNote(note);
		
		if(departmentimp.add(department)){
			updateDatabaseImp.updateLastDate();
			System.out.println("添加成功！");
			pw.print("<script>alert('添加成功！'); window.open('" + path + "/admin/department/Department.jsp','_self');</script>");
		}else{
			System.out.println("添加失败！");
			pw.print("<script>alert('添加失败！'); history.go(-1); </script>");
		}
		
	}
	
	private void updateDepartment(String path, PrintWriter pw, DepartmentImp departmentimp,
			UpdateDatabaseImp updateDatabaseImp, HttpServletRequest request) {
		
		Staff loginStaff = (Staff) request.getSession().getAttribute("Staff");
		if (loginStaff == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path	+ "/login.jsp','_blank');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String note = request.getParameter("note");
		
		Department department = new Department();
		department.setId(Integer.valueOf(id));
		department.setName(name);
		department.setNote(note);
		
		if(departmentimp.update(department)){
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('修改成功！'); window.open('" + path + "/admin/department/Department.jsp','_self');</script>");
		}else{
			pw.print("<script>alert('修改失败！'); history.go(-1);</script>");
		}
	}

	private void deleteDepartment(String path, PrintWriter pw,
			DepartmentImp departmentimp, UpdateDatabaseImp updateDatabaseImp,
			HttpServletRequest request) {
		
		Staff loginStaff = (Staff) request.getSession().getAttribute("Staff");
		if (loginStaff == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path	+ "/login.jsp','_blank');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		
		if(departmentimp.delete(Integer.valueOf(id))){
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('删除成功！'); window.open('" + path + "/admin/department/Department.jsp','_self');</script>");
		}else{
			pw.print("<script>alert('删除失败！'); window.open('" + path + "/admin/department/Department.jsp','_self');</script>");
		}
	}
	
	private void getNextPageDepartment(String path, HttpServletRequest request, PrintWriter pw,
			DepartmentImp departmentimp) throws JSONException {
		
		Staff loginStaff = (Staff) request.getSession().getAttribute("Staff");
		if (loginStaff == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path	+ "/login.jsp','_blank');</script>");
			return;
		}
		
		page = Integer.valueOf(request.getParameter("page"));
		pageSize = Integer.valueOf(request.getParameter("pageSize"));
		System.out.println("page=" + page + ", pageSize=" + pageSize);
		
		//使用JSON返回分页
		List<Department> departmentList = departmentimp.query(page, pageSize);
		int count = departmentimp.countDepartment();
		System.out.println("count=" + count);
		
		//总页数公式:(总记录数+页码数-1)/页码数
		int pageCount = (count + pageSize - 1) / pageSize;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageCount", pageCount);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(departmentList);
		jsonObject.put("result", jsonArray);
		pw.print(jsonObject);
		System.out.println("jsonObject=" + jsonObject);
	}

	private void getDepartmentById(String path, PrintWriter pw, DepartmentImp departmentimp,
			HttpServletRequest request) throws JSONException {
		
		Staff loginStaff = (Staff) request.getSession().getAttribute("Staff");
		if (loginStaff == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path	+ "/login.jsp','_blank');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		
		Department department = departmentimp.getDepartmentById(Integer.valueOf(id));
		//使用JSON返回对象
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", department);
		pw.print(jsonObject);
	}
	
	private void getAllDepartment(PrintWriter pw, DepartmentImp departmentimp) throws JSONException {
		//使用JSON返回对象
		JSONObject jsonObject = new JSONObject();
		List<Department> departmentList = departmentimp.listDepartment();
		jsonObject.put("result", departmentList);
		pw.print(jsonObject);
		System.out.println("jsonObject=" + jsonObject);
	}
}
