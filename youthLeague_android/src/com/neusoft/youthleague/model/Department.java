package com.neusoft.youthleague.model;

import java.io.Serializable;


public class Department  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8994927319125792586L;
	
	private String  name;
	private int id;
	
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
}
