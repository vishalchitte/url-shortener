package com.urlshortener.url_shortener; 

//  Importing static assert methods from JUnit
import static org.junit.Assert.*;
import org.junit.Test; 
import java.sql.Connection; 

public class DBUtilTest {

    //  Test method to check DB connection
    @Test
    public void testConnectionNotNull() {
        try {
            //  Attempt to get connection using DBUtil
            Connection conn = DBUtil.getConnection();
            
            //  Assert that connection is not null (test passes if true)
            assertNotNull("Database connection should not be null", conn);
            
            //  Close connection after test
            conn.close();
        } catch (Exception e) {
            //  If any exception occurs, fail the test with exception message
            fail("Exception while connecting to DB: " + e.getMessage());
        }
    }
}
