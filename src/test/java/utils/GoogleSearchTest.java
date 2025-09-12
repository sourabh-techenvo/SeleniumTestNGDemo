package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(utils.TestListener.class)   // hook your existing listener
public class GoogleSearchTest extends BaseTest {

	@Test
	public void testOpenGoogle() {
	    driver.get("https://www.google.com");
	    Assert.assertTrue(driver.getTitle().contains("Google"), "Page title should contain 'Google'");
	}

	
//    @Test
//    public void testGoogleSearch() {
//        driver.get("https://www.google.com");
//
//        WebElement searchBox = driver.findElement(By.name("q"));
//        searchBox.sendKeys("Selenium WebDriver");
//        searchBox.submit();
//
//        // validation
//        String title = driver.getTitle();
//        Assert.assertTrue(title.contains("Selenium"),
//                "Expected title to contain 'Selenium' but got: " + title);
//    }
}
