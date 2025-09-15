package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Logs steps with screenshots for PDF reports.
 */
public class ActionLogger {

    public final WebDriver driver;
    public final Path outputDir;
    public final List<Step> steps = new ArrayList<>();

    public ActionLogger(WebDriver driver, Path outputDir) throws Exception {
        this.driver = driver;
        this.outputDir = outputDir;

        if (!Files.exists(outputDir.getParent())) {
            Files.createDirectories(outputDir.getParent());
        }
    }

    public void logStep(String description) {
        try {
            // Take screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dest = outputDir.getParent().resolve(
                    System.currentTimeMillis() + "_step.png"
            );
            Files.copy(src.toPath(), dest);

            steps.add(new Step(description, dest.toAbsolutePath().toString()));
        } catch (Exception e) {
            steps.add(new Step(description + " (screenshot failed)", null));
        }
    }

    /**
     * Attach steps to the current TestNG result so PdfReportGenerator can pick them up.
     */
    public void generatePdfReport() {
        ITestResult result = Reporter.getCurrentTestResult();
        if (result != null) {
            result.setAttribute("steps", new ArrayList<>(steps));
        }
    }

    // Step object
    public static class Step {
        private final String description;
        private final String screenshotPath;

        public Step(String description, String screenshotPath) {
            this.description = description;
            this.screenshotPath = screenshotPath;
        }

        public String getDescription() {
            return description;
        }

        public String getScreenshotPath() {
            return screenshotPath;
        }
    }
}
