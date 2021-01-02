package com.trdsimul.ordermgmt.dto;

public class ErrorMessage {

	private String key;
	private String value;
	private String errorMessage;

	
	public ErrorMessage(String key, String value, String errorMessage) {
		super();
		this.key = key;
		this.value = value;
		this.errorMessage = errorMessage;
	}


	public ErrorMessage() {
		super();
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
