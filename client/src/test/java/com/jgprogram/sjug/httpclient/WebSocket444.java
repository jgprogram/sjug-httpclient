package com.jgprogram.sjug.httpclient;

import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class WebSocket444 {

    private static final String WS_BASE_URL = "ws://localhost:3446";
    private final ExchangeRatesUpdateJSONMapper jsonMapper = new ExchangeRatesUpdateJSONMapper();
    private ExchangeRatesUpdate exchangeRatesUpdate;
    private boolean messageSent = false;

    @After
    public void cleanup() {
        exchangeRatesUpdate = null;
        messageSent = false;
    }

    @Test
    public void shouldSendAndReceiveMessageFromWebSocket() {
        // when
        HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .connectTimeout(Duration.ofMinutes(1))
                .buildAsync(
                        URI.create(WS_BASE_URL),
                        webSocketListener())
                .thenAccept(this::onWebSocketReady);

        pauseSeconds(1);

        // then
        assertTrue(messageSent);
        // and
        var rates = exchangeRatesUpdate.rates();
        assertThat(rates.size(), is(2));
        assertTrue(rates.containsAll(Set.of(
                rateOf("EUR", 4.3243),
                rateOf("USD", 3.8326))));
    }

    private ExchangeRatesUpdate.Rate rateOf(String eur, double v) {
        return ExchangeRatesUpdate.Rate.of("EUR", BigDecimal.valueOf(v));
    }

    private void onWebSocketReady(WebSocket webSocket) {
        webSocket.sendText("PLN", true);
        messageSent = true;
    }

    private WebSocket.Listener webSocketListener() {
        return new WebSocket.Listener() {

            CompletableFuture allPartReceived = new CompletableFuture();
            StringBuilder strBuilder = new StringBuilder();

            @Override
            public CompletionStage<?> onText(WebSocket webSocket,
                                             CharSequence data,
                                             boolean last) {
                strBuilder.append(data);
                webSocket.request(1);

                if (last) {
                    exchangeRatesUpdate = jsonMapper.map(strBuilder.toString());
                    allPartReceived.complete(exchangeRatesUpdate);
                    var completedStage = allPartReceived;
                    cleanup();

                    return completedStage;
                }

                return allPartReceived;
            }

            private void cleanup() {
                allPartReceived = new CompletableFuture();
                strBuilder = new StringBuilder();
            }
        };
    }

    private void pauseSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
