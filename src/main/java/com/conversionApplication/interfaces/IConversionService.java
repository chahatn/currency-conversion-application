package com.conversionApplication.interfaces;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.conversionApplication.model.BestExchangeRatePathData;
import com.conversionApplication.model.CurrencyExchangeRateResponse;
import com.conversionApplication.model.CurrencyNode;
import com.conversionApplication.model.ExchangeNode;

@Component
public interface IConversionService {

	List<CurrencyExchangeRateResponse> getCurrencyExchangeRate();
	
	ArrayList<BestExchangeRatePathData> getLongestPath(HashMap<String, ArrayList<ExchangeNode>> adjacencyMap, String startCode, float inputAmount);
	
	HashMap<String, ArrayList<ExchangeNode>> getAdjacencyListMap(List<CurrencyExchangeRateResponse> currencyExchangeRateResponse);
	
	void writeBestExchangeRatesToCsv(PrintWriter printWriter , List<BestExchangeRatePathData> list);
}
