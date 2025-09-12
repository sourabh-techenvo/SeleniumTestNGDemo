package utils;


import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FacebookLoginTest extends BaseTest {

	@Test
	public void testFacebookLoginPage() {
	    driver.get("https://www.facebook.com/login");

	    WebElement emailField = driver.findElement(By.id("email"));
	    WebElement passwordField = driver.findElement(By.id("pass"));
	    WebElement loginButton = driver.findElement(By.name("login"));

	    emailField.sendKeys("test@example.com");
	    passwordField.sendKeys("wrongpassword");

	    loginButton.click();

	    // Wait for login error
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    try {
	        WebElement errorMsg = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div._9ay7"))
	        );
	        Assert.assertTrue(errorMsg.isDisplayed(), "Error message should appear");
	    } catch (Exception e) {
	        Assert.fail("Login error message not displayed");
	    }
	}

}
