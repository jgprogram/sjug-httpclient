package com.jgprogram.sjug.httpclient;

import org.junit.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AsyncJson222 {

    private static final String HTTP_BASE_URL = "http://localhost:3080";
    private static final String BANK_ACCOUNT_DETAILS_URI = HTTP_BASE_URL + "/api/account-details";
    private static final String CONTENT_TYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String IBAN = "PL04104022223333444455556666";
    private static final BigDecimal BALANCE = BigDecimal.valueOf(522.41);
    private static final String CURRENCY = "PLN";
    private static final int STATUS_OK = 200;
    private final BankAccountDetailsJSONMapper bankAccountDetailsJSONMapper = new BankAccountDetailsJSONMapper();

    @Test
    public void shouldGetBankAccountDetailsAsync() throws InterruptedException, TimeoutException, ExecutionException {
        // given
        var httpClient = preconfiguredHttpClient();
        // and
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(BANK_ACCOUNT_DETAILS_URI + "/" + IBAN))
                .GET()
                .build();

        // when
        var bankAccountDetails = new BankAccountDetails(null, null, null);

        // then
        assertThat(bankAccountDetails.getIban(), is(IBAN));
        assertThat(bankAccountDetails.getBalance(), is(BALANCE));
        assertThat(bankAccountDetails.getCurrency(), is(CURRENCY));
    }

    private HttpClient preconfiguredHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .authenticator(BasicAuthenticator.of("user", "pass".toCharArray()))
                .build();
    }
}
