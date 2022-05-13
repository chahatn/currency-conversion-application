package com.conversionApplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversionApplication.interfaces.IConversionService;
import com.conversionApplication.model.CurrencyExchangeRateResponse;

@RestController
public class ConversionController {
	
	@Autowired
	IConversionService conversionService;
	
	@GetMapping(value = "/v1/exchangeRate") 
	ResponseEntity<?> getApiExchangeRate(){
		List<CurrencyExchangeRateResponse> list = new ArrayList<>();
		list = conversionService.getCurrencyExchangeRate();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
