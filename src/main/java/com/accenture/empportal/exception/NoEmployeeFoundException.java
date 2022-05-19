package com.accenture.empportal.exception;

public class NoEmployeeFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -695486056270031436L;

	public NoEmployeeFoundException(String Msg) {
		super(Msg);
	}
}
