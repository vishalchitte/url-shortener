package com.urlshortener.url_shortener;

import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getConnection(); //  Get connection
            System.out.println("H2 Database connected successfully!");

            conn.close(); //  Close connection
        } catch (Exception e) {
            e.printStackTrace(); //  Print any errors
        }
    }
}
