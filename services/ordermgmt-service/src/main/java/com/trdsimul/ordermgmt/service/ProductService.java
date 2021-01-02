package com.trdsimul.ordermgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trdsimul.ordermgmt.dto.ProductResponseDTO;
import com.trdsimul.ordermgmt.model.entity.HistoricalProductMapping;
import com.trdsimul.ordermgmt.model.entity.ProductDetails;
import com.trdsimul.ordermgmt.repository.HistoricalProductMappingRepository;
import com.trdsimul.ordermgmt.repository.ProductDetailsRepository;

/**
 * The Class Product Service.
 */
@Service
public class ProductService {

	@Autowired
	ProductDetailsRepository productDetailsRepository;
	
	@Autowired
	HistoricalProductMappingRepository mappingRepository;

	/**
	 * Gets the all products.
	 *
	 * @return all products
	 */
	public List<ProductResponseDTO> getAllProducts() {
		
		List<ProductResponseDTO> allProducts = new ArrayList<ProductResponseDTO>();
		Iterable<ProductDetails> iterable = productDetailsRepository.findAll();
		for (ProductDetails productDetails : iterable) {
			ProductResponseDTO productDTO = new ProductResponseDTO();
			productDTO.setProductCode(productDetails.getProductCode());
			productDTO.setProductId(productDetails.getProductId());
			productDTO.setProductName(productDetails.getProductName());
			allProducts.add(productDTO);
		}
		return allProducts;
	}

	public ProductDetails fetchProductDetails(Integer productId) {
		
		Optional<ProductDetails> productDetails = productDetailsRepository.findById(productId);
		if(productDetails.isPresent()) {
			return productDetails.get();
		}
		else
			return null;
		
	}

	public List<HistoricalProductMapping> fetchProductMapping() {
		List<HistoricalProductMapping> productMappings = new ArrayList<HistoricalProductMapping>();
		Iterable<HistoricalProductMapping> prodMapping = mappingRepository.findAll();
		for(HistoricalProductMapping productMapping : prodMapping) {
			productMappings.add(productMapping);
		}
		return productMappings;
	}

	
}
