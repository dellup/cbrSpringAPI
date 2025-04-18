package org.example.controller;

import org.example.exception.InvalidDateException;
import org.example.model.RequestLog;
import org.example.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("")
    public ResponseEntity<RequestLog> getCurrencyRates(@RequestParam("date")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                           LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("The date cannot be in the future");
        }

        RequestLog result = currencyService.getCurrencyRates(date);
        return ResponseEntity.ok(result);
    }
}
