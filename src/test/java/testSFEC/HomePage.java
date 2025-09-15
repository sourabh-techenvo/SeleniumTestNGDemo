package testSFEC;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
    	if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance is null!");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public void searchForActionsOrPeople(String input) {
        try {
            Thread.sleep(5000); // 0.5 second delay

            Actions actions = new Actions(driver);

            for (int i = 0; i < 3; i++) {
                actions.sendKeys(Keys.TAB).perform();
                Thread.sleep(500);
            }

            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
            actions.sendKeys(Keys.BACK_SPACE).perform();

            actions.sendKeys(input).perform();
            Thread.sleep(10000);

            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(500);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(500);

            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
