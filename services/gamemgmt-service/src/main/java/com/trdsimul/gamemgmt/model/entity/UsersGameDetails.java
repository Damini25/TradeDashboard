package com.trdsimul.gamemgmt.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UsersGameDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer userGameId;
	private Integer userId;
	private Integer gameId;
	private String gameSessionId;
	private Boolean isActive;
	private Integer playbackFrequency;
	private Double startingBalance;
	private Double availableBalance;
	private String bidAsk;
	private Double startingVolume;
	private Double availableVolume;

	public UsersGameDetails() {
		super();
	}

	public UsersGameDetails(Integer userGameId, Integer userId, Integer gameId, String gameSessionId, Boolean isActive,
			Integer playbackFrequency, Double startingBalance, Double availableBalance, String bidAsk,
			Double startingVolume, Double availableVolume) {
		super();
		this.userGameId = userGameId;
		this.userId = userId;
		this.gameId = gameId;
		this.gameSessionId = gameSessionId;
		this.isActive = isActive;
		this.playbackFrequency = playbackFrequency;
		this.startingBalance = startingBalance;
		this.availableBalance = availableBalance;
		this.bidAsk = bidAsk;
		this.startingVolume = startingVolume;
		this.availableVolume = availableVolume;
	}

	public String getBidAsk() {
		return bidAsk;
	}

	public void setBidAsk(String bidAsk) {
		this.bidAsk = bidAsk;
	}

	public Double getStartingVolume() {
		return startingVolume;
	}

	public void setStartingVolume(Double startingVolume) {
		this.startingVolume = startingVolume;
	}

	public Integer getUserGameId() {
		return userGameId;
	}

	public void setUserGameId(Integer userGameId) {
		this.userGameId = userGameId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getGameSessionId() {
		return gameSessionId;
	}

	public void setGameSessionId(String gameSessionId) {
		this.gameSessionId = gameSessionId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getPlaybackFrequency() {
		return playbackFrequency;
	}

	public void setPlaybackFrequency(Integer playbackFrequency) {
		this.playbackFrequency = playbackFrequency;
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

	public Double getAvailableVolume() {
		return availableVolume;
	}

	public void setAvailableVolume(Double availableVolume) {
		this.availableVolume = availableVolume;
	}

}
