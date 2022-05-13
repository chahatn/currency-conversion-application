package com.conversionApplication.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.conversionApplication.model.CurrencyExchangeRateResponse;

@Component
public interface IConversionService {

	List<CurrencyExchangeRateResponse> getCurrencyExchangeRate();
}
