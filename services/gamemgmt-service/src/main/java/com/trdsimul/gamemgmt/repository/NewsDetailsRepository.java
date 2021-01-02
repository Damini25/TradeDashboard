package com.trdsimul.gamemgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.gamemgmt.model.entity.NewsDetails;

public interface NewsDetailsRepository extends CrudRepository<NewsDetails, Integer> {

	@Query(" select nd from NewsDetails nd where nd.gameId = :gameId ")
	List<NewsDetails> findAllByGame(@Param("gameId") Integer gameId);

}
