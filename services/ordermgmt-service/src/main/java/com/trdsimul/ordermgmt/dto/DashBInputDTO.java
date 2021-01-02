package com.trdsimul.ordermgmt.dto;

public class DashBInputDTO {

	private Integer productId;
	private Integer gameId;
	private Integer noOfRows;
	private Integer traderId;
	
	public Integer getNoOfRows() {
		return noOfRows;
	}
	public void setNoOfRows(Integer noOfRows) {
		this.noOfRows = noOfRows;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getTraderId() {
		return traderId;
	}
	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}
	
	
}
