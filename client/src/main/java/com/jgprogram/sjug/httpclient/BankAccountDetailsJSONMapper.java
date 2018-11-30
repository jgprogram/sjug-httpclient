package com.jgprogram.sjug.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class BankAccountDetailsJSONMapper {

    private final ObjectMapper objectMapper;

    public BankAccountDetailsJSONMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public BankAccountDetails map(String bankAccountDetailsJSON) {
        try {
            return objectMapper.readValue(bankAccountDetailsJSON, BankAccountDetails.class);
        } catch (IOException e) {
            e.printStackTrace();
            return BankAccountDetails.sample();
        }
    }

    public BankAccountDetails map(byte[] bankAccountDetailsBytes) {
        try {
            var b = objectMapper.readValue(bankAccountDetailsBytes, BankAccountDetails.class);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return BankAccountDetails.sample();
        }
    }
}