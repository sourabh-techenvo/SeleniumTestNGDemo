package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Files;
import java.nio.file.Path;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();

        // Headless for CI
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // ✅ Use a unique temporary user-data-dir to avoid session conflicts
        Path tempDir = Files.createTempDirectory("chrome-user-data");
        options.addArguments("--user-data-dir=" + tempDir.toAbsolutePath());

        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
