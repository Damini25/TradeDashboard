/**********************************************************************
Project:                        
Class Name:                    
Class Type:              
Author:       
Description:                    	
Dependencies:              
 Revision History:     
   <File version> <Change Request> <Author> <Revision Date> <Description of Change>
**********************************************************************/
 package com.trdsimul.ordermgmt.controller;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.trdsimul.ordermgmt.dto.BidAskOrderDTO;
import com.trdsimul.ordermgmt.dto.DashBInputDTO;
import com.trdsimul.ordermgmt.dto.DashBResponse;
import com.trdsimul.ordermgmt.dto.DashBResponseSingleObject;
import com.trdsimul.ordermgmt.dto.ErrorMessage;
import com.trdsimul.ordermgmt.dto.PnlAndPositionsDTO;
import com.trdsimul.ordermgmt.dto.ProductResponseDTO;
import com.trdsimul.ordermgmt.model.entity.HistoricalProductMapping;
import com.trdsimul.ordermgmt.ordergen.service.OrderGenService;
import com.trdsimul.ordermgmt.service.ProductService;
import com.trdsimul.ordermgmt.service.UserDashBService;


/**
 * The Class UserDashbController.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/userdashb")
public class UserDashbController {

	/** The dash B service. */
	@Autowired
	UserDashBService dashBService;

	/** The product service. */
	@Autowired
	ProductService productService;

	@Autowired
	OrderGenService orderGenService;
	
	@Autowired
	RestTemplate restTemplate;
	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(UserDashbController.class);

	/**
	 * Fetch orders for bid/ask screen.
	 *
	 * @param inputDTO the inputDTO class object
	 * @return the dashBresponse class object
	 * @throws InterruptedException 
	 * @throws CloneNotSupportedException 
	 */
	@PostMapping(path = "/bidaskscreen/fetch", consumes = "application/json", produces = "application/json")
	public DashBResponseSingleObject fetchOrders(@RequestBody DashBInputDTO inputDTO, @RequestHeader("gameId") Integer gameId,  @RequestHeader("userId") Integer userId, @RequestHeader("gameSessionId") String gameSessionId) throws CloneNotSupportedException, InterruptedException {
		DashBResponseSingleObject dashBResponse = new DashBResponseSingleObject();
		URI uri = buildRestTemplateForCheckingGameSession(gameSessionId);
		Boolean status = restTemplate.getForObject(uri, Boolean.class);
		if (status) {
			Boolean playbackStatus = checkPlayBackFlag(gameId);
			BidAskOrderDTO dto = dashBService.fetchOrders(gameId, inputDTO);
			dto.setPlaybackFlag(playbackStatus);
			dashBResponse.setSuccess(true);
			dashBResponse.setError(null);
			dashBResponse.setData(dto);
		} else {
			dashBService.flushData();
			ErrorMessage message = new ErrorMessage();
			message.setKey("gameSessionEnded");
			dashBResponse.setSuccess(null);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
		}
		return dashBResponse;
	}

	private Boolean checkPlayBackFlag(Integer gameId) {
		String url = "http://gamemgmt-service/game/checkPlaybackFlag";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
		        .queryParam("gameId", gameId);
		
		URI uri = builder.build().toUri();
		DashBResponseSingleObject respObj = restTemplate.getForObject(uri, DashBResponseSingleObject.class);
		LinkedHashMap<Boolean, Boolean> dto =  (LinkedHashMap<Boolean, Boolean>) respObj.getData();
		return (Boolean) dto.get(dto.keySet().toArray()[0]);
	}

	private URI buildRestTemplateForCheckingGameSession(String sessionId) {
		String url = "http://gamemgmt-service/game/isgamelive";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        .queryParam("gameSessionId", sessionId);
		return builder.build().toUri();
	}

	/**
	 * List all products.
	 *
	 * @return the dashBresponse class object
	 */
	@GetMapping(path = "/products")
	public DashBResponse allProducts() {
		List<ProductResponseDTO> productDTOs = productService.getAllProducts();
		DashBResponse dashBResponse = new DashBResponse();
		dashBResponse.setSuccess(true);
		dashBResponse.setError(null);
		dashBResponse.setData(productDTOs);
		return dashBResponse;
	}
	
	@PostMapping(path = "/portfolio", consumes = "application/json", produces = "application/json")
	public DashBResponseSingleObject fetchPortfolioDetails(@RequestHeader("gameId") Integer gameId,
			@RequestHeader("userId") Integer userId, @RequestHeader("gameSessionId") String gameSessionId) {
		URI uri = buildRestTemplateForCheckingGameSession(gameSessionId);
		Boolean status = restTemplate.getForObject(uri, Boolean.class);
		DashBResponseSingleObject dashBResponse = new DashBResponseSingleObject();
		if (status) {
			List<Integer> productIds = orderGenService.allgameproducts(gameId);
			PnlAndPositionsDTO pnlAndPortfolioDTO = dashBService.fetchPortfolioDetails(gameId, userId, productIds);
			dashBResponse.setSuccess(true);
			dashBResponse.setError(null);
			dashBResponse.setData(pnlAndPortfolioDTO);
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
	
	@GetMapping(path = "/findproductmapping")
	public List<HistoricalProductMapping> fetchProductMapping() {
		return productService.fetchProductMapping();
	}
}
