package com.trdsimul.gamemgmt.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.gamemgmt.model.entity.UsersGameDetails;

public interface UserGameDetailsRepository extends CrudRepository<UsersGameDetails, Integer> {

	@Query("select ugd from UsersGameDetails ugd where ugd.userId = :userId and ugd.isActive = true")
	Optional<UsersGameDetails> checkUserIsActiveForAnyGameSession(@Param("userId")Integer userId);

	@Modifying
	@Transactional
	@Query("update UsersGameDetails ugd set ugd.isActive = false where ugd.gameId = :gameId and ugd.isActive = true")
	void updateStatusForAllUsers(@Param("gameId") Integer gameId);

	@Modifying
	@Transactional
	@Query("delete from UsersGameDetails ugd where ugd.gameId = :gameId ")
	void deleteByGameId(@Param("gameId") Integer gameId);

	@Query(" select ugd from UsersGameDetails ugd where ugd.gameId = :gameId and ugd.isActive = true ")
	List<UsersGameDetails> allActiveUsersForGame(@Param("gameId") Integer gameId);

	@Query(" select ugd from UsersGameDetails ugd where ugd.gameId = :gameId and ugd.isActive = true and ugd.userId = :userId ")
	Optional<UsersGameDetails> findUserStatus(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

}
