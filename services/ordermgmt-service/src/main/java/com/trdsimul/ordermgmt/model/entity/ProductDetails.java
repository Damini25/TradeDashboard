package com.trdsimul.ordermgmt.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ProductDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer productId;
	private String productCode;
	private String productName;
	private Boolean isActive;
	private String description;
	private String productType;
	private Integer contractSize;

	public ProductDetails() {
		super();
	}


	public ProductDetails(Integer productId, String productCode, String productName, Boolean isActive,
			String description, String productType, Integer contractSize) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.isActive = isActive;
		this.description = description;
		this.productType = productType;
		this.contractSize = contractSize;
	}


	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public Integer getContractSize() {
		return contractSize;
	}


	public void setContractSize(Integer contractSize) {
		this.contractSize = contractSize;
	}
	
	

	
}
