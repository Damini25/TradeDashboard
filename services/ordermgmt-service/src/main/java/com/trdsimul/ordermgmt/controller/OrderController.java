package com.trdsimul.ordermgmt.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.trdsimul.ordermgmt.dto.DashBInputDTO;
import com.trdsimul.ordermgmt.dto.DashBResponse;
import com.trdsimul.ordermgmt.dto.ErrorMessage;
import com.trdsimul.ordermgmt.dto.OrderBookDTO;
import com.trdsimul.ordermgmt.enums.OrderStatus;
import com.trdsimul.ordermgmt.model.entity.OrderDetails;
import com.trdsimul.ordermgmt.service.OrderService;

/**
 * The Class OrderController.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/ordermgmt/")
public class OrderController {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	RestTemplate restTemplate;

	private static Logger LOG = LoggerFactory.getLogger(OrderController.class);

	Map<Integer, OrderService> productServiceMap = new ConcurrentHashMap<Integer, OrderService>();

	/**
	 * Gets the order service based on product.
	 *
	 * @param productId the product id
	 * @return the order service
	 */
	public synchronized OrderService getOrderService(Integer productId) {
		LOG.info("Fetching product details ... ");
		if (!productServiceMap.containsKey(productId)) {
			OrderService orderService = (OrderService) applicationContext.getBean("orderService");
			orderService.productId = productId;
			productServiceMap.put(productId, orderService);
			// order.flushOrders(productId);
		}
		OrderService order = productServiceMap.get(productId);
		return order;
	}

	/**
	 * Book new order.
	 *
	 * @param order the order
	 * @return the order details
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	@PostMapping(path = "/neworder", produces = "application/json")
	public DashBResponse bookNewOrder(@RequestBody OrderDetails order, @RequestHeader("gameId") Integer gameId,
			@RequestHeader("userId") Integer userId) {
		DashBResponse dashBResponse = new DashBResponse();
		ErrorMessage message = new ErrorMessage();
		LOG.info("Book new order : +");
		try {
			if (order.getUnfulfilledQuantity() == null) {
				order.setUnfulfilledQuantity(order.getTotalQty());
			}
			order.setGameId(gameId);
			order.setTraderId(userId);
			order.setOrderStatusId(OrderStatus.OPEN.getOrderStatusId());
			LOG.info("Order details : " + order);
			getOrderService(order.getProductId()).bookNewOrder(order);
			dashBResponse.setSuccess(true);
			dashBResponse.setError(null);
			dashBResponse.setData(null);
		} catch (Exception e) {
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
			LOG.error("Unknown Exception Occurred !!" + e);
		}
		return dashBResponse;
	}

	private URI buildRestTemplateForCheckingGameSession(String sessionId) {
		String url = "http://gamemgmt-service/game/isgamelive";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("gameSessionId", sessionId);
		return builder.build().toUri();
	}

	/**
	 * Fetch trader & order book.
	 *
	 * @param inputDTO the inputDTO class object
	 * @return the dashBresponse class object
	 */
	@PostMapping(path = "/orderbook", consumes = "application/json", produces = "application/json")
	public DashBResponse fetchTraderOrderBook(@RequestBody DashBInputDTO inputDTO,
			@RequestHeader("gameId") Integer gameId, @RequestHeader("userId") Integer userId,
			@RequestHeader("gameSessionId") String gameSessionId) {
		URI uri = buildRestTemplateForCheckingGameSession(gameSessionId);
		Boolean status = restTemplate.getForObject(uri, Boolean.class);
		DashBResponse dashBResponse = new DashBResponse();
		if (status) {
			List<OrderBookDTO> bookDTOs = new ArrayList<OrderBookDTO>();
			OrderBookDTO bookDTO = getOrderService(inputDTO.getProductId()).fetchTraderOrderBook(inputDTO, userId, gameId);
			bookDTOs.add(bookDTO);
			dashBResponse.setSuccess(true);
			dashBResponse.setError(null);
			dashBResponse.setData(bookDTOs);
		} else {
			ErrorMessage message = new ErrorMessage();
			message.setKey("gameSessionEnded");
			message.setErrorMessage("Game Session Ended");
			dashBResponse.setSuccess(null);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
		}
		return dashBResponse;
	}

}
