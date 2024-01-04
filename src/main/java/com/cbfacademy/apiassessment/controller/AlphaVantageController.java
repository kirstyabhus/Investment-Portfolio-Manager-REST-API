package com.cbfacademy.apiassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.service.AlphaVantageService;

@RestController
@RequestMapping("/alphavantage")
public class AlphaVantageController {

    private final AlphaVantageService alphaVantageService;

    @Autowired
    public AlphaVantageController(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    @GetMapping("/{symbol}/price")
    public String getStockPrice(@PathVariable String symbol) {
        return alphaVantageService.getPrice(symbol);
    }

    // paths to add: topic, date (maybe)
    @GetMapping("{symbol}/news")
    public String getNewsSentiment(@PathVariable String symbol) {
        return alphaVantageService.getNewsSentiment(symbol);
    }
    // tickers={stock}&topics={topic}&sort=RELEVANCE&limit=5
}
