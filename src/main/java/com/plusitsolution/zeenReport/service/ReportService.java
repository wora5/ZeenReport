package com.plusitsolution.zeenReport.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plusitsolution.common.toolkit.PlusCSVBuilder;
import com.plusitsolution.common.toolkit.PlusCSVUtils;



@Service
public class ReportService {
	
	@SuppressWarnings("unchecked")
	public byte[] getConvert(MultipartFile uploadfile) throws IOException{
		
		Map<String, Map<String, Integer>> registerMap = new HashMap<>();
		List<String> allCategories = new ArrayList<>();
		String[] headers = new String[] {"shopID", "SHELF_SHARE"};
		Reader inputFile = new InputStreamReader(uploadfile.getInputStream());
		/*Create csv and read the input file*/
		PlusCSVBuilder csv = PlusCSVUtils.csv("");
		List<String[]> lines = PlusCSVUtils.readCSV(headers, inputFile, true);
		/*Break down SHELF_SHARE*/
		for (String[] line : lines){
			String shopID = line[0];
			String SHELF_SHARE = line[1];
			String productName = null;
			ObjectMapper mapper = new ObjectMapper();
			if (!SHELF_SHARE.isEmpty() && SHELF_SHARE != null) {
				Map<String, Integer> skuMap = registerMap.get(shopID);
				if (skuMap == null) {
					skuMap = new HashMap<>();
					registerMap.put(shopID, skuMap);
				}
				Map<String ,Object> shelfShareMap = mapper.readValue(SHELF_SHARE, Map.class);
				Map<String, Map<String, Integer>> skuCountsMap = (Map<String, Map<String, Integer>>) shelfShareMap.get("skuCounts");
				/*Break down categories into each product*/
				for (Map.Entry<String, Map<String, Integer>> productCategoriesMap : skuCountsMap.entrySet()) {
					Map<String, Integer> productMap = productCategoriesMap.getValue();
					/*Create all categories*/
		            for (Map.Entry<String, Integer> productEntry: productMap.entrySet()) {
		            	StringBuffer name = new StringBuffer();
		            	productName = name.append(productCategoriesMap.getKey()).append(".").append(productEntry.getKey()).toString();
		            	if(!allCategories.contains(productName)) {
		                	allCategories.add(productName);
		                }	
		            	Integer productValue = productMap.get(productName);
		    			if (productValue == null || productEntry.getValue() > productValue) {
		    				skuMap.put(productName, productEntry.getValue());
		    			}
		            } 
		        }
			}
	    }
		
		/*Create result column header*/
		allCategories.add(0, "shopID");
		csv.headers(allCategories.toArray(new String[0]));
		
		/*Write the result into file*/
		for(Map.Entry<String, Map<String, Integer>> entry : registerMap.entrySet()){
			Map<String, Integer> productMap = entry.getValue();
			Object[] shopInfo = new String[allCategories.size()];
			for(String list : allCategories) {
				//System.out.println(productMap.get(list));
				System.out.println(list + allCategories.indexOf(list));
				if(productMap.get(list) == null) {
					shopInfo[allCategories.indexOf(list)] = "0";
				}
				else {
					shopInfo[allCategories.indexOf(list)] = String.valueOf(productMap.get(list));
				}
			}
			shopInfo[0] = entry.getKey().toString();
			csv.line(shopInfo);
		}
		return csv.writeBytes();
	}
}
