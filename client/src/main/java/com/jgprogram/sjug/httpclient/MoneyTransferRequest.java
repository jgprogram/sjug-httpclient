package com.jgprogram.sjug.httpclient;

import java.math.BigDecimal;

public class MoneyTransferRequest {
    private String ibanFrom;
    private String ibanTo;
    private BigDecimal amount;
    private String currency;

    public static MoneyTransferRequest of(String ibanFrom, String ibanTo, BigDecimal amount, String currency) {
        return new MoneyTransferRequest(ibanFrom, ibanTo, amount, currency);
    }

    // JSON Serializer
    private MoneyTransferRequest() {
    }

    public MoneyTransferRequest(String ibanFrom, String ibanTo, BigDecimal amount, String currency) {
        this.ibanFrom = ibanFrom;
        this.ibanTo = ibanTo;
        this.amount = amount;
        this.currency = currency;
    }

    public String getIbanFrom() {
        return ibanFrom;
    }

    public String getIbanTo() {
        return ibanTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
