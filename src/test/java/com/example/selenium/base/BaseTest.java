package com.example.selenium.base;

import org.openqa.selenium.WebDriver;
import com.example.selenium.config.ConfigReader;
import com.example.selenium.config.DriverFactory;

public class BaseTest {

	protected WebDriver driver;
	private ConfigReader configReader;

	public void setUp() {
		// Initialize ConfigReader
		configReader = ConfigReader.getInstance();

		// Fetch configuration values
		String browser = configReader.getStringProperty("browser");
		int implicitWait = configReader.getIntProperty("implicitWait");

		// Initialize WebDriver using DriverFactory
		driver = DriverFactory.initializeDriver(browser, implicitWait);

		// Navigate to base URL
		String baseUrl = configReader.getStringProperty("baseUrl");
		driver.get(baseUrl);
	}

	public void tearDown() {
		// Quit WebDriver

		DriverFactory.quitDriver();
	}

}
