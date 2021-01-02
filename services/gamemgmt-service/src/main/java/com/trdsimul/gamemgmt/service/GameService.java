package com.trdsimul.gamemgmt.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trdsimul.gamemgmt.model.entity.GameDetails;
import com.trdsimul.gamemgmt.model.entity.GameSession;
import com.trdsimul.gamemgmt.repository.GameDetailsRepository;
import com.trdsimul.gamemgmt.repository.GameSessionRepository;
import com.trdsimul.gamemgmt.repository.HistoricalOrdersRepository;

@Service
public class GameService {

	@Autowired
	GameDetailsRepository gameDetailsRepository;
	
	@Autowired
	GameSessionRepository gameSessionRepository;
	
	@Autowired
	HistoricalOrdersRepository histOrderRepository;
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	OrderGenService orderGenService;
	
	@Autowired
	RestTemplate restTemplate;

	private static Logger LOG = LoggerFactory.getLogger(GameService.class);

	public List<GameDetails> listAllGames() {

		List<GameDetails> gameDetails = new ArrayList<GameDetails>();
		Iterable<GameDetails> iterableGames = gameDetailsRepository.findAll();
		for(GameDetails game : iterableGames) {
			gameDetails.add(game);
		}
		return gameDetails;
	}
	
	public List<GameDetails> listAllActiveGames() {
		List<GameDetails> gamesList = gameDetailsRepository.findAllActiveGames();
		return gamesList;
		
	}


	public GameDetails createNewGame(GameDetails inputDTO) {

		return gameDetailsRepository.save(inputDTO); 
	}

	public Boolean deleteGame(GameDetails inputDTO) {

		if (inputDTO instanceof GameDetails) {
			Optional<GameDetails> savedGame = gameDetailsRepository.findById(inputDTO.getGameId());
			if (savedGame.isPresent()) {
				gameDetailsRepository.delete(savedGame.get());
				return true;
			} else
				return false;
		} else
			return false;
	}

	public GameSession startGame(GameDetails gameDetails) throws IOException {
		String sessionId =  sessionService.getNewSessionToken(gameDetails.getGameId(), gameDetails.getGameInterval());
		gameDetails.setIsGameActive(true);
		gameDetails.setPlaybackFlag(true);
		LOG.info("Creating new game session..");
		GameSession gameSession = createNewGameSession(gameDetails, sessionId);
		gameDetailsRepository.save(gameDetails);
		return gameSession;
	}

	private GameSession createNewGameSession(GameDetails gameDetails, String sessionId) {
		GameSession gameSession = new GameSession();
		Date dtStartTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dtStartTime);
		cal.add(Calendar.MINUTE, gameDetails.getGameInterval());
		gameSession.setGameId(gameDetails.getGameId());
		gameSession.setStartDateTime(dtStartTime);
		gameSession.setEndDateTime(cal.getTime());
		gameSession.setSessionId(sessionId);
		gameSession.setPlaybackFrequency(gameDetails.getPlaybackFrequency());
		gameSession.setIsActive(gameDetails.getIsGameActive());
		return gameSessionRepository.save(gameSession);
	}

	public Map<Integer, Boolean> checkGameIsLive(String sessionId) throws ParseException, ClassNotFoundException, IOException {

		return sessionService.isSessionValid(sessionId);
	}

	public GameSession findGameSessionDetails(Integer gameId) {
		GameSession gameSession = gameSessionRepository.findByGameId(gameId);
		return gameSession;

	}

	public void stopGame(Integer gameId) {
		gameSessionRepository.updateGameSessionStatus(gameId);
		gameDetailsRepository.updateGameDetailsStatus(gameId);

	}

	public GameDetails fetchGameDetails(Integer gameId) {
		Optional<GameDetails> gameDetails = gameDetailsRepository.findById(gameId);
		if(gameDetails.isPresent()) {
			return gameDetails.get();
		}
		else
			return null;	
	}

	public GameDetails updateGameDetail(GameDetails gameDetails) {
		Optional<GameDetails> optSavedGame = gameDetailsRepository.findById(gameDetails.getGameId());
		if(optSavedGame.isPresent()) {
			GameDetails savedGame = optSavedGame.get();
			if (savedGame.getPlaybackEndTime().compareTo(gameDetails.getPlaybackEndTime()) != 0  ||
					savedGame.getPlaybackStartTime().compareTo(gameDetails.getPlaybackStartTime()) != 0 ||
						savedGame.getGameInterval().compareTo(gameDetails.getGameInterval()) != 0) {
				histOrderRepository.deleteByGameId(gameDetails.getGameId());
				Integer playbackFrequency = orderGenService.fetchRecordsBetweenDatesAndCalFrequency(gameDetails);
				gameDetails.setPlaybackFrequency(playbackFrequency);
			}
			return gameDetailsRepository.save(gameDetails);
		} else
			return null;
		
	}

	public void deleteGameDetails(Integer gameId) {
		gameSessionRepository.deleteGameDetails(gameId);
		gameDetailsRepository.deleteById(gameId);
		
	}

	public void updateGameDetails(GameDetails savedGame) {
		gameDetailsRepository.save(savedGame);
	}
	
}
