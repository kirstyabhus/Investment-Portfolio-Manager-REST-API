package com.cbfacademy.apiassessment.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cbfacademy.apiassessment.config.ApiConfig;
import com.cbfacademy.apiassessment.utility.DateUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AlphaVantageService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private DateUtility dateUtility;

    // fetches data from alpha vantage api with given query
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

    // get current price of the symbol
    // TODO change API for price, since alphaV api limit is too small
    public String getPrice(String symbol) {
        JsonNode rootNode = fetchDataFromAlphaVantage("function=GLOBAL_QUOTE&symbol=" + symbol);

        if (rootNode != null) {
            JsonNode priceNode = rootNode.path("Global Quote").path("05. price");
            return priceNode.asText();
        }
        return null;
    }

    public String getNewsSentiment(String tickers) {
        String topic = "technology"; // specific to tech for now

        // get current date in alphavantage format
        String time_from = dateUtility.getAlphaVantageTimeDateFormat();

        JsonNode rootNode = fetchDataFromAlphaVantage(
                "function=NEWS_SENTIMENT&tickers=" + tickers + "&topics=" + topic + "&sort=RELEVANCE&limit=1"
                        + "&time_from=" + time_from);

        // JsonNode rootNode =
        // fetchDataFromAlphaVantage("function=NEWS_SENTIMENT&tickers=" + tickers);

        ArrayList<String> newsArrayList = new ArrayList<String>();
        // tickers={stock}&topics={topic}&sort=RELEVANCE&limit=5
        if (rootNode != null) {

            // find the news node of the json (news is found in the feed node in the api
            // json)
            JsonNode feedNode = rootNode.get("feed");
            if (feedNode != null && feedNode.isArray()) {
                for (JsonNode item : feedNode) {
                    // get the desired details of the news article
                    String title = item.get("title").asText();
                    String url = item.get("url").asText();
                    String timePublished = item.get("time_published").asText();
                    String timePublishedFormatted = dateUtility.getDateTimePretty(timePublished);
                    String summary = item.get("summary").asText();

                    // return (title + "\n" + url + "\n" + timePublishedFormatted + "\n" + summary);
                    // create a string of the details of the news article in the current iteration
                    String newsString = title + "\n" + url + "\n" + timePublishedFormatted + "\n" + summary;
                    // add the news with its details into the array
                    newsArrayList.add(newsString);
                }
            }
            // return all news articles
            return newsArrayList.toString();
        }
        return null;
    }
}