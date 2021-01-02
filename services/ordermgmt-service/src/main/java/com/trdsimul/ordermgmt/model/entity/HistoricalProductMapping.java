package com.trdsimul.ordermgmt.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class HistoricalProductMapping {

	@Id
	private Integer productId;
	private String histProdName;

	public HistoricalProductMapping() {
		super();
	}

	public HistoricalProductMapping(String histProdName, Integer productId) {
		super();
		this.histProdName = histProdName;
		this.productId = productId;
	}

	public String getHistProdName() {
		return histProdName;
	}

	public void setHistProdName(String histProdName) {
		this.histProdName = histProdName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
