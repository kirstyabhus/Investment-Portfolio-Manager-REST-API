package com.cbfacademy.apiassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cbfacademy.apiassessment.config.ApiConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AlphaVantageService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiConfig apiConfig;

    private JsonNode fetchDataFromAlphaVantage(String query) {
        try {
            String baseUrl = apiConfig.getBaseUrl();
            String apiKey = apiConfig.apiKey();
            String apiUrl = baseUrl + query + "&apikey=" + apiKey;

            String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            // do i need to further process the json response?

            // Return the fetched data as a string
            return rootNode;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPrice(String symbol) {
        JsonNode rootNode = fetchDataFromAlphaVantage("function=GLOBAL_QUOTE&symbol=" + symbol);

        if (rootNode != null) {
            JsonNode priceNode = rootNode.path("Global Quote").path("05. price");
            return priceNode.asText();
        }
        return null;
    }

    public String getNewsSentiment(String tickers) {
        String topic = "technology";
        JsonNode rootNode = fetchDataFromAlphaVantage(
                "function=NEWS_SENTIMENT&tickers=" + tickers + "&topics=" + topic + "&sort=RELEVANCE&limit=1");
        // JsonNode rootNode =
        // fetchDataFromAlphaVantage("function=NEWS_SENTIMENT&tickers=" + tickers);

        // tickers={stock}&topics={topic}&sort=RELEVANCE&limit=5
        if (rootNode != null) {
            // Process the rootNode or return it as needed for news sentiment handling
            return rootNode.toString(); // Example: returning rootNode as a string
        }
        return null;
    }
}