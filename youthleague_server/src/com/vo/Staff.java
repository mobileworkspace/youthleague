package com.vo;

import java.io.Serializable;

public class Staff  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7804817582574041466L;

	int id;
	String mobile;//手机-登陆账号
	String phone;//座机
	String password;//登陆密码
	String name;//姓名

	int organization_id;//机构id
	String organization_Name;//机构名称
	int department_id;//部门id
	String department_Name;//部门名称
	int position_id;//职位id
	String position_Name;//职位名称

	int is_administrator;//是否管理员
	int is_leader;
	int is_hipe;
	int is_departure;//是否离职
	int is_warrant;//是否授权

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(int organizationId) {
		organization_id = organizationId;
	}

	public int getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(int departmentId) {
		department_id = departmentId;
	}

	public int getPosition_id() {
		return position_id;
	}

	public void setPosition_id(int positionId) {
		position_id = positionId;
	}

	public int getIs_administrator() {
		return is_administrator;
	}

	public void setIs_administrator(int isAdministrator) {
		is_administrator = isAdministrator;
	}

	public int getIs_leader() {
		return is_leader;
	}

	public void setIs_leader(int isLeader) {
		is_leader = isLeader;
	}

	public int getIs_hipe() {
		return is_hipe;
	}

	public void setIs_hipe(int isHipe) {
		is_hipe = isHipe;
	}

	public int getIs_departure() {
		return is_departure;
	}

	public void setIs_departure(int isDeparture) {
		is_departure = isDeparture;
	}

	public int getIs_warrant() {
		return is_warrant;
	}

	public void setIs_warrant(int isWarrant) {
		is_warrant = isWarrant;
	}

	public String getOrganization_Name() {
		return organization_Name;
	}

	public void setOrganization_Name(String organization_Name) {
		this.organization_Name = organization_Name;
	}

	public String getDepartment_Name() {
		return department_Name;
	}

	public void setDepartment_Name(String department_Name) {
		this.department_Name = department_Name;
	}

	public String getPosition_Name() {
		return position_Name;
	}

	public void setPosition_Name(String position_Name) {
		this.position_Name = position_Name;
	}

}
