package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CurrencyConversionDto;
import com.example.model.Currency;
import com.example.model.CurrencyConversion;
import com.example.repository.CurrencyRepository;
import com.example.service.CurrencyConversionService;



@RestController
@RequestMapping("/currency")
@CrossOrigin("*")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;
    
    @Autowired
    private CurrencyRepository currencyRepository;
    
    
    //Admin Part to Add Currencies
    @PostMapping("/add")
    public ResponseEntity<Object> addCurrency(@RequestBody Currency currency){
    	Currency curr=currencyRepository.findByCode(currency.getCode());
    	
    	 if (curr != null) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Currency is already present");
         }
    	 String currencyCodePattern = "^[A-Z]{3}$";
    	 String currencyNamePattern = "^[a-zA-Z]+$";

    	    if (currency.getCode().isEmpty() || currency.getName().isEmpty()) {
    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Currency Details");
    	    }
    	    
    	    if (!currency.getName().matches(currencyNamePattern)) {
    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Currency Name");
    	    }
    	    
    	    if (!currency.getCode().matches(currencyCodePattern)) {
    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Currency Code");
    	    }

    	    if (currency.getExchangeRate() <= 0) {
    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Conversion Rate");
    	    }

         currencyConversionService.addCurrency(currency);
         return ResponseEntity.status(HttpStatus.OK).body(currency);
    	
    }
    
    
    //Get All Currencies
    @GetMapping("/all")
    public ResponseEntity<List<Currency>> getAllExchangeRates() {
        List<Currency> exchangeRateDtos = currencyConversionService.getCurrentExchangeRates();
        return ResponseEntity.ok(exchangeRateDtos);
    }
    
    
    
    //Get Currency ByCode
    @GetMapping("/{code}")
	public ResponseEntity<Object> getCurrency(@PathVariable("code") String code) {
    	Currency curr=currencyRepository.findByCode(code);
   	  if (curr == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Currency is Not present");
        }

	return ResponseEntity.ok(curr);	
	}
    
    //Admin Part to Delete Currency
    @DeleteMapping("/delete/{code}")
    public ResponseEntity<Object> deleteCurrency(@PathVariable("code") String code) {
        Optional<Currency> currencyOptional = currencyConversionService.getCurrencyRate(code);
        if (!currencyOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found");
        }

        Currency currency = currencyOptional.get();
        currencyConversionService.deleteCurrency(currency);
        return ResponseEntity.status(HttpStatus.OK).body("Currency deleted successfully");
    }
    
    //Admin Part to Update Currency
    @PutMapping("/update")
    public ResponseEntity<String> updateCurrency(@RequestParam String code,@RequestParam double rate){
    	
    	Optional<Currency> optional=currencyConversionService.getCurrencyRate(code);
    	
    	if(optional==null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Code") ;
    	}
    	Currency currency=optional.get();
    	
    	if(rate!=0.0)
    		currency.setExchangeRate(rate);
    	currencyConversionService.addCurrency(currency);
    	return ResponseEntity.status(HttpStatus.OK).body("Currency Updated");
    }
    
    
    //View Rate
    @GetMapping("/convert/view")
    public ResponseEntity<Object> viewConvertCurrency(
        @RequestParam String fromCurrency,
        @RequestParam String toCurrency,
        @RequestParam double amount
    ) {
    	if(amount<0) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount Cannot be Negative");
    	}
        CurrencyConversionDto result = currencyConversionService.convert(fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(result);
    }
    
//   Save Rate
    @GetMapping("/convert/rate")
    public ResponseEntity<Object> convertCurrency(
        @RequestParam String fromCurrency,
        @RequestParam String toCurrency,
        @RequestParam double amount
    ) {
    	if(amount<0) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount Cannot be Negative");
    	}
        CurrencyConversionDto result = currencyConversionService.convertSave(fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(result);
    }
    
    
    
    
    //Total Exchange Fee Earned By Company
    @GetMapping("/totalExchangeRate")
    public ResponseEntity<Object> geTotalExchangeRate() {
        List<CurrencyConversion> totalExchangeRateList = currencyConversionService.getTotalExchangeRate();
        double totalExchangeRate=0;
        for(CurrencyConversion c:totalExchangeRateList) {
        	totalExchangeRate=totalExchangeRate+c.getTotalExchangeFee();
        }
        return ResponseEntity.ok(totalExchangeRate);
    }


	public void setCurrencyRepository(CurrencyRepository currencyRepository2) {
		// TODO Auto-generated method stub
		
	}


	public void setCurrencyConversionService(CurrencyConversionService currencyConversionService2) {
		// TODO Auto-generated method stub
		
	}



}

