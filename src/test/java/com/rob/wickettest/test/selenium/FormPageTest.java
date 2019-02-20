package com.rob.wickettest.test.selenium;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.openqa.selenium.By.*;
import static org.junit.Assert.*;

@Category(SeleniumCategory.class)
public class FormPageTest
{
	private static final Logger log = LoggerFactory.getLogger(FormPageTest.class);
	private static final String APP_URL_PROPERTY = "app.url";
	private static final long TIMEOUT = 5L;
	private static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	private static final String FORM_LINK_ID = "form-page-link";

	private static ChromeDriverService chromeDriverService;
	private static String testUrl;

	private WebDriver webDriver;

	@BeforeClass
	public static void beforeClass() throws IOException
	{

		chromeDriverService = ChromeDriverService.createDefaultService();
		testUrl = System.getProperty(APP_URL_PROPERTY);
		chromeDriverService.start();
	}

	@AfterClass
	public static void afterClass()
	{
		chromeDriverService.stop();
	}

	@Before
	public void before() throws MalformedURLException
	{
		webDriver = new RemoteWebDriver(chromeDriverService.getUrl(), new ChromeOptions());
		webDriver.manage().timeouts().implicitlyWait(TIMEOUT, TIMEOUT_UNIT);
		webDriver.get(testUrl);
	}

	@After
	public void after()
	{
		webDriver.quit();
	}

	/**
	 * Verify the page has a submit button.
	 */
	@Test
	public void testSubmitExists()
	{
		WebElement submitButton = null;
		navigateToFormPage();
		for (WebElement e : webDriver.findElements(tagName("input")))
		{
			if (e.getAttribute("type").equalsIgnoreCase("submit"))
			{
				submitButton = e;
				break;
			}
		}
		assertNotNull(submitButton);
	}

	@Test
	public void testSubmitExistsByXpath() throws InterruptedException
	{
		navigateToFormPage();
		final WebElement submitButton = webDriver
				.findElement(xpath("//label[@for='text-field']/../..//input[@type='submit']"));
		assertNotNull(submitButton);
	}

	private void navigateToFormPage()
	{
		final WebElement formLink = webDriver.findElement(id(FORM_LINK_ID));
		formLink.click();
	}
}
