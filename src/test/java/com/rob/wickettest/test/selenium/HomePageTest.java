package com.rob.wickettest.test.selenium;

import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

@Category(SeleniumCategory.class)
public class HomePageTest
{
    private static WebDriver webDriver;
    private static String appUrl;

    @BeforeClass
    public static void beforeClass()
    {
        // Get the app url
        appUrl = System.getProperty("app.url");

        // Download the correct Chrome driver.
        WebDriverManager.chromedriver().setup();

        // Create the Chrome driver.
        //webDriver = new ChromeDriver();
    }

    @AfterClass
    public static void afterClass()
    {
        // Close Chrome.
        //webDriver.quit();
    }

    @Before
    public void before()
    {
        open(appUrl);
    }

    @Test
    public void checkDetailsPageLink()
    {
        assertTrue("Could not find Details Page link", $(By.xpath("//a[contains(text(), 'Details Page')]")).exists());
    }

    @Test
    public void checkFormPageLink()
    {
        assertTrue("Could not find Form Page link", $(By.xpath("//a[contains(text(), 'Form Page')]")).exists());
    }
}
