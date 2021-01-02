package com.trdsimul.gamemgmt.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.trdsimul.gamemgmt.model.entity.HistoricalProductMapping;
import com.trdsimul.gamemgmt.model.entity.MasterHistoricalOrder;
import com.trdsimul.gamemgmt.model.entity.NewsDetails;
import com.trdsimul.gamemgmt.repository.MasterHistoricalOrderRepository;
import com.trdsimul.gamemgmt.repository.NewsDetailsRepository;

@Service
public class NewsAndFileDataService {

	@Autowired
	MasterHistoricalOrderRepository hisPrdMappingRepository;

	@Autowired
	NewsDetailsRepository newsDetailsRepository;
	
	@Autowired
	RestTemplate restTemplate;

	public List<MasterHistoricalOrder> hisOrderMastList = null;

	private static Logger LOG = LoggerFactory.getLogger(NewsAndFileDataService.class);

	/**
	 * Function - Takes excel file as input and uploads data.
	 *
	 * @param file        the file
	 * @param gameId
	 * @param gameDetails
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public Boolean uploadExcelData(MultipartFile file) throws IOException, ParseException {

		hisOrderMastList = new ArrayList<MasterHistoricalOrder>();
		String url = "http://ordermgmt-service/userdashb/findproductmapping";
		HistoricalProductMapping[] histProdArr = restTemplate.getForObject(
				url , HistoricalProductMapping[].class);
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			MasterHistoricalOrder order = new MasterHistoricalOrder();
			XSSFRow row = worksheet.getRow(i);
			String prodName = row.getCell(0).getStringCellValue();
			order.setProductName(prodName);
			Date eventDate = row.getCell(1).getDateCellValue();
			SimpleDateFormat format1 = new SimpleDateFormat("d/M/YYYY hh:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
			order.setEventDate(format2.parse(format2.format(eventDate)));
			order.setPrice(row.getCell(2).getNumericCellValue());
			order.setTotalQty(row.getCell(3).getNumericCellValue());
			order.setBidOffer(row.getCell(4).getStringCellValue());
			// find product mapping and add id here.
			String[] arr = prodName.split("_");
			for (int j = 0; j < histProdArr.length; j++) {
				if (histProdArr[j].getHistProdName().equalsIgnoreCase(arr[0]))
					order.setProductId(histProdArr[j].getProductId());
			}
			hisOrderMastList.add(order);
		}
		return saveExcelDataToDB(hisOrderMastList);

	}

	/**
	 * Function - persist data to DB.
	 *
	 * @param hisOrderMastList the hist orders
	 */
	public Boolean saveExcelDataToDB(List<MasterHistoricalOrder> hisOrderMastList) {
		try {
			hisPrdMappingRepository.saveAll(hisOrderMastList);
			LOG.info("Saved all orders from file");
			return true;
		}
		catch(IllegalArgumentException ex) {
			LOG.error("Error", ex);
			return false;
		}
	}

	public List<Date> fetchDateWiseData() {
		return hisPrdMappingRepository.fetchDateWiseData();
	}

	public Boolean uploadExcelDataForNews(Integer gameId, MultipartFile file) throws IOException {
		
		List<NewsDetails> newsDetailsList = new ArrayList<NewsDetails>();
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			NewsDetails newsDetails = new NewsDetails();
			XSSFRow row = worksheet.getRow(i);
			newsDetails.setNewsDateTime(row.getCell(0).getDateCellValue());
			newsDetails.setNewsDetails(row.getCell(1).getStringCellValue());
			newsDetails.setGameId(gameId);
			newsDetailsList.add(newsDetails);
		}
		newsDetailsRepository.saveAll(newsDetailsList);
		return true;
	}

	public List<NewsDetails> fetchAllNews(Integer gameId) {
		List<NewsDetails> newsList = new ArrayList<NewsDetails>();
		newsList = newsDetailsRepository.findAllByGame(gameId);
		return newsList;
	}

}
