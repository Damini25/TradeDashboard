package com.trdsimul.ordermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.ordermgmt.model.entity.OrderDetails;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Integer> {

	
	@Query( value = "SELECT od.* FROM Order_Details od WHERE od.game_Id = :gameId and od.product_Id = :productId "
			+ "and od.bid_Offer = 'Bid' ORDER BY od.order_time DESC, od.price DESC LIMIT :limit", nativeQuery = true )
	List<OrderDetails> fetchBuyOrderDetails(@Param("gameId") Integer gameId, @Param("productId") Integer productId,
			@Param("limit") Integer limit);

	@Query( value = "SELECT od.* FROM Order_Details od WHERE od.game_Id = :gameId and od.product_Id = :productId "
			+ "and od.bid_Offer = 'Ask' ORDER BY od.order_time DESC, od.price ASC LIMIT :limit", nativeQuery = true)
	List<OrderDetails> fetcAskOrderDetails(@Param("gameId") Integer gameId, @Param("productId") Integer productId
			, @Param("limit") Integer limit);

	@Query( value = "Delete from OrderDetails od where od.gameId = :gameId and od.productId = :productId")
		void deleteOrdersByProductAndGame(@Param("gameId") Integer gameId, @Param("productId") Integer productId);
	/*
	 * @Query() void checkForTradeMatch();
	 */

}
