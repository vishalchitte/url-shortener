package com.urlshortener.url_shortener;

import static org.junit.Assert.*;
import org.junit.Test;

/* Helps you explain confidently in viva why each import and assertion is used
✔ Shows your clarity in unit testing basics
✔ Demonstrates professional code documentation*/
public class MainTest {

	// Test to ensure Main.main runs without throwing exceptions
	@Test
	public void testMainRuns() {
		try {
			// Call main method with empty args
			Main.main(new String[] {});

			// If no exception is thrown, test passes
			assertTrue(true);
		} catch (Exception e) {
			// If exception occurs, fail the test with error message
			fail("Exception in Main: " + e.getMessage());
		}
	}
}
