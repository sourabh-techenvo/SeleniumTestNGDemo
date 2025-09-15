package testSFEC;


import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void navigateTo(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL is null or empty!");
        }
        logger.info("Navigating to URL: " + url);
        driver.get(url);
    }

    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: " + title);
        return title;
    }

    public String getCurrentURL() {
        String currentURL = driver.getCurrentUrl();
        logger.info("Current URL: " + currentURL);
        return currentURL;
    }
}
