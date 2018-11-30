package com.jgprogram.sjug.httpclient;

import org.junit.After;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WebSocket444 {

    private static final String WS_BASE_URL = "ws://localhost:3446";
    private static final String EXPECTED_MSG = "Hi from WS server!";
    private String messageFromWS;

    @After
    public void cleanup() {
        messageFromWS = null;
    }

    @Test
    public void shouldSendAndReceiveMessageFromWebSocket() {
        // when send message to the server
        HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .connectTimeout(Duration.ofMinutes(1))
                .buildAsync(
                        URI.create(WS_BASE_URL),
                        new WebSocket.Listener() {
                            CompletableFuture allPartReceived = new CompletableFuture();

                            StringBuilder strBuilder = new StringBuilder();

                            @Override
                            public CompletionStage<?> onText(WebSocket webSocket,
                                                             CharSequence data,
                                                             boolean last) {
                                strBuilder.append(data);
                                webSocket.request(1);

                                if (last) {
                                    messageFromWS = strBuilder.toString();

                                    strBuilder = new StringBuilder();
                                    allPartReceived.complete(messageFromWS);
                                    var completedStage = allPartReceived;
                                    allPartReceived = new CompletableFuture();

                                    return completedStage;
                                }

                                return allPartReceived;
                            }
                        })
                .thenAccept(ws -> ws.sendText("Hi from client", true));

        pauseSeconds(1);

        // then the server responds "Hi from server"
        assertThat(messageFromWS, is(EXPECTED_MSG));
    }

    private void pauseSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
