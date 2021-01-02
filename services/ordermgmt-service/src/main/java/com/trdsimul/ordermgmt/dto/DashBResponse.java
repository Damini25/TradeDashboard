package com.trdsimul.ordermgmt.dto;

import java.util.List;

public class DashBResponse {

	private Boolean success;
	private Object error;
	private List<?> data;

	public DashBResponse() {
	}

	public DashBResponse(Boolean success, Object error, List<?> data) {
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

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

}
