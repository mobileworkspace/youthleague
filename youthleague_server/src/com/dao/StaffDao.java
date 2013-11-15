package com.dao;

import java.util.List;

import com.vo.Staff;

public interface StaffDao {

	// 添加
	public boolean add(Staff vo);

	// 删除
	public boolean delete(int id);

	// 修改
	public boolean amend(Staff vo);

	// 分页查询
	public List<Staff> query(int page, int pageSize, String type,
			List<Staff> oranzition_list_staff, String phone);

	// 查看总页数
	public int countStaff(List<Staff> oranzition_list_staff);

	// 返回一个对象
	public Staff getStaffById(int id);

	// max(id)
	public int maxId(String phone, String mobile, String name);

	// 查看电话号码是否存在
	public boolean existMobile(String mobile, String id);

	// 登陆
	public Staff login(String mobile, String password);

	// 返回所有机构相关联的员工
	public List<Staff> getAllStaffByOrganization(int superId);

}
