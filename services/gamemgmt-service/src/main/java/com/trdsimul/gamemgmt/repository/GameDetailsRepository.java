package com.trdsimul.gamemgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.gamemgmt.model.entity.GameDetails;

public interface GameDetailsRepository extends CrudRepository<GameDetails, Integer> {

	@Modifying
	@Transactional
	@Query("update GameDetails gd set gd.isGameActive = false, gd.playbackFlag = false where gd.gameId = :gameId and gd.isGameActive = true")
	void updateGameDetailsStatus(@Param("gameId") Integer gameId);

	@Query("select gd from GameDetails gd where gd.isGameActive = true")
	List<GameDetails> findAllActiveGames();

}
