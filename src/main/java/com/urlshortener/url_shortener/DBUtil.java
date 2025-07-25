package com.urlshortener.url_shortener; 

import java.sql.Connection;           
import java.sql.DriverManager;        
import java.sql.SQLException;         

public class DBUtil {

    // Method to get a Connection object for H2 database
    public static Connection getConnection() throws SQLException {
    
    	//  Database URL with path './data/urlshortener'
        // './data' means folder relative to project root
        String url = "jdbc:h2:./data/urlshortener";

        //  Username and password for H2 database
        String username = "sa";
        String password = "";

        //  Get and return the connection
        return DriverManager.getConnection(url, username, password);
    }
}
