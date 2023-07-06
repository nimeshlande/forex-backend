package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.Currency;
import com.example.repository.CurrencyRepository;
import com.example.service.CurrencyConversionService;

public class CurrencyConversionControllerTest {

    private CurrencyConversionService currencyConversionService;
    private CurrencyRepository currencyRepository;
    private CurrencyConversionController currencyConversionController;

    @BeforeEach
    public void setUp() {
        currencyConversionService = mock(CurrencyConversionService.class);
        currencyRepository = mock(CurrencyRepository.class);
        currencyConversionController = new CurrencyConversionController();
    }

    @Test
    public void testAddCurrency_ValidCurrency_Success() {
        // Mock input data
        Currency currency = new Currency(1L ,"USD", "US Dollar", 1.0);

        // Mock currencyRepository.findByCode() behavior
        when(currencyRepository.findByCode(currency.getCode())).thenReturn(null);

        // Mock currencyConversionService.addCurrency() behavior
        when(currencyConversionService.addCurrency(currency)).thenReturn(currency);

        // Perform the test
        ResponseEntity<Object> response = currencyConversionController.addCurrency(currency);

        // Verify the expected behavior
        verify(currencyRepository).findByCode(currency.getCode());
        verify(currencyConversionService).addCurrency(currency);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currency, response.getBody());
    }

    @Test
    public void testAddCurrency_ExistingCurrency_BadRequest() {
        // Mock input data
        Currency currency = new Currency(2L,"USD", "US Dollar", 1.0);

        // Mock currencyRepository.findByCode() behavior
        when(currencyRepository.findByCode(currency.getCode())).thenReturn(currency);

        // Perform the test
        ResponseEntity<Object> response = currencyConversionController.addCurrency(currency);

        // Verify the expected behavior
        verify(currencyRepository).findByCode(currency.getCode());
        verify(currencyConversionService, never()).addCurrency(currency);

        // Assert the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Currency is already present", response.getBody());
    }

    @Test
    public void testAddCurrency_InvalidCurrencyDetails_BadRequest() {
        // Mock input data
        Currency currency = new Currency(1L,"", "", 1.0);

        // Perform the test
        ResponseEntity<Object> response = currencyConversionController.addCurrency(currency);

        // Verify the expected behavior
        verify(currencyRepository, never()).findByCode(anyString());
        verify(currencyConversionService, never()).addCurrency(any());

        // Assert the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid Currency Details", response.getBody());
    }

    // More test cases for other methods can be added similarly
}
