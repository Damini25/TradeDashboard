package com.trdsimul.gamemgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trdsimul.gamemgmt.dto.UserCredDTO;
import com.trdsimul.gamemgmt.enums.GameMode;
import com.trdsimul.gamemgmt.model.entity.GameDetails;
import com.trdsimul.gamemgmt.model.entity.GameSession;
import com.trdsimul.gamemgmt.model.entity.UserDetails;
import com.trdsimul.gamemgmt.model.entity.UsersGameDetails;
import com.trdsimul.gamemgmt.repository.UserDetailsRepository;
import com.trdsimul.gamemgmt.repository.UserGameDetailsRepository;

@Service
public class UserService {

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Autowired
	UserGameDetailsRepository userGameDetailsRepository;
	
	public UserDetails loginUser(UserCredDTO credDTO) {

		Optional<UserDetails> userDetails = userDetailsRepository.loginUser(credDTO.getUsername(),
				credDTO.getPassword());

		if (userDetails.isPresent()) {
			return userDetails.get();
		} else
			return null;

	}

	public UsersGameDetails createNewUserGameSession(GameDetails gameDetails, GameSession gameSession, Integer userId) {

		UsersGameDetails usersGameDetails = checkUserActiveForGame(userId, gameDetails.getGameId());
		if (usersGameDetails == null) {
			UsersGameDetails newUserForInpGame = new UsersGameDetails();
			newUserForInpGame.setGameId(gameSession.getGameId());
			newUserForInpGame.setGameSessionId(gameSession.getSessionId());
			newUserForInpGame.setIsActive(true);
			newUserForInpGame.setUserId(userId);
			newUserForInpGame.setPlaybackFrequency(gameSession.getPlaybackFrequency());
			newUserForInpGame.setStartingBalance(gameDetails.getStartingBalance());
			newUserForInpGame.setAvailableBalance(gameDetails.getStartingBalance());
			if (gameDetails.getGameMode().equalsIgnoreCase(GameMode.volume.toString())) {
				newUserForInpGame.setStartingVolume(gameDetails.getStartingVolume());
				newUserForInpGame.setAvailableVolume(gameDetails.getStartingVolume());
				newUserForInpGame.setBidAsk(gameDetails.getBidAsk());
			}
			return userGameDetailsRepository.save(newUserForInpGame);
		} else {
			return usersGameDetails;
		}

	}
	
	private UsersGameDetails checkUserActiveForGame(Integer userId, Integer gameId) {
		Optional<UsersGameDetails> usersGameDetails = userGameDetailsRepository
				.checkUserIsActiveForAnyGameSession(userId);

		if (usersGameDetails.isPresent() && usersGameDetails.get().getGameId().compareTo(gameId) != 0 ) {
			usersGameDetails.get().setIsActive(false);
			userGameDetailsRepository.save(usersGameDetails.get());
			return null;
		} else {
			return usersGameDetails.isPresent() ? usersGameDetails.get() : null;
		}

	}

	public void updateStatusForAllUsers(Integer gameId) {
		userGameDetailsRepository.updateStatusForAllUsers(gameId);
	}

	public UserDetails getUserDetails(Integer userId) {
		 Optional<UserDetails> userDetails = userDetailsRepository.findById(userId);
		 if(userDetails.isPresent()) {
			 return userDetails.get();
		 }
		 else
			 return null;
	}

	public void deleteUsersForGame(Integer gameId) {
		userGameDetailsRepository.deleteByGameId(gameId);
	}

	public List<UsersGameDetails> allActiveUsersForGame(Integer gameId) {
		return userGameDetailsRepository.allActiveUsersForGame(gameId);
		
	}

	public void createTwoBotUsers(GameSession gameSession, GameDetails gameDetails) {
		Integer userTypeId = 2;
		List<UserDetails> botUsers = userDetailsRepository.getBotUsers(userTypeId);
		for (UserDetails user : botUsers) {
			UsersGameDetails usersGameDetails = checkUserActiveForGame(user.getUserId(), gameDetails.getGameId());
			if (usersGameDetails == null) {
				UsersGameDetails newUserForInpGame = new UsersGameDetails();
				newUserForInpGame.setGameId(gameSession.getGameId());
				newUserForInpGame.setGameSessionId(gameSession.getSessionId());
				newUserForInpGame.setIsActive(true);
				newUserForInpGame.setUserId(user.getUserId());
				newUserForInpGame.setPlaybackFrequency(gameSession.getPlaybackFrequency());
				newUserForInpGame.setStartingBalance(999999d);
				newUserForInpGame.setAvailableBalance(999999d);
				if (gameDetails.getGameMode().equalsIgnoreCase(GameMode.volume.toString())) {
					newUserForInpGame.setStartingVolume(gameDetails.getStartingVolume());
					newUserForInpGame.setAvailableVolume(gameDetails.getStartingVolume());
					newUserForInpGame.setBidAsk(gameDetails.getBidAsk());
				}
				userGameDetailsRepository.save(newUserForInpGame);
			}
		}
		
	}

	public Map<Integer, Boolean> userStatusForGames(Integer userId, List<GameDetails> gameDetailsList) {
		
		Map<Integer, Boolean> userGameStatus = new HashMap<Integer, Boolean>();
		for(GameDetails gameDetails : gameDetailsList) {
			Optional<UsersGameDetails> usersGameDetails = userGameDetailsRepository.findUserStatus(gameDetails.getGameId(), userId);
			if(usersGameDetails.isPresent()) {
				userGameStatus.put(gameDetails.getGameId(), true);
			} else {
				userGameStatus.put(gameDetails.getGameId(), false);
			}
		}
		return userGameStatus;
	}

}
