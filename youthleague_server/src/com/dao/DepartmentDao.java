package com.dao;

import java.util.List;

import com.vo.Department;

public interface DepartmentDao {
	
	//添加
	public boolean add(Department vo);
	//删除
	public boolean delete(int id);
	//修改
	public boolean update(Department vo);
	//分页查询
	public List<Department> query(int page, int pageSize);
	//查看总页数
	public int countDepartment();
	//返回一个对象
	public Department getDepartmentById(int id);
	
	//List
	public List<Department> listDepartment();

}
