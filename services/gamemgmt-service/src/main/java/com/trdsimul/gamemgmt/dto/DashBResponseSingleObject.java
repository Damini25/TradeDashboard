package com.trdsimul.gamemgmt.dto;

public class DashBResponseSingleObject {

	private Boolean success;
	private Object error;
	private Object data;

	public DashBResponseSingleObject() {
		super();
	}

	public DashBResponseSingleObject(Boolean success, Object error, Object data) {
		super();
		this.success = success;
		this.error = error;
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
