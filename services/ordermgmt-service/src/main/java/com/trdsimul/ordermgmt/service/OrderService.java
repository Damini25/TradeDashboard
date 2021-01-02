package com.trdsimul.ordermgmt.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.trdsimul.ordermgmt.dto.DashBInputDTO;
import com.trdsimul.ordermgmt.dto.OrderBookDTO;
import com.trdsimul.ordermgmt.dto.PnlAndPositionsDTO;
import com.trdsimul.ordermgmt.dto.PortfolioDTO;
import com.trdsimul.ordermgmt.model.entity.OrderDetails;
import com.trdsimul.ordermgmt.model.entity.ProductDetails;
import com.trdsimul.ordermgmt.model.entity.UsersGameDetails;
import com.trdsimul.ordermgmt.repository.ExecutedOrdersDetailsRepository;
import com.trdsimul.ordermgmt.repository.OrderDetailsRepository;

/**
 * The Class Order Service.
 */
@Service
public class OrderService implements Cloneable {

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Autowired
	ExecutedOrdersDetailsRepository executedOrdersDetailsRepository;

	@Autowired
	ProductService productService;

	@Autowired
	RestTemplate restTemplate;
	
	private static Logger LOG = LoggerFactory.getLogger(OrderService.class);

	public Integer productId = 0;
	Map<Integer, OrderServiceImpl> productServiceMap = new ConcurrentHashMap<Integer, OrderServiceImpl>();
	static Map<Integer, Double> userStartingBalance = new HashMap<Integer, Double>();
	static Map<Integer, Double> userStartingVolume = new HashMap<Integer, Double>();
	private final ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);

	public OrderService () {
		executor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				saveIntoDB();
				
			}
		}, 10, 2, TimeUnit.SECONDS);
	}

	private void saveIntoDB() {
		Set<Integer> productIds = productServiceMap.keySet();
		for(Integer productId : productIds) {
			orderDetailsRepository.saveAll(getOrderServiceImpl(productId).getAskOffersOpen());
			orderDetailsRepository.saveAll(getOrderServiceImpl(productId).getBidOffersOpen());
			executedOrdersDetailsRepository.saveAll(getOrderServiceImpl(productId).getExecutedOrders());
		}
	}
	
	public synchronized OrderServiceImpl getOrderServiceImpl(OrderDetails order) {
		return getOrderServiceImpl(order.getProductId());
	}

	public synchronized OrderServiceImpl getOrderServiceImpl(Integer productId) {
		if (!productServiceMap.containsKey(productId)) {
			OrderServiceImpl orderService = new OrderServiceImpl();
			orderService.productId = productId;
			productServiceMap.put(productId, orderService);
		}
		OrderServiceImpl order = productServiceMap.get(productId);
		return order;
	}

	/**
	 * Book new order.
	 *
	 * @param order the order
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	public void bookNewOrder(OrderDetails order) throws CloneNotSupportedException {
		List<UsersGameDetails> gameUsers = fetchAllUsers(order.getGameId());
		if(!gameUsers.isEmpty()) {
			OrderServiceImpl.volumeGameMode = gameUsers.get(0).getBidAsk();
		}
		for(UsersGameDetails usersGameDetails : gameUsers) {
			userStartingBalance.put(usersGameDetails.getUserId(), usersGameDetails.getStartingBalance());
			userStartingVolume.put(usersGameDetails.getUserId(), usersGameDetails.getStartingVolume());
		}
		getOrderServiceImpl(order).bookNewOrder(order, gameUsers);
		
	}

	/**
	 * Fetch trader order book.
	 *
	 * @param inputDTO the input DTO
	 * @param gameId
	 * @param userId
	 * @return the order book DTO
	 */
	public OrderBookDTO fetchTraderOrderBook(DashBInputDTO inputDTO, Integer userId, Integer gameId) {
		return getOrderServiceImpl(inputDTO.getProductId()).fetchTraderOrderBook(inputDTO, userId, gameId);
	}


	public List<OrderDetails> getBidOffersOpen(Integer productId) {
		return getOrderServiceImpl(productId).getBidOffersOpen();
	}

	public List<OrderDetails> getAskOffersOpen(Integer productId) {

		return getOrderServiceImpl(productId).getAskOffersOpen();
	}
	
	public PnlAndPositionsDTO fetchPortfolioDetails(Integer userId, List<Integer> productIds) {
		PnlAndPositionsDTO pnlAndPositionsDTO = new PnlAndPositionsDTO();
		List<PortfolioDTO> portfolioDTOs = new ArrayList<PortfolioDTO>();
		Double realizedPnl = 0d;
		Double unrealizedPnl = 0d;
		for (Integer productId : productIds) {
			ProductDetails productDetails = productService.fetchProductDetails(productId);
			PortfolioDTO portfolioDTO = getOrderServiceImpl(productId).fetchPortfolioDetails(userId, productDetails);
			realizedPnl += portfolioDTO.getRealizedPnl() != null ? portfolioDTO.getRealizedPnl() : 0d;
			unrealizedPnl += portfolioDTO.getUnrealizedPnl() != null ? portfolioDTO.getUnrealizedPnl() : 0d ;
			portfolioDTOs.add(portfolioDTO);
			
		}
		
		Double availableBalance = OrderServiceImpl.gameUsersBalance.containsKey(userId) ? OrderServiceImpl.gameUsersBalance.get(userId) : 0d;
		Double startingBalance = userStartingBalance.containsKey(userId) ? userStartingBalance.get(userId) : 0d;
		
		Double availableVolume = OrderServiceImpl.gameUsersVolume.get(userId);
		Double startingVolume = userStartingVolume.get(userId);
		
		Double pnlValue = realizedPnl + unrealizedPnl + availableBalance - startingBalance;
		BigDecimal pnlValueBD = BigDecimal.valueOf(pnlValue);
		pnlValueBD = pnlValueBD.setScale(2 , RoundingMode.HALF_UP);
		pnlValue = pnlValueBD.doubleValue();

		
		pnlAndPositionsDTO.setStartingBalance(startingBalance);
		pnlAndPositionsDTO.setAvailableBalance(availableBalance);
		pnlAndPositionsDTO.setStartingVolume(startingVolume);
		pnlAndPositionsDTO.setAvailableVolume(availableVolume);
		pnlAndPositionsDTO.setPortfolioDtos(portfolioDTOs);
		pnlAndPositionsDTO.setPnlValue(pnlValue);
		return pnlAndPositionsDTO;
	}

	public List<UsersGameDetails> fetchAllUsers(Integer gameId) {
		URI uri = buildRestTemplateToFetchGameUsers(gameId);
		UsersGameDetails[] gameUsers = restTemplate.getForObject( uri, UsersGameDetails[].class);
		return Arrays.asList(gameUsers);
	}
	
	private URI buildRestTemplateToFetchGameUsers(Integer gameId) {
		String url = "http://gamemgmt-service/user/allGameUsers";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("gameId", gameId);
		return builder.build().toUri();
	}	
	
	public void flushData() {
		Set<Integer> productIds = productServiceMap.keySet();
		for(Integer product : productIds) {
			getOrderServiceImpl(product).flushData();
		}
	}

}
