package com.trdsimul.gamemgmt.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.gamemgmt.model.entity.MasterHistoricalOrder;

public interface MasterHistoricalOrderRepository extends CrudRepository<MasterHistoricalOrder, Integer> {

	@Query(" select DISTINCT(DATE(homd.eventDate)) as avldates from MasterHistoricalOrder homd")
	List<Date> fetchDateWiseData();

	@Query(" select homd from MasterHistoricalOrder homd where homd.eventDate >= :startTime AND homd.eventDate <= :endTime")
	List<MasterHistoricalOrder> fetchBetweenDatesInterval(@Param("startTime") Timestamp playbackStartTime, @Param("endTime") Timestamp playbackEndTime);

}
