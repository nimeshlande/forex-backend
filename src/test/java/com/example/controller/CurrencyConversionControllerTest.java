package com.example.controller;

import com.example.dto.CurrencyConversionDto;
import com.example.model.Currency;
import com.example.repository.CurrencyRepository;
import com.example.service.CurrencyConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CurrencyConversionControllerTest {

	private CurrencyConversionController currencyConversionController;

	@Mock
	private CurrencyConversionService currencyConversionService;

	@Mock
	private CurrencyRepository currencyRepository;

	@BeforeEach
	void setUp() {
		currencyConversionService = mock(CurrencyConversionService.class);
		currencyRepository = mock(CurrencyRepository.class);
		currencyConversionController = new CurrencyConversionController();
	}

//	@Test
//	void testAddCurrency_Success() {
//		Currency currency = new Currency();
//		currency.setCode("USD");
//		currency.setName("United States Dollar");
//		currency.setExchangeRate(1.0);
//
//		when(currencyRepository.findByCode(currency.getCode())).thenReturn(null);
//
//		ResponseEntity<Object> response = currencyConversionController.addCurrency(currency);
//
//		verify(currencyConversionService).addCurrency(currency);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(currency, response.getBody());
//	}

//	@Test
//	void testAddCurrency_DuplicateCurrency() {
//		Currency currency = new Currency();
//		currency.setCode("USD");
//		currency.setName("United States Dollar");
//		currency.setExchangeRate(1.0);
//
//		when(currencyRepository.findByCode(currency.getCode())).thenReturn(currency);
//
//		ResponseEntity<Object> response = currencyConversionController.addCurrency(currency);
//
//		verify(currencyConversionService, never()).addCurrency(currency);
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//		assertEquals("Currency is already present", response.getBody());
//	}

//	@Test
//	void testGetAllExchangeRates() {
//		List<Currency> currencyList = new ArrayList<>();
//		currencyList.add(new Currency(1L,"USD", "UnitedStatesDollar", 1.0));
//		currencyList.add(new Currency(2L,"EUR", "Euro", 0.85));
//
//		when(currencyConversionService.getCurrentExchangeRates()).thenReturn(currencyList);
//
//		ResponseEntity<List<Currency>> response = currencyConversionController.getAllExchangeRates();
//
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(currencyList, response.getBody());
//	}

	// Add more test cases for other methods in the CurrencyConversionController
	// class
}
