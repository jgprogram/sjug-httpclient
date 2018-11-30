package com.jgprogram.sjug.httpclient;

import java.math.BigDecimal;

public class BankAccountDetails {

    private String iban;
    private BigDecimal balance;
    private String currency;

    // JSON mapper
    private BankAccountDetails() {
    }

    public BankAccountDetails(String iban, BigDecimal balance, String currency) {
        this.iban = iban;
        this.balance = balance;
        this.currency = currency;
    }

    public static BankAccountDetails sample() {
        return new BankAccountDetails(
                "0000000000000000000000000000",
                BigDecimal.ZERO,
                "EUR"
        );
    }

    public String getIban() {
        return iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
