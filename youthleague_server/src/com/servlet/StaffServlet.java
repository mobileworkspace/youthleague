package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.dao.imp.StaffImp;
import com.dao.imp.UpdateDatabaseImp;
import com.dbc.Jdbc_driver;
import com.vo.Staff;

public class StaffServlet extends HttpServlet {

	/**
	 * 员工
	 */
	private static final long serialVersionUID = -5666895817936930372L;

	private int pageSize = 10;
	private int page = 1;
	
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
		PrintWriter pw = response.getWriter();
		String path = request.getContextPath();
		
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		
		String type = request.getParameter("type");
		
		StaffImp staffImp = new StaffImp();
		UpdateDatabaseImp updateDatabaseImp = new UpdateDatabaseImp();

		if ("add".equals(type)) {  //管理员使用
			addStaff(request, pw, path, staffImp, updateDatabaseImp);
		} else if ("logon".equals(type)) {   //普通员工使用
			registerStaff(request, pw, path, staffImp, updateDatabaseImp);
		} else if ("nextPage".equals(type)) {
			try {
				getNextPageStaff(request, pw, staffSession, staffImp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("delete".equals(type)) {
			deleteStaff(request, pw, path, staffImp, updateDatabaseImp);
		} else if ("object".equals(type)) {
			try {
				getStaffById(request, pw, staffImp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("update".equals(type)) {   //管理员使用
			updateStaff(request, pw, path, staffImp, updateDatabaseImp);
		} else if ("oneStaffupdate".equals(type)) {  //手机和普通员工共同使用
			updateStaffSelf(request, pw, path, staffImp, updateDatabaseImp);
		} else if ("login".equals(type)) {
			login(request, response, pw, path, staffImp);
		} else if ("exit".equals(type)) {
			logout(request, pw, path);
		}
		
		pw.flush();
		pw.close();
	}


	private void addStaff(HttpServletRequest request, PrintWriter pw,
			String path, StaffImp staffImp, UpdateDatabaseImp updateDatabaseImp) {
		
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");

		String organization_id = request.getParameter("organization_id");
		String department_id = request.getParameter("department_id");
		String position_id = request.getParameter("position_id");

		String is_administrator = request.getParameter("is_administrator");
		String is_departure = request.getParameter("is_departure");
		String is_warrant = request.getParameter("is_warrant");

		String password = request.getParameter("password");
		
		// 判断用户是否存在
		if (staffImp.existMobile(mobile, null)) {
			pw.print("<script>alert('注册失败,该用户已经存在'); history.go(-1); </script>");
		} else {
			Staff staff = new Staff();
			staff.setName(name);
			staff.setMobile(mobile);
			staff.setPhone(phone);
			staff.setPassword(password);
			staff.setDepartment_id(Integer.valueOf(department_id));
			staff.setOrganization_id(Integer.valueOf(organization_id));
			staff.setPosition_id(Integer.valueOf(position_id));
			staff.setIs_administrator(Integer.valueOf(is_administrator));
			staff.setIs_departure(Integer.valueOf(is_departure));
			staff.setIs_warrant(Integer.valueOf(is_warrant));
			if (staffImp.add(staff)) {
				updateDatabaseImp.updateLastDate();
//				pw.print(0);
				pw.print("<script>alert('注册员工成功'); window.open('" + path + "/admin/staff/Staff.jsp','_self');</script>");
			}else{
				pw.print("<script>alert('注册员工失败'); history.go(-1); </script>");
			}

		}
	}

	//仅供普通员工使用
	private void registerStaff(HttpServletRequest request, PrintWriter pw,
			String path, StaffImp staffImp, UpdateDatabaseImp updateDatabaseImp) {
		
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");

		String organization_id = request.getParameter("organization_id");
		String department_id = request.getParameter("department_id");
		String position_id = request.getParameter("position_id");

		String password = request.getParameter("password");
		
		// 判断用户是否存在
		if (staffImp.existMobile(mobile, null)) {
			pw.print("<script>alert('注册失败,该用户已经存在'); history.go(-1); </script>");
		} else {
			Staff staff = new Staff();
			staff.setName(name);
			staff.setMobile(mobile);
			staff.setPhone(phone);
			staff.setPassword(password);
			staff.setDepartment_id(Integer.valueOf(department_id));
			staff.setOrganization_id(Integer.valueOf(organization_id));
			staff.setPosition_id(Integer.valueOf(position_id));
			if (staffImp.logon(staff)) {
				updateDatabaseImp.updateLastDate();
				pw.print("<script>alert('注册员工成功，请等待管理员批准'); history.go(-1); </script>");
			}else{
				pw.print("<script>alert('注册员工失败'); history.go(-1); </script>");
			}
		}
	}
	
	// 更新员工
	private void updateStaff(HttpServletRequest request, PrintWriter pw,
			String path, StaffImp staffImp, UpdateDatabaseImp updateDatabaseImp) {
		
		String id = request.getParameter("id");
		
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");

		String organization_id = request.getParameter("organization_id");
		String department_id = request.getParameter("department_id");
		String position_id = request.getParameter("position_id");

		String is_administrator = request.getParameter("is_administrator");
		String is_departure = request.getParameter("is_departure");
		String is_warrant = request.getParameter("is_warrant");

		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		
		// 判断用户是否存在
		if (staffImp.existMobile(mobile, id)) {
			pw.print("<script>alert('指定的手机号码已被注册'); history.go(-1); </script>");
		}else{
			
			Staff staff = new Staff();
			
			staff.setName(name);
			staff.setMobile(mobile);
			staff.setPhone(phone);
			staff.setDepartment_id(Integer.valueOf(department_id));
			staff.setOrganization_id(Integer.valueOf(organization_id));
			staff.setPosition_id(Integer.valueOf(position_id));
			staff.setIs_administrator(Integer.valueOf(is_administrator));
			staff.setIs_departure(Integer.valueOf(is_departure));
			staff.setIs_warrant(Integer.valueOf(is_warrant));
			staff.setId(Integer.valueOf(id));
			
			if (password1 != null && password2 != null
					&& !"".equals(password1) && !"".equals(password2)) {
				if (password1.equals(password2)) {
					staff.setPassword(password1);
				}
			}
			
			if(staffImp.amend(staff)){
				updateDatabaseImp.updateLastDate();
				pw.print("<script>alert('修改成功！'); window.open('" + path + "/admin/staff/Staff.jsp','_self');</script>");
			}else{
				pw.print("<script>alert('修改失败！');  history.go(-1) </script>");
			}
		}
	}

	//供普通员工和手机端共同使用
	private void updateStaffSelf(HttpServletRequest request, PrintWriter pw,
			String path, StaffImp staffImp, UpdateDatabaseImp updateDatabaseImp) {
		
		String id = request.getParameter("id");
		
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");

		String organization_id = request.getParameter("organization_id");
		String department_id = request.getParameter("department_id");
		String position_id = request.getParameter("position_id");

		String password = request.getParameter("password");
		
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		
		String web = request.getParameter("web");
		if(web==null){
			web = "1";
		}
		
		// 普通员工对自己信息的修改
		try {
			
			if (staffImp.existMobile(mobile, id)) {
				
				if("1".equals(web)){
					pw.print("<script>alert('指定的手机号码已被注册'); history.go(-1); </script>");
				}else{
					pw.print(-1);
				}
				
			}else{
				
				Staff staff = new Staff();
				
				staff.setName(name);
				staff.setMobile(mobile);
				staff.setPhone(phone);
				staff.setDepartment_id(Integer.valueOf(department_id));
				staff.setOrganization_id(Integer.valueOf(organization_id));
				staff.setPosition_id(Integer.valueOf(position_id));
				staff.setId(Integer.valueOf(id));
				
				if (password1 != null && password2 != null && 
						!"".equals(password1) && !"".equals(password2) && 
						password1.equals(password2)) {
					staff.setPassword(password1);
				}else{
					staff.setPassword(password);
				}
				
				if(staffImp.amendByMobile(staff)){
					updateDatabaseImp.updateLastDate();
					
					if("1".equals(web)){
						pw.print("<script>alert('修改成功'); history.go(-1); </script>");
					}else{
						pw.print(0);
					}
					
				}else{
					
					if("1".equals(web)){
						pw.print("<script>alert('修改失败'); history.go(-1); </script>");
					}else{
						pw.print(-2);
					}
				}
			}

		} catch (Exception e) {
			
			if("1".equals(web)){
				pw.print("<script>alert('修改失败'); history.go(-1); </script>");
			}else{
				pw.print(-2);
			}		
		}
	}
	
	private void deleteStaff(HttpServletRequest request, PrintWriter pw,
			String path, StaffImp staffImp, UpdateDatabaseImp updateDatabaseImp) {
		
		String id = request.getParameter("id");
		
		if (staffImp.delete(Integer.valueOf(id))) {
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('删除成功'); window.open('" + path	+ "/admin/staff/Staff.jsp','_self');</script>");
		} else {
			pw.print("<script>alert('删除失败'); window.open('" + path	+ "/admin/staff/Staff.jsp','_self');</script>");
		}
		
	}
	
	private void getStaffById(HttpServletRequest request, PrintWriter pw, StaffImp staffImp) throws JSONException {
		
		String id = request.getParameter("id");
		
		// 查找员工
		Staff staff = staffImp.getStaffById(Integer.valueOf(id));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", staff);
		pw.print(jsonObject);
		
		System.out.println("getStaff()");
		System.out.println(jsonObject.toString());
	}
	
	private void getNextPageStaff(HttpServletRequest request, PrintWriter pw,
			Staff staffSession, StaffImp staffImp) throws JSONException {

		String find_id = request.getParameter("find_id");
		page = Integer.valueOf(request.getParameter("page"));
		pageSize = Integer.valueOf(request.getParameter("pageSize"));
		
		System.out.println("===== 开始返回json =====");
		// 使用JSON返回分页
		List<Staff> staff = null;
		int count = 0;
		if (staffSession != null && staffSession.getIs_warrant() == 1) {
			
			System.out.println("管理员登陆的机构id:" + staffSession.getOrganization_id());
			
			if (staffSession.getOrganization_id() == -1) {
				
				System.out.println("这是超级管理员登陆后查看所有员工");
				
				staff = staffImp.query(page, pageSize, find_id, staffImp
						.getAllStaffByOrganization(staffSession
								.getOrganization_id()), staffSession
						.getMobile());
				count = staffImp.getAllStaff();
				
			} else {
				
				System.out.println("这是普通管理员登陆后查看员工");
				// 根据当前登陆用户的机构返回它的父类机构id
				// organizationDao.getOrganizationById(staffSession.getOrganization_id()).getSuper_id()
				// 获取机构底下的用户
				// staffImp.getAllStaffByOrganization(organizationDao.getOrganizationById(staffSession.getOrganization_id()).getSuper_id())
				staff = staffImp.query(page, pageSize, find_id, staffImp
						.getAllStaffByOrganization(staffSession
								.getOrganization_id()), staffSession
						.getMobile());
				count = staffImp.getAllStaffByOrganization(staffSession
								.getOrganization_id()).size();
			}
			
			// 总页数公式:(总记录数+页码数-1)/页码数
			int pageCount = (count + pageSize - 1) / pageSize;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("pageCount", pageCount);
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(staff);
			jsonObject.put("result", jsonArray);
			System.out.println(jsonObject.toString());
			pw.print(jsonObject);
		}
	}

	private void login(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, String path,
			StaffImp staffImp){
		
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		
		// 员工登陆
		Staff staff = staffImp.login(mobile, password);
		
		if (staff == null) {
			pw.print("<script>alert('登录失败，用户不存在或密码错误');window.open('"
					+ path + "/login.jsp','_self');</script>");
		} else {
			
			request.getSession().setAttribute("Staff", staff);
			
			if (staff.getIs_administrator() == 0) { // 一般用户登录
				
				System.out.println("===== 已授权登录，显示个人信息，但不是管理员 =====");
				try {
//					request.setAttribute("id", staff.getId());
					request.getRequestDispatcher("admin/staff/OneStaff.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (staff.getIs_administrator() == 1 && staff.getMobile().equals("admin")) { // 超级管理员
				
				System.out.println("===== 超级管理员登陆 =====");
				pw.print("<script>window.open('" + path + "/admin/home.jsp','_self');</script>");
				
			} else {
				System.out.println("===== 已授权的管理员，显示本机构下所有员工 =====");
				pw.print("<script>window.open('" + path + "/admin/home.jsp','_self');</script>");
			}
		}
	}

	private void logout(HttpServletRequest request, PrintWriter PW, String path) {
		try {
			Jdbc_driver.Connec().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().removeAttribute("Staff");
//		PW.print("<script> window.open('" + path + "/login.jsp','_self');</script>");
		PW.print("1");
	}
}
