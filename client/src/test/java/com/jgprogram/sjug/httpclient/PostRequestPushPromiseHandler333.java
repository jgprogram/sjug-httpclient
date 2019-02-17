package com.jgprogram.sjug.httpclient;

import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PostRequestPushPromiseHandler333 {

    private static final String HTTP_BASE_URL = "https://localhost:3445";
    private static final String MONEY_TRANSFER_URI = HTTP_BASE_URL + "/api/money-transfer";
    private static final String CONTENT_TYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String IBAN_FROM = "PL04104022223333444455556666";
    private static final String IBAN_TO = "PL11402022223333444455556666";
    private static final int STATUS_OK = 200;
    private final MoneyTransferRequestJSONMapper moneyTransferRequestJSONMapper = new MoneyTransferRequestJSONMapper();
    private final BankAccountDetailsJSONMapper bankAccountDetailsJSONMapper = new BankAccountDetailsJSONMapper();

    private HttpResponse<byte[]> transferConfirmationPdfHttpResponse;
    private BankAccountDetails bankAccountDetails;

    @After
    public void cleanup() {
        transferConfirmationPdfHttpResponse = null;
        bankAccountDetails = null;
    }

    @Test
    public void shouldTransferMoneyFromOneAccountToAnother() throws InterruptedException, TimeoutException, ExecutionException {
        // given
        var bankTransferRequest = MoneyTransferRequest.of(IBAN_FROM, IBAN_TO, BigDecimal.valueOf(500), "PLN");
        // and
        var httpClient = HttpClient.newHttpClient();
        // and
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(MONEY_TRANSFER_URI))
                .POST(HttpRequest.BodyPublishers.ofString(
                        moneyTransferRequestJSONMapper.map(bankTransferRequest)
                ))
                .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        // when
        httpClient.sendAsync(
                httpRequest,
                HttpResponse.BodyHandlers.ofByteArray(),
                handlePushPromise())
                .thenAccept(httpResponse -> transferConfirmationPdfHttpResponse = httpResponse);

        pauseSeconds(1);

        // then
        assertThat(transferConfirmationPdfHttpResponse.statusCode(), is(200));
        assertTrue(transferConfirmationPdfHttpResponse.body().length > 0);
        // and
        assertThat(bankAccountDetails.getIban(), is("PL04104022223333444455556666"));
        assertThat(bankAccountDetails.getBalance(), is(BigDecimal.valueOf(0)));
        assertThat(bankAccountDetails.getCurrency(), is("PLN"));
    }

    private HttpResponse.PushPromiseHandler<byte[]> handlePushPromise() {
        return (initiatingRequest, pushPromiseRequest, acceptor) ->
                acceptor.apply(HttpResponse.BodyHandlers.ofByteArray())
                        .thenApply(HttpResponse::body)
                        .thenApply(bankAccountDetailsJSONMapper::map)
                        .thenAccept(accountDetails -> bankAccountDetails = accountDetails);
    }

    private void pauseSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
