package com.cbfacademy.apiassessment.model;

import java.util.UUID;

import jakarta.persistence.Entity;

/**
 * Represents a stock investment.
 * Implements the Investment interface.
 */
@Entity
public class Stock implements Investment {
    private UUID id;
    private String type = "Stock";
    private String symbol;
    private String name;
    private int sharesQuantity;
    private double purchasePrice;
    private double totalValue;
    private double currentValue; // TODO Fetch current value from API?

    // private Double esgScore; // TODO

    /**
     * Default constructor used by GSON during JSON deserialization.
     * Sets a random UUID for the Stock.
     */
    public Stock() {
        this.id = UUID.randomUUID();
    }

    /**
     * Constructs an Stock object when the UUID is provided explicitly.
     * Calculates the total value based on shares quantity and purchase price.
     */
    public Stock(UUID id, String type, String symbol, String name, int sharesQuantity, double purchasePrice,
            double totalValue,
            double currentValue) {
        this.id = id;
        this.type = type;
        this.symbol = symbol;
        this.name = name;
        this.sharesQuantity = sharesQuantity;
        this.purchasePrice = purchasePrice;
        this.totalValue = sharesQuantity * purchasePrice;
        this.currentValue = currentValue;
    }

    /**
     * Constructs an Stock object with a randomly generated UUID.
     * Used when the UUID is not provided explicitly.
     * Calculates the total value based on shares quantity and purchase price.
     */
    public Stock(String type, String symbol, String name, int sharesQuantity, double purchasePrice, double totalValue,
            double currentValue) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.symbol = symbol;
        this.name = name;
        this.sharesQuantity = sharesQuantity;
        this.purchasePrice = purchasePrice;
        this.totalValue = sharesQuantity * purchasePrice;
        this.currentValue = currentValue;
    }

    /*
     * public Double getEsgScore() {
     * return esgScore;
     * }
     * 
     * public void setEsgScore(Double esgScore) {
     * this.esgScore = esgScore;
     * }
     */

    @Override
    public String toString() {
        return String.format(
                "Stock[id=%s, type=%s, symbol='%s', name='%s', sharesQuantity='%d', purchasePrice='%.2f', totalValue='%.2f', currentValue='%.2f']",
                id, type, symbol, name, sharesQuantity, purchasePrice, totalValue, currentValue);
    }

    public String getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSharesQuantity() {
        return sharesQuantity;
    }

    public void setSharesQuantity(int sharesQuantity) {
        this.sharesQuantity = sharesQuantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void calculateTotalValue() {
        this.totalValue = this.sharesQuantity * this.purchasePrice;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

}
