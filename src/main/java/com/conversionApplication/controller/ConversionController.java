package com.conversionApplication.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversionApplication.interfaces.IConversionService;
import com.conversionApplication.model.BestExchangeRatePathData;
import com.conversionApplication.model.CurrencyExchangeRateResponse;
import com.conversionApplication.model.ExchangeNode;

@RestController
public class ConversionController {
	
	@Autowired
	IConversionService conversionService;
	
	@Value("${filename}")
	private String filename;
	
	/*
	 @API getApiExchangeRate(currencyCode , amount) generates the best possible exchange rate path from one currency to all other currencies present.
	 @param:
	  - @FromCurrencyCode (String) - Input value of Currency Code from which you can exchange to all other currencies
	  - @Amount - Input amount for exchanging currencies
	  - URl- 
	 */
	@GetMapping(value = "/v1/getBestExchangeRate", produces = "text/csv") 
	ResponseEntity<?> getApiExchangeRate(@PathParam(value = "FromCurrencyCode") String FromCurrencyCode ,@PathParam(value = "amount") float amount,
			HttpServletResponse servletResponse) throws IOException{
		List<CurrencyExchangeRateResponse> list = new ArrayList<>();
		/*
		 * Calling external Api for getting exchange rates and code
		 * URL: https://api-coding-challenge.neofinancial.com/currency-conversion?seed=19760
		 */
		list = conversionService.getCurrencyExchangeRate();		
		/*
		 * getAdjacencyListMap - Generates adjacency map for all the currencies
		 * 
		 * Key   Value
		 * CAD->{{ARS,1.09,Argentina,100},{HKD,1.10,HongKong,100}};
		 * HKD->{{ARS,1.09,Argentina,100},{CAD,1.5.Canada,100}}
		 */
		
		HashMap<String, ArrayList<ExchangeNode>> getAdjacencyListMap = conversionService.getAdjacencyListMap(list);
		
		/*
		 *  getLongestPath() generates longest path eligible for lowest currency exchange rates		  
		 */
		
		List<BestExchangeRatePathData> bestExchangeRatePathDataList = conversionService.getLongestPath(getAdjacencyListMap, FromCurrencyCode, amount);
		
		/*
		 * CSV Generation
		 */
		servletResponse.setContentType("text/csv");
		servletResponse.addHeader("Content-Disposition","attachment; filename=\""+filename+".csv\"");
        conversionService.writeBestExchangeRatesToCsv(
        		servletResponse.getWriter(),bestExchangeRatePathDataList);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
