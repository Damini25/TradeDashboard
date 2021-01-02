package com.trdsimul.ordermgmt.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.trdsimul.ordermgmt.comparator.AskComparator;
import com.trdsimul.ordermgmt.comparator.BidComparator;
import com.trdsimul.ordermgmt.comparator.OrderExecTimeComparator;
import com.trdsimul.ordermgmt.comparator.OrderTimeComparator;
import com.trdsimul.ordermgmt.dto.DashBInputDTO;
import com.trdsimul.ordermgmt.dto.OrderBookDTO;
import com.trdsimul.ordermgmt.dto.PortfolioDTO;
import com.trdsimul.ordermgmt.enums.BidAsk;
import com.trdsimul.ordermgmt.enums.OrderStatus;
import com.trdsimul.ordermgmt.enums.OrderType;
import com.trdsimul.ordermgmt.model.entity.ExecutedOrdersDetails;
import com.trdsimul.ordermgmt.model.entity.OrderDetails;
import com.trdsimul.ordermgmt.model.entity.ProductDetails;
import com.trdsimul.ordermgmt.model.entity.UsersGameDetails;

/**
 * The Class Order Service.
 */

public class OrderServiceImpl implements Cloneable {

	static int orderCounter = 1;
	public Integer productId = 0;
	private List<OrderDetails> bidOffersOpen = new CopyOnWriteArrayList<OrderDetails>();
	private List<OrderDetails> askOffersOpen = new CopyOnWriteArrayList<OrderDetails>();
	private List<ExecutedOrdersDetails> executedOrders = new CopyOnWriteArrayList<ExecutedOrdersDetails>();
	static Map<Integer, Double> gameUsersBalance = new ConcurrentHashMap<Integer, Double>();
	static Map<Integer, Double> gameUsersVolume = new ConcurrentHashMap<Integer, Double>();
	static String volumeGameMode = null;

	/**
	 * Book new order.
	 *
	 * @param order the order
	 * @param gameUsers 
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	public void bookNewOrder(OrderDetails order, List<UsersGameDetails> users) throws CloneNotSupportedException {
		addAnyNewUser(users);
		order.setOrderId(orderCounter++);
		checkOrderType(order);
	}

	@SuppressWarnings("static-access")
	private void addAnyNewUser(List<UsersGameDetails> users) {
		for (UsersGameDetails user : users) {
			if (!this.gameUsersBalance.containsKey(user.getUserId())) {
				this.gameUsersBalance.put(user.getUserId(), user.getAvailableBalance());
			}
			if (!this.gameUsersVolume.containsKey(user.getUserId())) {
				this.gameUsersVolume.put(user.getUserId(), (user.getAvailableVolume()!= null ? user.getAvailableVolume() : 0d));
			}
		}
	}

	/**
	 * Check order type.
	 *
	 * @param order the order
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	public void checkOrderType(OrderDetails order) throws CloneNotSupportedException {
		if (order.getOrderTypeId() == OrderType.MARKET.getOrderStatusId()) {
			pickMarketOrdersForTradeMatch(order);
		} else if(order.getOrderTypeId() == OrderType.LIMIT_ORDER.getOrderStatusId()) {
			pickLimitOrdersForTradeMatch(order);
		}

	}

	/**
	 * Pick market orders for trade match.
	 *
	 * @param newOrder the order
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	public void pickMarketOrdersForTradeMatch(OrderDetails newOrder) throws CloneNotSupportedException {
		
		Double latestMarketPrice = fetchLatestMarketPrice(newOrder.getProductId(), newOrder.getBidOffer().equals(BidAsk.Ask.toString()));
		newOrder.setPrice(latestMarketPrice);


		Collections.sort(askOffersOpen, new AskComparator());
		Collections.sort(bidOffersOpen, new BidComparator());

		Double newQuantity = newOrder.getUnfulfilledQuantity();
		if (newOrder.getBidOffer().equalsIgnoreCase(BidAsk.Bid.toString())) {
			for (OrderDetails existingOrder : askOffersOpen) {
				if (!existingOrder.getTraderId().equals(newOrder.getTraderId())) {
					if (newOrder.getUnfulfilledQuantity() > 0 && newOrder.getPrice() >= existingOrder.getPrice() && !newQuantity.equals(0d)) {
							Double existingQuantity = existingOrder.getUnfulfilledQuantity();
							if (newQuantity >= existingQuantity) {
								newQuantity = newQuantity - existingQuantity;
								addExecutedOrder(newOrder, existingQuantity, existingOrder);
								removeAskOrder(newOrder, existingQuantity, existingOrder);
								updateUserBalance(newOrder.getTraderId(), existingOrder.getTraderId(), existingQuantity, existingOrder.getPrice());
								if(volumeGameMode != null) { updateUserVolume(newOrder.getTraderId(), existingOrder.getTraderId(), existingQuantity); }
							} else { // 20 < 30
								removeAskOrder(newOrder, newQuantity, existingOrder);
								updateUserBalance(newOrder.getTraderId(), existingOrder.getTraderId(), newQuantity, existingOrder.getPrice());
								if(volumeGameMode != null) { updateUserVolume(newOrder.getTraderId(), existingOrder.getTraderId(), newQuantity); }
								newQuantity = 0d;
							}
						}
					}
				}
				if (newQuantity > 0) {
					if(newQuantity.compareTo(newOrder.getTotalQty()) != 0) {
						OrderDetails sysGenOrder = createNewSystemGenOrder(newQuantity, newOrder);
						addBidOrder(sysGenOrder);
					} else {
						addBidOrder(newOrder);
					}
				}

		} else {
			for (OrderDetails existingOrder : bidOffersOpen) {
				if (!existingOrder.getTraderId().equals(newOrder.getTraderId())) {
					if (newOrder.getUnfulfilledQuantity() > 0 // && newOrder.getPrice() <= existingOrder.getPrice()
							&& !newQuantity.equals(0d)) {
						Double existingQuantity = existingOrder.getUnfulfilledQuantity();
						if (newQuantity >= existingQuantity) {
							newQuantity = newQuantity - existingQuantity;
							addExecutedOrder(newOrder, existingQuantity, existingOrder);
							removeBidOrder(newOrder, existingQuantity, existingOrder);
							updateUserBalance(newOrder.getTraderId(), existingOrder.getTraderId(), existingQuantity, existingOrder.getPrice());
							if(volumeGameMode != null) { updateUserVolume(newOrder.getTraderId(), existingOrder.getTraderId(), existingQuantity); }
						} else {
							removeBidOrder(newOrder, newQuantity, existingOrder);
							updateUserBalance(newOrder.getTraderId(), existingOrder.getTraderId(), newQuantity, existingOrder.getPrice());
							if(volumeGameMode != null) { updateUserVolume(newOrder.getTraderId(), existingOrder.getTraderId(), newQuantity); }
							newQuantity = 0d;
						}
					}
				}
			}
			if (newQuantity > 0) {
				if(newQuantity.compareTo(newOrder.getTotalQty()) != 0) {
					OrderDetails sysGenOrder = createNewSystemGenOrder(newQuantity, newOrder);
					addAskOffer(sysGenOrder);
				} else {
					addAskOffer(newOrder);
				}
			}
		}
	}

	/**
	 * Pick limit orders for trade match. Instead of news being shown as ribbon, it
	 * should be shown as static item and stay for few seconds or a minute based on
	 * the news timestamp.
	 * 
	 * @param newOrder the new order
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	public void pickLimitOrdersForTradeMatch(OrderDetails newOrder) throws CloneNotSupportedException {
		Collections.sort(askOffersOpen, new AskComparator());
		Collections.sort(bidOffersOpen, new BidComparator());

		Double newQuantity = newOrder.getUnfulfilledQuantity();
		if (newOrder.getBidOffer().equalsIgnoreCase(BidAsk.Bid.toString())) {
			for (OrderDetails existingOrder : askOffersOpen) {
				if (!existingOrder.getTraderId().equals(newOrder.getTraderId())) {
					if (newOrder.getUnfulfilledQuantity() > 0 && newOrder.getPrice() >= existingOrder.getPrice() && !newQuantity.equals(0d)) {
						Double existingQuantity = existingOrder.getUnfulfilledQuantity();
						if (newQuantity >= existingQuantity) {
							newQuantity = newQuantity - existingQuantity;
							addExecutedOrder(newOrder, existingQuantity, existingOrder);
							removeAskOrder(newOrder, existingQuantity, existingOrder);
							updateUserBalance(newOrder.getTraderId(), existingOrder.getTraderId(), existingQuantity, existingOrder.getPrice());
							if(volumeGameMode != null) { updateUserVolume(newOrder.getTraderId(), existingOrder.getTraderId(), existingQuantity); }
						} else { // 20 < 30
							removeAskOrder(newOrder, newQuantity, existingOrder);
							updateUserBalance(newOrder.getTraderId(), existingOrder.getTraderId(), newQuantity, existingOrder.getPrice());
							if(volumeGameMode != null) { updateUserVolume(newOrder.getTraderId(), existingOrder.getTraderId(), newQuantity); }
							newQuantity = 0d;
						}
					}
				}
			}
			if (newQuantity > 0) {
				if(newQuantity.compareTo(newOrder.getTotalQty()) != 0) {
					OrderDetails sysGenOrder = createNewSystemGenOrder(newQuantity, newOrder);
					addBidOrder(sysGenOrder);
				} else {
					addBidOrder(newOrder);
				}
			}

		} else {
			for (OrderDetails existingOrder : bidOffersOpen) {
				if (!existingOrder.getTraderId().equals(newOrder.getTraderId())) {
					if (newOrder.getUnfulfilledQuantity() > 0 && newOrder.getPrice() <= existingOrder.getPrice()
							&& !newQuantity.equals(0d)) {
						Double existingQuantity = existingOrder.getUnfulfilledQuantity();
						if (newQuantity >= existingQuantity) {
							newQuantity = newQuantity - existingQuantity;
							addExecutedOrder(newOrder, existingQuantity, existingOrder);
							removeBidOrder(newOrder, existingQuantity, existingOrder);
							updateUserBalance(existingOrder.getTraderId(), newOrder.getTraderId(), existingQuantity, existingOrder.getPrice());
							if(volumeGameMode != null) { updateUserVolume(newOrder.getTraderId(), existingOrder.getTraderId(), existingQuantity); }
						} else {
							removeBidOrder(newOrder, newQuantity, existingOrder);
							updateUserBalance(existingOrder.getTraderId(), newOrder.getTraderId(), newQuantity, existingOrder.getPrice());
							if(volumeGameMode != null) { updateUserVolume(newOrder.getTraderId(), existingOrder.getTraderId(), newQuantity); }
							newQuantity = 0d;
						}
					}
				}
			}
			if (newQuantity > 0) {
				if(newQuantity.compareTo(newOrder.getTotalQty()) != 0) {
					OrderDetails sysGenOrder = createNewSystemGenOrder(newQuantity, newOrder);
					addAskOffer(sysGenOrder);
				} else {
					addAskOffer(newOrder);
				}
			}
		}
	}

	private void updateUserBalance(Integer bidTraderId, Integer askTraderId, Double existingQuantity, Double price) {
		Double bidAvlBalance = gameUsersBalance.get(bidTraderId);
		Double askAvlBalance = gameUsersBalance.get(askTraderId);
		bidAvlBalance = bidAvlBalance - (price * existingQuantity);
		askAvlBalance = askAvlBalance + (price * existingQuantity);
		BigDecimal bidAvlBalanceBD = BigDecimal.valueOf(bidAvlBalance);
		BigDecimal askAvlBalanceBD = BigDecimal.valueOf(askAvlBalance);
		bidAvlBalanceBD = bidAvlBalanceBD.setScale(2 , RoundingMode.HALF_UP);
		askAvlBalanceBD = askAvlBalanceBD.setScale(2 , RoundingMode.HALF_UP);
		gameUsersBalance.put(bidTraderId, bidAvlBalanceBD.doubleValue());
		gameUsersBalance.put(askTraderId, askAvlBalanceBD.doubleValue());
	}
	
	private void updateUserVolume(Integer bidTraderId, Integer askTraderId, Double quantity) {
		Double bidAvlVolume = gameUsersVolume.get(bidTraderId);
		Double askAvlVolume = gameUsersVolume.get(askTraderId);
		if (volumeGameMode.equalsIgnoreCase(BidAsk.Ask.toString())) {
			bidAvlVolume =+ quantity;
			askAvlVolume =- quantity;
		}
		else {
			bidAvlVolume =- quantity;
			askAvlVolume =+ quantity;
		}
		gameUsersBalance.put(bidTraderId, bidAvlVolume);
		gameUsersBalance.put(askTraderId, askAvlVolume);		
	}

	/**
	 * Adds the executed order.
	 * 
	 * @param newOrder
	 * @param newQuantity
	 *
	 * @param existingOrder the existing order
	 */
	public void addExecutedOrder(OrderDetails newOrder, Double newQuantity, OrderDetails existingOrder) {
		ExecutedOrdersDetails newExecOrder = new ExecutedOrdersDetails();
		if (existingOrder.getBidOffer().equalsIgnoreCase(BidAsk.Ask.toString())) {
			newExecOrder.setAskTraderId(existingOrder.getTraderId());
			newExecOrder.setAskOrderId(existingOrder.getOrderId());
			newExecOrder.setBidTraderId(newOrder.getTraderId());
			newExecOrder.setBidOrderId(newOrder.getOrderId());
		} else {
			newExecOrder.setBidTraderId(existingOrder.getTraderId());
			newExecOrder.setBidOrderId(existingOrder.getOrderId());			
			newExecOrder.setAskTraderId(newOrder.getTraderId());
			newExecOrder.setAskOrderId(newOrder.getOrderId());
		}
		newExecOrder.setPrice(existingOrder.getPrice());
		newExecOrder.setProductId(existingOrder.getProductId());
		newExecOrder.setOrderStatusId(OrderStatus.EXECUTED.getOrderStatusId());
		newExecOrder.setGameId(existingOrder.getGameId());
		newExecOrder.setOrderExecutionTime(new Date());
		newExecOrder.setTotalQty(newQuantity);
		executedOrders.add(newExecOrder);
		// saveExecutedOrders(newExecOrder);
	}

	/**
	 * Adds the bid order.
	 *
	 * @param orderDetails the order details
	 */
	public void addBidOrder(OrderDetails orderDetails) {
		bidOffersOpen.add(orderDetails);
		// saveOrder(orderDetails);

	}

	/**
	 * Adds the ask offer.
	 *
	 * @param orderDetails the order details
	 */
	public void addAskOffer(OrderDetails orderDetails) {
		askOffersOpen.add(orderDetails);
		// saveOrder(orderDetails);
	}

	/**
	 * Removes the ask order.
	 *
	 * @param quantity     the quantity
	 * @param orderDetails the order details
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	public void removeAskOrder(OrderDetails newOrder, Double quantity, OrderDetails orderDetails)
			throws CloneNotSupportedException {
		double askQuantity = orderDetails.getUnfulfilledQuantity();
		if (askQuantity == quantity) {
			orderDetails.setOrderStatusId(OrderStatus.EXECUTED.getOrderStatusId());
			orderDetails.setUnfulfilledQuantity(askQuantity - quantity);
			askOffersOpen.remove(orderDetails);
			// saveOrder(orderDetails);
		} else {
			Double newQty = askQuantity - quantity; // 10
			OrderDetails sysGenOrder = createNewSystemGenOrder(newQty, orderDetails);
			addExecutedOrder(newOrder, quantity, orderDetails);
			askOffersOpen.remove(orderDetails);
			addAskOffer(sysGenOrder);
			orderDetails.setOrderStatusId(OrderStatus.PARTIAL_EXECUTED.getOrderStatusId());
			// saveOrder(orderDetails);
		}
	}

	/**
	 * Removes the bid order.
	 * 
	 * @param newOrder
	 *
	 * @param quantity     the quantity
	 * @param orderDetails the order details
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	public void removeBidOrder(OrderDetails newOrder, Double quantity, OrderDetails orderDetails)
			throws CloneNotSupportedException {
		double askQuantity = orderDetails.getUnfulfilledQuantity();
		if (askQuantity == quantity) {
			orderDetails.setOrderStatusId(OrderStatus.EXECUTED.getOrderStatusId());
			orderDetails.setUnfulfilledQuantity(askQuantity - quantity);
			bidOffersOpen.remove(orderDetails);
			// saveOrder(orderDetails);
		} else {
			Double newQty = askQuantity - quantity;
			OrderDetails sysGenOrder = createNewSystemGenOrder(newQty, orderDetails);
			addExecutedOrder(newOrder, quantity, orderDetails);
			bidOffersOpen.remove(orderDetails);
			addBidOrder(sysGenOrder);
			orderDetails.setOrderStatusId(OrderStatus.PARTIAL_EXECUTED.getOrderStatusId());
			// saveOrder(orderDetails);
		}
	}

	/**
	 * Creates the new system generated order.
	 *
	 * @param newQuantity  the new quantity
	 * @param orderDetails the order details
	 * @return the order details
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	OrderDetails createNewSystemGenOrder(Double newQuantity, OrderDetails orderDetails)
			throws CloneNotSupportedException {
		OrderDetails sysGenOrder = (OrderDetails) orderDetails.clone();
		// sysGenOrder.setTotalQty(newQuantity);
		sysGenOrder.setUnfulfilledQuantity(newQuantity);
		sysGenOrder.setOrigOrderId(orderDetails.getOrderId());
		sysGenOrder.setOrderId(orderCounter++);
		return sysGenOrder;
	}

	/**
	 * Flush orders.
	 *
	 * @param productId the product id
	 */
	public void flushOrders(Integer productId) {
		this.askOffersOpen.clear();
		this.bidOffersOpen.clear();
		this.executedOrders.clear();
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
		OrderBookDTO orderBookDTO = new OrderBookDTO();
		List<OrderDetails> allOrders = new ArrayList<OrderDetails>(inputDTO.getNoOfRows());
		List<ExecutedOrdersDetails> allTrades = new ArrayList<ExecutedOrdersDetails>(inputDTO.getNoOfRows());
		Integer totalOrdersCount = inputDTO.getNoOfRows() * 2;
		for (OrderDetails orderDetails : askOffersOpen) {
			if (orderDetails.getProductId() == inputDTO.getProductId() && allOrders.size() <= inputDTO.getNoOfRows()
					&& orderDetails.getGameId().equals(gameId) && orderDetails.getTraderId().equals(userId)) {
				allOrders.add(orderDetails);
			}
		}
		for (OrderDetails orderDetails : bidOffersOpen) {
			if (orderDetails.getProductId() == inputDTO.getProductId() && allOrders.size() <= totalOrdersCount
					&& orderDetails.getGameId().equals(gameId) && orderDetails.getTraderId().equals(userId)) {
				allOrders.add(orderDetails);
			}
		}
		for (ExecutedOrdersDetails orderDetails : executedOrders) {
			if (orderDetails.getProductId() == inputDTO.getProductId() && allTrades.size() <= inputDTO.getNoOfRows() && orderDetails.getGameId().equals(gameId)) {
					if (orderDetails.getAskTraderId().equals(userId)) {
						orderDetails.setIsSelling(true);
					}
					else {
						orderDetails.setIsSelling(false);
					}
				allTrades.add(orderDetails);
			}
		}
		Collections.sort(allOrders, new OrderTimeComparator());
		Collections.sort(allTrades, new OrderExecTimeComparator());
		orderBookDTO.setAllOrders(allOrders);
		orderBookDTO.setAllTrades(allTrades);
		return orderBookDTO;
	}

	/**
	 * Fetch latest market price.
	 * 
	 * @param integer
	 * @param isAskOrder 
	 *
	 * @return the order details
	 */
	private Double fetchLatestMarketPrice(Integer integer, boolean isAskOrder) {
		Double executedOrderPrice = null;
		if (executedOrders != null) {
			for (int i = executedOrders.size() - 1; i >= 0; i--) {
				ExecutedOrdersDetails executedOrder = executedOrders.get(i);
				if (executedOrder.getProductId().equals(integer)) {
					executedOrderPrice = executedOrder.getPrice();
					break;
				}
			}
		}
		else if (isAskOrder)
			executedOrderPrice = !bidOffersOpen.isEmpty() ? bidOffersOpen.get(0).getPrice() : 0d;
		else
			executedOrderPrice = !askOffersOpen.isEmpty() ? askOffersOpen.get(0).getPrice() : 0d;
		return executedOrderPrice;
	}

	public List<OrderDetails> getBidOffersOpen() {
		return bidOffersOpen;
	}

	public List<OrderDetails> getAskOffersOpen() {
		return askOffersOpen;
	}

	public List<ExecutedOrdersDetails> getExecutedOrders() {
		return executedOrders;
	}

	private List<ExecutedOrdersDetails> getExecutedOrders(Integer traderId) {
		List<ExecutedOrdersDetails> executedOrdersByTrader = new ArrayList<ExecutedOrdersDetails>();
		for (int i = this.executedOrders.size() - 1; i >= 0; i--) {
			ExecutedOrdersDetails executedOrder = executedOrders.get(i);
			if (executedOrder.getAskTraderId().equals(traderId) || executedOrder.getBidTraderId().equals(traderId)) {
				executedOrdersByTrader.add(executedOrder);
			}
		}
		return executedOrdersByTrader;
	}

	public PortfolioDTO fetchPortfolioDetails(Integer userId, ProductDetails productDetails) {
		PortfolioDTO portfolio = new PortfolioDTO();
		List<ExecutedOrdersDetails> executedOrdersByTrader = getExecutedOrders(userId);
		if (!executedOrdersByTrader.isEmpty()) {
			portfolio.setTicker(productDetails.getProductCode());
			portfolio.setProductType(productDetails.getProductType());
			portfolio.setContractSize(productDetails.getContractSize());
			if (this.askOffersOpen != null && !this.askOffersOpen.isEmpty()) {
				portfolio.setAsk(this.askOffersOpen.get(0).getPrice());
			}
			if (this.bidOffersOpen != null && !this.bidOffersOpen.isEmpty()) {
				portfolio.setBid(this.bidOffersOpen.get(0).getPrice());
			}
			portfolio.setPosition(calculatePositions(userId, executedOrdersByTrader, productDetails.getContractSize()));
			portfolio.setCost(calculateVolWeightedAvgPrice(userId, executedOrdersByTrader));
			portfolio.setLast(executedOrders.get(0).getPrice());
			if(executedOrders.size() > 2)
			portfolio.setColorCoding(executedOrders.get(0).getPrice().compareTo(executedOrders.get(1).getPrice()));
			portfolio.setRealizedPnl(calculateRealizedPnl(userId, executedOrdersByTrader));
			portfolio.setUnrealizedPnl(calculateUnRealizedPnl(userId, executedOrdersByTrader));
		}
		return portfolio;

	}
	
	private Double calculateUnRealizedPnl(Integer userId, List<ExecutedOrdersDetails> executedOrdersByTrader) {
		
		Double askVolume = 0d; Double bidVolume = 0d;
		Double shortCurrentPositions = 0d; Double longCurrentPositions = 0d;
		Double avgEntryPrice = 0d;
		Double shortUnRealizedPnl = 0d;
		Double mktAskPrice = this.askOffersOpen.get(0).getPrice();

		for (ExecutedOrdersDetails ordersDetails : executedOrdersByTrader) {
			if (userId.equals(ordersDetails.getAskTraderId())) {
				shortCurrentPositions += (ordersDetails.getTotalQty() * ordersDetails.getPrice());
				askVolume += ordersDetails.getTotalQty();
			}
		}
		
		avgEntryPrice = longCurrentPositions / (bidVolume != 0 ? bidVolume : 1);
		shortUnRealizedPnl = askVolume * (avgEntryPrice - mktAskPrice);
		Double unrealizedPnl = longCurrentPositions + shortUnRealizedPnl ;
		BigDecimal unrealizedPnlBD = BigDecimal.valueOf(unrealizedPnl);
		unrealizedPnlBD = unrealizedPnlBD.setScale(2 , RoundingMode.HALF_UP);
		return unrealizedPnlBD.doubleValue();

	}

	private Double calculateRealizedPnl(Integer userId, List<ExecutedOrdersDetails> executedOrdersByTrader) {

		Double askVolume = 0d; Double bidVolume = 0d;
		Double shortCurrentPositions = 0d; Double longCurrentPositions = 0d;
		Double avgEntryPrice = 0d; Double avgExitPrice = 0d;
		Double shortRealizedPnl = 0d; Double longRealizedPnl = 0d;

		for (ExecutedOrdersDetails ordersDetails : executedOrdersByTrader) {
			if (userId.equals(ordersDetails.getAskTraderId())) {
				shortCurrentPositions += (ordersDetails.getTotalQty() * ordersDetails.getPrice());
				askVolume += ordersDetails.getTotalQty();
			} else {
				longCurrentPositions += (ordersDetails.getTotalQty() * ordersDetails.getPrice());
				bidVolume += ordersDetails.getTotalQty();
			}
		}
		
		avgEntryPrice = longCurrentPositions / (bidVolume != 0 ? bidVolume : 1) ;
		avgExitPrice = shortCurrentPositions / (askVolume != 0 ? askVolume : 1) ;
		shortRealizedPnl = askVolume * (avgEntryPrice - avgExitPrice);
		longRealizedPnl = bidVolume * (avgExitPrice - avgEntryPrice);
		Double realizedPnl = longRealizedPnl + shortRealizedPnl;
		BigDecimal realizedPnlBD = BigDecimal.valueOf(realizedPnl);
		realizedPnlBD = realizedPnlBD.setScale(2 , RoundingMode.HALF_UP);
		return realizedPnlBD.doubleValue();
	}

	private Double calculateVolWeightedAvgPrice(Integer userId, List<ExecutedOrdersDetails> executedOrdersList) {

		Double numerator = 0d;
		Double denominator = 0d;

		for (ExecutedOrdersDetails ordersDetails : executedOrdersList) {
			numerator += (ordersDetails.getTotalQty() * ordersDetails.getPrice());
			denominator += ordersDetails.getTotalQty();
		}
		Double vwap = numerator / denominator;
		BigDecimal vwapBD = BigDecimal.valueOf(vwap);
		vwapBD = vwapBD.setScale(2 , RoundingMode.HALF_UP);		
		return vwapBD.doubleValue();

	}

	private Double calculatePositions(Integer userId, List<ExecutedOrdersDetails> executedOrdersList, Integer contractSize) {

		Double longPositions = 0d;
		Double shortPositions = 0d;

		for (ExecutedOrdersDetails ordersDetails : executedOrdersList) {
			if (userId.equals(ordersDetails.getAskTraderId())) {
				shortPositions += ordersDetails.getTotalQty();
			} else {
				longPositions += ordersDetails.getTotalQty();
			}
		}
		Double positions = (longPositions + ((-1) * shortPositions)) * contractSize;
		BigDecimal positionsBD = BigDecimal.valueOf(positions);
		positionsBD = positionsBD.setScale(2 , RoundingMode.HALF_UP);		
		return positionsBD.doubleValue();
	}
	
	void flushData() {
		bidOffersOpen.clear();
		askOffersOpen.clear();
		executedOrders.clear();
		gameUsersBalance.clear();
		gameUsersVolume.clear();
	}

}
