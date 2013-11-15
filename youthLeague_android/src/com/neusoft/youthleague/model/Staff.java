package com.neusoft.youthleague.model;

import java.io.Serializable;


public class Staff implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1676648864733281516L;
	
	private int id;
	private String  name;
	private String pwd;
	private String mobile;
	private String phone;

	private int organizationId;
	private int departmentId;
	private int positionId;
	
	private boolean isAdministrator;
	private boolean isLeader;
	private boolean isHipe;
	private boolean isDeparture;
	private boolean isWarrant;
	
	private boolean isSelected;
	private String departmentName;
	private String positionName;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public boolean isAdministrator() {
		return isAdministrator;
	}

	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}

	public boolean isHipe() {
		return isHipe;
	}

	public void setHipe(boolean isHipe) {
		this.isHipe = isHipe;
	}

	public boolean isDeparture() {
		return isDeparture;
	}

	public void setDeparture(boolean isDeparture) {
		this.isDeparture = isDeparture;
	}

	public boolean isWarrant() {
		return isWarrant;
	}

	public void setWarrant(boolean isWarrant) {
		this.isWarrant = isWarrant;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	
}
