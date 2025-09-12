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
	public void testFacebookLoginPage()  {
	    driver.get("https://www.facebook.com/login");
	    Assert.assertTrue(driver.getTitle().contains("Facebook"), "Page title should contain 'Facebook'");
	}
	


}
