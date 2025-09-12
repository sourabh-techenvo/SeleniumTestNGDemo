package utils;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class TestListener implements ITestListener {
    private final List<ITestResult> results = Collections.synchronizedList(new ArrayList<>());

    private final Path reportsRoot = Paths.get("target", "reports");
    private final Path screenshotsDir = reportsRoot.resolve("screenshots");
    private final Path htmlDir = reportsRoot.resolve("html");
    private final Path pdfDir = reportsRoot.resolve("pdfs");

    @Override
    public void onStart(ITestContext context) {
        try {
            Files.createDirectories(screenshotsDir);
            Files.createDirectories(htmlDir);
            Files.createDirectories(pdfDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        results.add(result);
        takeScreenshot(result); // take screenshot even on success
    }

    @Override
    public void onTestFailure(ITestResult result) {
        results.add(result);
        takeScreenshot(result);
    }

    // helper method
    private void takeScreenshot(ITestResult result) {
        Object instance = result.getInstance();
        if (instance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) instance).getDriver();
            if (driver != null) {
                try {
                    Files.createDirectories(screenshotsDir);
                    String fileName = result.getMethod().getMethodName() + "_" + System.currentTimeMillis() + ".png";
                    File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    Path dest = screenshotsDir.resolve(fileName);
                    FileUtils.copyFile(srcFile, dest.toFile());
                    result.setAttribute("screenshot", dest.toAbsolutePath().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        results.add(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        // Generate HTML (ExtentReports), Excel, and PDFs
        try {
            ReportManager.generateHtmlReport(results, htmlDir.toFile(), screenshotsDir.toFile());
            ExcelReportGenerator.generateExcel(results, reportsRoot.resolve("report.xlsx").toFile());
            PdfReportGenerator.generatePdfs(results, pdfDir.toFile(), screenshotsDir.toFile());

            // Zip them
            Utils.zipDirectory(htmlDir.toFile(), reportsRoot.resolve("html-report.zip").toFile());
            Utils.zipFile(reportsRoot.resolve("report.xlsx").toFile(), reportsRoot.resolve("excel-report.zip").toFile());
            Utils.zipDirectory(pdfDir.toFile(), reportsRoot.resolve("pdf-report.zip").toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // unused / no-op
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onTestFailedWithTimeout(ITestResult result) {}
}
