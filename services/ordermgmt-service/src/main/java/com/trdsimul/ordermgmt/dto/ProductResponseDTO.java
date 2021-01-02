package com.trdsimul.ordermgmt.dto;

public class ProductResponseDTO {

	private Integer productId;
	private String productCode;
	private String productName;

	public ProductResponseDTO() {
		super();
	}

	public ProductResponseDTO(Integer productId, String productCode, String productName) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
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

}
