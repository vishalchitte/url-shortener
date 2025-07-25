package com.urlshortener.url_shortener;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

public class ShortenHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // ✅ Handle OPTIONS preflight request for CORS
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
            exchange.sendResponseHeaders(204, -1); // ✅ 204 No Content
            return;
        }

        // ✅ Allow only POST requests for actual shortening
        if (!"POST".equals(exchange.getRequestMethod())) {
            String response = "Method Not Allowed";
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");

            exchange.sendResponseHeaders(405, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return;
        }

        // ✅ Add CORS headers for POST success responses too
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");

        // ✅ Read the original URL from request body
        InputStream is = exchange.getRequestBody();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String originalUrl = br.readLine();

        // ✅ Generate a random short code (first 6 characters of UUID)
        String shortCode = UUID.randomUUID().toString().substring(0, 6);

        try {
            // ✅ Insert into urls table using JDBC
            Connection conn = DBUtil.getConnection();
            String sql = "INSERT INTO urls (original_url, short_url) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, originalUrl);
            pstmt.setString(2, shortCode);
            pstmt.executeUpdate();

            conn.close();

            // ✅ Return the short code as response
            String response = "Short URL code: " + shortCode;
            byte[] responseBytes = response.getBytes();
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            String response = "Error occurred while shortening URL";
            byte[] responseBytes = response.getBytes();
            exchange.sendResponseHeaders(500, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }
}
