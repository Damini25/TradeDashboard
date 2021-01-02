package com.trdsimul.gamemgmt.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trdsimul.gamemgmt.model.entity.GameDetails;
import com.trdsimul.gamemgmt.model.entity.HistoricalOrderDetails;
import com.trdsimul.gamemgmt.model.entity.MasterHistoricalOrder;
import com.trdsimul.gamemgmt.repository.HistoricalOrdersRepository;
import com.trdsimul.gamemgmt.repository.MasterHistoricalOrderRepository;

/**
 * The Class Order Generator Service.
 */
@Service
public class OrderGenService {

	@Autowired
	HistoricalOrdersRepository historicalOrdersRepository;
	
	@Autowired
	MasterHistoricalOrderRepository masterHistoricalOrderRepository;

	@Autowired
	RestTemplate restTemplate;
	
	public List<HistoricalOrderDetails> hisOrderList = new ArrayList<HistoricalOrderDetails>();
	public Iterator<HistoricalOrderDetails> iter = null;
	int index = 0;

	private static Logger LOG = LoggerFactory.getLogger(OrderGenService.class);

	/**
	 * Function - Takes excel file as input and uploads data.
	 *
	 * @param gameDetails 
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public Integer fetchRecordsBetweenDatesAndCalFrequency(GameDetails gameDetails) {

		Integer numberofRecords = 0;
		java.sql.Timestamp playbkackStartTimeTS = new Timestamp(gameDetails.getPlaybackStartTime().getTime());
		java.sql.Timestamp playbkackEndTimeTS = new Timestamp(gameDetails.getPlaybackEndTime().getTime());
		List<MasterHistoricalOrder> masterHistOrders = masterHistoricalOrderRepository.fetchBetweenDatesInterval(playbkackStartTimeTS, playbkackEndTimeTS);
		if (masterHistOrders != null) {
			for (MasterHistoricalOrder mastHistOrder : masterHistOrders) {
				// Filter orders based on play back dates provided.
				if (filterOrderBasedOnDateInterval(mastHistOrder, gameDetails)) {
					HistoricalOrderDetails histOrder = new HistoricalOrderDetails();
					histOrder.setBidOffer(mastHistOrder.getBidOffer());
					histOrder.setGameId(gameDetails.getGameId());
					histOrder.setTotalQty(mastHistOrder.getTotalQty());
					String[] arr = mastHistOrder.getProductName().split("_");
					histOrder.setProductName(arr[0]);
					histOrder.setPrice(mastHistOrder.getPrice());
					histOrder.setProductId(mastHistOrder.getProductId());
					histOrder.setEventDate(mastHistOrder.getEventDate());
					hisOrderList.add(histOrder);
					++numberofRecords;
				}
			}
		}
		LOG.info("Number of historical orders found : "+ numberofRecords);
		// Setting default frequency of 3 seconds in case no hostorical orders are present.
		Integer iFrequencyInMilliS = 3000;
		if (numberofRecords != 0) {
			float frequency = (float) ((gameDetails.getGameInterval()*60) / numberofRecords) ;
			iFrequencyInMilliS = (int) frequency*1000;
		}
		LOG.info("Playback Frequency will be : "+ iFrequencyInMilliS);
		historicalOrdersRepository.saveAll(hisOrderList);
		return iFrequencyInMilliS;
	}

	private Boolean filterOrderBasedOnDateInterval(MasterHistoricalOrder order, GameDetails gameDetails) {

		if (order.getEventDate().compareTo(gameDetails.getPlaybackStartTime()) > 0
				&& order.getEventDate().compareTo(gameDetails.getPlaybackEndTime()) < 0)
			return true;
		else
			return false;
	}

	/**
	 * Function - persist data to DB.
	 *
	 * @param histOrders the hist orders
	 */
	public Boolean saveExcelDataToDB(List<HistoricalOrderDetails> histOrders) {
		historicalOrdersRepository.saveAll(histOrders);
		return true;
	}


}
