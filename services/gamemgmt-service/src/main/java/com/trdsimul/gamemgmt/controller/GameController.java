package com.trdsimul.gamemgmt.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.trdsimul.gamemgmt.dto.DashBResponse;
import com.trdsimul.gamemgmt.dto.DashBResponseSingleObject;
import com.trdsimul.gamemgmt.dto.ErrorMessage;
import com.trdsimul.gamemgmt.dto.PlaybackFlagDTO;
import com.trdsimul.gamemgmt.dto.UserGameStatusDTO;
import com.trdsimul.gamemgmt.model.entity.GameDetails;
import com.trdsimul.gamemgmt.model.entity.GameSession;
import com.trdsimul.gamemgmt.model.entity.UsersGameDetails;
import com.trdsimul.gamemgmt.service.GameService;
import com.trdsimul.gamemgmt.service.OrderGenService;
import com.trdsimul.gamemgmt.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/game/")
public class GameController {

	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderGenService orderGenService;
	
	@Autowired
	RestTemplate restTemplate;
	
	private static Logger LOG = LoggerFactory.getLogger(GameController.class);

	
	@PostMapping(path = "/allgames", consumes = "application/json", produces = "application/json")
	public DashBResponse listAllActiveGames(@RequestHeader("userId") Integer userId) {
		DashBResponse dashBResponse = new DashBResponse();
		ErrorMessage message = new ErrorMessage();
		try {
			List<GameDetails> dtos = new ArrayList<GameDetails>();
			LOG.info("finding all games for user.");
			if (userService.getUserDetails(userId).getUserTypeId() == 0) {
				LOG.info("Logged in as Admin. Finding all games");
				dashBResponse.setSuccess(true);
				dashBResponse.setError(null);
				dtos = gameService.listAllGames();
				dashBResponse.setData(dtos);
			} else {
				List<UserGameStatusDTO> userGameStatusDTOs = new ArrayList<UserGameStatusDTO>();
				dashBResponse.setSuccess(true);
				dashBResponse.setError(null);
				LOG.info("Logged in as User. Finding all active games");
				List<GameDetails> gameDetailsList = gameService.listAllActiveGames();
				Map<Integer, Boolean> userGameStatus = userService.userStatusForGames(userId, gameDetailsList);
				for(GameDetails gameDetails : gameDetailsList) {
					UserGameStatusDTO userPerGameStatus = new UserGameStatusDTO(gameDetails, userGameStatus.get(gameDetails.getGameId()));
					userGameStatusDTOs.add(userPerGameStatus);
				}
				dashBResponse.setData(userGameStatusDTOs);
			}
		} catch (Exception e) {
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
			LOG.error("Unknown Exception Occurred !!" + e);
		}
		return dashBResponse;
	}

	@PostMapping(path = "/creategame", consumes = "application/json", produces = "application/json")
	public DashBResponseSingleObject createNewGame(@RequestBody GameDetails gameDetails) {
		DashBResponseSingleObject dashBResponse = new DashBResponseSingleObject();
		ErrorMessage message = new ErrorMessage();
		try {
			LOG.info("Checking game details already exists...");
			if (gameDetails.getGameId() == null) {
				LOG.info("No game found. Creating new...");
				gameDetails.setPlaybackFlag(false);
				GameDetails savedGame = gameService.createNewGame(gameDetails);
				LOG.info("Fetching historical orders for given playback start time : "
						+ gameDetails.getPlaybackStartTime() + "and end time : " + gameDetails.getPlaybackEndTime());
				if (savedGame != null) {
					Integer playbackFrequency = orderGenService.fetchRecordsBetweenDatesAndCalFrequency(savedGame);
					savedGame.setPlaybackFrequency(playbackFrequency);
					gameService.updateGameDetails(savedGame);
					dashBResponse.setSuccess(true);
					dashBResponse.setError(null);
					dashBResponse.setData(savedGame);
				}
				else {
					LOG.error("Error creating new game");
					message.setErrorMessage("Error creating new game");
					message.setKey("createGameFailed");
					dashBResponse.setSuccess(false);
					dashBResponse.setError(message);
					dashBResponse.setData(null);					
				}
			} else {
				LOG.info(" Game details found !! Updating it now. ");
				GameDetails gameDetails2 = gameService.updateGameDetail(gameDetails);
				if ( gameDetails2 != null) {
					dashBResponse.setSuccess(true);
					dashBResponse.setError(null);
					dashBResponse.setData(gameDetails2);
				} else {
					LOG.error("Error updating game config");
					message.setErrorMessage("Error updating game config");
					message.setKey("updateFailed");
					dashBResponse.setSuccess(false);
					dashBResponse.setError(message);
					dashBResponse.setData(null);	
				}
			}
		} catch (Exception e) {
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
			LOG.error("Unknown Exception Occurred !!" + e);
		}
		return dashBResponse;
	}

	@DeleteMapping(path = "/deletegame", produces = "application/json")
	public DashBResponse deleteGame(@RequestParam("gameId") Integer gameId) {
		DashBResponse dashBResponse = new DashBResponse();
		ErrorMessage message = new ErrorMessage();
		try {
			LOG.info(" Deleting game : " + gameId);
			LOG.info(" Deleting all users for game.");
			userService.deleteUsersForGame(gameId);
			LOG.info(" Deleting all associated sessions and game details. ");
			gameService.deleteGameDetails(gameId);
			dashBResponse.setSuccess(true);
			dashBResponse.setError(null);
			dashBResponse.setData(null);
		} catch (Exception e) {
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
			LOG.error("Unknown Exception Occurred !!" + e);
		}
		return dashBResponse;

	}
	
	@PostMapping(path = "/startgame", consumes = "application/json", produces = "application/json")
	public DashBResponse startGame(@RequestBody GameDetails gameDetails) throws IOException {
		DashBResponse dashBResponse = new DashBResponse();
		ErrorMessage message = new ErrorMessage();
		try {
			gameDetails.setPlaybackFlag(true);
			LOG.info("Starting new game... ");
			GameSession gameSession = gameService.startGame(gameDetails);
			if (gameSession != null) {
				userService.createTwoBotUsers(gameSession, gameDetails);
				List<GameSession> gameSessionList = new ArrayList<GameSession>();
				gameSessionList.add(gameSession);
				dashBResponse.setSuccess(true);
				dashBResponse.setError(null);
				dashBResponse.setData(gameSessionList);
				LOG.info("Game started !! ");
			}
			else {
				LOG.error("Game start Failed");
				message.setKey("startGameFailed");
				message.setErrorMessage("Game start Failed");
				dashBResponse.setSuccess(false);
				dashBResponse.setError(message);
				dashBResponse.setData(null);
			}
		} catch (Exception e) {
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
			LOG.error("Unknown Exception Occurred !!" + e);
		}
		return dashBResponse;
	}
	
	@PostMapping(path = "/joingame", consumes = "application/json", produces = "application/json")
	public DashBResponseSingleObject joinGame(@RequestParam("gameId") Integer gameId, @RequestHeader("userId") Integer userId) throws ParseException, ClassNotFoundException, IOException {
		DashBResponseSingleObject dashBResponse = new DashBResponseSingleObject();
		ErrorMessage message = new ErrorMessage();
		try {
			LOG.info("Fetching game session details... ");
			GameSession gameSession = gameService.findGameSessionDetails(gameId);
			GameDetails gameDetails = gameService.fetchGameDetails(gameId);
			if (checkGameIsLive(gameSession.getSessionId())) {
				LOG.info("Game is live. Registering user -" + userId + " under game -" + gameId);
				UsersGameDetails usersGameDetails = userService.createNewUserGameSession(gameDetails, gameSession, userId);
				dashBResponse.setSuccess(true);
				dashBResponse.setError(null);
				dashBResponse.setData(usersGameDetails);
			} else {
				dashBResponse.setSuccess(null);
				dashBResponse.setError("Game has already ended.");
				dashBResponse.setData(null);
			}
		} catch (Exception e) {
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
			LOG.error("Unknown Exception Occurred !!" + e);
		}
		return dashBResponse;		
	}

	@PostMapping(path = "/stopgame", consumes = "application/json", produces = "application/json")
	public DashBResponseSingleObject stopGame(@RequestParam("gameId") Integer gameId) {
		DashBResponseSingleObject dashBResponse = new DashBResponseSingleObject();
		ErrorMessage message = new ErrorMessage();
		try {
			LOG.info("Stopping game now... ");
			userService.updateStatusForAllUsers(gameId);
			gameService.stopGame(gameId);
			dashBResponse.setSuccess(true);
			dashBResponse.setError(null);
			dashBResponse.setData(null);
		} catch (Exception e) {
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
			LOG.error("Unknown Exception Occurred !!" + e);
		}
		return dashBResponse;
	}

	@GetMapping(path = "/isgamelive")
	public Boolean checkGameIsLive(@RequestParam("gameSessionId") String gameSessionId) throws ParseException, ClassNotFoundException, IOException {
		LOG.info("Checking if game is live... ");
		Map<Integer, Boolean> gameStatus = gameService.checkGameIsLive(gameSessionId);
		Integer gameId = (Integer) gameStatus.keySet().toArray()[0];
		GameSession gameSession = gameService.findGameSessionDetails(gameId);
		if (gameStatus.get(gameId) && (!(gameSession == null) && gameSession.getIsActive()) ) {
			LOG.info("Game : "+ gameId +" is active... ");
			return true;
		} else {
			LOG.info("Game : "+ gameId +" has ended... ");
			GameDetails gameDetails = gameService.fetchGameDetails(gameId);
			if (gameDetails.getIsGameActive()) {
				LOG.info(" Stopping Game : "+ gameId );
				stopGame(gameId);
			}
			return false;
		}

	}

	@GetMapping(path = "/checkPlaybackFlag")
	public DashBResponseSingleObject checkPlaybackFlag(@RequestParam("gameId") Integer gameId) {
		DashBResponseSingleObject dashBResponse = new DashBResponseSingleObject();
		ErrorMessage message = new ErrorMessage();
		try {
			GameDetails gameDetails = gameService.fetchGameDetails(gameId);
			if(gameDetails != null) {
				dashBResponse.setSuccess(true);
				dashBResponse.setError(null);
				PlaybackFlagDTO flagDTO = new PlaybackFlagDTO(gameDetails.getPlaybackFlag());
				dashBResponse.setData(flagDTO);
			}
		} catch (Exception e) {
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
			LOG.error("Unknown Exception Occurred !!" + e);
		}
		return dashBResponse;
	}
	
}
