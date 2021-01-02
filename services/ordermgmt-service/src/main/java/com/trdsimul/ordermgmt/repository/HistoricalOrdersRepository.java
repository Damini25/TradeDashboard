package com.trdsimul.ordermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.ordermgmt.dto.GraphResponseDTO;
import com.trdsimul.ordermgmt.model.entity.HistoricalOrderDetails;

public interface HistoricalOrdersRepository extends CrudRepository<HistoricalOrderDetails, Long> {

	@Query("Select histOrders from HistoricalOrderDetails histOrders ORDER BY histOrders.histOrderId")
	Iterable<HistoricalOrderDetails> findAllOrderByTime();

	@Query("select new com.trdsimul.ordermgmt.dto.GraphResponseDTO(ROUND(SUM(hod.price)/COUNT(*),2), from_unixtime(60 + unix_timestamp(hod.eventDate) - unix_timestamp(hod.eventDate)%60)) from HistoricalOrderDetails hod where hod.bidOffer = :bidOfferType and hod.productId = :productId group by FLOOR(unix_timestamp(hod.eventDate) / 60), hod.productId, hod.bidOffer ")
	List<GraphResponseDTO> fetchGraphData(
			/* @Param("gapTime") Long gapTime, */@Param("bidOfferType") String bidOfferType, @Param("productId") Integer productId);

	@Query("select DISTINCT(hod.productId) from HistoricalOrderDetails hod where hod.gameId = :gameId")
	List<Integer> allgameproducts(@Param("gameId") Integer gameId);

	@Query("select hod from HistoricalOrderDetails hod where hod.gameId = :gameId")
	List<HistoricalOrderDetails> findAllByGameId(@Param("gameId") Integer gameId);

}
