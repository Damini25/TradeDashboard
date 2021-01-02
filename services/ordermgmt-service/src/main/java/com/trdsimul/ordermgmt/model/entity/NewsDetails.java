package com.trdsimul.ordermgmt.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class NewsDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer newsDetailsId;
	private Date newsDateTime;
	private String newsDetails;

	public NewsDetails() {
		super();
	}

	public NewsDetails(Integer newsDetailsId, Date newsDateTime, String newsDetails) {
		super();
		this.newsDetailsId = newsDetailsId;
		this.newsDateTime = newsDateTime;
		this.newsDetails = newsDetails;
	}

	public Integer getNewsDetailsId() {
		return newsDetailsId;
	}

	public void setNewsDetailsId(Integer newsDetailsId) {
		this.newsDetailsId = newsDetailsId;
	}

	public Date getNewsDateTime() {
		return newsDateTime;
	}

	public void setNewsDateTime(Date newsDateTime) {
		this.newsDateTime = newsDateTime;
	}

	public String getNewsDetails() {
		return newsDetails;
	}

	public void setNewsDetails(String newsDetails) {
		this.newsDetails = newsDetails;
	}

}
