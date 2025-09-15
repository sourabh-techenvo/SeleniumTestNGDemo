package testSFEC;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

// import ExtentReports.ExtentTestManager;
import testSFEC.HomePage;
import testSFEC.ManageData;
import testSFEC.PageObjectManager;
// import TestBase;
// import ScreenShot;
// import ScreenshoReporter;
import utils.*;

public class TS_0001_SFEC_LEGALENTITY_CREATE extends BaseTest {

    private PageObjectManager pageObjectManager;
    private HomePage homePage;
    private ManageData manageData;
    // private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

            private WebDriver driver;

    // @BeforeClass
    // public void setUp() {
    //     // Start Chrome before tests
    //     driver = new ChromeDriver();
    //     driver.manage().window().maximize();
    // }


//    @BeforeMethod
//    public void setUp() {
//    }

    @Override
	@BeforeMethod
    public void LaunchApplication() throws TimeoutException, MalformedURLException, InterruptedException, IOException,
            ExecutionException, AWTException {
        try {
            super.LaunchApplication();
//            ScreenshotReporter.setCurrentTestClass("TS_0001_SFEC_LEGALENTITY_CREATE");
        //    logger.logStep("Application Launched");
        } catch (Exception e) {
            // ExtentTestManager.getTest().fail("Failed to launch the application: " + e.getMessage());
            throw new AssertionError("Failed to launch the application: " + e.getMessage());
        }
    }





    @Test
    public void LegalEntity_CREATE() throws Exception {
        // ExtentTest test = ExtentTestManager.getTest();
         driver = getDriver();  
        // driver.get("https://practicetestautomation.com/practice-test-login/");

        // Create ActionLogger
        // ActionLogger logger = new ActionLogger(driver,
        //         Paths.get("target/reports/LoginSteps.pdf"));
    ActionLogger logger = new ActionLogger(driver,
            Paths.get("target/reports/TS_0001_SFEC_LEGALENTITY_CREATE.pdf"));

        try {
            pageObjectManager = getPageObjectManager();
            homePage = pageObjectManager.getHomePage();
           
            actions = new Actions(driver);
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            PageFactory.initElements(driver, this);

            // ScreenShot.setDriver(driver);
//            ScreenshotReporter.setCurrentTestClass("TS_0001_SFEC_LEGALENTITY_CREATE");

            homePage.searchForActionsOrPeople("Manage Data");
            logger.logStep("Search Action Performed");

            manageData = pageObjectManager.getManageData();

            manageData.selectsearchentity(4, "Legal Entity");
            logger.logStep("Selected Legal Entity");

            manageData.selectEffectiveStartDate(10);
            logger.logStep("Selected Effective Start Date");

            WebElement LegalEntityID = manageData.getInputField(1);
            String LegalEntityIDNo=Utilities.generateIntegers(6);
            manageData.setData(LegalEntityID, LegalEntityIDNo);
            logger.logStep("Created legal entity with id: "+LegalEntityIDNo+"");



            WebElement LegalEntityName = manageData.getInputField(2);
            manageData.setData(LegalEntityName, Utilities.generateRandomString(5));

            WebElement description = manageData.getInputField(3);
            manageData.setData(description, "Creating New Legal Entity");
            Thread.sleep(5000);

            manageData.selectDropdownOption(5, "Active");
            logger.logStep("Selected Status as Active");

            manageData.selectsearchdropdown(6, "New Zealand Monthly");
            logger.logStep("Default Pay Group is Selected");

            manageData.selectsearchdropdown(7, "Hong Kong");
            logger.logStep("Parent Cost Center is Selected");

            manageData.setstandardhours("40");
            logger.logStep("Entered Standard Hours");
            Thread.sleep(8000);

            manageData.selectsearchdropdown(8, "Indian Rupee");
            logger.logStep("Currency is Selected");
            Thread.sleep(6000);

            manageData.selectsearchdropdown(9, "India");
            logger.logStep("Country is Selected");
            Thread.sleep(5000);

            manageData.selectsearchdropdown(10, "Display Name ");
            logger.logStep("toNameFormatGO is Selected");
            Thread.sleep(5000);

            manageData.selectsearchdropdown(11, "Display Name ");
            logger.logStep("Name Format for Legal Entity is Selected");
            Thread.sleep(5000);

            manageData.selectsearchdropdown(12, "Display Name ");
            logger.logStep("Name Format for General Display is Selected");
            Thread.sleep(2000);

            manageData.Save();
            logger.logStep("Clicked Save Button");

            // test.pass("Test Case Passed");


            // ScreenshotReporter.flushScreenshotsForClass("TS_0001_SFEC_LEGALENTITY_CREATE");

        } catch (Exception e) {
            logger.logStep("Test Failure" );
            // if (test != null) {
            //     test.fail("Test Failed due to: " + e.getMessage());
            //     logger.logStep("Test Failure");
            // }
            throw new AssertionError("Test Failed due to: " + e.getMessage());
        }
    }

    @AfterMethod
    public void CloseApplication() {
        try {
//quitDriver();
        //    logger.logStep("Application Closed");
        } catch (Exception e) {
            // ExtentTestManager.getTest().fail("Failed to close the application: " + e.getMessage());
            throw new AssertionError("Failed to close the application: " + e.getMessage());
        }
    }
}

