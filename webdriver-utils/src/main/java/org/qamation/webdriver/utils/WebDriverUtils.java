package org.qamation.webdriver.utils;

import java.util.regex.Matcher;

import org.openqa.selenium.*;
import org.qamation.utils.RegExpUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebDriverUtils {
	protected WebDriver driver;

	protected String browserName;



    public static JavascriptExecutor getJavaScriptExecutor(WebDriver driver) {
        if (driver instanceof JavascriptExecutor) {
            TimeOutsConfig.setDriverTimeOuts(driver);
            return (JavascriptExecutor) driver;
        } else throw new RuntimeException("Cannot turn this driver into JavaScript");
    }


    public WebDriverUtils(WebDriver driver) {
		this.driver = driver;
		browserName = getBrowserName(driver);
	}

	public boolean checkIfElementIdMatchesIDPattern(String elementId, String pattern) {
		RegExpUtils u = new RegExpUtils(elementId, pattern);
		Matcher m = u.getMatcher();
		return m.matches();
	}

	public boolean isPageReady() {
		if (browserName.toLowerCase().contains("expl"))
			return IsPageReadyUtils.isDocumentStateReady(driver);

		boolean isScriptsLoaded = IsPageReadyUtils.isScriptsLoadingDone(driver);

		boolean isDocReady = IsPageReadyUtils.isDocumentStateReady(driver);

		return isDocReady && isScriptsLoaded;
	}


	public WebElement getWebElementIfPresents(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, TimeOutsConfig.getDriverImplicitWaitTimeOutMillis());
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		WebElement el = driver.findElement(locator);
		return el;
	}

	public String readFromLocation(By locator) {
		WebElement el = getWebElementIfPresents(locator);
		String text = el.getText();
		return text;
	}
	
	public String getPageSource() {
		return driver.getPageSource();
	}

	
	public String readFromLocation(String location) {
		LocatorFactory lu = new LocatorFactory();
		By locator = lu.getLocator(location);
		String readText = readFromLocation(locator);
		return readText;
	}
	
	public String readFromLocation(String location, int length) {
		LocatorFactory lu = new LocatorFactory();
		By locator = lu.getLocator(location);
		String readText = readFromLocation(locator);
		if (length >= readText.length()) return readText;
		return readText.substring(0, length-1);
	}
	
	public void openPage(String url) {
		driver.get(url);
		isPageReady();

	}

	public void refresh() {
	    driver.navigate().refresh();
	    isPageReady();
	}

	public void goBack(String url) {
		driver.navigate().to(url);
		isPageReady();
	}

	public void pause(long explicitWaitMillSec) {
		try {
			Thread.sleep(explicitWaitMillSec);
		}
		catch(Exception e) {}
	}

	public String getBrowserName() {
		return browserName;
	}

	protected void checkWebDriberNotNull() {
		if (driver == null)
			throw new RuntimeException("Driver is null");
	}

	private String getBrowserName(WebDriver driver) {
		Capabilities cap = ((RemoteWebDriver)driver).getCapabilities();
		return cap.getBrowserName();
	}
}
