package testSFEC;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import testSFEC.BasePage;
// import Pages.AddNewEmployeePage;
import testSFEC.HomePage;
import testSFEC.LoginPage;
import testSFEC.ManageData;
import testSFEC.ManageOrganizationPayandJobStructuresPage;
// import Pages.ManagePositions;
// import Pages.PersonalInfo;
// import Pages.PositionOrgChartPage;
// import Pages.ProbationPage;
// import Pages.SAPSuccessFactorONBPage;
// import Pages.SAPSuccessFactorRCMPage;

public class PageObjectManager {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private BasePage basePage;
    private ManageOrganizationPayandJobStructuresPage manageOrganizationPage;
    private ManageData manageData;

    // private ManagePositions managePositions;

    // private AddNewEmployeePage addNewEmployeePage;

    // private PositionOrgChartPage positionOrgChartPage;

    // private PositionOrgChartPage positionOrgChart;
    // private PersonalInfo personalinfo;
    // private ProbationPage probation;
    // private ProbationPage probationPage;
    // private SAPSuccessFactorONBPage onboardingPage;

    // private SAPSuccessFactorRCMPage rcmPage;



    // ✅ Fix: Proper constructor to store WebDriver
    public PageObjectManager(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance cannot be null.");
        }
        this.driver = driver;
    }

    public BasePage getBasePage() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
        if (basePage == null) {
        	basePage = new BasePage(driver);
        }
        return basePage;
    }


    public LoginPage getLoginPage() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }

    public HomePage getHomePage() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
        if (homePage == null) {
            homePage = new HomePage(driver);
        }
        return homePage;
    }

    public ManageOrganizationPayandJobStructuresPage getManageOrganizationPayandJobStructuresPage()
            throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
        if (manageOrganizationPage == null) {
            manageOrganizationPage = new ManageOrganizationPayandJobStructuresPage(driver);
        }
        return manageOrganizationPage;
    }

	public ManageData getManageData() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
	     if (manageData == null) {
	    	 manageData = new ManageData(driver);
	        }
	        return manageData;
	}



//     public ManagePositions getManagePositions()

//             throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
//         if (managePositions == null) {
//             managePositions = new ManagePositions(driver);
//         }
//         return managePositions;
//     }

// 	public PositionOrgChartPage getPositionOrgChartPage() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
// 	     if (positionOrgChart == null) {
// 	    	 positionOrgChart = new PositionOrgChartPage(driver);
// 	        }
// 	        return positionOrgChart;
// 	}


// 	public AddNewEmployeePage getAddNewEmployeePage() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
//         if (addNewEmployeePage == null) {
//             addNewEmployeePage = new AddNewEmployeePage(driver);
//            }
//            return addNewEmployeePage;
//    }


// 	public PersonalInfo getPersonalInfo() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
// 	     if (personalinfo == null) {
// 	    	 personalinfo = new PersonalInfo(driver);
// 	        }
// 	        return personalinfo;
// 	}
// 	public ProbationPage getProbation() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
// 	     if (probation == null) {
// 	    	 probation = new ProbationPage(driver);
// 	        }
// 	        return probation;
// 	}


// 	public SAPSuccessFactorRCMPage getSAPSuccessFactorRCMPage() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
// 	     if (rcmPage == null) {
// 	    	 rcmPage = new SAPSuccessFactorRCMPage(driver);
// 	        }
// 	        return rcmPage;
// 	}

// 	public ProbationPage getProbationPage() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
//         if (probationPage == null) {
//             probationPage = new ProbationPage(driver);
//            }
//            return probationPage;
//    }

// 	public  SAPSuccessFactorONBPage getOnboardingPage() throws TimeoutException, MalformedURLException, InterruptedException, IOException, ExecutionException, AWTException {
//         if (onboardingPage == null) {
//         	onboardingPage = new SAPSuccessFactorONBPage(driver);
//            }
//            return onboardingPage;
//    }

}
