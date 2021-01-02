package com.trdsimul.ordermgmt.dto;

import java.util.List;

public class GraphResponseOnlyPriceDTO {

	private List<Double> askOrders;
	private List<Double> bidOrders;
	public GraphResponseOnlyPriceDTO() {
		super();
	}
	public GraphResponseOnlyPriceDTO(List<Double> askOrders, List<Double> bidOrders) {
		super();
		this.askOrders = askOrders;
		this.bidOrders = bidOrders;
	}
	public List<Double> getAskOrders() {
		return askOrders;
	}
	public void setAskOrders(List<Double> askOrders) {
		this.askOrders = askOrders;
	}
	public List<Double> getBidOrders() {
		return bidOrders;
	}
	public void setBidOrders(List<Double> bidOrders) {
		this.bidOrders = bidOrders;
	}
	
	
}
