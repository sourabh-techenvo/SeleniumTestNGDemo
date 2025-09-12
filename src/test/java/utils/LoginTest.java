package utils;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginWithScreenshots() throws Exception {
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // Create ActionLogger with target PDF file
        ActionLogger logger = new ActionLogger(driver,
                Paths.get("target/reports/LoginSteps.pdf"));

        // Step 1: Enter username
        driver.findElement(By.id("username")).sendKeys("student");
        logger.logStep("Entered username");

        // Step 2: Enter password
        driver.findElement(By.id("password")).sendKeys("Password123");
        logger.logStep("Entered password");

        // Step 3: Click submit
        driver.findElement(By.id("submit")).click();
        logger.logStep("Clicked on submit button");

        // Generate PDF with all screenshots & descriptions
        logger.generatePdfReport();
    }
}
