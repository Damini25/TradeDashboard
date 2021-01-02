package com.trdsimul.ordermgmt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trdsimul.ordermgmt.comparator.AskComparator;
import com.trdsimul.ordermgmt.comparator.BidComparator;
import com.trdsimul.ordermgmt.dto.BidAskOrderDTO;
import com.trdsimul.ordermgmt.dto.DashBInputDTO;
import com.trdsimul.ordermgmt.dto.PnlAndPositionsDTO;
import com.trdsimul.ordermgmt.model.entity.OrderDetails;
import com.trdsimul.ordermgmt.ordergen.service.OrderGenService;
import com.trdsimul.ordermgmt.repository.HistoricalOrdersRepository;
import com.trdsimul.ordermgmt.repository.OrderDetailsRepository;

/**
 * The Class User DashB Service.
 */
@Service
public class UserDashBService {

	private static Logger LOG = LoggerFactory.getLogger(UserDashBService.class);

	@Autowired
	OrderDetailsRepository orderRepository;

	@Autowired
	HistoricalOrdersRepository historicalOrdersRepository;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderGenService orderGenService;

	/**
	 * Fetch Bid/Ask orders.
	 * 
	 * @param gameId
	 *
	 * @param inputDTO the inputDTO class object
	 * @return the Order Details list
	 * @throws InterruptedException
	 * @throws CloneNotSupportedException
	 */
	public BidAskOrderDTO fetchOrders(Integer gameId, DashBInputDTO inputDTO)
			throws CloneNotSupportedException, InterruptedException {
		BidAskOrderDTO bidAskOrderDTO = new BidAskOrderDTO();
		List<String> latestNewsList = orderGenService.generateOrdersOnFixedInterval(gameId);
		bidAskOrderDTO.setLatestNews(latestNewsList);
		List<OrderDetails> bidResponseList = new ArrayList<OrderDetails>();
		List<OrderDetails> askResponseList = new ArrayList<OrderDetails>();
		List<OrderDetails> bidOrdersList = orderService.getBidOffersOpen(inputDTO.getProductId());
		List<OrderDetails> askOrdersList = orderService.getAskOffersOpen(inputDTO.getProductId());
		Collections.sort(bidOrdersList, new BidComparator());
		Collections.sort(askOrdersList, new AskComparator());
		for (OrderDetails orderDetails : askOrdersList) {
			if (orderDetails.getProductId() == inputDTO.getProductId()
					&& askResponseList.size() <= inputDTO.getNoOfRows() && orderDetails.getGameId().equals(gameId)) {
				askResponseList.add(orderDetails);
			}
		}
		for (OrderDetails orderDetails : bidOrdersList) {
			if (orderDetails.getProductId() == inputDTO.getProductId()
					&& bidResponseList.size() <= inputDTO.getNoOfRows() && orderDetails.getGameId().equals(gameId)) {
				bidResponseList.add(orderDetails);
			}
		}
		bidAskOrderDTO.setAllAskOrders(askResponseList);
		bidAskOrderDTO.setAllBidOrders(bidResponseList);
		return bidAskOrderDTO;
	}

	public PnlAndPositionsDTO fetchPortfolioDetails(Integer gameId, Integer userId, List<Integer> productIds) {

		return orderService.fetchPortfolioDetails(userId, productIds);
	}

	public void flushData() {
		orderGenService.flushData();
		orderService.flushData();
		
	}

}
