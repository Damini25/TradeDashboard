package com.trdsimul.ordermgmt.dto;

public class PortfolioDTO {

	private String ticker;
	private String productType;
	private Integer contractSize;
	private Double position;
	private Double cost;
	private Double last;
	private Double bid;
	private Double ask;
	private Double realizedPnl;
	private Integer colorCoding;
	private Double unrealizedPnl;

	public PortfolioDTO() {
		super();
	}

	public PortfolioDTO(String ticker, String productType, Integer contractSize, Double position, Double cost,
			Double last, Double bid, Double ask, Double realizedPnl, Integer colorCoding, Double unrealizedPnl) {
		super();
		this.ticker = ticker;
		this.productType = productType;
		this.contractSize = contractSize;
		this.position = position;
		this.cost = cost;
		this.last = last;
		this.bid = bid;
		this.ask = ask;
		this.realizedPnl = realizedPnl;
		this.colorCoding = colorCoding;
		this.unrealizedPnl = unrealizedPnl;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Integer getContractSize() {
		return contractSize;
	}

	public void setContractSize(Integer contractSize) {
		this.contractSize = contractSize;
	}

	public Double getPosition() {
		return position;
	}

	public void setPosition(Double position) {
		this.position = position;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getLast() {
		return last;
	}

	public void setLast(Double last) {
		this.last = last;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public Double getRealizedPnl() {
		return realizedPnl;
	}

	public void setRealizedPnl(Double realizedPnl) {
		this.realizedPnl = realizedPnl;
	}

	public Integer getColorCoding() {
		return colorCoding;
	}

	public void setColorCoding(Integer colorCoding) {
		this.colorCoding = colorCoding;
	}

	public Double getUnrealizedPnl() {
		return unrealizedPnl;
	}

	public void setUnrealizedPnl(Double unrealizedPnl) {
		this.unrealizedPnl = unrealizedPnl;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}
