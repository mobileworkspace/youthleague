package com.dao;

import java.util.List;

import com.vo.Organization;

public interface OrganizationDao {

	// 添加
	public boolean add(Organization vo);

	// 删除
	public boolean delete(int id);

	// 修改
	public boolean amend(Organization vo);

	// 分页查询
	public List<Organization> query(int page, int pageSize);

	// 查看总页数
	public int countOrganization();

	// 返回一个对象
	public Organization getOrganizationById(int id);
	
	public String getOrganizationNameById(int super_id);

	// 获取最大的ID
	// public int MaxId();

	// 返回所有ID
	public List<Organization> getAllOrganization();

	public List<Organization> getSubOrganization();
	
	// 找出本级及其下级机构
	public List<Organization> getAllChildOrganizations(int superId);

}
