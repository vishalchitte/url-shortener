package com.urlshortener.url_shortener;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Gets the path after / to extract the short code Queries DB for that code If
 * found, sends HTTP 302 redirect to the original URL If not found, returns 404
 * Not Found If no path (home page), shows server running message
 */

public class RootHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// Get the requested path, removing leading "/"
		String path = exchange.getRequestURI().getPath().substring(1);

		//  Handle OPTIONS preflight request for CORS
		if ("OPTIONS".equals(exchange.getRequestMethod())) {
			exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
			exchange.sendResponseHeaders(204, -1); // ✅ 204 No Content
			return;
		}

		if (path.isEmpty()) {
			// If no path provided, show welcome message
			String response = "URL Shortener Server is running";
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
			return;
		}

		try {
			// Check DB if this short URL exists
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT original_url FROM urls WHERE short_url = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, path);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				// If found, redirect to original URL
				String originalUrl = rs.getString("original_url");
				exchange.getResponseHeaders().add("Location", originalUrl);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_TEMP, -1);
			} else {
				// If not found, show error message
				String response = "Short URL not found!";
				exchange.sendResponseHeaders(404, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			String response = "Error occurred during redirection";
			exchange.sendResponseHeaders(500, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
