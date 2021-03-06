package org.qamation.webdriver.utils;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

	public static WebDriver createRemoteIEDriver(URL hub) {
		DesiredCapabilities dc =  setIECapabilities();
		RemoteWebDriver driver = createRemoteWebdriver(hub, dc);
		return driver;
	}

	public static WebDriver createChromeWebDriver(String path) {
		System.setProperty("webdriver.chrome.driver",path);
		DesiredCapabilities dc = setChromeCapabilities();
		addDefaultCapabilitiesTo(dc);
		WebDriver driver = new ChromeDriver(dc);
		return driver;
	}

	public static WebDriver createIEWebDriver(String path) {
		System.setProperty("webdriver.ie.driver",path);
		DesiredCapabilities dc = setIECapabilities();
		addDefaultCapabilitiesTo(dc);
		WebDriver driver = new InternetExplorerDriver(dc);
		return driver;
	}

	public static WebDriver createFFWebDriver(String path) {
		System.setProperty("webdriver.firefox.bin",path);
		DesiredCapabilities dc = setFFCapabilities();
		addDefaultCapabilitiesTo(dc);
		WebDriver driver = new FirefoxDriver(dc);
		return driver;
	}
	
	public static WebDriver createRemoteChromeDriver(URL hub) {
		DesiredCapabilities dc = setChromeCapabilities();
		RemoteWebDriver driver = createRemoteWebdriver(hub, dc);
		return driver;
	}


    public static WebDriver createRemoteFFWebDriver(URL hub) {
		DesiredCapabilities dc = setFFCapabilities();
		RemoteWebDriver driver = createRemoteWebdriver(hub,dc);
		return driver;
    }

	private static DesiredCapabilities setFFCapabilities() {
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		return dc;
	}

	private static RemoteWebDriver createRemoteWebdriver(URL hub, DesiredCapabilities capabilities) {
		addDefaultCapabilitiesTo(capabilities);
		RemoteWebDriver driver = 	new RemoteWebDriver(hub, capabilities);
		return driver;
	}


	private static void addDefaultCapabilitiesTo(DesiredCapabilities dc) {
		dc.setCapability(org.openqa.selenium.remote.CapabilityType.ACCEPT_SSL_CERTS, true);
		dc.setCapability(org.openqa.selenium.remote.CapabilityType.SUPPORTS_JAVASCRIPT,true);
		dc.setCapability(org.openqa.selenium.remote.CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
	}

	private static DesiredCapabilities setIECapabilities() {
		DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
		dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
		dc.setCapability(
				org.openqa.selenium.ie.InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				true);

		dc.setCapability(
				org.openqa.selenium.ie.InternetExplorerDriver.IGNORE_ZOOM_SETTING,
				true);
		dc.setCapability(org.openqa.selenium.ie.InternetExplorerDriver.NATIVE_EVENTS, true);
		dc.setCapability(org.openqa.selenium.ie.InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,
				true);
		dc.setCapability(org.openqa.selenium.ie.InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,
				true);

		//dc.setCapability(org.openqa.selenium.ie.InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);

		return dc;
	}

	private static DesiredCapabilities setChromeCapabilities() {
		DesiredCapabilities dc = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options = options.addArguments("--disable-extensions");
		dc.setCapability(ChromeOptions.CAPABILITY, options);
		return dc;
	}


}
