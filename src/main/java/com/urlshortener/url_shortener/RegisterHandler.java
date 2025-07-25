package com.urlshortener.url_shortener;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// ✅ Handle OPTIONS preflight request
		if ("OPTIONS".equals(exchange.getRequestMethod())) {
			exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
			exchange.sendResponseHeaders(204, -1);
			return;
		}

		// ✅ Allow only POST method
		if (!"POST".equals(exchange.getRequestMethod())) {
			String response = "Method Not Allowed";
			exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			exchange.sendResponseHeaders(405, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
			return;
		}

		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

		// ✅ Read username and password from request body
		BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		String username = br.readLine();
		String password = br.readLine();

		try {
			Connection conn = DBUtil.getConnection();

			// ✅ Check if username already exists
			String checkSql = "SELECT * FROM users WHERE username = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setString(1, username);
			ResultSet rs = checkStmt.executeQuery();

			if (rs.next()) {
				// ✅ Username exists, return conflict message
				String response = "Username already exists. Please choose another.";
				byte[] responseBytes = response.getBytes();
				exchange.sendResponseHeaders(409, responseBytes.length);
				OutputStream os = exchange.getResponseBody();
				os.write(responseBytes);
				os.close();
				conn.close();
				return;
			}

			// ✅ Insert new user
			String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
			PreparedStatement insertStmt = conn.prepareStatement(insertSql);
			insertStmt.setString(1, username);
			insertStmt.setString(2, password);
			insertStmt.executeUpdate();

			conn.close();

//            String response = "User registered successfully!";
//            byte[] responseBytes = response.getBytes();
//            exchange.sendResponseHeaders(200, responseBytes.length);
//            OutputStream os = exchange.getResponseBody();
//            os.write(responseBytes);
//            os.close();

			String response = "User registered successfully!";
			byte[] responseBytes = response.getBytes();
			exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
			exchange.sendResponseHeaders(200, responseBytes.length);
			OutputStream os = exchange.getResponseBody();
			os.write(responseBytes);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			String response = "Error occurred while registering user";
			byte[] responseBytes = response.getBytes();
			exchange.sendResponseHeaders(500, responseBytes.length);
			OutputStream os = exchange.getResponseBody();
			os.write(responseBytes);
			os.close();
		}
	}
}
