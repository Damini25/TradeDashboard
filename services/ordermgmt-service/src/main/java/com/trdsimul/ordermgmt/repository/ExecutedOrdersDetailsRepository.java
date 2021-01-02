package com.trdsimul.ordermgmt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.ordermgmt.model.entity.ExecutedOrdersDetails;

public interface ExecutedOrdersDetailsRepository extends CrudRepository<ExecutedOrdersDetails, Integer> {

	@Query( value = "Delete from ExecutedOrdersDetails od where od.gameId = :gameId and od.productId = :productId")
	void deleteOrdersByProductAndGame(@Param("gameId") Integer gameId, @Param("productId") Integer productId);
}
