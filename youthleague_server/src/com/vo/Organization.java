package com.vo;

public class Organization {

	int id;
	String name;
	String address;
	int super_id;
	String super_name;

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

	public int getSuper_id() {
		return super_id;
	}

	public void setSuper_id(int superId) {
		super_id = superId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSuper_name() {
		return super_name;
	}

	public void setSuper_name(String super_name) {
		this.super_name = super_name;
	}

}
