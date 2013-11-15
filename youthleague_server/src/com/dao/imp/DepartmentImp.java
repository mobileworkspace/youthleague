package com.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dao.DepartmentDao;
import com.dbc.Jdbc_driver;
import com.vo.Department;

public class DepartmentImp implements DepartmentDao {
	
	Connection connc;
	PreparedStatement pre;
	ResultSet result;

	public DepartmentImp() {
		connc = Jdbc_driver.Connec();
	}

	// 添加
	public boolean add(Department vo) {

		boolean flag = false;
		
		try {
			pre = connc.prepareStatement("insert into department(name,note) values(?,?)");
			pre.setString(1, vo.getName());
			pre.setString(2, vo.getNote());
			if(pre.executeUpdate()>0){
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}

		return flag;
	}

	// 修改
	public boolean update(Department vo) {
		
		boolean flag = false;
		
		try {
			pre = connc.prepareStatement("update department  set name=?, note=? where id=?");
			pre.setString(1, vo.getName());
			pre.setString(2, vo.getNote());
			pre.setInt(3, vo.getId());
			if(pre.executeUpdate()>0){
				flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally{
//			close();
//		}
		
		return flag;
	}

	// 删除
	public boolean delete(int id) {
		
		boolean flag = false;
		
		try {
			pre = connc.prepareStatement("delete from department where id=?");
			pre.setInt(1, id);
			if(pre.executeUpdate()>0){
				flag = true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		
		return flag;
	}

	// 分页查询
	public List<Department> query(int page, int pageSize) {
		
		List<Department> list = new ArrayList<Department>();
		
		try {
			pre = connc.prepareStatement("select * from department order by id desc limit ?,?");
			pre.setInt(1, (page - 1) * pageSize);
			pre.setInt(2, pageSize);
			result = pre.executeQuery();
			while (result.next()) {
				Department department = new Department();
				department.setId(result.getInt("id"));
				department.setName(result.getString("name"));
				department.setNote(result.getString("note"));
				list.add(department);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		
		return list;
	}

	// 返回总数
	public int countDepartment() {
		
		int count = 0;
		
		try {
			
			pre = connc.prepareStatement("select count(*) from department");
			result = pre.executeQuery();
			while (result.next()) {
				count = result.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		
		return count;
	}

	// 返回一个对象
	public Department getDepartmentById(int id) {
		
		Department department = new Department();

		try {
			pre = connc.prepareStatement("select * from department where id=?");
			pre.setInt(1, id);
			result = pre.executeQuery();
			
			while (result.next()) {
				int idd = result.getInt("id");
				String name = result.getString("name");
				String note = result.getString("note");
				department.setId(idd);
				department.setName(name);
				department.setNote(note);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally{
//			close();
//		}
		
		return department;
	}

	// 返回所有对象
	public List<Department> listDepartment() {
		
		List<Department> list = new ArrayList<Department>();
		
		try {
			
			pre = connc.prepareStatement("select * from department");
			result = pre.executeQuery();
			while (result.next()) {
				Department department = new Department();
				department.setId(result.getInt("id"));
				department.setName(result.getString("name"));
				department.setNote(result.getString("note"));
				list.add(department);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
//		finally{
//			close();
//		}
		
		return list;
	}

	// 关闭资源
	public void close() {
		
		try {
			if (result != null) {
				result.close();
				result = null;
			}
			if (pre != null) {
				pre.close();
				pre = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}