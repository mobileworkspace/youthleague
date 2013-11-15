package com.neusoft.youthleague.model;

import java.io.Serializable;


public class Position  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2215421029856284306L;
	
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
