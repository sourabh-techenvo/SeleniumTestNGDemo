package utils;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import org.testng.ITestResult;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Generate PDF reports for each test case with screenshots & step descriptions.
 */
public class PdfReportGenerator {

    @SuppressWarnings("unchecked")
    public static void generatePdfs(List<ITestResult> results, File pdfDir, File screenshotsDir) {
        try {
            if (!pdfDir.exists()) pdfDir.mkdirs();

            for (ITestResult result : results) {
                String testName = result.getMethod().getMethodName();
                String pdfFilePath = new File(pdfDir, testName + ".pdf").getAbsolutePath();

                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
                document.open();

                // Title
              Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
                Paragraph title = new Paragraph("Test Case: " + testName, titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                // Status
                Font statusFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                String status = result.isSuccess() ? "PASSED" : "FAILED";
                Paragraph statusPara = new Paragraph("Status: " + status, statusFont);
                statusPara.setSpacingAfter(10);
                document.add(statusPara);

                // Error (if any)
                if (result.getThrowable() != null) {
                    Paragraph errorPara = new Paragraph("Error: " + result.getThrowable().getMessage(),
                            FontFactory.getFont(FontFactory.HELVETICA, 10));
                    errorPara.setSpacingAfter(10);
                    document.add(errorPara);
                }

                // 🔹 Prefer multiple step logs if present
                Object stepsAttr = result.getAttribute("steps");
                if (stepsAttr != null && stepsAttr instanceof List) {
                    List<ActionLogger.Step> steps = (List<ActionLogger.Step>) stepsAttr;
                    for (ActionLogger.Step step : steps) {
                        Paragraph stepDesc = new Paragraph("Step: " + step.getDescription(),
                                FontFactory.getFont(FontFactory.HELVETICA, 11));
                        stepDesc.setSpacingBefore(10);
                        stepDesc.setSpacingAfter(5);
                        document.add(stepDesc);

                        if (step.getScreenshotPath() != null) {
                            try {
                                Image screenshot = Image.getInstance(step.getScreenshotPath());
                                screenshot.scaleToFit(500, 350);
                                screenshot.setAlignment(Element.ALIGN_CENTER);
                                document.add((Element) screenshot);
                            } catch (Exception e) {
                                document.add(new Paragraph("Failed to load step screenshot: " + e.getMessage()));
                            }
                        }
                    }
                } else {
                    // 🔹 fallback: single screenshot from TestListener
                    Object screenshotAttr = result.getAttribute("screenshot");
                    if (screenshotAttr != null) {
                        String screenshotPath = screenshotAttr.toString();
                        try {
                            Image screenshot = Image.getInstance(screenshotPath);
                            screenshot.scaleToFit(500, 350);
                            screenshot.setAlignment(Element.ALIGN_CENTER);
                            document.add(new Paragraph("Final Screenshot:"));
                            document.add((Element) screenshot);
                        } catch (Exception e) {
                            document.add(new Paragraph("Failed to add screenshot: " + e.getMessage()));
                        }
                    }
                }

                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
