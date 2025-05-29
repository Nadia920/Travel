package com.java.Travel.controller.dto;

import org.wildfly.common.annotation.NotNull;


public class BankCardDTO {

    @NotNull
    private Double balance;

    @NotNull
    private String cardOwner;

    @NotNull
    private String cardNumber;

    @NotNull
    private String expiration;

    @NotNull
    private String cvc;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }


}