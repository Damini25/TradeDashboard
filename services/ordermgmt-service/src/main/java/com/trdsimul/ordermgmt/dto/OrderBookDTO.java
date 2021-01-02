package com.trdsimul.ordermgmt.dto;

import java.util.List;

import com.trdsimul.ordermgmt.model.entity.ExecutedOrdersDetails;
import com.trdsimul.ordermgmt.model.entity.OrderDetails;

public class OrderBookDTO {

	private List<OrderDetails> allOrders;
	private List<ExecutedOrdersDetails> allTrades;

	public OrderBookDTO() {
		super();
	}

	public OrderBookDTO(List<OrderDetails> allOrders, List<ExecutedOrdersDetails> allTrades) {
		super();
		this.allOrders = allOrders;
		this.allTrades = allTrades;
	}

	public List<OrderDetails> getAllOrders() {
		return allOrders;
	}

	public void setAllOrders(List<OrderDetails> allOrders) {
		this.allOrders = allOrders;
	}

	public List<ExecutedOrdersDetails> getAllTrades() {
		return allTrades;
	}

	public void setAllTrades(List<ExecutedOrdersDetails> allTrades) {
		this.allTrades = allTrades;
	}

}
