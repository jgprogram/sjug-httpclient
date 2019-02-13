package com.jgprogram.sjug.httpclient;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ClientRequestResponse111 {

    private static final String HTTP_BASE_URL = "http://localhost:3080/api/account-details";
    private static final String IBAN = "PL04104022223333444455556666";
    private static final String BANK_ACCOUNT_DETAILS_URI = HTTP_BASE_URL + IBAN;
    private static final String CONTENT_TYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";

    private static final int STATUS_OK = 200;

    @Test
    public void shouldGetBankAccountDetails() throws IOException, InterruptedException {
        // given

        // and

        // when


        // then

    }
}
