package com.trdsimul.gamemgmt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.gamemgmt.model.entity.GameSession;

public interface GameSessionRepository extends CrudRepository<GameSession, String> {

	@Query("select gs from GameSession gs where gs.gameId = :gameId and gs.isActive = true")
	GameSession findByGameId(@Param("gameId") Integer gameId);

	@Modifying
	@Transactional
	@Query("update GameSession gs set gs.isActive = false where gs.gameId = :gameId and gs.isActive = true")
	void updateGameSessionStatus(@Param("gameId") Integer gameId);

	@Modifying
	@Transactional
	@Query("delete from GameSession gs where gs.gameId = :gameId")
	void deleteGameDetails(@Param("gameId") Integer gameId);

}
