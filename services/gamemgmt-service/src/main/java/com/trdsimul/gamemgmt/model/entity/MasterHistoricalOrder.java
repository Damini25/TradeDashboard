package com.trdsimul.gamemgmt.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class MasterHistoricalOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer histOrderId;
	private String productName;
	private Date eventDate;
	private Double price;
	private Double totalQty;
	private String bidOffer;
	private Integer productId;

	public MasterHistoricalOrder() {
		super();
	}

	public MasterHistoricalOrder(Integer histOrderId, String productName, Date eventDate, Double price,
			Double totalQty, String bidOffer, Integer productId) {
		super();
		this.histOrderId = histOrderId;
		this.productName = productName;
		this.eventDate = eventDate;
		this.price = price;
		this.totalQty = totalQty;
		this.bidOffer = bidOffer;
		this.productId = productId;
	}

	public Integer getHistOrderId() {
		return histOrderId;
	}

	public void setHistOrderId(Integer histOrderId) {
		this.histOrderId = histOrderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
