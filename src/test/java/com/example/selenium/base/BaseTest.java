package com.example.selenium.base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.selenium.config.ConfigReader;
import com.example.selenium.config.DriverFactory;
import com.example.selenium.utils.LocatorReader;

public class BaseTest {

	protected static WebDriver driver;
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

	public static void enterText(String locatorKey, String text)
			throws NoSuchElementException, ElementNotInteractableException {

		WebElement textField = getElement(locatorKey);

		// Check if the text field is enabled
		if (!textField.isEnabled()) {
			throw new ElementNotInteractableException("Text field is disabled: " + locatorKey);
		}

		// If enabled, send the text
		textField.sendKeys(text);
	}

	public static void selectOption(String locatorKey, String option)
			throws NoSuchElementException, ElementNotInteractableException, StaleElementReferenceException {

		List<WebElement> optionList = getElements(locatorKey);

		// Check if the list is empty
		if (optionList.isEmpty()) {
			throw new NoSuchElementException("No options found for locatorKey: " + locatorKey);
		}

		for (WebElement opt : optionList) {

			if (opt.getText().equalsIgnoreCase(option)) {

				opt.sendKeys(option);
				return;
			}
		}

		throw new NoSuchElementException("Option '" + option + "' not found in dropdown for locatorKey: " + locatorKey);
	}

	public static void clickElement(String locatorKey) throws NoSuchElementException, ElementNotInteractableException {

		WebElement button = getElement(locatorKey);

		if (!button.isEnabled()) {

			throw new ElementNotInteractableException("Error: Button is not interactable: " + locatorKey);
		}

		button.click();
	}

	private static List<WebElement> getElements(String locatorKey) {

		List<WebElement> elements = null;

		if (!isElementPresent(locatorKey))

			System.out.println("Element is not present with locatorKey:" + locatorKey);

		elements = driver.findElements(getlocator(locatorKey));

		return elements;
	}

	public static WebElement getElement(String locatorKey) {

		WebElement element = null;

		if (!isElementPresent(locatorKey))

			System.out.println("Element is not present with locatorKey:" + locatorKey);

		element = driver.findElement(getlocator(locatorKey));

		return element;

	}

	private static By getlocator(String locatorKey) {

		By by = null;

		if (locatorKey.endsWith("_id")) {

			by = By.id(LocatorReader.getLocator(locatorKey));
		}
		if (locatorKey.endsWith("_name")) {

			by = By.name(LocatorReader.getLocator(locatorKey));
		}
		if (locatorKey.endsWith("_className")) {

			by = By.className(LocatorReader.getLocator(locatorKey));
		}
		if (locatorKey.endsWith("_linkText")) {

			by = By.linkText(LocatorReader.getLocator(locatorKey));
		}
		if (locatorKey.endsWith("_partialLinkText")) {

			by = By.partialLinkText(LocatorReader.getLocator(locatorKey));
		}
		if (locatorKey.endsWith("_xpath")) {

			by = By.xpath(LocatorReader.getLocator(locatorKey));
		}

		if (locatorKey.endsWith("_cssSelector")) {

			by = By.cssSelector(LocatorReader.getLocator(locatorKey));
		}

		return by;
	}

	private static boolean isElementPresent(String locatorKey) {
		System.out.println("check for the Element Present.......");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		try {

			wait.until(ExpectedConditions.presenceOfElementLocated(getlocator(locatorKey)));
			return true; // Element is present
		} catch (NoSuchElementException e) {
			System.out.println("Element not found: " + e.getMessage());
			return false; // Element is not present
		} catch (TimeoutException e) {
			System.out.println("Timeout while waiting for element: " + e.getMessage());
			return false; // Element was not found within the timeout
		} catch (Exception e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());
			return false; // Handle any other exceptions
		}

	}

}
