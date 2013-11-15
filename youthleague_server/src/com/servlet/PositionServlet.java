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

import com.dao.imp.PositionImp;
import com.dao.imp.UpdateDatabaseImp;
import com.vo.Position;
import com.vo.Staff;

public class PositionServlet extends HttpServlet {

	private int pageSize = 10;
	private int page = 1;
	
	private static final long serialVersionUID = -9223346672528038174L;

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
		
		PositionImp positionImp = new PositionImp();
		UpdateDatabaseImp updateDatabaseImp = new UpdateDatabaseImp();
		
		String type = request.getParameter("type");
		
		if ("add".equals(type)) {
			addPosition(request, path, pw, positionImp, updateDatabaseImp);
		} else if ("nextPage".equals(type)) {
			try {
				getNextPagePosition(request, path, pw, positionImp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("delete".equals(type)) {
			deletePosition(request, path, pw, positionImp, updateDatabaseImp);
		} else if ("object".equals(type)) {
			try {
				getPositionById(request, path, pw, positionImp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("amend".equals(type)) {
			updatePosition(request, path, pw, positionImp, updateDatabaseImp);
		} else if ("list".equals(type)) {
			try {
				getAllPosition(pw, positionImp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		pw.flush();
		pw.close();
	}

	private void addPosition(HttpServletRequest request, String path,
			PrintWriter pw, PositionImp positionImp,
			UpdateDatabaseImp updateDatabaseImp) {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path + "/login.jsp','_blank');</script>");
			return;
		}
		
		String name = request.getParameter("name");
		String note = request.getParameter("note");
		
		Position position = new Position();
		position.setName(name);
		position.setNote(note);
		
		if(positionImp.add(position)){
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('添加成功！'); window.open('" + path + "/admin/position/Position.jsp','_self');</script>");
		}else{
			pw.print("<script>alert('添加失败！'); history.go(-1); </script>");
		}
		
	}
	
	private void updatePosition(HttpServletRequest request, String path,
			PrintWriter pw, PositionImp positionImp,
			UpdateDatabaseImp updateDatabaseImp) {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path + "/login.jsp','_blank');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String note = request.getParameter("note");
		
		Position Position = new Position();
		Position.setId(Integer.valueOf(id));
		Position.setName(name);
		Position.setNote(note);
		
		if(positionImp.amend(Position)){
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('修改成功！'); window.open('" + path + "/admin/position/Position.jsp','_self');</script>");
		}else{
			pw.print("<script>alert('修改失败！'); history.go(-1)</script>");
		}
		
	}

	private void deletePosition(HttpServletRequest request, String path,
			PrintWriter pw, PositionImp positionImp,
			UpdateDatabaseImp updateDatabaseImp) {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path
					+ "/login.jsp','_self');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		
		if(positionImp.delete(Integer.valueOf(id))){
			updateDatabaseImp.updateLastDate();
			pw.print("<script>alert('删除成功！'); window.open('" + path + "/admin/position/Position.jsp','_self');</script>");
		}else{
			pw.print("<script>alert('删除失败！'); window.open('" + path + "/admin/position/Position.jsp','_self');</script>");
		}
		
	}
	
	private void getPositionById(HttpServletRequest request, String path,
			PrintWriter pw, PositionImp positionImp) throws JSONException {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path + "/login.jsp','_blank');</script>");
			return;
		}
		
		String id = request.getParameter("id");
		Position position = positionImp.getPositionById(Integer.valueOf(id));
		
		//使用JSON返回对象
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", position);
		pw.print(jsonObject);
	}

	private void getNextPagePosition(HttpServletRequest request, String path,
			PrintWriter pw, PositionImp positionImp) throws JSONException {
		
		//判断session是否为空(判断用户是否空)
		Staff staffSession = (Staff) request.getSession().getAttribute("Staff");
		if (staffSession == null) {
			pw.print("<script>alert('请先登录系统');window.open('" + path
					+ "/login.jsp','_self');</script>");
			return;
		}
		
		page = Integer.valueOf(request.getParameter("page"));
		pageSize = Integer.valueOf(request.getParameter("pageSize"));
		
		//使用JSON返回分页
		List<Position> positionLists = positionImp.query(page, pageSize);
		int count = positionImp.countPosition();
		
		//总页数公式:(总记录数+页码数-1)/页码数
		int pageCount = (count + pageSize - 1) / pageSize;
		JSONObject jsonObjects = new JSONObject();
		jsonObjects.put("pageCount", pageCount);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(positionLists);
		jsonObjects.put("result", jsonArray);
		pw.print(jsonObjects);
	}

	private void getAllPosition(PrintWriter pw, PositionImp positionImp) throws JSONException {
		//使用JSON返回对象
		JSONObject jsonObject = new JSONObject();
		List<Position> positionList = positionImp.ListPosition();
		jsonObject.put("result", positionList);
		pw.print(jsonObject);
	}
}
