package com.jgprogram.sjug.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class ExchangeRatesUpdateJSONMapper {

    private ObjectMapper objectMapper;

    public ExchangeRatesUpdateJSONMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public ExchangeRatesUpdate map(String strData) {
        try {
            return objectMapper.readValue(strData, ExchangeRatesUpdate.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
