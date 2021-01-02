package com.trdsimul.gamemgmt.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trdsimul.gamemgmt.dto.DashBResponse;
import com.trdsimul.gamemgmt.dto.DashBResponseSingleObject;
import com.trdsimul.gamemgmt.dto.ErrorMessage;
import com.trdsimul.gamemgmt.model.entity.NewsDetails;
import com.trdsimul.gamemgmt.service.NewsAndFileDataService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/gamedata/")
public class NewsAndFileDataController {

	@Autowired
	NewsAndFileDataService newsAndFileDataService;
	
	private static Logger LOG = LoggerFactory.getLogger(NewsAndFileDataController.class);

	/**
	 * Function - Saving file data to DB.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	@PostMapping(path = "/uploadFile")
	public DashBResponseSingleObject mapExcelDataToDB(@RequestParam("file") MultipartFile file)  {
		LOG.info(" Started uploading historical orders.. ");
		DashBResponseSingleObject dashBResponse = new DashBResponseSingleObject();
		ErrorMessage message = new ErrorMessage();
		try {
			Boolean uploadStatus = newsAndFileDataService.uploadExcelData(file);
			if (uploadStatus) {
				dashBResponse.setSuccess(true);
				dashBResponse.setError(null);
				dashBResponse.setData(uploadStatus);
			} else {
				LOG.error("Upload failed !!");
				message.setKey("uploadFailed");
				message.setErrorMessage("Upload Failed Abruptly !!");
				dashBResponse.setSuccess(false);
				dashBResponse.setError(message);
				dashBResponse.setData(null);
			}
		} catch (Exception e) {
			LOG.error("Unknown Exception Occurred !!" + e);
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
		}
		return dashBResponse;
	}
	
	@GetMapping(path = "/datewisedata")
	public DashBResponse fetchDateWiseData() {
		LOG.info("Fetching dates for which orders are present.. ");
		ErrorMessage message = new ErrorMessage();
		DashBResponse dashBResponse = new DashBResponse();
		try {
			List<Date> availableDates = newsAndFileDataService.fetchDateWiseData();
			dashBResponse.setSuccess(true);
			dashBResponse.setError(null);
			dashBResponse.setData(availableDates);
		} catch (Exception e) {
			LOG.error("Unknown Exception Occurred !!" + e);
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
		}
		return dashBResponse;
	}
	
	
	@PostMapping(path = "/uploadNews")
	public DashBResponseSingleObject mapNewsToDB(@RequestParam("gameId") Integer gameId, @RequestParam("file") MultipartFile file) throws IOException, ParseException {
		LOG.info("Upload news for game :"+ gameId);
		DashBResponseSingleObject dashBResponse = new DashBResponseSingleObject();
		ErrorMessage message = new ErrorMessage();
		try {
			Boolean uploadStatus = newsAndFileDataService.uploadExcelDataForNews(gameId, file);
			dashBResponse.setSuccess(true);
			dashBResponse.setError(null);
			dashBResponse.setData(uploadStatus);
		} catch (Exception e) {
			LOG.error("Unknown Exception Occurred !!" + e);
			message.setKey("unknownExceptionOccured");
			message.setErrorMessage(e.getMessage());
			dashBResponse.setSuccess(false);
			dashBResponse.setError(message);
			dashBResponse.setData(null);
		}
		return dashBResponse;
	}

	@GetMapping(path = "/allNews")
	public List<NewsDetails> fetchAllNews(@RequestParam("gameId") Integer gameId) throws IOException, ParseException {
		LOG.info("Fetching all loaded news for game :" + gameId);
		List<NewsDetails> newsList =  newsAndFileDataService.fetchAllNews(gameId);
		return newsList;
	}
	
}
