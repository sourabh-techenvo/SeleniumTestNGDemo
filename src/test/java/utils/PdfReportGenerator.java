package utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.testng.ITestResult;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfReportGenerator {

    /**
     * Generate PDFs for each test result.
     *
     * @param results        List of ITestResult from TestListener
     * @param pdfDir         Folder to save PDFs
     * @param screenshotsDir Folder where screenshots are stored
     */
    public static void generatePdfs(List<ITestResult> results, File pdfDir, File screenshotsDir) {
        try {
            if (!pdfDir.exists()) pdfDir.mkdirs();

            for (ITestResult result : results) {
                String testName = result.getMethod().getMethodName();
                String pdfFilePath = new File(pdfDir, testName + ".pdf").getAbsolutePath();

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
                document.open();

                // Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLUE);
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
                    Paragraph errorPara = new Paragraph("Error: " + result.getThrowable().getMessage());
                    errorPara.setSpacingAfter(10);
                    document.add(errorPara);
                }

                // Screenshot
                Object screenshotAttr = result.getAttribute("screenshot");
                if (screenshotAttr != null) {
                    String screenshotPath = screenshotAttr.toString();
                    try {
                        Image screenshot = Image.getInstance(screenshotPath);
                        screenshot.scaleToFit(500, 400);
                        screenshot.setAlignment(Element.ALIGN_CENTER);
                        document.add(screenshot);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Paragraph imgError = new Paragraph("Failed to add screenshot: " + e.getMessage());
                        document.add(imgError);
                    }
                }

                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
