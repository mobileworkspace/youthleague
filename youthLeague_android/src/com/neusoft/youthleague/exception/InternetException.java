package com.neusoft.youthleague.exception;

public class InternetException extends Exception {

	private static final long serialVersionUID = 2228807544801931071L;
	
	private String message;

	public InternetException(String message) {
		super();
		this.setMessage("Internet Exception Error:" + message);
	}
	
	public InternetException(Exception e) {
		super();
		this.setMessage("Internet Exception Error:" + e.getMessage());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getClass().getName() + '@' + Integer.toHexString(hashCode());
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
