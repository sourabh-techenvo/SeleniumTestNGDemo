package utils;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FacebookLoginTest extends BaseTest {

    @Test
    public void testFacebookLoginPage() {
        // 1️⃣ Open Facebook login page
        driver.get("https://www.facebook.com/login");

        // 2️⃣ Wait a bit for page to load (or use explicit waits)
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // 3️⃣ Find email and password fields
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("pass"));
        WebElement loginButton = driver.findElement(By.name("login"));

        // 4️⃣ Enter dummy credentials
        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("incorrectpassword");

        // 5️⃣ Check that login button is displayed
        Assert.assertTrue(loginButton.isDisplayed(), "Login button should be visible");

        // Optional: Click login to trigger error message (will fail because credentials are invalid)
        loginButton.click();

        // 6️⃣ Wait for error message
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        WebElement errorMsg = driver.findElement(By.xpath("//div[contains(text(),'incorrect')]"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message should appear for invalid credentials");
    }
}
