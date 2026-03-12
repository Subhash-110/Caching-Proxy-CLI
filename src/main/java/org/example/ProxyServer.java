package org.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProxyServer {

    public static void start(int port, String origin) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", exchange -> handleRequest(exchange, origin));

        server.setExecutor(null);
        server.start();

        System.out.println("Caching Proxy running on port " + port);
        System.out.println("Forwarding requests to " + origin);
    }

    private static void handleRequest(HttpExchange exchange, String origin) throws IOException {

        String path = exchange.getRequestURI().toString();
        String targetUrl = origin + path;

        String responseBody;

        if (CacheManager.contains(targetUrl)) {

            responseBody = CacheManager.get(targetUrl);

            exchange.getResponseHeaders().add("X-Cache", "HIT");

        } else {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .GET()
                    .build();

            try {
                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                responseBody = response.body();

                CacheManager.put(targetUrl, responseBody);

                exchange.getResponseHeaders().add("X-Cache", "MISS");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        exchange.sendResponseHeaders(200, responseBody.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(responseBody.getBytes());
        os.close();
    }
}