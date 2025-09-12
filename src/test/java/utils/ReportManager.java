package utils;


import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;

import java.io.File;
import java.util.List;

public class ReportManager {

    public static void generateHtmlReport(List<ITestResult> results, File htmlOutputDir, File screenshotsDir) {
        try {
            if (!htmlOutputDir.exists()) htmlOutputDir.mkdirs();
            File htmlFile = new File(htmlOutputDir, "index.html");
            ExtentSparkReporter spark = new ExtentSparkReporter(htmlFile);
            ExtentReports extent = new ExtentReports();
            extent.attachReporter(spark);

            for (ITestResult r : results) {
                String testName = r.getMethod().getMethodName();
                ExtentTest test = extent.createTest(r.getTestClass().getName() + "." + testName);
                Status s = mapStatus(r.getStatus());
                if (r.getThrowable() != null) {
                    test.log(s, r.getThrowable());
                } else {
                    test.log(s, "Test " + s.toString());
                }

                Object screenshotPath = r.getAttribute("screenshot");
                if (screenshotPath != null) {
                    String sPath = screenshotPath.toString();
                    File f = new File(sPath);
                    if (f.exists()) {
                        test.addScreenCaptureFromPath(f.getAbsolutePath());
                    }
                }
            }
            extent.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Status mapStatus(int testNgStatus) {
        switch (testNgStatus) {
            case ITestResult.FAILURE:
                return Status.FAIL;
            case ITestResult.SKIP:
                return Status.SKIP;
            default:
                return Status.PASS;
        }
    }
}
