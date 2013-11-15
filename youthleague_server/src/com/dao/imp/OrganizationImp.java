package com.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.OrganizationDao;
import com.dbc.Jdbc_driver;
import com.vo.Organization;

public class OrganizationImp implements OrganizationDao {
	
	Connection connc;
	PreparedStatement pre;
	ResultSet result;

	public OrganizationImp() {
		connc = Jdbc_driver.Connec();
	}

	public boolean add(Organization vo) {   //错误
		boolean flag = false;
		try {
			pre = connc.prepareStatement("insert into organization(name,address,super_id) values(?,?,?)");
			pre.setString(1, vo.getName());
			pre.setString(2, vo.getAddress());
			pre.setInt(3, vo.getSuper_id());
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

	public boolean amend(Organization vo) {
		boolean flag = false;
		try {
			pre = connc.prepareStatement("update organization  set name=?,address=?,super_id=? where id=?");
			pre.setString(1, vo.getName());
			pre.setString(2, vo.getAddress());
			pre.setInt(3, Integer.valueOf(vo.getSuper_id()));
			pre.setInt(4, vo.getId());
			int i = pre.executeUpdate();
			if (i > 0) {
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

	public int countOrganization() {
		int count = 0;
		try {
			pre = connc.prepareStatement("select count(*) from organization");
			result = pre.executeQuery();
			while (result.next()){
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

	public boolean delete(int id) {
		boolean flag = false;
		try {
			pre = connc.prepareStatement("delete from organization where id=?");
			pre.setInt(1, id);
			int i = pre.executeUpdate();
			if (i >= 1) {
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

	public Organization getOrganizationById(int id) {
		Organization oepartment = new Organization();
		OrganizationDao organizationDao = new OrganizationImp();
		try {
			pre = connc.prepareStatement("select * from organization where id=?");
			pre.setInt(1, id);
			result = pre.executeQuery();
			while (result.next()) {
				oepartment.setId(result.getInt("id"));
				oepartment.setName(result.getString("name"));
				oepartment.setAddress(result.getString("address"));
				oepartment.setSuper_id(result.getInt("super_id"));
				oepartment.setSuper_name(organizationDao.getOrganizationNameById(result.getInt("super_id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return oepartment;
	}

	public String getOrganizationNameById(int super_id){
		String str = "";
		try {
			pre = connc.prepareStatement("select name from organization where id=?");
			pre.setInt(1, super_id);
			result = pre.executeQuery();
			while (result.next()) {
				str = result.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return str;
	}
	
	public List<Organization> query(int page, int pageSize) {
		List<Organization> list = new ArrayList<Organization>();
		OrganizationDao organizationDao = new OrganizationImp();
		try {
			pre = connc.prepareStatement("SELECT * FROM organization ORDER BY id DESC limit ?,?");
			pre.setInt(1, (page - 1) * pageSize);
			pre.setInt(2, pageSize);
			result = pre.executeQuery();
			while (result.next()) {
				Organization organization = new Organization();
				organization.setId(result.getInt("id"));
				organization.setName(result.getString("name"));
				organization.setAddress(result.getString("address"));
				organization.setSuper_id(result.getInt("super_id"));
				organization.setSuper_name(organizationDao.getOrganizationById(result.getInt("super_id")).getName());
				list.add(organization);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return list;
	}

	// 返回所有id, name
	public List<Organization> getAllOrganization() {
		List<Organization> list = new ArrayList<Organization>();
		try {
			pre = connc.prepareStatement("select * from organization ");
			result = pre.executeQuery();
			while (result.next()) {
				Organization organization = new Organization();
				organization.setName(result.getString("name"));
				organization.setId(result.getInt("id"));
				list.add(organization);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return list;
	}

	// 返回所有id, name(除id为-1的机构)
	public List<Organization> getSubOrganization() {
		List<Organization> list = new ArrayList<Organization>();
		try {
			pre = connc.prepareStatement("select * from organization where id != -1");
			result = pre.executeQuery();
			while (result.next()) {
				Organization organization = new Organization();
				organization.setName(result.getString("name"));
				organization.setId(result.getInt("id"));
				list.add(organization);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return list;
	}
	
	// 返回相关联的部门
	public List<Organization> getAllChildOrganizations(int superId) {
/*		System.out.println("查询相关联的机构->传入的机构: " + superId);
		boolean flag = false;
		String allSuperId = "," + superId;
		System.out.println("初次化的: " + allSuperId);
		List<Organization> oragnization_list = new ArrayList<Organization>();
		try {
			do {
				System.out.println("循环的allSuperId: " + allSuperId);
				allSuperId = allSuperId.substring(1, allSuperId.length());
				pre = connc.prepareStatement("SELECT * FROM organization WHERE super_id in ( " + allSuperId + " )");
				//pre = connc.prepareStatement("SELECT * FROM organization WHERE super_id in ( " + allSuperId + " ) or id in (" + allSuperId + ")");
				result = pre.executeQuery();
				while (result.next()) {
					allSuperId = "";
					flag = true;
					Organization organization = new Organization();
					organization.setId(result.getInt("id"));
					organization.setName(result.getString("name"));
					organization.setAddress(result.getString("address"));
					organization.setSuper_id(result.getInt("super_id"));
					oragnization_list.add(organization);
					allSuperId = allSuperId + "," + organization.getId();
				}
				close();
				if (!flag || oragnization_list.size() == 1) {
					break;
				}
				flag = false;
			} while (true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return oragnization_list;*/
		
		List<Organization> oragnization_list = new ArrayList<Organization>();
		try {
			pre = connc.prepareStatement("SELECT * FROM organization WHERE super_id in ( " + superId + " ) or id in (" + superId + ")");
			result = pre.executeQuery();
			while (result.next()) {
				Organization organization = new Organization();
				organization.setId(result.getInt("id"));
				organization.setName(result.getString("name"));
				organization.setAddress(result.getString("address"));
				organization.setSuper_id(result.getInt("super_id"));
				oragnization_list.add(organization);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		finally {
//			close();
//		}
		return oragnization_list;
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
