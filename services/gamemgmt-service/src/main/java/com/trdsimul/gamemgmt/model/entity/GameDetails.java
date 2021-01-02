package com.trdsimul.gamemgmt.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class GameDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer gameId;
	private String gameCode;
	private String gameMode;
	private String bidAsk;
	private Integer gameInterval;
	private Integer playbackFrequency;
	private Double startingBalance;
	private Double startingVolume;
	private Date playbackStartTime;
	private Date playbackEndTime;
	private Boolean isGameActive;
	private Boolean playbackFlag;

	public GameDetails() {
		super();
	}

	public GameDetails(Integer gameId, String gameCode, String gameMode, String bidAsk, Integer gameInterval,
			Integer playbackFrequency, Double startingBalance, Double startingVolume, Date playbackStartTime,
			Date playbackEndTime, Boolean isGameActive, Boolean playbackFlag) {
		super();
		this.gameId = gameId;
		this.gameCode = gameCode;
		this.gameMode = gameMode;
		this.bidAsk = bidAsk;
		this.gameInterval = gameInterval;
		this.playbackFrequency = playbackFrequency;
		this.startingBalance = startingBalance;
		this.startingVolume = startingVolume;
		this.playbackStartTime = playbackStartTime;
		this.playbackEndTime = playbackEndTime;
		this.isGameActive = isGameActive;
		this.playbackFlag = playbackFlag;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
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

	public Boolean getIsGameActive() {
		return isGameActive;
	}

	public void setIsGameActive(Boolean isGameActive) {
		this.isGameActive = isGameActive;
	}

	public Integer getPlaybackFrequency() {
		return playbackFrequency;
	}

	public void setPlaybackFrequency(Integer playbackFrequency) {
		this.playbackFrequency = playbackFrequency;
	}

	public Date getPlaybackStartTime() {
		return playbackStartTime;
	}

	public void setPlaybackStartTime(Date playbackStartTime) {
		this.playbackStartTime = playbackStartTime;
	}

	public Date getPlaybackEndTime() {
		return playbackEndTime;
	}

	public void setPlaybackEndTime(Date playbackEndTime) {
		this.playbackEndTime = playbackEndTime;
	}

	public Integer getGameInterval() {
		return gameInterval;
	}

	public void setGameInterval(Integer gameInterval) {
		this.gameInterval = gameInterval;
	}

	public Boolean getPlaybackFlag() {
		return playbackFlag;
	}

	public void setPlaybackFlag(Boolean playbackFlag) {
		this.playbackFlag = playbackFlag;
	}

}
