package com.trdsimul.ordermgmt.enums;

public enum OrderStatus {

	PARTIAL_EXECUTED(3),EXECUTED(2),OPEN(1); 
	
	private Integer orderStatusId;
	
	OrderStatus(Integer orderStatusId){
		this.orderStatusId = orderStatusId;	
	}

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	
	
}
