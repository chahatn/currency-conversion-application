package com.conversionApplication.service;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conversionApplication.interfaces.IConversionService;
import com.conversionApplication.model.BestExchangeRatePathData;
import com.conversionApplication.model.CurrencyExchangeRateResponse;
import com.conversionApplication.model.CurrencyNode;
import com.conversionApplication.model.ExchangeNode;
import com.conversionApplication.util.ConversionConstants;

@Service
public class ConversionService implements IConversionService {
	@Value("${neo.base.path.url}")
	private String neoBasePath;
	
	@Value("${exchange.rate.url}")
	private String exchangeRateUrl;
	
	@Value("${countryName}")
	private String countryName;
	
	/*
	 * Calling external API for getting exchange rates, CurrencyCode, CurrencyCode and other fields
	 * Returns a list of CurrencyExchangeRateResponse from the API
	 * API URL: https://api-coding-challenge.neofinancial.com/currency-conversion?seed=19760
	 */
	@Override
	public List<CurrencyExchangeRateResponse> getCurrencyExchangeRate(){
		List<CurrencyExchangeRateResponse> list = new ArrayList<>();
		String urlString = neoBasePath+exchangeRateUrl;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CurrencyExchangeRateResponse[]> response = restTemplate.getForEntity(urlString,CurrencyExchangeRateResponse[].class);
		list = Arrays.asList(response.getBody());
		return list;
	}
	
	/*
	 * 
		 * getAdjacencyListMap - Generates adjacency map for all the currencies. It concludes the date into Hashmap with 
		 * @countryCode as a Key 
		 * @List of countries and their conversion rates that can be converted from the CountryCode(Key)
		 * 
		 * @Hashmap<K,V>
		 * Key   Value
		 * CAD->{{ARS,1.09,Argentina,100},{HKD,1.10,HongKong,100}};
		 * HKD->{{ARS,1.09,Argentina,100},{CAD,1.5.Canada,100}}
		 
	 */
	
	@Override
	public HashMap<String, ArrayList<ExchangeNode>> getAdjacencyListMap(List<CurrencyExchangeRateResponse> currencyExchangeRateResponse){
		HashMap<String, ArrayList<ExchangeNode>> adjacencyMap=new HashMap<>();
		for(CurrencyExchangeRateResponse currencyExchangeRate : currencyExchangeRateResponse) {
			if(adjacencyMap.containsKey(currencyExchangeRate.getFromCurrencyCode())) {
				adjacencyMap.get(currencyExchangeRate.getFromCurrencyCode()).
				add(new ExchangeNode(currencyExchangeRate.getToCurrencyCode(),currencyExchangeRate.getExchangeRate(),currencyExchangeRate.getToCurrencyName(),0));
			}else {
				ArrayList<ExchangeNode> aList = new ArrayList<ExchangeNode>();
				aList.add(new ExchangeNode(currencyExchangeRate.getToCurrencyCode(),currencyExchangeRate.getExchangeRate(),currencyExchangeRate.getToCurrencyName(),0));
				adjacencyMap.put(currencyExchangeRate.getFromCurrencyCode(), aList);
			}
		}		
		return adjacencyMap;
	}
	
	
	/*
	 *  getLongestPath() generates longest path eligible for lowest currency exchange rates.
	 *  Uses (Modified) Dijakstra's Algorithm for finding the longest path and lowest exchange rates.
	 *  Returns @list<BestExchangeRatePathData> which contains CountryName , CurrencyPath , CurrencyCode and ExchangeRate
	 */
	
	@Override
	public ArrayList<BestExchangeRatePathData> getLongestPath(HashMap<String, ArrayList<ExchangeNode>> adjacencyMap, String startCode, float inputAmount) {
		
		PriorityQueue<ExchangeNode> queue = new PriorityQueue<>((x,y)->{return y.getAmount()-x.getAmount()>=0?1:-1;});
		
		ArrayList<BestExchangeRatePathData> bestExchangeRatePathDataList = new ArrayList<>();
		
		Map<String , CurrencyNode> currencyMap = new HashMap<>();
		queue.add(new ExchangeNode(startCode,inputAmount,countryName,0));
		currencyMap.put(startCode, new CurrencyNode(countryName,inputAmount,startCode));
		while(!queue.isEmpty()) {
			ExchangeNode exchangeNode = queue.poll();
			ArrayList<ExchangeNode> alist = adjacencyMap.get(exchangeNode.getCurrencyCode());
			if(alist!=null)
				for(ExchangeNode exchange : alist) {
					
					if(!currencyMap.containsKey(exchange.getCurrencyCode()) || 
							(currencyMap.get(exchangeNode.getCurrencyCode()).getAmount() * exchange.getExchangeRate()) >
																				currencyMap.get(exchange.getCurrencyCode()).getAmount()) {	
						
						CurrencyNode node=currencyMap.get(exchange.getCurrencyCode());
						
						if(node==null) {
							node=new CurrencyNode(getCountryName(exchange.getCurrencyName()),
									currencyMap.get(exchangeNode.getCurrencyCode()).getAmount() * exchange.getExchangeRate(),
									currencyMap.get(exchangeNode.getCurrencyCode()).getPath()+"|"+exchange.getCurrencyCode()); 
						}else {
							node.setAmount(node.getAmount() * exchange.getExchangeRate());
							node.setPath(currencyMap.get(exchangeNode.getCurrencyCode()).getPath()+"|"+exchange.getCurrencyCode());
						}
						currencyMap.put(exchange.getCurrencyCode(),node);
						
						queue.add(new ExchangeNode(exchange.getCurrencyCode(),
								exchange.getExchangeRate(),
								exchange.getCurrencyCode(),
								(currencyMap.get(exchangeNode.getCurrencyCode()).getAmount() * exchange.getExchangeRate())));
						
					}
				}					
			
		}
		convertToList(currencyMap,bestExchangeRatePathDataList );
		return  bestExchangeRatePathDataList;
	}

	private void convertToList(Map<String, CurrencyNode> currencyMap, ArrayList<BestExchangeRatePathData> bestExchangeRatePathDataList) {
		
		
		for(Map.Entry<String, CurrencyNode> mapEntry : currencyMap.entrySet()) {
			BestExchangeRatePathData bestExchangeRatePathData = new BestExchangeRatePathData(mapEntry.getValue().getCountry(),
					mapEntry.getValue().getAmount(),mapEntry.getValue().getPath(), mapEntry.getKey());
			bestExchangeRatePathDataList.add(bestExchangeRatePathData);			
		}
		
	}

	private String getCountryName(String currencyName) {
		String[] currencyArrayStrings = currencyName.split("\\s+");
		String countryNameString=currencyArrayStrings[0]+" ";
		for(int i = 1 ; i < currencyArrayStrings.length-1 ;i++){
			countryNameString += currencyArrayStrings[i]+" ";
		}
		return countryNameString.trim();
	}
	
	/*
	 * writeBestExchangeRatesToCsv(..) - Genrates CSV file with header as CurrencyCode , Country, ExchangeRate , Path
	 */

	@Override
	public void writeBestExchangeRatesToCsv(PrintWriter printWriter, List<BestExchangeRatePathData> list) {
		String[] HEADERS= {
				ConversionConstants.CURRENCY_CODE , ConversionConstants.COUNTRY , ConversionConstants.EXCHANGERATE , ConversionConstants.PATH
		};
		try (CSVPrinter csvPrinter = new CSVPrinter(printWriter, CSVFormat.DEFAULT.withHeader(HEADERS))) {
          for (BestExchangeRatePathData exchangeResponse : list) {
              csvPrinter.printRecord(exchangeResponse.getCurrencyCode(),
              		exchangeResponse.getCountry(),exchangeResponse.getAmount(),exchangeResponse.getPath());
          }
      } catch (IOException e) {
         System.out.println("Error generating CSV "+ e);
      }
		
	}

}
