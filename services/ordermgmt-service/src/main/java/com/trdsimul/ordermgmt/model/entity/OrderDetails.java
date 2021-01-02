package com.trdsimul.ordermgmt.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class OrderDetails implements Cloneable {

	@Id
	private Integer orderId;
	private Integer origOrderId;
	private Integer gameId;
	private Integer traderId;
	private Integer productId;
	private Double unfulfilledQuantity;
	private Double totalQty;
	private String bidOffer;
	private Integer currencyId;
	private Double price;
	private Integer orderTypeId;
	private Date orderTime;
	private Integer orderStatusId;

	public OrderDetails() {
	}

	public OrderDetails(Integer orderId, Integer origOrderId, Integer gameId, Integer traderId, Integer productId,
			Double unfulfilledQuantity, Double totalQty, String bidOffer, Integer currencyId, Double price,
			Integer orderTypeId, Date orderTime, Integer orderStatusId) {
		super();
		this.orderId = orderId;
		this.origOrderId = origOrderId;
		this.gameId = gameId;
		this.traderId = traderId;
		this.productId = productId;
		this.unfulfilledQuantity = unfulfilledQuantity;
		this.totalQty = totalQty;
		this.bidOffer = bidOffer;
		this.currencyId = currencyId;
		this.price = price;
		this.orderTypeId = orderTypeId;
		this.orderTime = orderTime;
		this.orderStatusId = orderStatusId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Double getUnfulfilledQuantity() {
		return unfulfilledQuantity;
	}

	public void setUnfulfilledQuantity(Double unfulfilledQuantity) {
		this.unfulfilledQuantity = unfulfilledQuantity;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}

	public String getBidOffer() {
		return bidOffer;
	}

	public void setBidOffer(String bidOffer) {
		this.bidOffer = bidOffer;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public Integer getOrigOrderId() {
		return origOrderId;
	}

	public void setOrigOrderId(Integer origOrderId) {
		this.origOrderId = origOrderId;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "OrderDetails [orderId=" + orderId + ", totalQty=" + totalQty + ", bidOffer=" + bidOffer + ", price="
				+ price + "]";
	}

}
