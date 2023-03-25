package com.softserve.edu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

/**
 * The test checks whether the product "iMac"
 * at the price of 111.55 euros is present on the page.
 */
public class ItemTest {
    private final String EXPECTED_PRODUCT_NAME = "iMac";
    private final String EXPECTED_PRICE = "111.55";
    private final String EXPECTED_CURRENCY = "€";

    @Test
    public void checkPresenceOfItem(){
        chromedriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*","ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        //Open Home Page
        driver.get("https://demo.opencart.com/");
        //Select currency Euro
        driver.findElement(By.xpath("//span[contains(text(), 'Currency')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='EUR']"))).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//strong[contains(text(), '€')]"),"€"));
        //Click on the Desktops and Mac menu
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//a[contains(text(), 'Desktops')][@class='nav-link dropdown-toggle']"))).perform();
        driver.findElement(By.xpath("//a[contains(text(), 'Mac')][contains(@href, 'path=20_27')]")).click();
        wait.until(ExpectedConditions.urlContains("path=20_27"));
        //Check whether the product "iMac" at the price of 111.55 euros is present on the page
        SoftAssert softAssert = new SoftAssert();
        Assert.assertEquals(driver.findElement(By.xpath("//a[contains(text(),'iMac')]")).getText(),(EXPECTED_PRODUCT_NAME));
        Assert.assertTrue(driver.findElement(By.xpath("//span[@class='price-new']")).getText().contains(EXPECTED_PRICE));
        Assert.assertTrue(driver.findElement(By.xpath("//span[@class='price-new']")).getText().contains(EXPECTED_CURRENCY));
        softAssert.assertAll();
    }
}
