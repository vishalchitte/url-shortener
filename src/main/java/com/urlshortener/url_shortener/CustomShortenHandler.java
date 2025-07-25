package com.urlshortener.url_shortener;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 *  Validates logged-in user
	Checks uniqueness of custom short code
 	Inserts into DB and returns success or appropriate error

 * 	Created CustomShortenHandler.java with code
 	Registered in Main.java
 	Tested using Postman, confirmed working*/

public class CustomShortenHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// Allow only POST method
		// ✅ Handle OPTIONS preflight request for CORS
		if ("OPTIONS".equals(exchange.getRequestMethod())) {
			exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
			exchange.sendResponseHeaders(204, -1); // ✅ 204 No Content
			return;
		}

		if (!"POST".equals(exchange.getRequestMethod())) {
			String response = "Method Not Allowed";
			exchange.sendResponseHeaders(405, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
			return;
		}

		// Read username, original URL, and custom code from request body
		BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		String username = br.readLine();
		String originalUrl = br.readLine();
		String customCode = br.readLine();

		try {
			Connection conn = DBUtil.getConnection();

			// Get user ID for provided username
			String getUserSql = "SELECT id FROM users WHERE username = ?";
			PreparedStatement getUserStmt = conn.prepareStatement(getUserSql);
			getUserStmt.setString(1, username);
			ResultSet userRs = getUserStmt.executeQuery();

			if (!userRs.next()) {
				// User not found
				String response = "User not found. Please login.";
				exchange.sendResponseHeaders(401, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
				conn.close();
				return;
			}

			int userId = userRs.getInt("id");

			// Check if custom code is already taken
			String checkSql = "SELECT * FROM urls WHERE short_url = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setString(1, customCode);
			ResultSet checkRs = checkStmt.executeQuery();

			if (checkRs.next()) {
				// Custom code already exists
				String response = "Custom short code already taken. Choose another.";
				exchange.sendResponseHeaders(409, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
				conn.close();
				return;
			}

			// Insert new URL with custom code
			String insertSql = "INSERT INTO urls (original_url, short_url, user_id) VALUES (?, ?, ?)";
			PreparedStatement insertStmt = conn.prepareStatement(insertSql);
			insertStmt.setString(1, originalUrl);
			insertStmt.setString(2, customCode);
			insertStmt.setInt(3, userId);
			insertStmt.executeUpdate();

			conn.close();

			String response = "Custom short URL created successfully: " + customCode;
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
			String response = "Error occurred while creating custom short URL";
			exchange.sendResponseHeaders(500, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
