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

import com.dao.imp.OrganizationImp;
import com.dao.imp.UpdateDatabaseImp;
import com.vo.Organization;
import com.vo.Staff;

public class OrganizationServlet extends HttpServlet {

	private int pageSize = 10;
	private int page = 1;

	private static final long serialVersionUID = -7875044675779879213L;

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
		
		OrganizationImp organizationImp = new OrganizationImp();
		UpdateDatabaseImp updateDatabaseImp = new UpdateDatabaseImp();
		
		String type = request.getParameter("type");
		
		if ("add".equals(type)) {
			addOrganization(pw, path, organizationImp, updateDatabaseImp, request);
		}  else if ("amend".equals(type)) {
			updateOrganization(pw, path, organizationImp, updateDatabaseImp, request);
		} else if ("delete".equals(type)) {
			deleteOrganization(pw, path, organizationImp, updateDatabaseImp, request);
		} else if ("object".equals(type)) {
			try {
				getOrganizationById(pw, path, organizationImp, request);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("nextPage".equals(type)) {
			try {
				getNextPage(request, path, pw, organizationImp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("list".equals(type)) {
			try {
				getSubOrganization(pw, organizationImp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("list_all".equals(type)) {
			try {
				getAllOrganization(pw, organizationImp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		pw.flush();
		pw.close();
	}

	private void addOrganization(PrintWriter pw, String path, OrganizationImp organizationImp,
			UpdateDatabaseImp updateDatabaseImp, HttpServletRequest request) {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path + "/login.jsp','_blank');</script>");
			return;
		}
		
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String super_id = request.getParameter("super_id");
		
		Organization organization = new Organization();
		organization.setName(name);
		organization.setAddress(address);
		organization.setSuper_id(Integer.valueOf(super_id));
		if(organizationImp.add(organization)){
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('添加成功!'); window.open('" + path + "/admin/organization/Organization.jsp','_self');</script>");
		}else{
			pw.print("<script>alert('删除失败!'); history.go(-1);</script>");
		}
	}
	
	private void updateOrganization(PrintWriter pw, String path,
			OrganizationImp organizationImp,
			UpdateDatabaseImp updateDatabaseImp, HttpServletRequest request) {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path + "/login.jsp','_blank');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String super_id = request.getParameter("super_id");
		
		Organization organization = new Organization();
		organization.setId(Integer.valueOf(id));
		organization.setName(name);
		organization.setAddress(address);
		organization.setSuper_id(Integer.valueOf(super_id));
		if(organizationImp.amend(organization)){
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('修改成功！'); window.open('" + path + "/admin/organization/Organization.jsp','_self');</script>");
		}else{
			pw.print("<script>alert('修改失败！'); history.go(-1); </script>");
		}
	}
	
	private void deleteOrganization(PrintWriter pw, String path,
			OrganizationImp organizationImp,
			UpdateDatabaseImp updateDatabaseImp, HttpServletRequest request) {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path + "/login.jsp','_blank');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		
		if (organizationImp.delete(Integer.valueOf(id))) {
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('删除成功！'); window.open('" + path + "/admin/organization/Organization.jsp','_self');</script>");
		} else {
			pw.print("<script>alert('删除失败，有员工关联！'); window.open('" + path + "/admin/organization/Organization.jsp','_self');</script>");
		}
	}
	
	private void getOrganizationById(PrintWriter pw, String path,
			OrganizationImp organizationImp, HttpServletRequest request) throws JSONException {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path + "/login.jsp','_blank');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		Organization organization = organizationImp.getOrganizationById(Integer.valueOf(id));
		// 使用JSON返回对象
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", organization);
		System.out.println(jsonObject.toString());
		pw.print(jsonObject);
	}
	
	private void getNextPage(HttpServletRequest request, String path, PrintWriter pw,
			OrganizationImp organizationImp) throws JSONException {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path + "/login.jsp','_blank');</script>");
			return;
		}
		
		page = Integer.valueOf(request.getParameter("page"));
		pageSize = Integer.valueOf(request.getParameter("pageSize"));
		
		// 使用JSON返回分页
		List<Organization> organizations = organizationImp.query(page, pageSize);
		int count = organizationImp.countOrganization();
		
		// 总页数公式:(总记录数+页码数-1)/页码数
		int pageCount = (count + pageSize - 1) / pageSize;
		JSONObject jsonObjects = new JSONObject();
		jsonObjects.put("pageCount", pageCount);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(organizations);
		jsonObjects.put("result", jsonArray);
		pw.print(jsonObjects);
	}

	private void getAllOrganization(PrintWriter pw,
			OrganizationImp organizationImp) throws JSONException {
		
		List<Organization> listId = organizationImp.getAllOrganization();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", listId);
		pw.print(jsonObject);
	}

	private void getSubOrganization(PrintWriter pw,
			OrganizationImp organizationImp) throws JSONException {
		
		List<Organization> listId = organizationImp.getSubOrganization();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", listId);
		pw.print(jsonObject);
	}
	
}
