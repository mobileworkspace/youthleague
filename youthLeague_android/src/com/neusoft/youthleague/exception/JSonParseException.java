package com.neusoft.youthleague.exception;

import org.json.JSONException;

public class JSonParseException extends JSONException {

	private static final long serialVersionUID = 4969696377731088083L;
	
	private String message;

	public JSonParseException(String message) {
		super(message);
		this.setMessage("Json Parse Exception Error:" + message);
	}
	
	public JSonParseException(Exception e) {
		super(e.getMessage());
		this.setMessage("Json Parse Exception Error:" + e.getMessage());
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
