package com.example.selenium.tests;

import com.example.selenium.base.BaseTest;

public class LoginTest extends BaseTest {

	public void testLogin() {
		setUp(); // Sets up WebDriver and navigates to the base URL

		
		System.out.println("Title of the page: " + driver.getTitle());

		tearDown(); // Quits WebDriver
	}

	public static void main(String[] args) throws InterruptedException {
		LoginTest loginTest = new LoginTest();
		loginTest.testLogin();
	}
}
