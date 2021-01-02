package com.trdsimul.ordermgmt.repository;

import org.springframework.data.repository.CrudRepository;

import com.trdsimul.ordermgmt.model.entity.ProductDetails;

public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Integer> {

}
