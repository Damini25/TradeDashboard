package com.trdsimul.gamemgmt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.gamemgmt.model.entity.HistoricalOrderDetails;

public interface HistoricalOrdersRepository extends CrudRepository<HistoricalOrderDetails, Long> {

	@Query("Select histOrders from HistoricalOrderDetails histOrders ORDER BY histOrders.histOrderId")
	Iterable<HistoricalOrderDetails> findAllOrderByTime();

	@Modifying
	@Transactional
	@Query("Delete from HistoricalOrderDetails hod where hod.gameId = :gameId")
	void deleteByGameId(@Param("gameId") Integer gameId);

}
