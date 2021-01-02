package com.trdsimul.gamemgmt.dto;

import com.trdsimul.gamemgmt.model.entity.GameDetails;

public class UserGameStatusDTO {

	private GameDetails gameDetails;
	private Boolean hasUserJoined;

	public UserGameStatusDTO(GameDetails gameDetails, Boolean hasUserJoined) {
		super();
		this.gameDetails = gameDetails;
		this.hasUserJoined = hasUserJoined;
	}

	public GameDetails getGameDetails() {
		return gameDetails;
	}

	public void setGameDetails(GameDetails gameDetails) {
		this.gameDetails = gameDetails;
	}

	public Boolean getHasUserJoined() {
		return hasUserJoined;
	}

	public void setHasUserJoined(Boolean hasUserJoined) {
		this.hasUserJoined = hasUserJoined;
	}

}
