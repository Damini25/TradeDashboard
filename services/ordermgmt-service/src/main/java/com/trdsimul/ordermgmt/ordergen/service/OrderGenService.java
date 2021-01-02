package com.trdsimul.ordermgmt.ordergen.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.trdsimul.ordermgmt.enums.BidAsk;
import com.trdsimul.ordermgmt.enums.OrderStatus;
import com.trdsimul.ordermgmt.enums.OrderType;
import com.trdsimul.ordermgmt.model.entity.HistoricalOrderDetails;
import com.trdsimul.ordermgmt.model.entity.NewsDetails;
import com.trdsimul.ordermgmt.model.entity.OrderDetails;
import com.trdsimul.ordermgmt.repository.HistoricalOrdersRepository;
import com.trdsimul.ordermgmt.service.OrderService;

/**
 * The Class Order Generator Service.
 */
@Service
public class OrderGenService {

	@Autowired
	HistoricalOrdersRepository historicalOrdersRepository;

	@Autowired
	OrderService orderService;
	
	@Autowired
	RestTemplate restTemplate;

	public static boolean generateOrdersFlag = true;

	public List<HistoricalOrderDetails> hisOrderList = null;
	Date lastPickedNewsTime = null;
	public List<NewsDetails> newsDetailsList = null;
	int index = 0;

	private URI buildRestTemplateToFetchNews(Integer gameId) {
		String url = "http://gamemgmt-service/gamedata/allNews";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        .queryParam("gameId", gameId);
		return builder.build().toUri();
	}

	/**
	 * Generate orders on fixed interval.
	 *
	 * @param restTemplate the rest template
	 * @throws CloneNotSupportedException the clone not supported exception
	 * @throws InterruptedException       the interrupted exception
	 */
	public List<String> generateOrdersOnFixedInterval(Integer gameId) throws CloneNotSupportedException, InterruptedException {

		List<String> latestNews = new ArrayList<String>();
		if (newsDetailsList == null || newsDetailsList.isEmpty()) {
			URI uri = buildRestTemplateToFetchNews(gameId);
			NewsDetails[] newsDetailsArr = restTemplate.getForObject(uri, NewsDetails[].class);
			newsDetailsList = new CopyOnWriteArrayList<NewsDetails>();
			newsDetailsList = Arrays.asList(newsDetailsArr);
		}
		
		if (hisOrderList == null || hisOrderList.isEmpty()) {
			hisOrderList = new CopyOnWriteArrayList<HistoricalOrderDetails>();
			hisOrderList = historicalOrdersRepository.findAllByGameId(gameId);
		}

		if (index <= hisOrderList.size()) {
			HistoricalOrderDetails histOrder = hisOrderList.get(index++);
			for(NewsDetails newsDetails : newsDetailsList) {
				if(lastPickedNewsTime == null) {
					if(newsDetails.getNewsDateTime().compareTo(histOrder.getEventDate()) <= 0 ) {
						latestNews.add(newsDetails.getNewsDetails());
					}
					lastPickedNewsTime = histOrder.getEventDate();
				}
				else {
					if( lastPickedNewsTime.compareTo(newsDetails.getNewsDateTime()) < 0 && newsDetails.getNewsDateTime().compareTo(histOrder.getEventDate()) <= 0 ) {
						latestNews.add(newsDetails.getNewsDetails());
						lastPickedNewsTime = histOrder.getEventDate();
					}
				}
					
			}
			OrderDetails newOrder = new OrderDetails();
			// newOrder.setOrderId(orderService.orderCounter++);
			newOrder.setBidOffer(histOrder.getBidOffer());
			newOrder.setPrice(histOrder.getPrice());
			newOrder.setProductId(histOrder.getProductId());
			newOrder.setGameId(gameId);
			if(histOrder.getBidOffer().equalsIgnoreCase(BidAsk.Ask.toString())) {
				newOrder.setTraderId(999);
			}
			else {
				newOrder.setTraderId(666);
			}
			newOrder.setOrderStatusId(OrderStatus.OPEN.getOrderStatusId());
			newOrder.setOrderTypeId(OrderType.LIMIT_ORDER.getOrderStatusId());
			newOrder.setTotalQty(histOrder.getTotalQty());
			newOrder.setUnfulfilledQuantity(histOrder.getTotalQty());
			newOrder.setOrderTime(new Date());
			orderService.bookNewOrder(newOrder);
		}
		return latestNews;

	}

	public List<HistoricalOrderDetails> getHisOrderList() {
		return hisOrderList;
	}

	public void setHisOrderList(List<HistoricalOrderDetails> hisOrderList) {
		this.hisOrderList = hisOrderList;
	}

	public void updategenerateordersflag(Boolean flag) {
		generateOrdersFlag = flag;

	}

	public List<Integer> allgameproducts(Integer gameId) {
		
		return historicalOrdersRepository.allgameproducts(gameId);
	}

	public void flushData() {
		generateOrdersFlag = true;
		hisOrderList.clear();
		lastPickedNewsTime = null;
		newsDetailsList.clear();
		index = 0;
	}

}
