package com.example.selenium.tests;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import com.example.selenium.base.BaseTest;
import com.example.selenium.utils.LocatorReader;

public class LoginTest extends BaseTest {

	public void testLogin() {
		setUp(); // Sets up WebDriver and navigates to the base URL

		System.out.println("Title of the page: " + driver.getTitle());

		try {

			selectOption("categoryDropdown_xpath", "Books");

			enterText("searchBox_id", "Harrypoter");

			clickElement("searchButton_id");
		} catch (NoSuchElementException e) {

			System.out.println(e.getMessage());

		} catch (ElementNotInteractableException e) {

			System.out.println(e.getMessage());

		} catch (StaleElementReferenceException e) {

			System.out.println(e.getMessage());
		}

		// tearDown(); // Quits WebDriver

	}

	public static void main(String[] args) throws InterruptedException {
		LoginTest loginTest = new LoginTest();
		loginTest.testLogin();

	}
}
