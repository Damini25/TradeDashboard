package com.trdsimul.ordermgmt.ordergen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trdsimul.ordermgmt.dto.DashBResponse;
import com.trdsimul.ordermgmt.model.entity.ProductDetails;
import com.trdsimul.ordermgmt.ordergen.service.OrderGenService;
import com.trdsimul.ordermgmt.service.ProductService;

/**
 * Order Generator Controller Class.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/ordergen/")
public class OrderGenController {

	@Autowired
	OrderGenService orderGenService;
	
	@Autowired
	ProductService productService;

	/**
	 * Function - It will generate orders on custom fixed interval and push into order table.
	 *
	 * @throws CloneNotSupportedException the clone not supported exception
	 * @throws InterruptedException the interrupted exception
	 */
	@GetMapping(path = "/generateorders")
	public void generateOrders() throws CloneNotSupportedException, InterruptedException {

//		orderGenService.generateOrdersOnFixedInterval();
		
	}
	
	@PostMapping(path = "/allgameproducts")
	public DashBResponse allgameproducts(@RequestHeader("gameId") Integer gameId) {
		List<ProductDetails> products = new ArrayList<ProductDetails>();
		List<Integer> productIds = orderGenService.allgameproducts(gameId);
		for (Integer productId : productIds) {
			products.add(productService.fetchProductDetails(productId));
		}
		DashBResponse dashBResponse = new DashBResponse();
		dashBResponse.setSuccess(true);
		dashBResponse.setError(null);
		dashBResponse.setData(products);
		return dashBResponse;
	}
}
