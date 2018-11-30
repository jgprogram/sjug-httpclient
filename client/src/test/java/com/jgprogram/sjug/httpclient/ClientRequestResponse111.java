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

    private static final String HTTP_BASE_URL = "http://localhost:3080";
    private static final String BANK_ACCOUNT_DETAILS_URI = HTTP_BASE_URL + "/api/account-details";
    private static final String CONTENT_TYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String IBAN = "PL04104022223333444455556666";
    private static final int STATUS_OK = 200;

    @Test
    public void shouldGetBankAccountDetails() throws IOException, InterruptedException {
        // given
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .authenticator(BasicAuthenticator.of("user", "pass".toCharArray()))
                .build();
        // and
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(BANK_ACCOUNT_DETAILS_URI + "/" + IBAN))
                .GET()
                .build();

        // when
        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(httpResponse.statusCode(), is(STATUS_OK));
        assertThat(httpResponse.body(), isA(String.class));
        assertTrue(httpResponse.headers().firstValue(CONTENT_TYPE).isPresent());
    }
}
