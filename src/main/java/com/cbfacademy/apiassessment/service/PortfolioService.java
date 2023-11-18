package com.cbfacademy.apiassessment.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sound.sampled.Port;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.utility.JsonUtility;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;

@Service
public class PortfolioService {

    private String filePath = "C:///Users//kabhu//cbf-final-project//java-rest-api-assessment-kirstyabhus//src//main//resources//data.json";

    @Autowired
    private JsonUtility jsonUtility;

    @Autowired
    private final PortfolioRepository portfolioRepository;

    @Autowired
    private final InvestmentRepository investmentRepository;

    PortfolioService(PortfolioRepository portfolioRepository, InvestmentRepository investmentRepository) {
        this.portfolioRepository = portfolioRepository;
        this.investmentRepository = investmentRepository;
    }

    // get all portfolios
    public List<Portfolio> getPortfolios() {
        return portfolioRepository.findAll();
    }

    // get portfolio by id
    public Portfolio getPortfolioById(UUID id) {
        return portfolioRepository.findById(id);
    }

    // create a new portfolio / update a portfolio
    public Portfolio createOrUpdatePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    // delete a portfolio
    public void deletePortfolio(UUID id) {
        portfolioRepository.deletePortfolio(id);
    }

    // sort portfolios based on value or name
    public List<Portfolio> getSortedPortfolios(String sortCriteria, String sortOrder) {
        Map<UUID, Portfolio> portfoliosMap = portfolioRepository.getPortfoliosMap();

        Comparator<Portfolio> comparator = getComparatorForSortCriteria(sortCriteria);

        if (comparator == null) {
            throw new IllegalArgumentException("Invalid sort criteria: " + sortCriteria);
        }

        List<Portfolio> sortedPortfolios = new ArrayList<>(portfoliosMap.values());

        // Sort based on ascending or descending order
        if (sortOrder.equalsIgnoreCase("asc")) {
            sortedPortfolios.sort(comparator);
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            sortedPortfolios.sort(comparator.reversed());
        } else {
            throw new IllegalArgumentException("Invalid sort order: " + sortOrder);
        }

        // update the JSON with sorted protfolio
        try {
            jsonUtility.writePortfoliosToJSON(sortedPortfolios, filePath);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return sortedPortfolios;
    }

    // get the comparator for the sorting based on given sort criteria
    private Comparator<Portfolio> getComparatorForSortCriteria(String sortCriteria) {
        switch (sortCriteria.toLowerCase()) {
            case "value":
                return Comparator.comparing(Portfolio::getTotalValue);
            case "name":
                return Comparator.comparing(Portfolio::getName);
            default:
                // if invalid criteria
                return null;

        }
    }

    // move investment between portfolios
    public List<Portfolio> moveInvestment(UUID fromPortfolioId, UUID toPortfolioId, UUID investmentId) {
        // get the portfolios and investment by their id
        Portfolio fromPortfolio = portfolioRepository.getPortfoliosMap().get(fromPortfolioId);
        Portfolio toPortfolio = portfolioRepository.getPortfoliosMap().get(toPortfolioId);
        Investment investmentToMove = investmentRepository.findById(fromPortfolioId, investmentId);

        // delete the investment from the source portfolio (also updates the JSON)
        investmentRepository.deleteInvestment(fromPortfolioId, investmentId);

        // add the investment to the destination portfolio (also updates the JSON)
        investmentRepository.save(toPortfolioId, investmentToMove);

        return portfolioRepository.findAll();
    }

    // TODO OTHER LOGIC
    // sort portolio by name (if there's more than one portfolio)
    // move a stock between portfolios
}
