package com.urlshortener.url_shortener;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * We read username and password from request body
 Query users table to check credentials
 Send appropriate success or failure response
**/

public class LoginHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
	
		//  Handle OPTIONS preflight request for CORS
		if ("OPTIONS".equals(exchange.getRequestMethod())) {
			exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
			exchange.sendResponseHeaders(204, -1); // ✅ 204 No Content
			return;
		}

		// Allow only POST method
		if (!"POST".equals(exchange.getRequestMethod())) {
			String response = "Method Not Allowed";
			exchange.sendResponseHeaders(405, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
			return;
		}

		// ✅ Read username and password from request body
		InputStream is = exchange.getRequestBody();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String username = br.readLine();
		String password = br.readLine();

		try {
			// ✅ Check if user exists in database with given username and password
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			String response;

			if (rs.next()) {
				// ✅ User found, login success
				response = "Login successful!";
			} else {
				// ✅ User not found, invalid credentials
				response = "Invalid username or password.";
			}

			conn.close();

			// ✅ Send response
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
			String response = "Error occurred while logging in";
			exchange.sendResponseHeaders(500, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
