package com.trdsimul.gamemgmt.enums;

public enum OrderType {

	MARKET(1),LIMIT_ORDER(2),STOP_LOSS(3); 
	
	private Integer orderStatusId;
	
	OrderType(Integer orderStatusId){
		this.orderStatusId = orderStatusId;	
	}

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	
	
}
