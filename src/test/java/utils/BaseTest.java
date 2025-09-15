package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import testSFEC.LoginPage;
import testSFEC.PageObjectManager;
import testSFEC.TestData;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class BaseTest {
    protected WebDriver driver;

    
    private PageObjectManager pageObjectManager;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // FIX: set unique user-data-dir
        String uniqueProfile = "/tmp/chrome-profile-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + uniqueProfile);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();


        // Headless for CI
        // options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // options.addArguments("start-maximized");



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

    	 public void LaunchApplication() throws InterruptedException, TimeoutException, MalformedURLException, IOException, ExecutionException, AWTException {
	        WebDriver driver = getDriver();
	        if (driver == null) {
	            throw new IllegalStateException("WebDriver is not initialized.");
	        }

	        System.out.println("🚀 Launching Application: " + TestData.url);

	        if (pageObjectManager == null) {
	            pageObjectManager = new PageObjectManager(driver);
	        }

	        pageObjectManager.getBasePage().navigateTo(TestData.url);

	        loginPage = pageObjectManager.getLoginPage();
	        loginPage.login();
	        Thread.sleep(5000); // 0.5 second delay

	        System.out.println("Successfully Logged In!");
	    }

           public PageObjectManager getPageObjectManager() {
	        if (pageObjectManager == null) {
	            pageObjectManager = new PageObjectManager(getDriver());
	        }
	        return pageObjectManager;
	    }
}
