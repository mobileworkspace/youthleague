package com.neusoft.youthleague.model;

import java.io.Serializable;


public class Organization  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7334542660661800841L;
	
	private int id;
	private String  name;
	private String address;
	private int superId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSuperId() {
		return superId;
	}

	public void setSuperId(int superId) {
		this.superId = superId;
	}
}
