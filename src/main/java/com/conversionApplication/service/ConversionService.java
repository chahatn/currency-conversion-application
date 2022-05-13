package com.conversionApplication.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.weaving.DefaultContextLoadTimeWeaver;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conversionApplication.interfaces.IConversionService;
import com.conversionApplication.model.CurrencyExchangeRateResponse;

@Service
public class ConversionService implements IConversionService {
	
	@Override
	public List<CurrencyExchangeRateResponse> getCurrencyExchangeRate(){
		List<CurrencyExchangeRateResponse> list = new ArrayList<>();
		String urlString ="https://api-coding-challenge.neofinancial.com/currency-conversion?seed=98794";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CurrencyExchangeRateResponse[]> response = restTemplate.getForEntity(urlString,CurrencyExchangeRateResponse[].class);
		list = Arrays.asList(response.getBody());
		return list;
	}

}
