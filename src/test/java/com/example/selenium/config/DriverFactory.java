package com.example.selenium.config;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	private static WebDriver driver;

	public static WebDriver initializeDriver(String browser, int implicitWait) {

		if (driver == null) {

			switch (browser.toLowerCase()) {

			case "chrome":
				
           
				driver = new ChromeDriver();
				
				break;

			case "firefox":

				driver = new FirefoxDriver();
				break;

			default:
				throw new IllegalArgumentException("Unsupported browser: " + browser);

			}

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		driver.manage().deleteAllCookies();
		System.out.println("Deleted all cookies to avoid potential CAPTCHA issues.");
		return driver;

	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

}
