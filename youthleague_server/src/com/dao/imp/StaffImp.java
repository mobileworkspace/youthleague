package com.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.DepartmentDao;
import com.dao.OrganizationDao;
import com.dao.PositionDao;
import com.dao.StaffDao;
import com.dbc.Jdbc_driver;
import com.vo.Organization;
import com.vo.Staff;

public class StaffImp implements StaffDao {

	Connection connc;
	PreparedStatement pre;
	ResultSet result;
	DepartmentDao departmentDao = new DepartmentImp();
	OrganizationDao organizationDao = new OrganizationImp();
	PositionDao positionDao = new PositionImp();

	public StaffImp() {
		connc = Jdbc_driver.Connec();
	}

	public boolean add(Staff vo) {
		boolean flag = false;
		try {
			pre = connc
					.prepareStatement("insert into staff(name,mobile,phone,organization_id,department_id,position_id,is_administrator,is_departure,is_warrant,password_) values(?,?,?,?,?,?,?,?,?,?)");
			pre.setString(1, vo.getName());
			pre.setString(2, vo.getMobile());
			pre.setString(3, vo.getPhone());
			pre.setInt(4, vo.getOrganization_id());
			pre.setInt(5, vo.getDepartment_id());
			pre.setInt(6, vo.getPosition_id());
			pre.setInt(7, vo.getIs_administrator());
			pre.setInt(8, vo.getIs_departure());
			pre.setInt(9, vo.getIs_warrant());
			pre.setString(10, vo.getPassword());
			if (pre.executeUpdate() > 0) {
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

	public boolean logon(Staff vo) {
		boolean flag = false;
		try {
			pre = connc
					.prepareStatement("insert into staff(name,mobile,phone,organization_id,department_id,position_id,is_administrator,is_departure,is_warrant,password_) values(?,?,?,?,?,?,?,?,?,?)");
			pre.setString(1, vo.getName());
			pre.setString(2, vo.getMobile());
			pre.setString(3, vo.getPhone());
			pre.setInt(4, vo.getOrganization_id());
			pre.setInt(5, vo.getDepartment_id());
			pre.setInt(6, vo.getPosition_id());
			pre.setInt(7, vo.getIs_administrator());
			pre.setInt(8, vo.getIs_departure());
			pre.setInt(9, vo.getIs_warrant());
			pre.setString(10, vo.getPassword());
			if (pre.executeUpdate() > 0) {
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

	public boolean amend(Staff staff) {
		System.out.println("来到修改Servlet=======" + staff.getIs_warrant());
		boolean flag = false;
		try {
			if (staff.getPassword() != null) {
				pre = connc
						.prepareStatement("update staff  set mobile=?,phone=?,organization_id=?,department_id=?,position_id=?,is_administrator=?,is_leader=?,is_hipe=?,is_departure=?,is_warrant=?,name=?,password_=? where id=?");
				pre.setString(12, staff.getPassword());
				pre.setInt(13, staff.getId());
			} else {
				pre = connc
						.prepareStatement("update staff  set mobile=?,phone=?,organization_id=?,department_id=?,position_id=?,is_administrator=?,is_leader=?,is_hipe=?,is_departure=?,is_warrant=?,name=? where id=?");
				pre.setInt(12, staff.getId());
			}
			pre.setString(1, staff.getMobile());
			pre.setString(2, staff.getPhone());
			pre.setInt(3, staff.getOrganization_id());
			pre.setInt(4, staff.getDepartment_id());
			pre.setInt(5, staff.getPosition_id());
			pre.setInt(6, staff.getIs_administrator());
			pre.setInt(7, staff.getIs_leader());
			pre.setInt(8, staff.getIs_hipe());
			pre.setInt(9, staff.getIs_departure());
			pre.setInt(10, staff.getIs_warrant());
			pre.setString(11, staff.getName());
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

	public boolean amendByMobile(Staff staff) {
		System.out.println("来到修改Servlet=======" + staff.getIs_warrant());
		boolean flag = false;
		try {

			pre = connc
					.prepareStatement("update staff  set mobile=?,phone=?,organization_id=?,department_id=?,position_id=?,name=?,password_=? where id=?");
			pre.setString(7, staff.getPassword());
			pre.setInt(8, staff.getId());

			pre.setString(1, staff.getMobile());
			pre.setString(2, staff.getPhone());
			pre.setInt(3, staff.getOrganization_id());
			pre.setInt(4, staff.getDepartment_id());
			pre.setInt(5, staff.getPosition_id());
			pre.setString(6, staff.getName());
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
	
	public int countStaff(List<Staff> oranzition_list_staff) {
		// int count = 0;
		try {
			pre = connc.prepareStatement("select count(*) from staff");
			result = pre.executeQuery();
			while (result.next()) {
				// count = result.getInt(1);
			}
		} catch (Exception e) {
		} 
//		finally {
//			close();
//		}
		return oranzition_list_staff.size();
	}

	public boolean delete(int id) {
		boolean flag = false;
		try {
			pre = connc
					.prepareStatement("update staff set is_departure=1 where id=?");
			pre.setInt(1, id);
			if (pre.executeUpdate() > 0) {
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

	public Staff getStaffById(int id) {
		Staff staff = new Staff();
		try {
			pre = connc.prepareStatement("select * from staff where id=?");
			pre.setInt(1, Integer.valueOf(id));
			result = pre.executeQuery();
			while (result.next()) {
				staff.setId(id);
				staff.setMobile(result.getString("mobile"));
				staff.setPhone(result.getString("phone"));
				staff.setOrganization_id(result.getInt("organization_id"));
				staff.setDepartment_id(result.getInt("department_id"));
				staff.setPosition_id(result.getInt("position_id"));
				
				staff.setDepartment_Name(departmentDao.getDepartmentById(
						staff.getDepartment_id()).getName());
				staff.setOrganization_Name(organizationDao.getOrganizationById(
						staff.getOrganization_id()).getName());
				staff.setPosition_Name(positionDao.getPositionById(
						staff.getPosition_id()).getName());
				
				staff.setIs_administrator(result.getInt("is_administrator"));
				staff.setIs_departure(result.getInt("is_departure"));
				staff.setIs_warrant(result.getInt("is_warrant"));
				staff.setPassword(result.getString("password_"));
				staff.setName(result.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return staff;
	}

	public List<Staff> query(int page, int pageSize, String type,
			List<Staff> oranzition_list_staff, String mobile) {
		List<Staff> list = new ArrayList<Staff>();
		String oranzition_list_staff_id = "";
		for (Staff list_staff_id : oranzition_list_staff) {
			oranzition_list_staff_id += list_staff_id.getId() + ",";
		}
		try {
			if ("null".equals(type) || type == null || "".equals(type)) {
				// 规定是admin用户名的登陆---超级管理员
				if (mobile.equals("admin")) {
					pre = connc
							.prepareStatement("select * from staff where is_departure='0' and organization_id !=-1 "
									+ "order by id desc limit ?,?");
				} else {
				// 一般管理员登陆
					System.out.println("返回符合条件的员工id: " + oranzition_list_staff_id);
					oranzition_list_staff_id = oranzition_list_staff_id
							.substring(0, oranzition_list_staff_id.length() - 1);
					pre = connc
							.prepareStatement("select * from staff where id in ("
									+ oranzition_list_staff_id
									+ ") order by id desc limit ?,?");
				}
				pre.setInt(1, (page - 1) * pageSize);
				pre.setInt(2, pageSize);
			} else {
				// 查询请求
				if (mobile.equals("admin")) {
					pre = connc
							.prepareStatement("select * from staff where is_departure='0' and organization_id !=-1 and mobile LIKE '%"
									+ type
									+ "%' OR phone LIKE '%"
									+ type
									+ "%' OR name LIKE '%"
									+ type
									+ "%' order by id desc ");
				} else {
					System.out.println("非admin管理员查询");
/*					oranzition_list_staff_id = oranzition_list_staff_id
							.substring(0, oranzition_list_staff_id.length() - 1);
					pre = connc
							.prepareStatement("select * from staff where id=? and id in ("
									+ oranzition_list_staff_id + ")");
					pre.setInt(1, Integer.valueOf(type));*/
					System.out.println("模糊查询符合条件的id" + oranzition_list_staff_id);
					String code = oranzition_list_staff_id.substring(0, oranzition_list_staff_id.length()-1);
					System.out.println(code);
					pre = connc
							.prepareStatement("select * from staff where id in (" + code + ") and is_departure='0' and organization_id !=-1 and (mobile LIKE '%"
									+ type
									+ "%' OR phone LIKE '%"
									+ type
									+ "%' OR name LIKE '%"
									+ type
									+ "%') order by id desc ");
				}
			}
			result = pre.executeQuery();
			while (result.next()) {
				Staff staff = new Staff();
				staff.setId(result.getInt("id"));
				staff.setMobile(result.getString("mobile"));
				staff.setPhone(result.getString("phone"));
				staff.setOrganization_id(result.getInt("organization_id"));
				staff.setDepartment_id(result.getInt("department_id"));
				staff.setPosition_id(result.getInt("position_id"));

				staff.setDepartment_Name(departmentDao.getDepartmentById(
						staff.getDepartment_id()).getName());
				staff.setOrganization_Name(organizationDao.getOrganizationById(
						staff.getOrganization_id()).getName());
				staff.setPosition_Name(positionDao.getPositionById(
						staff.getPosition_id()).getName());
				
				staff.setIs_administrator(result.getInt("is_administrator"));
				staff.setIs_leader(result.getInt("is_leader"));
				staff.setIs_hipe(result.getInt("is_hipe"));
				staff.setIs_departure(result.getInt("is_departure"));
				staff.setIs_warrant(result.getInt("is_warrant"));
				staff.setPassword(result.getString("password_"));
				staff.setName(result.getString("name"));
				list.add(staff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return list;
	}

	public int maxId(String phone, String mobile, String name) {
		int max = 0;
		try {
			pre = connc
					.prepareStatement("select id from staff where phone=? and mobile=? and name=? and is_departure='0'");
			pre.setString(1, phone);
			pre.setString(2, mobile);
			pre.setString(3, name);
			result = pre.executeQuery();
			while (result.next()) {
				max = result.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return max;
	}

	public boolean existMobile(String mobile, String id) {
		boolean flag = false;
		try {
			
			if(id==null){
				pre = connc.prepareStatement("select * from staff where mobile=? and is_departure='0'");
				pre.setString(1, mobile);
			}else{
				pre = connc.prepareStatement("select * from staff where mobile=? and is_departure='0' and id != ? ");
				pre.setString(1, mobile);
				pre.setString(2, id);
			}
			
			result = pre.executeQuery();
			while (result.next()) {
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

	public Staff login(String mobile, String password) {
		Staff staff = null;
		try {
			pre = connc
					.prepareStatement("select * from staff where mobile=? and password_=? and is_departure='0'");
			pre.setString(1, mobile);
			pre.setString(2, password);
			result = pre.executeQuery();
			while (result.next()) {
				staff = new Staff();
				staff.setId(result.getInt("id"));
				staff.setMobile(mobile);
				staff.setPhone(result.getString("phone"));
				staff.setOrganization_id(result.getInt("organization_id"));
				staff.setDepartment_id(result.getInt("department_id"));
				staff.setPosition_id(result.getInt("position_id"));
				staff.setIs_administrator(result.getInt("is_administrator"));
				staff.setIs_leader(result.getInt("is_leader"));
				staff.setIs_hipe(result.getInt("is_hipe"));
				staff.setIs_departure(result.getInt("is_departure"));
				staff.setIs_warrant(result.getInt("is_warrant"));
				staff.setPassword(password);
				staff.setName(result.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return staff;
	}

	// 返回相关联机构的所有员工
	public List<Staff> getAllStaffByOrganization(int superId) {
		List<Organization> list_organ = new OrganizationImp()
				.getAllChildOrganizations(superId);
		String count_OrganizationId = "";
		List<Staff> organization_list_Staff = new ArrayList<Staff>();
		for (Organization item : list_organ) {
			count_OrganizationId += item.getId() + ",";
		}
		if (count_OrganizationId.length() >= 2) {
			count_OrganizationId = count_OrganizationId.substring(0,
					count_OrganizationId.length() - 1);
		} else {
			count_OrganizationId = "0";
		}
		try {
			pre = connc
					.prepareStatement("SELECT * FROM staff WHERE is_departure='0' and organization_id in ( "
							+ count_OrganizationId + " )");
			result = pre.executeQuery();
			while (result.next()) {
				Staff staff = new Staff();
				staff.setId(result.getInt("id"));
				staff.setMobile(result.getString("mobile"));
				staff.setPhone(result.getString("phone"));
				staff.setIs_administrator(result.getInt("is_administrator"));
				staff.setIs_leader(result.getInt("is_leader"));
				staff.setIs_hipe(result.getInt("is_hipe"));
				staff.setIs_departure(result.getInt("is_departure"));
				staff.setIs_warrant(result.getInt("is_warrant"));
				staff.setPassword(result.getString("password_"));
				staff.setName(result.getString("name"));
				organization_list_Staff.add(staff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return organization_list_Staff;
	}

	public int getAllStaff(){
		int sum = 0;
		try {
			pre = connc.prepareStatement("SELECT count(*) FROM staff WHERE is_departure='0' and organization_id !=-1");
			result = pre.executeQuery();
			if(result.next()){
				sum = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		finally {
//			close();
//		}
		return sum;
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
