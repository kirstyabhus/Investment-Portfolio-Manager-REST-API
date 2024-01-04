package com.cbfacademy.apiassessment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.service.PortfolioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller handling portolio-related operations.
 * Exposes endpoints for managing portfolios.
 */
@RestController
@RequestMapping("/api/v1/portfolios")
public class PortfolioController {

    private final PortfolioService service;

    @Autowired
    PortfolioController(PortfolioService service) {
        this.service = service;
    }

    @Operation(summary = "Get all portfolios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Portfolios retrieved", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to fetch portfolios", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("")
    public ResponseEntity<?> getAllPortfolios() {
        try {

            List<Portfolio> portfolios = service.getPortfolios();
            return ResponseEntity.ok(portfolios);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to fetch portfolios - " + e.getMessage());
        }
    }

    @Operation(summary = "Get portfolio by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Portfolio retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Portfolio.class))),
            @ApiResponse(responseCode = "404", description = "Portfolio Not Found: Unable to find portfolio", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to fetch portfolio", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPortfolioById(@PathVariable UUID id) {
        try {

            Portfolio portfolio = service.getPortfolioById(id);

            if (portfolio != null) {

                return ResponseEntity.ok(portfolio);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Portfolio Not Found: Unable to find portfolio with ID " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to fetch portfolio - " + e.getMessage());
        }
    }

    @Operation(summary = "Create a new portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Portfolio created successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to create portfolio", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(path = "/new", produces = "application/json")
    public ResponseEntity<?> createPortfolio(@RequestBody Portfolio portfolio) {
        try {
            Portfolio createdPortfolio = service.createOrUpdatePortfolio(portfolio);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdPortfolio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to create portfolio - " + e.getMessage());
        }
    }

    @Operation(summary = "Update a portfolio by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Portfolio updated successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Portfolio Not Found: Unable to find portfolio", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to update portfolio", content = @Content(mediaType = "text/plain"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePortfolio(@PathVariable UUID id, @RequestBody Portfolio portfolio) {
        try {

            Portfolio updatedPortfolio = service.createOrUpdatePortfolio(portfolio);

            if (updatedPortfolio != null) {
                return ResponseEntity.ok(updatedPortfolio);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Portfolio Not Found: Unable to find portfolio with ID " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to update portfolio - " + e.getMessage());
        }
    }

    @Operation(summary = "Delete a portfolio by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Portfolio deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to delete portfolio", content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortfolio(@PathVariable UUID id) {
        try {

            service.deletePortfolio(id);

            String feedbackMessage = "Portfolio deleted successfully";
            return ResponseEntity.ok().body(feedbackMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to delete portfolio - " + e.getMessage());
        }
    }

    @Operation(summary = "Get portfolios sorted by specified criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sorted portfolios retrieved", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to fetch sorted portfolios", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/sorted")
    public ResponseEntity<?> getSortedPortfolios(
            @RequestParam(name = "sort_by", defaultValue = "name") String sortCriteria,
            @RequestParam(name = "order_by", defaultValue = "asc") String sortOrder) {
        try {

            List<Portfolio> sortedPortfolios = service.getSortedPortfolios(sortCriteria, sortOrder);

            return ResponseEntity.ok(sortedPortfolios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to fetch sorted portfolios - " + e.getMessage());
        }
    }

    @Operation(summary = "Move investment between portfolios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investment moved successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unable to move investment", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(path = "/move-investment", produces = "application/json")
    public ResponseEntity<?> moveInvestment(@RequestParam("fromPortfolioId") UUID fromPortfolioId,
            @RequestParam("toPortfolioId") UUID toPortfolioId,
            @RequestParam("investmentId") UUID investmentId) {
        try {
            service.moveInvestment(fromPortfolioId, toPortfolioId, investmentId);

            String feedbackMessage = "Investment moved successfully";
            return ResponseEntity.ok().body(feedbackMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: Unable to move investment - " + e.getMessage());
        }
    }

}
