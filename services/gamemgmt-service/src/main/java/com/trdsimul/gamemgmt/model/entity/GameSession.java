package com.trdsimul.gamemgmt.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class GameSession {

	@Id
	private String sessionId;
	private Integer gameId;
	private Date startDateTime;
	private Date endDateTime;
	private Integer playbackFrequency;
	private Boolean isActive;

	public GameSession() {
		super();
	}


	public GameSession(String sessionId, Integer gameId, Date startDateTime, Date endDateTime,
			Integer playbackFrequency, Boolean isActive) {
		super();
		this.sessionId = sessionId;
		this.gameId = gameId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.playbackFrequency = playbackFrequency;
		this.isActive = isActive;
	}


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
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

}
