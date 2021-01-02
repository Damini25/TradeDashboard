package com.trdsimul.ordermgmt.dto;

import java.util.Date;

public class DashBChartScreenResponseDTO {

	private String productName;
	private Date eventDate;
	private Double price;
	private Double totalQty;
	private String bidOffer;
	private String onlyTime;

	public DashBChartScreenResponseDTO() {
		super();
	}

	public DashBChartScreenResponseDTO(String productName, Date eventDate, Double price, Double totalQty,
			String bidOffer, String onlyTime) {
		super();
		this.productName = productName;
		this.eventDate = eventDate;
		this.price = price;
		this.totalQty = totalQty;
		this.bidOffer = bidOffer;
		this.onlyTime = onlyTime;
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

	public String getOnlyTime() {
		return onlyTime;
	}

	public void setOnlyTime(String onlyTime) {
		this.onlyTime = onlyTime;
	}

}
