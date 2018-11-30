package com.jgprogram.sjug.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MoneyTransferRequestJSONMapper {

    private final ObjectMapper objectMapper;

    public MoneyTransferRequestJSONMapper() {
        objectMapper = new ObjectMapper();
    }

    public String map(MoneyTransferRequest moneyTransferRequest) {
        try {
            return objectMapper.writeValueAsString(moneyTransferRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
