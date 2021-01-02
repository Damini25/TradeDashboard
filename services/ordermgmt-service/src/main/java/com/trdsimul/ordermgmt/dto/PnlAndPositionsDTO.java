package com.trdsimul.ordermgmt.dto;

import java.util.List;

public class PnlAndPositionsDTO {

	private Double pnlValue;
	private Double availableVolume;
	private Double startingVolume;
	private Double availableBalance;
	private Double startingBalance;
	private List<PortfolioDTO> portfolioDtos;

	public PnlAndPositionsDTO() {
		super();
	}

	public PnlAndPositionsDTO(Double pnlValue, Double availableVolume, Double startingVolume, Double availableBalance,
			Double startingBalance, List<PortfolioDTO> portfolioDtos) {
		super();
		this.pnlValue = pnlValue;
		this.availableVolume = availableVolume;
		this.startingVolume = startingVolume;
		this.availableBalance = availableBalance;
		this.startingBalance = startingBalance;
		this.portfolioDtos = portfolioDtos;
	}

	public Double getStartingBalance() {
		return startingBalance;
	}

	public void setStartingBalance(Double startingBalance) {
		this.startingBalance = startingBalance;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public List<PortfolioDTO> getPortfolioDtos() {
		return portfolioDtos;
	}

	public void setPortfolioDtos(List<PortfolioDTO> portfolioDtos) {
		this.portfolioDtos = portfolioDtos;
	}

	public Double getPnlValue() {
		return pnlValue;
	}

	public void setPnlValue(Double pnlValue) {
		this.pnlValue = pnlValue;
	}

	public Double getAvailableVolume() {
		return availableVolume;
	}

	public void setAvailableVolume(Double availableVolume) {
		this.availableVolume = availableVolume;
	}

	public Double getStartingVolume() {
		return startingVolume;
	}

	public void setStartingVolume(Double startingVolume) {
		this.startingVolume = startingVolume;
	}

}
