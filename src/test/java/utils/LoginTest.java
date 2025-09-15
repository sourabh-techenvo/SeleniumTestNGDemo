package utils;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class LoginTest extends BaseTest {

    @Test
public void testLoginWithScreenshots() throws Exception {
    driver.get("https://practicetestautomation.com/practice-test-login/");

    // Create ActionLogger
    ActionLogger logger = new ActionLogger(driver,
            Paths.get("target/reports/LoginSteps.pdf"));

    driver.findElement(By.id("username")).sendKeys("student");
    logger.logStep("Entered username");

    driver.findElement(By.id("password")).sendKeys("Password123");
    logger.logStep("Entered password");

    driver.findElement(By.id("submit")).click();
    logger.logStep("Clicked on submit button");

    // Save steps into TestNG result (PdfReportGenerator will pick them up)
    logger.generatePdfReport();
}

}
