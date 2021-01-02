package com.trdsimul.ordermgmt.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The Class ExecutedOrdersDetails.
 */
@Entity
@Table
public class ExecutedOrdersDetails implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer trdId;
	private Integer askOrderId;
	private Integer bidOrderId;
	private Integer gameId;
	private Integer bidTraderId;
	private Integer askTraderId;
	private Integer productId;
	private Double totalQty;
	@Transient
	private Boolean isSelling;
	private Integer currencyId;
	private Double price;
	private Integer orderStatusId;
	private Date orderExecutionTime;

	/**
	 * Instantiates a new executed orders details.
	 */
	public ExecutedOrdersDetails() {
	}




	public ExecutedOrdersDetails(Integer trdId, Integer askOrderId, Integer bidOrderId, Integer gameId,
			Integer bidTraderId, Integer askTraderId, Integer productId, Double totalQty, Boolean isSelling,
			Integer currencyId, Double price, Integer orderStatusId, Date orderExecutionTime) {
		super();
		this.trdId = trdId;
		this.askOrderId = askOrderId;
		this.bidOrderId = bidOrderId;
		this.gameId = gameId;
		this.bidTraderId = bidTraderId;
		this.askTraderId = askTraderId;
		this.productId = productId;
		this.totalQty = totalQty;
		this.isSelling = isSelling;
		this.currencyId = currencyId;
		this.price = price;
		this.orderStatusId = orderStatusId;
		this.orderExecutionTime = orderExecutionTime;
	}




	public Integer getTrdId() {
		return trdId;
	}

	public void setTrdId(Integer trdId) {
		this.trdId = trdId;
	}

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public Integer getAskOrderId() {
		return askOrderId;
	}

	public void setAskOrderId(Integer askOrderId) {
		this.askOrderId = askOrderId;
	}

	public Integer getBidOrderId() {
		return bidOrderId;
	}

	public void setBidOrderId(Integer bidOrderId) {
		this.bidOrderId = bidOrderId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getBidTraderId() {
		return bidTraderId;
	}

	public void setBidTraderId(Integer bidTraderId) {
		this.bidTraderId = bidTraderId;
	}

	public Integer getAskTraderId() {
		return askTraderId;
	}

	public void setAskTraderId(Integer askTraderId) {
		this.askTraderId = askTraderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	/**
	 * Clone.
	 *
	 * @return the object
	 * @throws CloneNotSupportedException the clone not supported exception
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Date getOrderExecutionTime() {
		return orderExecutionTime;
	}

	public void setOrderExecutionTime(Date orderExecutionTime) {
		this.orderExecutionTime = orderExecutionTime;
	}

	public Boolean getIsSelling() {
		return isSelling;
	}

	public void setIsSelling(Boolean isSelling) {
		this.isSelling = isSelling;
	}

	@Override
	public String toString() {
		return "ExecutedOrdersDetails [trdId=" + trdId + ", askOrderId=" + askOrderId + ", bidOrderId=" + bidOrderId
				+ ", gameId=" + gameId + ", bidTraderId=" + bidTraderId + ", askTraderId=" + askTraderId
				+ ", productId=" + productId + "]";
	}


}
