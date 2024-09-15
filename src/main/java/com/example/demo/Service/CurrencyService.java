package com.example.demo.Service;

import com.example.demo.Dto.CurrencyDTO;
import com.example.demo.Entity.Currency;
import com.example.demo.Repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public List<CurrencyDTO> getAllCurrencies() {
        return currencyRepository.findAll().stream().map(currency -> {
            CurrencyDTO dto = new CurrencyDTO();
            dto.setId(currency.getId());
            dto.setCode(currency.getCode());
            dto.setName(currency.getName());
            dto.setPrice(currency.getPrice());
            return dto;
        }).collect(Collectors.toList());
    }

    public CurrencyDTO updatePrice(String fromCode, String toCode, Double amount) {
        Currency fromCurrency = currencyRepository.findByCode(fromCode);
        Currency toCurrency = currencyRepository.findByCode(toCode);

        double convertedAmount = (amount / fromCurrency.getPrice()) * toCurrency.getPrice();

        CurrencyDTO dto = new CurrencyDTO();
        dto.setCode(toCurrency.getCode());
        dto.setName(toCurrency.getName());
        dto.setPrice(convertedAmount);

        return dto;
    }
}
