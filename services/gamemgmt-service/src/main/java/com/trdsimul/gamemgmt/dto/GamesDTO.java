package com.trdsimul.gamemgmt.dto;

public class GamesDTO {

	private String gameCode;
	private String gameMode;
	private String bidAsk;
	private Integer gameInterval;
	private Double startingBalance;
	private Double startingVolume;

	public GamesDTO() {
		super();
	}

	public GamesDTO(String gameCode, String gameMode, String bidAsk, Integer gameInterval, Double startingBalance,
			Double startingVolume) {
		super();
		this.gameCode = gameCode;
		this.gameMode = gameMode;
		this.bidAsk = bidAsk;
		this.gameInterval = gameInterval;
		this.startingBalance = startingBalance;
		this.startingVolume = startingVolume;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public String getBidAsk() {
		return bidAsk;
	}

	public void setBidAsk(String bidAsk) {
		this.bidAsk = bidAsk;
	}

	public Integer getGameInterval() {
		return gameInterval;
	}

	public void setGameInterval(Integer gameInterval) {
		this.gameInterval = gameInterval;
	}

	public Double getStartingBalance() {
		return startingBalance;
	}

	public void setStartingBalance(Double startingBalance) {
		this.startingBalance = startingBalance;
	}

	public Double getStartingVolume() {
		return startingVolume;
	}

	public void setStartingVolume(Double startingVolume) {
		this.startingVolume = startingVolume;
	}

}
