package utils;


import org.testng.Assert;
import org.testng.annotations.*;

@Listeners(TestListener.class)
public class SampleTests extends BaseTest {

//    @BeforeMethod
//    public void setUp() {
//        createDriver();
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        quitDriver();
//    }

    @Test
    public void testGoogleTitle_pass() {
        getDriver().get("https://www.google.com");
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("google"), "Title should contain google");
    }

    @Test
    public void testExampleFail_withScreenshot() {
        getDriver().get("https://example.com");
        String title = getDriver().getTitle();
        // Intentional failure to capture screenshot + demonstrate reports
        Assert.assertEquals(title, "THIS WILL FAIL", "Forcing a failure to demo reports");
    }
}
