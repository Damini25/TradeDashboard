package com.trdsimul.ordermgmt.dto;

import java.util.List;

import com.trdsimul.ordermgmt.model.entity.OrderDetails;

public class BidAskOrderDTO {

	private List<OrderDetails> allBidOrders;
	private List<OrderDetails> allAskOrders;
	private List<String> latestNews;
	private Boolean playbackFlag;

	public BidAskOrderDTO() {
		super();
	}


	public BidAskOrderDTO(List<OrderDetails> allBidOrders, List<OrderDetails> allAskOrders, List<String> latestNews,
			Boolean playbackFlag) {
		super();
		this.allBidOrders = allBidOrders;
		this.allAskOrders = allAskOrders;
		this.latestNews = latestNews;
		this.playbackFlag = playbackFlag;
	}



	public List<String> getLatestNews() {
		return latestNews;
	}


	public void setLatestNews(List<String> latestNews) {
		this.latestNews = latestNews;
	}


	public List<OrderDetails> getAllBidOrders() {
		return allBidOrders;
	}

	public void setAllBidOrders(List<OrderDetails> allBidOrders) {
		this.allBidOrders = allBidOrders;
	}

	public List<OrderDetails> getAllAskOrders() {
		return allAskOrders;
	}

	public void setAllAskOrders(List<OrderDetails> allAskOrders) {
		this.allAskOrders = allAskOrders;
	}

	public Boolean getPlaybackFlag() {
		return playbackFlag;
	}

	public void setPlaybackFlag(Boolean playbackFlag) {
		this.playbackFlag = playbackFlag;
	}

}
