package com.urlshortener.url_shortener;

import java.sql.Connection;
import java.sql.Statement;

public class DBSetup {
	public static void main(String[] args) {
		try {
			// Get database connection
			Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();

			// SQL for creating 'users' table
			String createUsers = "CREATE TABLE IF NOT EXISTS users (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "username VARCHAR(255) UNIQUE NOT NULL," + "password VARCHAR(255) NOT NULL" + ");";

			// SQL for creating 'urls' table
			String createUrls = "CREATE TABLE IF NOT EXISTS urls (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "original_url VARCHAR(2000) NOT NULL," + "short_url VARCHAR(100) UNIQUE NOT NULL,"
					+ "user_id INT," + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
					+ "FOREIGN KEY (user_id) REFERENCES users(id)" + ");";

			// ✅ Execute table creation queries
			stmt.execute(createUsers);
			stmt.execute(createUrls);

			System.out.println("Tables created successfully!");

			// ✅ Close connection
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
