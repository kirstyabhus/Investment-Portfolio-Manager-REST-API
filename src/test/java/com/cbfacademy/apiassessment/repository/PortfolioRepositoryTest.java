package com.cbfacademy.apiassessment.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.cbfacademy.apiassessment.model.ESGRating;
import com.cbfacademy.apiassessment.model.ETF;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.model.Stock;
import com.cbfacademy.apiassessment.utility.JsonUtility;

import java.io.IOException;
import java.util.*;

public class PortfolioRepositoryTest {

    @InjectMocks
    private PortfolioRepository portfolioRepository;

    @Mock
    private JsonUtility jsonUtility;

    private List<Portfolio> mockPortfolios;
    private Portfolio testPortfolio1;
    private Portfolio testPortfolio2;
    private Portfolio testPortfolio3;

    @BeforeEach
    void setUp() throws IOException {

        MockitoAnnotations.openMocks(this);
        // Create three mock Portfolio instances
        UUID portfolioId1 = UUID.randomUUID();
        UUID portfolioId2 = UUID.randomUUID();
        UUID portfolioId3 = UUID.randomUUID();

        ETF investment1 = new ETF("ETF", "TEST", "TESTER", ESGRating.AAA, 5, 5.0, 5.0, 5.0);
        Stock investment2 = new Stock("Stock", "TEST", "TESTER", ESGRating.BBB, 5, 5.0, 5.0, 5.0);
        // Investment investment2 = new Stock(
        List<Investment> investments = new ArrayList<>(Arrays.asList(investment1, investment2));

        testPortfolio1 = new Portfolio(portfolioId1, "Portfolio 1", 0, investments);
        testPortfolio2 = new Portfolio(portfolioId2, "Portfolio 2", 0, investments);
        testPortfolio3 = new Portfolio(portfolioId3, "Portfolio 3", 0, investments);

        // Create mock portfolios list
        mockPortfolios = Arrays.asList(testPortfolio1, testPortfolio2, testPortfolio3);

        // (usually...)
        // Mock the behavior of jsonUtility.readPortfoliosFromJSON to return the mock
        // portfolios list
        Mockito.when(jsonUtility.readPortfoliosFromJSON(anyString())).thenReturn(mockPortfolios);

        // running portfolio repo with our mockportfolios as output from json utility
        portfolioRepository.populatePortfolioMap();
    }

    @Test
    void findAllReturnsAllPortfolios() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        assertTrue(portfolios.contains(testPortfolio1));
        assertTrue(portfolios.contains(testPortfolio2));
        assertTrue(portfolios.contains(testPortfolio3));
        assertEquals(3, portfolios.size());

    }

    @Test
    void findByIdReturnsPortfolioById() {
        Portfolio targetPortfolio3 = portfolioRepository.findById(testPortfolio3.getPortfolioId());
        assertEquals(testPortfolio3, targetPortfolio3);

        Portfolio targetPortfolio1 = portfolioRepository.findById(testPortfolio3.getPortfolioId());
        assertEquals(testPortfolio3, targetPortfolio1);

    }

}