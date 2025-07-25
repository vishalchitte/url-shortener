package com.urlshortener.url_shortener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBCheck {
    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getConnection();
            String sql = "SELECT * FROM urls";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Original URL: " + rs.getString("original_url") +
                        ", Short URL: " + rs.getString("short_url"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
