package com.jgprogram.sjug;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUsages {

    private static final String HTTP_BASE_URL = "http://localhost:3080";
    private static final String HTTPS_BASE_URL = "http://localhost:3443";

    public void runHttpAspects() {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(HTTP_BASE_URL))
                .GET()
                .setHeader("Content-type", "application/json")
                .build();

        try {
            String response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
