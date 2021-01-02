package com.trdsimul.ordermgmt.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GraphResponseDTO {

	private Double price;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date onlyTime;

	public GraphResponseDTO() {
	}

	public GraphResponseDTO(Double price, Date onlyTime) {
		super();
		this.price = price;
		this.onlyTime = onlyTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getOnlyTime() {
		return onlyTime;
	}

	public void setOnlyTime(Date onlyTime) {
		this.onlyTime = onlyTime;
	}

	@Override
	public String toString() {
		return "GraphResponseDTO [price=" + price + ", onlyTime=" + onlyTime + "]";
	}

	

}
